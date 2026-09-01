import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

class SortByLength implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.length() - o2.length();
    }
}
public class Main {
    public static void main(String[] args) {
        Set<String> set1 = new TreeSet<>();
        set1.add("Alexander"); set1.add("Bob");
        set1.add("Casey"); set1.add("Duke");
        System.out.println("Natural order: " + set1);
        Set<String> set2 = new TreeSet<>(new SortByLength());
        set2.add("Alexander"); set2.add("Bob");
        set2.add("Casey"); set2.add("Duke");
        System.out.println("Length order : " + set2);
        // nullFirst()
        Comparator<String> comp =
                Comparator.nullsFirst(new SortByLength());
        Set<String> set3 = new TreeSet<>(comp);
        set3.add("Alexander"); set3.add("Bob");
        set3.add("Casey"); set3.add("Duke");
        set3.add(null);
        System.out.println("  with null  : " + set3);
        // reverseOrder()
        Set<String> set4 = new TreeSet<>(Comparator.reverseOrder());
        set4.add("Alexander"); set4.add("Bob");
        set4.add("Casey"); set4.add("Duke");
        System.out.println("Reverse order: " + set4);
    }
}