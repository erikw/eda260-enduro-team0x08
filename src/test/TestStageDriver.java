package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.StageDriver;

public class TestStageDriver {

	protected StageDriver d1;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		d1 = new StageDriver(1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStageList() {
		d1.setStartTime(new EnduroTime("12.00.00"));
		d1.setFinishTime(new EnduroTime("13.00.00"));

		assertEquals("12.00.00", d1.getStartTimes().get(0).toString());
		assertEquals("13.00.00", d1.getFinishTimes().get(0).toString());
	}

	@Test
	public void testGetTotalTimeString() {
		d1.setStartTime(new EnduroTime("12.00.00"));
		d1.setFinishTime(new EnduroTime("13.00.00"));
		assertEquals("01.00.00", d1.getTotalTime());
	}

	@Test
	public void testGetTotal() {
		// Stage 1
		d1.setStartTime(new EnduroTime("12.00.00"));
		d1.setFinishTime(new EnduroTime("13.00.00"));
		// Stage 2
		d1.setStartTime(new EnduroTime("14.00.00"));
		d1.setFinishTime(new EnduroTime("15.00.00"));

		assertEquals(new EnduroTime("02.00.00"), d1.getTotal());
	}

	@Test
	public void testCompareTo() {
		d1.setStartTime(new EnduroTime("12.00.00"));
		d1.setFinishTime(new EnduroTime("13.00.00"));
		d1.setStartTime(new EnduroTime("13.30.00"));
		d1.setFinishTime(new EnduroTime("14.00.00"));
		StageDriver d2 = new StageDriver(2);
		d2.setStartTime(new EnduroTime("12.00.00"));
		d2.setFinishTime(new EnduroTime("13.00.00"));
		d2.setStartTime(new EnduroTime("13.50.00"));
		d2.setFinishTime(new EnduroTime("14.00.00"));
		StageDriver d3 = new StageDriver(3);
		d3.setStartTime(new EnduroTime("12.00.00"));
		d3.setFinishTime(new EnduroTime("13.00.00"));

		assertTrue(d1.compareTo(d2) > 0); // d2 has lower total time
		assertTrue(d1.compareTo(d3) < 0); // d3 has less stages
		assertTrue(d2.compareTo(d3) < 0); // d3 has less stages
	}
	
	@Test
	public void setManyStartTimesAndFinishTimes(){
		ArrayList<EnduroTime> startTimes = new ArrayList<EnduroTime>();
		startTimes.add(new EnduroTime("12.00.00"));
		startTimes.add(new EnduroTime("12.00.10"));
		startTimes.add(new EnduroTime("12.00.20"));
		ArrayList<EnduroTime> finishTimes = new ArrayList<EnduroTime>();
		finishTimes.add(new EnduroTime("13.00.00"));
		finishTimes.add(new EnduroTime("13.00.10"));
		finishTimes.add(new EnduroTime("13.00.20"));
		d1.setStartTimes(startTimes);
		d1.setFinishTimes(finishTimes);
		ArrayList<EnduroTime> actualStartTimes = d1.getStartTimes();
		ArrayList<EnduroTime> actualFinishTimes = d1.getFinishTimes();
		for(int i = 0; i < 3; i++){
			assertEquals(startTimes.get(i),actualStartTimes.get(i));
			assertEquals(finishTimes.get(i),actualFinishTimes.get(i));
		}
		
	}

}
