import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        List<String> list =
                new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
        for (String s : list) {
            if (s.equals("A")) { list.remove(s); }
        }
        System.out.println(list);
    }
}