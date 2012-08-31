package xl;


/**
 * An <code>Address</code> object represents a position in a rectangular grid
 * using a column and a row number starting with 0. The external
 * <code>String</code> representation uses a letter starting with 'A' for
 * columns and numbers starting with '1' for rows. The case for letter is not
 * significant.
 * <p>
 * The methods hashCode and equals are redefined to support the use of
 * <code>Address</code> objects as keys in a HashMap.
 *
 * @author Lennart Andersson
 */
public class Address {
    private int row;
    private int col;

    /**
     * Creates a new <code>Address</code> instance with the specified row and
     * column numbers.
     *
     * @param row
     *            the <code>int</code> row number.
     * @param col
     *            the <code>int</code> column number.
     */
    public Address(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Creates a new <code>Address</code> from the given string representation.
     *
     * @param s
     *            a <code>String</code> value
     * @exception throws XLException if the address is not valid.
     */
    public Address(String s) {
        int length = s.length();
        if (length < 2)
            throw new XLException("Illegal address: " + s);
        col = Character.toUpperCase(s.charAt(0)) - 'A';
        for (int i = 1; i < length; i++) {
            if (!Character.isDigit(s.charAt(i)))
                throw new XLException("Illegal address: " + s);
            row = 10 * row + s.charAt(i) - '0';
        }
        row--;
    }

    /**
     * Returns the column number.
     *
     * @return the column number.
     */
    public int col() {
        return col;
    }

    /**
     * Compares this object to the specified object. The result is true if and
     * only if the argument is not null and is an Address object that contains
     * the same column and row numbers as this object.
     *
     * @param the
     *            object to compare with.
     * @return true if the objects contain the same numbers; false otherwise.
     */
    public boolean equals(Object object) {
        Address other = (Address) object;
        return row == other.row && col == other.col;
    }

    /**
     * Returns a hash code for this Integer.
     *
     * @return a hash code value for this object.
     */
    public int hashCode() {
        return row * 3711 + col;
    }

    /**
     * Returns the row number.
     *
     * @return the row number.
     */
    public int row() {
        return row;
    }

    /**
     * The <code>toString</code> method returns the textual represenation of
     * this <code>Address</code>.
     *
     * @return the <code>String</code> representation of this
     *         <code>Address</code>.
     */
    public String toString() {
        return String.valueOf((char) ('A' + col)) + String.valueOf(row + 1);
    }
}