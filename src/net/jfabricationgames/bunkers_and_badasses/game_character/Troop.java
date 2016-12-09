package net.jfabricationgames.bunkers_and_badasses.game_character;

import java.io.Serializable;

public abstract class Troop implements Serializable{
	
	private static final long serialVersionUID = 6212351588292032077L;
	
	public static final int PLAYER_TROOP = 1;
	public static final int NEUTRAL_TROOP = 2;
	
	protected int strength;
	protected int type;
	
	public int getStrength() {
		return strength;
	}
	
	public int getType() {
		return type;
	}
}