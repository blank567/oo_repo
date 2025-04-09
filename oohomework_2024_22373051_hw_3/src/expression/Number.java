package expression;

import java.math.BigInteger;

public class Number implements Factor {
    private final BigInteger num;

    public Number(BigInteger num) {
        this.num = num;
    }

    public Number(int num) {
        this.num = BigInteger.valueOf(num);
    }

    public String toString() {
        return this.num.toString();
    }

    public BigInteger getNum() {
        return num;
    }

}
