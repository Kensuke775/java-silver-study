import java.util.List;
public class Main {
    public static void main(String... args) {
        List<Integer> list = List.of(0, 2, 4, 6, 8, 10);
        list = list.subList(2, 4);
        list.add(0);
        System.out.println(list);
    }
}