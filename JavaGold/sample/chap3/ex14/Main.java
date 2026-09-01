import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        Stream<Integer> stream = Stream.iterate(0, i -> i++);
        var result = stream.limit(5L)
                .map(i -> i + 10)
                .findAny();
        // insert code here
    }
}