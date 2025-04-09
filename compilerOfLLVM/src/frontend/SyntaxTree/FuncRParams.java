package frontend.SyntaxTree;

import midend.Value;

import java.util.ArrayList;

public class FuncRParams {
    private ArrayList<Exp> exps;

    public FuncRParams() {
        exps = new ArrayList<Exp>();
    }

    public void addExp(Exp exp) {
        exps.add(exp);
    }

    public ArrayList<Exp> getExps() {
        return exps;
    }

    public ArrayList<Value> genLLVM() {
        ArrayList<Value> rParams = new ArrayList<>();
        for(Exp exp : exps) {
            rParams.add(exp.genLLVM());
        }
        return rParams;
    }
}
