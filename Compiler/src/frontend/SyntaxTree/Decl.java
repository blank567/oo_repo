package frontend.SyntaxTree;

public class Decl {
    private ConstDecl constDecl;
    private VarDecl varDecl;

    public Decl(ConstDecl constDecl) {
        this.constDecl = constDecl;
    }
    public Decl(VarDecl varDecl) {
        this.varDecl = varDecl;
    }

    public void genGlobalLLVM() {
        if (constDecl != null) {
            constDecl.genGlobalLLVM();
        }
        if (varDecl != null) {
            varDecl.genGlobalLLVM();
        }
    }

    public void genLLVM() {
        if (constDecl != null) {
            constDecl.genLLVM();
        }
        if (varDecl != null) {
            varDecl.genLLVM();
        }
    }
}
