package codecollaborateeclipse;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
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

    WebSocketClient client;
    CCWebSocket socket;
    URI uri;
    
    public boolean sendPatch() {
    	return sendPatch ("{\"Tag\": 112, \"Action\": \"Change\", \"Resource\": \"File\", \"ResId\": \"5629a0c2111aeb63cf000002\", \"FileVersion\":"+System.currentTimeMillis()+", \"Changes\": \"@@ -40,16 +40,17 @@\\n almost i\\n+t\\n n shape\", \"UserId\": \"56297d8e111aeb5f53000001\", \"Token\": \"token-fahslaj\"}");
    }
    
    public boolean sendPatch(String patch) {
        String patchRequest = patch;
        try {
            socket.sendMessage(patchRequest);
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
        String loginRequest = "{\"Tag\":5, \"Resource\":\"User\", \"Action\":\"Login\", \"Email\":\""+email+"\", \"Password\":\""+password+"\"}";
        try {
            socket.sendMessage(loginRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean subscribe() {
        String subscribeRequest = "{\"Tag\":10, \"Resource\":\"User\", \"Action\":\"Subscribe\", \"Projects\":[\"5629a063111aeb63cf000001\"], \"UserId\":\"56297d8e111aeb5f53000001\", \"Token\": \"token-fahslaj\"}";
        try {
            socket.sendMessage(subscribeRequest);
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
