package frontend;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Lexer {
    private int line;
    private char curChar;
    private int pos;
    private ArrayList<Token> tokens;
    private ArrayList<String> input;
    boolean isComment;
    ArrayList<ErrorInfo> errors;

    public Lexer(ArrayList<String> input) {
        this.line = 1;
        this.pos = 0;
        this.tokens = new ArrayList<>();
        this.input = input;
        this.isComment = false;
        this.errors = new ArrayList<>();
    }

    public ArrayList<ErrorInfo> getErrors() {
        return errors;
    }

    public ArrayList<Token> readLine(){
        for (String s : input) {
            this.pos = 0;
            getTokensOfLine(s);
            this.line++;
        }
//        System.out.println(tokens);
        return tokens;
    }

    public String getNumber(String s) {
        StringBuilder sb = new StringBuilder();
        while (pos < s.length() && Character.isDigit(s.charAt(pos))) {
            sb.append(s.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public String getIdent(String s) {
        StringBuilder sb = new StringBuilder();
        while (pos < s.length() && (Character.isLetter(s.charAt(pos)) || Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '_')) {
            sb.append(s.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public String getStringConst(String s) {
        StringBuilder sb = new StringBuilder();
//        sb.append(s.charAt(pos));
        pos++;
        while (pos < s.length() && s.charAt(pos) != '\"') {
            sb.append(s.charAt(pos));
            pos++;
        }
//        sb.append(s.charAt(pos));
        return sb.toString();
    }

    public String getCharConst(String s) {
        StringBuilder sb = new StringBuilder();
//        sb.append(s.charAt(pos));
        pos++;
        while (pos < s.length() && s.charAt(pos) != '\'') {
            if (s.charAt(pos) == '\\') {
                sb.append(s.charAt(pos));
                pos++;
            }
            sb.append(s.charAt(pos));
            pos++;
        }
//        sb.append(s.charAt(pos));
        return sb.toString();
    }

    public TokenType getTokenType(String s) {
        switch (s) {
            case "main":
                return TokenType.MAINTK;
            case "const":
                return TokenType.CONSTTK;
            case "int":
                return TokenType.INTTK;
            case "char":
                return TokenType.CHARTK;
            case "break":
                return TokenType.BREAKTK;
            case "continue":
                return TokenType.CONTINUETK;
            case "if":
                return TokenType.IFTK;
            case "else":
                return TokenType.ELSETK;
            case "for":
                return TokenType.FORTK;
            case "getint":
                return TokenType.GETINTTK;
            case "getchar":
                return TokenType.GETCHARTK;
            case "printf":
                return TokenType.PRINTFTK;
            case "return":
                return TokenType.RETURNTK;
            case "void":
                return TokenType.VOIDTK;
            default:
                return TokenType.IDENFR;
        }
    }

    public void getTokensOfLine(String s) {
        while (pos < s.length()) {
            curChar = s.charAt(pos);
            while (Character.isWhitespace(curChar)) {
                pos++;
                if (pos < s.length()) {
                    curChar = s.charAt(pos);
                } else {
                    break;
                }
            }
            if (isComment && curChar != '*') {
                pos++;
                continue;
            }
            if (curChar == '/') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '/') {
                    break;
                }
                else if (pos < s.length() && s.charAt(pos) == '*') {
                    isComment = true;
                } else {
                    Token token = new Token(TokenType.DIV, "/", line);
                    tokens.add(token);
                    pos--;
                }
            } else if(isComment && curChar == '*') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '/') {
                    isComment = false;
                    pos++;
                }
                pos--;
            } else if (Character.isDigit(curChar)) {
                Token token = new Token(TokenType.INTCON, getNumber(s), line);
                tokens.add(token);
                pos--;
            } else if (Character.isLetter(curChar) || curChar == '_') {
                String ident = getIdent(s);
                Token token = new Token(getTokenType(ident), ident, line);
                tokens.add(token);
                pos--;
            } else if(curChar == '\"') {
                Token token = new Token(TokenType.STRCON, getStringConst(s), line);
                tokens.add(token);
            } else if(curChar == '\'') {
                Token token = new Token(TokenType.CHRCON, getCharConst(s), line);
                tokens.add(token);
            } else if(curChar == '!') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '=') {
                    Token token = new Token(TokenType.NEQ, "!=", line);
                    tokens.add(token);
                } else {
                    pos--;
                    Token token = new Token(TokenType.NOT, "!", line);
                    tokens.add(token);
                }
            } else if(curChar == '&') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '&') {
                    Token token = new Token(TokenType.AND, "&&", line);
                    tokens.add(token);
                } else {
                    Token token = new Token(TokenType.AND, "&&", line);
                    tokens.add(token);
                    this.errors.add(new ErrorInfo('a', line));
                    pos--;
                }
            } else if(curChar == '|') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '|') {
                    Token token = new Token(TokenType.OR, "||", line);
                    tokens.add(token);
                } else {
                    Token token = new Token(TokenType.OR, "||", line);
                    tokens.add(token);
                    this.errors.add(new ErrorInfo('a', line));
                    pos--;
                }
            } else if(curChar == '+') {
                Token token = new Token(TokenType.PLUS, "+", line);
                tokens.add(token);
            } else if(curChar == '-') {
                Token token = new Token(TokenType.MINU, "-", line);
                tokens.add(token);
            } else if(curChar == '*') {
                Token token = new Token(TokenType.MULT, "*", line);
                tokens.add(token);
            } else if(curChar == '/') {
                Token token = new Token(TokenType.DIV, "/", line);
                tokens.add(token);
            } else if(curChar == '%') {
                Token token = new Token(TokenType.MOD, "%", line);
                tokens.add(token);
            } else if(curChar == '<') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '=') {
                    Token token = new Token(TokenType.LEQ, "<=", line);
                    tokens.add(token);
                } else {
                    pos--;
                    Token token = new Token(TokenType.LSS, "<", line);
                    tokens.add(token);
                }
            } else if(curChar == '=') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '=') {
                    Token token = new Token(TokenType.EQL, "==", line);
                    tokens.add(token);
                } else {
                    pos--;
                    Token token = new Token(TokenType.ASSIGN, "=", line);
                    tokens.add(token);
                }
            } else if(curChar == '>') {
                pos++;
                if (pos < s.length() && s.charAt(pos) == '=') {
                    Token token = new Token(TokenType.GEQ, ">=", line);
                    tokens.add(token);
                } else {
                    pos--;
                    Token token = new Token(TokenType.GRE, ">", line);
                    tokens.add(token);
                }
            } else if(curChar == ';') {
                Token token = new Token(TokenType.SEMICN, ";", line);
                tokens.add(token);
            } else if(curChar == ',') {
                Token token = new Token(TokenType.COMMA, ",", line);
                tokens.add(token);
            } else if(curChar == '(') {
                Token token = new Token(TokenType.LPARENT, "(", line);
                tokens.add(token);
            } else if(curChar == ')') {
                Token token = new Token(TokenType.RPARENT, ")", line);
                tokens.add(token);
            } else if(curChar == '[') {
                Token token = new Token(TokenType.LBRACK, "[", line);
                tokens.add(token);
            } else if(curChar == ']') {
                Token token = new Token(TokenType.RBRACK, "]", line);
                tokens.add(token);
            } else if(curChar == '{') {
                Token token = new Token(TokenType.LBRACE, "{", line);
                tokens.add(token);
            } else if(curChar == '}') {
                Token token = new Token(TokenType.RBRACE, "}", line);
                tokens.add(token);
            }
            pos++;
        }
    }
}
