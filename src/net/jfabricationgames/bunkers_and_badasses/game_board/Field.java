package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import net.jfabricationgames.bunkers_and_badasses.game_character.Building;
import net.jfabricationgames.bunkers_and_badasses.game_character.Troop;
import net.jfabricationgames.bunkers_and_badasses.user.User;

public class Field {
	
	private List<Field> neighbours;
	private User affiliation;
	private List<Troop> troops;
	private Building building;
	private String name;
	
	private Point fieldPosition;
	private Point normalTroupsPosition;
	private Point badassTroupsPosition;
	private Point buildingPosition;
	
	private Color fieldColor;
	
	@Override
	public String toString() {
		return name;
	}
	
	public List<Field> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(List<Field> neighbours) {
		this.neighbours = neighbours;
	}
	
	public User getAffiliation() {
		return affiliation;
	}
	public void setAffiliation(User affiliation) {
		this.affiliation = affiliation;
	}
	
	public List<Troop> getTroops() {
		return troops;
	}
	public void setTroops(List<Troop> troops) {
		this.troops = troops;
	}
	
	public Building getBuilding() {
		return building;
	}
	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public Point getFieldPosition() {
		return fieldPosition;
	}
	public void setFieldPosition(Point fieldPosition) {
		this.fieldPosition = fieldPosition;
	}
	
	public Point getNormalTroupsPosition() {
		return normalTroupsPosition;
	}
	public void setNormalTroupsPosition(Point normalTroupsPosition) {
		this.normalTroupsPosition = normalTroupsPosition;
	}
	
	public Point getBadassTroupsPosition() {
		return badassTroupsPosition;
	}
	public void setBadassTroupsPosition(Point badassTroupsPosition) {
		this.badassTroupsPosition = badassTroupsPosition;
	}
	
	public Point getBuildingPosition() {
		return buildingPosition;
	}
	public void setBuildingPosition(Point buildingPosition) {
		this.buildingPosition = buildingPosition;
	}
	
	public Color getFieldColor() {
		return fieldColor;
	}
	public void setFieldColor(Color fieldColor) {
		this.fieldColor = fieldColor;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}