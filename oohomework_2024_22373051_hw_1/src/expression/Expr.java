package expression;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public HashMap<Integer, BigInteger> getans() {
        HashMap<Integer, BigInteger> init = new HashMap<>();
        HashMap<Integer, BigInteger> ans = new HashMap<>();
        for (Term term : terms) { // 遍历所有项
            for (Term item : term.mult().terms) { // 对每个项进行化简
                BigInteger coe = BigInteger.ONE;
                int exponent = 0;
                for (Factor factor : item.getFactors()) { // 计算每个项的系数和幂指数
                    if (factor instanceof Number) {
                        coe = coe.multiply(((Number) factor).getNum());
                    }
                    if (factor instanceof Power) {
                        exponent += ((Power) factor).getExponent();
                    }
                }
                init.put(exponent, init.getOrDefault(exponent, BigInteger.ZERO).add(coe)); // 合并同类项
            }
        }

        for (Map.Entry<Integer, BigInteger> entry : init.entrySet()) {
            Integer key = entry.getKey();
            BigInteger value = entry.getValue();
            if (ans.containsKey(key)) {
                ans.put(key, ans.get(key).add(value));
            } else {
                ans.put(key, value);
            }
        }
        return ans;
    }

    public Expr combine(HashMap<Integer, BigInteger> combinedTerms) {
        this.terms.clear();//combine后的expr
        for (int exponent : combinedTerms.keySet()) {
            BigInteger coe = combinedTerms.get(exponent);
            Term newTerm = new Term();
            newTerm.addFactor(new Power(exponent));
            newTerm.addFactor(new Number(coe));
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

