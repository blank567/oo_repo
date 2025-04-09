package midend.Instructions;

import backend.MipsInstruction.Div;
import backend.MipsInstruction.Mfhi;
import backend.MipsInstruction.Mflo;
import backend.Register;
import midend.User;
import midend.Value;

import static backend.Register.*;
import static backend.Register.freeTReg;
import static midend.Module.module;

public class IrSrem extends User {
    public IrSrem(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = srem i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
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
        Mfhi mfhi = new Mfhi(reg);
        this.setRegister(reg);
        module.getCurBasicBlock().addMipsInstructions(mfhi);

        freeTReg(reg1);
        freeTReg(reg2);
    }
}
