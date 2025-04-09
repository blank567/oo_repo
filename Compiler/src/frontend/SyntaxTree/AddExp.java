package frontend.SyntaxTree;

import frontend.TokenType;
import static midend.Module.module;

import midend.Instructions.IrAdd;
import midend.Instructions.IrSub;
import midend.Instructions.IrZext;
import midend.Value;

import java.util.ArrayList;

public class AddExp {
    private ArrayList<MulExp> mulExps;
    private ArrayList<TokenType> tokenTypes;

    public AddExp() {
        mulExps = new ArrayList<MulExp>();
        tokenTypes = new ArrayList<TokenType>();
    }

    public void addMulExp(MulExp mulExp) {
        mulExps.add(mulExp);
    }

    public void addTokenType(TokenType tokenType) {
        tokenTypes.add(tokenType);
    }

    public int calAddExp() {
        int result = mulExps.get(0).calMulExp();
        for (int i = 1; i < mulExps.size(); i++) {
            if (tokenTypes.get(i - 1) == TokenType.PLUS) {
                result += mulExps.get(i).calMulExp();
            } else if(tokenTypes.get(i - 1) == TokenType.MINU) {
                result -= mulExps.get(i).calMulExp();
            } else {
                System.out.println("Invalid token type in AddExp");
            }
        }
        return result;
    }

    public Value genLLVM() {
        Value op1 = mulExps.get(0).genLLVM();
        for(int i = 1; i < mulExps.size(); i++) {
            Value op2 = mulExps.get(i).genLLVM();
            if(tokenTypes.get(i - 1) == TokenType.PLUS) {
                if(op1.isNumber() && op2.isNumber()) {
                    int res = Integer.parseInt(op1.value) + Integer.parseInt(op2.value);
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
                    IrAdd irAdd = new IrAdd(regName, op1, op2);
                    op1 = irAdd;
                    module.getCurBasicBlock().addInstruction(irAdd);
                }
            } else if(tokenTypes.get(i - 1) == TokenType.MINU) {
                if(op1.isNumber() && op2.isNumber()) {
                    int res = Integer.parseInt(op1.value) - Integer.parseInt(op2.value);
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
                    IrSub irSub = new IrSub(regName, op1, op2);
                    op1 = irSub;
                    module.getCurBasicBlock().addInstruction(irSub);
                }
            } else {
                System.out.println("Invalid token type in AddExp");
            }
        }
        return op1;
    }

//    public Value genLLVM() { //未优化,即不考虑常量的情况，全部分配寄存器
//        Value op1 = mulExps.get(0).genLLVM();
//        for(int i = 1; i < mulExps.size(); i++) {
//            String regName = module.getCurFunction().allocateReg();
//            Value op2 = mulExps.get(i).genLLVM();
//            if(tokenTypes.get(i - 1) == TokenType.PLUS) {
//                IrAdd irAdd = new IrAdd(regName, op1, op2);
//                op1 = irAdd;
//                module.getCurBasicBlock().addInstruction(irAdd);
//            } else if(tokenTypes.get(i - 1) == TokenType.MINU) {
//                IrSub irSub = new IrSub(regName, op1, op2);
//                op1 = irSub;
//                module.getCurBasicBlock().addInstruction(irSub);
//            } else {
//                System.out.println("Invalid token type in AddExp");
//            }
//        }
//        return op1;
//    }
}
