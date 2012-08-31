package sheet;

import xl.Adjustment;
import xl.CyclicReferenceException;
import expr.Environment;


public class BlankSlot implements Slot{
	public double value(Environment env) {
		throw new CyclicReferenceException();
	}
	public String toString() {
		return "";
	}
	public String toString(Environment env, Adjustment adjustment) {
		return "";
	}
}