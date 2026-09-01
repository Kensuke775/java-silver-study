import java.util.*;

public class Main {
    public static void main(String... args) {
        List<Member> members = new ArrayList<>();
        members.add(new Member("Carol", 5000));
        members.add(new Member("Elizabeth", 300));
        members.add(new Member("Amanda", 1000));
        // insert code here
        for (Member obj : members) {
            System.out.print(obj.name() + ":" + obj.points() + " ");
        }
    }
}
record Member(String name, int points) {}