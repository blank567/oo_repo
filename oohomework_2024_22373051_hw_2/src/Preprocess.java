import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



        while (true) {
            String regex = "(\\d+)(\\^)(\\+?\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            if (matcher.find()) {
                BigInteger coe = new BigInteger(matcher.group(1));
                BigInteger exponent = new BigInteger(matcher.group(3));
                BigInteger result = coe.pow(exponent.intValue());
                input = input.replace(matcher.group(), result.toString());
            } else {
                output = input;
                break;
            }
        }
    }

    public String getInput() {
        return output;
    }
}
