package driver.factory;

import driver.LapDriver;

/**
 * 
 * A factory for making LapDrivers
 *
 */
public class LapFactory implements DriverFactory<LapDriver> {

	/**
	 * A method for making a Lap Driver.
	 * 
	 * @param startNumber
	 *            the startNr you want the driver you are making to have.
	 * @return driver A new Lap Driver.
	 */
	public LapDriver make(int startNumber) {
		return new LapDriver(startNumber);
	}

}
