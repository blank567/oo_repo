package frontend.SyntaxTree;

import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import midend.BasicBlock;
import midend.GlobalVar;
import midend.Instructions.*;
import midend.Value;

import static frontend.Parser.symbolTables;
import static midend.Module.module;

import java.util.ArrayList;
import java.util.ListIterator;

public class Stmt {
    private LVal lVal;
    private ArrayList<Exp> exps;
    private Block block;
    private Cond ifCond;
    private Stmt ifStmt;
    private Stmt elseStmt;
    private ForStmt forStmt1;
    private Cond forCond;
    private ForStmt forStmt2;
    private Stmt ForStmt;
    private String stringConst;
    private int type;

    private boolean isContainReturn;

    public Stmt() {
        exps = new ArrayList<Exp>();
        isContainReturn = false;
        type = -1;
    }

    public ForStmt getForStmt2() {
        return this.forStmt2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContainReturn() {
        isContainReturn = true;
    }

    public boolean isContainReturn() {
        return isContainReturn;
    }

    public boolean isReturnHaveExp() {
        return exps.size() > 0;
    }

    public void setLVal(LVal lVal) {
        this.lVal = lVal;
    }

    public void addExp(Exp exp) {
        this.exps.add(exp);
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setIfCond(Cond ifCond) {
        this.ifCond = ifCond;
    }

    public void setIfStmt(Stmt ifStmt) {
        this.ifStmt = ifStmt;
    }

    public void setElseStmt(Stmt elseStmt) {
        this.elseStmt = elseStmt;
    }

    public void setForStmt1(ForStmt forStmt1) {
        this.forStmt1 = forStmt1;
    }

    public void setForCond(Cond forCond) {
        this.forCond = forCond;
    }

    public void setForStmt2(ForStmt forStmt2) {
        this.forStmt2 = forStmt2;
    }

    public void setForStmt(Stmt ForStmt) {
        this.ForStmt = ForStmt;
    }

    public void setStringConst(String stringConst) {
        this.stringConst = stringConst;
    }

    public boolean isSameInPrintf() {
        int count = 0;
        int index = 0;

        while ((index = stringConst.indexOf("%d", index)) != -1) {
            count++;
            index += 2;
        }

        index = 0;
        while ((index = stringConst.indexOf("%c", index)) != -1) {
            count++;
            index += 2;
        }
        return count == exps.size();
    }

    public Symbol searchSymbol() {
        ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
        while (iterator.hasPrevious()) {
            SymbolTable tmp = iterator.previous();
            if (tmp.getDirectory().containsKey(lVal.getIdent().getValue())) {
                return tmp.getDirectory().get(lVal.getIdent().getValue());
            }
        }
        return null;
    }

    public ArrayList<IrBr> genLLVM() {
        if(type == 0) {
            Value adder = lVal.lValGenLLVM();
            Value data = exps.get(0).genLLVM();

            if((adder.valueType == 0 || adder.valueType == 2) && data.valueType == 1) {
                String reg = module.getCurFunction().allocateReg();
                IrZext irZext = new IrZext(reg, data);
                module.getCurBasicBlock().addInstruction(irZext);
                data = irZext;
            }
            if((adder.valueType == 1 || adder.valueType == 3) && data.valueType == 0) {
                String reg = module.getCurFunction().allocateReg();
                IrTrunc irTrunc = new IrTrunc(reg, data);
                module.getCurBasicBlock().addInstruction(irTrunc);
                data = irTrunc;
            }

            IrStore irStore = new IrStore(data, adder);
            module.getCurBasicBlock().addInstruction(irStore);
        }
        else if(type == 1) {
            if (exps.size() > 0) {
                exps.get(0).genLLVM();
            }
        } else if (type == 2) {
            System.out.println("Block Stmt");
        } else if (type == 3) {
            ArrayList<IrBr> irBrs = new ArrayList<>();
            ifCond.genLLVM(irBrs, "stmtLabel", "elseStmtLabel");

            module.getCurFunction().allocateBasicBlock();

            for (IrBr irBr : irBrs) {
                irBr.backFill(module.getCurBasicBlock(), "stmtLabel");
            }

            return irBrs;
//            ifStmt.genLLVM();
//
//            IrBr irBr = new IrBr(new Value("1", 0), "ifEndLabel", "");
//            module.getCurBasicBlock().addInstruction(irBr);
//            irBrs.add(irBr);
//
//            if (elseStmt != null) {
//                module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
//                basicBlock = new BasicBlock(module.getCurFunction().allocateReg());
//                module.setCurBasicBlock(basicBlock);
//
//                for (IrBr irBr1 : irBrs) {
//                    irBr1.backFill(basicBlock, "elseStmtLabel");
//                }
//                elseStmt.genLLVM();
//
//                irBr = new IrBr(new Value("1", 0), "ifEndLabel", "");
//                module.getCurBasicBlock().addInstruction(irBr);
//                irBrs.add(irBr);
//            }
//
//            module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
//            basicBlock = new BasicBlock(module.getCurFunction().allocateReg());
//            module.setCurBasicBlock(basicBlock);
//
//            for (IrBr irBr1 : irBrs) {
//                irBr1.backFill(basicBlock, "ifEndLabel");
//            }
//
//            for (IrBr irBr1 : irBrs) {
//                irBr1.backFill(basicBlock, "elseStmtLabel");
//            }

        } else if (type == 4) {
            if(forStmt1 != null) {
                forStmt1.genLLVM();
            }
            ArrayList<IrBr> irBrs = new ArrayList<>();

            module.getCurFunction().allocateBasicBlock();

            Value condLabel = module.getCurBasicBlock();
            if(forCond != null) {
                forCond.genLLVM(irBrs, "stmtLabel", "forEndLabel");
            }

            module.getCurFunction().allocateBasicBlock();

            for (IrBr irBr : irBrs) {
                irBr.backFill(module.getCurBasicBlock(), "stmtLabel");
            }
            IrBr tmp = new IrBr(condLabel, condLabel);
            irBrs.add(tmp);
            return irBrs;
        } else if (type == 5) {
            System.out.println("For Stmt");
        } else if (type == 6) {
            System.out.println("For Stmt");
        } else if (type == 7) {
            if (exps.size() == 1) {
                Value value = exps.get(0).genLLVM();

                if(value.valueType == 0 && module.getCurFunction().valueType == 1) {
                    String reg = module.getCurFunction().allocateReg();
                    IrTrunc irTrunc = new IrTrunc(reg, value);
                    module.getCurBasicBlock().addInstruction(irTrunc);
                    value = irTrunc;
                }

                if(value.valueType == 1 && module.getCurFunction().valueType == 0) {
                    String reg = module.getCurFunction().allocateReg();
                    IrZext irZext = new IrZext(reg, value);
                    module.getCurBasicBlock().addInstruction(irZext);
                    value = irZext;
                }

                IrRet irRet = new IrRet(value);
                module.getCurBasicBlock().addInstruction(irRet);
            } else if (exps.size() == 0) {
                IrRet irRet = new IrRet();
                module.getCurBasicBlock().addInstruction(irRet);
            }
        } else if (type == 8) {
           String regName = module.getCurFunction().allocateReg();
           Value symbolValue = lVal.lValGenLLVM();
           IrCall irCall = new IrCall(regName, 0, "@getint", new ArrayList<>());
           module.getCurBasicBlock().addInstruction(irCall);

           if(symbolValue.valueType == 1 || symbolValue.valueType == 3) {
               String reg = module.getCurFunction().allocateReg();
               IrTrunc irTrunc = new IrTrunc(reg, irCall);
               module.getCurBasicBlock().addInstruction(irTrunc);
               IrStore irStore = new IrStore(irTrunc, symbolValue);
               module.getCurBasicBlock().addInstruction(irStore);
               return null;
           }
           IrStore irStore = new IrStore(irCall, symbolValue);
           module.getCurBasicBlock().addInstruction(irStore);
       } else if (type == 9) {
           String regName = module.getCurFunction().allocateReg();
           Value symbolValue = lVal.lValGenLLVM();
           IrCall irCall = new IrCall(regName, 0, "@getchar", new ArrayList<>());  //类型转换还没写
           module.getCurBasicBlock().addInstruction(irCall);

            if(symbolValue.valueType == 1 || symbolValue.valueType == 3) {
                String reg = module.getCurFunction().allocateReg();
                IrTrunc irTrunc = new IrTrunc(reg, irCall);
                module.getCurBasicBlock().addInstruction(irTrunc);
                IrStore irStore = new IrStore(irTrunc, symbolValue);
                module.getCurBasicBlock().addInstruction(irStore);
                return null;
            }

           IrStore irStore = new IrStore(irCall, symbolValue);
           module.getCurBasicBlock().addInstruction(irStore);
       } else if(type == 10) {
            ArrayList<Value> values = new ArrayList<>();
            for(Exp exp : exps) {
                values.add(exp.genLLVM());
            }
            int j = 0;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < stringConst.length(); i++) {
                if(stringConst.charAt(i) == '%' && stringConst.charAt(i+1) == 'd') {
                    if(sb.toString().length() != 0) {
                        String code = module.allocateCode();
                        GlobalVar globalVar = new GlobalVar(code, 7, sb.toString());
                        module.addGlobalVar(globalVar);
                        IrCall irCall = new IrCall(null, 3, "@putstr", sb.toString(), code);
                        module.getCurBasicBlock().addInstruction(irCall);
                        sb = new StringBuilder();
                    }

                    i++;
                    ArrayList<Value> tmp = new ArrayList<>();

                    if(values.get(j).valueType == 1) {
                        IrZext irZext = new IrZext(module.getCurFunction().allocateReg(), values.get(j));
                        module.getCurBasicBlock().addInstruction(irZext);
                        tmp.add(irZext);
                    } else {
                        tmp.add(values.get(j));
                    }

                    j++;
                    IrCall irCall = new IrCall(null, 0, "@putint", tmp);
                    module.getCurBasicBlock().addInstruction(irCall);

                } else if(stringConst.charAt(i) == '%' && stringConst.charAt(i+1) == 'c') {
                    if(sb.toString().length() != 0) {
                        String code = module.allocateCode();
                        GlobalVar globalVar = new GlobalVar(code, 7, sb.toString());
                        module.addGlobalVar(globalVar);
                        IrCall irCall = new IrCall(null, 3, "@putstr", sb.toString(), code);
                        module.getCurBasicBlock().addInstruction(irCall);

                        sb = new StringBuilder();
                    }

                    i++;
                    ArrayList<Value> tmp = new ArrayList<>();
                    if(values.get(j).valueType == 1) {
                        IrZext irZext = new IrZext(module.getCurFunction().allocateReg(), values.get(j));
                        module.getCurBasicBlock().addInstruction(irZext);
                        tmp.add(irZext);
                    } else {
                        tmp.add(values.get(j));
                    }
                    j++;
                    IrCall irCall = new IrCall(null, 1, "@putch", tmp);
                    module.getCurBasicBlock().addInstruction(irCall);
                } else {
                    sb.append(stringConst.charAt(i));
                }
            }
            if(sb.toString().length() != 0) {
                String code = module.allocateCode();;
                GlobalVar globalVar = new GlobalVar(code, 7, sb.toString());
                module.addGlobalVar(globalVar);
                IrCall irCall = new IrCall(null, 3, "@putstr", sb.toString(), code);
                module.getCurBasicBlock().addInstruction(irCall);
            }
        }
        return null;
    }
}

//语句 Stmt →
//    0      LVal '=' Exp ';' // 每种类型的语句都要覆盖
//    1    | [Exp] ';' //有无Exp两种情况
//    2    | Block
//    3    | 'if' '(' Cond ')' Stmt [ 'else' Stmt ] // 1.有else 2.无else
//    4    | 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt // 1. 无缺省，1种情况 2.
//        ForStmt与Cond中缺省一个，3种情况 3. ForStmt与Cond中缺省两个，3种情况 4. ForStmt与Cond全部
//        缺省，1种情况
//    5    | 'break' ';'
//    6     | 'continue' ';'
//    7    | 'return' [Exp] ';' // 1.有Exp 2.无Exp
//    8    | LVal '=' 'getint''('')'';'
//    9    | LVal '=' 'getchar''('')'';'
//    10    | 'printf''('StringConst {','Exp}')'';' // 1.有Exp 2.无Exp
