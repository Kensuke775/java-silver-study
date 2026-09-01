import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String... args) {
        Path path = Paths.get("dir", "a");
        Path p1 = path.getRoot();
        Path p2 = path.getFileName();
        Path p3 = path.getName(0);
        Path p4 = p3.getParent();
        System.out.println(p1 + " " + p2 + " " + p3 + " " + p4);
    }
}