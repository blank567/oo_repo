package frontend.SyntaxTree;

import midend.Value;

import java.util.ArrayList;

public class ConstInitVal {
    private ArrayList<ConstExp> constExps;
    private String stringConst;

    public ConstInitVal() {
        this.constExps = new ArrayList<>();
    }

    public void addConstExp(ConstExp constExp) {
        this.constExps.add(constExp);
    }

    public void setStringConst(String stringConst) {
        this.stringConst = stringConst;
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
                if(i >= constExps.size()) {
                    res.add(0);
                }
                else {
                    res.add(constExps.get(i).calConstExp());
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
            for (ConstExp exp : constExps) {
                values.add(exp.genLLVM());
            }
            return values;
        }
    }
}
