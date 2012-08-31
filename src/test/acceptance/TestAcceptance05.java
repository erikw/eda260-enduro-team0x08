package test.acceptance;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.MarathonGenerator;
import generator.formatter.CSVFormatter;
import generator.sorter.Sorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;

import parser.FinishParser;
import parser.PersonParser;
import parser.StartParser;
import driver.AbstractDriver;
import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

public class TestAcceptance05 {

	private MarathonGenerator generator;
	private StartParser startParser;
	private PersonParser personParser;
	private FinishParser finishParser;
	private Model<MarathonDriver> model;
	private Sorter<MarathonDriver> sorter;
	private DriverFactory<? extends AbstractDriver> factory;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		Configuration.formatter = new CSVFormatter();
		Configuration.factory = factory;
		Configuration.nameFile = "Iterationer/Iteration1/acceptanstest5/namnfil.txt";
		
		model = new Model<MarathonDriver>();
		sorter = new Sorter<MarathonDriver>();
		factory = new MarathonFactory();
		generator = new MarathonGenerator(
				"Iterationer/Iteration1/acceptanstest5/namnfil.txt", sorter);
		

		try {
			startParser = new StartParser(
					"Iterationer/Iteration1/acceptanstest5/starttider.txt",
					factory);
			finishParser = new FinishParser(
					"Iterationer/Iteration1/acceptanstest5/maltider.txt",
					factory);
			personParser = new PersonParser(
					"Iterationer/Iteration1/acceptanstest5/namnfil.txt",
					factory);
			startParser.parse(model);
			finishParser.parse(model);
			personParser.parse(model);

			Configuration.printPlacment = true;
			Configuration.sortBy = null;
			
			generator.generateResult(model);

		} catch (IOException e) {
			error(e, "Could not instantiate or use parsers.");
		}
	}

	@After
	public void tearDown() {
		new File("data/starttider.txt").delete();
		new File("data/maltider.txt").delete();
		new File("data/namnfil.txt").delete();
		new File("data/resultat.txt").delete();

	}

	@Test
	public void testResults() {

		Scanner scanResults;
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			BufferedReader scanExpected = new BufferedReader(
					new FileReader(
							new File(
									"Iterationer/Iteration1/acceptanstest5/resultat.txt")));
			while (scanResults.hasNextLine()) {
				assertEquals(scanExpected.readLine(), scanResults.nextLine());
			}
			scanExpected.close();
			scanResults.close();
		} catch (IOException e) {
			error(e, "Could not scan file" + fileName);
		}
	}

}