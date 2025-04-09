package midend.Instructions;

import midend.User;
import midend.Value;

public class IrBr extends User {
    String trueBranch = "";
    String falseBranch = "";

    public IrBr(Value cmp, String trueBranch, String falseBranch) {
        super("br", -1);
        super.addOperand(cmp);
        super.addOperand(new Value("label1", -1));
        super.addOperand(new Value("label2", -1));
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public IrBr(Value cmp, Value op1) {
        super("br", -1);
        super.addOperand(cmp);
        super.addOperand(op1);
        this.trueBranch = "";
        this.falseBranch = "";
    }

    public Value getValue() {
        return super.operands.get(0);
    }

    public String toString() {
        if (operands.get(0).valueType == 0) {
            return "br label " + (operands.get(0).value.equals("1") ? operands.get(1).value : operands.get(2).value) + "\n";
        } else {
            return "br i1 " + operands.get(0).value + ", label " + operands.get(1).value + ", label " + operands.get(2).value + "\n";
        }
    }

    public void backFill(Value label, String fakeLabel) {
        if(trueBranch.equals(fakeLabel)) {
            operands.set(1, label);
            trueBranch = "";
        }
        if(falseBranch.equals(fakeLabel)) {
            operands.set(2, label);
            falseBranch = "";
        }
    }

    public String toMips() {
        return "";
    }
}
