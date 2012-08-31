package parser;

import java.io.FileNotFoundException;
import java.util.List;

import common.EnduroTime;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * A start file parser.
 */
public class StartParser extends AbstractParser {
	/**
	 * Creates a parser for a start time file.
	 * 
	 * @param fileName
	 *            The file name of the file to parse.
	 */
	public StartParser(String fileName,
			DriverFactory<? extends AbstractDriver> driverFactor)
			throws FileNotFoundException {
		super(fileName, driverFactor);
	}

	/**
	 * A method for adding start times to a Driver
	 * 
	 * @param driver
	 *            the driver that you want to enter times for
	 * @param data
	 */
	protected void addDriverData(AbstractDriver driver, String[] data) {
		driver.setStartTime(new EnduroTime(data[1]));
	}

	@Override
	protected void chopHeader(List<String[]> list) {
		//Startfile have no header
	}
}
