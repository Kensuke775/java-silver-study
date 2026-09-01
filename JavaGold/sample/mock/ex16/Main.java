import java.util.*;

public class Main {
    public static void main(String... args) {
        List<Integer> scores = new ArrayList<>();
        scores.add(1);
        scores.add(2);
        scores.add(1, null);                    // (A)
        Double d = scores.get(0);
        var v = scores.remove(1);               // (B)
        System.out.print(d);
        System.out.print(v);
    }
}