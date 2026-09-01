import java.util.ArrayDeque;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<String> queue = new ArrayDeque<>();
        queue.add("First"); queue.offer("Second");
        System.out.println("peek(): " + queue.peek()); // First
        System.out.println("size(): " + queue.size()); // 2
        System.out.println("poll(): " + queue.poll()); // First
        System.out.println("poll(): " + queue.poll()); // Second
        System.out.println("size(): " + queue.size()); // 0
        System.out.println("peek(): " + queue.peek()); // null
     // System.out.println("element(): " + queue.element());
    }
}