import java.io.Console;
import java.io.PrintWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console.");
            return;
        }
        String id = console.readLine("Enter your ID      : ");
        char[] pass = console.readPassword("Enter your password: ");
        PrintWriter out = console.writer();
        if(id != null) out.println(id);
        if(pass != null) out.println(pass);
        Arrays.fill(pass, ' ');
    }
}