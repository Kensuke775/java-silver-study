import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main {
    public static void main(String[] args) throws IOException {
        Path base = Path.of("dir/sub");
        Path tmp = Path.of("tmp");
        Files.createDirectories(base);
        Files.createDirectory(base.resolve(tmp));

        Path pathA = Path.of("dir/fileA.txt");
        Path pathB = Path.of("dir/fileB.txt");
        Path pathC = Path.of("dir/sub/fileC.txt");
        Files.createFile(pathA);
        Files.copy(pathA, pathB,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES);
        Files.move(pathB, pathC,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
        Files.delete(base.resolve(tmp));
        boolean deleted = Files.deleteIfExists(pathC);
        System.out.println(deleted);            // true
    }
}