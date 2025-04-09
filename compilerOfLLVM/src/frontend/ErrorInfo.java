package frontend;

public class ErrorInfo {
    private char type;
    private int line;

    public ErrorInfo(char type, int line) {
        this.type = type;
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public String toString() {
        return line + " " + type;
    }
}
