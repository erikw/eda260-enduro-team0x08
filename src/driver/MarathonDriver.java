package driver;

import common.EnduroTime;

/**
 * A driver that can race so called "marathons".
 */

public class MarathonDriver extends AbstractDriver {

	/**
	 * Creates a driver with a start number.
	 * 
	 * @param startNumber
	 *            The start number of the driver.
	 */
	public MarathonDriver(int startNumber) {
		super(startNumber);
	}

	/**
	 * Get a string representation of all start times.
	 * 
	 * @return times A String with the start times.
	 */
	public String getStartTimeString() {
		if (startTimes.isEmpty()) {
			return EMPTY_VALUE;
		} else {
			String ret = "";
			for (EnduroTime time : startTimes) {
				ret += time.toString() + ",";
			}
			return ret.substring(0, ret.length() - 1);
		}
	}

	/**
	 * Get a string representation of all finish times.
	 * 
	 * @return times A String with the finish times.
	 */
	public String getFinishTimeString() {
		if (finishTimes.isEmpty()) {
			return EMPTY_VALUE;
		} else {
			String ret = "";
			for (EnduroTime time : finishTimes) {
				ret += time.toString() + ",";
			}
			return ret.substring(0, ret.length() - 1);
		}

	}

	/**
	 * Returns a string representation of the total time if there are exactly
	 * one start and one finish time. Otherwise dummy text will be given.
	 * 
	 * @return time A string of the total tile, or dummy text.
	 */
	public String getTotalTime() {
		if (startTimes.size() == 0 || finishTimes.size() == 0) {
			return EMPTY_VALUE;
		}
		return startTimes.get(0).compare(finishTimes.get(0)).toString();
	}

	/**
	 * A method for comparing two drivers total times
	 */
	public int compareTo(Object driver) {
		String totalTime1 = this.getTotalTime();
		String totalTime2 = ((MarathonDriver) driver).getTotalTime();
		if (totalTime1.equals(EMPTY_VALUE) && totalTime2.equals(EMPTY_VALUE)) {
			return 0;
		} else if (totalTime1.equals(EMPTY_VALUE)) {
			return 1;
		} else if (totalTime2.equals(EMPTY_VALUE)) {
			return -1;
		}
		EnduroTime endu1 = new EnduroTime(totalTime1);
		EnduroTime endu2 = new EnduroTime(totalTime2);
		return endu1.compareTo(endu2);
	}

}