package expr;

import java.io.IOException;


public class TestExpr {
    public static void main(String[] args) {
        Parser parser = new ExprParser();
        try {
            Expr expr = parser.build("1+2*3");
            System.out.println(expr);
            System.out.println(expr.value(null));
            expr = parser.build("A3+A2*A1");
            Environment env = new Environment() {
                public double value(Object key) {
                    String string = key.toString();
                    if (string.equals("A3"))
                        return 1;
                    if (string.equals("A2"))
                        return 2;
                    if (string.equals("A1"))
                        return 3;
                    System.out.println(string + " is undefined");
                    return 0;
                }
            };
            System.out.println(expr);
            System.out.println(expr.value(env));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}