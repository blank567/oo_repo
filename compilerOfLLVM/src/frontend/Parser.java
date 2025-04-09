package frontend;
import frontend.SymbolTable.Symbol;
import frontend.SymbolTable.SymbolTable;
import frontend.SymbolTable.SymbolType;
import frontend.SyntaxTree.*;
import midend.BasicBlock;
import midend.Function;
import midend.Instructions.IrAlloca;
import midend.Instructions.IrBr;
import midend.Instructions.IrLoad;
import midend.Instructions.IrStore;
import midend.Value;

import static midend.Module.module;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Stack;

public class Parser {
    private ArrayList<Token> tokens;
    private Token curToken;
    private int curPos;
    FileWriter parserWriter;
    ArrayList<ErrorInfo> errors;
    private boolean isOutput;
    private boolean isCircle;
    private boolean isVoidFunc;

    public static Stack<SymbolTable> symbolTables;
    private int id;

    private static Stack<ArrayList<IrBr>> stackOfIrBrs;

    public Parser(ArrayList<Token> tokens, FileWriter parserWriter, ArrayList<ErrorInfo> errors) {
        this.tokens = tokens;
        this.curPos = 0;
        this.curToken = tokens.get(0);
        this.parserWriter = parserWriter;
        this.errors = errors;
        isOutput = true;
        isCircle = false;
        this.isVoidFunc = false;

        this.symbolTables = new Stack<>();
        this.id = 1;

        stackOfIrBrs = new Stack<>();
    }

    public ArrayList<ErrorInfo> getErrors() {
        return errors;
    }

    public Token getNextToken() throws IOException {
        if (isOutput) {
            //        System.out.println(curToken.toString());
            parserWriter.write(curToken.toString() + "\n");
        }
        this.curPos++;
        if (this.curPos < this.tokens.size()) {
            this.curToken = this.tokens.get(this.curPos);
            return this.curToken;
        }
        return null;
    }

    public Token getNextToken(int step) {
        if (this.curPos + step < this.tokens.size()) {
            return this.tokens.get(this.curPos + step);
        }
        return null;
    }

    // 递归遍历符号表及其子符号表的符号
    public void traverseSymbolTable(SymbolTable symbolTable) {
        // 遍历当前符号表的符号
        String symbolFile = "symbol.txt";
        try (FileWriter writer = new FileWriter(symbolFile, true)) {
            for (Symbol symbol : symbolTable.getSymbols()) {
                writer.write(symbol.toString() + "\n");
//                System.out.println(symbol.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 遍历子符号表
        for (SymbolTable child : symbolTable.getChildren()) {
            traverseSymbolTable(child); // 递归调用遍历子符号表
        }
    }

    // CompUnit → {Decl} {FuncDef} MainFuncDef
    public CompUnit parseCompUnit() throws IOException {
        CompUnit compUnit = new CompUnit();
        SymbolTable symbolTable = new SymbolTable(this.id, 0);
        symbolTables.push(symbolTable);
        this.id++;
        while (getNextToken(1).getType() != TokenType.MAINTK) {
            if (getNextToken(2).getType() == TokenType.LPARENT) {
                compUnit.addFuncDef(parseFuncDef());
            } else {
                Decl decl = parseDecl();
                compUnit.addDecl(decl);
                decl.genGlobalLLVM();
            }
        }
        Function function = new Function("@main", 0);
        module.setCurFunction(function);

        BasicBlock basicBlock = new BasicBlock(module.getCurFunction().allocateReg());
        module.setCurBasicBlock(basicBlock);

        compUnit.setMainFuncDef(parseMainFuncDef());

        module.getCurFunction().addBasicBlock(module.getCurBasicBlock());
        module.addFunction(module.getCurFunction());

        SymbolTable tmp = symbolTables.pop();
        traverseSymbolTable(tmp);
        if(isOutput) {
            //System.out.println("<CompUnit>");
            parserWriter.write("<CompUnit>\n");
        }
        return compUnit;
    }

    // Decl → ConstDecl | VarDecl
    public Decl parseDecl() throws IOException {
        Decl decl;
        if (this.curToken.getType() == TokenType.INTTK || this.curToken.getType() == TokenType.CHARTK) {
            decl = new Decl(parseVarDecl());
            return decl;
        } else if (this.curToken.getType() == TokenType.CONSTTK) {
            decl = new Decl(parseConstDecl());
            return decl;
        }
        System.out.println("Error: Invalid declaration at line " + this.curToken.getLine());
        return null;
    }

    // VarDecl → BType VarDef { ',' VarDef } ';' //i
    public VarDecl parseVarDecl() throws IOException {
        VarDecl varDecl = new VarDecl();
        BType bType = parseBType();
        varDecl.setBType(bType);
        VarDef varDef = parseVarDef(bType); //需要读到下一个token
        varDecl.addVarDef(varDef);

        while (this.curToken.getType() == TokenType.COMMA) {
            getNextToken();
            varDef = parseVarDef(bType);
            varDecl.addVarDef(varDef);
        }

        if (this.curToken.getType() == TokenType.SEMICN) {
            getNextToken();
        } else {
            errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
            System.out.println("Error: i" + getNextToken(-1).getLine());
        }
        if(isOutput) {
//        System.out.println("<VarDecl>");
            parserWriter.write("<VarDecl>\n");
        }
        return varDecl;
    }

    // VarDef → Ident [ '[' ConstExp ']' ] | Ident [ '[' ConstExp ']' ] '=' InitVal // b k
    public VarDef parseVarDef(BType bType) throws IOException {
        VarDef varDef = new VarDef();
        varDef.setType(bType.getTokenType());
        if (this.curToken.getType() == TokenType.IDENFR) {
            varDef.setIdent(this.curToken);
            getNextToken();
            if(this.curToken.getType() == TokenType.LBRACK) {
                getNextToken();
                ConstExp constExp = parseConstExp(); //需要读到下一个token
                varDef.setConstExp(constExp);
                if(this.curToken.getType() == TokenType.RBRACK) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('k', getNextToken(-1).getLine()));
                    System.out.println("Error: k" + getNextToken(-1).getLine());
                }
            }
            if(this.curToken.getType() == TokenType.ASSIGN) {
                getNextToken();
                InitVal initVal = parseInitVal(); //需要读到下一个token
                varDef.setInitVal(initVal);
            }
        }

        if (symbolTables.peek().getDirectory().containsKey(varDef.getIdent().getValue())) {
            errors.add(new ErrorInfo('b', varDef.getIdent().getLine()));
            System.out.println("Error: b" + varDef.getIdent().getLine());
        } else {
            Symbol symbol = varDef.getSymbol(symbolTables.peek().getId(), false);
            symbolTables.peek().addSymbol(symbol);
        }
        if(isOutput) {
//        System.out.println("<VarDef>");
            parserWriter.write("<VarDef>\n");
        }
        return varDef;
    }

