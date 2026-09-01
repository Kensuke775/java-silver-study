public class Counter {
    private int counter = 0;
    public synchronized void increment() {
        System.out.println(Thread.currentThread().getName()
                                        + ": " + ++counter);
    }
    public void decrement() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()
                                        + ": " + --counter);
        }
    }
}