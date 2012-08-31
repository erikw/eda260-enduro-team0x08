package driver;

import java.util.ArrayList;
import java.util.List;

import common.EnduroTime;

/**
 * Describes an abstract driver of an Enduro race.
 * 
 */
public abstract class AbstractDriver implements Comparable<Object> {

	/* The drivers start number. */
	protected int startNumber;

	/* The name of the class that this driver is in. */
	private String className;

	/* A variable length list of additional user data. */
	protected List<String> pInfo = new ArrayList<String>();

	/* No value constant. */
	protected static final String EMPTY_VALUE = "--.--.--";

	/* List of start times. */
	protected ArrayList<EnduroTime> startTimes;

	/* List of finish times. */
	protected ArrayList<EnduroTime> finishTimes;

	/**
	 * Constructor for AbstractDriver, a driver with personal-info and
	 * startNumber.
	 * 
	 * @param startNumber
	 *            The start number of this driver.
	 */
	public AbstractDriver(int startNumber) {
		this.startNumber = startNumber;
		startTimes = new ArrayList<EnduroTime>();
		finishTimes = new ArrayList<EnduroTime>();
		className = "";
	}

	/**
	 * Get the start number.
	 * 
	 * @return returns an integer with the startNumber
	 */
	public int getStartNumber() {
		return startNumber;
	}

	/**
	 * Set the class for this driver.
	 * 
	 * @param className
	 *            The name of the class.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Get the name of the class.
	 * 
	 * @return The class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Adds a personal info.
	 * 
	 * @param data
	 *            , the name you want to enter
	 */
	public void addInfo(String data) {
		if (!pInfo.contains(data)) {
			pInfo.add(data);
		}
	}

	/**
	 * Adds list of personal info.
	 * 
	 * @param data
	 *            , the name you want to enter
	 */
	public void addAllInfo(List<String> list) {
	for(String data : list){
		addInfo(data);
	}
	}

	
	/**
	 * Gets the drivers info
	 * 
	 * @return Returns an ArrayList with Strings, the name of the driver and
	 *         other info
	 */
	public List<String> getInfo() {
		return pInfo;
	}

	/**
	 * Compares two Drivers totaltimes
	 * 
	 * @param driver
	 *            the other driver
	 * @return returns an Interger with the time differance between the
	 *         driver(in seconds)
	 */
	public abstract int compareTo(Object driver);

	/**
	 * 
	 * @return Returns a String with the total time
	 */
	public abstract String getTotalTime();

	/**
	 * Gets a list with the start times
	 * 
	 * @return list The ilist of start times.
	 */
	public ArrayList<EnduroTime> getStartTimes() {
		return startTimes;
	}

	/**
	 * Gets a list with the finish times
	 * 
	 * @return list The ilist of finish times.
	 */
	public ArrayList<EnduroTime> getFinishTimes() {
		return finishTimes;
	}

	/**
	 * A method for adding a start time
	 * 
	 * @param enduro
	 *            The start time that you want to add
	 */
	public boolean setStartTime(EnduroTime enduro) {
		return startTimes.add(enduro);
	}

	/**
	 * A method for adding a list with start times.
	 * 
	 * @param enduros
	 *            The start times that you want to add
	 */
	public void setStartTimes(List<EnduroTime> enduros) {
		startTimes.addAll(enduros);
	}

	/**
	 * an method for adding a list with finish times
	 * 
	 * @param enduros
	 *            The finish times that you want to add
	 */
	public void setFinishTimes(List<EnduroTime> enduros) {
		finishTimes.addAll(enduros);

	}

	/**
	 * A method for adding a finish time
	 * 
	 * @param enduro
	 *            The finish time that you want to add
	 */
	public void setFinishTime(EnduroTime enduro) {
		finishTimes.add(enduro);
	}

	/**
	 * Changes a drivers startnumber
	 * 
	 * @param startNumber
	 *            new startnumber
	 */
	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	/**
	 * Removes a given starttime
	 * 
	 * @param enduroTime
	 *            time to remove
	 * @return true if success.
	 */
	public boolean removeStartTime(EnduroTime enduroTime) {
		return startTimes.remove(enduroTime);
	}

	/**
	 * Removes a given finishtime
	 * 
	 * @param enduroTime
	 *            time to remove
	 * @return true if success.
	 */
	public boolean removeFinishTime(EnduroTime enduroTime) {
		return finishTimes.remove(enduroTime);
	}

}