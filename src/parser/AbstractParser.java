package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import model.Model;
import au.com.bytecode.opencsv.CSVReader;
import driver.AbstractDriver;
import driver.factory.DriverFactory;

/**
 * An abstract parser that reads for example start files or person files.
 */
public abstract class AbstractParser {
	/* A CSV reader. */
	protected CSVReader reader;

	/* Factory for making Drivers. */
	private DriverFactory<? extends AbstractDriver> driverFactory;

	/* Column names that are found in headers. */
	protected String[] columns;

	protected String currentClassName;

	/**
	 * Instantiate the abstract part of a parser.
	 * 
	 * @param fileName
	 *            The name of the file to parse.
	 * @param driverFactory
	 *            A factory for making Drivers.
	 * @throws FileNotFoundException
	 */
	public AbstractParser(String fileName,
			DriverFactory<? extends AbstractDriver> driverFactory)
			throws FileNotFoundException {
		reader = new CSVReader(new FileReader(fileName), ';');
		this.driverFactory = driverFactory;
		this.columns = new String[0];
		this.currentClassName = "";

	}

	/**
	 * Parses the file, and enters the times in the Map.
	 * 
	 * @param model
	 *            The map to put found drivers in.
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public <D extends AbstractDriver> void parse(Model<D> model)
			throws IOException {
		List<String[]> allRows = reader.readAll();
		trimArray(allRows);
		chopHeader(allRows);

		for (String[] row : allRows) {
			// If only one column its class name
			if (row.length == 1) {
				currentClassName = row[0];
			} else {
				// TODO: Handle none numeric
				int key = Integer.parseInt(row[0]);

				// Get driver, if no luck, create one!
				D driver = model.getDriver(key);
				if (driver == null) {
					driver = (D) driverFactory.make(key);
					model.put(key, driver);
				}
				// Append data using template method
				addDriverData(driver, row);

			}
		}

		// Add columns to model for later use
		if (columns.length > 0) {
			model.setColumnNames(columns);
		}

		reader.close();
	}

	/**
	 * Remove space characters at the beginning and end of the supplied array of
	 * strings. Needed because CSV-parse don't remove spaces.
	 * 
	 * @param list
	 *            The subject list to be trimmed.
	 */
	private void trimArray(List<String[]> list) {
		for (String[] current : list) {
			for (int i = 0; i < current.length; ++i) {
				current[i] = current[i].trim();
			}
		}
	}

	/**
	 * Add driver data found.
	 * 
	 * @param driver
	 *            The driver to use.
	 * @param data
	 *            The data to add.
	 */
	protected abstract void addDriverData(AbstractDriver driver, String[] data);

	/**
	 * Extract the first line that is the header. Empty implementation, free to
	 * override. Currently only used by the PersonParser.
	 * 
	 * @param list
	 *            Raw list of parsed data.
	 */
	protected abstract void chopHeader(List<String[]> list);
}
