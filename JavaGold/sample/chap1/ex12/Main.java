import java.util.ArrayDeque;
import java.util.Deque;
public class Main {
    public static void main(String... args) {
        String s1 = "Duke"; String s2 = "James";
        Deque<String> deque = new ArrayDeque<String>();
        s1 = deque.peekFirst();
        deque.offer(s1); deque.add(s2);
        deque.addLast("Scott");
        System.out.println(deque.pollFirst());
    }
}