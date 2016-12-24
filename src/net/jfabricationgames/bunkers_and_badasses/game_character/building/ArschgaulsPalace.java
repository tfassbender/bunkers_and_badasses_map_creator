package net.jfabricationgames.bunkers_and_badasses.game_character.building;

public class ArschgaulsPalace extends Building {
	
	static {
		staticImage = imageLoader.loadImage("arschgaul_1_small.png");
	}
	
	public ArschgaulsPalace() {
		super();
		recruitableTroops = 3;
		//TODO change to real values when known
		ammoMining = 1;
		creditMining = 1;
		eridiumMining = 1;
		points = 1;
		//Unsure if attackable
	}
}