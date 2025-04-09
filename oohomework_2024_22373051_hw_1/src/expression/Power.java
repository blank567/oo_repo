package expression;

import java.math.BigInteger;

public class Power implements Factor {
    private final int exponent;

    public Power(int exponent) {
        this.exponent = exponent;
    }

    public Power(BigInteger exponent) {
        this.exponent = exponent.intValue();
    }

    public int getExponent() {
        return exponent;
    }
}
