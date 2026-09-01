public class Main {
    public static void main(String... args) {
        var stream = // insert code here
        stream.filter(n -> n % 2 == 0)
                .forEach(System.out::print);
    }
}