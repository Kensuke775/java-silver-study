import java.util.concurrent.*;

public class Main {
    public static void main(String... args) {
        CyclicBarrier barrier = new CyclicBarrier(3,
                () -> System.out.println("TRIPPED!"));
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 1; i <= 3; i++) {
            final int taskNo = i;
            executor.submit(() -> {
                try {
                    System.out.println("Task started:" + taskNo);
                    barrier.await(1, TimeUnit.SECONDS);
                    System.out.println("Passed barrier:" + taskNo);
                } catch (TimeoutException e) {
                    System.out.println("TIMEOUT!");
                } catch (BrokenBarrierException e) {
                    System.out.println("BROKEN!");
                } catch (InterruptedException e) {
                    System.out.println("INTERRUPTED!");
                }
            });
        }
        executor.shutdown();
    }
}