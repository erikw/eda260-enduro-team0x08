package test.acceptance;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.LapGenerator;
import generator.sorter.Sorter;
import generator.sorter.StartNumberSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.FinishParser;
import parser.PersonParser;
import parser.StartParser;

import common.Configuration;

import driver.AbstractDriver;
import driver.LapDriver;
import driver.factory.DriverFactory;
import driver.factory.LapFactory;

public class TestVarvloppFullt {

	LapGenerator generator;
	private Model<LapDriver> model;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		Configuration.nameFile = "Iterationer/varvlopptid-fullt/namnfil.txt";
		generator = new LapGenerator(new Sorter<LapDriver>());
		model = new Model<LapDriver>();
		DriverFactory<? extends AbstractDriver> factory = new LapFactory();
		try {
			StartParser startParser = new StartParser(
					"Iterationer/varvlopptid-fullt/starttider.txt", factory);
			FinishParser finishParser = new FinishParser(
					"Iterationer/varvlopptid-fullt/maltider.txt", factory);

			PersonParser personParser = new PersonParser(
					"Iterationer/varvlopptid-fullt/namnfil.txt", factory);
			startParser.parse(model);
			finishParser.parse(model);
			personParser.parse(model);

		} catch (IOException e) {
			error(e, "Could not instantiate or use parsers.");
		}
	}

	@Test
	public void testResults() {
		Configuration.printPlacment = false;
		Configuration.sortBy = new StartNumberSorter();
		generator.generateResult(model);
		Scanner scanResults;
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			String accepFileName = "Iterationer/varvlopptid-fullt/resultat.txt";
			BufferedReader scanExpected = new BufferedReader(new FileReader(
					new File(accepFileName)));

			while (scanResults.hasNextLine()) {
				assertEquals(scanExpected.readLine(), scanResults.nextLine());
			}

			scanExpected.close();
			scanResults.close();
		} catch (Exception e) {
			error(e, "Could not scan file: " + fileName);
		}
	}

	@Test
	public void testResultsSorted() {
		Configuration.printPlacment = true;
		Configuration.sortBy = null;
		generator.generateResult(model);
		generator.generateResult(model);
		Scanner scanResults;
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			String accepFileName = "Iterationer/varvlopptid-fullt/sortresultat.txt";
			BufferedReader scanExpected = new BufferedReader(new FileReader(
					new File(accepFileName)));

			while (scanResults.hasNextLine()) {
				assertEquals(scanExpected.readLine(), scanResults.nextLine());
			}

			scanExpected.close();
			scanResults.close();
		} catch (Exception e) {
			error(e, "Could not scan file" + fileName);
		}
	}

	@After
	public void tearDown() {
		new File("data/result.txt").delete();
	}
}