import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        try (Stream<Path> dir = Files.list(Path.of("dir"))) {
            dir.forEach(System.out::println);
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}