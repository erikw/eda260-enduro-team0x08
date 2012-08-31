package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;

import parser.PersonParser;
import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

public class TestDriverClasses {

	private List<String> expectedList;
	private Model<MarathonDriver> model;
	private PersonParser pp;
	private DriverFactory<MarathonDriver> factory;

	@Before
	public void setUp() {
		expectedList = new ArrayList<String>();
		expectedList.add("JUNIOR");
		expectedList.add("SENIOR");
		String fileName = "Iterationer/Iteration2/acceptanstest13/namnfil.txt";
		model = new Model<MarathonDriver>();
		factory = new MarathonFactory();
		Configuration.debug = 1;

		try {
			pp = new PersonParser(fileName, factory);
			pp.parse(model);
		} catch (Exception e) {
			error(e, "Could not create a personparser from file" + fileName);
		}
	}

	@After
	public void tearDown() {
		expectedList = null;
	}

	@Test
	public void testClasses() {
		List<String> actualList = model.getClassNames();
		assertEquals(expectedList.size(), actualList.size());
		for (int i = 0; i < expectedList.size(); ++i) {
			assertEquals(expectedList.get(i), actualList.get(i));
		}

	}

	@Test
	public void testDriverClasses() {
		HashMap<Integer, MarathonDriver> expectedDrivers = new HashMap<Integer, MarathonDriver>();
		MarathonDriver d1 = new MarathonDriver(1);
		MarathonDriver d2 = new MarathonDriver(2);
		MarathonDriver d3 = new MarathonDriver(101);
		MarathonDriver d4 = new MarathonDriver(102);
		MarathonDriver d5 = new MarathonDriver(103);

		d1.setClassName("SENIOR");
		d2.setClassName("SENIOR");
		d3.setClassName("JUNIOR");
		d4.setClassName("JUNIOR");
		d5.setClassName("JUNIOR");

		expectedDrivers.put(1, d1);
		expectedDrivers.put(2, d2);
		expectedDrivers.put(101, d3);
		expectedDrivers.put(102, d4);
		expectedDrivers.put(103, d5);

		String personFileName = "Iterationer/Iteration2/acceptanstest13/namnfil.txt";
		PersonParser pp = null;
		try {
			pp = new PersonParser(personFileName, factory);
		} catch (FileNotFoundException e) {
			error(e, "Could not create a personparser from file"
					+ personFileName);
		}

		try {
			pp.parse(model);
		} catch (IOException e) {
			error(e, "Could not parse persons.");
		}

		assertEquals(expectedDrivers.size(), model.getMap().size());
		Iterator<MarathonDriver> eItr = expectedDrivers.values().iterator();
		Iterator<MarathonDriver> iItr = model.getMap().values().iterator();
		while (eItr.hasNext()) {
			assertEquals(eItr.next().getClassName(), iItr.next().getClassName());
		}
	}

}
