package frontend.SyntaxTree;

import java.util.ArrayList;

public class CompUnit {
    private ArrayList<Decl> decls;
    private ArrayList<FuncDef> funcDefs;
    private MainFuncDef mainFuncDef;

    public CompUnit() {
        decls = new ArrayList<Decl>();
        funcDefs = new ArrayList<FuncDef>();
    }

    public void addDecl(Decl decl) {
        decls.add(decl);
    }

    public void addFuncDef(FuncDef funcDef) {
        funcDefs.add(funcDef);
    }

    public void setMainFuncDef(MainFuncDef mainFuncDef) {
        this.mainFuncDef = mainFuncDef;
    }}
