import java.io.*;

public class Main {
    public static void main(String... args) {
        try (FileOutputStream fos = new FileOutputStream("item.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(new Item("T-shirt", 10));
            oos.flush();
        } catch (IOException e) { e.printStackTrace(); }
        try (FileInputStream fis = new FileInputStream("item.ser");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            System.out.println((Item)ois.readObject());
        } catch (Exception e) { e.printStackTrace(); }
    }
}
class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private transient int quantity;
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    private void writeObject(ObjectOutputStream out)
                                            throws IOException {
        out.defaultWriteObject();
        out.writeInt(quantity);
    }
    private void readObject(ObjectInputStream in)
                    throws IOException, ClassNotFoundException {
        // insert code here
    }
    @Override
    public String toString() {
        return "Item{name=" + name + ", quantity=" + quantity + "}";
    }
}