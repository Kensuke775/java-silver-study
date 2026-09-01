public class Counter {
    private int counter = 0;
    public static final int MAX_NUMBER = 5;
    public synchronized void increment() {
        while(counter == MAX_NUMBER) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        System.out.println(Thread.currentThread().getName()
                                        + ": " + ++counter);
        notifyAll();
    }
    public void decrement() {
        synchronized (this) {
            while(counter == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {}
            }
            System.out.println(Thread.currentThread().getName()
                                        + ": " + --counter);
            notifyAll();
        }
    }
}