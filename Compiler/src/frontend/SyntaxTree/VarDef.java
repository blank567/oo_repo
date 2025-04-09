package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.Token;
import frontend.TokenType;
import midend.GlobalVar;
import midend.Instructions.*;
import midend.Value;

import static midend.Module.module;

import java.util.ArrayList;

import static frontend.Parser.symbolTables;

public class VarDef {
    private Token ident;
    private ConstExp constExp;
    private InitVal initVal;
    private TokenType type;

    public VarDef() {
    }

    public void setIdent(Token curToken) {
        this.ident = curToken;
    }

    public Token getIdent() {
        return this.ident;
    }

    public void setConstExp(ConstExp constExp) {
        this.constExp = constExp;
    }

    public void setInitVal(InitVal initVal) {
        this.initVal = initVal;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public SymbolType getVarDefSymbolType() {
        if (constExp != null) {
            switch (type) {
                case INTTK:
                    return SymbolType.IntArray;
                case CHARTK:
                    return SymbolType.CharArray;
                default:
                    return null;
            }
        } else {
            switch (type) {
                case INTTK:
                    return SymbolType.Int;
                case CHARTK:
                    return SymbolType.Char;
                default:
                    return null;
            }
        }
    }

    public Symbol getSymbol(int id, boolean isParam) {
        return new Symbol(id, this.ident, getVarDefSymbolType(), isParam);
    }

    public void genGlobalLLVM() {
        SymbolTable symbolTable = symbolTables.peek();
        Symbol curSymbol = symbolTable.getDirectory().get(ident.getValue());
        SymbolType symbolType = curSymbol.getSymbolType();

        ArrayList<Integer> initVals = new ArrayList<>();
        if (symbolType == SymbolType.Int) {
            if(initVal == null) {
                initVals.add(0);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 0, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            } else if(initVal.getExps().size() == 1) {
                initVals = initVal.calInitVal(1);
                curSymbol.setInitVal(initVals.get(0));
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 0, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            } else {
                System.out.println("ERROR: Global array initialization is not supported.");
            }
        } else if (symbolType == SymbolType.Char) {
            if(initVal == null) {
                initVals.add(0);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 1, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            } else {
                initVals = initVal.calInitVal(1);
                curSymbol.setInitVal(initVals.get(0));
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 1, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            }
        } else if (symbolType == SymbolType.IntArray) {
            if(initVal == null) {
                int len = constExp.calConstExp();
                for(int i = 0; i < len; i++) {
                    initVals.add(0);
                }
                curSymbol.setInitVals(initVals);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 4, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            } else {
                int len = constExp.calConstExp();
                initVals = initVal.calInitVal(len);
                curSymbol.setInitVals(initVals);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 4, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            }
        } else if (symbolType == SymbolType.CharArray) {
            if(initVal == null) {
                int len = constExp.calConstExp();
                for(int i = 0; i < len; i++) {
                    initVals.add(0);
                }
                curSymbol.setInitVals(initVals);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 5, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            } else {
                int len = constExp.calConstExp();
                initVals = initVal.calInitVal(len);
                curSymbol.setInitVals(initVals);
                GlobalVar globalVar = new GlobalVar("@"+ident.getValue(), 5, initVals);
                curSymbol.setValue(globalVar);
                module.addGlobalVar(globalVar);
            }
        }  else {
            System.out.println("ERROR: Unknown symbol type in genGlobalLLVM");
        }
    }

    public void genLLVM() {
        SymbolTable symbolTable = symbolTables.peek();
        Symbol curSymbol = symbolTable.getDirectory().get(ident.getValue());
        SymbolType symbolType = curSymbol.getSymbolType();

        if (symbolType == SymbolType.Int) {
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 0, 1);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(initVal != null) {
                Value value = initVal.genLLVM().get(0);

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
        } else if (symbolType == SymbolType.Char) {
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 1, 1);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(initVal != null) {
                Value value = initVal.genLLVM().get(0);

                if(value.valueType == 0) {
                    regName = module.getCurFunction().allocateReg();
                    IrTrunc irTrunc = new IrTrunc(regName, value);
                    module.getCurBasicBlock().addInstruction(irTrunc);
                    value = new Value(regName, 1);
                }

                IrStore irStore = new IrStore(value, irAlloca);
                module.getCurBasicBlock().addInstruction(irStore);
            }
            curSymbol.setValue(irAlloca);
        } else if (symbolType == SymbolType.IntArray) {
            int len = constExp.calConstExp();
            curSymbol.setSize(len);
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 4, len);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(initVal != null) {
                ArrayList<Value> values = initVal.genLLVM();
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
        } else if (symbolType == SymbolType.CharArray) {
            int len = constExp.calConstExp();
            curSymbol.setSize(len);
            String regName = module.getCurFunction().allocateReg();
            IrAlloca irAlloca = new IrAlloca(regName, 5, len);
            module.getCurBasicBlock().addInstruction(irAlloca);
            if(initVal != null) {
                ArrayList<Value> values = initVal.genLLVM();
                for(int i = 0; i < values.size(); i++) {
                    regName = module.getCurFunction().allocateReg();
                    IrGetElementPtr irGetElementPtr = new IrGetElementPtr(regName, 1, irAlloca, new Value(String.valueOf(i), 0), len);
                    module.getCurBasicBlock().addInstruction(irGetElementPtr);

                    if(values.get(i).valueType == 0) {
                        regName = module.getCurFunction().allocateReg();
                        IrTrunc irTrunc = new IrTrunc(regName, values.get(i));
                        module.getCurBasicBlock().addInstruction(irTrunc);
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
