package frontend.SyntaxTree;

import midend.Instructions.IrBr;

import java.util.ArrayList;

public class Cond {
    private LOrExp lOrExp;

    public Cond(LOrExp lOrExp) {
        this.lOrExp = lOrExp;
    }

    public void genLLVM(ArrayList<IrBr> irBrs, String trueBranch, String falseBranch) {
        lOrExp.genLLVM(irBrs, trueBranch, falseBranch);
    }
}
