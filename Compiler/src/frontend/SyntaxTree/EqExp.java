package frontend.SyntaxTree;

import frontend.TokenType;
import midend.Instructions.IrIcmp;
import midend.Instructions.IrZext;
import midend.Value;

import java.util.ArrayList;

import static midend.Module.module;

public class EqExp {
    private ArrayList<RelExp> relExps;
    private ArrayList<TokenType> tokenTypes;

    public EqExp() {
        relExps = new ArrayList<RelExp>();
        tokenTypes = new ArrayList<TokenType>();
    }

    public void addRelExp(RelExp relExp) {
        relExps.add(relExp);
    }

    public void addTokenType(TokenType tokenType) {
        tokenTypes.add(tokenType);
    }

    public Value genLLVM() {
        Value op1 = relExps.get(0).genLLVM();
        for(int i = 1; i < relExps.size(); i++) {
            Value op2 = relExps.get(i).genLLVM();
            if (op1.isNumber() && op2.isNumber()) {
                int num1 = Integer.parseInt(op1.value);
                int num2 = Integer.parseInt(op2.value);
                if (tokenTypes.get(i - 1) == TokenType.EQL) {
                    op1 = new Value(num1 == num2 ? "1" : "0", 0);
                } else if (tokenTypes.get(i - 1) == TokenType.NEQ) {
                    op1 = new Value(num1 != num2 ? "1" : "0", 0);
                } else {
                    System.out.println("Invalid token type in EqExp");
                }
            } else {
                String regName = module.getCurFunction().allocateReg();
                IrIcmp irIcmp = new IrIcmp(regName, tokenTypes.get(i - 1), op1, op2);
                module.getCurBasicBlock().addInstruction(irIcmp);
                regName = module.getCurFunction().allocateReg();
                IrZext irZext = new IrZext(regName, irIcmp);
                module.getCurBasicBlock().addInstruction(irZext);
                op1 = irZext;
 //               op1 = irIcmp;
            }
        }
        if(op1.isNumber()) {
            int num = Integer.parseInt(op1.value);
            return new Value((num != 0) ? "1" : "0", 0);
        } else {
            String regName = module.getCurFunction().allocateReg();
            IrIcmp irIcmp = new IrIcmp(regName, TokenType.NEQ, op1, new Value("0", 0));
            module.getCurBasicBlock().addInstruction(irIcmp);
            return irIcmp;
        }
    }
}
