package sheet;

import java.io.IOException;
import xl.EmptyInputException;
import expr.Expr;
import expr.ExprParser;

public class SlotParser {
	private ExprParser exprParser;

	public SlotParser(ExprParser exprParser) {
		this.exprParser = exprParser;
	}

	public Slot parse(String s) {
		if (s.length() <= 0) {
			throw new EmptyInputException();
		} else if (s.charAt(0) == '#') {
			return new Comment(s);
		} else {
			Expr e = null;
			try {
				e = exprParser.build(s);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return new SlotExpr(e);
		}
	}

}
