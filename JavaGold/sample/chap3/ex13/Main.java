import java.util.List;

public class Main {
    public static void main(String... args) {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (Integer i : list)
            if (i % 2 == 0 && i < 8) System.out.print(i);
    }
}