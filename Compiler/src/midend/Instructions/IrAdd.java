package midend.Instructions;

import backend.MipsInstruction.Addi;
import backend.MipsInstruction.Addu;
import backend.MipsInstruction.Lw;
import backend.Register;
import midend.BasicBlock;
import midend.User;
import midend.Value;

import static midend.Module.module;
import static backend.Register.allocTReg;
import static backend.Register.freeTReg;
import static backend.Register.loadValueToReg;

public class IrAdd extends User {

    public IrAdd(String value, Value op1, Value op2) {
        super(value, 0);
        super.addOperand(op1);
        super.addOperand(op2);
    }

    public String toString() {
        return super.value + " = add i32 " + operands.get(0).value + ", " + operands.get(1).value + "\n";
    }

    public void toMips() {
        Value op1 = operands.get(0);
        Value op2 = operands.get(1);
        BasicBlock curBasicBlock = module.getCurBasicBlock();
        if(op1.isNumber() || op2.isNumber()) {
            if(op1.isNumber()) {
                if(op2.getIsInReg()) {
                    Register reg = allocTReg();
                    Addi addi = new Addi(reg, op2.getRegister(), Integer.parseInt(op1.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                } else if(op2.getIsInStack()){
                    Register reg = allocTReg();
                    Lw lw = new Lw(reg, op2.getOffset(), Register.$fp);
                    curBasicBlock.addMipsInstructions(lw);
                    Addi addi = new Addi(reg, reg, Integer.parseInt(op1.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                } else {
                    Register reg = op2.getRegister();
                    Addi addi = new Addi(reg, reg, Integer.parseInt(op1.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                }
            } else if(op2.isNumber()) {
                if(op1.getIsInReg()) {
                    Register reg = allocTReg();
                    Addi addi = new Addi(reg, op1.getRegister(), Integer.parseInt(op2.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                } else if(op1.getIsInStack()){
                    Register reg = allocTReg();
                    Lw lw = new Lw(reg, op1.getOffset(), Register.$fp);
                    curBasicBlock.addMipsInstructions(lw);
                    Addi addi = new Addi(reg, reg, Integer.parseInt(op2.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                } else {
                    Register reg = op1.getRegister();
                    Addi addi = new Addi(reg, reg, Integer.parseInt(op2.value));
                    this.setRegister(reg);
                    curBasicBlock.addMipsInstructions(addi);
                }
            } else {
                System.out.println("Error: add operation with two non-number values");
            }
        } else { // 先这样写，后面直接用这一段就可以了，不考虑优化的话
            loadValueToReg(op1);
            loadValueToReg(op2);

            Register reg1 = op1.getRegister();
            Register reg2 = op2.getRegister();

            Register reg = allocTReg();
            Addu addu = new Addu(reg, reg1, reg2);
            this.setRegister(reg);
            curBasicBlock.addMipsInstructions(addu);
            freeTReg(reg1);
            freeTReg(reg2);
        }
    }
}
