package codecollaborateeclipse;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.document.DocumentManager;
import codecollaborateeclipse.preferences.PreferenceConstants;

public class Core {
	
	private static DocumentManager listener;
	private static CCWebSocketConnector connector;
	private static String username;
	private static String password;
	
	static {
		connector = new CCWebSocketConnector();
		listener = new DocumentManager(connector);
		connector.setEditorListener(listener);
		//ResourceManager.getFileMetadata();
	}
	
	public static void begin() {
		username = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USERNAME);
		password = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PASSWORD);
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
	
	public static void end() {
		connector.close();
		listener.close();
	}
	
	public static void setUsername(String e) {
		username = e;
	}
	
	public static void setPassword(String p) {
		password = p;
	}

	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
}
