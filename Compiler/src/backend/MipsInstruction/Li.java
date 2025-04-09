package backend.MipsInstruction;

import backend.Register;

public class Li implements MipsInstruction {
    private Register target;
    private int imm;

    public Li(Register target, int imm) {
        this.target = target;
        this.imm = imm;
    }

    public String toString() {
        return "li " + target + ", " + imm + "\n";
    }
}
