import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String... args) {
        String file = "output.txt";
        try (FileWriter fw = new FileWriter(file, false);
             BufferedWriter bw = new BufferedWriter(fw);) {
            bw.write("duke"); bw.flush();
        } catch(IOException e) {
            System.err.println("error");
        }
    }
}