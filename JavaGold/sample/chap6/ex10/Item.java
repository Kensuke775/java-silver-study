import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private transient int quantity;
    public Item(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "Item{name=" + name + ", quantity=" + quantity + "}";
    }
    private void /* x */ throws IOException {}
    private void /* y */ throws IOException, ClassNotFoundException {}
}