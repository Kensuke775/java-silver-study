import java.util.OptionalInt;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        IntBinaryOperator operator = (x, y) -> x + y;
        OptionalInt value1 = IntStream.range(1, 6).reduce(operator);
        System.out.println("value1: " + value1.getAsInt());
        OptionalInt noValue = IntStream.empty().reduce(Integer::sum);
        System.out.println("empty : " + noValue.orElse(0));
        int value2 = IntStream.range(1, 6).reduce(10, operator);
        System.out.println("value2: " + value2);
        int value3 = IntStream.empty().reduce(10, operator);
        System.out.println("empty : " + value3);
    }
}