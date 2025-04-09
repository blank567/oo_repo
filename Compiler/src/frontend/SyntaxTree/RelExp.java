package frontend.SyntaxTree;

import frontend.TokenType;
import midend.Instructions.IrIcmp;
import midend.Instructions.IrZext;
import midend.Value;

import java.util.ArrayList;
import static midend.Module.module;

public class RelExp {
    private ArrayList<AddExp> addExps;
    private ArrayList<TokenType> tokenTypes;

    public RelExp() {
        addExps = new ArrayList<AddExp>();
        tokenTypes = new ArrayList<TokenType>();
    }

    public void addAddExp(AddExp addExp) {
        addExps.add(addExp);
    }

    public void addTokenType(TokenType tokenType) {
        tokenTypes.add(tokenType);
    }

    public Value genLLVM() {
        Value op1 = addExps.get(0).genLLVM();
        for(int i = 1; i < addExps.size(); i++) {
            Value op2 = addExps.get(i).genLLVM();
            if (op1.isNumber() && op2.isNumber()) {
                int num1 = Integer.parseInt(op1.value);
                int num2 = Integer.parseInt(op2.value);
                if (tokenTypes.get(i - 1) == TokenType.LSS) {
                    op1 = new Value(num1 < num2 ? "1" : "0", 0);
                } else if (tokenTypes.get(i - 1) == TokenType.GRE) {
                    op1 = new Value(num1 > num2 ? "1" : "0", 0);
                } else if (tokenTypes.get(i - 1) == TokenType.LEQ) {
                    op1 = new Value(num1 <= num2 ? "1" : "0", 0);
                } else if (tokenTypes.get(i - 1) == TokenType.GEQ) {
                    op1 = new Value(num1 >= num2 ? "1" : "0", 0);
                } else {
                    System.out.println("Invalid token type in RelExp");
                }
            } else {

                if(op1.valueType == 1) {
                    String regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, op1);
                    module.getCurBasicBlock().addInstruction(irZext);
                    op1 = irZext;
                }

                if(op2.valueType == 1) {
                    String regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, op2);
                    module.getCurBasicBlock().addInstruction(irZext);
                    op2 = irZext;
                }

                String regName = module.getCurFunction().allocateReg();
                IrIcmp irIcmp = new IrIcmp(regName, tokenTypes.get(i - 1), op1, op2);
                module.getCurBasicBlock().addInstruction(irIcmp);
                regName = module.getCurFunction().allocateReg();
                IrZext irZext = new IrZext(regName, irIcmp);
                module.getCurBasicBlock().addInstruction(irZext);
                op1 = irZext;
            }
        }
        if(op1.valueType == 1) {
            String regName = module.getCurFunction().allocateReg();
            IrZext irZext = new IrZext(regName, op1);
            module.getCurBasicBlock().addInstruction(irZext);
            op1 = irZext;
        }
        return op1;
    }
}
