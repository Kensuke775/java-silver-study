import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        String text = """
                Hello, world!
                This is a sample text.
                I am learning the Stream API.
                """;
        Stream<String> lines = text.lines();
        lines.peek(System.out::print)
                .mapToInt(s -> s.split(" ").length)
                .filter(s -> s > 2)
                .forEach(System.out::println);
    }
}