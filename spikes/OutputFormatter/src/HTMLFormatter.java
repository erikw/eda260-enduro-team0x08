/**
 * A formatter that formats a HTML file.
 */
public class HTMLFormatter extends Formatter {

	/**
	 * Instantiate a HTML formatter.
	 */
	public HTMLFormatter() {
		super();
		builder.append("<html>").append("<head><title>")
				.append("Enduro resultat").append("</title></head>")
				.append("</head>").append("<body>");
		builder.append("<table>");
	}

	/**
	 * Begins a header line.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter beginHeader() {
		builder.append("<tr>");
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
		builder.append("<th>").append(value).append("</th>");
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
		builder.append("</tr>");
		return this;
	}

	/**
	 * Begins a new entry.
	 * 
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter beginEntry() {
		return beginHeader();
	}

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The string value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putEntryValue(String value) {
		builder.append("<td>").append(value).append("</td>");
		return this;
	}

	/**
	 * Puts an entry value inside an entry. pre: beginEntry has been called.
	 * 
	 * @param value
	 *            The integer value to add.
	 * @return This object. The intended usage is method chaining.
	 */
	public Formatter putEntryValue(int value) {
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
		for (int i = 0; i < numberOfEntries; ++i) {
			builder.append("<td></td>");
		}
		return this;
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
		builder.append("<tr><th>").append(value).append("</th></tr>");
		return this;
	}

	/**
	 * Returns the whole result. THis method will finalize the file so it should
	 * only be called once. And that is when the result building is complete.
	 * 
	 * @return The formatted output as a text string.
	 */
	public String toString() {
		builder.append("</table>").append("</body>").append("</html>");
		return builder.toString();
	}

	/**
	 * Get the file extension for the format. post: The formatting is done.
	 * Dispose this object because you can not use it for something with meaning
	 * now.
	 * 
	 * @return File Extension.
	 */
	public String getFileExtension() {
		return "html";
	}
}
