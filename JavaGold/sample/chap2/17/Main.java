import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<String, String> f1 = s -> s + "! ";
        Function<String, String> f2 = s -> s + "Duke";
        Function<String, String> andThen = f1.andThen(f2);
        Function<String, String> compose = f1.compose(f2);
        System.out.println("andThen: " + andThen.apply("Hello"));
        System.out.println("compose: " + compose.apply("Hello"));
    }
}