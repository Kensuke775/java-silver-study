# chapter7 2周目 オリジナル問題(sample/chap7/N形式)

## 問題1-1

```java
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("AIOOBE");
        } catch (RuntimeException e) {
            System.out.println("RE");
        } catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
```

このプログラムを引数なしで実行すると、出力はどうなるか。

A. `AIOOBE`
B. `RE`
C. `Exception`
D. コンパイルエラーが発生する

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。
解説：`args[0]`は引数なしなので`ArrayIndexOutOfBoundsException`(RuntimeExceptionのサブクラス)がスローされる。catchは子クラスから親クラスの順に正しく並んでいるので、一番具体的な`ArrayIndexOutOfBoundsException`のcatchブロックが最初にマッチする。

## 問題1-2

```java
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(args[0]);
        } finally {
            System.out.println("finally");
            throw new RuntimeException("from finally");
        }
    }
}
```

このプログラムを引数なしで実行すると、どうなるか。

A. `finally`と出力された後、`ArrayIndexOutOfBoundsException`がスローされる
B. `finally`と出力された後、`RuntimeException("from finally")`がスローされる
C. 何も出力されずに`ArrayIndexOutOfBoundsException`がスローされる
D. コンパイルエラーが発生する

### 実施記録

回答：A
正解：B
迷ったポイント：`try`ブロックで発生した`ArrayIndexOutOfBoundsException`が、そのまま外へ伝播すると誤解した。実際には`finally`ブロックの中で新たな例外(`RuntimeException("from finally")`)が投げられると、`try`側で発生していた元の例外は握りつぶされて完全に消え、`finally`の新しい例外だけが実際に外へ伝播する。「finallyは必ず実行される」だけでなく「finally内の例外はtry内の例外を置き換えてしまう」という点を見落とした。
