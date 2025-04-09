package expression;

import java.math.BigInteger;

public class Mono {
    private BigInteger coe;
    private BigInteger exponent;
    private final Poly expPoly;

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

    public Mono multMono(Mono mono) {
        return new Mono(this.exponent.add(mono.exponent), this.coe.multiply(mono.coe),
                this.expPoly.addPoly(mono.expPoly));
    }

    public Poly derive() {
        if (expPoly.getMonos().size() == 0) {
            if (this.exponent.equals(BigInteger.valueOf(0))) {
                return new Poly();
            } else {
                Poly poly = new Poly();
                BigInteger newCoe = this.coe.multiply(this.exponent);
                BigInteger newExponent = this.exponent.add(BigInteger.valueOf(-1));
                Mono mono = new Mono(newExponent, newCoe, poly);
                return new Poly(mono);
            }
        } else {
            if (this.exponent.equals(BigInteger.valueOf(0))) {
                Mono mono = new Mono(BigInteger.valueOf(0), this.coe, expPoly);
                Poly poly = new Poly(mono);
                return poly.multPoly(this.expPoly.derive());
            } else {
                BigInteger newCoe = this.coe.multiply(this.exponent);
                BigInteger newExponent = this.exponent.add(BigInteger.valueOf(-1));
                Mono mono = new Mono(newExponent, newCoe, expPoly);
                Poly poly = new Poly(mono);
                Poly poly1 = new Poly(this);
                return poly.addPoly(poly1.multPoly(this.expPoly.derive()));
            }
        }
    }
}
