package frontend.SymbolTable;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private int id; 		                    // 当前符号表的id。
    private int fatherId; 	                    // 外层符号表的id。
    private HashMap<String, Symbol> directory;  // 符号表的内容。
    private ArrayList<SymbolTable> children;    // 子符号表。
    private boolean isFunc;
    private ArrayList<Symbol> symbols;
    private String funcName;

    public SymbolTable(int id, int fatherId) {
        this.id = id;
        this.fatherId = fatherId;
        this.directory = new HashMap<>();
        this.children = new ArrayList<>();
        this.isFunc = false;
        this.symbols = new ArrayList<>();
        this.funcName = "";
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public void setFunc(boolean isFunc) {
        this.isFunc = isFunc;
    }

    public boolean isFunc() {
        return this.isFunc;
    }

    public void addSymbol(Symbol symbol) {
        if(!directory.containsKey(symbol.getName())) {
            directory.put(symbol.getName(), symbol);
        }
        symbols.add(symbol);
    }

    public void addChild(SymbolTable child) {
        children.add(child);
    }

    public int getId() {
        return id;
    }

    public HashMap<String, Symbol> getDirectory() {
        return directory;
    }

    public ArrayList<SymbolTable> getChildren() {
        return children;
    }

    public ArrayList<Symbol> getSymbols() {
        return symbols;
    }
}
