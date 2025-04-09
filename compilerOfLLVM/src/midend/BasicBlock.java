package midend;
import static midend.Module.module;

import java.util.ArrayList;

public class BasicBlock extends User{
    ArrayList<Value> instructions;

    public BasicBlock(String name) {
        super(name, 0);
        instructions = new ArrayList<>();
    }

    public ArrayList<Value> getInstructions() {
        return instructions;
    }

    public void addInstruction(Value instruction) {
        if(instructions.size() > 0) {
            Value lastInstruction = instructions.get(instructions.size() - 1);
            if("br".equals(lastInstruction.value) || "ret".equals(lastInstruction.value)) {
                return;
            }
        }
        instructions.add(instruction);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        if(instructions.size() == 0) {
            return "    ret void\n";
        }

        for(Value instruction : instructions) {
            sb.append("    " + instruction.toString());

            if(instructions.get(instructions.size() - 1) == instruction) {
                if(!"ret".equals(instruction.value) && !"br".equals(instruction.value)) {
                    sb.append("    ret void\n");
                }
            }
        }
        return sb.toString();
    }
}
