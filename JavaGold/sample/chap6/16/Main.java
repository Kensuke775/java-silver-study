import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path dir = Path.of("C:/sample/chap6/16");
        Path file = Path.of("Main.java");
        System.out.printf("%-15s: %s%n", "isDirectory()"
                                    , Files.isDirectory(dir));
        System.out.printf("%-15s: %s%n", "exists()"
                                    , Files.exists(dir));
        System.out.printf("%-15s: %s%n", "isRegularFile()"
                                    , Files.isRegularFile(file));
    }
}