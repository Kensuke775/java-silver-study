import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
/*
        Path base = Path.of("dir");
        Path pathA = Path.of("dir", "subA", "x");
        Path pathB = Path.of("dir", "subB");

        Files.createDirectories(pathA);
        Files.createDirectories(pathB);
        Files.createFile(pathA.resolve("fileA.txt"));
        Files.createFile(pathB.resolve("fileB.txt"));
        Files.createFile(base.resolve("fileC.txt"));
*/
        Path dir = Path.of("dir");
        try(Stream<Path> stream = Files.walk(dir, 3)) {
            stream.forEach(System.out::println);
        }
    }
}