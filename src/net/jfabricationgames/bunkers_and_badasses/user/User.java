package net.jfabricationgames.bunkers_and_badasses.user;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = -5406347585962423196L;
	
	private String username;
	private boolean online;
	private boolean inGame;
	
	public User(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return username;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return username.equals(((User) obj).getUsername());
		}
		else {
			return super.equals(obj);
		}
	}
	
	@Override
	public int hashCode() {
		//generate a hash code depending on the username
		int hash = 43;
		for (int i = 0; i < username.length(); i++) {
			hash *= 17;
			hash += username.charAt(i);
		}
		return hash;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public boolean isInGame() {
		return inGame;
	}
	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}
}