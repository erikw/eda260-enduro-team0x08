package test.gui;

import gui.MainView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;

/**
 * This test is a mere demonstration that it is sincerely possible to make a
 * test which tests an exception-case.
 * 
 * @author Tobias Magnusson, den görkäcke Jackie McLean-diggaren.
 * 
 */

public class TestGUIWithNoFiles {
	private File newConfFile;
	private ArrayList<String> oldFile = new ArrayList<String>();
	private MainView main;

	@Before
	public void setUp() {
		Configuration.debug = 1;
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
			/*
			 * Writing this data to conffile raceType = e nameFile =
			 * nonExisting.txt
			 * 
			 * e-antalEtapper = 3
			 */
			fw.write("raceType = e\nnameFile = nonExisting.txt\n\ne-antalEtapper = 3\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGUIWithNoPersonFile() {
		main = new MainView();
	}
	@After
	public void restore() {
		try {
			newConfFile.delete();
			newConfFile.createNewFile();
			FileWriter fw = new FileWriter(newConfFile);
			/*
			 * Writing this data to conffile 
			 * raceType = e 
			 * nameFile =persons.txt
			 * 
			 * e-antalEtapper = 3
			 */
			for (int i = 0; i < oldFile.size(); i++) {
				fw.write(oldFile.get(i));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		main.dispose();
		main=null;
	}

}
