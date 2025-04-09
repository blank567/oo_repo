package midend.Instructions;


import midend.User;
import midend.Value;

public class IrTrunc extends User {

    // value = trunc i32 op to i8
    public IrTrunc(String value, Value op) {
        super(value, 1);
        super.addOperand(op);
    }

    public String toString() {
        return value + " = trunc i32 " + operands.get(0).value + " to i8\n";
    }

    public void toMips() {
        Value op = operands.get(0);
        if(op.getIsInStack()) {
            this.setOffset(op.getOffset());
        } else {
            this.setRegister(op.getRegister());
        }
    }
}
