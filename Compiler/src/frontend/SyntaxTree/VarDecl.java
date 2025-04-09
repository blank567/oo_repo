package frontend.SyntaxTree;

import java.util.ArrayList;

public class VarDecl {
    private BType bType;
    private ArrayList<VarDef> varDefs;

    public VarDecl() {
        this.varDefs = new ArrayList<>();
    }

    public void setBType(BType bType) {
        this.bType = bType;
    }

    public void addVarDef(VarDef varDef) {
        this.varDefs.add(varDef);
    }

    public void genGlobalLLVM() {
        for (VarDef varDef : varDefs) {
            varDef.genGlobalLLVM();
        }
    }

    public void genLLVM() {
        for (VarDef varDef : varDefs) {
            varDef.genLLVM();
        }
    }
}
