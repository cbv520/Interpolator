import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        Map<String, String> values = new HashMap<>();
        values.put("person", "blake");
        values.put("verb", "ate");
        values.put("noun", "pie");

        System.out.println(intpolate("${person} ${verb} ${noun}", values::get));
    }

    public static String intpolate(String str, Function<String, String> replacer) {
        StringBuilder result = new StringBuilder();
        String[] split =  str.split("\\$\\{(?:.*?)}");
        System.out.println(Arrays.toString(split));
        Pattern pattern = Pattern.compile("((?<=\\$\\{)(?:.*?)(?=}))");
        Matcher matcher = pattern.matcher(str);
        int i = 0;
        boolean match = matcher.find();
        while(match || i < split.length) {
            if(i < split.length)
                result.append(split[i]);
            if(match) {
                String var = matcher.group(0);
                try {
                    result.append(replacer.apply(var));
                } catch (Exception e) {
                    result.append("${").append(var).append("}");
                }
            }
            i++;
            match = matcher.find();
        }

        return result.toString();
    }
}
