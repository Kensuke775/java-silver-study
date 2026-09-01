import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        var result = Stream.iterate(0, i -> i + 1)
                .limit(10).skip(20).findFirst();
        System.out.println(result);
    }
}