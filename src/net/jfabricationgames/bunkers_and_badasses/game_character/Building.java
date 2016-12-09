package net.jfabricationgames.bunkers_and_badasses.game_character;

import java.io.Serializable;

public abstract class Building implements Serializable {
	
	private static final long serialVersionUID = -7099388827063901279L;
	
	protected int recruitableTroops;
	protected int ammoMining;
	protected int creditMining;
	protected int eridiumMining;
	protected int landMineVictims;
	protected int additionalDefence;
	protected int moveDistance;
	
	protected boolean attackable;
	protected boolean badassTroopsRecruitable;
	
	protected String name;
	
	public Building() {
		recruitableTroops = 1;
		ammoMining = 0;
		creditMining = 0;
		eridiumMining = 0;
		landMineVictims = 0;
		additionalDefence = 0;
		moveDistance = 1;
		attackable = true;
		badassTroopsRecruitable = false;
	}
	
	public String getName() {
		return name;
	}
}