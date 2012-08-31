package generator.formatter;

/**
 * An abstract class for formatting output text for the Enduro-program.
 * 
 */
public abstract class Formatter {

	/* String Builder to build the result with. */
	protected StringBuilder builder;

	/**
	 * Instantiates a formatter.
	 */
	protected Formatter() {
		builder = new StringBuilder();
	}

	/**
	 * Begins a header line.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter beginHeader();

	/**
	 * Puts an value inside a header. pre: beginHeader has been called.
	 * 
	 * @param value
	 *            The string value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter putHeaderValue(String value);

	/**
	 * Puts an value inside a header. pre: beginHeader has been called.
	 * 
	 * @param value
	 *            The integer value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter putHeaderValue(int value);

	/**
	 * Ends a header line. post: putValueHeader should not be called until a new
	 * beginHeader is used.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter endHeader();

	/**
	 * Begins a new entry.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter beginEntry();

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The string value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter putEntryValue(String value);

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The integer value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter putEntryValue(int value);

	/**
	 * Fills out the specified number of values with spaces.
	 * 
	 * @param numberOfEntries
	 *            How many entries that should be filled.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter fillEmptyEntries(int numberOfEntries);

	/**
	 * Fills out the specified number of values with spaces.
	 * 
	 * @param numberOfHeaders
	 *            How many headers that should be filled.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter fillEmptyHeaders(int numberOfHeaders);

	/**
	 * Ends an entry. post: putEntryValue should not be used until a beginEntry
	 * has been used.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter endEntry();

	/**
	 * Puts an complete class header that consists of one value only, namely
	 * value.
	 * 
	 * @param value
	 *            The header value to put.
	 * @return This object. The intended usage is method chaining.
	 */
	public abstract Formatter putClassHeader(String value);

	/**
	 * Returns the whole result. THis method will finalize the file so it should
	 * only be called once. And that is when the result building is complete.
	 * post: The formatted information is destroyed.
	 * 
	 * @return The formatted output as a text string.
	 */
	public abstract String toString();

	/**
	 * Get the file extension for the format. post: The formatting is done.
	 * Dispose this object because you can not use it for something with meaning
	 * now.
	 * 
	 * @return File Extension.
	 */
	public abstract String getFileExtension();
}
