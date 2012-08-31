package generator;

import generator.formatter.Formatter;
import generator.sorter.Sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Model;

import common.Configuration;
import common.EnduroTime;

import driver.AbstractDriver;
import driver.LapDriver;

/**
 * A file generator for LapDrivers.
 */
public class LapGenerator extends AbstractGenerator {

	/**
	 * Instantiate an LapGenerator which uses the personFile-path supplied as
	 * argument.
	 * 
	 * @param sorter
	 *            The sorter to use for sorting the result.
	 */
	public LapGenerator(Sorter<? extends AbstractDriver> sorter) {
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
	protected <D extends AbstractDriver> void computeResult(
			Model<D> model, Formatter formatter) {
		// "linjal"
		// StartNr; Namn; #Varv; TotalTid; Varv1; Varv2; Varv3; Start;
		// Varvning1; Varvning2; Mål
		if (Configuration.printPlacment) {
			formatter.beginHeader().putHeaderValue("Place");
		}
		// Add person headers.
		addPersonInfoHeader(model, formatter);
		formatter.putHeaderValue("#Varv").putHeaderValue("TotalTid");

		int maxLaps = computeMaxNbrRealLaps(model);

		// Varvtider.
		for (int i = 1; i <= maxLaps; i++) {
			formatter.putHeaderValue("Varv" + i);
		}

		//Headers only if you sort by startNr
		if (!Configuration.printPlacment) {
			formatter.putHeaderValue("Start");

			// Varvningstider.
			for (int i = 1; i < maxLaps; i++) {
				formatter.putHeaderValue("Varvning" + i);
			}

			formatter.putHeaderValue("Mål");
		}
		formatter.endHeader();

		List<LapDriver> drivers = (List<LapDriver>) sorter.sort(model, (Comparator<? super AbstractDriver>) Configuration.sortBy);
		int placeCounter = 1;
		for (LapDriver driver : drivers) {
			formatter.beginEntry();
			if (Configuration.printPlacment) {
				formatter.putEntryValue(placeCounter++);
			}
			formatter.putEntryValue(driver.getStartNumber());

			// Add person info.
			addPersonInfoValues(model, formatter, driver);

			ArrayList<EnduroTime> startTimes = driver.getStartTimes();
			ArrayList<EnduroTime> lapTimes = driver.getFinishTimes();
			Collections.sort(lapTimes);

			// #varv.
			if (lapTimes.isEmpty()) {
				formatter.putEntryValue(0);
			} else {
				formatter.putEntryValue(lapTimes.size());
			}

			// Varvtider.
			if (startTimes.size() > 0 && lapTimes.size() > 0) {

				// Totaltid.
				EnduroTime start = startTimes.get(0);
				EnduroTime finish = lapTimes.get(lapTimes.size() - 1);
				formatter.putEntryValue(start.compare(finish).toString());

				// Varvtid nr.1
				EnduroTime startTime = startTimes.get(0);
				EnduroTime firstLapTime = lapTimes.get(0);
				formatter.putEntryValue(startTime.compare(firstLapTime)
						.toString());
			} else if ((startTimes.size() == 0 && lapTimes.size() == 0)) {
				// Totaltid
				formatter.putHeaderValue(EMPTY_VALUE);

			} else {
				// Totaltid
				formatter.putHeaderValue(EMPTY_VALUE);
				// Varvtid nr. 1.
				formatter.fillEmptyEntries(1);
			}

			// Varvtid, nr. x, x > 1.
			EnduroTime enduro;
			Boolean uncorrectLapTime = false;
			int printedLaps = 0;
			for (; printedLaps < lapTimes.size() - 1; ++printedLaps) {
				EnduroTime current = lapTimes.get(printedLaps);
				EnduroTime next = lapTimes.get(printedLaps + 1);
				enduro = current.compare(next);
				if (enduro.compareTo(Configuration.minTime) < 0) {
					uncorrectLapTime = true;

				}
				formatter.putEntryValue(enduro.toString());
			}
			// Fyller ut med tomma entries.
			formatter.fillEmptyEntries((maxLaps - 1) - printedLaps);

			//Columns only if you sort by startNr
			if (!Configuration.printPlacment) {
				// Lägg till starttid.
				if (startTimes.size() > 0) {
					formatter.putEntryValue(startTimes.get(0).toString());
				} else {
					formatter.putEntryValue("Start?");
					// formatter.fillEmptyEntries(1);
				}

				int printedVarvningar = 0;
				// Varvningstider.
				for (; printedVarvningar < lapTimes.size() - 1; printedVarvningar++) {
					formatter.putEntryValue(lapTimes.get(printedVarvningar)
							.toString());
				}

				// Måltid
				if (lapTimes.size() > 0) {
					formatter.putEntryValue(lapTimes.get(lapTimes.size() - 1)
							.toString());
				} else {
					formatter.putEntryValue("Slut?");
					// formatter.fillEmptyEntries(1);
				}

				// Fyller ut tomma entries.
				formatter.fillEmptyEntries((maxLaps - 1) - printedVarvningar);
			}
			// Hanterar flera starttider
			manyStartTimes(formatter, driver);

			// Kollar om totaltiden är giltig ( tillräckligt lång )
			if (uncorrectLapTime == true) {
				formatter.putEntryValue("Omöjlig varvtid?");
			}
			formatter.endEntry();
		}
	}

	/**
	 * Calculate the maximum number of laps the one driver has taken.
	 * 
	 * @param model
	 *            The model where the drivers are.
	 * @return The maximum number of laps taken.
	 */
	private int computeMaxNbrRealLaps(Model<? extends AbstractDriver> model) {
		int max = 0;
		for (AbstractDriver absDriver : model.getMap().values()) {
			LapDriver driver = (LapDriver) absDriver;
			if (driver.numberOfLaps() > max) {
				max = driver.numberOfLaps();
			}
		}
		return max;
	}
}
