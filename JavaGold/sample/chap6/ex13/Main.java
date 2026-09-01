import java.nio.file.Path;

public class Main {
    public static void main(String... args) {
        Path p1 = Path.of("C:/dir/x");
        Path p2 = Path.of("../y");
        System.out.println(p1.getParent().resolve(p2));
        System.out.println(p2.resolve(p1));
        System.out.println(p1.resolveSibling(p2));
        System.out.println(p2.resolveSibling(p1));
    }
}