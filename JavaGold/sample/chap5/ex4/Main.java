public class Main {
    public static void main(String... args) {
        Thread th = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.print(i + " ");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                System.out.println("Interrupted ");
            }
        });
        th.start();
        th.join(300);
        System.out.print("Finished ");
    }
}