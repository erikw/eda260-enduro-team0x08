package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.MarathonGenerator;
import generator.sorter.Sorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.AbstractParser;
import parser.StartParser;

import common.Configuration;
import common.EditConfigForTest;
import common.EnduroTime;

import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

/**
 * 
 * @author Team08
 * 
 */

public class TestMarathonGenerator {
	private MarathonGenerator generator;
	private File startFile;
	private File finishFile;
	private File resultFile;
	// private Scanner currentScanner;
	private Scanner startScanner;
	private Scanner finishScanner;
	private Scanner resultScanner;
	private Model<MarathonDriver> model;
	private AbstractParser pp1, pp2, pp3, pp4;

	// private HashMap<Integer, MarathonDriver> drivers;
	private File f1;
	private File f2;
	private File f3;
	private File f4;
	private MarathonDriver driver1;
	private MarathonDriver driver2;
	private MarathonDriver driver3;
	// private PrintWriter pw;
	// private PrintWriter fw;
	@SuppressWarnings("unused")
	private HashMap<Integer, MarathonDriver> emptydrivers;
	// private Scanner scanner;
	private Sorter<MarathonDriver> sorter;
	private DriverFactory<MarathonDriver> factory;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		EditConfigForTest.edit("raceType = m\nnameFile=persons.txt");
		Configuration.printPlacment = true;
		Configuration.debug = 1;
		model = (Model<MarathonDriver>) Configuration.model;
		sorter = (Sorter<MarathonDriver>) Configuration.sorter;
		MarathonFactory mf = (MarathonFactory) Configuration.factory;
		driver1 = mf.make(1);
		driver1.setStartTime(new EnduroTime("12.00.00"));
		driver1.setFinishTime(new EnduroTime("12.02.00"));
		driver2 = mf.make(2);
		driver2.setStartTime(new EnduroTime("12.01.00"));
		driver2.setFinishTime(new EnduroTime("12.05.00"));

		model.put(1, driver1);
		model.put(2, driver2);
		generator = new MarathonGenerator("persons.txt", sorter);
		factory = new MarathonFactory();
		try {
			startFile = new File(Configuration.startFile);
			startFile.createNewFile();
			finishFile = new File(Configuration.finishFile);
			finishFile.createNewFile();
			resultFile = new File("data/result.txt");
			resultFile.createNewFile();
		} catch (IOException e) {
			error(e, "Could not create new files");
		}

		try {
			startScanner = new Scanner(startFile);
			finishScanner = new Scanner(finishFile);
			resultScanner = new Scanner(resultFile);
		} catch (FileNotFoundException e) {
			error(e, "Could not create new Scanners.");
		}
		f1 = new File("data/testStart.txt");
		f2 = new File("data/testFinish.txt");
		f3 = new File("data/testFullStart.txt");
		f4 = new File("data/testFullFinish.txt");
		try {
			f1.createNewFile();
			f2.createNewFile();
			f3.createNewFile();
			f4.createNewFile();
		} catch (IOException e) {
			error(e, "Could not create a new file " + f3.getName());
		}
		try {
			pp1 = new StartParser("data/testStart.txt", factory);
			pp2 = new StartParser("data/testFinish.txt", factory);
			pp3 = new StartParser("data/testFullStart.txt", factory);
			pp4 = new StartParser("data/testFullFinish.txt", factory);

		} catch (FileNotFoundException e) {
			error(e, "Could not create new parsers");
		}
		try {
			pp1.parse(model);
			pp2.parse(model);
			pp3.parse(model);
			pp4.parse(model);
		} catch (IOException e) {
			error(e, "Could not parse.");
		}

	}

	private void EditConfigForTest(String string) {
		// TODO Auto-generated method stub
		
	}

	@After
	public void tearDown() {
		pp1 = null;
		pp2 = null;
		pp3 = null;
		pp4 = null;
		generator = null;
		driver1 = null;
		driver2 = null;
		driver3 = null;
		emptydrivers = null;
		startScanner.close();
		finishScanner.close();
		resultScanner.close();
		startFile.delete();
		finishFile.delete();
		resultFile.delete();
		new File("data/result.txt").delete();
		f1.delete();
		f2.delete();
		f3.delete();
		f4.delete();
	}

	/**
	 * No story. Iteration 0
	 */

	@Test
	public void testEmpty() {
		emptydrivers = new HashMap<Integer, MarathonDriver>();
		generator.generateAll(new Model<MarathonDriver>());

		assertEquals("Place; StartNr; Namn; Totaltid; Starttid; Måltid",
				resultScanner.nextLine());
		assertEquals(false, startScanner.hasNext());
		assertEquals(false, finishScanner.hasNext());
	}

	@Test
	public void testGenerateResult() {
		driver3 = new MarathonDriver(3);
		driver3.setStartTime(new EnduroTime("12.00.11"));
		// driver3.setStartTime(new EnduroTime("15.10.15"));
		driver3.setFinishTime(new EnduroTime("12.40.55"));

		model.put(3, driver3);

		generator.generateResult(model);

		assertEquals("Place; StartNr; Namn; Totaltid; Starttid; Måltid",
				resultScanner.nextLine());
		assertEquals("1; 1; Anders Asson; 00.02.00; 12.00.00; 12.02.00; Omöjlig Totaltid?",
				resultScanner.nextLine());
		assertEquals("2; 2; Berit Bsson; 00.04.00; 12.01.00; 12.05.00; Omöjlig Totaltid?",
				resultScanner.nextLine());
		assertEquals("3; 3; ; 00.40.44; 12.00.11; 12.40.55", resultScanner
				.nextLine());
	}

	@Test
	public void testGeneratorStart() {
		generator.generateStart(model);
		assertEquals("1; 12.00.00", startScanner.nextLine());
		assertEquals("2; 12.01.00", startScanner.nextLine());
	}

	@Test
	public void testGeneratorFinish() {
		generator.generateFinish(model);
		assertEquals("1; 12.02.00", finishScanner.nextLine());
		assertEquals("2; 12.05.00", finishScanner.nextLine());
	}
	@After
	public void brytIhop(){
		EditConfigForTest.restore();
	}

}
