package midend;

import backend.Register;

import java.util.ArrayList;

public class Value {
    public String value;
    public int valueType;
    // i32 -> 0
    // i8 -> 1
    // i32* -> 2
    // i8* -> 3
    // [n x i32] -> 4
    // [n x i8] -> 5
    // i1 -> 6
    // print -> 7

    private Register register;
    private boolean isInReg;

    private boolean isInStack;
    private int offset;

    public Value(String value, int valueType) {
        this.value = value;
        this.valueType = valueType;
        this.isInReg = false;
        this.isInStack = false;
    }

    public boolean isNumber() {
        return value.matches("^-?\\d+$");
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Register getRegister() {
        return register;
    }

    public void setIsInReg(boolean isInReg) {
        this.isInReg = isInReg;
    }

    public boolean getIsInReg() {
        return isInReg;
    }

    public void setIsInStack(boolean isInStack) {
        this.isInStack = isInStack;
    }

    public boolean getIsInStack() {
        return isInStack;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }
}
