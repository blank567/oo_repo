package backend.MipsInstruction;

import backend.Register;

public class Move implements MipsInstruction {
    private Register target;
    private Register source;

    public Move(Register target, Register source) {
        this.target = target;
        this.source = source;
    }

    public String toString() {
        return "move " + target + ", " + source + "\n";
    }
}
