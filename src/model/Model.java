package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import common.Configuration;
import common.EnduroTime;

import driver.AbstractDriver;

/**
 * An abstract model for drivers.
 * 
 * @param <D>
 *            The driver type parameter.
 */
public class Model<D extends AbstractDriver> extends Observable {
	/* The map for storing the drivers. */
	protected HashMap<Integer, D> drivers;

	// TODO: Make conf-file work, will cause too many files open if inited
	// below.

	/* A data structure to store drivers wit no start number. */
	private HashMap<Integer, D> unregisteredDrivers;

	/* An array of columns found in the person files header. */
	private String[] columns;

	/**
	 * Instantiate the abstract part of the model.
	 * 
	 * @param map
	 *            The map of drivers to use.
	 */
	public Model(HashMap<Integer, D> map) {
		this.drivers = map;
		// Defaults to no columns.
		this.columns = new String[0];
		unregisteredDrivers = new HashMap<Integer, D>();
	}

	/**
	 * Instantiate the abstract part of the model.
	 */
	public Model() {
		this(new HashMap<Integer, D>());
	}

	/**
	 * Put a driver to the model.
	 * 
	 * @param startnummer
	 *            The start number of the driver.
	 * @param driver
	 *            The driver to put.
	 */

	public <T extends AbstractDriver> void put(int startnummer, T driver) {
		drivers.put(startnummer, (D) driver);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Puts all the content of the supplied map to this model.
	 * 
	 * @param otherMap
	 *            The data to add to the model.
	 */
	public void openFromMap(HashMap<Integer, D> otherMap) {
		drivers.putAll(otherMap);
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns a driver with key i
	 * 
	 * @param startNbr
	 *            The start number to look for.
	 * @return D The driver with the key startNbr.
	 */
	public D getDriver(int startNbr) {
		return drivers.get(startNbr);
	}

	/**
	 * Get the map of drivers.
	 * 
	 * @return A HashMap of Drivers.
	 */
	public HashMap<Integer, D> getMap() {
		return drivers;
	}

	/**
	 * Get the map of unregistered drivers.
	 * 
	 * @return A HashMap of unregistered drivers.
	 */
	public HashMap<Integer, D> getUnregisteredMap() {
		return unregisteredDrivers;
	}

	/**
	 * Adds a start time to driver with key start number that is supplied.
	 * 
	 * @param startNbr
	 *            The start number associated with the desired driver to change.
	 * @param time
	 *            The start tie to put.
	 */
	public void putStart(int startNbr, String time) {
		drivers.get(startNbr).setStartTime(new EnduroTime(time));
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Adds a finish time to driver with key start number supplied.
	 * 
	 * @param startNbr
	 *            The start number of the driver to change.
	 * @param time
	 *            The finish time to put.
	 */
	public void putFinish(int startNbr, String time) {
		drivers.get(startNbr).setFinishTime(new EnduroTime(time));
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Makes a string with the "results" for the driver with the start number
	 * supplied.
	 * 
	 * @param startNbr
	 *            The start number of the driver to use.
	 * @return returns a String with the "results"
	 */
	// TODO: En modell som beräknar saker? icke! En modell håller i data som
	// andra plockar ut och gör saker med. Bort med den här till något ställe
	// där den hör hemma. SRP-principen. SRP > ALP i detta fallet.
	public String getResult(int startNbr) {
		D driver = drivers.get(startNbr);
		return driver.getStartNumber() + ","
				+ driver.getStartTimes().toString() + ","
				+ driver.getFinishTimes().toString();
	}

	/**
	 * Generates a mass start. All drivers will get the same start time.
	 * 
	 * @param startTime
	 *            The start time to give to everyone in this model.
	 */
	public void generateMassStart(String startTime) {
		for (D driver : drivers.values()) {
			driver.setStartTime(new EnduroTime(startTime));
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Generates a mass start. All drivers with start number between first and
	 * last get the same start time.
	 * 
	 * @param first
	 *            Start number of the lower bound.
	 * @param last
	 *            Start number of the upper bound.
	 * @param startTime
	 *            The start time to give the users.
	 */
	public void generateMassStartWave(int first, int last, String startTime) {
		for (int i = first; i <= last; i++) {
			if (drivers.containsKey(i)) {
				drivers.get(i).setStartTime(new EnduroTime(startTime));
			} else {
				// TODO:Fix masstart with conf-file.
				// DriverFactory<D> df = (DriverFactory<D>) conf.factory;
				// map.put(i, df.make(i));
				// map.get(i).setStartTime(new EnduroTime(startTime));
			}
		}
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Returns a String vectorprivate ArrayList<AbstractDriver>
	 * unregisteredDrivers; with the column names
	 * 
	 * @return Returns a String vector with the column names
	 */
	public String[] getColumnNames() {
		return columns;
	}

	/**
	 * A method for setting the column names
	 * 
	 * @param columns
	 *            , a String vector with the names
	 */
	public void setColumnNames(String[] columns) {
		this.columns = columns;
	}

	/**
	 * Returns a List<String> with the class names
	 * 
	 * @return Returns a List<String> with the class names
	 */
	public List<String> getClassNames() {
		List<String> list = new ArrayList<String>();
		for (D driver : drivers.values()) {
			String driverClass = driver.getClassName();
			if (!list.contains(driverClass)
					&& !driver.getClassName().equals(""))
				list.add(driverClass);
		}
		return list;
	}

	public <T extends AbstractDriver> void putUnregisteredDriver(T driver) {
		unregisteredDrivers.put(driver.getStartNumber(), (D) driver);
	}

	@SuppressWarnings("unchecked")
	public void putUnregisteredDrivers(
			HashMap<Integer, ? extends AbstractDriver> unRegs) {
		unregisteredDrivers
				.putAll((Map<? extends Integer, ? extends D>) unRegs);
	}

	/**
	 * Get a subset of this model with all the drivers that belongs to the
	 * specified driver class.
	 * 
	 * @param className
	 *            The class name to filter on.
	 * @return A subset model with only drivers from the specified class.
	 */
	public Model<? extends AbstractDriver> subset(String className) {
		Model<D> subset = new Model<D>();
		for (AbstractDriver driver : drivers.values()) {
			if (driver.getClassName().equals(className)) {
				subset.put(driver.getStartNumber(), driver);
			}
		}
		subset.setColumnNames(columns);
		return subset;
	}

	/**
	 * Get a model with drivers that are not registered.
	 * 
	 * @return A subset model with only drivers with no start number.
	 */
	public Model<? extends AbstractDriver> subsetUnregistered() {
		Model<D> subset = new Model<D>();
		for (AbstractDriver driver : unregisteredDrivers.values()) {
			subset.put(driver.getStartNumber(), driver);
		}
		subset.setColumnNames(columns);
		return subset;
	}

	/**
	 * Changes the start number of a driver
	 * 
	 * @param stNrold
	 *            old startnumber
	 * @param stNr
	 *            new start number
	 * @return TODO
	 */
	public boolean change(int stNrold, int stNr) {
		D oldDriver = drivers.get(stNrold);
		if (oldDriver == null) {
			return false;
		}
		D newDriver = drivers.get(stNr);
		if (newDriver == null) {
			oldDriver.setStartNumber(stNr);
			drivers.put(stNr, oldDriver);
		} else { // merge drivers
			newDriver.setStartTimes(oldDriver.getStartTimes());
			newDriver.setFinishTimes(oldDriver.getFinishTimes());
			newDriver.addAllInfo(oldDriver.getInfo());
		}
		drivers.remove(stNrold);
		this.setChanged();
		this.notifyObservers();
		return true;
	}

	/**
	 * Moves a start time for old driver to new driver
	 * 
	 * @param stNrold
	 *            driver with the time
	 * @param stNr
	 *            the driver that should have the time
	 * @param enduroTime
	 *            the time to move
	 * @return true if success
	 */
	@SuppressWarnings("unchecked")
	public boolean moveTimeStart(int stNrold, int stNr, EnduroTime enduroTime) {
		boolean success = false;
		D oldDriver = drivers.get(stNrold);
		if (oldDriver != null && oldDriver.removeStartTime(enduroTime)) {
			D newDriver = drivers.get(stNr);
			if (newDriver == null) {
				newDriver = (D) (Configuration.factory).make(stNr);
				drivers.put(stNr, newDriver);
			}
			newDriver.setStartTime(enduroTime);
			this.setChanged();
			this.notifyObservers();
			success = true;
		}
		return success;
	}

	/**
	 * Moves a finish time for old driver to new driver
	 * 
	 * @param stNrold
	 *            driver with the time
	 * @param stNr
	 *            the driver that should have the time
	 * @param enduroTime
	 *            the time to move
	 * @return true if success
	 */
	@SuppressWarnings("unchecked")
	public boolean moveTimeFinish(int stNrold, int stNr, EnduroTime enduroTime) {
		D oldDriver = drivers.get(stNrold);
		if (oldDriver == null) {
			return false;
		}
		if (!oldDriver.removeFinishTime(enduroTime)) {
			return false;
		}
		D newDriver = drivers.get(stNr);
		if (newDriver == null) {
			newDriver = (D) (Configuration.factory).make(stNr);
			drivers.put(stNr, newDriver);
		}
		newDriver.setFinishTime(enduroTime);
		this.setChanged();
		this.notifyObservers();
		return true;
	}

	/**
	 * Determine if the model contains any drivers.
	 * 
	 * @return true if the model contains drivers, false otherwse.
	 */
	public boolean isEmpty() {
		return drivers.isEmpty();
	}

}
