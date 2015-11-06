package codecollaborateeclipse;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;

import codecollaborateeclipse.models.Response.File;
import codecollaborateeclipse.models.Response.Project;

public class Storage {
	private static Storage storage = new Storage();
	
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
//	private HashMap<String, Object> resources = new HashMap<String, Object>();
//	
//	public static void set(String key, Object value) {
//		Object old = storage.resources.get(key);
//		storage.firePropertyChange(key, old, storage.resources.put(key, value));
//	}
//
//	public static Object get(String key) {
//		return storage.resources.get(key);
//	}
	
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> projectNames = new ArrayList<String>();
	private HashMap<String, Project> projects = new HashMap<String, Project>();
	private String username;
	private String password;
	private String connectionStatus = "Not connected";
	private String token;

	public HashMap<String, Project> getProjects() {
		return projects;
	}

	public void setProjects(HashMap<String, Project> projects) {
		firePropertyChange("projects", this.projects, this.projects = projects);
	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		firePropertyChange("users", this.users, this.users = users);
	}

	public ArrayList<String> getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(ArrayList<String> projects) {
		firePropertyChange("projects", this.projectNames, this.projectNames = projects);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		firePropertyChange("username", this.username, this.username = username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		firePropertyChange("password", this.password, this.password = password);
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		firePropertyChange("connectionStatus", this.connectionStatus, this.connectionStatus = connectionStatus);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		firePropertyChange("token", this.token, this.token = token);
	}

	public PropertyChangeSupport getChangeSupport() {
		return changeSupport;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
	
	public static Storage getInstance() {
		return storage;
	}
}

//	private static ArrayList<String> users;
//	private static ArrayList<String> projects;
//	private static String username;
//	private static String password;
//	private static String connectionStatus;
//	private static String token;
//	
//	public static ArrayList<String> getUsers() {
//		return users;
//	}
//	public static void setUsers(ArrayList<String> users) {
//		Storage.users = users;
//	}
//	public static ArrayList<String> getProjects() {
//		return projects;
//	}
//	public static void setProjects(ArrayList<String> projects) {
//		Storage.projects = projects;
//	}
//	public static String getUsername() {
//		return username;
//	}
//	public static void setUsername(String username) {
//		Storage.username = username;
//	}
//	public static String getPassword() {
//		return password;
//	}
//	public static void setPassword(String password) {
//		Storage.password = password;
//	}
//	public static String getConnectionStatus() {
//		return connectionStatus;
//	}
//	public static void setConnectionStatus(String connectionStatus) {
//		Storage.connectionStatus = connectionStatus;
//	}
//	public static String getToken() {
//		return token;
//	}
//	public static void setToken(String token) {
//		Storage.token = token;
//	}
