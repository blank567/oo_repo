package midend.Instructions;

import backend.Register;
import midend.BasicBlock;
import midend.Function;
import midend.User;
import midend.Value;

import java.util.HashMap;

import static midend.Module.module;

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

    public void toMips() {
        Function curFunction = module.getCurFunction();
        HashMap<Value, Register> active = curFunction.getValue2Register();
        if(active.containsKey(this)) {
            this.setRegister(active.get(this));
        } else {
            switch (valueType) {
                case 0:
                case 1:
                case 2:
                case 3: {
                    this.setIsInStack(true);
                    this.setOffset(module.offset);
                    module.offset += 4;
                    break;
                }
                case 4:
                case 5: {
                    this.setIsInStack(true);
                    this.setOffset(module.offset);
                    module.offset += 4 * len;
                    break;
                }
            }
        }
    }
}
