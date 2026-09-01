import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Path base = Path.of("../");
        BiPredicate<Path, BasicFileAttributes> matcher =
                (path, attr) -> path.toString().endsWith(".txt")
                                && attr.isRegularFile();
        try (Stream<Path> stream = Files.find(base, 5, matcher)) {
            stream.forEach(System.out::println);
        }
    }
}