import java.util.Set;
import java.util.TreeSet;

record Client(String name) implements Comparable<Client> {
    @Override
    public int compareTo(Client o) {
        return this.name.compareTo(o.name);
    }
}
record Person(String name) {}

public class Main {
    public static void main(String[] args) {
        Set<Client> set1 = new TreeSet<>();
        set1.add(new Client("Bob"));
        set1.add(new Client("Carol"));
        set1.add(new Client("Alice"));
        System.out.println(set1);
        Set<Person> set2 = new TreeSet<>();
    //  set2.add(new Person("Duke"));
    }
}