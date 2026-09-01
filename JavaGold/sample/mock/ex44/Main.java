public class Main {
    public static void main(String... args) {
        Account duke = new Account("Duke", 100);
        Account james = new Account("James", 200);
        new Thread(() -> duke.movePointTo(james, 10), "T1").start();
        new Thread(() -> james.movePointTo(duke, 20), "T2").start();
    }
}
class Account {
    private final String name;
    private int point;
    Account(String name, int point) {
        this.name = name;
        this.point = point;
    }
    public void movePointTo(Account other, int amount) {
        synchronized (this) {
            synchronized (other) {
                this.point -= amount;
                other.point += amount;
                System.out.printf("%s moved %dpt from %s to %s%n",
                        Thread.currentThread().getName(),
                        amount, this.name, other.name);
            }
        }
    }
}