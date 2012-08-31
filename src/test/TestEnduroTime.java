package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import parser.FinishParser;
import parser.StartParser;

import common.Configuration;
import common.DebugException;
import common.EnduroTime;

import driver.MarathonDriver;
import driver.factory.DriverFactory;
import driver.factory.MarathonFactory;

public class TestEnduroTime {
	protected EnduroTime time;
	private Model<MarathonDriver> model;

	@Before
	public void setUp() throws Exception {
		time = new EnduroTime();
		model = new Model<MarathonDriver>();
		Configuration.debug = 1;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetTimeInt() {
		time.set(12, 10, 5);
		assertEquals(time.toString(), "12.10.05");
	}

	@Test
	public void testSetTimeSeconds() {
		time.set(3662);
		assertEquals(time.toString(), "01.01.02");
	}

	@Test
	public void testSetTimeString() {
		time.set("12.10.05");
		assertEquals(time.toString(), "12.10.05");
	}

	@Test
	public void testCompareSameTime() {
		EnduroTime compare1 = new EnduroTime("12.10.05");
		EnduroTime compare2 = new EnduroTime(12, 10, 5);

		assertEquals(compare1.compare(compare2).toString(), "00.00.00");
	}

	@Test
	public void testCompare() {
		EnduroTime compare1 = new EnduroTime("12.10.05");
		EnduroTime compare2 = new EnduroTime(12, 10, 10);
		EnduroTime compare3 = new EnduroTime(13, 10, 10);

		assertEquals(compare1.compare(compare2).toString(), "00.00.05");
		assertEquals(compare1.compare(compare3).toString(), "01.00.05");
	}

	@Test
	public void testManyTimesDrivers() {
		MarathonDriver d1 = null;
		MarathonDriver d2 = null;
		MarathonDriver d3 = null;
		MarathonDriver d4 = null;
		MarathonDriver d5 = null;
		HashMap<Integer, MarathonDriver> map = null;
		DriverFactory<MarathonDriver> factory = new MarathonFactory();
		try {
			StartParser startParser = new StartParser(
					"Iterationer/Iteration1/acceptanstest3/starttider.txt",
					factory);
			FinishParser finishParser = new FinishParser(
					"Iterationer/Iteration1/acceptanstest3/maltider.txt",
					factory);
			startParser.parse(model);
			finishParser.parse(model);
			map = model.getMap();
		} catch (IOException e) {
			error(e,
					"Could not instantiate start or finish parser or parse with them.");

		}

		d1 = (MarathonDriver) map.get(1);
		d2 = (MarathonDriver) map.get(2);
		d3 = (MarathonDriver) map.get(3);
		d4 = (MarathonDriver) map.get(4);
		d5 = (MarathonDriver) map.get(5);
		assertEquals(new EnduroTime("12.00.00"), d1.getStartTimes().get(0));
		assertEquals(new EnduroTime("12.01.00"), d2.getStartTimes().get(0));
		assertEquals(new EnduroTime("12.02.00"), d3.getStartTimes().get(0));
		assertEquals(new EnduroTime("12.03.00"), d4.getStartTimes().get(0));
		assertEquals(new EnduroTime("12.04.00"), d5.getStartTimes().get(0));

		assertEquals(new EnduroTime("13.23.34"), d1.getFinishTimes().get(0));
		assertEquals(new EnduroTime("13.15.16"), d2.getFinishTimes().get(0));
		assertEquals(new EnduroTime("13.05.06"), d3.getFinishTimes().get(0));
		assertEquals(new EnduroTime("13.12.07"), d4.getFinishTimes().get(0));
		assertEquals(new EnduroTime("13.16.07"), d5.getFinishTimes().get(0));
	}

	@Test
	public void testAddTime() {
		EnduroTime t1 = new EnduroTime("01.00.01");
		t1.add(new EnduroTime("02.30.05"));
		assertEquals(new EnduroTime("03.30.06"), t1);
	}

	@Test
	public void testCompareTo() {
		assertTrue(new EnduroTime("02.30.05").compareTo(new EnduroTime(
				"03.30.06")) < 0);
	}

	@Test
	public void testNullTime() {
		EnduroTime et = new EnduroTime("--.--.--");
		assertEquals(new EnduroTime("00.00.00"), et);
	}

	@Test
	public void setMultipli() {
		EnduroTime t1 = new EnduroTime("01.00.00");
		assertEquals(new EnduroTime("03.00.00"), t1.mul(3));
	}

	@Test(expected = DebugException.class)
	public void compareBooleanWithEnduroTime() {
		EnduroTime t1 = new EnduroTime();
		t1.equals(Boolean.TRUE);
	}
}
