package test.acceptance;

import static common.ErrorMessage.error;
import static org.junit.Assert.*;
import generator.StageGenerator;
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
import driver.StageDriver;
import driver.factory.DriverFactory;
import driver.factory.StageFactory;

public class TestAcceptance19b {

	StageGenerator generator;
	private Model<StageDriver> model;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		Configuration.nameFile = "Iterationer/Iteration3/acceptanstest19b/namnfil.txt";

		generator = new StageGenerator(new Sorter<StageDriver>());
		model = new Model<StageDriver>();
		DriverFactory<? extends AbstractDriver> factory = new StageFactory();
		try {

			StartParser startParser = new StartParser(
					"Iterationer/Iteration3/acceptanstest19b/starttider1.txt",
					factory);
			StartParser startParser2 = new StartParser(
					"Iterationer/Iteration3/acceptanstest19b/starttider2.txt",
					factory);
			FinishParser finishParser = new FinishParser(
					"Iterationer/Iteration3/acceptanstest19b/maltider1.txt",
					factory);
			FinishParser finishParser2 = new FinishParser(
					"Iterationer/Iteration3/acceptanstest19b/maltider2.txt",
					factory);

			PersonParser personParser = new PersonParser(
					"Iterationer/Iteration3/acceptanstest19b/namnfil.txt",
					factory);
			startParser.parse(model);
			startParser2.parse(model);
			finishParser.parse(model);
			finishParser2.parse(model);
			personParser.parse(model);

			Configuration.maxStages = 2;
			Configuration.printPlacment = false;
			generator.generateResult(model);
			
		} catch (IOException e) {
			error(e, "Could not instantiate or use parsers.");
		}
	}

	// funkar bara för 2 etapplopp
	@Test
	public void testResults() {
		int def = Configuration.maxStages;
		Configuration.maxStages = 2;
		if (Configuration.maxStages == 2) {
			Scanner scanResults;
			String fileName = "data/result.txt";
			try {
				scanResults = new Scanner(new File(fileName));
				String accepFileName = "Iterationer/Iteration3/acceptanstest19b/resultat.txt";
				BufferedReader scanExpected = new BufferedReader(
						new FileReader(new File(accepFileName)));

				while (scanResults.hasNextLine()) {
					assertEquals(scanExpected.readLine(),
							scanResults.nextLine());
				}

				scanExpected.close();
				scanResults.close();
			} catch (Exception e) {
				error(e, "Could not scan file" + fileName);
			}
		}
		Configuration.maxStages = def;
	}

	@After
	public void tearDown() {
		new File("data/result.txt").delete();
	}
}