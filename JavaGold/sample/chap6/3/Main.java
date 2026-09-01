import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String file = "data3.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            BufferedReader br = new BufferedReader(new FileReader(file))) {
            bw.write("Writing and Reading data");
            bw.newLine();
            bw.write("Using buffered streams.");
            bw.flush();
            Stream<String> lines = br.lines();
            lines.forEach(System.out::println);
        } catch (Exception e) { e.printStackTrace(); }
    }
}