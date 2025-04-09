package midend.Instructions;

import backend.MipsInstruction.Mflo;
import backend.MipsInstruction.Mult;
import backend.MipsInstruction.Subu;
import backend.Register;
import midend.User;
import midend.Value;

import static backend.Register.*;
import static backend.Register.freeTReg;
import static midend.Module.module;

public class IrMul extends User {
    public IrMul(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = mul i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }

    public void toMips() {
        Value op1 = operands.get(0);
        Value op2 = operands.get(1);
        loadValueToReg(op1);
        loadValueToReg(op2);

        Register reg1 = op1.getRegister();
        Register reg2 = op2.getRegister();

        Mult mult = new Mult(reg1, reg2);
        module.getCurBasicBlock().addMipsInstructions(mult);

        Register reg = allocTReg();
        Mflo mflo = new Mflo(reg);
        this.setRegister(reg);
        module.getCurBasicBlock().addMipsInstructions(mflo);

        freeTReg(reg1);
        freeTReg(reg2);
    }
}
