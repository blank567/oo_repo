package midend;

import midend.Instructions.IrBr;

import java.util.ArrayList;

import static midend.Module.module;

public class Function extends User {
    private int regNum;
    private ArrayList<BasicBlock> basicBlocks;
    private ArrayList<Value> params;

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
}