    // InitVal → Exp | '{' [ Exp { ',' Exp } ] '}' | StringConst
    public InitVal parseInitVal() throws IOException {
        InitVal initVal = new InitVal();
        if (this.curToken.getType() == TokenType.LBRACE) {
            getNextToken();
            if (this.curToken.getType() != TokenType.RBRACE) {
                Exp exp = parseExp();
                initVal.addExp(exp);
                while (this.curToken.getType() == TokenType.COMMA) {
                    getNextToken();
                    exp = parseExp();
                    initVal.addExp(exp);
                }
                if (this.curToken.getType() == TokenType.RBRACE) {
                    getNextToken();
                }
            } else {
                getNextToken();
            }
        } else if (this.curToken.getType() == TokenType.STRCON) {
            initVal.setStringConst(this.curToken.getValue());
            getNextToken();
        } else {
            Exp exp = parseExp();
            initVal.addExp(exp);
        }
        if(isOutput) {
//        System.out.println("<InitVal>");
            parserWriter.write("<InitVal>\n");
        }
        return initVal;
    }

    // ConstDecl → 'const' BType ConstDef { ',' ConstDef } ';' // i
    public ConstDecl parseConstDecl() throws IOException {
        ConstDecl constDecl = new ConstDecl();
        getNextToken();
        if (this.curToken.getType() == TokenType.INTTK || this.curToken.getType() == TokenType.CHARTK) {
            BType bType = parseBType();
            constDecl.setBType(bType);

            ConstDef constDef = parseConstDef(bType); //需要读到下一个token
            constDecl.addConstDef(constDef);

            while (this.curToken.getType() == TokenType.COMMA) {
                getNextToken();
                constDef = parseConstDef(bType);
                constDecl.addConstDef(constDef);
            }
        }
        if (this.curToken.getType() == TokenType.SEMICN) {
            getNextToken();
        } else {
            errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
            System.out.println("Error: i" + getNextToken(-1).getLine());
        }
        if(isOutput) {
//        System.out.println("<ConstDecl>");
            parserWriter.write("<ConstDecl>\n");
        }
        return constDecl;
    }

