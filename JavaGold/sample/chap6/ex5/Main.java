import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String... args) {
        String file = "input.txt";
        try (FileReader reader = new FileReader(file);) {
            var value = // insert code here
            System.out.println(value);
        } catch(IOException e) { e.printStackTrace(); }
    }
}