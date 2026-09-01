import java.util.*;

public class Main<T> {
    public <E> void printList(List<E> list) {
        list.forEach(e -> System.out.print(e + ":"));
    }
    public void printMap(Map<T, E> map) {
            map.forEach((k, v) -> System.out.print(k + ":" + v));
    }
    public static void main(String... args) {
        Main<Integer> obj = new Main<>();
        obj.printList(List.of(1, 2, 3));
        obj.printMap(Map.of(1, "One"));
    }
}