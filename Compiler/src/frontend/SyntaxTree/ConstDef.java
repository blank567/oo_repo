package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.Token;
import frontend.TokenType;
import midend.GlobalVar;
import midend.Instructions.IrAlloca;
import midend.Instructions.IrGetElementPtr;
import midend.Instructions.IrStore;
import midend.Instructions.IrZext;
import midend.Value;

import java.util.ArrayList;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

public class ConstDef {
    private Token ident;
    private ConstExp constExp;
    private ConstInitVal constInitVal;
    private TokenType type;


    public void setIdent(Token curToken) {
        this.ident = curToken;
    }

    public Token getIdent() {
        return this.ident;
    }

    public void setConstExp(ConstExp constExp) {
        this.constExp = constExp;
    }

    public void setConstInitVal(ConstInitVal constInitVal) {
        this.constInitVal = constInitVal;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public SymbolType getConstDefSymbolType() {
        if (constExp != null) {
            switch (type) {
                case INTTK:
                    return SymbolType.ConstIntArray;
                case CHARTK:
                    return SymbolType.ConstCharArray;
                default:
                    return null;
            }
        } else {
            switch (type) {
                case INTTK:
                    return SymbolType.ConstInt;
                case CHARTK:
                    return SymbolType.ConstChar;
                default:
                    return null;
            }
        }
    }

    public Symbol getSymbol(int id, boolean isParam) {
        return new Symbol(id, this.ident, getConstDefSymbolType(), isParam);
    }

    public void genGlobalLLVM() {
        SymbolTable symbolTable = symbolTables.peek();
        Symbol curSymbol = symbolTable.getDirectory().get(ident.getValue());
        SymbolType symbolType = curSymbol.getSymbolType();

        ArrayList<Integer> initVals = new ArrayList<>();
        if (symbolType == SymbolType.ConstInt) {
            initVals = constInitVal.calInitVal(1);
            curSymbol.setInitVal(initVals.get(0));
            GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 0, initVals);
            curSymbol.setValue(globalVar);
            module.addGlobalVar(globalVar);
        } else if (symbolType == SymbolType.ConstChar) {
            initVals = constInitVal.calInitVal(1);
            curSymbol.setInitVal(initVals.get(0));
            GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 1, initVals);
            curSymbol.setValue(globalVar);
            module.addGlobalVar(globalVar);
        } else if (symbolType == SymbolType.ConstIntArray) {
            int len = constExp.calConstExp();
            initVals = constInitVal.calInitVal(len);
            curSymbol.setInitVals(initVals);
            GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 4, initVals);
            curSymbol.setValue(globalVar);
            module.addGlobalVar(globalVar);
        } else if (symbolType == SymbolType.ConstCharArray) {
            int len = constExp.calConstExp();
            initVals = constInitVal.calInitVal(len);
            curSymbol.setInitVals(initVals);
            GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 5, initVals);
            curSymbol.setValue(globalVar);
            module.addGlobalVar(globalVar);
        }  else {
            System.out.println("ERROR: Unknown symbol type in genGlobalLLVM");
        }
    }

    public void genLLVM() {
        SymbolTable symbolTable = symbolTables.peek();
        Symbol curSymbol = symbolTable.getDirectory().get(ident.getValue());
        SymbolType symbolType = curSymbol.getSymbolType();

        if (symbolType == SymbolType.ConstInt) {
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 0, 1);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(constInitVal != null) {
                Value value = constInitVal.genLLVM().get(0);

                if(value.valueType == 1) {
                    regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, value);
                    module.getCurBasicBlock().addInstruction(irZext);
                    value = new Value(regName, 0);
                }

                IrStore irStore = new IrStore(value, irAlloca);
                module.getCurBasicBlock().addInstruction(irStore);
            }
            curSymbol.setValue(irAlloca);
        } else if (symbolType == SymbolType.ConstChar) {
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 1, 1);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(constInitVal != null) {
                Value value = constInitVal.genLLVM().get(0);

                if(value.valueType == 0) {
                    regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, value);
                    module.getCurBasicBlock().addInstruction(irZext);
                    value = new Value(regName, 1);
                }

                IrStore irStore = new IrStore(value, irAlloca);
                module.getCurBasicBlock().addInstruction(irStore);
            }
            curSymbol.setValue(irAlloca);
        } else if (symbolType == SymbolType.ConstIntArray) {
            int len = constExp.calConstExp();
            curSymbol.setSize(len);
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 4, len);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(constInitVal != null) {
                ArrayList<Value> values = constInitVal.genLLVM();
                for(int i = 0; i < values.size(); i++) {
                    regName = module.getCurFunction().allocateReg();
                    IrGetElementPtr irGetElementPtr = new IrGetElementPtr(regName, 0, irAlloca, new Value(String.valueOf(i), 0), len);
                    module.getCurBasicBlock().addInstruction(irGetElementPtr);

                    if(values.get(i).valueType == 1) {
                        regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, values.get(i));
                        module.getCurBasicBlock().addInstruction(irZext);
                        values.set(i, new Value(regName, 0));
                    }

                    IrStore irStore = new IrStore(values.get(i), irGetElementPtr);
                    module.getCurBasicBlock().addInstruction(irStore);
                }
            }
            curSymbol.setValue(irAlloca);
        } else if (symbolType == SymbolType.ConstCharArray) {
            int len = constExp.calConstExp();
            curSymbol.setSize(len);
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 5, len);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(constInitVal != null) {
                ArrayList<Value> values = constInitVal.genLLVM();
                for(int i = 0; i < values.size(); i++) {
                    regName = module.getCurFunction().allocateReg();
                    IrGetElementPtr irGetElementPtr = new IrGetElementPtr(regName, 1, irAlloca, new Value(String.valueOf(i), 0), len);
                    module.getCurBasicBlock().addInstruction(irGetElementPtr);

                    if(values.get(i).valueType == 0) {
                        regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, values.get(i));
                        module.getCurBasicBlock().addInstruction(irZext);
                        values.set(i, new Value(regName, 1));
                    }

                    IrStore irStore = new IrStore(values.get(i), irGetElementPtr);
                    module.getCurBasicBlock().addInstruction(irStore);
                }
            }
            curSymbol.setValue(irAlloca);
        } else {
            System.out.println("ERROR: Unknown symbol type in genLLVM");
        }
    }
}