    // ConstDef → Ident [ '[' ConstExp ']' ] '=' ConstInitVal // k
    public ConstDef parseConstDef(BType bType) throws IOException {
        ConstDef constDef = new ConstDef();
        constDef.setType(bType.getTokenType());

        if (this.curToken.getType() == TokenType.IDENFR) {
            constDef.setIdent(this.curToken);
            getNextToken();
            if(this.curToken.getType() == TokenType.LBRACK) {
                getNextToken();
                ConstExp constExp = parseConstExp(); //需要读到下一个token
                constDef.setConstExp(constExp);
                if(this.curToken.getType() == TokenType.RBRACK) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('k', getNextToken(-1).getLine()));
                    System.out.println("Error: k" + getNextToken(-1).getLine());
                }
            }
            if(this.curToken.getType() == TokenType.ASSIGN) {
                getNextToken();
                ConstInitVal constInitVal = parseConstInitVal();
                constDef.setConstInitVal(constInitVal);
            }
        }
        if (symbolTables.peek().getDirectory().containsKey(constDef.getIdent().getValue())) {
            errors.add(new ErrorInfo('b', constDef.getIdent().getLine()));
            System.out.println("Error: b" + constDef.getIdent().getLine());
        } else {
            Symbol symbol = constDef.getSymbol(symbolTables.peek().getId(), false);
            symbolTables.peek().addSymbol(symbol);
        }
        if(isOutput) {
//        System.out.println("<ConstDef>");
            parserWriter.write("<ConstDef>\n");
        }
        return constDef;
    }

    // ConstInitVal → ConstExp | '{' [ ConstExp { ',' ConstExp } ] '}' | StringConst
    public ConstInitVal parseConstInitVal() throws IOException {
        ConstInitVal constInitVal = new ConstInitVal();
        if(this.curToken.getType() == TokenType.LBRACE) {
            getNextToken();
            if (this.curToken.getType() != TokenType.RBRACE) {
                ConstExp constExp = parseConstExp(); //需要读到下一个token
                constInitVal.addConstExp(constExp);
                while (this.curToken.getType() == TokenType.COMMA) {
                    getNextToken();
                    constExp = parseConstExp();
                    constInitVal.addConstExp(constExp);
                }
                if (this.curToken.getType() == TokenType.RBRACE) {
                    getNextToken();
                }
            } else {
                getNextToken();
            }
        } else if(this.curToken.getType() == TokenType.STRCON) {
            constInitVal.setStringConst(this.curToken.getValue());
            getNextToken();
        } else {
            ConstExp constExp = parseConstExp();
            constInitVal.addConstExp(constExp);
        }
        if(isOutput) {
//        System.out.println("<ConstInitVal>");
            parserWriter.write("<ConstInitVal>\n");
        }
        return constInitVal;
    }

    public BType parseBType() throws IOException {
        BType bType = new BType(this.curToken.getType());
        getNextToken();
        return bType;
    }

    public void reWriteValue(ArrayList<FuncFParam> funcFParams){
        SymbolTable symbolTable = symbolTables.peek();

        for(FuncFParam funcFParam : funcFParams) {
            Symbol curSymbol = symbolTable.getDirectory().get(funcFParam.getIdent().getValue());
            Value value = null;
            String regName = module.getCurFunction().allocateReg();
            switch (funcFParam.getFuncFParamSymbolType()) {
                case Int:
                    IrAlloca irAlloca = new IrAlloca(regName, 0, 1);
                    module.getCurBasicBlock().addInstruction(irAlloca);
                    IrStore irStore = new IrStore(curSymbol.getValue(), irAlloca);
                    module.getCurBasicBlock().addInstruction(irStore);
                    value = new Value(regName, 0);
                    curSymbol.setValue(value);
                    break;
                case Char:
                    irAlloca = new IrAlloca(regName, 1, 1);
                    module.getCurBasicBlock().addInstruction(irAlloca);
                    irStore = new IrStore(curSymbol.getValue(), irAlloca);
                    module.getCurBasicBlock().addInstruction(irStore);
                    value = new Value(regName, 1);
                    curSymbol.setValue(value);
                    break;
                case IntArray:
                    irAlloca = new IrAlloca(regName, 2, 1);
                    module.getCurBasicBlock().addInstruction(irAlloca);
                    irStore = new IrStore(curSymbol.getValue(), irAlloca);
                    module.getCurBasicBlock().addInstruction(irStore);
                    regName = module.getCurFunction().allocateReg();
                    IrLoad irLoad = new IrLoad(regName, 2, irAlloca);
                    module.getCurBasicBlock().addInstruction(irLoad);
                    value = new Value(regName, 2);
                    curSymbol.setValue(value);
                    break;
                case CharArray:
                    irAlloca = new IrAlloca(regName, 3, 1);
                    module.getCurBasicBlock().addInstruction(irAlloca);
                    irStore = new IrStore(curSymbol.getValue(), irAlloca);
                    module.getCurBasicBlock().addInstruction(irStore);
                    regName = module.getCurFunction().allocateReg();
                    irLoad = new IrLoad(regName, 3, irAlloca);
                    module.getCurBasicBlock().addInstruction(irLoad);
                    value = new Value(regName, 3);
                    curSymbol.setValue(value);
                    break;
                default:
                    break;
            }
        }
    }

    // FuncDef → FuncType Ident '(' [FuncFParams] ')' Block // b g j
    public FuncDef parseFuncDef() throws IOException {
        FuncDef funcDef = new FuncDef();
        FuncType funcType = parseFuncType();
        if(funcType.getTokenType() == TokenType.VOIDTK) {
            isVoidFunc = true;
        }
        funcDef.setFuncType(funcType);
        SymbolTable symbolTable = symbolTables.peek();
        if (this.curToken.getType() == TokenType.IDENFR) {
            funcDef.setIdent(this.curToken);
            if (symbolTable.getDirectory().containsKey(funcDef.getIdent().getValue())) {
                errors.add(new ErrorInfo('b', funcDef.getIdent().getLine()));
                System.out.println("Error: b" + funcDef.getIdent().getLine());
            }

            Symbol symbol = funcDef.getSymbol(symbolTable.getId(), false);
            symbol.setFunc(true);
            symbolTable.addSymbol(symbol);

            getNextToken();
            if (this.curToken.getType() == TokenType.LPARENT) {
                getNextToken();
                if (this.curToken.getType() != TokenType.RPARENT && this.curToken.getType() != TokenType.LBRACE) {
                    FuncFParams funcFParams = parseFuncFParams();
                    funcDef.setFuncFParams(funcFParams);
                }
                if (this.curToken.getType() == TokenType.RPARENT) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                    System.out.println("Error: j" + getNextToken(-1).getLine());
                }
            }
            BasicBlock basicBlock = new BasicBlock(module.getCurFunction().allocateReg());
            module.setCurBasicBlock(basicBlock);

            if(funcDef.getFuncFParams() != null) {
                reWriteValue(funcDef.getFuncFParams().getFuncFParams());
            }

            Block block = parseBlock();
            funcDef.setBlock(block);
        }
        isVoidFunc = false;

        if (funcDef.isLackReturn()) {
            errors.add(new ErrorInfo('g', getNextToken(-1).getLine()));
            System.out.println("Error: g" + getNextToken(-1).getLine());
        }
