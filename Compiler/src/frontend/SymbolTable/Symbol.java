package frontend.SymbolTable;

import frontend.Token;
import frontend.TokenType;
import midend.Value;

import java.util.ArrayList;

public class Symbol {
    private int id;
    private Token token;
    private SymbolType type;
    private boolean isParam;
    private boolean isFunc;

    private int initVal;
    private ArrayList<Integer> initVals;
    private int size;
    private Value value;
//    private int dimension; // 0->var 1->array 2->function

    public int getSize() {
        return this.size;
    }
    public void setInitVal(int initVal) {
        this.initVal = initVal;
        size = 1;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setInitVals(ArrayList<Integer> initVals) {
        this.initVals = initVals;
        size = initVals.size();
    }

    public int getInitVal() {
        return this.initVal;
    }

    public ArrayList<Integer> getInitVals() {
        return this.initVals;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return this.value;
    }

    public SymbolType getSymbolType() {
        return this.type;
    }

    public boolean isConst() {
        return type == SymbolType.ConstInt ||
                type == SymbolType.ConstChar ||
                type == SymbolType.ConstIntArray ||
                type == SymbolType.ConstCharArray;
    }

    public Symbol(int id, Token token, SymbolType symbolType, boolean isParam) {
        this.id = id;
        this.token = token;
        this.type = symbolType;
        this.isParam = isParam;
        this.isFunc = false;
    }

    public Symbol(int id, Token token) {
        this.id = id;
        this.token = token;
    }

    public void setFunc(boolean isFunc) {
        this.isFunc = isFunc;
    }

    public boolean isFunc() {
        return this.isFunc;
    }

    public SymbolType getType() {
        return this.type;
    }

    public boolean isParam() {
        return this.isParam;
    }

    public String getName() {
        return this.token.getValue();
    }

    @Override
    public String toString() {
        return id + " " + token.getValue() + " " + type;
    }
}
