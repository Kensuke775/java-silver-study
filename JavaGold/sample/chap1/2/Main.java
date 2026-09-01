public class Main {
    public static void main(String[] args) {
        Integer iWrapper = 100;     // オートボクシング
        int iValue = iWrapper;      // オートアンボクシング
     // Double dWrapper = 10;       // コンパイルエラー
        Long lWrapper = null;
     // long lvalue = lWrapper;     // NullPointerException
    }
}