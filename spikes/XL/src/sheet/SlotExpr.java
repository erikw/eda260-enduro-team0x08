package sheet;

import xl.Adjustment;
import expr.Environment;
import expr.Expr;
/**
 * Wrapper for Expr class.
 *
 */
public class SlotExpr implements Slot {
	private Expr exp;
	
	public SlotExpr (Expr exp) {
		this.exp = exp;
	}
	public String toString() {
		return exp.toString();
	}
	
	public double value(Environment env) {
		return exp.value(env);
	}
	
	public String toString(Environment env, Adjustment adjustment) {
		return adjustment.right(value(env));
	}
}