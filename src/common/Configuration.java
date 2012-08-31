package common;

import static common.ErrorMessage.error;
import generator.AbstractGenerator;
import generator.LapGenerator;
import generator.MarathonGenerator;
import generator.StageGenerator;
import generator.formatter.CSVFormatter;
import generator.formatter.Formatter;
import generator.formatter.HTMLFormatter;
import generator.sorter.Sorter;
import generator.sorter.StartNumberSorter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Locale;
import java.util.Properties;

import model.Model;
import parser.FinishParser;
import parser.StartParser;
import driver.AbstractDriver;
import driver.LapDriver;
import driver.MarathonDriver;
import driver.StageDriver;
import driver.factory.DriverFactory;
import driver.factory.LapFactory;
import driver.factory.MarathonFactory;
import driver.factory.StageFactory;

/**
 * 
 * A class that reads settings from the configuration file "endufor.config" and
 * creates the appropriate objects and sets the settings.
 * 
 */
@SuppressWarnings("unchecked")
public class Configuration {

	/* The model to use. */
	public static Model<? extends AbstractDriver> model;

	/* The generator the program uses. */
	public static AbstractGenerator generator;

	/* The sorter to use to sort the result. */
	public static Sorter<? extends AbstractDriver> sorter;

	/* The name file to read person data from. */
	public static String nameFile;

	/* The file to read and write start times from/to. */
	public static String startFile;

	/* The file to read and write finish times from/to. */
	public static String finishFile;

	/* The factory that produces the correct drivers for the current race. */
	public static DriverFactory<? extends AbstractDriver> factory;

	/* The result formatter for the result file. */
	public static Formatter formatter;

	/* The minimum time that a lap can take. */
	public static EnduroTime minTime;

	/* The comparator to use with sorting. */
	public static Comparator<? super AbstractDriver> sortBy;

	/* Default time when the race begins. */
	public final static String DEFAULT_TIMEWHENOPEN = "00.00.00";

	/* The open time for start of a race. */
	public static EnduroTime timeWhenOpen;

	/* The station number of this station. Used for identification. */
	public static String stationID;

	/* The maximum number of stages. */
	public static int maxStages;

	/* Set to 1 if placment should be printed in the result file. */
	public static boolean printPlacment;

	/*
	 * Enable or disable error dialogs. Debug = 1 gives runtime-exceptions
	 * instead.
	 */
	public static int debug = 0;

	/**
	 * Static initializer block. This block is executed when the program is
	 * loaded to the VM.
	 */

	static {
		debug = 1;
		update();
		debug = 0;
	}

	/**
	 * Private method for reading batch files for stage.
	 * 
	 * @param sEBatchTimeFiles
	 *            Comma-separated filenames
	 * @param nbrOfStages
	 *            The number of stages.
	 */
	private static void stageFillWithTimes(String sEBatchTimeFiles, int nbrOfStages) {
		if (sEBatchTimeFiles == null || sEBatchTimeFiles.length() == 0) {
			return;
		}
		String[] fileNames = sEBatchTimeFiles.split(",");
		if (fileNames.length % 2 != 0) {
			error(new FileNotFoundException(), "Mismatched files for batchjob");
			return;
		}
		if (fileNames.length / 2 != nbrOfStages) {
			error(new Exception(),
					"Number of files is not equal to number of stages.");
			return;
		}
		for (int i = 0; i < fileNames.length; i += 2) {
			try {
				StartParser startparser = new StartParser(fileNames[i], factory);
				FinishParser finishparser = new FinishParser(fileNames[i + 1],
						factory);
				startparser.parse(model);
				finishparser.parse(model);
			} catch (FileNotFoundException e) {
				error(e, "File not found " + fileNames[i] + " or "
						+ fileNames[i + 1] + ".");
			} catch (IOException e) {
				error(e, "There was an error in the parsing of " + fileNames[i]
						+ " or " + fileNames[i + 1] + ".");
			}
		}
	}

