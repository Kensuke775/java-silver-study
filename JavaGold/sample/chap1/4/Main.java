public class Main {
    public static void main(String[] args) {
        Box<String> box1 = new Box<String>();
        box1.set("Gold");
        String s = box1.get();
     // box1.set(10);                    // コンパイルエラー
        Box<Integer> box2 = new Box<Integer>();
        box2.set(10);
        Integer i = box2.get();
     // Box<int> box3 = new Box<int>();  // コンパイルエラー
        Box box4 = new Box();
        box4.set(true);
        Boolean b = (Boolean)box4.get();
    }
}