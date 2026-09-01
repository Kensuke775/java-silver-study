import java.util.stream.*;

public class Main {
    public static void main(String... args) {
        Thread t = new Thread(() -> {
            IntStream.range(0, 3).forEach(i -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
                System.out.print(Thread.currentThread().getName());
            });
        }, "task ");
        try {
            t.start();
            t.interrupt();
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("end");
    }
}