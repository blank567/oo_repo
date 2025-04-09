package frontend.SyntaxTree;

import midend.Value;

public class PrimaryExp {
    private Exp exp;
    private LVal lVal;
    private String number;
    private String character;
    private int flag;

    public PrimaryExp() {
        flag = -1;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
        flag = 0;
    }

    public void setLVal(LVal lVal) {
        this.lVal = lVal;
        flag = 1;
    }

    public void setNumber(String number) {
        this.number = number;
        flag = 2;
    }

    public void setCharacter(String character) {
        this.character = character;
        flag = 3;
    }

    public int calPrimaryExp() {
        if (flag == 0) {
            return exp.calExp();
        } else if (flag == 1) {
            return lVal.calLVal();
        } else if (flag == 2) {
            return Integer.parseInt(number);
        } else if (flag == 3) {
            return getChar();
        } else {
            System.out.println("PrimaryExp calPrimaryExp error");
            return 0;
        }
    }

    public Value genLLVM() {
        if (flag == 0) {
            return exp.genLLVM();
        } else if (flag == 1) {
            return lVal.genLLVM();
        } else if (flag == 2) {
            return new Value(number, 0);
        } else if (flag == 3) {
            System.out.println(character);
            return new Value(String.valueOf(getChar()), 1);
        } else {
            System.out.println("PrimaryExp genLLVM error");
            return null;
        }
    }

    public int getChar() {
        if(character.length() == 1) {
            return character.charAt(0);
        } else if(character.length() == 2) {
            switch (character.charAt(1)) {
                case 'a':
                    return 7;
                case 'b':
                    return '\b';
                case 'f':
                    return '\f';
                case 'v':
                    return 11;
                case 'n':
                    return '\n';
                case 't':
                    return '\t';
                case '0':
                    return '\0';
                case '\\':
                    return '\\';
                case '\'':
                    return '\'';
                case '\"':
                    return '\"';
                default:
                    return 0;
            }
        }
        return 0;
    }
}
