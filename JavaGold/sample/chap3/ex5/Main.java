import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        Stream<Long> stream = Stream.of("1", "2", "3", "4", "5")
                .mapToLong(Long::parseLong)
                // insert code here
    }
}