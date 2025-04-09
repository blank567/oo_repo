package expression;

import java.math.BigInteger;
import java.util.ArrayList;

public class Poly extends Expr {
    private final ArrayList<Mono> monos;

    public Poly() {
        this.monos = new ArrayList<>();
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
}
