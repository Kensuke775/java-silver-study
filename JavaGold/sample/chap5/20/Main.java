import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(10, 20, 30);
        int total = list.parallelStream().reduce(1,
            (sum, num) -> {
                System.out.println(
                        "[accumulator] sum:" + sum + ", num:" + num);
                return sum + num;
            },
            (sub1, sub2) -> {
                System.out.println(
                        "[combiner] sub1:" + sub1 + ", sub2:" + sub2);
                return sub1 + sub2;
            });
        System.out.println("Total length: " + total);
    }
}