package midend.Instructions;

import backend.MipsInstruction.*;
import backend.Register;
import midend.BasicBlock;
import midend.User;
import midend.Value;

import static backend.Register.allocTReg;
import static backend.Register.freeTReg;
import static backend.Register.loadValueToReg;
import static midend.Module.module;

public class IrSub extends User {

    public IrSub(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = sub i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }

    public void toMips() {
        Value op1 = operands.get(0);
        Value op2 = operands.get(1);
        loadValueToReg(op1);
        loadValueToReg(op2);

        Register reg1 = op1.getRegister();
        Register reg2 = op2.getRegister();

        Register reg = allocTReg();
        Subu subu = new Subu(reg, reg1, reg2);
        this.setRegister(reg);
        module.getCurBasicBlock().addMipsInstructions(subu);
        freeTReg(reg1);
        freeTReg(reg2);
    }
}
