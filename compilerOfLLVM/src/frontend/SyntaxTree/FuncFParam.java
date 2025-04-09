package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolType;
import frontend.Token;

public class FuncFParam {
    private BType bType;
    private Token ident;
    private boolean isArray;

    public FuncFParam() {
        this.isArray = false;
    }

    public void setBType(BType bType) {
        this.bType = bType;
    }

    public void setIdent(Token ident) {
        this.ident = ident;
    }

    public Token getIdent() {
        return this.ident;
    }

    public void setIsArray() {
        this.isArray = true;
    }

    public SymbolType getFuncFParamSymbolType() {
        if (this.isArray) {
            switch (bType.getType()) {
                case INTTK:
                    return SymbolType.IntArray;
                case CHARTK:
                    return SymbolType.CharArray;
                default:
                    return null;
            }
        } else {
            switch (bType.getType()) {
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
        return new Symbol(id, this.ident, getFuncFParamSymbolType(), isParam);
    }
}
