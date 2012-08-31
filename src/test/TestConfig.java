package test;

import static org.junit.Assert.*;

import generator.AbstractGenerator;
import generator.MarathonGenerator;
import generator.StageGenerator;
import generator.formatter.Formatter;
import generator.formatter.HTMLFormatter;

import java.io.File;
import java.util.HashMap;

import model.Model;

import org.junit.After;
import org.junit.Test;

import common.Configuration;
import common.DebugException;
import common.EditConfigForTest;
import common.EnduroTime;
import driver.LapDriver;
import driver.StageDriver;

public class TestConfig {

	@Test(expected = DebugException.class)
	public void testTimeWhenOpen() {
		EditConfigForTest.edit("raceType = varv\n" + "nameFile = persons.txt\n"
				+ "minTime = 00.15.00\n" + "sortBy = std\nresFormatter = csv\n"
				+ "timeWhenOpen = 10.00.00\n" + "e_numStages = 2\n"
				+ "e_specialStage = !ej implementerad\n"
				+ "e_batchTimeFiles = ");
		Configuration.debug = 1;
		Configuration.update();
		LapDriver ld = (LapDriver) Configuration.factory.make(1);
		assertFalse(ld.setStartTime(new EnduroTime("09.00.00")));
		assertTrue(ld.setStartTime(new EnduroTime("10.01.00")));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBatchTimeFiles() {
		EditConfigForTest
				.edit("raceType = etapp\n"
						+ "nameFile = persons.txt\n"
						+ "minTime = 00.15.00\n"
						+ "sortBy = std\nresFormatter = csv\n"
						+ "timeWhenOpen = 00.00.00\n"
						+ "e_numStages = 2\n"
						+ "e_specialStage = !ej implementerad\n"
						+ "e_batchTimeFiles = Iterationer/etapplopp-fullt/SS1Start.txt,Iterationer/etapplopp-fullt/SS1Mal.txt,Iterationer/etapplopp-fullt/SS2Start.txt,Iterationer/etapplopp-fullt/SS2Mal.txt");
		Configuration.update();
		Model<StageDriver> model = (Model<StageDriver>) Configuration.model;
		HashMap<Integer, StageDriver> map = model.getMap();
		StageDriver berit = map.get(2);
		assertEquals(true, berit.getStartTimes().size() == 2
				&& berit.getFinishTimes().size() == 2);
		assertEquals("11.00.00", berit.getStartTimes().get(0).toString());
		assertEquals("11.33.16", berit.getFinishTimes().get(0).toString());
		assertEquals("11.42.59", berit.getStartTimes().get(1).toString());
		assertEquals("12.16.29", berit.getFinishTimes().get(1).toString());
	}
	
	@Test(expected = DebugException.class)
	public void testBatchTimeMismatchedFiles() {
		EditConfigForTest
				.edit("raceType = etapp\ne_batchTimeFiles = tjenixen.txt");
		Configuration.update();
		
	}
	
	@Test(expected = DebugException.class)
	public void testBatchTimeMismatchedFilesAndNbrOfStages() {
		EditConfigForTest
				.edit("raceType = etapp\ne_batchTimeFiles = tjenixen.txt\ne_numStages=4");
		Configuration.update();
		
	}

	@Test
	public void testMarathonWithBlankConfigFile() {
		EditConfigForTest
				.edit("e_specialStage = !ej implementerad\ne_batchTimeFiles =");
		Configuration.update();
		AbstractGenerator generator = Configuration.generator;
		assertTrue(generator instanceof MarathonGenerator);
	}

	@Test
	public void testWithBadlyFormattedTimes() {
		EditConfigForTest
				.edit("raceType = etapp\n" + "nameFile = persons.txt\n"
						+ "minTime = 00.15.00\n" + "sortBy = stNr\n"
						+ "resFormatter = flurp\n"
						+ "timeWhenOpen = imma.killin-tha.test\n"
						+ "e_numStages = immakillin-you\n"
						+ "e_specialStage = !ej implementerad\n"
						+ "e_batchTimeFiles =");
		Configuration.update();
		AbstractGenerator generator = Configuration.generator;
		assertTrue(generator instanceof StageGenerator);
	}

	@Test
	public void testWithHtmlFormatter() {
		EditConfigForTest.edit("resFormatter = html");
		Configuration.update();
		AbstractGenerator generator = Configuration.generator;
		Formatter form = Configuration.formatter;
		assertTrue(generator instanceof MarathonGenerator);
		assertTrue(form instanceof HTMLFormatter);
	}
	
	@Test
	public void testWithoutConfigfile() {
		EditConfigForTest.edit("resFormatter = html");
		new File("enduro.config").delete();
		AbstractGenerator generator = Configuration.generator;
		EditConfigForTest.restore();
	}

	@After
	public void restore() {
		EditConfigForTest.restore();
		Configuration.update();
	}

}
