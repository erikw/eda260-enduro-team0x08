package parser;

import java.io.FileNotFoundException;
import java.util.List;

import common.EnduroTime;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * A parser that reads finish time files.
 */
public class FinishParser extends AbstractParser {
	/**
	 * Instantiates a finish parser.
	 * 
	 * @param fileName
	 *            Name of the finish file to parse.
	 * @param driverFactory
	 *            A factory for making Drivers.
	 * @throws FileNotFoundException
	 *             If the provided file does not exist
	 */
	public FinishParser(String fileName,
			DriverFactory<? extends AbstractDriver> driverFactory)
			throws FileNotFoundException {
		super(fileName, driverFactory);
	}

	/**
	 * A method for adding finish times to a Driver
	 * 
	 * @param driver
	 *            the driver that you want to enter times for
	 * @param data
	 */
	protected void addDriverData(AbstractDriver driver, String[] data) {
		driver.setFinishTime(new EnduroTime(data[1]));
	}

	@Override
	protected void chopHeader(List<String[]> list) {
		//Finishfile have no header
	}
}
