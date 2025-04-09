package backend.MipsInstruction;

public class Branch implements MipsInstruction {
    public enum OP {
        bgt,
        blt,
        bge,
        ble,
        beq,
        bne
    }

    private OP op;
}
