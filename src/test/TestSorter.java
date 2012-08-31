package test;

import static org.junit.Assert.assertEquals;
import generator.LapGenerator;
import generator.sorter.Sorter;

import java.util.ArrayList;
import java.util.List;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EnduroTime;

import driver.LapDriver;
import driver.MarathonDriver;
import driver.StageDriver;

public class TestSorter {

	@Before
	public void setUp() {
		// generator = new MarathonGenerator(null);
		Configuration.debug = 1;

	}

	@Test
	public void testSortAfterMarathon() {
		Model<MarathonDriver> mm = new Model<MarathonDriver>();

		MarathonDriver md1 = new MarathonDriver(1);
		MarathonDriver md2 = new MarathonDriver(2);
		MarathonDriver md3 = new MarathonDriver(3);

		mm.put(1, md1);
		mm.put(2, md2);
		mm.put(3, md3);

		mm.generateMassStart("12.00.00");

		md1.setFinishTime(new EnduroTime("12.40.00"));
		md2.setFinishTime(new EnduroTime("12.30.00"));
		md3.setFinishTime(new EnduroTime("12.35.00"));

		Sorter<MarathonDriver> marathonSorter = new Sorter<MarathonDriver>();
		List<MarathonDriver> mList = marathonSorter.sort(mm);
		// mGen.generateResult(mm.getMap(), marathonSorter);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mList.size(); i++) {
			sb.append(((i + 1) + "; " + mList.get(i).getStartNumber() + "; "
					+ mList.get(i).getTotalTime() + "\n"));
		}

		assertEquals("1; 2; 00.30.00\n2; 3; 00.35.00\n3; 1; 00.40.00\n", sb
				.toString());
	}

	@Test
	public void testSortAfterLap() {
		Model<LapDriver> mm = new Model<LapDriver>();

		LapDriver md1 = new LapDriver(1);
		LapDriver md2 = new LapDriver(2);
		LapDriver md3 = new LapDriver(3);

		mm.put(1, md1);
		mm.put(2, md2);
		mm.put(3, md3);

		mm.generateMassStart("12.00.00");

		md1.setFinishTime(new EnduroTime("12.40.00"));
		md2.setFinishTime(new EnduroTime("12.45.00"));
		md2.setFinishTime(new EnduroTime("13.30.00"));
		md2.setFinishTime(new EnduroTime("13.45.00"));
		md3.setFinishTime(new EnduroTime("12.35.00"));

		Sorter<LapDriver> lapSorter = new Sorter<LapDriver>();
		List<LapDriver> mList = lapSorter.sort(mm);
		// mGen.generateResult(mm.getMap(), marathonSorter);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mList.size(); i++) {
			sb.append(((i + 1) + "; " + mList.get(i).getStartNumber() + "; "
					+ mList.get(i).numberOfLaps() + "; "
					+ mList.get(i).getTotalTime() + "\n"));
		}
		assertEquals("1; 2; 3; 01.45.00\n2; 3; 1; 00.35.00\n3; 1; 1; 00.40.00\n", sb
				.toString());
	}

	@Test
	public void testSortAfterStage() {
		Model<StageDriver> mm = new Model<StageDriver>();

		StageDriver md1 = new StageDriver(1);
		StageDriver md2 = new StageDriver(2);
		StageDriver md3 = new StageDriver(3);

		mm.put(1, md1);
		mm.put(2, md2);
		mm.put(3, md3);

		mm.generateMassStart("12.00.00");
		md1.setFinishTime(new EnduroTime("12.40.00"));
		md2.setFinishTime(new EnduroTime("13.30.00"));
		md3.setFinishTime(new EnduroTime("12.30.00"));
		
		md1.setStartTime(new EnduroTime("13.00.00"));
		md1.setFinishTime(new EnduroTime("13.15.00"));
		
		md1.setStartTime(new EnduroTime("13.30.00"));
		md1.setFinishTime(new EnduroTime("14.35.00"));
	
		md2.setStartTime(new EnduroTime("13.40.00"));
		md2.setFinishTime(new EnduroTime("14.10.00"));
		
		md2.setStartTime(new EnduroTime("14.40.00"));
		md2.setFinishTime(new EnduroTime("14.55.00"));
	
		md3.setStartTime(new EnduroTime("12.40.00"));
		md3.setFinishTime(new EnduroTime("12.50.00"));

		Sorter<StageDriver> stageSorter = new Sorter<StageDriver>();
		List<StageDriver> mList = stageSorter.sort(mm);
		// mGen.generateResult(mm.getMap(), marathonSorter);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mList.size(); i++) {
			sb.append(((i + 1) + "; " + mList.get(i).getStartNumber() + "; "
					+ mList.get(i).getStages().size() + "; "
					+ mList.get(i).getTotalTime() + "\n"));
		}
		assertEquals("1; 1; 3; 02.00.00\n2; 2; 3; 02.15.00\n3; 3; 2; 00.40.00\n", sb
				.toString());


	}
	
	@Test
	public void testEmptyLap() {
		Model<LapDriver> mm = new Model<LapDriver>();
		Sorter<LapDriver> sorter = new Sorter<LapDriver>();
		Configuration.nameFile ="persons.txt";
		LapGenerator lgen = new LapGenerator(sorter);
		LapDriver md1 = new LapDriver(1);
		LapDriver md2 = new LapDriver(2);
		LapDriver md3 = new LapDriver(3);

		mm.put(1, md1);
		mm.put(2, md2);
		mm.put(3, md3);
		lgen.generateAll(mm);	
}	
	@Test
	public void testSortWithoutMap(){
		MarathonDriver md1 = new MarathonDriver(1);
		MarathonDriver md2 = new MarathonDriver(2);
		MarathonDriver md3 = new MarathonDriver(3);
		md1.setStartTime(new EnduroTime("12.00.00"));
		md2.setStartTime(new EnduroTime("12.00.00"));
		md3.setStartTime(new EnduroTime("12.00.00"));
		md1.setFinishTime(new EnduroTime("12.40.00"));
		md2.setFinishTime(new EnduroTime("12.30.00"));
		md3.setFinishTime(new EnduroTime("12.35.00"));
		Sorter<MarathonDriver> marathonSorter = new Sorter<MarathonDriver>();
		List<MarathonDriver> list = new ArrayList<MarathonDriver>();
		list.add(md1);
		list.add(md2);
		list.add(md3);
		List<MarathonDriver> list2 = marathonSorter.sort(list,null);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list2.size(); i++) {
			sb.append(((i + 1) + "; " + list2.get(i).getStartNumber() + "; "
					+ list2.get(i).getTotalTime() + "\n"));
		}

		assertEquals("1; 2; 00.30.00\n2; 3; 00.35.00\n3; 1; 00.40.00\n", sb
				.toString());
	}

	@After
	public void tearDown() {
	}

}
