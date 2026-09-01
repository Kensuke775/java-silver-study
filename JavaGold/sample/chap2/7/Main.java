import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list1
                = Arrays.asList("L", "a", "m", "b", "d", "A");
        List<String> list2
                = Arrays.asList("Ex", "-", "pres", "-", "sion");
        Collections.sort(list1, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });
        Collections.sort(list2, (String o1, String o2) -> {
            return o1.compareToIgnoreCase(o2);
        });
        System.out.println("comp1: " + list1);
        System.out.println("comp2: " + list2);
    }
}