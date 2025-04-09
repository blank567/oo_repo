package backend.MipsInstruction;

import backend.Register;

public class Mflo implements MipsInstruction {
    private Register target;

    public Mflo(Register target) {
        this.target = target;
    }

    public String toString() {
        return "mflo " + target + "\n";
    }
}
