import java.util.List;
import java.util.function.BinaryOperator;

public class Main {
    public static void main(String... args) {
        List<String> list = List.of("Lemon", "Kiwi", "Orange");
        BinaryOperator<String> operator = (s1, s2) -> s1 + ":" + s2;
        String fruits = list.stream()
                .takeWhile(s -> s.length() == 0)
                .reduce("[FRUITS]", operator);              // (A)
        System.out.println(fruits);
    }
}