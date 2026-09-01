import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        String text = """
                T-shirt, Jeans
                Coat, Dress, Shirt
                """;
        BiConsumer<String, Consumer<String>> bc =
                (txt, consumer) -> {
                    for (String s : txt.split(", ")) {
                        consumer.accept(s);
                    }
                };
        text.lines()
                .mapMulti(bc)
                .forEach(s -> System.out.print(s + " "));
    }
}