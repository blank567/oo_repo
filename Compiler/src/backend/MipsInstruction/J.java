package backend.MipsInstruction;

public class J implements MipsInstruction {
    private String label;

    public J(String label) {
        this.label = label;
    }

    public String toString() {
        return "j " + label.substring(1) + "\n";
    }
}
