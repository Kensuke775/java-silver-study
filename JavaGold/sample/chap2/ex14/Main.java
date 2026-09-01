import java.util.function.IntUnaryOperator;

public class Main {
    public static void main(String... args) {
        IntUnaryOperator op1 = i -> i + 10;
        IntUnaryOperator op2 = i -> i * 2;
        IntUnaryOperator operator = op1.compose(op2);
        System.out.println(operator.applyAsInt(3));
    }
}