import java.util.concurrent.ThreadLocalRandom;

class CountUp implements Runnable {
    private Counter c;
    public CountUp(Counter c) { this.c = c; }
    @Override
    public void run() {
        long millis = ThreadLocalRandom.current().nextLong(5);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {}
            c.increment();
        }
    }
}
class CountDown implements Runnable {
    private Counter c;
    public CountDown(Counter c) { this.c = c; }
    @Override
    public void run() {
        long millis = ThreadLocalRandom.current().nextLong(5);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {}
            c.decrement();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        Counter c = new Counter();
        new Thread(new CountUp(c), "CountUp-Thread").start();
        new Thread(new CountDown(c), "  CountDown-Thread").start();
    }
}