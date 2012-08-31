package sheet;

import xl.Adjustment;
import expr.Environment;


public interface Slot{
	public double value(Environment env);
	public String toString();
	public String toString(Environment env, Adjustment adjustment);
}
