package codecollaborateeclipse;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.StatusCode;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by fahslaj on 10/10/2015.
 */
@WebSocket(maxTextMessageSize = 64 * 1024)
public class CCWebSocket {
    private final CountDownLatch closeLatch;

    @SuppressWarnings("unused")
    private Session session;

    public CCWebSocket() {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
        return this.closeLatch.await(duration, unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.printf("CC: Connection closed: %d - %s%n \n", statusCode, reason);
        this.session = null;
        this.closeLatch.countDown();
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.printf("CC: Got connect: %s%n \n", session);
        this.session = session;
//        try {
//            Future<Void> fut;
//            fut = session.getRemote().sendStringByFuture("{\"Resource\":\"User\", \"Action\":\"Login\", \"Email\":\"fahslaj@rose-hulman.edu\", \"Password\":\"abcd1234\"}");
//            fut.get(2, TimeUnit.SECONDS);
//            session.close(StatusCode.NORMAL, "I'm done");
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
    }

    public boolean sendMessage(String msg) throws Exception {
        if (session == null)
            throw new Exception("Session is null");
        Future<Void> fut;
        fut = session.getRemote().sendStringByFuture(msg);
        boolean done = false;
        try {
            fut.get(2, TimeUnit.SECONDS);
            done = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return done;
    }

    public void closeSession() {
        session.close(StatusCode.NORMAL, "Done");
    }

    @OnWebSocketMessage
    public void onMessage(String msg) {
        System.out.println("Stuff has happened: "+msg);
    }

    public boolean sessionNull() {
        return session == null;
    }
}
