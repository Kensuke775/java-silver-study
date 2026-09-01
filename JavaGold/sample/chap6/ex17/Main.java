import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String... args) throws IOException {
        Path path1 = Path.of("dir", "a", "b");
        Path path2 = Path.of("dir", "x", "y");
        Files.createDirectories(path1);
        Files.createDirectory(path2.getParent());       // (A)
        Files.createDirectories(path1);                 // (B)
        Files.createFile(path2);                        // (C)
        if (Files.isDirectory(path1)) {
            System.out.println(Files.delete(path1));    // (D)
        }
    }
}