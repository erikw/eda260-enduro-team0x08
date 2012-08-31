package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.StageGenerator;
import generator.sorter.Sorter;
import generator.sorter.StartNumberSorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.PersonParser;

import common.Configuration;
import common.EnduroTime;

import driver.StageDriver;
import driver.factory.StageFactory;

public class TestStageGenerator {
	private StageGenerator generator;
	private File startFile;
	private File finishFile;
	private File resultFile;
	// private Scanner currentScanner;
	private Scanner startScanner;
	private Scanner finishScanner;
	private Scanner resultScanner;

	private Model<StageDriver> model;
	private File f;
	private File f2;
	private File f3;
	private File f4;
	StageDriver driver1;
	StageDriver driver2;
	StageDriver driver3;
	PrintWriter pw;
	PrintWriter fw;
	Scanner scanner;

	@Before
	public void setUp() {
		Configuration.debug = 1;
		Configuration.factory = new StageFactory();
		driver1 = new StageDriver(1);
		driver1.setStartTime(new EnduroTime("12.02.00"));
		driver1.setFinishTime(new EnduroTime("12.05.00"));

		driver2 = new StageDriver(2);
		driver2.setStartTime(new EnduroTime("12.01.00"));
		driver2.setFinishTime(new EnduroTime("12.10.00"));

		model = new Model<StageDriver>();
		model.put(1, driver1);
		model.put(2, driver2);

		try {
			PersonParser pp = new PersonParser("persons.txt",
					new StageFactory());
			pp.parse(model);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Configuration.nameFile = "persons.txt";
		generator = new StageGenerator(new Sorter<StageDriver>());
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
		f = new File("data/testStart.txt");
		f2 = new File("data/testFinish.txt");
		f3 = new File("data/testFullStart.txt");
		f4 = new File("data/testFullFinish.txt");

	}

	@Test
	public void testEmpty() {
		Model<StageDriver> emptyModel = new Model<StageDriver>(
				new HashMap<Integer, StageDriver>());

		generator.generateAll(emptyModel);
		String tmp = "";
		for (int i = 0; i < Configuration.maxStages; i++) {
			tmp += "; ";
		}

		assertEquals("Place; StartNr; Namn; Totaltid; #Etapper" + tmp,
				resultScanner.nextLine());
		assertEquals(false, startScanner.hasNext());

	}

	@Test
	public void testGeneratorStart() {
		generator.generateStart(model);

		assertEquals("1; 12.02.00", startScanner.nextLine());
		assertEquals("2; 12.01.00", startScanner.nextLine());
	}

	@Test
	public void testGeneratorFinish() {
		generator.generateFinish(model);

		assertEquals("1; 12.05.00", finishScanner.nextLine());
		assertEquals("2; 12.10.00", finishScanner.nextLine());
	}

	@Test
	// (expected=NullPointerException.class)
	public void testNoNameFile() {
//		StageGenerator generator2 = new StageGenerator("monkeylost.txt",
//				new Sorter<StageDriver>());
		generator.generateResult(model);
	}

	@Test
	public void testGenerateResult() {
		Configuration.printPlacment = false;
		Configuration.sortBy = new StartNumberSorter();
		generator.generateResult(model);
		String tmp = "";
		for (int i = 0; i < Configuration.maxStages - 1; i++) {
			tmp += "; ";
		}

		assertEquals("StartNr; Namn; Totaltid; #Etapper; Etapp1; " + tmp
				+ "Start1; Mål1" + tmp + tmp, resultScanner.nextLine());
		assertEquals("1; Anders Asson; 00.03.00; 1; 00.03.00; " + tmp
				+ "12.02.00; 12.05.00" + tmp + tmp, resultScanner.nextLine());
		assertEquals("2; Berit Bsson; 00.09.00; 1; 00.09.00; " + tmp
				+ "12.01.00; 12.10.00" + tmp + tmp, resultScanner.nextLine());
		assertEquals(false, resultScanner.hasNext());
	}

	@Test
	public void testGenerateResultWithManyStages() {
		Configuration.printPlacment = true;
		driver1.setStartTime(new EnduroTime("12.10.00"));
		driver1.setFinishTime(new EnduroTime("12.50.00"));

		driver2.setStartTime(new EnduroTime("12.00.00"));
		driver2.setFinishTime(new EnduroTime("12.40.00"));
		driver2.setStartTime(new EnduroTime("12.00.00"));
		driver2.setFinishTime(new EnduroTime("12.50.00"));
		Configuration.maxStages = 4;
		Configuration.printPlacment = false;
		generator.generateResult(model);
		if (Configuration.maxStages > 3) {

			String tmp = "";
			for (int i = 0; i < (Configuration.maxStages - 3); i++) {
				tmp += "; ";
			}

			assertEquals(
					"StartNr; Namn; Totaltid; #Etapper; Etapp1; Etapp2; Etapp3; "
							+ tmp + "Start1; Mål1; Start2; Mål2; Start3; Mål3"
							+ tmp + tmp, resultScanner.nextLine());
			assertEquals("1; Anders Asson; 00.43.00; 2; 00.03.00; 00.40.00; ; "
					+ tmp + "12.02.00; 12.05.00; 12.10.00; 12.50.00; ; " + tmp
					+ tmp, resultScanner.nextLine());
			assertEquals(
					"2; Berit Bsson; 01.39.00; 3; 00.10.00; 00.40.00; 00.49.00; "
							+ tmp
							+ "12.00.00; 12.10.00; 12.00.00; 12.40.00; 12.01.00; 12.50.00"
							+ tmp + tmp, resultScanner.nextLine());
			assertEquals(false, resultScanner.hasNext());
		}
	}

	@After
	public void tearDown() {
		driver3 = null;
		model = null;
		startScanner.close();
		finishScanner.close();
		resultScanner.close();
		scanner = null;
		startFile.delete();
		finishFile.delete();
		resultFile.delete();
		new File("monkeylost.txt").delete();
		new File("data/result.txt").delete();
		f.delete();
		f2.delete();
		f3.delete();
		f4.delete();
		pw = null;
		fw = null;
	}
}
