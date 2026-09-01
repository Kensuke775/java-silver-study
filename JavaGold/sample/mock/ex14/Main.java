import java.io.*;

public class Main {
    public static void main(String... args) {
        String file = "greeting.txt";
        try (FileWriter fw = new FileWriter(file, true)) {
            fw.write(" there!");
        } catch (IOException ex) {
            System.err.println(ex);
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            int i;
            while((i = fis.read()) != -1) {
                System.out.print((char)i);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}