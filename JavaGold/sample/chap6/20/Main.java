import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(
                            Path dir, BasicFileAttributes attrs) {
                System.out.printf("%-15s: %s%n", "preVisitDir", dir);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(
                            Path file, BasicFileAttributes attrs) {
                System.out.printf("%-15s: %s%n", "visitFile", file);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}