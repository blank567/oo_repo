package frontend.SyntaxTree;

import midend.Value;

public class ConstExp {
    private AddExp addExp;

    public ConstExp(AddExp addExp) {
        this.addExp = addExp;
    }

    public int calConstExp() {
        return addExp.calAddExp();
    }

    public Value genLLVM() {
        return addExp.genLLVM();
    }
}
