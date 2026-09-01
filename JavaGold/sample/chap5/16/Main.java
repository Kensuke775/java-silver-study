import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        int parties = 3;
        Runnable barrierAction =
            () -> System.out.println("--- BARRIER IS TRIPPED ---");
        CyclicBarrier barrier =
            new CyclicBarrier(parties, barrierAction);
        for (int i = 0; i < parties; i++) {
            new MyThread(barrier).start();
        }
    }
}
class MyThread extends Thread {
    CyclicBarrier barrier;
    public MyThread(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        long millis = ThreadLocalRandom.current().nextLong(500);
        try {
            System.out.println(threadName + " at the barrier point");
            Thread.sleep(millis);
            barrier.await();
            System.out.println(threadName + " has passed the barrier");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}