	/**
	 * Reread contents from config-file, must be used after an edit.
	 */
	public static void update() {
		InputStream confStream;
		Properties myProperties = new Properties();
		String confFileName = "enduro.config";
		try {
			confStream = new FileInputStream(new File(confFileName));
			myProperties.load(confStream);
			confStream.close();
		} catch (FileNotFoundException e) {
			error(e, "Could not open the configuration file: " + confFileName);
		} catch (IOException e) {
			error(e, "Could not load settings from the config file "
					+ confFileName);
		}

		// Read settings
		String sRaceType = myProperties.getProperty("raceType");
		String sFormatter = myProperties.getProperty("resFormatter");
		String sMinTime = myProperties.getProperty("minTime");
		String sFileName = myProperties.getProperty("nameFile");
		String sSortBy = myProperties.getProperty("sortBy");
		String sNumStages = myProperties.getProperty("e_numStages");
		String sTimeWhenOpen = myProperties.getProperty("timeWhenOpen");
		String sEBatchTimeFiles = myProperties.getProperty("e_batchTimeFiles");
		String sStationID = myProperties.getProperty("stationID");

		// defaults
		if (sRaceType == null) {
			sRaceType = "marathon";
		}
		if (sFormatter == null) {
			sFormatter = "csv";
		}
		if (sMinTime == null) {
			sMinTime = "00.15.00";
		}
		if (sFileName == null) {
			sFileName = "persons.txt";
		}
		if (sSortBy == null) {
			sSortBy = "std";
		}
		if (sNumStages == null) {
			sNumStages = "3";
		}
		if (sTimeWhenOpen == null) {
			sTimeWhenOpen = DEFAULT_TIMEWHENOPEN;
		}
		if (sStationID == null) {
			sStationID = "-1";
		}

		// parsers
		try {
			maxStages = Integer.parseInt(sNumStages);
		} catch (NumberFormatException e) {
			maxStages = 2;
		}

		try {
			timeWhenOpen = new EnduroTime(sTimeWhenOpen);
		} catch (NumberFormatException ex) {
			timeWhenOpen = new EnduroTime(DEFAULT_TIMEWHENOPEN);
		}

		// Startion ID.
		stationID = sStationID;

		// Starttidsfil path
		startFile = "data/startFiles/start-" + stationID + ".txt";
		// Måltidsfil path
		finishFile = "data/finishFiles/finish-" + stationID + ".txt";

		// nameFile
		nameFile = sFileName;

		// raceType
		switch (sRaceType.toLowerCase(Locale.ENGLISH).charAt(0)) {
		default:
		case 'm':
			model = new Model<MarathonDriver>();
			sorter = new Sorter<MarathonDriver>();
			generator = new MarathonGenerator(nameFile,
					(Sorter<MarathonDriver>) sorter);
			factory = new MarathonFactory();
			break;
		case 'v':
			model = new Model<LapDriver>();
			sorter = new Sorter<LapDriver>();
			generator = new LapGenerator((Sorter<LapDriver>) sorter);
			factory = new LapFactory();
			break;
		case 'e':
			model = new Model<StageDriver>();
			sorter = new Sorter<StageDriver>();

			generator = new StageGenerator((Sorter<StageDriver>) sorter);

			factory = new StageFactory();
			stageFillWithTimes(sEBatchTimeFiles, maxStages);
			break;
		}

		// resFormatter
		if ("csv".equals(sFormatter)) {
			formatter = new CSVFormatter();
		} else if ("html".equals(sFormatter)) {
			formatter = new HTMLFormatter();
		} else { // Standard foramtter
			formatter = new CSVFormatter();
		}

		// minTime
		minTime = new EnduroTime(sMinTime);

		// sortBy
		if ("std".equals(sSortBy)) {
			sortBy = null;
			printPlacment = true;
		} else if ("stNr".equals(sSortBy)) {
			sortBy = new StartNumberSorter();
			printPlacment = false;
		}
	}
}
