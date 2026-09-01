public class Main {
    public static void main(String... args) {
        Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        t1.start();
        t2.start();
        t1.run();                       // (A)
        t2.start();                     // (B)
    }
}
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + " ");
    }
}