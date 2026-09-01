import java.io.Console;
import java.io.PrintWriter;
import java.util.Arrays;

public class Sample {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            return;
        }
        String id = console.readLine("Enter your %-10s: ", "ID");
        char[] pass = console.readPassword("Enter your %-10s: ", "password");
        PrintWriter out = console.writer();
        if(id != null) console.format("id   : %s%n", id);
        if(pass != null) console.printf("pass : %s%n", new String(pass));
        Arrays.fill(pass, ' ');
    }
}