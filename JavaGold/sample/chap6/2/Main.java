import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        String file = "data2.txt";
        try (FileWriter fw = new FileWriter(file, true);
            FileReader fr = new FileReader(file)) {
            fw.write("Writing\r\n");
            fw.write(" and ");
            fw.write("Reading\r\n");
            fw.flush();
            int c;
            while((c = fr.read()) != -1) {
                System.out.print((char)c);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}