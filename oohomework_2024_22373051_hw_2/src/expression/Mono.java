package expression;

import java.math.BigInteger;

public class Mono {
    private BigInteger coe;
    private BigInteger exponent;
    private Poly expPoly;

    public Mono(BigInteger exponent, BigInteger coe, Poly expPoly) {
        this.coe = coe;
        this.exponent = exponent;
        this.expPoly = expPoly;
    }

    public Mono() {
        this.expPoly = new Poly();
    }

    public BigInteger getCoe() {
        return coe;
    }

    public BigInteger getExponent() {
        return exponent;
    }

    public Poly getePoly() {
        return expPoly;
    }

    public void addCoe(BigInteger co) {
        this.coe = this.coe.add(co);
    }

    public boolean equMono(Mono mono) {
        return (this.coe.equals(mono.getCoe())) &&
                (this.exponent.equals(mono.getExponent())) &&
                (this.expPoly.equPoly(mono.getePoly()));
    }
}
