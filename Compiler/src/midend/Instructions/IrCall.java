package midend.Instructions;

import midend.User;
import midend.Value;

import java.util.ArrayList;

public class IrCall extends User {
    private String functionName;
    private String stringConst;
    private String code;

    public IrCall(String value, int valueType, String functionName, ArrayList<Value> args) {
        super(value, valueType);
        this.functionName = functionName;
        for (Value arg : args) {
            super.addOperand(arg);
        }
    }

    public IrCall(String value, int valueType, String functionName, String stringConst, String code) {
        super(value, valueType);
        this.functionName = functionName;
        this.stringConst = stringConst;
        this.code = code;
    }

    public String toString() {  //这里的类型还要修改 0 1 3
        StringBuilder sb = new StringBuilder();
        switch (valueType) {
            case 3:
                sb.append("call void @putstr(i8* getelementptr inbounds ([");
                sb.append(stringConst.replace("\\n", "n").length() + 1 + " x i8], [");
                sb.append(stringConst.replace("\\n", "n").length() + 1 + " x i8]* ");
                sb.append(code + ", i64 0, i64 0))\n");
                return sb.toString();
        }
        if(super.value == null) {
            sb.append("call void ").append(functionName).append("(");
        } else {
            sb.append(super.value).append(" = call ");
            sb.append(super.valueType == 0 ? "i32" : "i8");
            sb.append(" " + functionName).append("(");
        }
        for (int i = 0; i < operands.size(); i++) {
            switch (operands.get(i).valueType) {
                case 0:
                    sb.append("i32 ");
                    break;
                case 1:
                    sb.append("i8 ");
                    break;
                case 2:
                    sb.append("i32* ");
                    break;
                case 3:
                    sb.append("i8* ");
                    break;
            }
            sb.append(operands.get(i).value);
            if (i != operands.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(")\n");
        return sb.toString();
    }
}
