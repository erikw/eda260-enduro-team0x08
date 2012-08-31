package generator;

import static common.ErrorMessage.error;
import generator.formatter.Formatter;
import generator.sorter.Sorter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Model;
import parser.PersonParser;

import common.Configuration;
import common.EnduroTime;

import driver.AbstractDriver;

/**
 * An abstract generator for generating start, finish and result files for
 * different drivers.
 * 
 */
public abstract class AbstractGenerator {

	/* The sorter to sort the result with. */
	protected Sorter<? extends AbstractDriver> sorter;

	/* No value constant. */
	protected static final String EMPTY_VALUE = "--.--.--";

	/**
	 * Instantiate an AbstractGenerator.
	 * 
	 * @param sorter
	 *            The sorter to use for sorting the result.
	 */
	public AbstractGenerator(Sorter<? extends AbstractDriver> sorter) {
		this.sorter = sorter;

		// Create a default person file if the from argument supplied one does
		// not exists.
		String personFile = Configuration.nameFile;
		File existCheck = new File(personFile);
		if (!existCheck.exists()) {
			try {
				existCheck.createNewFile();
			} catch (IOException e) {
				error(e, "Could not create file " + existCheck);
			}
			FileWriter personWriter = null;
			try {
				personWriter = new FileWriter(existCheck);
				personWriter.write("StartNr; Namn");
				personWriter.close();
			} catch (IOException e) {
				error(e, "Could not create or use a filewriter for the file "
						+ existCheck);
			}
		}

		// Create the output folder to put all generated files in.
		new File("data/startFiles").mkdirs();
		new File("data/finishFiles").mkdir();
	}

	/**
	 * Generate parse results from start file, finish file and produce a result
	 * file.
	 * 
	 * @param model
	 *            The model to generate from.
	 * 
	 */
	public <D extends AbstractDriver> void generateAll(Model<D> model) {
		generateStart(model);
		generateFinish(model);
		generateResult(model);
	}

	/**
	 * Parse results from start file and put result in the supplied structure.
	 * 
	 * @param model
	 *            The structure to put the result in.
	 */
	public <D extends AbstractDriver> void generateStart(Model<D> model) {
		File start = new File(Configuration.startFile);
		
		try {
			start.createNewFile();
			FileWriter writer = new FileWriter(start);
			Map<Integer, D> map = model.getMap();
			Map<Integer, D> unregmap = model.getUnregisteredMap();

			for (D driver : map.values()) {
				
				// A driver can occupy more than one line, loop through all.
				for (EnduroTime time : driver.getStartTimes()) {
					writer.write(driver.getStartNumber() + "; "
							+ time.toString() + "\n");
				}
			}
			for (D driver : unregmap.values()) {
				// A driver can occupy more than one line, loop through all.
				for (EnduroTime time : driver.getStartTimes()) {
					writer.write(driver.getStartNumber() + "; "
							+ time.toString() + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			error(e, "Could not make a FileWriter for the file: " + start);
		}
	}

	/**
	 * Parse results from finish file and put result in the supplied structure.
	 * 
	 * @param model
	 */
	public <D extends AbstractDriver> void generateFinish(Model<D> model) {
		File finish = new File(Configuration.finishFile);
		try {
			FileWriter writer = new FileWriter(finish);

			Map<Integer, D> map = model.getMap();
			Map<Integer, D> unregmap = model.getUnregisteredMap();

			for (D driver : map.values()) {
				// A driver can occupy more than one line, loop through all.
				for (EnduroTime time : driver.getFinishTimes()) {
					writer.write(driver.getStartNumber() + "; "
							+ time.toString() + "\n");
				}
			}
			for (D driver : unregmap.values()) {
				// A driver can occupy more than one line, loop through all.
				for (EnduroTime time : driver.getFinishTimes()) {
					writer.write(driver.getStartNumber() + "; "
							+ time.toString() + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			error(e, "Could not create FileWriter for file: " + finish);
		}
	}

	/**
	 * Generates a result file from the supplied model in the format specified
	 * by the formatter in the configuration.
	 * 
	 * @param model
	 *            The model to use.
	 */
	public <D extends AbstractDriver> void generateResult(
			Model<? extends AbstractDriver> model) {
		try {
			PersonParser pparser = new PersonParser(Configuration.nameFile,
					Configuration.factory);
			pparser.parse(model);
		} catch (Exception e) {
			error(e, "Could not create a personparser for file "
					+ Configuration.nameFile + " or could not parse it.");
		}
		Formatter formatter = Configuration.formatter;
		List<String> classList = model.getClassNames();

		if (!classList.isEmpty()) {
			// Generate result individually for each class.
			for (String className : classList) {
				if (className.length() > 0) {
					formatter.putClassHeader(className);
				}
				Model<? extends AbstractDriver> classModel = model
						.subset(className);
				computeResult(classModel, formatter);
			}
		} else {
			computeResult(model, formatter);
		}

		// Drivers with no start number.
		Model<? extends AbstractDriver> unRegModel = model.subsetUnregistered();
		if (!unRegModel.isEmpty()) {
			formatter.putClassHeader("Unregisterted drivers");
			computeResult(unRegModel, formatter);
		}

		// Write result to file.
		File resultFile = new File("data/result."
				+ formatter.getFileExtension());
		try {
			FileWriter writer = new FileWriter(resultFile);
			writer.write(formatter.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			error(e, "Could not instantiate or use FileWriter for "
					+ resultFile);
		}
	}

	/**
	 * Adds the race type specific data to the formatter that the generateResult
	 * method uses.
	 * 
	 * @param model
	 *            The mode to generate from.
	 * @param formatter
	 *            A formatter for formatting the values.
	 */
	protected abstract <D extends AbstractDriver> void computeResult(
			Model<D> model, Formatter formatter);

	/**
	 * Add all the personal information that exists in the supplied model to the
	 * supplied formatter.
	 * 
	 * * @param model The model to get information from.
	 * 
	 * @param formatter
	 *            The formatter to put the result to.
	 */
	protected void addPersonInfoHeader(Model<? extends AbstractDriver> model,
			Formatter formatter) {
		String[] setInfo = model.getColumnNames();
		for (String property : setInfo) {
			formatter.putHeaderValue(property);
		}
	}

	/**
	 * Add all personal values found about the supplied driver to the supplied
	 * formatter.
	 * 
	 * @param model
	 *            The model to get information from.
	 * @param formatter
	 *            The formatter to put the result to.
	 * @param driver
	 *            The driver to fetch info from.
	 */
	protected void addPersonInfoValues(Model<? extends AbstractDriver> model,
			Formatter formatter, AbstractDriver driver) {
		List<String> personInfo = driver.getInfo();
		int infoPrinted = 0;
		for (String info : personInfo) {
			formatter.putEntryValue(info);
			infoPrinted++;
		}
		// Add missing columns on person information
		String[] setInfo = model.getColumnNames();
		formatter.fillEmptyEntries((setInfo.length - 1) - infoPrinted);
	}

	/**
	 * Adds one or many startimes to the formatter if there is more then one
	 * start time the string "Flera Startider?"
	 * 
	 * @param formatter
	 *            The formatter to put the result to.
	 * 
	 * @param driver
	 * 
	 */

	protected void manyStartTimes(Formatter formatter, AbstractDriver driver) {
		StringBuilder extraData = new StringBuilder();

		if (driver.getStartTimes().size() > 1) {
			extraData.append("Flera starttider? ");
			for (int i = 1; i < driver.getStartTimes().size(); i++) {
				extraData.append(driver.getStartTimes().get(i));
			}
			formatter.putEntryValue(extraData.toString());
		}
	}
}
