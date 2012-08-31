package generator.formatter;

/**
 * A formatter that formats a CSV file.
 * 
 */
public class CSVFormatter extends Formatter {

	/**
	 * Instantiate a CSV formatter.
	 */
	public CSVFormatter() {
		super();
	}

	/**
	 * Begins a header line.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter beginHeader() {
		return this;
	}

	/**
	 * Puts an value inside a header. pre: beginHeader has been called.
	 * 
	 * @param value
	 *            The string value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putHeaderValue(String value) {
		if (!firstValue()) {
			builder.append("; ");
		}
		builder.append(value);
		return this;
	}

	/**
	 * Puts an value inside a header. pre: beginHeader has been called.
	 * 
	 * @param value
	 *            The integer value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putHeaderValue(int value) {
		return putHeaderValue(String.valueOf(value));
	}

	/**
	 * Ends a header line. post: putValueHeader should not be called until a new
	 * beginHeader is used.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter endHeader() {
		builder.append('\n');
		return this;
	}

	/**
	 * Begins a new entry.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter beginEntry() {
		return this;
	}

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The string value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putEntryValue(String value) {
		// Implementation does not differ for a CSV file.
		return putHeaderValue(value);
	}

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The integer value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putEntryValue(int value) {
		// Implementation does not differ for a CSV file.
		return putEntryValue(String.valueOf(value));
	}

	/**
	 * Fills out the specified number of values with spaces.
	 * 
	 * @param numberOfEntries
	 *            How many entries that should be filled.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter fillEmptyEntries(int numberOfEntries) {
		if (numberOfEntries > 0) {
			if (firstValue()) {
				for (int i = 0; i < numberOfEntries; ++i) {
					builder.append("; ");
				}
			} else {
				// End last value with comma.
				builder.append("; ");
				for (int i = 0; i < numberOfEntries - 1; ++i) {
					builder.append("; ");
				}
			}
		}
		return this;
	}

	/**
	 * Fills out the specified number of values with spaces.
	 * 
	 * @param numberOfHeaders
	 *            How many headers that should be filled.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter fillEmptyHeaders(int numberOfHeaders) {
		return fillEmptyEntries(numberOfHeaders);
	}

	/**
	 * Ends an entry. post: putEntryValue should not be used until a beginEntry
	 * has been used.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter endEntry() {
		return endHeader();
	}

	/**
	 * Puts an complete class header that consists of one value only, namely
	 * value.
	 * 
	 * @param value
	 *            The header value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putClassHeader(String value) {
		builder.append(value).append('\n');
		return this;
	}

	/**
	 * Returns the whole result. THis method will finalize the file so it should
	 * only be called once. And that is when the result building is complete.
	 * post: The formatted information is destroyed.
	 * 
	 * @return The formatted output as a text string.
	 */
	public String toString() {
		String result = builder.toString();
		builder = new StringBuilder();
		return result;
	}

	/**
	 * Get the file extension for the format. post: The formatting is done.
	 * Dispose this object because you can not use it for something with meaning
	 * now.
	 * 
	 * @return File Extension.
	 */
	public String getFileExtension() {
		return "txt";
	}

	/**
	 * Determine if this is the first of a series of values.
	 * 
	 * @return true if this is the first, false otherwise.
	 */
	private boolean firstValue() {
		return builder.length() == 0
				|| builder.charAt(builder.length() - 1) == '\n';
	}
}
