import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadSample {
    public static void main(String[] args) {
        try(BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in))){
            System.out.println("Waiting for input...");
            String text = br.readLine();
            System.out.println("You entered: " + text);
        } catch(IOException e) { e.printStackTrace(); }
    }
}