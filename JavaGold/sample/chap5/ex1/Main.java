public class Main {
    public static void main(String... args) {
        Runnable r1 = () -> System.out.print("r1 ");
        Runnable r2 = () -> System.out.print("r2 ");
        new Thread(r1, "Runnable");
        new Thread(r2).start();
    }
}