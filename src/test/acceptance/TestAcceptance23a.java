package test.acceptance;

import static common.ErrorMessage.error;
import static org.junit.Assert.*;
import generator.StageGenerator;
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

import common.Configuration;

import parser.FinishParser;
import parser.PersonParser;
import parser.StartParser;
import driver.AbstractDriver;
import driver.StageDriver;
import driver.factory.DriverFactory;
import driver.factory.StageFactory;

public class TestAcceptance23a {

	StageGenerator generator;
	private Model<StageDriver> model;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		Configuration.nameFile = "Iterationer/Iteration3/acceptanstest23a/namnfil.txt";
		generator = new StageGenerator(new Sorter<StageDriver>());
		model = new Model<StageDriver>();
		DriverFactory<? extends AbstractDriver> factory = new StageFactory();
		try {

			StartParser startParser = new StartParser(
					"Iterationer/Iteration3/acceptanstest23a/starttider1.txt",
					factory);
			StartParser startParser2 = new StartParser(
					"Iterationer/Iteration3/acceptanstest23a/starttider2.txt",
					factory);
			FinishParser finishParser = new FinishParser(
					"Iterationer/Iteration3/acceptanstest23a/maltider1.txt",
					factory);
			FinishParser finishParser2 = new FinishParser(
					"Iterationer/Iteration3/acceptanstest23a/maltider2.txt",
					factory);

			PersonParser personParser = new PersonParser(
					"Iterationer/Iteration3/acceptanstest23a/namnfil.txt",
					factory);
			startParser.parse(model);
			startParser2.parse(model);
			finishParser.parse(model);
			finishParser2.parse(model);
			personParser.parse(model);

			
			

		} catch (IOException e) {
			error(e, "Could not instantiate or use parsers.");
		}
	}

	@Test
	public void testResults() {
		Configuration.maxStages = 2;
		Configuration.printPlacment = false;
		Configuration.sortBy = new StartNumberSorter();
		generator.generateResult(model);
		Scanner scanResults;
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			String accepFileName = "Iterationer/Iteration3/acceptanstest23a/resultat.txt";
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

	@Test
	public void testResultsSorted() {
		Configuration.maxStages = 2;
		Configuration.printPlacment = true;
		Configuration.sortBy = null;
		generator.generateResult(model);
		Scanner scanResults;
		generator.generateResult(model);
		String fileName = "data/result.txt";
		try {
			scanResults = new Scanner(new File(fileName));
			String accepFileName = "Iterationer/Iteration3/acceptanstest23a/sortresultat.txt";
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