import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public record Item(String name, int price) implements Serializable {
    public Item {
        System.out.println("Compact Constructor was called!");
    }
    private void writeObject(ObjectOutputStream out)
                                                throws IOException {
        System.out.println("writeObject()");
    }
    private void readObject(ObjectInputStream in)
                        throws IOException, ClassNotFoundException {
        System.out.println("readObject()");
    }
}