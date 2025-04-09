package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.Token;
import midend.Instructions.IrGetElementPtr;
import midend.Instructions.IrLoad;
import midend.Value;
import static midend.Module.module;

import java.util.ListIterator;

import static frontend.Parser.symbolTables;

public class LVal {
    private Token ident;
    private Exp exp;

    public LVal() {
    }

    public void setIdent(Token ident) {
        this.ident = ident;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public Token getIdent() {
        return this.ident;
    }

    public int calLVal() {
        Symbol curSymbol = searchSymbol();
        if (curSymbol == null) {
            System.out.println("Error: variable " + ident.getValue() + " not defined in LVal");
            return 0;
        }
        if (exp != null) {
            return curSymbol.getInitVals().get(exp.calExp());
        } else {
            return curSymbol.getInitVal();
        }
    }

    public Value lValGenLLVM() {
        Symbol curSymbol = searchSymbol();
        if (curSymbol == null) {
            System.out.println("Error: variable " + ident.getValue() + " not defined in LVal");
            return null;
        }
        if(curSymbol.getType() == SymbolType.Int || curSymbol.getType() == SymbolType.Char) {
            return curSymbol.getValue();
        } else if (curSymbol.getType() == SymbolType.IntArray || curSymbol.getType() == SymbolType.CharArray) {
            Value offset = exp.genLLVM();
            String offsetReg = module.getCurFunction().allocateReg();
            IrGetElementPtr irGetElementPtr = new IrGetElementPtr(offsetReg, curSymbol.getType() == SymbolType.IntArray ? 2 : 3,
                    curSymbol.getValue(), offset, curSymbol.getSize()); //这里的数据要修改 数组传参时无大小
            module.getCurBasicBlock().addInstruction(irGetElementPtr);
            return irGetElementPtr;
        } else {
            System.out.println("Error: variable " + ident.getValue() + " is not a valid LVal");
            return null;
        }
    }


    public Value genLLVM() {
        Symbol curSymbol = searchSymbol();
        if (curSymbol == null) {
            System.out.println("Error: variable " + ident.getValue() + " not defined in LVal");
            return null;
        }
        if(curSymbol.getType() == SymbolType.Int || curSymbol.getType() == SymbolType.Char || curSymbol.getType() == SymbolType.ConstInt || curSymbol.getType() == SymbolType.ConstChar) {
            String regName = module.getCurFunction().allocateReg();
            IrLoad irLoad = new IrLoad(regName, (curSymbol.getType() == SymbolType.Int || curSymbol.getType() == SymbolType.ConstInt) ? 0 : 1, curSymbol.getValue());
            module.getCurBasicBlock().addInstruction(irLoad);
            return irLoad;
        } else if(curSymbol.getType() == SymbolType.ConstInt || curSymbol.getType() == SymbolType.ConstChar) {
            return curSymbol.getValue();
        } else if(curSymbol.getType() == SymbolType.IntArray || curSymbol.getType() == SymbolType.CharArray || curSymbol.getType() == SymbolType.ConstIntArray || curSymbol.getType() == SymbolType.ConstCharArray) {
            if(exp == null) {
                String offsetReg = module.getCurFunction().allocateReg();
                IrGetElementPtr irGetElementPtr = new IrGetElementPtr(offsetReg, (curSymbol.getType() == SymbolType.IntArray || curSymbol.getType() == SymbolType.ConstIntArray) ? 2 : 3,
                        curSymbol.getValue(), new Value("0", 0), curSymbol.getSize()); //这里的数据要修改 数组传参时无大小
                module.getCurBasicBlock().addInstruction(irGetElementPtr);
                return irGetElementPtr;
            }
            Value offset = exp.genLLVM(); //对offset生成GetElementPtr指令的LLVM IR
            String offsetReg = module.getCurFunction().allocateReg();
            IrGetElementPtr irGetElementPtr = new IrGetElementPtr(offsetReg, (curSymbol.getType() == SymbolType.IntArray || curSymbol.getType() == SymbolType.ConstIntArray) ? 2 : 3,
                    curSymbol.getValue(), offset, curSymbol.getSize()); //这里的数据要修改 数组传参时无大小
            module.getCurBasicBlock().addInstruction(irGetElementPtr);

            String regName = module.getCurFunction().allocateReg();
            IrLoad irLoad = new IrLoad(regName, (curSymbol.getType() == SymbolType.IntArray || curSymbol.getType() == SymbolType.ConstIntArray) ? 0 : 1, irGetElementPtr);
            module.getCurBasicBlock().addInstruction(irLoad);
            return irLoad;
        } else if(curSymbol.getType() == SymbolType.ConstIntArray || curSymbol.getType() == SymbolType.ConstCharArray) {
            if(exp == null) {
                String offsetReg = module.getCurFunction().allocateReg();
                IrGetElementPtr irGetElementPtr = new IrGetElementPtr(offsetReg, curSymbol.getType() == SymbolType.IntArray ? 2 : 3,
                        curSymbol.getValue(), new Value("0", 0), curSymbol.getSize()); //这里的数据要修改 数组传参时无大小
                module.getCurBasicBlock().addInstruction(irGetElementPtr);
                return irGetElementPtr;
            }

            Value offset = exp.genLLVM();
            if(offset.isNumber()) {
                int offsetValue = Integer.parseInt(offset.value);
                Value value = new Value(String.valueOf(curSymbol.getInitVals().get(offsetValue)), curSymbol.getType() == SymbolType.ConstIntArray ? 0 : 1);
                String regName = module.getCurFunction().allocateReg();
                IrLoad irLoad = new IrLoad(regName, curSymbol.getType() == SymbolType.IntArray ? 0 : 1, value);
                module.getCurBasicBlock().addInstruction(irLoad);
                return irLoad;
            } else {
                String offsetReg = module.getCurFunction().allocateReg();
                IrGetElementPtr irGetElementPtr = new IrGetElementPtr(offsetReg, curSymbol.getType() == SymbolType.ConstIntArray ? 2 : 3,
                        curSymbol.getValue(), offset, curSymbol.getSize());
                module.getCurBasicBlock().addInstruction(irGetElementPtr);

                String regName = module.getCurFunction().allocateReg();
                IrLoad irLoad = new IrLoad(regName, curSymbol.getType() == SymbolType.ConstIntArray ? 0 : 1, irGetElementPtr);
                module.getCurBasicBlock().addInstruction(irLoad);
                return irLoad;
            }
        } else {
            System.out.println("Error: variable " + ident.getValue() + " not defined in LVal");
            return null;
        }
    }

    public Symbol searchSymbol() {
        ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
        while (iterator.hasPrevious()) {
            SymbolTable tmp = iterator.previous();
            if (tmp.getDirectory().containsKey(ident.getValue())) {
                return tmp.getDirectory().get(ident.getValue());
            }
        }
        return null;
    }
}
