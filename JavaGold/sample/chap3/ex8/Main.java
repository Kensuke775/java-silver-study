import java.util.stream.IntStream;

public class Main {
    public static void main(String... args) {
        var result =
                IntStream.rangeClosed(1, 3).skip(5L).findFirst();
    }
}