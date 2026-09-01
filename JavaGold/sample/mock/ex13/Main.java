import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        var result = IntStream.rangeClosed(1, 10)
                .parallel()
                .map(n -> {
                    return n + 10;
                })
                .findAny();
        System.out.println(result.orElse(-1));

    }
}