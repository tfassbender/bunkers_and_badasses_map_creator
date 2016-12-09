package net.jfabricationgames.bunkers_and_badasses.game_board;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BoardLoader {
	
	public Board loadBoard(File file) {
		//TODO
		return null;
	}
	
	public void storeBoard(Board board, File file) {
		try (FileOutputStream fileStream = new FileOutputStream(file);
				BufferedOutputStream bufferedStream = new BufferedOutputStream(fileStream);
				ObjectOutputStream objectStream = new ObjectOutputStream(bufferedStream);) {
			objectStream.writeObject(board);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}