//        if (funcDef.isSuperfluousReturn()) {
//            errors.add(new ErrorInfo('f', getNextToken(-2).getLine()));
//            System.out.println("Error: f" + getNextToken(-2).getLine());
//        }
        if(isOutput) {
//        System.out.println("<FuncDef>");
            parserWriter.write("<FuncDef>\n");
        }
        return funcDef;
    }

    // FuncType → 'void' | 'int' | 'char'
    public FuncType parseFuncType() throws IOException {
        FuncType funcType = new FuncType(this.curToken.getType());
        getNextToken();
        if(isOutput) {
//        System.out.println("<FuncType>");
            parserWriter.write("<FuncType>\n");
        }
        return funcType;
    }

    // MainFuncDef → 'int' 'main' '(' ')' Block // j
    public MainFuncDef parseMainFuncDef() throws IOException {
        if (this.curToken.getType() == TokenType.INTTK) {
            getNextToken();
            if (this.curToken.getType() == TokenType.MAINTK) {
                getNextToken();
                if (this.curToken.getType() == TokenType.LPARENT) {
                    getNextToken();
                    if (this.curToken.getType() == TokenType.RPARENT) {
                        getNextToken();
                    } else {
                        errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                        System.out.println("Error: j" + getNextToken(-1).getLine());
                    }
                    Block block = parseBlock();
                    if (!block.lastIsReturn()) {
                        errors.add(new ErrorInfo('g', getNextToken(-1).getLine()));
                        System.out.println("Error: g" + getNextToken(-1).getLine());
                    }
                    if(isOutput) {
//                    System.out.println("<MainFuncDef>");
                        parserWriter.write("<MainFuncDef>\n");
                    }
                    return new MainFuncDef(block);
                }
            }
        }
        return null;
    }

    // FuncFParams → FuncFParam { ',' FuncFParam }
    public FuncFParams parseFuncFParams() throws IOException {
        FuncFParams funcFParams = new FuncFParams();

        SymbolTable symbolTable = new SymbolTable(this.id, symbolTables.peek().getId());
        symbolTable.setFunc(true);
        symbolTables.peek().addChild(symbolTable);
        symbolTables.push(symbolTable);

        FuncFParam funcFParam = parseFuncFParam();
        funcFParams.addFuncFParam(funcFParam);
        while (this.curToken.getType() == TokenType.COMMA) {
            getNextToken();
            funcFParam = parseFuncFParam();
            funcFParams.addFuncFParam(funcFParam);
        }
        if(isOutput) {
//        System.out.println("<FuncFParams>");
            parserWriter.write("<FuncFParams>\n");
        }
        return funcFParams;
    }

    // FuncFParam → BType Ident ['[' ']'] // k
    public FuncFParam parseFuncFParam() throws IOException {
        FuncFParam funcFParam = new FuncFParam();
        funcFParam.setBType(parseBType());

        if (this.curToken.getType() == TokenType.IDENFR) {
            funcFParam.setIdent(this.curToken);
            getNextToken();
            if (this.curToken.getType() == TokenType.LBRACK) {
                getNextToken();
                funcFParam.setIsArray();
                if (this.curToken.getType() == TokenType.RBRACK) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('k', getNextToken(-1).getLine()));
                    System.out.println("Error: k" + getNextToken(-1).getLine());
                }
            }
        }

        SymbolTable symbolTable = symbolTables.peek();
        if (symbolTable.getDirectory().containsKey(funcFParam.getIdent().getValue())) {
            errors.add(new ErrorInfo('b', funcFParam.getIdent().getLine()));
            System.out.println("Error: b" + funcFParam.getIdent().getLine());
        } else {
            Symbol symbol = funcFParam.getSymbol(symbolTable.getId(), true);
            symbolTable.addSymbol(symbol);
        }
        if(isOutput) {
//        System.out.println("<FuncFParam>");
            parserWriter.write("<FuncFParam>\n");
        }
        return funcFParam;
    }

    // Block → '{' { BlockItem } '}'
    public Block parseBlock() throws IOException {
        Block block = new Block();
        if(!symbolTables.peek().isFunc()) {
            SymbolTable symbolTable = new SymbolTable(this.id, symbolTables.peek().getId());
            symbolTables.peek().addChild(symbolTable);
            symbolTables.push(symbolTable);
        }
        symbolTables.peek().setFunc(false);
        this.id++;

        if (this.curToken.getType() == TokenType.LBRACE) {
            getNextToken();
            while (this.curToken.getType() != TokenType.RBRACE) {
                BlockItem blockItem = parseBlockItem();
                block.addBlockItem(blockItem);
            }
            if (this.curToken.getType() == TokenType.RBRACE) {
                getNextToken();
            }
        }
        symbolTables.pop();
        if(isOutput) {
//        System.out.println("<Block>");
            parserWriter.write("<Block>\n");
        }
        return block;
    }

    // BlockItem → Decl | Stmt
    public BlockItem parseBlockItem() throws IOException {
        if (this.curToken.getType() == TokenType.INTTK || this.curToken.getType() == TokenType.CHARTK || this.curToken.getType() == TokenType.CONSTTK) {
            Decl decl = parseDecl();
            decl.genLLVM();
            return new BlockItem(decl);
        } else {
            Stmt stmt = parseStmt();
            return new BlockItem(stmt);
        }
    }

    public boolean tryParseLVal() throws IOException {
        this.isOutput = false;
        int pos = this.curPos;
        if (this.curToken.getType() == TokenType.IDENFR) {
            getNextToken();
            if (this.curToken.getType() == TokenType.LBRACK) {
                getNextToken();
                parseExp();
            }
            if(this.curToken.getType() == TokenType.RBRACK) {
                getNextToken();
            }
            if(this.curToken.getType() == TokenType.ASSIGN) {
                this.curPos = pos;
                this.curToken = this.tokens.get(this.curPos);
                this.isOutput = true;
                return true;
            }
        }
        this.curPos = pos;
        this.curToken = this.tokens.get(this.curPos);
        this.isOutput = true;
        return false;
    }

    // 语句 Stmt → LVal '=' Exp ';' // i
    // | [Exp] ';' // i
    // | Block
    // | 'if' '(' Cond ')' Stmt [ 'else' Stmt ] // j
    // | 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt
    // | 'break' ';' | 'continue' ';' // i
    // | 'return' [Exp] ';' // i
    // | LVal '=' 'getint''('')'';' // i j
    // | LVal '=' 'getchar''('')'';' // i j
    // | 'printf''('StringConst {','Exp}')'';' // i j
    public Stmt parseStmt() throws IOException {
        Stmt stmt = new Stmt();
        if (this.curToken.getType() == TokenType.IFTK) {
            ArrayList<IrBr> irBrs = new ArrayList<>();
            stmt.setType(3);
            getNextToken();
            if (this.curToken.getType() == TokenType.LPARENT) {
                getNextToken();
                stmt.setIfCond(parseCond());
                irBrs = stmt.genLLVM();
                if (this.curToken.getType() == TokenType.RPARENT) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                    System.out.println("Error: j" + getNextToken(-1).getLine());
                }
                stmt.setIfStmt(parseStmt());

                IrBr irBr = new IrBr(new Value("1", 0), "ifEndLabel", "");
                module.getCurBasicBlock().addInstruction(irBr);
                irBrs.add(irBr);

                if (this.curToken.getType() == TokenType.ELSETK) {
                    getNextToken();

                    module.getCurFunction().allocateBasicBlock();

                    for (IrBr irBr1 : irBrs) {
                        irBr1.backFill(module.getCurBasicBlock(), "elseStmtLabel");
                    }

                    stmt.setElseStmt(parseStmt());

                    irBr = new IrBr(new Value("1", 0), "ifEndLabel", "");
                    module.getCurBasicBlock().addInstruction(irBr);
                    irBrs.add(irBr);
                }

                module.getCurFunction().allocateBasicBlock();

                for (IrBr irBr1 : irBrs) {
                    irBr1.backFill(module.getCurBasicBlock(), "ifEndLabel");
                }

                for (IrBr irBr1 : irBrs) {
                    irBr1.backFill(module.getCurBasicBlock(), "elseStmtLabel");
                }
            }
        } else if (this.curToken.getType() == TokenType.FORTK) {
            ArrayList<IrBr> irBrs = new ArrayList<>();
            stmt.setType(4);
            getNextToken();
            if (this.curToken.getType() == TokenType.LPARENT) {
                getNextToken();
                if (this.curToken.getType() == TokenType.SEMICN) {
                    getNextToken();
                } else {
                    stmt.setForStmt1(parseForStmt());
                    getNextToken();
                }
                if (this.curToken.getType() == TokenType.SEMICN) {
                    getNextToken();
                } else {
                    stmt.setForCond(parseCond());
                    getNextToken();
                }
                irBrs = stmt.genLLVM();
                if (this.curToken.getType() == TokenType.RPARENT) {
                    getNextToken();
                } else {
                    stmt.setForStmt2(parseForStmt());
                    getNextToken();
                }
                Value condLabel = irBrs.get(irBrs.size() - 1).getValue();
                irBrs.remove(irBrs.size() - 1);

                stackOfIrBrs.push(irBrs);

                isCircle = true;
                stmt.setForStmt(parseStmt());
                isCircle = false;

                module.getCurFunction().allocateBasicBlock();

                for (IrBr irBr : irBrs) {
                    irBr.backFill(module.getCurBasicBlock(), "forStmtLabel");
                }
                if(stmt.getForStmt2() != null) {
                    stmt.getForStmt2().genLLVM();
                }

                IrBr irBr = new IrBr(new Value("1", 0), condLabel);
                module.getCurBasicBlock().addInstruction(irBr);
                irBrs.add(irBr);

                module.getCurFunction().allocateBasicBlock();

                for (IrBr irBr1 : irBrs) {
                    irBr1.backFill(module.getCurBasicBlock(), "forEndLabel");
                }
                stackOfIrBrs.pop();
            }
        } else if (this.curToken.getType() == TokenType.BREAKTK) {
            stmt.setType(5);
            if(!isCircle) {
                errors.add(new ErrorInfo('m', this.curToken.getLine()));
                System.out.println("Error: m" + this.curToken.getLine());
            }
            getNextToken();
            if (this.curToken.getType() == TokenType.SEMICN) {
                getNextToken();
            } else {
                errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                System.out.println("Error: i" + getNextToken(-1).getLine());
            }
            IrBr irBr = new IrBr(new Value("1", 0), "forEndLabel", "");
            module.getCurBasicBlock().addInstruction(irBr);
            stackOfIrBrs.peek().add(irBr);
        } else if (this.curToken.getType() == TokenType.CONTINUETK) {
            stmt.setType(6);
            if(!isCircle) {
                errors.add(new ErrorInfo('m', this.curToken.getLine()));
                System.out.println("Error: m" + this.curToken.getLine());
            }
            getNextToken();
            if (this.curToken.getType() == TokenType.SEMICN) {
                getNextToken();
            } else {
                errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                System.out.println("Error: i" + getNextToken(-1).getLine());
            }
            IrBr irBr = new IrBr(new Value("1", 0), "forStmtLabel", "");
            module.getCurBasicBlock().addInstruction(irBr);
            stackOfIrBrs.peek().add(irBr);
        } else if (this.curToken.getType() == TokenType.RETURNTK) {
            stmt.setType(7);
            stmt.setContainReturn();
            getNextToken();

            if (this.curToken.getType() != TokenType.SEMICN) {
                stmt.addExp(parseExp());
            }
            if (this.curToken.getType() == TokenType.SEMICN) {
                getNextToken();
            } else {
                errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                System.out.println("Error: i" + getNextToken(-1).getLine());
            }
            if(isVoidFunc && stmt.isReturnHaveExp()) {
                errors.add(new ErrorInfo('f', getNextToken(-2).getLine()));
                System.out.println("Error: f" + getNextToken(-2).getLine());
            }
            stmt.genLLVM();
        } else if (this.curToken.getType() == TokenType.PRINTFTK) {
            stmt.setType(10);
            int line = this.curToken.getLine();
            getNextToken();
            if (this.curToken.getType() == TokenType.LPARENT) {
                getNextToken();
                if (this.curToken.getType() == TokenType.STRCON) {
                    stmt.setStringConst(this.curToken.getValue());
                    getNextToken();
                    while (this.curToken.getType() == TokenType.COMMA) {
                        getNextToken();
                        stmt.addExp(parseExp());
                    }
                    if (this.curToken.getType() == TokenType.RPARENT) {
                        getNextToken();
                        if (this.curToken.getType() == TokenType.SEMICN) {
                            getNextToken();
                        } else {
                            errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                            System.out.println("Error: i" + getNextToken(-1).getLine());
                        }
                    } else {
                        errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                        System.out.println("Error: j" + getNextToken(-1).getLine());
                    }
                }
            }
            if (!stmt.isSameInPrintf()) {
                errors.add(new ErrorInfo('l', line));
                System.out.println("Error: l" + line);
            }
            stmt.genLLVM();
        } else if (this.curToken.getType() == TokenType.LBRACE) {
            stmt.setType(2);
            stmt.setBlock(parseBlock());
        } else if (tryParseLVal()) {  //Lval
            boolean flag = false;
            ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
            while (iterator.hasPrevious()) {
                SymbolTable tmp = iterator.previous();
                if(tmp.getDirectory().containsKey(this.curToken.getValue())){
                    if(tmp.getDirectory().get(this.curToken.getValue()).isConst()){
                        flag = true;
                    }
                    break;
                }
            }
             if (flag) {
                    errors.add(new ErrorInfo('h', this.curToken.getLine()));
                    System.out.println("Error: h" + this.curToken.getLine());
             }
            stmt.setLVal(parseLVal());
            if (this.curToken.getType() == TokenType.ASSIGN) {
                getNextToken();
                if (this.curToken.getType() == TokenType.GETINTTK || this.curToken.getType() == TokenType.GETCHARTK) {
                    if(this.curToken.getType() == TokenType.GETINTTK){
                        stmt.setType(8);
                    } else {
                        stmt.setType(9);
                    }

                    getNextToken();
                    if (this.curToken.getType() == TokenType.LPARENT) {
                        getNextToken();
                        if (this.curToken.getType() == TokenType.RPARENT) {
                            getNextToken();
                            if (this.curToken.getType() == TokenType.SEMICN) {
                                getNextToken();
                            } else {
                                errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                                System.out.println("Error: i" + getNextToken(-1).getLine());
                            }
                        } else {
                            errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                            System.out.println("Error: j" + getNextToken(-1).getLine());
                        }
                    }
                } else {
                    stmt.setType(0);
                    stmt.addExp(parseExp());
                    if (this.curToken.getType() == TokenType.SEMICN) {
                        getNextToken();
                    } else {
                        errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                        System.out.println("Error: i" + getNextToken(-1).getLine());
                    }
                }
            }
            stmt.genLLVM();
        } else {
            stmt.setType(1);
            if (this.curToken.getType() != TokenType.SEMICN) {
                stmt.addExp(parseExp());
                if (this.curToken.getType() != TokenType.SEMICN) {
                    errors.add(new ErrorInfo('i', getNextToken(-1).getLine()));
                    System.out.println("Error: i" + getNextToken(-1).getLine());
                } else {
                    getNextToken();
                }
            } else {
                getNextToken();
            }
            stmt.genLLVM();
        }
        if(isOutput) {
//        System.out.println("<Stmt>");
            parserWriter.write("<Stmt>\n");
        }
        return stmt;
    }

    // ForStmt → LVal '=' Exp
    public ForStmt parseForStmt() throws IOException {
        ForStmt forStmt = new ForStmt();
        boolean flag = false;
        ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
        while (iterator.hasPrevious()) {
            SymbolTable tmp = iterator.previous();
            if(tmp.getDirectory().containsKey(this.curToken.getValue())){
                if(tmp.getDirectory().get(this.curToken.getValue()).isConst()){
                    flag = true;
                }
                break;
            }
        }
        if (flag) {
            errors.add(new ErrorInfo('h', this.curToken.getLine()));
            System.out.println("Error: h" + this.curToken.getLine());
        }
        forStmt.setLVal(parseLVal());
        if (this.curToken.getType() == TokenType.ASSIGN) {
            getNextToken();
            forStmt.setExp(parseExp());
        }
        if(isOutput) {
//        System.out.println("<ForStmt>");
            parserWriter.write("<ForStmt>\n");
        }
        return forStmt;
    }

    // Exp → AddExp
    public Exp parseExp() throws IOException {
        this.isOutput = false;
        int pos = this.curPos;
        int type = -1;
        while(this.curToken.getValue().equals("(")){
            getNextToken();
        }
        if(this.curToken.getType() == TokenType.IDENFR){
            ListIterator<SymbolTable> iterator = symbolTables.listIterator(symbolTables.size());
            while (iterator.hasPrevious()) {
                SymbolTable tmp = iterator.previous();
                if(tmp.getDirectory().containsKey(this.curToken.getValue())){
                    SymbolType tp = tmp.getDirectory().get(this.curToken.getValue()).getType();
                    if(tp == SymbolType.Char || tp == SymbolType.Int || tp == SymbolType.ConstInt || tp == SymbolType.ConstChar){
                        type = 0;
                    } else if(tp == SymbolType.IntArray || tp == SymbolType.ConstIntArray) {
                        type = 1;
                    } else if(tp == SymbolType.CharArray || tp == SymbolType.ConstCharArray) {
                        type = 2;
                    }
                    getNextToken();
                    break;
                }
            }
        }
        if (this.curToken.getType() == TokenType.CHRCON || this.curToken.getType() == TokenType.INTCON) {
            type = 0;
        }
        if(this.curToken.getType() == TokenType.LBRACK){
            type = 0;
            getNextToken();
        }
        this.curPos = pos;
        this.curToken = this.tokens.get(pos);
        this.isOutput = true;

        Exp exp = new Exp(parseAddExp());
        exp.setType(type);
        if(isOutput) {
//        System.out.println("<Exp>");
            parserWriter.write("<Exp>\n");
        }
        return exp;
    }

    // Cond → LOrExp
    public Cond parseCond() throws IOException {
        Cond cond = new Cond(parseLOrExp());
        if(isOutput) {
//        System.out.println("<Cond>");
            parserWriter.write("<Cond>\n");
        }
        return cond;
    }

    // LVal → Ident ['[' Exp ']'] // k
    public LVal parseLVal() throws IOException {
        LVal lVal = new LVal();
        if (this.curToken.getType() == TokenType.IDENFR) {
            boolean flag = false;
            for (SymbolTable symbolTable : symbolTables) {
                if (symbolTable.getDirectory().containsKey(this.curToken.getValue())) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                errors.add(new ErrorInfo('c', this.curToken.getLine()));
                System.out.println("Error: c" + this.curToken.getLine());
            }
            lVal.setIdent(this.curToken);
            getNextToken();
            if (this.curToken.getType() == TokenType.LBRACK) {
                getNextToken();
                lVal.setExp(parseExp());
                if (this.curToken.getType() == TokenType.RBRACK) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('k', getNextToken(-1).getLine()));
                    System.out.println("Error: k" + getNextToken(-1).getLine());
                }
            }
        }
        if(isOutput) {
//        System.out.println("<LVal>");
            parserWriter.write("<LVal>\n");
        }
        return lVal;
    }

    // PrimaryExp → '(' Exp ')' | LVal | Number | Character// j
    public PrimaryExp parsePrimaryExp() throws IOException {
        PrimaryExp primaryExp = new PrimaryExp();
        if (this.curToken.getType() == TokenType.LPARENT) {
            getNextToken();
            primaryExp.setExp(parseExp());
            if (this.curToken.getType() == TokenType.RPARENT) {
                getNextToken();
            } else {
                errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                System.out.println("Error: j" + getNextToken(-1).getLine());
            }
        } else if (this.curToken.getType() == TokenType.INTCON) {
            primaryExp.setNumber(this.curToken.getValue());
            getNextToken();
            if(isOutput) {
//            System.out.println("<Number>");
                parserWriter.write("<Number>\n");
            }
        } else if (this.curToken.getType() == TokenType.CHRCON) {
            primaryExp.setCharacter(this.curToken.getValue());
            getNextToken();
            if(isOutput) {
//            System.out.println("<Character>");
                parserWriter.write("<Character>\n");
            }
        } else {
            primaryExp.setLVal(parseLVal());
        }
        if(isOutput) {
//        System.out.println("<PrimaryExp>");
            parserWriter.write("<PrimaryExp>\n");
        }
        return primaryExp;
    }

    public ArrayList<Symbol> getParams(String name) {
        ArrayList<Symbol> params = new ArrayList<>();
        SymbolTable symbolTable = symbolTables.get(0);
        int index = 0;
        for(Symbol symbol : symbolTable.getSymbols()) {
            if(symbol.getName().equals(name)) {
                SymbolTable symbolTable1 = symbolTable.getChildren().get(index);
                for(Symbol symbol1 : symbolTable1.getSymbols()) {
                    if(symbol1.isParam()) {
                        params.add(symbol1);
                    } else {
                        return params;
                    }
                }
                break;
            }
            if(symbol.isFunc()) {
                index++;
            }
        }
        return params;
    }

    // UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp // j
    public UnaryExp parseUnaryExp() throws IOException {
        UnaryExp unaryExp = new UnaryExp();
        ArrayList<Symbol> params = new ArrayList<>();
        if (this.curToken.getType() == TokenType.IDENFR && getNextToken(1).getType() == TokenType.LPARENT) {
            unaryExp.setIdent(this.curToken);
            boolean flag = false;
            for (SymbolTable symbolTable : symbolTables) {
                if (symbolTable.getDirectory().containsKey(this.curToken.getValue())) {
                    params = getParams(this.curToken.getValue());
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                errors.add(new ErrorInfo('c', this.curToken.getLine()));
                System.out.println("Error: c" + this.curToken.getLine());
            }
            getNextToken();
            if (this.curToken.getType() == TokenType.LPARENT) {
                getNextToken();
                FuncRParams funcRParams = new FuncRParams();
                if (this.curToken.getType() != TokenType.RPARENT && this.curToken.getType() != TokenType.SEMICN) {
                    funcRParams = parseFuncRParams();
                    unaryExp.setFuncRParams(funcRParams);
                }
                if(params.size() != funcRParams.getExps().size()) {
                    errors.add(new ErrorInfo('d', this.curToken.getLine()));
                    System.out.println("Error: d" + this.curToken.getLine());
                } else {
                    boolean flg = false;
                    for(int i = 0; i < params.size(); i++) {
                        SymbolType type = params.get(i).getType();
                        if(funcRParams.getExps().get(i).getType() == 1) {
                            if(type == SymbolType.Int || type == SymbolType.Char || type == SymbolType.ConstInt || type == SymbolType.ConstChar) {
                                flg = true;
                                break;
                            } else if (type == SymbolType.CharArray || type == SymbolType.ConstCharArray) {
                                flg = true;
                                break;
                            }
                        } else if(funcRParams.getExps().get(i).getType() == 2) {
                            if(type == SymbolType.Int || type == SymbolType.Char || type == SymbolType.ConstInt || type == SymbolType.ConstChar) {
                                flg = true;
                                break;
                            } else if (type == SymbolType.IntArray || type == SymbolType.ConstIntArray) {
                                flg = true;
                                break;
                            }
                        } else if(funcRParams.getExps().get(i).getType() == 0) {
                            if (type == SymbolType.IntArray || type == SymbolType.ConstIntArray || type == SymbolType.CharArray || type == SymbolType.ConstCharArray) {
                                flg = true;
                                break;
                            }
                        }
                    }
                    if(flg) {
                        errors.add(new ErrorInfo('e', this.curToken.getLine()));
                        System.out.println("Error: e" + this.curToken.getLine());
                    }
                }
                if (this.curToken.getType() == TokenType.RPARENT) {
                    getNextToken();
                } else {
                    errors.add(new ErrorInfo('j', getNextToken(-1).getLine()));
                    System.out.println("Error: j" + getNextToken(-1).getLine());
                }
            }

        } else if (this.curToken.getType() == TokenType.PLUS || this.curToken.getType() == TokenType.MINU || this.curToken.getType() == TokenType.NOT) {
            UnaryOp unaryOp = parseUnaryOp();
            unaryExp.setUnaryOp(unaryOp);
            unaryExp.setUnaryExp(parseUnaryExp());
        } else {
            unaryExp.setPrimaryExp(parsePrimaryExp());
        }
        if(isOutput) {
//        System.out.println("<UnaryExp>");
            parserWriter.write("<UnaryExp>\n");
        }
        return unaryExp;
    }

    // UnaryOp → '+' | '−' | '!' 注：'!'仅出现在条件表达式中
    public UnaryOp parseUnaryOp() throws IOException {
        UnaryOp unaryOp = new UnaryOp(this.curToken.getType());
        getNextToken();
        if(isOutput) {
//        System.out.println("<UnaryOp>");
            parserWriter.write("<UnaryOp>\n");
        }
        return unaryOp;
    }

    // FuncRParams → Exp { ',' Exp }
    public FuncRParams parseFuncRParams() throws IOException {
        FuncRParams funcRParams = new FuncRParams();
        Exp exp = parseExp();
        funcRParams.addExp(exp);
        while (this.curToken.getType() == TokenType.COMMA) {
            getNextToken();
            exp = parseExp();
            funcRParams.addExp(exp);
        }
        if(isOutput) {
//        System.out.println("<FuncRParams>");
            parserWriter.write("<FuncRParams>\n");
        }
        return funcRParams;
    }

    // MulExp → UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
    public MulExp parseMulExp() throws IOException {
        MulExp mulExp = new MulExp();
        mulExp.addUnaryExp(parseUnaryExp());
        if(isOutput) {
//        System.out.println("<MulExp>");
            parserWriter.write("<MulExp>\n");
        }
        while (this.curToken.getType() == TokenType.MULT || this.curToken.getType() == TokenType.DIV || this.curToken.getType() == TokenType.MOD) {
            mulExp.addTokenType(this.curToken.getType());
            getNextToken();
            mulExp.addUnaryExp(parseUnaryExp());
            if(isOutput) {
//            System.out.println("<MulExp>");
                parserWriter.write("<MulExp>\n");
            }
        }
        return mulExp;
    }

    // AddExp → MulExp | AddExp ('+' | '−') MulExp
    public AddExp parseAddExp() throws IOException {
        AddExp addExp = new AddExp();
        addExp.addMulExp(parseMulExp());
        if(isOutput) {
//        System.out.println("<AddExp>");
            parserWriter.write("<AddExp>\n");
        }
        while (this.curToken.getType() == TokenType.PLUS || this.curToken.getType() == TokenType.MINU) {
            addExp.addTokenType(this.curToken.getType());
            getNextToken();
            addExp.addMulExp(parseMulExp());
            if(isOutput) {
//            System.out.println("<AddExp>");
                parserWriter.write("<AddExp>\n");
            }
        }
        return addExp;
    }

    // RelExp → AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
    public RelExp parseRelExp() throws IOException {
        RelExp relExp = new RelExp();
        relExp.addAddExp(parseAddExp());
        if(isOutput) {
//        System.out.println("<RelExp>");
            parserWriter.write("<RelExp>\n");
        }
        while (this.curToken.getType() == TokenType.LSS || this.curToken.getType() == TokenType.LEQ || this.curToken.getType() == TokenType.GRE || this.curToken.getType() == TokenType.GEQ) {
            relExp.addTokenType(this.curToken.getType());
            getNextToken();
            relExp.addAddExp(parseAddExp());
            if(isOutput) {
//            System.out.println("<RelExp>");
                parserWriter.write("<RelExp>\n");
            }
        }
        return relExp;
    }

    // EqExp → RelExp | EqExp ('==' | '!=') RelExp
    public EqExp parseEqExp() throws IOException {
        EqExp eqExp = new EqExp();
        eqExp.addRelExp(parseRelExp());
        if(isOutput) {
//        System.out.println("<EqExp>");
            parserWriter.write("<EqExp>\n");
        }
        while (this.curToken.getType() == TokenType.EQL || this.curToken.getType() == TokenType.NEQ) {
            eqExp.addTokenType(this.curToken.getType());
            getNextToken();
            eqExp.addRelExp(parseRelExp());
            if(isOutput) {
//            System.out.println("<EqExp>");
                parserWriter.write("<EqExp>\n");
            }
        }
        return eqExp;
    }

    // LAndExp → EqExp | LAndExp '&&' EqExp
    public LAndExp parseLAndExp() throws IOException {
        LAndExp lAndExp = new LAndExp();
        lAndExp.addEqExp(parseEqExp());
        if(isOutput) {
//        System.out.println("<LAndExp>");
            parserWriter.write("<LAndExp>\n");
        }
        while (this.curToken.getType() == TokenType.AND) {
            getNextToken();
            lAndExp.addEqExp(parseEqExp());
            if(isOutput) {
//            System.out.println("<LAndExp>");
                parserWriter.write("<LAndExp>\n");
            }
        }
        return lAndExp;
    }

    // LOrExp → LAndExp | LOrExp '||' LAndExp
    public LOrExp parseLOrExp() throws IOException {
        LOrExp lOrExp = new LOrExp();
        lOrExp.addLAndExp(parseLAndExp());
        if(isOutput) {
//        System.out.println("<LOrExp>");
            parserWriter.write("<LOrExp>\n");
        }
        while (this.curToken.getType() == TokenType.OR) {
            getNextToken();
            lOrExp.addLAndExp(parseLAndExp());
            if(isOutput) {
//            System.out.println("<LOrExp>");
                parserWriter.write("<LOrExp>\n");
            }
        }
        return lOrExp;
    }

    // ConstExp → AddExp 注：使用的 Ident 必须是常量
    public ConstExp parseConstExp() throws IOException {
        ConstExp constExp = new ConstExp(parseAddExp());
        if(isOutput) {
//        System.out.println("<ConstExp>");
            parserWriter.write("<ConstExp>\n");
        }
        return constExp;
    }
}
