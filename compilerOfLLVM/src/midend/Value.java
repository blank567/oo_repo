package midend;

public class Value {
    public String value;
    public int valueType;

    // i32 -> 0
    // i8 -> 1
    // i32* -> 2
    // i8* -> 3
    // [n x i32] -> 4
    // [n x i8] -> 5
    // i1 -> 6
    // print -> 7

    public Value(String value, int valueType) {
        this.value = value;
        this.valueType = valueType;
    }

    public boolean isNumber() {
        return value.matches("^-?\\d+$");
    }

}
