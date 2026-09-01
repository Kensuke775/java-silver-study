import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        var x = List.of(11, 8, 17);
        var y = List.of(10, 1, 4, 7);
        Stream.of(x, y)
                .mapMulti(List::stream)                      // (A)
                .filter(e -> e > 5)
                .sorted()                                    // (B)
                .forEach(s -> System.out.print(s + " "));
    }
}