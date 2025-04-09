package backend.MipsInstruction;

import backend.Register;

public class Sw implements MipsInstruction {
    private Register base;
    private int offset;
    private Register source;

    public Sw(Register source, int offset, Register base) {
        this.source = source;
        this.offset = offset;
        this.base = base;
    }

    public String toString() {
        return "sw " + source + ", " +
                offset + "(" +
                base + ")\n";
    }
}
