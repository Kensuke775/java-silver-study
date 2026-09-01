import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int price;
    private transient int totalPrice;

    public Item(String name, int price) {
        System.out.println("Constructor was called!");
        this.name = name;
        this.price = price;
        this.totalPrice = calcTotalPrice();
    }
    private int calcTotalPrice() {
        return (int) (price * 1.1);
    }
    private void writeObject(ObjectOutputStream out)
                                            throws IOException {
        out.defaultWriteObject();
    }
    private void readObject(ObjectInputStream in)
                    throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.totalPrice = calcTotalPrice();
    }
    @Override public String toString() {
        return "Item{name=" + name + ", price=" + price
                            + ", totalPrice=" + totalPrice + "}";
    }
}