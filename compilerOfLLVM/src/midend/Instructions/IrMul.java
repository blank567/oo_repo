package midend.Instructions;

import midend.User;
import midend.Value;

public class IrMul extends User {
    public IrMul(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = mul i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }
}
