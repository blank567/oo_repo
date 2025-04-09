package backend.MipsInstruction;

import backend.Register;

public class Addi implements MipsInstruction {
    private Register target;
    private Register src;
    private int imm;

    public Addi(Register target, Register src, int imm) {
        this.target = target;
        this.src = src;
        this.imm = imm;
    }

    public String toString() {
        return "addi " + target + ", " + src + ", " + imm + "\n";
    }
}
