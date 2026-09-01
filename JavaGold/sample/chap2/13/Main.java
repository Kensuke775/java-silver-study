import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        UnaryOperator<Integer> unary = i -> i + i;
        BinaryOperator<String> binary = (s1, s2) -> s1.concat(s2);
        Integer i = unary.apply(10);                  // 20
        String s = binary.apply("Hi ", "Duke!");    // Hi Duke!
    }
}