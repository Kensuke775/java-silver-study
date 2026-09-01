import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String... args) {
        try (Stream<Path> dir = Files.find(Path.of("dir"), 2,
                                (p, attr) -> attr.isRegularFile())) {
            dir.forEach(System.out::println);
        } catch(IOException e) { e.printStackTrace(); }
    }
}