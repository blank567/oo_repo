package frontend.SyntaxTree;

public class BlockItem {
    private Stmt stmt;
    private Decl decl;

    public BlockItem(Stmt stmt) {
        this.stmt = stmt;
    }

    public BlockItem(Decl decl) {
        this.decl = decl;
    }

    public boolean isContainReturn() {
        if (stmt != null) {
            return stmt.isContainReturn();
        }
        return false;
    }

    public boolean isReturnHaveExp() {
        if (stmt != null) {
            if(stmt.isContainReturn()) {
                return stmt.isReturnHaveExp();
            }
        }
        return false;
    }
}
