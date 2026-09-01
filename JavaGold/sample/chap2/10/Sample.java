import java.util.function.Function;

public class Sample {
    public static void main(String[] args) {
        Function<Integer, String> stat = i -> Integer.toString(i);
        Function<Integer, String> inst = i -> i.toString();
     // Function<Integer, String> func = Integer::toString;
        stat.apply(100);
        inst.apply(100);

        Function<String, Integer> f1 = Integer::valueOf;
        Function<Integer, Integer> f2 = Integer::valueOf;
        f1.apply("100");
        f2.apply(100);
    }
}