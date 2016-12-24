package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import javax.swing.ImageIcon;

public class Board implements Serializable {
	
	private static final long serialVersionUID = 4537459302664184784L;
	
	private List<Field> fields;
	private List<Region> regions;
	private transient BufferedImage baseImage;
	private String name;
	
	private ImageIcon imageWrapper;

	public BufferedImage displayField() {
		//TODO
		return null;
	}
	
	/**
	 * Write the object to a serialized file. The not-serializabel parts (the buffered image) is stored separately at the end of the file.
	 * This method is somehow used by the Serializable interface.
	 * 
	 * WARNING: Do not change this method. Other signature would cause Serializable to not use this method.
	 * 
	 * @param out
	 * 		The ObjectOutputStream that is used to write down the file.
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		imageWrapper = new ImageIcon(baseImage);
		out.defaultWriteObject();//serialize the object but the transient fields (the baseImage)
		//out.writeInt(1);//write the number of images down (needed also if there is only one)
		//ImageIO.write(baseImage, "png", out);//write the image at the end of the file
	}
	/**
	 * Read the object from a serialized file. The not-serializabel parts (the buffered image) is stored separately at the end of the file.
	 * This method is somehow used by the Serializable interface.
	 * 
	 * WARNING: Do not change this method. Other signature would cause Serializable to not use this method.
	 * 
	 * @param in
	 * 		The ObjectInputStream that is used to read the file.
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();//load all the fields that could be serialized (not the transient fields like baseImage)
		baseImage = loadBaseImage(imageWrapper);
		imageWrapper = null;
		//in.readInt();//read the number of stored images (its only one so no need to store the value)
		//baseImage = ImageIO.read(in);//read the image file
	}
	
	/**
	 * Create a BufferedImage from an ImageIcon by drawing it.
	 *  
	 * @param imageIcon
	 * 		The ImageIcon.
	 * 
	 * @return
	 * 		The BufferedImage.
	 */
	private BufferedImage loadBaseImage(ImageIcon imageIcon) {
		BufferedImage img = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		imageIcon.paintIcon(null, g, 0, 0);
		g.dispose();
		return img;
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
	
	public List<Region> getRegions() {
		return regions;
	}
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
}