package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import midend.Instructions.IrStore;
import midend.Instructions.IrTrunc;
import midend.Instructions.IrZext;
import midend.Value;

import java.util.ListIterator;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

public class ForStmt {
    private LVal lVal;
    private Exp exp;

    public ForStmt() {
    }

    public void setLVal(LVal lVal) {
        this.lVal = lVal;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public void genLLVM() {
        Value adder = lVal.lValGenLLVM();
        Value data = exp.genLLVM();

        if((adder.valueType == 0 || adder.valueType == 2) && data.valueType == 1) {
            String reg = module.getCurFunction().allocateReg();
            IrZext irZext = new IrZext(reg, data);
            module.getCurBasicBlock().addInstruction(irZext);
            data = irZext;
        }
        if((adder.valueType == 1 || adder.valueType == 3) && data.valueType == 0) {
            String reg = module.getCurFunction().allocateReg();
            IrTrunc irTrunc = new IrTrunc(reg, data);
            module.getCurBasicBlock().addInstruction(irTrunc);
            data = irTrunc;
        }

        IrStore irStore = new IrStore(data, adder);
        module.getCurBasicBlock().addInstruction(irStore);
    }

    public Symbol searchSymbol() {
        ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
        while (iterator.hasPrevious()) {
            SymbolTable tmp = iterator.previous();
            if (tmp.getDirectory().containsKey(lVal.getIdent().getValue())) {
                return tmp.getDirectory().get(lVal.getIdent().getValue());
            }
        }
        return null;
    }
}
