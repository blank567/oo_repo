package midend.Instructions;

import backend.MipsInstruction.Move;
import backend.Register;
import midend.BasicBlock;
import midend.User;
import midend.Value;

import static backend.Register.*;
import static midend.Module.module;

public class IrLoad extends User {

    public IrLoad(String value, int valueType, Value addr) {
        super(value, valueType);
        super.addOperand(addr);
    }

    public String toString() {
        switch (valueType) {
            case 0:
                return super.value + " = load i32, i32* " + operands.get(0).value + "\n";
            case 1:
                return super.value + " = load i8, i8* " + operands.get(0).value + "\n";
            case 2:
                return super.value + " = load i32*, i32** " + operands.get(0).value + "\n";
            case 3:
                return super.value + " = load i8*, i8** " + operands.get(0).value + "\n";
            default:
                System.out.println("Error: IrLoad toString");
                return null;
        }
    }

    public void toMips() {
        Value addr = operands.get(0);
        BasicBlock curBasicBlock = module.getCurBasicBlock();
        if(addr.getIsInReg()) {
            Register reg = allocTReg();
            Move move = new Move(reg, addr.getRegister());
            this.setRegister(reg);
            curBasicBlock.addMipsInstructions(move);
        } else if(addr.getIsInStack()) {
        } else {
            this.setRegister(addr.getRegister());
        }
    }
}
