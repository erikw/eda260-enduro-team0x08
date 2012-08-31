package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that edits the config-file. Mainly for test purposes.
 * @author Jonas Klauber, Tobias Magnusson
 *
 */
public class EditConfigForTest {
	private static File newConfFile;
	private static ArrayList<String> oldFile = new ArrayList<String>();

	/**
	 * Write a string to enudro.config
	 * @param the string to be written
	 */
	public static void edit(String s) {
		oldFile = new ArrayList<String>();
		newConfFile = new File("enduro.config");
		try {
			Scanner scan = new Scanner(newConfFile);
			while (scan.hasNextLine()) {
				oldFile.add(scan.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			newConfFile.delete();
			newConfFile.createNewFile();
			FileWriter fw = new FileWriter(newConfFile);
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration.update();
	}
	
	/**
	 * Restore the config to the one before call to edit method.
	 */
	public static void restore(){
		try {
			newConfFile.delete();
			newConfFile.createNewFile();
			FileWriter fw = new FileWriter(newConfFile);
			for (int i = 0; i < oldFile.size(); i++) {
				fw.write(oldFile.get(i));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Configuration.update();
	}

}
