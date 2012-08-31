package test.acceptance;

import static org.junit.Assert.assertEquals;
import model.Model;

import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.MarathonDriver;

public class TestAcceptance11 {

	private Model<MarathonDriver> lm;

	@Test
	public void testMassStart() {
		Configuration.debug = 1;
		lm = new Model<MarathonDriver>();
		lm.put(0, new MarathonDriver(0));
		lm.put(1, new MarathonDriver(1));
		lm.put(3, new MarathonDriver(3));
		lm.put(2, new MarathonDriver(2));

		lm.generateMassStart("12.00.00");
		EnduroTime enduroTime = new EnduroTime("12.00.00");

		assertEquals(lm.getDriver(0).getStartTimes().get(0), enduroTime);
		assertEquals(lm.getDriver(1).getStartTimes().get(0), enduroTime);
		assertEquals(lm.getDriver(2).getStartTimes().get(0), enduroTime);
		assertEquals(lm.getDriver(3).getStartTimes().get(0), enduroTime);
	}

}
