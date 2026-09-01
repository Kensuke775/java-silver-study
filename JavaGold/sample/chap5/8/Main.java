import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("A", "B", "C"));
     // for (String s : list) {
     //     if (s.equals("A")) { list.remove(s); }
     // }
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("A")) { iterator.remove(); }
        }
        System.out.println("list.size(): " + list.size());
    }
}