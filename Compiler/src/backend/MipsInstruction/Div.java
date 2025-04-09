package backend.MipsInstruction;

import backend.Register;

public class Div implements MipsInstruction {
    private Register rs;
    private Register rt;

    public Div(Register rs, Register rt) {
        this.rs = rs;
        this.rt = rt;
    }

    public String toString() {
        return "div " + rs + ", " + rt + "\n";
    }
}
