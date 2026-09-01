import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        IntStream.range(0, 10)
                .parallel()                     // (A)
                .boxed()
                .sequential()                   // (B)
                .forEach(System.out::print);    // (C)
    }
}