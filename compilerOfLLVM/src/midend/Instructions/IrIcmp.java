package midend.Instructions;

import frontend.TokenType;
import midend.User;
import midend.Value;

public class IrIcmp extends User {
    private TokenType cmpOp;

    public IrIcmp(String value, TokenType cmpOp, Value op1, Value op2) {
        super(value, 6);
        super.addOperand(op1);
        super.addOperand(op2);
        this.cmpOp = cmpOp;
    }

    public String toString() {
        String cond = "default cmpOp";
        switch (cmpOp) {
            case EQL:
                cond = "eq";
                break;
            case NEQ:
                cond = "ne";
                break;
            case GEQ:
                cond = "sge";
                break;
            case LEQ:
                cond = "sle";
                break;
            case GRE:
                cond = "sgt";
                break;
            case LSS:
                cond = "slt";
                break;
        }
        return super.value + " = icmp " + cond + " i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }
}
