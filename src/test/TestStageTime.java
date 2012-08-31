package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;
import common.StageTime;

public class TestStageTime {

	protected StageTime t1;

	@Before
	public void setUp() throws Exception {
		Configuration.debug = 1;
		t1 = new StageTime(new EnduroTime("12.00.01"), new EnduroTime(
				"13.00.01"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEmptyTimes() {
		StageTime t2 = new StageTime();
		assertEquals(null, t2.getStartTime());
		assertEquals("Start?", t2.getStartTimeString());
		assertEquals(null, t2.getFinishTime());
		assertEquals("Slut?", t2.getFinishTimeString());
	}

	@Test
	public void testGetStartGetFinish() {
		assertEquals(t1.getStartTime(), new EnduroTime("12.00.01"));
		assertEquals(t1.getFinishTime(), new EnduroTime("13.00.01"));
	}

	@Test
	public void testSetStartFinish() {

		StageTime t2 = new StageTime();
		t2.setStartTime(new EnduroTime("14.00.00"));
		t2.setFinishTime(new EnduroTime("15.00.00"));

		assertEquals(t2.getStartTimeString(), "14.00.00");
		assertEquals(t2.getFinishTimeString(), "15.00.00");

	}

	@Test
	public void testCreatAmplifyedTime() {
		StageTime t2 = new StageTime(new EnduroTime("14.00.00"),new EnduroTime("15.00.00"), 5);
		assertEquals(t2.getTotalString(), "05.00.00");

	}
	@Test
	public void testGetTotal() {
		assertEquals(t1.getTotal(), new EnduroTime("01.00.00"));
	}

	@Test
	public void testAmplifier() {
		assertEquals(1, t1.getAmplifier());
		t1.setAmplifier(3);
		assertEquals(3, t1.getAmplifier());
		assertEquals(new EnduroTime("03.00.00"), t1.getTotal());
	}
	
	@Test
	public void testNoStartTime(){
		StageTime t2 = new StageTime();
		t2.setStartTime(null);
		assertEquals("",t2.getTotalString());
	}
}
