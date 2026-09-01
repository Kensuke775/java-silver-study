public class Main {
    public static void main(String[] args) {
        Box<Integer> iBox = new Box<>();
     // Box<> err = new Box()<String>;   // コンパイルエラー
        method(new Box<>());
        Box<String> sBox = method();
    }
    public static void method(Box<String> box) { /* 処理 */ }
    public static Box<String> method() {
        return new Box<>();
    }
}