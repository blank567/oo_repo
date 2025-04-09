package frontend.SyntaxTree;

import frontend.TokenType;
import midend.Instructions.IrMul;
import midend.Instructions.IrSdiv;
import midend.Instructions.IrSrem;
import midend.Instructions.IrZext;
import midend.Value;
import static midend.Module.module;

import java.util.ArrayList;

public class MulExp {
    private ArrayList<UnaryExp> unaryExps;
    private ArrayList<TokenType> tokenTypes;

    public MulExp() {
        unaryExps = new ArrayList<UnaryExp>();
        tokenTypes = new ArrayList<TokenType>();
    }

    public void addUnaryExp(UnaryExp unaryExp) {
        unaryExps.add(unaryExp);
    }

    public void addTokenType(TokenType tokenType) {
        tokenTypes.add(tokenType);
    }

    public int calMulExp() {
        int result = unaryExps.get(0).calUnaryExp();
        for (int i = 1; i < unaryExps.size(); i++) {
            if (tokenTypes.get(i - 1) == TokenType.MULT) {
                result *= unaryExps.get(i).calUnaryExp();
            } else if (tokenTypes.get(i - 1) == TokenType.DIV) {
                result /= unaryExps.get(i).calUnaryExp();
            } else if (tokenTypes.get(i - 1) == TokenType.MOD){
                result %= unaryExps.get(i).calUnaryExp();
            } else {
                System.out.println("Invalid token type in MulExp");
            }
        }
        return result;
    }

    public Value genLLVM() {
        Value op1 = unaryExps.get(0).genLLVM();
        for (int i = 1; i < unaryExps.size(); i++) {
            Value op2 = unaryExps.get(i).genLLVM();
            if(tokenTypes.get(i - 1) == TokenType.MULT) {
                if(op1.isNumber() && op2.isNumber()) {
                    int res = Integer.parseInt(op1.value) * Integer.parseInt(op2.value);
                    op1 = new Value(String.valueOf(res), 0);
                } else {
                    if(op1.valueType == 1) {
                        String regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, op1);
                        module.getCurBasicBlock().addInstruction(irZext);
                        op1 = new Value(regName, 0);
                    }
                    if(op2.valueType == 1) {
                        String regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, op2);
                        module.getCurBasicBlock().addInstruction(irZext);
                        op2 = new Value(regName, 0);
                    }
                    String regName = module.getCurFunction().allocateReg();
                    IrMul irMul = new IrMul(regName, op1, op2);
                    op1 = irMul;
                    module.getCurBasicBlock().addInstruction(irMul);
                }
            } else if(tokenTypes.get(i - 1) == TokenType.DIV) {
                if(op1.isNumber() && op2.isNumber()) {
                    int res = Integer.parseInt(op1.value) / Integer.parseInt(op2.value);
                    op1 = new Value(String.valueOf(res), 0);
                } else {
                    if(op1.valueType == 1) {
                        String regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, op1);
                        module.getCurBasicBlock().addInstruction(irZext);
                        op1 = new Value(regName, 0);
                    }
                    if(op2.valueType == 1) {
                        String regName = module.getCurFunction().allocateReg();
                        IrZext irZext = new IrZext(regName, op2);
                        module.getCurBasicBlock().addInstruction(irZext);
                        op2 = new Value(regName, 0);
                    }
                    String regName = module.getCurFunction().allocateReg();
                    IrSdiv irSdiv = new IrSdiv(regName, op1, op2);
                    op1 = irSdiv;
                    module.getCurBasicBlock().addInstruction(irSdiv);
                }
            } else if(tokenTypes.get(i - 1) == TokenType.MOD) {
                if(op1.valueType == 1) {
                    String regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, op1);
                    module.getCurBasicBlock().addInstruction(irZext);
                    op1 = new Value(regName, 0);
                }
                if(op2.valueType == 1) {
                    String regName = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(regName, op2);
                    module.getCurBasicBlock().addInstruction(irZext);
                    op2 = new Value(regName, 0);
                }
                String regName = module.getCurFunction().allocateReg();
                IrSrem irSrem = new IrSrem(regName, op1, op2);
                op1 = irSrem;
                module.getCurBasicBlock().addInstruction(irSrem);
            } else {
                System.out.println("Invalid token type in MulExp");
            }
        }
        return op1;
    }
}
