package expression;

import java.math.BigInteger;
import java.util.ArrayList;

public class Poly extends Expr {
    private final ArrayList<Mono> monos;

    public Poly() {
        this.monos = new ArrayList<>();
    }

    public Poly(Mono mono) {
        this.monos = new ArrayList<>();
        monos.add(mono);
    }

    public ArrayList<Mono> getMonos() {
        return monos;
    }

    public boolean equPoly(Poly poly) {
        if (poly.getMonos().size() == 0 && this.monos.size() == 0) {
            return true;
        }
        if (poly.getMonos().size() == this.monos.size()) {
            boolean flag = false;
            int cnt = 0;
            for (Mono mono : this.monos) {
                for (Mono mono1 : poly.getMonos()) {
                    if (mono.equMono(mono1)) {
                        flag = true;
                    }
                }
                if (flag) {
                    cnt++;
                }
                flag = false;
            }
            if (cnt == this.monos.size()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void addMono(BigInteger exponent, BigInteger coe, Poly expPoly) {
        int flag = 0;
        for (Mono mono : this.monos) {
            if (mono.getePoly().equPoly(expPoly) && mono.getExponent().equals(exponent)) {
                mono.addCoe(coe);
                flag = 1;
                break;
            }
        }
        if (flag == 0) {
            Mono mono = new Mono(exponent, coe, expPoly);
            monos.add(mono);
        }
        monos.removeIf(mono -> mono.getCoe().equals(BigInteger.ZERO));
    }

    public Poly derive() {
        Poly resultPoly = new Poly();
        for (Mono mono : monos) {
            resultPoly = resultPoly.addPoly(mono.derive());//每项求导相加
        }
        return resultPoly;
    }

    //    public void addMono(Mono mono) {
    //        monos.addMono(mono.getExponent(),mono.getCoe(),mono.getePoly());
    //    }

    public Poly addPoly(Poly poly) {
        Poly newPoly = new Poly();
        for (Mono mono : monos) {
            newPoly.addMono(mono.getExponent(), mono.getCoe(), mono.getePoly());
        }
        for (Mono mono : poly.getMonos()) {
            newPoly.addMono(mono.getExponent(), mono.getCoe(), mono.getePoly());
        }
        return newPoly;
    }

    public Poly multPoly(Poly poly) {
        Poly sumPoly = new Poly();
        for (int i = 0; i < poly.monos.size(); i++) {
            for (int j = 0; j < this.monos.size(); j++) {
                Mono mono = this.monos.get(j).multMono(poly.monos.get(i));//每项相乘再相加
                sumPoly = sumPoly.addPoly(new Poly(mono));
                //sumPoly.addMono(mono.getExponent(),mono.getCoe(),mono.getePoly());
            }
        }
        return sumPoly;
    }
}
