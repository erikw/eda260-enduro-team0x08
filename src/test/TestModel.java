package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import model.Model;

import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.AbstractDriver;
import driver.MarathonDriver;

public class TestModel {
	private Model<MarathonDriver> model;
	private HashMap<Integer, MarathonDriver> map;

	@Before
	public void setUp() {
		Configuration.debug = 1;
		model = new Model<MarathonDriver>();
		map = new HashMap<Integer, MarathonDriver>();
		map.put(1, new MarathonDriver(1));
		map.put(2, new MarathonDriver(2));
	}

	@Test
	public void testOpenFromMap() {
		model.openFromMap(map);
		assertTrue(model.getDriver(1).getStartNumber() == 1);
	}

	@Test
	public void testGetResult() {
		model.openFromMap(map);
		model.putStart(1, "12.00.00");
		model.putFinish(1, "12.15.00");
		model.putStart(2, "12.00.00");
		model.putFinish(2, "14.15.45");
		assertEquals("1,[12.00.00],[12.15.00]", model.getResult(1));
		assertEquals("2,[12.00.00],[14.15.45]", model.getResult(2));
	}

	@Test
	public void testGenerateMassStart() {
		map.put(3, new MarathonDriver(3));
		map.put(4, new MarathonDriver(4));
		model.openFromMap(map);
		model.generateMassStart("12.00.00");
		assertEquals(model.getDriver(1).getStartTimes(), model.getDriver(2)
				.getStartTimes());
		assertEquals(model.getDriver(2).getStartTimes(), model.getDriver(3)
				.getStartTimes());
		assertEquals(model.getDriver(3).getStartTimes(), model.getDriver(4)
				.getStartTimes());
	}

	@Test
	public void testGenerateMassStartWave() {
		map.put(3, new MarathonDriver(3));
		map.put(4, new MarathonDriver(4));
		map.put(5, new MarathonDriver(3));
		map.put(6, new MarathonDriver(4));
		model.openFromMap(map);
		// MarathonFactory mf = new MarathonFactory();
		model.generateMassStartWave(1, 2, "12.00.00");
		model.generateMassStartWave(3, 4, "13.00.00");
		model.generateMassStartWave(5, 6, "14.00.00");
		assertEquals(model.getDriver(1).getStartTimes(), model.getDriver(2)
				.getStartTimes());
		assertEquals(model.getDriver(3).getStartTimes(), model.getDriver(4)
				.getStartTimes());
		assertEquals(model.getDriver(5).getStartTimes(), model.getDriver(6)
				.getStartTimes());
		assertFalse(model.getDriver(2).getStartTimes()
				.equals(model.getDriver(3).getStartTimes()));
		assertFalse(model.getDriver(4).getStartTimes()
				.equals(model.getDriver(5).getStartTimes()));
	}

	@Test
	public void testGetNonExistingDriver() {
		AbstractDriver d = model.getDriver(52);
		assertEquals(null, d);

	}
	// Inte implementerad
	
	// @Test(expected = NullPointerException.class)
	// public void testGenerateMassStartWaveMissingDriver() {
	// MarathonFactory mf = new MarathonFactory();
	// model.generateMassStartWave(1, 3, "12.00.00", mf);
	
//	 }

	@Test
	public void testGetUnregistered() {
		model.openFromMap(map);
		MarathonDriver md1 = new MarathonDriver(5);
		MarathonDriver md2 = new MarathonDriver(6);
		HashMap<Integer, MarathonDriver> list = new HashMap<Integer, MarathonDriver>();
		list.put(md1.getStartNumber(), md1);
		list.put(md2.getStartNumber(), md2);
		model.putUnregisteredDriver(md1);
		model.putUnregisteredDriver(md2);
		HashMap<Integer, MarathonDriver> driver = model.getUnregisteredMap();

		assertEquals(list, driver);

	}

	@Test
	public void testSwap() {
		model.openFromMap(map);
		model.putStart(1, "12.00.00");
		model.putFinish(1, "12.15.00");

		model.moveTimeStart(1, 2, new EnduroTime("12.00.00"));
		model.moveTimeFinish(1, 2, new EnduroTime("12.15.00"));

		assertEquals(new EnduroTime("12.00.00"), model.getDriver(2)
				.getStartTimes().get(0));
		assertEquals(new EnduroTime("12.15.00"), model.getDriver(2)
				.getFinishTimes().get(0));

		assertEquals(0, model.getDriver(1).getStartTimes().size());
		assertEquals(0, model.getDriver(1).getFinishTimes().size());
	}

	@Test
	public void testChange() {
		model.openFromMap(map);
		model.change(1, 10);

		assertNull(model.getDriver(1));
		assertEquals(10, model.getDriver(10).getStartNumber());
	}

	@Test
	public void testChangeWithMerge() {
		model.openFromMap(map);
		model.putStart(1, "12.00.00");
		model.putFinish(1, "12.15.00");
		model.putStart(2, "12.10.00");
		model.putFinish(2, "12.30.00");

		model.change(1, 2);
		assertNull(model.getDriver(1));
		assertEquals(2, model.getDriver(2).getStartTimes().size());
		assertEquals(2, model.getDriver(2).getStartTimes().size());
	}

	@Test
	public void testChangeFail() {
		model.openFromMap(map);

		assertFalse(model.change(10, 15));
	}

	@Test
	public void testMoveStartFail() {
		model.openFromMap(map);
		model.putStart(1, "12.00.00");
		model.putFinish(1, "12.15.00");

		model.moveTimeStart(15, 1, new EnduroTime("12.00.00"));
		assertNull(model.getDriver(15));
		model.moveTimeStart(1, 2, new EnduroTime("12.15.00"));
		assertEquals(0, model.getDriver(2).getStartTimes().size());

		model.moveTimeStart(1, 3, new EnduroTime("12.00.00"));
		assertNotNull(model.getDriver(3));
	}

	@Test
	public void testMoveFinishFail() {
		model.openFromMap(map);
		model.putStart(1, "12.00.00");
		model.putFinish(1, "12.15.00");

		model.moveTimeFinish(15, 1, new EnduroTime("12.00.00"));
		assertNull(model.getDriver(15));
		model.moveTimeFinish(1, 2, new EnduroTime("12.00.00"));
		assertEquals(0, model.getDriver(2).getStartTimes().size());

		model.moveTimeFinish(1, 3, new EnduroTime("12.15.00"));
		assertNotNull(model.getDriver(3));
	}
}
