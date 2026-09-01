import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String result1 = Stream.of("Duke", "Amy", "James")
                .collect(Collectors.mapping(
                        String::toUpperCase,
                        Collectors.joining(" ** ")));
        System.out.println("result1: " + result1);
        String result2 = Stream.of("Duke", "Amy", "James")
                .collect(Collectors.filtering(
                        s -> s.length() > 3,
                        Collectors.joining(", ", "<<< ", " >>>")));
        System.out.println("result2: "+ result2);
        Optional<String> result3 = Stream.of("Duke", "Amy", "James")
                .collect(Collectors.filtering(
                        s -> s.length() > 3,
                        Collectors.minBy(Comparator.naturalOrder())));
        System.out.println("result3: "+ result3);
    }
}