import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path p1 = Paths.get("C:\\sample\\chap6\\10\\Main.java");
        Path p2 = Paths.get("/home/user/sample");
        System.out.printf("%-10s: %s%n", "p1", p1);
        System.out.printf("%-10s: %s%n", "p2", p2);

        Path p3 = Path.of("../sample/chap6/10");
        Path p4 = Path.of("C:", "sample", "chap6","10");
        System.out.printf("%-10s: %s%n", "p3", p3);
        System.out.printf("%-10s: %s%n", "p4", p4);

        FileSystem fs = FileSystems.getDefault();
        Path p5 = fs.getPath("C:/sample/chap6/10");
        System.out.printf("%-10s: %s%n", "p5", p5);

        String separator = fs.getSeparator();
        System.out.printf("%-10s: %s%n", "separator", separator);
        Iterable<Path> directories = fs.getRootDirectories();
        directories.forEach(System.out::println);
    }
}