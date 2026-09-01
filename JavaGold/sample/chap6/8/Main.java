import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {
    public static void main(String[] args) {
        Item item = new Item("Duke", 1000);
        try (FileOutputStream fos = new FileOutputStream("data8.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(item);
            oos.flush();
        } catch (IOException e) { e.printStackTrace(); }

        try (FileInputStream fis = new FileInputStream("data8.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            Item readItem = (Item) ois.readObject();
            System.out.println("Deserialized: " + readItem);
        } catch (Exception e) { e.printStackTrace(); }
    }
}