import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = List.of("Amy", "Duke", "James", "Robert");
        int maxLength = list.parallelStream()
                .reduce(0, (max, name) -> {
                    int length = name.length();
                    System.out.print(name + ":" + length + " ");
                    return Integer.max(max, length);
                }, Integer::max);
        System.out.println("[Max]:" + maxLength);
    }
}