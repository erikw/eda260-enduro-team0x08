package driver.factory;

import driver.StageDriver;

/**
 * 
 * A factory for making StageDrivers
 *
 */
public class StageFactory implements DriverFactory<StageDriver> {

	/**
	 * A method for making a Stage Driver.
	 * 
	 * @param startNumber
	 *            the startNr you want the driver you are making to have.
	 * @return driver A new Stage Driver.
	 */
	public StageDriver make(int startNumber) {
		return new StageDriver(startNumber);
	}

}
