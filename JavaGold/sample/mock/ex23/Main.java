import java.util.*;

public class Main {
    public static void main(String... args) {
        Set<? extends Number> set = new LinkedHashSet<>();
        set.add(Integer.valueOf(10));       // (A)
        set.add(null);                      // (B)

        List<? super Number> list = new LinkedList<>();
        list.add(Integer.valueOf(10));      // (C)
        list.add(null);                     // (D)
        Number value = list.get(1);         // (E)
    }
}