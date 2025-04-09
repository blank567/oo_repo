package expression;

public class Exp implements Factor {
    private final Expr expr;

    public Exp(Expr expr) {
        this.expr = expr;
    }

    public Poly getPoly() {
        Poly poly;
        if (expr instanceof Poly) {
            poly = (Poly) expr;
        } else {
            poly = expr.getans();
        }
        return poly;
    }
}
