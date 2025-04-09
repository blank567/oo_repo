package midend.Instructions;

import backend.MipsInstruction.Div;
import backend.MipsInstruction.Mflo;
import backend.MipsInstruction.Mult;
import backend.Register;
import midend.User;
import midend.Value;

import static backend.Register.*;
import static midend.Module.module;

public class IrSdiv extends User {
    public IrSdiv(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = sdiv i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }

    public void toMips() {
        Value op1 = operands.get(0);
        Value op2 = operands.get(1);
        loadValueToReg(op1);
        loadValueToReg(op2);

        Register reg1 = op1.getRegister();
        Register reg2 = op2.getRegister();

        Div div = new Div(reg1, reg2);
        module.getCurBasicBlock().addMipsInstructions(div);

        Register reg = allocTReg();
        Mflo mflo = new Mflo(reg);
        this.setRegister(reg);
        module.getCurBasicBlock().addMipsInstructions(mflo);

        freeTReg(reg1);
        freeTReg(reg2);
    }
}
