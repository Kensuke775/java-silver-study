class ResourceOperator implements Runnable {
    private String resource1;
    private String resource2;
    public ResourceOperator(String resource1, String resource2) {
        this.resource1 = resource1;
        this.resource2 = resource2;
    }
    @Override
    public void run() {
        synchronized (resource1) {
            System.out.println(Thread.currentThread().getName()
                    + " locked resource1 (" + resource1 + ")");
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName()
                        + " locked resource2 (" + resource2 + ")");
            }
        }
    }
}
public class Main {
    public static void main(String... args) {
        String resourceA = "resource-A";
        String resourceB = "resource-B";
        new Thread(new ResourceOperator(resourceA, resourceB),
                                "First-thread").start();
        new Thread(new ResourceOperator(resourceB, resourceA),
                                "Second-thread").start();
    }
}