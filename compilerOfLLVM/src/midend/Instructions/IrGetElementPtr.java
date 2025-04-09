package midend.Instructions;

import midend.User;
import midend.Value;

public class IrGetElementPtr extends User {
    private int len;
    public IrGetElementPtr(String value, int valueType, Value base, Value offset, int len) {
        super(value, valueType);
        this.len = len;
        super.addOperand(base);
        super.addOperand(offset);
    }

    public String toString() {
        Value base = operands.get(0);
        Value offset = operands.get(1);
        switch (base.valueType) {
            case 2:
                return super.value + " = getelementptr inbounds i32, i32* " + base.value + ", i32 " + offset.value + "\n";
            case 3:
                return super.value + " = getelementptr inbounds i8, i8* " + base.value + ", i32 " + offset.value + "\n";
            case 4:
                return super.value + " = getelementptr [" + len + " x i32], [" + len + " x i32]* " + base.value + ", i32 0, i32 " + offset.value + "\n";
            case 5:
                return super.value + " = getelementptr [" + len + " x i8], [" + len + " x i8]* " + base.value + ", i8 0, i32 " + offset.value + "\n";
            default:
                System.out.println("Error: IrGetElementPtr toString");
                return null;
        }
    }
}
