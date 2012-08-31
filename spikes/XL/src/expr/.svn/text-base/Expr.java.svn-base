package expr;

import sheet.Slot;
import xl.Address;

/**
 * An <code>Expr</code> object represents a real valued expression that may
 * contain variables. The value of a variable may be obtained from an
 * <code>Environment</code> object by specifying the <code>Address</code> of the
 * variable.
 * 
 * @see Environment
 * @see Address
 * @author Lennart Andersson
 */
public abstract class Expr {
    /**
     * The <code>toString</code> method returns a <code>String</code>
     * representation of this expression without unnecessary parentheses.
     * 
     * @return the <code>String</code> representation of this expression.
     */
    public String toString() {
        return toString(0);
    }

    /*
     * toString(prec) returns a string representation of this expression without
     * unnecessary parentheses. The prec argument specifies the precedence level
     * enclosing expression and is used to control the precence of parentheses.
     */
    public abstract String toString(int prec);

    /**
     * The <code>value</code> method returns the value of this expression.
     * 
     * @param env
     *            is the <code>Environment</code> containing the values of
     *            variables.
     * @return the <code>double</code> value of this expression.
     */
    public abstract double value(Environment env);
}