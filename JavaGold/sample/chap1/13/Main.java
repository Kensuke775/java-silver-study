import java.util.HashSet;
import java.util.Set;

class Improper {
    int id; String name;
    Improper(int id, String name) {
        this.id = id; this.name = name;
    }
    @Override public String toString() {
        return "Improper[" +
                "id=" + id + ", name=" + name + "]";
    }
}
record Proper(int id, String name) {}

public class Main {
    public static void main(String[] args) {
        Set<Improper> set1 = new HashSet<>();
        set1.add(new Improper(1, "bad"));
        set1.add(new Improper(1, "bad"));
        System.out.println("HashSet with Improper : " + set1);
        Set<Proper> set2 = new HashSet<>();
        set2.add(new Proper(2, "good"));
        set2.add(new Proper(2, "good"));
        System.out.println("HashSet with Proper   : " + set2);
    }
}