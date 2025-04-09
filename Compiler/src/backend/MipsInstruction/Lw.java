package backend.MipsInstruction;

import backend.Register;

public class Lw implements MipsInstruction {
    private Register target;
    private int offset;
    private Register base;

    public Lw(Register target, int offset, Register base) {
        this.target = target;
        this.offset = offset;
        this.base = base;
    }

    public String toString() {
        return "lw " + target + ", " +
                offset + "(" +
                base + ")\n";
    }
}
