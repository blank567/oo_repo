package expression;

import java.math.BigInteger;
import java.util.ArrayList;

public class Term {
    private final ArrayList<Factor> factors;

    public Term() {
        this.factors = new ArrayList<>();
    }

    public ArrayList<Factor> getFactors() {
        return factors;
    }

    public void addFactor(Factor factor) {
        this.factors.add(factor);
    }

    public void subFactor() {
        this.factors.add(new Number(BigInteger.valueOf(-1)));
    }

    public void addAllFactor(ArrayList<Factor> allfactors) {
        this.factors.addAll(allfactors);
    }

    public Expr mult() {
        Expr expr = new Expr();
        Term tmp = new Term();
        tmp.addFactor(new Number(BigInteger.ONE));
        expr.addTerm(tmp);//表达式中加入1的factor,开始相乘
        for (Factor factor : factors) {
            if (factor instanceof Expr) {
                expr = expr.exprmult((Expr) factor);
                expr = expr.combine(expr.getans());
            } else {
                for (Term term : expr.getTerms()) {
                    term.addFactor(factor);
                }
            }
        }
        return expr;
    }
}
