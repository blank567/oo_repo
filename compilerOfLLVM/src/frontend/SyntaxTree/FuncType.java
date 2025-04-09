package frontend.SyntaxTree;

import frontend.TokenType;

public class FuncType {
    private TokenType tokenType;

    public FuncType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public TokenType getTokenType() {
        return this.tokenType;
    }
}
