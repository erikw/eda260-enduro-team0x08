package driver.factory;

import driver.AbstractDriver;

/**
 * 
 * An interface for a Driver Factory that can make different Drivers.
 * 
 */
public interface DriverFactory<D extends AbstractDriver> {

	/**
	 * A method for making a driver.
	 * 
	 * @param startNumber
	 *            the startNr you want the driver you are making to have.
	 * @return driver A new Driver.
	 */
	public D make(int startNumber);
}
