package net.jfabricationgames.bunkers_and_badasses.user;

import java.util.List;

public abstract class UserManager {
	
	private static List<User> users;
	
	private static String username;
	
	public static List<User> getUsers() {
		return users;
	}
	public static void setUsers(List<User> users) {
		UserManager.users = users;
	}
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		UserManager.username = username;
	}
}