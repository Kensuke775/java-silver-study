import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Path abs1 = Path.of("C:/");
        Path abs2 = Path.of("D:/");
        Path rel1 = Path.of("x/y");
        Path rel2 = Path.of("../z");

        Path p1 = abs1.resolve(abs2);
        Path p2 = rel1.resolve(rel2);
        Path p3 = abs1.resolve(rel1);
        Path p4 = rel1.resolve("");
        System.out.printf("%-20s: %s%n", "abs1.resolve(abs2)", p1);
        System.out.printf("%-20s: %s%n", "rel1.resolve(rel2)", p2);
        System.out.printf("%-20s: %s%n", "abs1.resolve(rel1)", p3);
        System.out.printf("%-20s: %s%n", "rel1.resolve(\"\")", p4);

        Path p5 = abs1.resolveSibling(abs2);
        Path p6 = rel1.resolveSibling(rel2);
        Path p7 = abs1.resolveSibling(rel1);
        Path p8 = rel1.resolveSibling("");
        System.out.printf("%-25s: %s%n", "abs1.resolveSibling(abs2)", p5);
        System.out.printf("%-25s: %s%n", "rel1.resolveSibling(rel2)", p6);
        System.out.printf("%-25s: %s%n", "abs1.resolveSibling(rel1)", p7);
        System.out.printf("%-25s: %s%n", "rel1.resolveSibling(\"\")", p8);
    }
}