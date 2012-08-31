package sheet;

import xl.Adjustment;
import expr.Environment;


public class Comment implements Slot{
	private String comment;

	public Comment(String comment){
		this.comment = comment;
	}

	public double value(Environment env) {
		return 0.0;
	}

	public String toString() {
		return comment;
	}

	public String toString(Environment env, Adjustment adjustment) {
		return adjustment.left(comment);
	}
}
