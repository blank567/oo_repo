package midend.Instructions;

import midend.User;
import midend.Value;

public class IrRet extends User {
    public IrRet() {
        super("ret", 0);
    }

    public IrRet(Value value) {
        super("ret", value.valueType);
        super.addOperand(value);
    }

    public String toString() {
        if (operands.isEmpty()) {
            return "ret void\n";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("ret ");
            if(operands.get(0).valueType == 0) {
                sb.append("i32 ");
            } else if(operands.get(0).valueType == 1) {
                sb.append("i8 ");
            } else {
                sb.append("wrong type in IrRet");
            }
            sb.append(operands.get(0).value);
            return sb + "\n";
        }
    }
}
