class FirstThread extends Thread {
    @Override
    public void run() {
        System.out.println("First Thread: " + this.getName());
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Main Thread: "
                        + Thread.currentThread().getName());
        Thread t = new FirstThread();
        t.start();
     // t.start();          // IllegalThreadStateException
        new FirstThread().start();
        System.out.println("** End of main() **");
    }
}