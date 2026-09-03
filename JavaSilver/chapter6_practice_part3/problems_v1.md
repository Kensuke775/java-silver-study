## 問題48（原本6-21由来：List.add(int, E)のインデックスシフト×List.of()の不変性）

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> base = List.of("A", "B", "C");
        List<String> copy = new ArrayList<>(base);
        copy.add(1, "X");
        copy.add(copy.size(), "Y");
        System.out.println(copy);
        base.add(1, "Z");
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `[A, X, B, C, Y]` が出力された後、`UnsupportedOperationException` がスローされる
B. `[A, X, B, C, Y]` が出力され、正常終了する
C. `[A, B, X, C, Y]` が出力された後、`UnsupportedOperationException` がスローされる
D. 実行時に `IndexOutOfBoundsException` がスローされる
E. コンパイルエラーが発生する

### 解答

正解：**A**

### 補足

- `copy`は`new ArrayList<>(base)`で**baseの中身をコピーして作った別の可変リスト**。`base`自体（`List.of(...)`が返す不変リスト）とは別物なので、`copy`への変更は`base`に一切影響しない。
- `copy.add(1, "X")`：インデックス1に`"X"`を**挿入**し、それ以降の要素を後ろに1つずつシフトする。`[A, B, C]` → `[A, X, B, C]`。
- `copy.add(copy.size(), "Y")`：このタイミングで`copy.size()`は4なので、インデックス4への挿入＝末尾への追加になる。`[A, X, B, C, Y]`。（`size()`と同じインデックスへの`add`は「配列の最後に足す」という意味で正当な呼び出しであり、`IndexOutOfBoundsException`にはならない。Dはここを誤解した選択肢）
- `System.out.println(copy)`で`[A, X, B, C, Y]`が出力される。
- 最後の`base.add(1, "Z")`：`base`は`List.of(...)`が返す**不変リスト**なので、`add`を呼ぶと`UnsupportedOperationException`が実行時にスローされる。これはコンパイルエラーではない（`List`インタフェース自体には`add`メソッドが定義されているため、コンパイルは通る）。
- 出力(`println`)は例外より前の行にあるので、出力自体は正常に行われ、その**後**で例外が発生する。Bは例外が発生しないと誤解した選択肢。

### 実施記録

回答：B
正解：A
迷ったポイント：`copy`と`base`の中身の状態は正確にトレースできていた（`copy = A, X, B, C, Y`まで一致）が、最後の`base.add(1, "Z")`で`base`が`List.of()`由来の不変リストであるため`UnsupportedOperationException`がスローされる点を見落とし、「正常終了する」（B）を選んでしまった。`new ArrayList<>(base)`でコピーした`copy`は可変になるが、コピー元の`base`自体は不変のまま変わらないという区別が抜けていた。
