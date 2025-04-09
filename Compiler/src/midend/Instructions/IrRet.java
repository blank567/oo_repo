package midend.Instructions;

import backend.MipsInstruction.Jr;
import backend.MipsInstruction.Li;
import backend.MipsInstruction.Lw;
import backend.MipsInstruction.Move;
import backend.Register;
import midend.BasicBlock;
import midend.User;
import midend.Value;
import static midend.Module.module;

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

    public void toMips() {
        BasicBlock curbasicblock = module.getCurBasicBlock();
        if(!operands.isEmpty()) {
            Value op = operands.get(0);
            if(op.isNumber()) {
                Li li = new Li(Register.$v0, Integer.parseInt(op.value));
                curbasicblock.addMipsInstructions(li);
            } else if(op.getIsInStack()) {
                Lw lw = new Lw(Register.$v0, op.getOffset(), Register.$fp);
                curbasicblock.addMipsInstructions(lw);
            } else {
                Move move = new Move(Register.$v0, op.getRegister());
                curbasicblock.addMipsInstructions(move);
            }
        }
        if(!module.getCurFunction().value.equals("@main")) {
            curbasicblock.addMipsInstructions(new Jr());
        }
    }
}
