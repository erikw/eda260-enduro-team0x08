package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.MarathonDriver;

public class TestMarathonDriver {
	private MarathonDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new MarathonDriver(1);
		Configuration.debug = 1;
	}

	@Test
	public void testAddFinishTime() {
		ArrayList<EnduroTime> times = new ArrayList<EnduroTime>();
		times.add(new EnduroTime("12.00.00"));
		times.add(new EnduroTime("13.00.00"));
		driver.setFinishTimes(times);

		assertEquals(times, driver.getFinishTimes());
	}

	@Test
	public void testPutTime() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		assertEquals("12.00.00", driver.getStartTimes().get(0).toString());
		assertEquals("12.00.00", driver.getStartTimeString());

	}

	@Test
	public void testFinalTime() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setFinishTime(new EnduroTime("12.15.00"));
		assertEquals("00.15.00", driver.getTotalTime());

	}

	@Test
	public void testStartFinishString() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setStartTime(new EnduroTime("12.01.00"));
		driver.setFinishTime(new EnduroTime("13.00.00"));
		driver.setFinishTime(new EnduroTime("13.01.00"));

		assertEquals("12.00.00,12.01.00", driver.getStartTimeString());
		assertEquals("13.00.00,13.01.00", driver.getFinishTimeString());
	}

	@Test
	public void testTotalTime() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setFinishTime(new EnduroTime("13.01.01"));
		assertEquals("01.01.01", driver.getTotalTime());
		driver.setStartTime(new EnduroTime("12.05.00"));
		assertEquals("01.01.01", driver.getTotalTime());
	}

	@Test
	public void testAddEmptyList() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setStartTime(new EnduroTime("12.01.00"));
		driver.setFinishTime(new EnduroTime("13.00.00"));
		driver.setFinishTime(new EnduroTime("13.01.00"));

		ArrayList<EnduroTime> compare = driver.getStartTimes();

		driver.setStartTimes(new ArrayList<EnduroTime>());

		assertEquals(compare, driver.getStartTimes());
	}

	@Test
	public void testAddList() {
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setStartTime(new EnduroTime("12.01.00"));
		driver.setFinishTime(new EnduroTime("13.00.00"));
		driver.setFinishTime(new EnduroTime("13.01.00"));

		ArrayList<EnduroTime> result = driver.getStartTimes();
		result.add(new EnduroTime("00.00.00"));
		result.add(new EnduroTime("10.10.00"));

		ArrayList<EnduroTime> test = new ArrayList<EnduroTime>();
		test.add(new EnduroTime("00.00.00"));
		test.add(new EnduroTime("10.10.00"));

		driver.setStartTimes(test);
		assertEquals(result, driver.getStartTimes());

	}

	@Test
	public void testComparable() {
		MarathonDriver driver2 = new MarathonDriver(2);
		driver.setStartTime(new EnduroTime("12.00.00"));
		driver.setFinishTime(new EnduroTime("13.00.00"));

		driver2.setStartTime(new EnduroTime("12.00.00"));
		driver2.setFinishTime(new EnduroTime("12.30.00"));

		assertTrue(driver.compareTo(driver2) > 0);
	}
	
	@Test
	public void getTimesWhenThereIsNoTimesRegistered(){
		MarathonDriver driver2 = new MarathonDriver(2);
		assertEquals("--.--.--",driver2.getFinishTimeString());
		assertEquals("--.--.--",driver2.getStartTimeString());
	}
	
	@Test
	public void compareDriverWithoutTotalTimeToOneWithTotalTime(){
		MarathonDriver driver2 = new MarathonDriver(2);
		MarathonDriver driver3 = new MarathonDriver(3);
		driver3.setStartTime(new EnduroTime("12.00.00"));
		driver3.setFinishTime(new EnduroTime("12.15.13"));
		assertEquals(-1,driver3.compareTo(driver2));
	}

	@After
	public void tearDown() throws Exception {
		driver = null;
	}

}
