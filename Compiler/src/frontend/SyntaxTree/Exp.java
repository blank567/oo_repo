package frontend.SyntaxTree;

import midend.Value;

public class Exp {
    private AddExp addExp;
    private int type;

    public Exp(AddExp addExp) {
        this.addExp = addExp;
        this.type = -1;
//        0->var 1->IntArray 2->CharArray
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public int calExp() {
        return addExp.calAddExp();
    }

    public Value genLLVM() {
        return addExp.genLLVM();
    }
}
