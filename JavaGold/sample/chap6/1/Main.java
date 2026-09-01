import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String file = "data1.txt";
        try (FileOutputStream fos = new FileOutputStream(file);
            FileInputStream fis = new FileInputStream(file)) {
            fos.write("abcd".getBytes());
            fos.write(101);
            int i;
            while((i = fis.read()) != -1) {
                System.out.print(i + " ");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}