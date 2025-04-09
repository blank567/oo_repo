package expression;

import java.math.BigInteger;
import java.util.ArrayList;

public class Expr implements Factor {
    private final ArrayList<Term> terms;

    public Expr() {
        this.terms = new ArrayList<>();
    }

    public ArrayList<Term> getTerms() {
        return terms;
    }

    public void addTerm(Term term) {
        this.terms.add(term);
    }

    public Poly getans() {
        Poly init = new Poly();
        Poly ans = new Poly();
        for (Term term : terms) { // 遍历所有项
            for (Term item : term.mult().terms) { // 对每个项进行化简
                BigInteger coe = BigInteger.ONE;
                BigInteger exponent = BigInteger.ZERO;
                Poly expPoly = new Poly();
                //Expr e_expr
                for (Factor factor : item.getFactors()) { // 计算每个项的系数和幂指数
                    if (factor instanceof Number) {
                        coe = coe.multiply(((Number) factor).getNum());
                    }
                    if (factor instanceof Power) {
                        exponent = exponent.add(((Power) factor).getExponent());
                    }
                    if (factor instanceof Exp) {
                        Poly factorPoly = ((Exp) factor).getPoly();
                        for (Mono mono : factorPoly.getMonos()) {
                            expPoly.addMono(mono.getExponent(), mono.getCoe(), mono.getePoly());
                        }
                    }
                }
                init.addMono(exponent, coe, expPoly);
            }
        }
        return init;
    }

    public Expr combine(Poly combinedTerms) {
        this.terms.clear();//combine后的expr
        for (Mono mono : combinedTerms.getMonos()) {
            BigInteger exponent = mono.getExponent();
            BigInteger coe = mono.getCoe();
            Poly expPoly = mono.getePoly();
            Term newTerm = new Term();
            newTerm.addFactor(new Power(exponent));
            newTerm.addFactor(new Number(coe));
            newTerm.addFactor(new Exp((Expr) expPoly));
            terms.add(newTerm);
        }
        return this;
    }

    public Expr exprmult(Expr newexpr) {
        Expr expr = new Expr();
        for (Term thisterm : terms) {
            for (Term otherterm : newexpr.terms) {
                Term term = new Term();
                term.addAllFactor(thisterm.getFactors());
                term.addAllFactor(otherterm.getFactors());
                expr.addTerm(term);
            }
        }
        return expr;
    }
}

