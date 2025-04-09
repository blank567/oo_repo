package frontend.SyntaxTree;

import midend.Value;

import java.util.ArrayList;

public class InitVal {
    private ArrayList<Exp> exps;
    private String stringConst;

    public InitVal() {
        this.exps = new ArrayList<>();
    }

    public void addExp(Exp exp) {
        this.exps.add(exp);
    }

    public void setStringConst(String stringConst) {
        this.stringConst = stringConst;
    }

    public ArrayList<Exp> getExps() {
        return this.exps;
    }

    public ArrayList<Integer> calInitVal(int length) {
        ArrayList<Integer> res = new ArrayList<>();
        if (stringConst != null) {
            for (int i = 0; i < length; i++) {
                if(i >= stringConst.length()) {
                    res.add(0);
                }
                else {
                    res.add((int) stringConst.charAt(i));
                }
            }
        } else {
            for (int i = 0; i < length; i++) {
                if(i >= exps.size()) {
                    res.add(0);
                }
                else {
                    res.add(exps.get(i).calExp());
                }
            }
        }
        return res;
    }

    public ArrayList<Value> genLLVM() {
        if(stringConst != null) {
            ArrayList<Value> values = new ArrayList<>();
            for (int i = 0; i < stringConst.length(); i++) {
                values.add(new Value(String.valueOf((int) stringConst.charAt(i)), 1));
            }
            return values;
        } else {
            ArrayList<Value> values = new ArrayList<>();
            for (Exp exp : exps) {
                values.add(exp.genLLVM());
            }
            return values;
        }
    }
}
