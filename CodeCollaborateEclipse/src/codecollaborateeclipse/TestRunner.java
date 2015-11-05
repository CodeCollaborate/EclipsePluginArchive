package codecollaborateeclipse;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import codecollaborateeclipse.connections.CCWebSocket;
import codecollaborateeclipse.connections.CCWebSocketConnector;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * Created by fahslaj on 10/22/2015.
 */
public class TestRunner {
    public static void main(String[] args) {
        CCWebSocketConnector connector = new CCWebSocketConnector();
        connector.connect();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.login();
        connector.subscribe();
        connector.sendPatch();
        connector.close();
    }

    public static void test() {
    	CCWebSocketConnector connector = new CCWebSocketConnector();
        WebSocketClient client = new WebSocketClient();
        CCWebSocket socket = new CCWebSocket(connector);
        boolean closeStatus = false;
        try {
            client.start();
            URI uri = new URI("ws://codecollaborate.csse.rose-hulman.edu/ws/");
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket, uri, request);
            Thread.sleep(1000);
            socket.sendMessage("{\"Resource\":\"User\", \"Action\":\"Login\", \"Email\":\"fahslaj@rose-hulman.edu\", \"Password\":\"abcd1234\"}");

            System.out.printf("CC: Connecting to : $s$n \n", uri);
            closeStatus = socket.awaitClose(5, TimeUnit.SECONDS);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
