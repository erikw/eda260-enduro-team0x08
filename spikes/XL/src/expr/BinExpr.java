package expr;



abstract class BinExpr extends Expr {
    private Expr expr1;
    private Expr expr2;
    protected int precedence1;
    protected int precedence2;

    BinExpr(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    abstract protected double op(double op1, double op2);

    abstract protected String opString();

    public String toString(int prec) {
        StringBuffer buffer = new StringBuffer();
        boolean parentheses = prec > precedence1;
        if (parentheses)
            buffer.append('(');
        buffer.append(expr1.toString(precedence1));
        buffer.append(opString());
        buffer.append(expr2.toString(precedence2));
        if (parentheses)
            buffer.append(')');
        return buffer.toString();
    }
    

    public double value(Environment env) {
        return op(expr1.value(env), expr2.value(env));
    }
}