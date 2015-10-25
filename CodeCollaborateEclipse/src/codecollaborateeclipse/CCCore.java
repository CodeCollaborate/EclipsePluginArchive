package codecollaborateeclipse;

import codecollaborateeclipse.listener.EditorListener;

public class CCCore {
	
	private EditorListener listener;
	private CCWebSocketConnector connector;
	
	
	public CCCore() {
		this.connector = new CCWebSocketConnector();
		this.listener = new EditorListener(this.connector);
	}
	
	public void begin() {
        this.connector.connect();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.connector.login();
        this.connector.subscribe();
        this.connector.socket.setEditorListener(this.listener);
        this.listener.listen();
		
	}
	
//	public void startEditorListener() {
//		listene
//		
//		
//	}
//	
//	public void startWebSocket() {
//		
//		
//		
//	}
	
}
