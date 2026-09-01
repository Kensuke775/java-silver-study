public class Main {
    public static void main(String[] args) {
        String name = "Duke"; int age = 30;
        String text =
                String.format("Hi %s, you're %d now!", name, age);
        System.out.println(text);
        System.out.printf("Hi %2$s, you're %1$d now!%n", age, name);

        System.out.printf("value: %05d%n", 123);
        System.out.printf("value: %,5d%n", 1234);
        System.out.printf("value: %5.2f%n", 3.14);
    }
}