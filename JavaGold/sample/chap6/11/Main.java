import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path path = Path.of("C:/dir/a/b/file.txt");
        Path p1 = path.getRoot();
        Path p2 = path.getParent();
        Path p3 = path.getFileName();
        Path p4 = path.getName(0);
        Path p5 = path.subpath(1, 3);
     // Path ng1 = path.getName(5);
     // Path ng2 = path.subpath(3, 3);
        int count = path.getNameCount();
        boolean isAbsolute = path.isAbsolute();

        System.out.printf("%-5s [ %s ]%n", "Path", path);
        System.out.printf("%-15s: %s%n", "getRoot()", p1);
        System.out.printf("%-15s: %s%n", "getParent()", p2);
        System.out.printf("%-15s: %s%n", "getFileName()", p3);
        System.out.printf("%-15s: %s%n", "getName(0)", p4);
        System.out.printf("%-15s: %s%n", "subpath(1, 3)", p5);
        System.out.printf("%-15s: %d%n", "getNameCount()", count);
        System.out.printf("%-15s: %s%n", "isAbsolute()", isAbsolute);
    }
}