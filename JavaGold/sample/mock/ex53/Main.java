import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String... args) {
        try {
            var lines = // insert code here
            lines.stream().forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}