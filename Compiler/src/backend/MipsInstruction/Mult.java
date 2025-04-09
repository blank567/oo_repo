package backend.MipsInstruction;

import backend.Register;

public class Mult implements MipsInstruction {
    private Register rs;
    private Register rt;

    public Mult(Register rs, Register rt) {
        this.rs = rs;
        this.rt = rt;
    }

    public String toString() {
        return "mult " + rs + ", " + rt + "\n";
    }
}
