import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String text = """
                T-shirt, Jeans
                Coat, Dress, Shirt
                """;
        text.lines()                            // Stream<String>
                .flatMap(s -> Arrays.stream(s.split(", ")))
                .forEach(s -> System.out.print(s + " "));
        System.out.println();
        text.lines()                            // Stream<String>
                .map(s -> Arrays.stream(
                        s.split(", "))) // Stream<Stream<String>>
                .forEach(stream -> System.out.println(stream));
             // .forEach(stream ->
             //     stream.forEach(s -> System.out.print(s + " ")));
    }
}