package driver.factory;

import driver.MarathonDriver;

/**
 * 
 * A factory for making MarathonDrivers
 *
 */
public class MarathonFactory implements DriverFactory<MarathonDriver> {

	/**
	 * A method for making a  Marathon Driver.
	 * 
	 * @param startNumber
	 *            the startNr you want the driver you are making to have.
	 * @return driver A new Marathon Driver.
	 */
	public MarathonDriver make(int startNumber) {
		return new MarathonDriver(startNumber);
	}

}
