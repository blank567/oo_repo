package frontend.SyntaxTree;

import midend.BasicBlock;
import midend.Instructions.IrBr;
import midend.Value;
import static midend.Module.module;

import java.util.ArrayList;

public class LAndExp {
    private ArrayList<EqExp> eqExps;

    public LAndExp() {
        eqExps = new ArrayList<EqExp>();
    }

    public void addEqExp(EqExp eqExp) {
        eqExps.add(eqExp);
    }

    public void genLLVM(ArrayList<IrBr> irBrs, boolean isEnd, String trueBranch, String falseBranch) {
        for (int i = 0; i < eqExps.size(); i++) {
            if (i != 0) {
                module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
                BasicBlock curBasicBlock = new BasicBlock(module.getCurFunction().allocateReg());
                module.setCurBasicBlock(curBasicBlock);

                for (IrBr irBr : irBrs) {
                    irBr.backFill(curBasicBlock, "nextAndLabel");
                }
            }

            Value value = eqExps.get(i).genLLVM();
            IrBr irBr;
            if(isEnd && i == eqExps.size() - 1) {  //LOrExp的最后一项，LAndExp最后一项
                irBr = new IrBr(value, trueBranch, falseBranch);
            } else if(!isEnd && i == eqExps.size() - 1) { //LOrExp的非最后一项,LAndExp的最后一项
                irBr = new IrBr(value, trueBranch, "nextOrLabel");
            } else if(!isEnd && i != eqExps.size() - 1) { //LOrExp的非最后一项，LAndExp的非最后一项
                irBr = new IrBr(value, "nextAndLabel", "nextOrLabel");
            } else { //LOrExp的最后一项，LAndExp的非最后一项
                irBr = new IrBr(value, "nextAndLabel", falseBranch);
            }

            irBrs.add(irBr);
            module.getCurBasicBlock().addInstruction(irBr);
        }
    }
}
