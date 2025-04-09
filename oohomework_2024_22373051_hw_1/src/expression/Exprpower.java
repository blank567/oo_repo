package expression;

import java.math.BigInteger;

public class Exprpower implements Factor {
    private static Factor factor;
    private static int exponent;

    //    public Exprpower(Factor factor, int exponent) {
    //        Exprpower.factor = factor;
    //        Exprpower.exponent = exponent;
    //    }

    public Exprpower(Factor factor, BigInteger exponent) {
        Exprpower.factor = factor;
        Exprpower.exponent = exponent.intValue();
    }

    public static Factor getFactor() {
        return factor;
    }

    public static int getExponent() {
        return exponent;
    }
}
