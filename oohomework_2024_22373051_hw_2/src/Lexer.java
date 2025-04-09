import java.math.BigInteger;

public class Lexer {
    private final String input;
    private int pos = 0;
    private String curToken;

    public Lexer(String input) {
        this.input = input;
        this.next();
    }

    public void next() {
        if (pos == input.length()) {
            return;
        }

        char c = input.charAt(pos);
        if (Character.isDigit(c)) {
            curToken = this.getNumber();
        } else if ("()+-x*^ep".indexOf(c) != -1) {
            pos++;
            curToken = String.valueOf(c);
        }
    }

    public String getNumber() {
        StringBuilder sb = new StringBuilder();
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            sb.append(input.charAt(pos));
            pos++;
        }
        return sb.toString();
    }

    public BigInteger isNumber() {
        if (Character.isDigit(this.peek().charAt(0))) {
            return  new BigInteger(this.peek());
        }
        return BigInteger.valueOf(-1);
    }

    public String peek() {
        return this.curToken;
    }
}
