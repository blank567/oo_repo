package backend.MipsInstruction;

import backend.Register;

public class Mfhi implements MipsInstruction {
    private Register target;

    public Mfhi(Register target) {
        this.target = target;
    }

    public String toString() {
        return "mfhi " + target + "\n";
    }
}
