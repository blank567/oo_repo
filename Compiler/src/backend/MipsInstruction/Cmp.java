package backend.MipsInstruction;

import backend.Register;

public class Cmp implements MipsInstruction {
    public enum OP {
        sgt,
        slt,
        sge,
        sle,
        seq,
        sne
    }

    private Register target;
    private OP op;
    private Register cmp1;
    private Register cmp2;

    public Cmp(Register target, OP op, Register cmp1, Register cmp2) {
        this.target = target;
        this.op = op;
        this.cmp1 = cmp1;
        this.cmp2 = cmp2;
    }

    public String toString() {
        return op + " " + target + ", " + cmp1 + ", " + cmp2 + "\n";
    }
}
