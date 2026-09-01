class FirstRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("First Runnable: "
                        + Thread.currentThread().getName());
    }
}
public class Main {
    public static void main(String[] args) {
        new Thread(new FirstRunnable(), "Thread-Runnable").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous class: "
                        + Thread.currentThread().getName());
            }
        }).start();

        Runnable r = () -> System.out.println("Lambda expression: "
                        + Thread.currentThread().getName());
        new Thread(r).start();
    }
}