import java.io.*;
import java.nio.file.*;

public class Main {
    public static void main(String... args) {
        Path dir = Paths.get("dir/data");
        Path file = dir.resolve("source.txt");
        try {
            if(Files.isDirectory(dir)) {
                if(Files.deleteIfExists(file)) {
                    System.out.print("Deleted: ");
                } else {
                    System.out.print("Failed to delete: ");
                }
                System.out.println(file.getFileName());
            } else {
                System.out.println("Directory not found: " + dir);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}