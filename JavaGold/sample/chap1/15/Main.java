import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        // Double-ended queue
        Deque<String> deque = new ArrayDeque<>();
        deque.add("A"); deque.addFirst("B");deque.addLast("C");
        System.out.println("-- Deque -- " + deque);
        System.out.println("remove()     : " + deque.remove());
        System.out.println("removeFirst(): " + deque.removeFirst());
        System.out.println("removeLast() : " + deque.removeLast());
        System.out.println("isEmpty()    : " + deque.isEmpty());
        // Stack
        Deque<String> stack = new ArrayDeque<>();
        stack.push("First"); stack.push("Second");
        System.out.println("-- Stack -- " + stack);
        System.out.println("pop()    : " + stack.pop());
        System.out.println("pop()    : " + stack.pop());
        System.out.println("isEmpty(): " + stack.isEmpty());
    }
}