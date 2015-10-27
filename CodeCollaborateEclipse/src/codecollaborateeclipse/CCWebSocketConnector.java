package codecollaborateeclipse;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import codecollaborateeclipse.models.FileChangeRequest;
import codecollaborateeclipse.models.LoginRequest;
import codecollaborateeclipse.models.SubscribeRequest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by fahslaj on 10/10/2015.
 */
public class CCWebSocketConnector {

    private Queue requestQueue;
    private HashMap requestMap;
    private ObjectMapper mapper = new ObjectMapper();

    WebSocketClient client;
    CCWebSocket socket;
    URI uri;
    
    public boolean sendPatch() {
    	return sendPatch ("@@ -40,16 +40,17 @@\\n almost i\\n+t\\n n shape");
    }
    
    public boolean sendPatch(String patch) {
    	int Tag = 112;
    	String ResId = "5629a0c2111aeb63cf000002";
    	long FileVersion = System.currentTimeMillis();
    	String Changes = patch.replaceAll("\n", "");
    	String UserId = "56297d8e111aeb5f53000001";
    	String Token = "token-fahslaj";
    	
        FileChangeRequest fcr = new FileChangeRequest(Tag);
        fcr.setResId(ResId);
        fcr.setFileVersion(FileVersion);
        fcr.setChanges(Changes);
        fcr.setUserId(UserId);
        fcr.setToken(Token);
    	try {
            socket.sendMessage(mapper.writeValueAsString(fcr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean login() {
        return login("fahslaj@rose-hulman.edu", "abcd1234");
    }

    public boolean login(String email, String password) {
        int tag = 5;
        LoginRequest lr = new LoginRequest(tag);
        lr.setEmail(email);
        lr.setPassword(password);
    	try {
            socket.sendMessage(mapper.writeValueAsString(lr));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean subscribe() {
        int tag = 10;
        String[] projects = {"5629a063111aeb63cf000001"};
        String userId = "56297d8e111aeb5f53000001";
        String token = "token-fahslaj";
        SubscribeRequest sr = new SubscribeRequest(tag);
        sr.setUserId(userId);
        sr.setToken(token);
        sr.setProjects(projects);
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
        client = new WebSocketClient();
        socket = new CCWebSocket();
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
        boolean closeStatus = false;
        try {
            closeStatus = socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return closeStatus;
    }
}
