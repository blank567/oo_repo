import java.util.ArrayList;

public class Replace {
    private static ArrayList<Character> forder = new ArrayList<>();
    private static ArrayList<Character> gorder = new ArrayList<>();
    private static ArrayList<Character> horder = new ArrayList<>();

    private static String of = null;
    private static String og = null;
    private static String oh = null;

    public static void process(String input) {
        String tmp = input;
        tmp = tmp.replaceAll("exp", "!");
        tmp = tmp.replaceAll("x", "&");
        tmp = tmp.replaceAll("!", "exp");
        int index = 1;
        while (tmp.charAt(index) != '=') {
            if (tmp.charAt(index) == '&' || tmp.charAt(index) == 'y' || tmp.charAt(index) == 'z') {
                if (tmp.charAt(0) == 'f') {
                    forder.add(tmp.charAt(index));
                }
                if (tmp.charAt(0) == 'g') {
                    gorder.add(tmp.charAt(index));
                }
                if (tmp.charAt(0) == 'h') {
                    horder.add(tmp.charAt(index));
                }
            }
            index++;
        }
        if (tmp.charAt(0) == 'f') {
            of = tmp.substring(index + 1);
        }
        if (tmp.charAt(0) == 'g') {
            og = tmp.substring(index + 1);
        }
        if (tmp.charAt(0) == 'h') {
            oh = tmp.substring(index + 1);
        }
    }

    public static String add(String input) {
        String tmp = input;
        while (tmp.contains("f") || tmp.contains("g") || tmp.contains("h")) {
            if (of != null) {
                tmp = solve(tmp, "f");
            }
            if (og != null) {
                tmp = solve(tmp, "g");
            }
            if (oh != null) {
                tmp = solve(tmp, "h");
            }
        }
        return tmp;
    }

    public static String solve(String input, String funcName) {
        int start = 0;
        int end = 0;
        ArrayList<Character> order;
        String func;
        if (funcName == "f") {
            order = forder;
            func = of;
        } else if (funcName == "g") {
            order = gorder;
            func = og;
        } else {
            order = horder;
            func = oh;
        }
        String expr = input;
        ArrayList<String> par = new ArrayList<>();
        while (expr.contains(funcName)) {
            start = expr.indexOf(funcName);
            end = start + 2;
            int index = start + 2;
            int last = index;
            int count = 1;
            while (count != 0) {
                if (expr.charAt(index) == ',' && count == 1) {
                    par.add(expr.substring(last, index));
                    last = index + 1;
                }
                if (expr.charAt(index) == '(') {
                    count++;
                } else if (expr.charAt(index) == ')') {
                    if (count == 1) {
                        par.add(expr.substring(last, index));
                        break;
                    }
                    count--;
                }
                end++;
                index++;
            }
            String tmpFunc = func;
            for (int i = 0; i < order.size(); i++) {
                tmpFunc = tmpFunc.replaceAll(order.get(i).toString(),
                        '(' + par.get(i) + ')');//加括号(),会算错
            }
            expr = expr.substring(0, start) + '(' + tmpFunc + ')' + expr.substring(end + 1);//整合
            par.clear();
        }
        return expr;
    }
}
