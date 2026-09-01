import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String... args) {
        int count = 0;
        List<String> list = Arrays.asList("Duke", "James");
        UnaryOperator<String> operator = s -> {
                    String tmp = s.toUpperCase() + count;
                    System.out.print(tmp + ":");    // (A)
                    count++;                        // (B)
                    return s.toLowerCase() + ":";   // (C)
                };
        list.replaceAll(operator);
        list.forEach(System.out::print);
    }
}