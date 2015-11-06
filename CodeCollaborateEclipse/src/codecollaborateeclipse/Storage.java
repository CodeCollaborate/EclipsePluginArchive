package codecollaborateeclipse;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import codecollaborateeclipse.events.DisplayListener;
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
	
	private DisplayList<String> users = new DisplayList<String>();
	private DisplayList<String> projectNames = new DisplayList<String>();
	private HashMap<String, Project> projects = new HashMap<String, Project>();
	private String username;
	private String password;
	private String connectionStatus = "Not connected";
	private String token;
	
	public Project[] getProjectsArray() {
		return projects.values().toArray(new Project[0]);
	}

	public HashMap<String, Project> getProjects() {
		return projects;
	}

	public void setProjects(HashMap<String, Project> projects) {
		firePropertyChange("projects", this.projects, this.projects = projects);
	}

	public DisplayList<String> getUsers() {
		return users;
	}

	public void setUsers(DisplayList<String> users) {
		firePropertyChange("users", this.users, this.users = users);
	}

	public DisplayList<String> getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(DisplayList<String> projects) {
		firePropertyChange("projectNames", this.projectNames, this.projectNames = projects);
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
	
	public static class DisplayList<E> extends ArrayList<E> {
		private ArrayList<DisplayListener> listeners = new ArrayList<DisplayListener>();
		
		public void addDisplayListener(DisplayListener listener) {
			listeners.add(listener);
		}
		
		public void removeDisplayListeners() {
			listeners = new ArrayList<DisplayListener>();
		}
		
		public void notifyListeners() {
			for (DisplayListener l : listeners) {
				l.onNotification(this);
			}
		}
		
		@Override
		public boolean add(E object) {
			boolean b = super.add(object);
		    notifyListeners();
		    return b;
		};

		@Override
		public void add(int index, E object) {
		    super.add(index, object);
		    notifyListeners();
		};
		
		@Override
		public boolean addAll(Collection<? extends E> c) {
			boolean b = super.addAll(c);
			notifyListeners();
			return b;
		}

		@Override
		public E remove(int index) {
		    E e = super.remove(index);
		    notifyListeners();
		    return e;
		}
	}
}
