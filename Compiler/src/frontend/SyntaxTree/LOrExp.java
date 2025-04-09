package frontend.SyntaxTree;

import midend.BasicBlock;
import midend.Instructions.IrBr;
import static midend.Module.module;

import java.util.ArrayList;

public class LOrExp {
    private ArrayList<LAndExp> lAndExps;

    public LOrExp() {
        lAndExps = new ArrayList<>();
    }

    public void addLAndExp(LAndExp lAndExp) {
        lAndExps.add(lAndExp);
    }

    public void genLLVM(ArrayList<IrBr> irBrs, String trueBranch, String falseBranch) {
        for (int i = 0; i < lAndExps.size(); i++) {
            if(i != 0) {
                module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
                BasicBlock curBasicBlock = new BasicBlock(module.getCurFunction().allocateReg());
                module.setCurBasicBlock(curBasicBlock);

                for (IrBr irBr : irBrs) {
                    irBr.backFill(curBasicBlock, "nextOrLabel");
                }
            }
            lAndExps.get(i).genLLVM(irBrs, i == lAndExps.size() - 1, trueBranch, falseBranch);
        }
    }

}
