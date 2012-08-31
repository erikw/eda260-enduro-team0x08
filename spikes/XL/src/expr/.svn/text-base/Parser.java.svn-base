package expr;

import java.io.IOException;
import java.io.Reader;
import xl.XLException;

public interface Parser {
    /**
     * The <code>build</code> method returns an <code>Expr</code> representation
     * of the expression provided by <code>reader</code>.
     * 
     * @param reader
     *            a <code>Reader</code> provided the string to be parsed.
     * @return an <code>Expr</code> representation of the string.
     * @exception IOException
     *                if the <code>reader</code> does not deliver data.
     * @exception ExprParserException
     *                if the reader input violates the grammar.
     */
    public Expr build(Reader reader) throws IOException;

    /**
     * The <code>build</code> method returns an <code>Expr</code> representation
     * of the expression provided by the <code>input</code> string.
     * 
     * @param input
     *            the <code>String</code> to be parsed.
     * @return an <code>Expr</code> representation of the string.
     * @exception IOException
     *                if the <code>input</code> does not deliver data.
     * @exception XLException
     *                if the input violates the grammar.
     */
    public Expr build(String input) throws IOException;
}