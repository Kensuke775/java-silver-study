import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String[] clothes = {"shirt", "dress", "T-shirt", "dress"};
        Stream<String> stream = Arrays.stream(clothes).distinct();
        stream.forEach(s -> System.out.print(s + " "));
    }
}