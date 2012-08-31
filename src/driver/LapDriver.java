package driver;

import java.util.ArrayList;
import java.util.List;

import common.Configuration;
import common.EnduroTime;
import common.ErrorMessage;
import common.NotOpenException;

/**
 * A driver that can drive laps.
 */
public class LapDriver extends AbstractDriver {

	/*
	 * A list with starttimes and a list with laptimes. Index i is the
	 * laptime/laptimes of the ith lap. That is index i indicates the endtime of
	 * the it
	 */

	/**
	 * Instantiate a LapDriver with the supplied start number.
	 * 
	 * @param startNumber
	 *            The start number
	 */
	public LapDriver(int startNumber) {
		super(startNumber);
	}

	/**
	 * An method that puts an List with different lap times in a specific lap If
	 * the lap is empty
	 * 
	 * @param lap
	 *            The lap that you want to change.
	 * @param times
	 *            An ArayList with the times you want to put in that specific
	 *            lap.
	 * */
	public void putLapTime(int lap, EnduroTime times) {
		finishTimes.add(lap, times);
	}

	/**
	 * An method that returns the times for a specific lap.
	 * 
	 * @param lap
	 *            The lap that you want the times for.
	 * @return List of times An List with all the EnduroTimes for that lap.
	 */
	public EnduroTime getLapTimes(int lap) {
		return finishTimes.get(lap);
	}

	/**
	 * An method that returns the times in seconds for a specific lap in form of
	 * a String.
	 * 
	 * @return Returns a String with the total time in seconds as a string
	 */
	public String getTotalTime() {
		if (finishTimes.isEmpty() || startTimes.isEmpty()) {
			return "--.--.--";
		}
		return startTimes.get(0).compare(
				finishTimes.get(finishTimes.size() - 1)).toString();
	}

	/**
	 * A method that returns the numbers of laps.
	 * 
	 * @return Returns an int with the number of Laps for this Driver
	 */
	public int numberOfLaps() {
		return finishTimes.size();
	}

	/**
	 * an method for getting the finish times
	 * 
	 * @return Returns an List<EnduroTime> with all the laps finish times
	 */
	public ArrayList<EnduroTime> getFinishTimes() {
		return finishTimes;
	}

	/**
	 * A method for adding a start time.
	 * 
	 * @param enduro
	 *            The start time you want to add
	 * @return true if startTime, false otherwise. Mainly for test purposes.
	 */
	public boolean setStartTime(EnduroTime enduro) {
		EnduroTime diff = Configuration.timeWhenOpen.compare(enduro);
		if (diff.toSeconds() >= 0) {
			startTimes.add(enduro);
			return true;
		} else {
			ErrorMessage.error(new NotOpenException(),
					"Not open for registration, check back at "
							+ Configuration.timeWhenOpen.toString() + ".");
			return false;
		}
	}

	/**
	 * Compares nbr of laps and totalTimes
	 * 
	 * @param driver
	 *            , the object to be compared to this
	 */
	public int compareTo(Object driver) {
		if (this.numberOfLaps() - ((LapDriver) driver).numberOfLaps() != 0) {
			return ((LapDriver) driver).numberOfLaps() - this.numberOfLaps();
		}
		String totalTime1 = this.getTotalTime();
		String totalTime2 = ((LapDriver) driver).getTotalTime();
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
