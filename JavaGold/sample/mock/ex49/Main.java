import java.util.*;

public class Main {
    public static void main(String... args) {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(0);
        deque.addLast(1);
        deque.push(2);
        System.out.print(deque.peek());
        deque.offerLast(3);
        deque.forEach(System.out::print);
    }
}