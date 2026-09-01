import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Duke", "Sara", "Duke", "Lucy");
        Set<String> set = list.parallelStream().collect(
                HashSet::new,
                HashSet::add,
                HashSet::addAll);
        set.forEach(e -> System.out.print(e + " "));
    }
}