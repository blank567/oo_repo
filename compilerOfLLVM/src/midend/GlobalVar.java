package midend;

import java.util.ArrayList;

public class GlobalVar extends User{
    private ArrayList<Integer> initials;
    //  0 -> int
    //  1 -> char
    //  2 -> int[]
    //  3 -> char[]
    private String stringConst;

    public GlobalVar(String value, int valueType, ArrayList<Integer> initials) {
        super(value, valueType);
        this.initials = initials;
    }

    public GlobalVar(String value, int valueType, String stringConst) {
        super(value, valueType);
        this.stringConst = stringConst;
    }

    public boolean isZeroInitializer() {
        for(int i = 0; i < initials.size(); i++) {
            if(initials.get(i) != 0) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        switch (valueType) {
            case 0: {
                return value + " = dso_local global i32 " + initials.get(0) + "\n";
            }
            case 1: {
                return value + " = dso_local global i8 " + initials.get(0) + "\n";
            }
            case 4: {
                sb.append(value + " = dso_local global [").append(initials.size()).append(" x i32] ");
                if (isZeroInitializer()) {
                    sb.append("zeroinitializer\n");
                } else {
                    sb.append("[");
                    for (int i = 0; i < initials.size(); i++) {
                        sb.append("i32 ").append(initials.get(i));
                        if (i != initials.size() - 1) {
                            sb.append(", ");
                        } else {
                            sb.append("]\n");
                        }
                    }
                }
                return sb.toString();
            }
            case 5: {
                sb.append(value + " = dso_local global [").append(initials.size()).append(" x i8] ");
                if (isZeroInitializer()) {
                    sb.append("zeroinitializer\n");
                } else {
                    sb.append("[");
                    for (int i = 0; i < initials.size(); i++) {
                        sb.append("i8 ").append(initials.get(i));
                        if (i != initials.size() - 1) {
                            sb.append(", ");
                        } else {
                            sb.append("]\n");
                        }
                    }
                }
                return sb.toString();
            }
            case 7:{
                sb.append(value + " = private unnamed_addr constant [");
                sb.append(stringConst.replace("\\n", "n").length() + 1 + " x i8] c\"");
                sb.append(stringConst.replace("\\n", "\\0A")).append("\\00");
                sb.append("\", align 1\n");
                return sb.toString();
            }
            default: {
                System.out.println("Error: GlobalVar type error");
                return null;
            }
        }
    }
}
