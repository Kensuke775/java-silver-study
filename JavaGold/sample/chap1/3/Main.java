public class Main {
    public static void main(String[] args) {
        Box box = new Box(); box.set("Java");
        String s = (String) box.get();
        box.set(10);
     // s = (String) box.get(); // ClassCastException
    }
}