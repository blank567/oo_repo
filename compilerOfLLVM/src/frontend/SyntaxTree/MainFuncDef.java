package frontend.SyntaxTree;

import midend.BasicBlock;
import midend.Function;

import static midend.Module.module;

public class MainFuncDef {
    private Block block;

    public MainFuncDef(Block block) {
        this.block = block;
    }
}
