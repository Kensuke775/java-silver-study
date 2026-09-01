import java.io.Console;

public class Main {
    public static void main(String... args) {
        Console console = System.console();
        if (console == null) {
            System.err.println("No console");
            return;
        }
        String name = console.readLine("Name    : ");
        char[] password = console.readPassword("Password: ");
        console.format("Hello %s%n", name);
    }
}