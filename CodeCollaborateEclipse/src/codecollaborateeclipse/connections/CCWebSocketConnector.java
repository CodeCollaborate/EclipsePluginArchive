package codecollaborateeclipse.connections;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import codecollaborateeclipse.Storage;
import codecollaborateeclipse.events.ResponseNotificationListener;
import codecollaborateeclipse.models.FileChangeRequest;
import codecollaborateeclipse.models.LoginRequest;
import codecollaborateeclipse.models.Notification;
import codecollaborateeclipse.models.PullFileRequest;
import codecollaborateeclipse.models.Request;
import codecollaborateeclipse.models.Response;
import codecollaborateeclipse.models.ServerMessage;
import codecollaborateeclipse.models.SubscribeRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by fahslaj on 10/10/2015.
 */
public class CCWebSocketConnector {

	private ArrayList<ResponseNotificationListener> listeners = new ArrayList<ResponseNotificationListener>();
    private Queue requestQueue;
    private HashMap<Long, Request> requestMap = new HashMap();
    private ObjectMapper mapper = new ObjectMapper();
    private int currentTag = 0;

    WebSocketClient client;
    CCWebSocket socket;
    URI uri;
    
    public boolean sendPatch() {
    	return sendPatch ("@@ -40,16 +40,17 @@\\n almost i\\n+t\\n n shape");
    }
    
    public boolean sendPatch(String patch) {
    	String ResId = "5629a0c2111aeb63cf000002";
    	long FileVersion = System.currentTimeMillis();
    	String Changes = patch;//.replaceAll("\n", "");
    	
        FileChangeRequest fcr = new FileChangeRequest(getTag());
        fcr.setResId(ResId);
        fcr.setFileVersion(FileVersion);
        fcr.setChanges(Changes);
        fcr.setUsername(Storage.getUsername());
        fcr.setToken(Storage.getToken());
        requestMap.put(fcr.getTag(), fcr);
    	try {
            socket.sendMessage(mapper.writeValueAsString(fcr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean login() {
        LoginRequest lr = new LoginRequest(getTag());
        lr.setUsername(Storage.getUsername());
        lr.setPassword(Storage.getPassword());
        requestMap.put(lr.getTag(),  lr);
    	try {
            socket.sendMessage(mapper.writeValueAsString(lr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean subscribe() {
        String[] projects = {"5629a063111aeb63cf000001"};
        SubscribeRequest sr = new SubscribeRequest(getTag());
        sr.setUsername(Storage.getUsername());
        sr.setToken(Storage.getToken());
        sr.setProjects(projects);
        requestMap.put(sr.getTag(), sr);
        try {
            socket.sendMessage(mapper.writeValueAsString(sr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean connect() {
        return connect("ws://codecollaborate.csse.rose-hulman.edu/ws/");
    }

    public boolean connect(String uriString) {
    	if (client != null || socket != null) {
	        return false;
    	}
        client = new WebSocketClient();
        socket = new CCWebSocket(this);
        try {
            client.start();
            uri = new URI(uriString);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean close() {
    	if (client == null || socket == null) {
    		return false;
    	}
        boolean closeStatus = false;
        try {
            closeStatus = socket.awaitClose(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        client = null;
        socket = null;
        return closeStatus;
    }
    
    /**
     * Called when a response or notification is received from the server
     * @param jsonResponse
     */
    public void receiveMessage(String jsonMessage) {
    	JSONObject jobject = new JSONObject(jsonMessage);
    	String type = jobject.getString("Type");
    	if (type.equals("Response")) {
    		Response response = null;
			try {
				response = mapper.readValue(jsonMessage, Response.class);
			} catch (Exception e) {
				System.out.println("Error: Cannot map response.");
				e.printStackTrace();
				return;
			}
			if (requestMap.containsKey(response.getTag())) {
				notify(response, requestMap.remove(response.getTag()));
			} else {
				System.out.println("Extraneous response: "+response.getMessage());
				return;
			}
    	} else if (type.equals("Notification")) {
    		Notification notification = null;
    		try {
    			notification = mapper.readValue(jsonMessage, Notification.class);
    		} catch (Exception e) {
    			System.out.println("Error: Cannot map notification.");
    			e.printStackTrace();
    			return;
    		}
    		notify(notification, null);
    	} else {
    		System.out.println("Error: message is neither a response nor a notification.");
    		return;
    	}
    }
    
    public boolean pullDocument() {
    	return pullDocument("5629a0c2111aeb63cf000002");
    }
    
    public boolean pullDocument(String resId) {
        PullFileRequest pfr = new PullFileRequest(getTag());
        pfr.setResId(resId);
        pfr.setUsername(Storage.getUsername());
        pfr.setToken(Storage.getToken());
        requestMap.put(pfr.getTag(), pfr);
    	try {
            socket.sendMessage(mapper.writeValueAsString(pfr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private int getTag() {
    	return currentTag++;
    }
    
    public void addResponseNotificationListener(ResponseNotificationListener listener) {
    	if (listener == null)
    		throw new NullPointerException("Listener may not be null");
    	this.listeners.add(listener);
    }
    
    public void notify(ServerMessage r, Request req) {
    	for (ResponseNotificationListener listener : listeners) {
    		listener.respond(r, req);
    	}
    }
}
