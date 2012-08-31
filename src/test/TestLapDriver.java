package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.LapDriver;

public class TestLapDriver {
	private LapDriver lapDriver;

	@Before
	public void setUp() {

		lapDriver = new LapDriver(1);
		Configuration.debug = 1;
		Configuration.timeWhenOpen = new EnduroTime("00.00.00");
	}

	@Test
	public void testGetStartNumber() {
		assertTrue(lapDriver.getStartNumber() == 1);
	}

	@Test
	public void testGetClassName() {
		lapDriver.setClassName("Junior");
		assertEquals("Junior", lapDriver.getClassName());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetLapTimeOfNonExistingLap() {
		lapDriver.getLapTimes(10);
	}

	@Test
	public void testLapTimes() {
		EnduroTime times = new EnduroTime("12.00.00");
		lapDriver.putLapTime(0, times);
		assertEquals(times, lapDriver.getLapTimes(0));
	}

	@Test
	public void putInStartTime() {
		EnduroTime times = new EnduroTime("12.00.00");
		lapDriver.putLapTime(0, times);

		assertTrue(lapDriver.getLapTimes(0).equals(new EnduroTime("12.00.00")));
	}

	@Test
	public void putInLapTime() {
		EnduroTime lap0 = new EnduroTime("12.00.00");

		EnduroTime lap1 = new EnduroTime("12.00.01");

		EnduroTime lap2 = new EnduroTime("12.00.30");

		lapDriver.putLapTime(0, lap0);
		lapDriver.putLapTime(1, lap1);
		lapDriver.putLapTime(2, lap2);

		assertEquals(lapDriver.getLapTimes(0), new EnduroTime("12.00.00"));
		assertEquals(lapDriver.getLapTimes(1), new EnduroTime("12.00.01"));

		assertEquals(lapDriver.getLapTimes(2), new EnduroTime("12.00.30"));
	}

	@Test
	public void testTotaltime() {
		EnduroTime lap0 = new EnduroTime("00.20.00");
		EnduroTime lap1 = new EnduroTime("01.00.00");
		lapDriver.setStartTime(new EnduroTime("00.00.00"));
		lapDriver.setFinishTime(lap0);
		lapDriver.setFinishTime(lap1);
		assertEquals(new EnduroTime("01.00.00")
		.toString(), lapDriver.getTotalTime());

	}

	@Test
	public void testCompareTo() {

		EnduroTime lap0 = new EnduroTime("10.00.00");
		EnduroTime lap1 = new EnduroTime("12.00.00");
		EnduroTime lap2 = new EnduroTime("13.23.30");
		EnduroTime lap4 = new EnduroTime("12.00.00");
		EnduroTime lap5 = new EnduroTime("13.19.30");
		lapDriver.setStartTime(new EnduroTime("00.00.00"));
		lapDriver.setFinishTime(lap0);
		lapDriver.setFinishTime(lap1);
		lapDriver.setFinishTime(lap2); 	// total = 13.23.30
		LapDriver lapDriver2 = new LapDriver(2);
		LapDriver lapDriver3 = new LapDriver(3);
		lapDriver2.setStartTime(new EnduroTime("00.00.00"));
		lapDriver2.setFinishTime(lap0);
		lapDriver2.setFinishTime(lap4);		// total = 12.00.00
	
		lapDriver3.setStartTime(new EnduroTime("00.00.00"));
		lapDriver3.setFinishTime(lap0);
		lapDriver3.setFinishTime(lap4);
		lapDriver3.setFinishTime(lap5); // total = 13.19.30

		assertEquals(true, lapDriver.compareTo(lapDriver3) > 0); // Same Laps compare
		// times
		assertEquals(true, lapDriver.compareTo(lapDriver2) < 0); // More laps owns
		// faster time
		assertTrue(lapDriver2.compareTo(lapDriver3) > 0); // --''--

	}
	
	@Test
	public void testSetSeveralLapTimes(){
		LapDriver ld1 = new LapDriver(1);
		ArrayList<EnduroTime> st = new ArrayList<EnduroTime>();
		ArrayList<EnduroTime> ft = new ArrayList<EnduroTime>();
		ld1.setStartTimes(st);
		ld1.setFinishTimes(ft);
		assertEquals("--.--.--",ld1.getTotalTime());
	}
	
	@Test
	public void testRigorousCompare(){
		LapDriver ld1 = new LapDriver(1);
		LapDriver ld2 = new LapDriver(2);
		ld2.setFinishTime(new EnduroTime("14.00.20"));
		ld1.setStartTime(new EnduroTime("12.00.20"));
		ld1.setFinishTime(new EnduroTime("12.05.19"));
		assertEquals(-1,ld1.compareTo(ld2));
		assertEquals(1,ld2.compareTo(ld1));
	}
	
}