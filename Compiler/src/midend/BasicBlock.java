package midend;

import backend.MipsInstruction.MipsInstruction;
import midend.Instructions.*;

import java.util.*;

public class BasicBlock extends User{
    private ArrayList<Value> instructions;

    private ArrayList<MipsInstruction> mipsInstructions;

    private HashSet<Value> defSet;
    private HashSet<Value> useSet;
    private HashMap<Value, Integer> useCount;

    public BasicBlock(String name) {
        super(name, 0);
        instructions = new ArrayList<>();
    }

    public ArrayList<Value> getInstructions() {
        return instructions;
    }

    public ArrayList<MipsInstruction> getMipsInstructions() {
        return mipsInstructions;
    }

    public void addMipsInstructions(MipsInstruction mipsInstructions) {
        this.mipsInstructions.add(mipsInstructions);
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
        analyseDefUse();
        analyseUseCount();
        return sb.toString();
    }

    public String toMips() {
        StringBuilder sb = new StringBuilder();
        for(Value instruction : instructions) {
            //sb.append(instruction.toMips());
        }
        return sb.toString();
    }

    public void analyseDefUse() {
        defSet = new HashSet<>();
        useSet = new HashSet<>();
        for(Value instruction : instructions) {
            if(!(instruction instanceof IrStore)) {
                for(Value operand : ((User)instruction).getOperands()) {
                    if(!defSet.contains(operand) && (operand instanceof IrAlloca && operand.valueType != 4 && operand.valueType != 5)) {
                        useSet.add(operand);
                    }
                }
            }
            if(instruction instanceof IrStore) {
                Value operand = ((IrStore)instruction).getOperands().get(1);
                if(!useSet.contains(operand) &&
                        (operand instanceof IrAlloca && operand.valueType != 4 && operand.valueType != 5)) {
                    defSet.add(((IrStore)instruction).getOperands().get(1));
                }
            }
        }
        System.out.println("block:");
        System.out.println("def:" + defSet);
        System.out.println("use:" + useSet);
    }

    public void analyseUseCount() {
        useCount = new HashMap<>();
        for(Value instruction : instructions) {
            for(Value operand : ((User)instruction).getOperands()) {
                if(useSet.contains(operand) || defSet.contains(operand)) {
                    if (useCount.containsKey(operand)) {
                        useCount.put(operand, useCount.get(operand) + 1);
                    } else {
                        useCount.put(operand, 1);
                    }
                }
            }
        }
        System.out.println("useCount:");
        System.out.println(useCount);
    }

    public HashSet<Value> getDefSet() {
        return defSet;
    }

    public HashSet<Value> getUseSet() {
        return useSet;
    }

    public HashMap<Value, Integer> getUseCount() {
        return useCount;
    }
}
