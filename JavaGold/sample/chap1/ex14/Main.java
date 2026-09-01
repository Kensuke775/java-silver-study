import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
public class Main {
    public static void main(String... args) {
        Comparator<String> comp
                = Comparator.nullsLast(Comparator.naturalOrder());
        TreeSet<String> fruits = new TreeSet<>(comp);
        fruits.add("Lemon"); fruits.add("Apple"); fruits.add(null);
        for (String s : fruits) System.out.print(s + " ");
        Collections.addAll(fruits, "Kiwi", "Apple", "Lime");
        for (String s : fruits) System.out.print(s + " ");
    }
}