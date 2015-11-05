package codecollaborateeclipse;

import org.eclipse.jface.util.PropertyChangeEvent;

import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.document.DocumentManager;
import codecollaborateeclipse.preferences.PreferenceConstants;

public class Core {
	
	private DocumentManager listener;
	private CCWebSocketConnector connector;
	private static Core core = new Core();
	
	public Core() {
		connector = new CCWebSocketConnector();
		listener = new DocumentManager(connector);
		connector.setEditorListener(listener);
		//ResourceManager.getFileMetadata();
	}
	
	public void begin() {
		Storage.setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME));
		Storage.setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD));
		connector.connect();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.login();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.subscribe();
        listener.listen();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.pullDocument();
	}
	
	public void end() {
		connector.close();
		listener.close();
	}
	
	public static Core getInstance() {
		return core;
	}
}
