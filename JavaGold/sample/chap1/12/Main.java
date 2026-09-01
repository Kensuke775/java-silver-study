import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();    // HashSet
        boolean add1 = set1.add(3);
        boolean add2 = set1.add(3);
        set1.add(null); set1.add(null);
        set1.add(2); set1.add(1);
        System.out.println("HashSet: " + set1);
        System.out.println(" add1: " + add1 + ", add2: " + add2);
        boolean rmv1 = set1.remove(3);
        boolean rmv2 = set1.remove(3);
        System.out.println(" rmv1: " + rmv1 + ", rmv2: " + rmv2);
        // LinkedHashSet
        Set<Integer> set2 = new LinkedHashSet<>();
        set2.add(3); set2.add(3);
        set2.add(null); set2.add(null);
        set2.add(2); set2.add(1);
        System.out.println("LinkedHashSet: " + set2);
        // TreeSet
        Set<String> set3 = new TreeSet<>();
        set3.add("Duke"); set3.add("James"); set3.add("Alice");
        System.out.println("TreeSet: " + set3);
    }
}