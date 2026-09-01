import java.nio.file.Path;

public class Main {
    public static void main(String... args) {
        Path path = Path.of("C:/dir/../sub/sample/.././sample.txt");
        System.out.println(path.normalize());
    }
}