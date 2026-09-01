class MyThread extends Thread {
    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(getName() + ":" + i);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        System.out.println(getName() + " finished.");
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("** main thread started! **");
        Thread t = new MyThread();
        t.start();
        t.interrupt();
        t.join();
        System.out.println("t.getState(): " + t.getState());
        System.out.println("** main thread finished! **");
    }
}