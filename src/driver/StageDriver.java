package driver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.EnduroTime;
import common.StageTime;

/**
 * A driver that can drive a stage race.
 */
public class StageDriver extends AbstractDriver {

	/* The stage times. */
	private List<StageTime> stages;

	private String fails;

	/**
	 * Instantiate a StageDriver with the supplied start number and the number
	 * of stages the configuration file decides
	 * 
	 * @param startNbr
	 *            The start number
	 */
	public StageDriver(int startNbr) {
		super(startNbr);
		stages = new ArrayList<StageTime>();
		fails = "";
	}

	/**
	 * An method that returns the Stagetimes in a list.
	 * 
	 * @return Returns an ArrayList<StageTime> with stadge times
	 */
	public List<StageTime> getStages() {
		generateStageTimes();
		return stages;
	}

	/**
	 * A method for getting the total Time
	 * 
	 * @return Returns an EnduroTime with the total time. (all the stageTimes
	 *         combined)
	 */
	public EnduroTime getTotal() {
		generateStageTimes();
		EnduroTime total = new EnduroTime("00.00.00");
		for (StageTime s : stages) {
			total.add(s.getTotal());
		}
		return total;
	}

	/**
	 * A method that returns the total Time as a String
	 * 
	 * @return Returns a String with total time as a string
	 */
	public String getTotalTime() {
		return getTotal().toString();
	}

	/**
	 * Compares stages and total times
	 * 
	 * @param rhs
	 *            The StageDriver to compare with.
	 * @return 1 , 0 , -1
	 */
	public int compareTo(Object rhs) {
		generateStageTimes();
		StageDriver other = (StageDriver) rhs;
		int diff = other.getStages().size() - stages.size();
		if (diff == 0) {
			// Same amount of stages, compare total time.
			return getTotal().compareTo(other.getTotal());
		} else {
			// Differnet amout of stages
			return diff;
		}
	}

	/**
	 * Generates stage times from startTimes and finishTimes.
	 */
	private void generateStageTimes() { // Dennna metoden är jätte ofärdig!
		Collections.sort(startTimes);
		Collections.sort(finishTimes);
		int size = finishTimes.size();
		stages.clear();
		if (startTimes.size() < finishTimes.size()) {
			size = startTimes.size();
		}

		if (startTimes.size() == finishTimes.size()) {
			for (int g = 0; g < startTimes.size(); g++) {
				StageTime nTime = new StageTime(startTimes.get(g), finishTimes
						.get(g));
				stages.add(nTime);
			}
		} else {
			for (int j = 0; j < size; j++) {
				if (startTimes.get(j).compareTo(finishTimes.get(j)) < 0) {
					stages.add(new StageTime(startTimes.get(j), finishTimes
							.get(j)));
				} else if (startTimes.get(j).compareTo(finishTimes.get(j + 1)) < 0
						&& j < size) {
					stages.add(new StageTime(startTimes.get(j), finishTimes
							.get(j + 1)));
					setFinishFail("Etapp" + j + " " + finishTimes.get(j));
				} else {
					setFinishFail("Etapp" + j + " " + finishTimes.get(j));
				}
			}

		}
	}

	private void setFinishFail(String string) {
		StringBuilder sb = new StringBuilder();
		sb.append(string + " ");
		fails = sb.toString();

	}

	public String getFails() {
		return fails.trim();
	}

}