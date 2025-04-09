package midend.Instructions;

import midend.User;

public class IrAlloca extends User {
    private int len;
    public IrAlloca(String value, int valueType, int len) {
        super(value, valueType);
        this.len = len;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(value).append(" = alloca ");
        switch (valueType) {
            case 0: {
                sb.append("i32");
                break;
            }
            case 1: {
                sb.append("i8");
                break;
            }
            case 2: {
                sb.append("i32*");
                break;
            }
            case 3: {
                sb.append("i8*");
                break;
            }
            case 4: {
                sb.append("[").append(len).append(" x i32]");
                break;
            }
            case 5: {
                sb.append("[").append(len).append(" x i8]");
                break;
            }
        }
        return sb + "\n";
    }
}
