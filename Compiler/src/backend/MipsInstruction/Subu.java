package backend.MipsInstruction;

import backend.Register;

public class Subu implements MipsInstruction {
    private Register target;
    private Register src1;
    private Register src2;

    public Subu(Register target, Register src1, Register src2) {
        this.target = target;
        this.src1 = src1;
        this.src2 = src2;
    }

    public String toString() {
        return "subu " + target + ", " + src1 + ", " + src2 + "\n";
    }
}
