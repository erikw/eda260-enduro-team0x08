package test.acceptance;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.MarathonGenerator;
import generator.formatter.CSVFormatter;
import generator.sorter.Sorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

public class TestAcceptance06 {

	MarathonGenerator generator;
	private Model<MarathonDriver> model;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		Configuration.formatter = new CSVFormatter();
		Configuration.factory = new MarathonFactory();
		Configuration.nameFile = "Iterationer/Iteration1/acceptanstest6/namnfil.txt";

		generator = new MarathonGenerator(
				"Iterationer/Iteration1/acceptanstest6/namnfil.txt",
				new Sorter<MarathonDriver>());

		model = new Model<MarathonDriver>();
		DriverFactory<? extends AbstractDriver> factory = new MarathonFactory();
		try {

			StartParser startParser = new StartParser(
					"Iterationer/Iteration1/acceptanstest6/starttider.txt",
					factory);
			FinishParser finishParser = new FinishParser(
					"Iterationer/Iteration1/acceptanstest6/maltider.txt",
					factory);

			PersonParser personParser = new PersonParser(
					"Iterationer/Iteration1/acceptanstest6/namnfil.txt",
					factory);
			startParser.parse(model);
			finishParser.parse(model);
			personParser.parse(model);
			generator.generateResult(model);

		} catch (Exception e) {
			error(e, "Could not instantiate or use parsers.");
		}
	}

	@Test
	public void testResults() {
		// Vi har skrivit om resultatfilen men visste inte hur placeringen
		// skulle
		// ges till de utan start/måltid.... !!!!!!!!!!!!!!!!!!1

		Scanner scanResults;
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			BufferedReader scanExpected = new BufferedReader(
					new FileReader(
							new File(
									"Iterationer/Iteration1/acceptanstest6/resultat.txt")));

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
		new File("data/starttider.txt").delete();
		new File("data/maltider.txt").delete();
		new File("data/namnfil.txt").delete();
		new File("data/result.txt").delete();

	}
}