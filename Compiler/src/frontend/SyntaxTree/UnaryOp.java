package frontend.SyntaxTree;

import frontend.TokenType;

public class UnaryOp {
    private TokenType tokenType;

    public UnaryOp(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenType getOp() {
        return tokenType;
    }
}
