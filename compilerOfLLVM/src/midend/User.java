package midend;

import java.util.ArrayList;

public class User extends Value{
    public ArrayList<Value> operands;

    public User(String value, int valueType) {
        super(value, valueType);
        operands = new ArrayList<>();
    }

    public void addOperand(Value operand) {
        operands.add(operand);
    }

}
