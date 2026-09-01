import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {
    public static void main(String... args) {
        try (FileOutputStream fos = new FileOutputStream("book.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(new Book(100, "Java Gold"));
            oos.flush();
        } catch (IOException e) { e.printStackTrace(); }

        try (FileInputStream fis = new FileInputStream("book.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            
            Book obj = // insert code here
            System.out.println(obj);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
record Book(int id, String title) implements Serializable {}