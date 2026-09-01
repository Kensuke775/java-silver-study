import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String... args) {
        String text = "Zero ";
        BinaryOperator<String> op1 = String::concat;
        UnaryOperator<String> op2 = text::concat;
        System.out.print(op1.apply("Two ", "One "));
        System.out.print(op2.apply("Three "));
    }
}