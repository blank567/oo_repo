package frontend.SyntaxTree;
import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import midend.Value;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

import java.util.ArrayList;

public class FuncFParams {
    private ArrayList<FuncFParam> funcFParams;

    public FuncFParams() {
        this.funcFParams = new ArrayList<>();
    }

    public ArrayList<FuncFParam> getFuncFParams() {
        return this.funcFParams;
    }

    public void addFuncFParam(FuncFParam funcFParam) {
        this.funcFParams.add(funcFParam);

        if(symbolTables == null) {
            return;
        }
        SymbolTable symbolTable = symbolTables.peek();
        Symbol curSymbol = symbolTable.getDirectory().get(funcFParam.getIdent().getValue());

        String regName = module.getCurFunction().allocateReg();
        Value value = null;
        switch (funcFParam.getFuncFParamSymbolType()) {
            case Int:
                value = new Value(regName, 0);
                module.getCurFunction().addParam(value);
                curSymbol.setValue(value);
                break;
            case Char:
                value = new Value(regName, 1);
                module.getCurFunction().addParam(value);
                curSymbol.setValue(value);
                break;
            case IntArray:
                value = new Value(regName, 2);
                module.getCurFunction().addParam(value);
                curSymbol.setValue(value);
                break;
            case CharArray:
                value = new Value(regName, 3);
                module.getCurFunction().addParam(value);
                curSymbol.setValue(value);
                break;
            default:
                break;
        }
    }
}
