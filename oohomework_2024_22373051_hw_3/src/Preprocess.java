import java.util.ArrayList;

public class Preprocess {
    private final String output;

    public Preprocess(String str) {
        String input = str;
        input = input.replaceAll("[ \t]", "");
        //todo
        //solve 2^2 0^0 问题
        while (input.contains("++") || input.contains("--") || input.contains("+-")
                || input.contains("-+")) {
            input = input.replaceAll("\\+-", "-");
            input = input.replaceAll("-\\+", "-");
            input = input.replaceAll("--", "+");
            input = input.replaceAll("\\+\\+", "+");
        }

        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 'd') {
                left.add(i);
            }
        }

        for (int i = 0; i < input.length(); i++) {
            if (left.contains(i)) {
                int count = 0;
                for (int j = i + 2; j < input.length(); j++) {
                    if (input.charAt(j) == '(') {
                        count++;
                    }
                    if (input.charAt(j) == ')') {
                        count--;
                    }
                    if (count == 0) {
                        right.add(j);
                        break;
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (left.contains(i)) {
                result.append('(');
                result.append(input.charAt(i));
            } else if (right.contains(i)) {
                result.append(input.charAt(i));
                result.append(')');
            } else {
                result.append(input.charAt(i));
            }
        }
        output = result.toString();
        //        StringBuilder result = new StringBuilder();
        //        int count = 0; //代表‘d’
        //        int count1 = 0;//代表（
        //        int flag = 0;
        //        for (char c : input.toCharArray()) {
        //            if (c == 'd') {
        //                result.append("(");
        //                count++;
        //                result.append(c);
        //                flag = count1 - count + 1;
        //            } else if (c == '(') {
        //                if (count > 0) {
        //                    count1++;
        //                }
        //                result.append(c);
        //            } else if (c == ')') {
        //                result.append(c);
        //                if (count1 > 0 && count > 0) {
        //                    count1--;
        //                }
        //                if (count > (count1 - flag)) {
        //                    result.append(")");
        //                    count--;
        //                }
        //            } else {
        //                result.append(c);
        //            }
        //        }
        //        output = result.toString();

        //        while (true) {
        //            String regex = "(\\d+)(\\^)(\\+?\\d+)";
        //            Pattern pattern = Pattern.compile(regex);
        //            Matcher matcher = pattern.matcher(input);
        //            if (matcher.find()) {
        //                BigInteger coe = new BigInteger(matcher.group(1));
        //                BigInteger exponent = new BigInteger(matcher.group(3));
        //                BigInteger result = coe.pow(exponent.intValue());
        //                input = input.replace(matcher.group(), result.toString());
        //            } else {
        //                output = input;
        //                break;
        //            }
        //        }
    }

    public String getInput() {
        return output;
    }
}
