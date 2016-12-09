package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.List;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 4537459302664184784L;
	
	private List<Field> fields;
	private BufferedImage baseImage;
	private String name;

	public BufferedImage displayField() {
		//TODO
		return null;
	}
	
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public BufferedImage getBaseImage() {
		return baseImage;
	}
	public void setBaseImage(BufferedImage baseImage) {
		this.baseImage = baseImage;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}