package test;

import static common.ErrorMessage.error;
import static org.junit.Assert.assertEquals;
import generator.LapGenerator;
import generator.sorter.Sorter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Configuration;
import common.EditConfigForTest;
import common.EnduroTime;

import driver.AbstractDriver;
import driver.LapDriver;
import driver.factory.DriverFactory;
import driver.factory.LapFactory;

public class TestLapGenerator {
	private LapGenerator lg;

	@Before
	public void setUp() {
		EditConfigForTest.edit("raceType = varv\nnameFile=persons.txt");
		Configuration.factory = new LapFactory();
		Configuration.nameFile = "persons.txt";
		Sorter<LapDriver> sorter = new Sorter<LapDriver>();
		lg = new LapGenerator(sorter);
		Configuration.debug = 1;
		Configuration.printPlacment = false;
	}

	@Test
	public void testNoLapTimes() {
		HashMap<Integer, LapDriver> map = new HashMap<Integer, LapDriver>();
		map.put(1, new LapDriver(1));
		map.put(2, new LapDriver(2));
		Model<LapDriver> model = new Model<LapDriver>(map);
		lg.generateResult(model);
		String fileName = "data/result.txt";
		try {
			Scanner scanResults = new Scanner(new File(fileName));
			ArrayList<String> sb = new ArrayList<String>();
			sb.add("StartNr; Namn; #Varv; TotalTid; Start; Mål");
			sb.add("1; Anders Asson; 0; --.--.--; Start?; Slut?");
			sb.add("2; Berit Bsson; 0; --.--.--; Start?; Slut?");
			int i = 0;
			while (scanResults.hasNextLine()) {
				assertEquals(sb.get(i), scanResults.nextLine());
				i++;
			}

		} catch (Exception e) {
			error(e, "Could not scan file" + fileName);
		}
	}

	@Test
	public void testWriteUnregistered() {
		HashMap<Integer, LapDriver> map = new HashMap<Integer, LapDriver>();
		HashMap<Integer, AbstractDriver> unRegs = new HashMap<Integer, AbstractDriver>();
		LapDriver l1 = new LapDriver(1);
		LapDriver l2 = new LapDriver(2);
		LapDriver l3 = new LapDriver(3);
		l1.setStartTime(new EnduroTime("12.00.00"));
		l1.setFinishTime(new EnduroTime("12.15.00"));
		l2.setStartTime(new EnduroTime("12.00.02"));
		l2.setFinishTime(new EnduroTime("12.15.04"));
		l3.setStartTime(new EnduroTime("12.00.06"));
		l3.setFinishTime(new EnduroTime("12.15.06"));

		map.put(1, l1);

		map.put(2, l2);

		unRegs.put(3, l3);

		Model<LapDriver> model = new Model<LapDriver>(map);
		model.putUnregisteredDrivers(unRegs);

		lg.generateResult(model);
		String fileName = "data/result.txt";
		try {
			Scanner scanResults = new Scanner(new File(fileName));
			ArrayList<String> sb = new ArrayList<String>();
			sb.add("StartNr; Namn; #Varv; TotalTid; Varv1; Start; Mål");
			sb.add("1; Anders Asson; 1; 00.15.00; 00.15.00; 12.00.00; 12.15.00");
			sb.add("2; Berit Bsson; 1; 00.15.02; 00.15.02; 12.00.02; 12.15.04");
			sb.add("Unregisterted drivers");
			sb.add("StartNr; Namn; #Varv; TotalTid; Varv1; Start; Mål");
			sb.add("3; ; 1; 00.15.00; 00.15.00; 12.00.06; 12.15.06");
			int i = 0;
			while (scanResults.hasNextLine()) {
				assertEquals(sb.get(i), scanResults.nextLine());
				i++;
			}

		} catch (Exception e) {
			error(e, "Could not scan file" + fileName);
		}
	}

	@After
	public void smashToPiecesWithVirtualHacksaw() {
		EditConfigForTest.restore();
		new File("data/starttider.txt").delete();
		new File("data/maltider.txt").delete();
		new File("data/namnfil.txt").delete();
		new File("data/result.txt").delete();

	}

}
