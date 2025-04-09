package expression;

import java.math.BigInteger;

public class Power implements Factor {
    private final BigInteger exponent;

    public Power(BigInteger exponent) {
        this.exponent = exponent;
    }

    //    public Power(BigInteger exponent) {
    //        this.exponent = exponent.intValue();
    //    }

    public BigInteger getExponent() {
        return exponent;
    }
}
