package backend.MipsInstruction;

import backend.Register;

public class Jr implements MipsInstruction {

    public Jr() {

    }

    public String toString() {
        return "jr " + Register.$ra + "\n";
    }
}
