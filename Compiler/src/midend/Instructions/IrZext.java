package midend.Instructions;

import midend.User;
import midend.Value;

// value = zext i1 op to i32
// value = zext i8 op to i32

public class IrZext extends User {
    public IrZext(String value, Value op) {
        super(value, 0);
        super.addOperand(op);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value).append(" = zext ");
        if(operands.get(0).valueType == 1) {
            sb.append("i8 ");
        } else if(operands.get(0).valueType == 6) {
            sb.append("i1 ");
        } else {
            sb.append("wrong type in IrZext");
        }
        sb.append(operands.get(0).value).append(" to i32\n");
        return sb.toString();
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
