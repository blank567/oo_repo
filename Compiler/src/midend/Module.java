package midend;

import java.util.ArrayList;

public class Module {
    static public Module module = new Module();
    private Function curFunction;
    private BasicBlock curBasicBlock;
    private final ArrayList<GlobalVar> globalVars = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();
    private int printCode = 0;

    public int offset = 0;

    public String allocateCode() {
        return "@.str." + printCode++;
    }

    public void setCurFunction(Function function) {
        curFunction = function;
    }

    public Function getCurFunction() {
        return curFunction;
    }

    public void setCurBasicBlock(BasicBlock basicBlock) {
        curBasicBlock = basicBlock;
    }

    public BasicBlock getCurBasicBlock() {
        return curBasicBlock;
    }

    public void addGlobalVar(GlobalVar globalVar) {
        globalVars.add(globalVar);
    }

    public void addFunction(Function function) {
        functions.add(function);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("declare i32 @getint()\n" +
                "declare i32 @getchar()\n" +
                "declare void @putint(i32)\n" +
                "declare void @putch(i32)\n" +
                "declare void @putstr(i8*)\n\n");
        for (GlobalVar globalVar : globalVars) {
            sb.append(globalVar.toString());
        }
        sb.append("\n");
        for (Function function : functions) {
            sb.append(function.print());
            sb.append("\n");
        }
        return sb.toString();
    }
}
