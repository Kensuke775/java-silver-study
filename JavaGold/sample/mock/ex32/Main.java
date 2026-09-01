import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        String letter = "a";
        BinaryOperator<String> operator = (e1, e2) -> e1 + ":" + e2;
        String result = Stream.of("Blue", "White", "Red", "Green")
                .sorted(Comparator.comparingInt(String::length))
                .filter(s -> s.contains(letter))
                .reduce("[CLOTHES]", operator);
        System.out.println(result.get());
    }
}