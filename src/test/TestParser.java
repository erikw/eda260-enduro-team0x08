package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.AbstractParser;
import parser.FinishParser;
import parser.PersonParser;
import parser.StartParser;

import common.Configuration;
import common.EnduroTime;

import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

/**
 * 
 * @author Team08
 * 
 */

public class TestParser {
	private HashMap<Integer, MarathonDriver> map;
	private File f0;
	private File f1;
	private MarathonDriver d1;
	private MarathonDriver d2;
	private Model<MarathonDriver> model;
	private DriverFactory<MarathonDriver> factory;

	private AbstractParser sp, fp, pp;

	@Before
	public void setUp() {
		Configuration.debug = 1;
		try {
			try {
				f0 = new File("data/start.txt");
				f0.delete();
				f1 = new File("data/finish.txt");
				f1.delete();
				PrintWriter pw0 = new PrintWriter(new FileWriter(
						"data/start.txt"));
				pw0.println("1;01.00.00\n1;20.00.00\n1;30.00.00;10.00.00");
				pw0.close();
				PrintWriter pw1 = new PrintWriter(new FileWriter(
						"data/finish.txt"));
				pw1.println("1;01.00.10\n1;20.00.10\n1;30.01.00;11.00.00");
				pw1.close();
			} catch (IOException e) {
				error(e, "Could not create PrintWriters.");
			}
			factory = new MarathonFactory();
			sp = new StartParser("data/start.txt", factory);
			fp = new FinishParser("data/finish.txt", factory);
			pp = new PersonParser("persons.txt", factory);
			model = new Model<MarathonDriver>();
		} catch (FileNotFoundException e) {
			fail();
		}
	}

	@After
	public void tearDown() {
		f0.delete();
		f1.delete();
	}


	@Test
	public void testParse() {
		try {
			sp = new StartParser("data/start.txt", factory);
			fp = new FinishParser("data/finish.txt", factory);

		} catch (FileNotFoundException e) {
			assertTrue(false);
		}
		try {
			sp.parse(model);
			fp.parse(model);
			map = model.getMap();
			MarathonDriver d1 = map.get(1);
			assertTrue(d1.getStartTimes().get(0).equals(
					new EnduroTime("01.00.00")));
			assertTrue(d1.getFinishTimes().get(0).equals(
					new EnduroTime("01.00.10")));

		} catch (IOException e) {
			assertTrue(false);
		}

	}

	@Test
	public void testParsemultiTimes() {
		try {
			sp = new StartParser("data/start.txt", factory);
		} catch (FileNotFoundException e) {
			assertTrue(false);
		}
		try {
			sp.parse(model);
			map = model.getMap();

			MarathonDriver d1 = map.get(1);
			assertTrue(d1.getStartTimes().get(0).equals(
					new EnduroTime("01.00.00")));
			assertTrue(d1.getStartTimes().get(1).equals(
					new EnduroTime("20.00.00")));
			assertTrue(d1.getStartTimes().get(2).equals(
					new EnduroTime("30.00.00")));

		} catch (IOException e) {
			assertTrue(false);
		}
	}

	@Test
	public void testPersonParser() throws IOException {
		pp = new PersonParser("persons.txt", factory);
		pp.parse(model);
		map = model.getMap();
		d1 = map.get(1);
		d2 = map.get(2);
		assertEquals(d1.getInfo().get(0), "Anders Asson");
		assertEquals(d2.getInfo().get(0), "Berit Bsson");

	}

}
