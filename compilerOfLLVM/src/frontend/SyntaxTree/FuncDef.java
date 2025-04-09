package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.Token;
import frontend.TokenType;
import midend.BasicBlock;
import midend.Function;
import midend.Instructions.IrAlloca;
import midend.Instructions.IrStore;
import midend.Value;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

public class FuncDef {
    private FuncType funcType;
    private Token ident;
    private FuncFParams funcFParams;
    private Block block;

    public FuncFParams getFuncFParams() {
        return funcFParams;
    }

    public boolean isLackReturn() {
        if (funcType.getTokenType() != TokenType.VOIDTK && !block.lastIsReturn()) {
            return true;
        }
        return false;
    }

    public boolean isSuperfluousReturn() {
        if (funcType.getTokenType() == TokenType.VOIDTK && block.isReturnHaveExp()) {
            return true;
        }
        return false;
    }

    public void setFuncType(FuncType funcType) {
        this.funcType = funcType;
    }

    public void setIdent(Token curToken) {
        this.ident = curToken;

//        -1 -> void
//        0 -> int
//        1 -> char
        if(symbolTables == null) {
            return;
        }
        Function function = new Function("@" + ident.getValue(), funcType.getTokenType() == TokenType.VOIDTK ? -1 : funcType.getTokenType() == TokenType.INTTK ? 0 : 1);
        module.setCurFunction(function);
    }

    public Token getIdent() {
        return this.ident;
    }

    public void setFuncFParams(FuncFParams funcFParams) {
        this.funcFParams = funcFParams;
    }

    public void setBlock(Block block) {
        this.block = block;

        if(symbolTables == null) {
            return;
        }
        module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
        module.addFunction(module.getCurFunction());
    }

    public SymbolType getFuncSymbolType() {
        switch (funcType.getTokenType()) {
            case INTTK:
                return SymbolType.IntFunc;
            case CHARTK:
                return SymbolType.CharFunc;
            case VOIDTK:
                return SymbolType.VoidFunc;
            default:
                return null;
        }
    }

    public Symbol getSymbol(int id, boolean isParam) {
        return new Symbol(id, this.ident, getFuncSymbolType(), isParam);
    }
}
