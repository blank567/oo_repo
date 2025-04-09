import expression.Dx;
import expression.Exp;
import expression.Expr;
import expression.Factor;
import expression.Number;
import expression.Power;
import expression.Term;

import java.math.BigInteger;

public class Parser {
    private final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Expr parseExpr() {
        Expr expr = new Expr();
        Term term = parseTerm();
        expr.addTerm(term);
        while (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            boolean sub = lexer.peek().equals("-");
            lexer.next();
            term = parseTerm();
            if (sub) {
                term.subFactor();
            }
            expr.addTerm(term);
        }
        return expr;
    }

    public Term parseTerm() {
        Term term = new Term();
        while (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            boolean sub = lexer.peek().equals("-");
            lexer.next();
            if (sub) {
                term.subFactor();
            }
        }

        Factor factor = parseFactor();
        if (lexer.peek().equals("^")) {
            lexer.next();
            if (lexer.peek().equals("+")) {
                lexer.next();
            }
            BigInteger exponent = lexer.isNumber();
            if (!exponent.equals(BigInteger.valueOf(-1))) {
                lexer.next();
                for (int i = 0; i < exponent.intValue(); i++) {
                    term.addFactor(factor);
                }
            }
        } else {
            term.addFactor(factor);
        }

        while (lexer.peek().equals("*")) {
            lexer.next();
            factor = parseFactor();
            if (lexer.peek().equals("^")) {
                lexer.next();
                if (lexer.peek().equals("+")) {
                    lexer.next();
                }
                BigInteger exponent = lexer.isNumber();
                if (!exponent.equals(BigInteger.valueOf(-1))) {
                    lexer.next();
                    for (int i = 0; i < exponent.intValue(); i++) {
                        term.addFactor(factor);
                    }
                }
            } else {
                term.addFactor(factor);
            }
        }
        return term;
    }

    public Factor parseFactor() {
        if (lexer.peek().equals("x")) {
            lexer.next();
            if (lexer.peek().equals("^")) {
                lexer.next();
                return factorOfPower();
            }
            return new Power(BigInteger.ONE);
        } else if (lexer.peek().equals("(")) {
            lexer.next();
            Factor expr = parseExpr();
            lexer.next();
            return expr;
        } else if (lexer.peek().equals("e")) {
            lexer.next();
            lexer.next();
            lexer.next(); //"("
            lexer.next(); //if (lexer.peek().equals("("))
            Expr expr = parseExpr();
            lexer.next();
            return new Exp(expr);
        } else if (lexer.peek().equals("d")) {
            lexer.next();
            lexer.next();
            lexer.next();
            Expr expr = parseExpr();
            lexer.next();
            return new Dx(expr);
        } else {
            return factorOfNum();
        }
    }

    public Factor factorOfPower() {
        if (lexer.peek().equals("+")) {
            lexer.next();
        }
        BigInteger exponent = lexer.isNumber();
        if (!exponent.equals(BigInteger.valueOf(-1))) {
            lexer.next();
            if (exponent.equals(BigInteger.valueOf(0))) {
                return new Number(1);//特殊处理
            } else {
                //System.out.println(exponent);
                return new Power(exponent);
            }
        } else {
            //指数为负的情况，可以删去或修改
            System.out.println("Error input");
            return null;
        }
    }

    public Factor factorOfNum() {
        if (lexer.peek().equals("+") || lexer.peek().equals("-")) {
            boolean flag = lexer.peek().equals("-");
            lexer.next();
            BigInteger num = new BigInteger(lexer.peek());
            lexer.next();
            if (flag) {
                return new Number(num.negate());
            }
            return new Number(num);
        } else if (!lexer.isNumber().equals(BigInteger.valueOf(-1))) {
            BigInteger num = new BigInteger(lexer.peek());
            lexer.next();
            return new Number(num);
        } else {
            lexer.next();
            return new Number(1);
        }
    }
}