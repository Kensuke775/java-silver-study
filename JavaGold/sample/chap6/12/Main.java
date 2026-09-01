import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path p1 = Path.of("C:/dir/.././a/b/sample.txt");
        Path p2 = p1.normalize();
        System.out.printf("%-15s: %s%n", "p1", p1);
        System.out.printf("%-15s: %s%n%n", "p1.normalize()", p2);

        Path p3 = Path.of("a/b/c/file1.txt");
        Path p4 = Path.of("a/b/file2.txt");
        Path p5 = p3.relativize(p4);
        System.out.printf("%-18s: %s%n", "p3", p3);
        System.out.printf("%-18s: %s%n", "p4", p4);
        System.out.printf("%-18s: %s%n%n", "p3.relativize(p4)", p5);
        
        Path p6 = p2.toAbsolutePath();
        Path p7 = p3.toAbsolutePath();
        System.out.printf("%-20s: %s%n", "p2.toAbsolutePath()", p6);
        System.out.printf("%-20s: %s%n", "p3.toAbsolutePath()", p7);
    }
}