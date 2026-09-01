import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        Path path = Path.of("othello.txt");
        try {
            Stream<String> lines = // insert code here
            lines.forEach(System.out::println);
        } catch (IOException e) { e.printStackTrace(); }
    }
}