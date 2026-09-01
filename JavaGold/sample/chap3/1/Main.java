import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        for (Integer i : list) {
            if (i % 2 == 0) {
                System.out.print(i);
            }
        }
        System.out.println();
        list.stream()                           // ストリームの生成
                .filter(i -> i % 2 == 0)        // 中間操作
                .forEach(System.out::print);    // 終端操作
    }
}