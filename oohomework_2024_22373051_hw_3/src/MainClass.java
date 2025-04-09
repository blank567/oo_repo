import expression.Expr;
import expression.Mono;
import expression.Poly;

import java.math.BigInteger;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String tmp = scanner.nextLine();
            Preprocess process = new Preprocess(tmp);
            tmp = process.getInput();
            Replace.process(tmp);
        }
        String input = scanner.nextLine();
        Preprocess process = new Preprocess(input);
        input = process.getInput();
        //System.out.println(input);
        input = Replace.add(input);
        //System.out.println(input);

        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        Expr expr = parser.parseExpr();

        Poly answer = expr.getans();
        StringBuilder sb = print(answer);
        String ansString = sb.toString();
        ansString = ansString.replaceAll("-1\\*", "-");
        ansString = ansString.replaceAll("\\+1\\*", "\\+");
        System.out.println(ansString);
    }

    public static StringBuilder print(Poly answer) {
        StringBuilder sb = new StringBuilder();
        for (Mono mono : answer.getMonos()) {
            BigInteger i = mono.getExponent();
            BigInteger coe = mono.getCoe();
            if (coe.compareTo(BigInteger.ZERO) != 0) {
                if (i.equals(BigInteger.ZERO)) {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : "")).append(coe);
                } else if (i.equals(BigInteger.ONE)) {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : ""))
                            .append(coe.equals(BigInteger.ONE) ? "x" :
                                    coe.equals(BigInteger.valueOf(-1)) ? "-x" :
                                            coe + "*x");
                } else {
                    sb.append((coe.compareTo(BigInteger.ZERO) > 0 ? "+" : ""))
                            .append(coe.equals(BigInteger.ONE) ? "" :
                                    coe.equals(BigInteger.valueOf(-1)) ? "-" :
                                            coe + "*")
                            .append("x^").append(i);
                }
            }
            if (sb.length() == 0) {
                sb.append(0);
            }
            if (sb.charAt(0) == '+') {
                sb.deleteCharAt(0);
            }
            Poly expPoly = mono.getePoly();
            if (expPoly.getMonos().size() != 0 && coe.compareTo(BigInteger.ZERO) != 0) {
                sb.append("*exp((");
                sb.append(print(expPoly));
                sb.append("))");
            }
        }
        if (sb.length() == 0) {
            sb.append(0);
        }
        return sb;
    }
}
