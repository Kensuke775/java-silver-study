import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("C:/sample/chap6/15/names.txt");
        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            List<String> names = List.of("Amy ", "Carol ", "Duke ");
            for (String s : names) {
                bw.write(s); bw.newLine();
            }
        }
        System.out.print("lines()       : ");
        Stream<String> lines = Files.lines(file);
        lines.forEach(System.out::print);
        System.out.print("\nreadAllLines(): ");
        List<String> allLines = Files.readAllLines(file);
        allLines.forEach(System.out::print);
    }
}