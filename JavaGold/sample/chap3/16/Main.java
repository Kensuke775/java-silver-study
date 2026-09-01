import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

record Clothes(String name, int size) {}
public class Main {
    public static void main(String[] args) {
        List<Clothes> clothes = List.of(
                new Clothes("Jacket", 2),new Clothes("Jacket", 1),
                new Clothes("Dress", 2));
        List<String> names = clothes.stream()
                .map(Clothes::name)
                .collect(Collectors.toList());
        names.add("T-shirt");
        Set<Integer> sizes = clothes.stream()
                .map(Clothes::size)
                .collect(Collectors.toSet());
        Set<String> orderedNames = clothes.stream()
                .map(Clothes::name)
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println("List   : " + names);
        System.out.println("Set    : " + sizes);
        System.out.println("TreeSet: " + orderedNames);
    }
}