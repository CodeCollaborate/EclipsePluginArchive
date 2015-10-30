package codecollaborateeclipse;

import codecollaborateeclipse.listener.EditorListener;

public class CCCore {
	
	private EditorListener listener;
	private CCWebSocketConnector connector;
	
	
	public CCCore() {
		this.connector = new CCWebSocketConnector();
		this.listener = new EditorListener(this.connector);
		this.connector.setEditorListener(this.listener);
		//ResourceManager.getFileMetadata();
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
        this.listener.listen();
		
	}
	
	public void end() {
		this.connector.close();
	}
}
