package backend;

import backend.MipsInstruction.Li;
import backend.MipsInstruction.Lw;
import midend.Value;

import static midend.Module.module;

import java.util.ArrayList;

public enum Register {
    $zero, $at, $v0, $v1, $a0, $a1, $a2, $a3,
    $t0, $t1, $t2, $t3, $t4, $t5, $t6, $t7,
    $s0, $s1, $s2, $s3, $s4, $s5, $s6, $s7,
    $t8, $t9, $k0, $k1, $gp, $sp, $fp, $ra;

    private boolean isUsed = false;

    public static Register getByOffset(Register register, int offset) {
        int targetIndex = register.ordinal() + offset;
        if (targetIndex < 0 || targetIndex >= Register.values().length) {
            throw new IllegalArgumentException("Offset out of bounds: " + targetIndex);
        }
        return Register.values()[targetIndex];
    }

    public static boolean isGlobalReg(Register register) {
        return register.ordinal() >= $t0.ordinal() && register.ordinal() <= $s7.ordinal();
    }

    public static boolean isTempReg(Register register) {
        return register.ordinal() >= $t8.ordinal() && register.ordinal() <= $k1.ordinal();
    }

    public static Register allocTReg() {
        for(int i = $t8.ordinal(); i <= $k1.ordinal(); i++) {
            if (!Register.values()[i].isUsed) {
                Register.values()[i].isUsed = true;
                return Register.values()[i];
            }
        }
        System.out.println("No available temporary register.");
        return null;
    }

    public static void freeTReg(Register register) {
        if (register.ordinal() >= $t8.ordinal() && register.ordinal() <= $k1.ordinal()) {
            register.isUsed = false;
        }
    }

    public static void loadValueToReg(Value value) {
        if(value.isNumber()) {
            Register reg = allocTReg();
            Li li = new Li(reg, Integer.parseInt(value.value));
            value.setRegister(reg);
            module.getCurBasicBlock().addMipsInstructions(li);
        } else if(value.getIsInStack()) {
            Register reg = allocTReg();
            Lw lw = new Lw(reg, value.getOffset(), Register.$fp);
            value.setRegister(reg);
            module.getCurBasicBlock().addMipsInstructions(lw);
        }
    }

}
