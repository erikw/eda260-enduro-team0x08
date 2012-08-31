package parser;

import java.io.FileNotFoundException;
import java.util.List;

import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * A parser that reads person files.
 */
public class PersonParser extends AbstractParser {

	/**
	 * Instantiate a person parser.
	 * 
	 * @param fileName
	 *            The name of the file to parse.
	 * @throws FileNotFoundException
	 */
	public PersonParser(String fileName,
			DriverFactory<? extends AbstractDriver> driverFactory)
			throws FileNotFoundException {
		super(fileName, driverFactory);
	}

	/**
	 * Add driver data found.
	 * 
	 * @param driver
	 *            The driver to use.
	 * @param data
	 *            The data to add.
	 */
	public void addDriverData(AbstractDriver driver, String[] data) {
		// Add class name if exists.
		if (currentClassName.length() > 0){
			driver.setClassName(currentClassName);
		}
		// First is startNo
		for (int i = 1; i < data.length; i++){
			driver.addInfo(data[i]);
		}
	}

	/**
	 * Extract the first line that is the header.
	 * 
	 * @param list
	 *            Raw list of parsed data.
	 */
	public void chopHeader(List<String[]> list) {
		columns = list.remove(0);
	}
}
