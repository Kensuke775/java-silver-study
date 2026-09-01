import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Path base = Path.of("../");
        try(Stream<Path> stream = Files.list(base)) {
            stream.limit(5L).forEach(System.out::println);
        }
    }
}