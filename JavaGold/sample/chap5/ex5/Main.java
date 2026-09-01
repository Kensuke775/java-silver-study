public class Main {
    public static void main(String... args) {
        Thread th = new Thread(() -> {
            try {
                while (true) {
                    System.out.print("Started ");
                    Thread.sleep(1000);
                    System.out.print(Thread.currentThread().getName());
                }
            } catch (InterruptedException e) {
                System.out.print("Interrupted ");
            }
        }, "Runnable ");
        th.start();
        th.interrupt();
    }
}