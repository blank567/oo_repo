package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.Token;
import frontend.TokenType;
import midend.Instructions.*;
import midend.Value;

import java.util.ArrayList;
import java.util.ListIterator;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

public class UnaryExp {
    private PrimaryExp primaryExp;
    private Token ident;
    private FuncRParams funcRParams;
    private UnaryOp unaryOp;
    private UnaryExp unaryExp;

    public UnaryExp() {
    }

    public void setPrimaryExp(PrimaryExp primaryExp) {
        this.primaryExp = primaryExp;
    }

    public void setIdent(Token ident) {
        this.ident = ident;
    }

    public Token getIdent() {
        return this.ident;
    }

    public void setFuncRParams(FuncRParams funcRParams) {
        this.funcRParams = funcRParams;
    }

    public void setUnaryOp(UnaryOp unaryOp) {
        this.unaryOp = unaryOp;
    }

    public void setUnaryExp(UnaryExp unaryExp) {
        this.unaryExp = unaryExp;
    }

    public int calUnaryExp() {
        if (primaryExp != null) {
            return primaryExp.calPrimaryExp();
        } else if (ident != null) {
            return 0;
        } else if (funcRParams != null) {
            System.out.println("no need to implement funcRParams in calUnaryExp, need genLLVMCode instead");
            return 0;
        } else if (unaryOp != null) {
            if(unaryOp.getOp() == TokenType.PLUS) {
                return unaryExp.calUnaryExp();
            } else if(unaryOp.getOp() == TokenType.MINU) {
                return -unaryExp.calUnaryExp();
            } else {
                System.out.println("Error: not supported operator " + unaryOp.getOp() + " in CalUnaryExp");
                return 0;
            }
        } else {
            System.out.println("Error: no valid expression in CalUnaryExp");
            return 0;
        }
    }

    public Value genLLVM() {
        if (primaryExp != null) {
            return primaryExp.genLLVM();
        } else if (ident != null) {
            Symbol curSymbol = searchSymbol();
            ArrayList<Value> params = new ArrayList<>();
            if(funcRParams != null) {
                params = funcRParams.genLLVM();
            }
            if(curSymbol == null) {
                System.out.println("Error: variable " + ident.getValue() + " not defined in UnaryExp");
                return null;
            }
            if(curSymbol.getType() == SymbolType.IntFunc || curSymbol.getType() == SymbolType.CharFunc) {
                String regName = module.getCurFunction().allocateReg();
                IrCall irCall = new IrCall(regName, curSymbol.getType() == SymbolType.IntFunc ? 0 : 1,
                        "@" + curSymbol.getName(), params);
                module.getCurBasicBlock().addInstruction(irCall);
                return irCall;
            } else if(curSymbol.getType() == SymbolType.VoidFunc) {
                IrCall irCall = new IrCall(null, -1, "@" + curSymbol.getName(), params);
                module.getCurBasicBlock().addInstruction(irCall);
                return null;
            } else {
                System.out.println("Error: variable " + ident.getValue() + " not defined in UnaryExp");
                return null;
            }
        } else if (unaryOp != null) {
            if(unaryOp.getOp() == TokenType.PLUS) {
                return unaryExp.genLLVM();
            } else if(unaryOp.getOp() == TokenType.MINU) {
                Value tmp = unaryExp.genLLVM();
                if(tmp.isNumber()) {
                    return new Value(String.valueOf(-Integer.parseInt(tmp.value)), 0);
                } else {
                    String regName = module.getCurFunction().allocateReg();
                    IrSub irSub = new IrSub(regName, new Value("0", 0), tmp);
                    module.getCurBasicBlock().addInstruction(irSub);
                    return irSub;
                }
            } else if(unaryOp.getOp() == TokenType.NOT) {
                Value tmp = unaryExp.genLLVM();

                if(tmp.valueType != 0) {
                    IrZext irZext = new IrZext(module.getCurFunction().allocateReg(), tmp);
                    module.getCurBasicBlock().addInstruction(irZext);
                    tmp = irZext;
                }

                String regName = module.getCurFunction().allocateReg();
                IrIcmp irIcmp = new IrIcmp(regName, TokenType.EQL, tmp, new Value("0", 0));
                module.getCurBasicBlock().addInstruction(irIcmp);

                regName = module.getCurFunction().allocateReg();
                IrZext irZext = new IrZext(regName, irIcmp);
                module.getCurBasicBlock().addInstruction(irZext);

                return new Value(regName, 0);
//                return irIcmp;
            } else {
                System.out.println("Error: not supported operator " + unaryOp.getOp() + " in genLLVM");
                return null;
            }
        } else {
            System.out.println("Error: no valid expression in genLLVM");
            return null;
        }
    }

    public Symbol searchSymbol() {
        ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
        while (iterator.hasPrevious()) {
            SymbolTable tmp = iterator.previous();
            if (tmp.getDirectory().containsKey(ident.getValue())) {
                return tmp.getDirectory().get(ident.getValue());
            }
        }
        return null;
    }
}
