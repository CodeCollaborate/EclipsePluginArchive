package codecollaborateeclipse;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import codecollaborateeclipse.listener.EditorListener;
import codecollaborateeclipse.preferences.PreferenceConstants;

public class CCCore {
	
	private static EditorListener listener;
	private static CCWebSocketConnector connector;
	private static String email;
	private static String password;
	
	static {
		connector = new CCWebSocketConnector();
		listener = new EditorListener(connector);
		connector.setEditorListener(listener);
		//ResourceManager.getFileMetadata();
	}
	
	public static void begin() {
		email = Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_EMAIL);
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
	
	public static void setEmail(String e) {
		email = e;
	}
	
	public static void setPassword(String p) {
		password = p;
	}

	public static String getEmail() {
		return email;
	}
	
	public static String getPassword() {
		return password;
	}
}
