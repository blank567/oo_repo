package expression;

public class Dx implements Factor {
    private final Factor factor;

    public Dx(Factor factor) {
        this.factor = factor;
    }

    public Poly getPoly() {
        Poly poly;
        if (factor instanceof Poly) {
            poly = (Poly) factor;
        } else {
            poly = ((Expr) factor).getans();
        }
        return poly;
    }
}
