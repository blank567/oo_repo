package midend.Instructions;

import midend.User;
import midend.Value;

public class IrStore extends User {
    public IrStore(Value value, Value value1) {
        super("*store", -1);
        super.addOperand(value);  //data
        super.addOperand(value1); //addr
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int valueType = operands.get(0).valueType;
        switch (valueType) {
            case 0: {
                sb.append("store i32 ").append(operands.get(0).value).append(", i32* ").append(operands.get(1).value);
                break;
            }
            case 1: {
                sb.append("store i8 ").append(operands.get(0).value).append(", i8* ").append(operands.get(1).value);
                break;
            }
            case 2: {
                sb.append("store i32* ").append(operands.get(0).value).append(", i32** ").append(operands.get(1).value);
                break;
            }
            case 3: {
                sb.append("store i8* ").append(operands.get(0).value).append(", i8** ").append(operands.get(1).value);
                break;
            }
            default: {
                sb.append("store type error");
                break;
            }
        }
        return sb + "\n";
    }
}
