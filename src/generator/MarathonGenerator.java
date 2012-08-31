package generator;

import generator.formatter.Formatter;
import generator.sorter.Sorter;

import java.util.Comparator;
import java.util.List;

import model.Model;

import common.Configuration;
import common.EnduroTime;

import driver.AbstractDriver;
import driver.MarathonDriver;

/**
 * A file generator for MarathonDrivers.
 */

public class MarathonGenerator extends AbstractGenerator {

	/**
	 * Instantiate an MarathonGenerator which uses the personFile-path supplied
	 * as argument.
	 * 
	 * @param personFile
	 *            the personFile-path.
	 * @param sorter
	 *            The sorter to use for sorting the result.
	 */
	public MarathonGenerator(String personFile,
			Sorter<? extends AbstractDriver> sorter) {
		super(sorter);
	}

	/**
	 * Generates a string that the generateResult method uses
	 * 
	 * @param model
	 *            The model to use.
	 * @param formatter
	 *            A formatter for formatting the values.
	 */
	@SuppressWarnings("unchecked")
	protected <D extends AbstractDriver> void computeResult(Model<D> model,
			Formatter formatter) {
		// "Linjal"
		// <Place;> StartNr; Namn;..;...; Totaltid; Starttid; Måltid
		formatter.beginHeader();
		if (Configuration.printPlacment) {
			formatter.putHeaderValue("Place");
		}

		// Add person headers.
		addPersonInfoHeader(model, formatter);
		formatter.putHeaderValue("Totaltid").putHeaderValue("Starttid")
				.putHeaderValue("Måltid").endHeader();

		int placeCounter = 1;

		List<MarathonDriver> drivers = (List<MarathonDriver>) sorter.sort(
				model,
				(Comparator<? super AbstractDriver>) Configuration.sortBy);
		for (MarathonDriver driver : drivers) {
			formatter.beginEntry();
			if (Configuration.printPlacment) {
				formatter.putEntryValue(placeCounter++);
			}

			formatter.putEntryValue(driver.getStartNumber());

			// Add person info.
			addPersonInfoValues(model, formatter, driver);

			formatter.putEntryValue(driver.getTotalTime());

			// Error Handling
			if (driver.getStartTimes().isEmpty()) {
				formatter.putEntryValue("Start?");
			} else {
				formatter.putEntryValue(driver.getStartTimes().get(0)
						.toString());
			}

			if (driver.getFinishTimes().isEmpty()) {
				formatter.putEntryValue("Slut?");
			} else {
				formatter.putEntryValue(driver.getFinishTimes().get(0)
						.toString());

			}
			// Hanterar flera starttider
			manyStartTimes(formatter, driver);

			StringBuilder extraData = new StringBuilder();
			// Hanterar flera måltider
			if (driver.getFinishTimes().size() > 1) {
				extraData.append("Flera måltider? ");
				for (int i = 1; i < driver.getFinishTimes().size(); i++) {
					extraData.append(driver.getFinishTimes().get(i));
				}
				formatter.putEntryValue(extraData.toString());
			}

			// Kollar om totaltiden är giltig ( tillräckligt lång )
			EnduroTime enduro = new EnduroTime(driver.getTotalTime());
			if (enduro.compareTo(Configuration.minTime) < 0
					&& !driver.getTotalTime().equals(EMPTY_VALUE)) {
				formatter.putEntryValue("Omöjlig Totaltid?");
			}

			formatter.endEntry();
		}

	}
}