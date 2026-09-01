import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.stream.Stream;

public class WriteSample {
    public static void main(String[] args) {
        try(PrintWriter out = new PrintWriter("data4.txt")){
            out.println(100L);
            out.println(3.14);
            out.print(LocalDate.now());
        } catch(IOException e) { e.printStackTrace(); }

        try (BufferedReader br = new BufferedReader(new FileReader("data4.txt"))) {
            Stream<String> lines = br.lines();
            lines.forEach(System.out::println);
        } catch (Exception e) { e.printStackTrace(); }
    }
}