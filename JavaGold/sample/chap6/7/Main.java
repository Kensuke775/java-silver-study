import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        String text = "Duke";
        LocalTime time = LocalTime.now();

        try (FileOutputStream fos = new FileOutputStream("data7.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(text);
            oos.writeObject(time);
            oos.flush();
            System.out.println("Wrote data to data7.ser!");
        } catch (IOException e) { e.printStackTrace(); }

        try (FileInputStream fis = new FileInputStream("data7.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            String readText = (String)ois.readObject();
            LocalTime readTime = (LocalTime)ois.readObject();
            System.out.println("readText: " + readText);
            System.out.println("readDate: " + readTime);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}