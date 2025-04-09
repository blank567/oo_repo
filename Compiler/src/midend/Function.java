package midend;

import backend.Register;
import midend.Instructions.IrBr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static backend.Register.getByOffset;
import static midend.Module.module;
import static backend.Register.isGlobalReg;

public class Function extends User {
    private int regNum;
    private ArrayList<BasicBlock> basicBlocks;
    private ArrayList<Value> params;

    private HashMap<Value, Register> value2Register;
    private HashMap<Register, Value> register2Value;

    public HashMap<Value, Register> getValue2Register() {
        return value2Register;
    }

    public HashMap<Register, Value> getRegister2Value() {
        return register2Value;
    }

    public void addBasicBlock(BasicBlock basicBlock) {
        basicBlocks.add(basicBlock);
    }

    public void addParam(Value param) {
        params.add(param);
    }

    public Function(String name, int valueType) {
        super(name, valueType);
        regNum = 0;
        basicBlocks = new ArrayList<>();
        params = new ArrayList<>();
        value2Register = new HashMap<>();
        register2Value = new HashMap<>();
    }

    public String allocateReg() {
//        if(module.getCurBasicBlock() != null && module.getCurBasicBlock().getInstructions().size() > 0) {
//            Value lastInstruction = module.getCurBasicBlock().getInstructions().get(module.getCurBasicBlock().getInstructions().size() - 1);
//            if("br".equals(lastInstruction.value) || "ret".equals(lastInstruction.value)) {
//                return "%" + regNum;
//            }
//        }
        return "%ver" + regNum++;
    }

    public void setRegNum(int regNum) {
        this.regNum = regNum;
    }

    public void allocateBasicBlock() {
        String regName = module.getCurFunction().allocateReg();
        Value value = new Value(regName, 0);
        if(module.getCurBasicBlock().getInstructions().size() == 0 || !"br".equals(module.getCurBasicBlock().getInstructions().get(module.getCurBasicBlock().getInstructions().size() - 1).value)) {
            IrBr irBr = new IrBr(new Value("1", 0), value);
            module.getCurBasicBlock().addInstruction(irBr);
        }

        module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
        BasicBlock basicBlock = new BasicBlock(regName);
        module.setCurBasicBlock(basicBlock);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("define dso_local ");
        switch (super.valueType) {
            case -1:
                sb.append("void ");
                break;
            case 0:
                sb.append("i32 ");
                break;
            case 1:
                sb.append("i8 ");
                break;
            default:
                System.out.println("Error: unknown valueType in Function.print()");
                break;
        }
        sb.append(super.value + "(");
        for(int i = 0; i < params.size(); i++) {
            switch (params.get(i).valueType) {
                case 0:
                    sb.append("i32 ");
                    break;
                case 1:
                    sb.append("i8 ");
                    break;
                case 2:
                    sb.append("i32* ");
                    break;
                case 3:
                    sb.append("i8* ");
                    break;
                default:
                    System.out.println("Error: unknown valueType in Function.print()");
                    break;
            }
            sb.append(params.get(i).value);
            if(i != params.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(") {\n");
        for(BasicBlock basicBlock : basicBlocks) {
            sb.append(basicBlock.value.substring(1) + ":\n");
            sb.append(basicBlock.print());
        }
        sb.append("}\n");
        return sb.toString();
    }

    public String toMips() {
        module.setCurFunction(this);
        StringBuilder sb = new StringBuilder();
        for(BasicBlock basicBlock : basicBlocks) {
            module.setCurBasicBlock(basicBlock);
            allocateMipsReg();
            sb.append(basicBlock.toMips());
            clearMipsReg();
        }
        return sb.toString();
    }

    public void allocateMipsReg() {
        Register register = Register.$t0;
        BasicBlock curBasicBlock = module.getCurBasicBlock();
        HashMap<Value, Integer> useCount = curBasicBlock.getUseCount();
        List<Map.Entry<Value, Integer>> sortedEntries = new ArrayList<>(useCount.entrySet());
        sortedEntries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        for (Map.Entry<Value, Integer> entry : sortedEntries) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            if (isGlobalReg(register)) {
                value2Register.put(entry.getKey(), register);
                register2Value.put(register, entry.getKey());
                entry.getKey().setIsInReg(true);
                if(entry.getKey() instanceof GlobalVar) {
                    entry.getKey().setRegister(register);
                }
            }
            register = getByOffset(register, 1);
        }
    }

    public void clearMipsReg() {
        for(Value value : value2Register.keySet()) {
                value.setIsInReg(false);
        }
        value2Register.clear();
        register2Value.clear();
    }
}
