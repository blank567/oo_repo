import expression.Expr;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Preprocess process = new Preprocess(input);
        //input = input.replaceAll("[ \t]", "");
        input = process.getInput();
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Expr expr = parser.parseExpr();
        HashMap<Integer, BigInteger> answer = expr.getans();
        print(answer);
    }

    public static void print(HashMap<Integer, BigInteger> answer) {
        StringBuilder sb = new StringBuilder();
        for (int i : answer.keySet()) {
            BigInteger coe = answer.get(i);
            if (coe.compareTo(BigInteger.ZERO) != 0) {
                if (i == 0) {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : "")).append(coe);
                } else if (i == 1) {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : ""))
                            .append(coe.equals(BigInteger.ONE) ? "x" :
                                    coe.equals(BigInteger.valueOf(-1)) ? "-x" : coe + "*x");
                } else {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : ""))
                            .append(coe.equals(BigInteger.ONE) ? "" :
                                    coe.equals(BigInteger.valueOf(-1)) ? "-" : coe + "*")
                            .append("x^").append(i);
                }
            }
        }

        if (sb.length() == 0) {
            sb.append(0);
        }
        if (sb.charAt(0) == '+') {
            sb.deleteCharAt(0);
        }
        String ansString = sb.toString();
        //ansString = ansString.replaceAll("1\\*", "");
        System.out.println(ansString);//0^0  2^2
    }
}
