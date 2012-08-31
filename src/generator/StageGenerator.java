package generator;

import generator.formatter.Formatter;
import generator.sorter.Sorter;

import java.util.ArrayList;
import java.util.List;

import model.Model;

import common.Configuration;
import common.EnduroTime;
import common.StageTime;

import driver.AbstractDriver;
import driver.StageDriver;

/**
 * A file generator for StageDrivers.
 */

public class StageGenerator extends AbstractGenerator {
	/**
	 * Instantiate an StageGenerator which uses the personFile-path supplied as
	 * argument.
	 * 
	 * @param sorter
	 *            The sorter to use for sorting the result.
	 */
	public StageGenerator(Sorter<StageDriver> sorter) {
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
	protected <D extends AbstractDriver> void computeResult(Model<D> model,
			Formatter formatter) {

		// "Linjal"
		// StartNr; Namn; Klubb; MC; Totaltid; #Etapper; Etapp1; Etapp2; Start1;
		// Mål1; Start2; Mål2
		formatter.beginHeader();
		if (Configuration.printPlacment) {
			formatter.putHeaderValue("Place");
		}
		// Add person headers.
		addPersonInfoHeader(model, formatter);

		int maxStages = computeMaxNbrStages(model);

		formatter.putHeaderValue("Totaltid").putHeaderValue("#Etapper");
		// EtappX, där X är ett naturligt tal i den definition där 0 inte ingår.
		for (int i = 1; i <= maxStages; i++) {
			formatter.putHeaderValue("Etapp" + i);
		}

		// Fill out with empty stages.
		formatter.fillEmptyHeaders(Configuration.maxStages - maxStages);

		// Headers only if you sort by startNr
		if (!Configuration.printPlacment) {
			// Start#; Mål#; ...
			for (int i = 1; i <= maxStages; i++) {
				formatter.putHeaderValue("Start" + i);
				formatter.putHeaderValue("Mål" + i);
			}
			// Fill out empty headers.
			formatter
					.fillEmptyHeaders((Configuration.maxStages - maxStages) * 2);
		}
		formatter.endHeader();

		@SuppressWarnings("unchecked")
		List<StageDriver> drivers = (List<StageDriver>) sorter.sort(model,
				Configuration.sortBy);

		int placeCounter = 1;
		for (StageDriver driver : drivers) {
			formatter.beginEntry();
			if (Configuration.printPlacment && driver.getStartTimes().size() == maxStages && driver.getFinishTimes().size() == maxStages) {
				formatter.putEntryValue(placeCounter++);
			}else if(Configuration.printPlacment){
				formatter.putEntryValue(" ");
			}

			formatter.putEntryValue(driver.getStartNumber());

			// Add person info.
			addPersonInfoValues(model, formatter, driver);
			
			if(driver.getStages().size()==0){
				formatter.putEntryValue("");
			}
			else{
				formatter.putEntryValue(driver.getTotalTime());
			}
			formatter.putHeaderValue(driver.getStages().size());

			// Print stages
			List<StageTime> stages = driver.getStages();
			int printedStages = 0;
			for (StageTime stage : stages) {
				formatter.putEntryValue(stage.getTotalString());
				printedStages++;
			}
			formatter.fillEmptyEntries(Configuration.maxStages - printedStages);

			// columns only if you sort by startNr
			if (!Configuration.printPlacment) {
				// Print start- and finishtimes
				printedStages = 0;
				for (StageTime stage : stages) {
					formatter.putEntryValue(stage.getStartTimeString());
					formatter.putEntryValue(stage.getFinishTimeString());
					printedStages++;
				}
				formatter
						.fillEmptyEntries((Configuration.maxStages - printedStages) * 2);
			}

			// Handles incorrect times
			// ArrayList<EnduroTime> start = driver.getStartTimes();
			// ArrayList<EnduroTime> finish = driver.getFinishTimes();
			StringBuilder sb = new StringBuilder();
			if (maxStages < driver.getFinishTimes().size()) {
				sb.append("Flera Sluttider ");
			}
			if (maxStages < driver.getStartTimes().size()) {
				sb.append("Flera StartTider ");
			}
			sb.append(driver.getFails());
			if (sb.length() > 0) {
				formatter.putEntryValue(sb.toString());
			}
			formatter.endEntry();
		}
	}

	/**
	 * Calculate the maximum number of stages that one driver has taken.
	 * 
	 * @param model
	 *            The model with the drivers.
	 * @return The maximum number of stages.
	 */
	private int computeMaxNbrStages(Model<? extends AbstractDriver> model) {
		int max = 0;
		for (AbstractDriver absDriver : model.getMap().values()) {
			StageDriver driver = (StageDriver) absDriver;
			if (driver.getStages().size() > max) {
				max = driver.getStages().size();

			}
		}

		return max;
	}
}
