package codecollaborateeclipse;

import java.util.ArrayList;

public class Storage {
	private static ArrayList<String> users;
	private static ArrayList<String> projects;
	private static String username;
	private static String password;
	private static String connectionStatus;
	private static String token;
	
	public static ArrayList<String> getUsers() {
		return users;
	}
	public static void setUsers(ArrayList<String> users) {
		Storage.users = users;
	}
	public static ArrayList<String> getProjects() {
		return projects;
	}
	public static void setProjects(ArrayList<String> projects) {
		Storage.projects = projects;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Storage.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		Storage.password = password;
	}
	public static String getConnectionStatus() {
		return connectionStatus;
	}
	public static void setConnectionStatus(String connectionStatus) {
		Storage.connectionStatus = connectionStatus;
	}
	public static String getToken() {
		return token;
	}
	public static void setToken(String token) {
		Storage.token = token;
	}
	
}
