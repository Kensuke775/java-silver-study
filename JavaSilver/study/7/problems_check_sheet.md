# 第7章 章末問題（原本7-1〜7-16）復習ログ

`problems_v1.md`はClaude作成のオリジナル問題（問題1〜）用。こちらのv2は、教科書の章末問題（原本7-1〜7-16）の答え合わせと、間違えた問題の復習記録専用。



## 採点結果（2026-08-25）

8/16 正解。


| 問題   | 回答  | 正解    | 判定                             |
| ---- | --- | ----- | ------------------------------ |
| 7-1  | AD  | A、D   | ✓                              |
| 7-2  | BCD | B、C、D | ✓                              |
| 7-3  | CD  | C、D   | ✓                              |
| 7-4  | AD  | B、D   | ✗                              |
| 7-5  | D   | E     | ✗                              |
| 7-6  | B   | B     | ✓                              |
| 7-7  | D   | D     | ✓                              |
| 7-8  | BE  | A、E   | ✗                              |
| 7-9  | BCD | B、C、D | ✓                              |
| 7-10 | C   | E     | ✗                              |
| 7-11 | D   | D     | ✓                              |
| 7-12 | BC  | C、E   | ✗                              |
| 7-13 | ACE | A、B、C | ✗                              |
| 7-14 | A   | A     | ✓                              |
| 7-15 | E   | B     | ✗                              |
| 7-16 | ADE | A、B、E | ✗（[問題7-16復習](#q7-16-review)参照） |


間違えた8問：7-4, 7-5, 7-8, 7-10, 7-12, 7-13, 7-15, 7-16。ここから1問ずつ復習していく。

---



## 問題7-4 復習

```java
public class Main {
    public static void main(String[] args) {
        String s = null;
        try {
            int i = Integer.parseInt(s);
            System.out.println(s);
        } catch ( /* insert code here */ ) {
            System.out.println("exception");
        }
    }
}
```

`exception`と出力するには、7行目にどのコードを挿入するか（2つ選択）。

A. `ClassCastException e`
B. `NumberFormatException e`
C. `NullPointerException ex`
D. `RuntimeException ex`
E. `ArithmeticException ex`

**正解：B、D**（自分の回答：A、D → 不正解）

### 復習ポイント

`Integer.parseInt(null)`は`NullPointerException`ではなく**`NumberFormatException`**を投げる。

継承チェーン：

```
NumberFormatException → IllegalArgumentException → RuntimeException → Exception
```

**キャッチできる条件は「自分自身の型、またはその祖先(スーパークラス)」ならどれでもよい**——「NumberFormatExceptionかRuntimeExceptionしかない」という決め打ちのルールではない。今回はたまたま選択肢の祖先型が`RuntimeException`しか無かっただけで、`IllegalArgumentException`や`Exception`が選択肢にあればそれらも正解になる。実際に`javac`+`java`で検証済み：


| catch型                              | 結果                   |
| ----------------------------------- | -------------------- |
| `ClassCastException`                | 無関係な兄弟 → キャッチ不可、異常終了 |
| `NumberFormatException`             | 自分自身 → キャッチ成功        |
| `NullPointerException`              | 無関係な兄弟 → キャッチ不可、異常終了 |
| `RuntimeException`                  | 祖先 → キャッチ成功          |
| `ArithmeticException`               | 無関係な兄弟 → キャッチ不可、異常終了 |
| `IllegalArgumentException`（選択肢外で検証） | 祖先 → キャッチ成功          |
| `Exception`（選択肢外で検証）                | 祖先 → キャッチ成功          |


**迷ったポイント**：`NullPointerException`を選んでしまった（`s`が`null`だから`NullPointerException`が飛ぶと誤解）。実際は`Integer.parseInt()`の内部実装が、引数が`null`の場合も「解析できない文字列」として扱い`NumberFormatException`を投げる（`NullPointerException`は投げない）。

---



## 問題7-16 復習

```java
public class Main {
    public static void main(String[] args) {
        try {
            FirstException ex;
            // insert code here
            throw ex;
        } catch(FirstException ex) {}
    }
}
class FirstException extends RuntimeException {
    FirstException() {}
    FirstException(Throwable cause) { super(cause); }
    FirstException(String message) { super(message); }
}
class SecondException extends FirstException {
    SecondException(Throwable cause) { super(cause); }
}
```

コンパイルを成功させるには、5行目にどのコードを挿入するか（3つ選択）。

A. `ex = new FirstException();`
B. `ex = new SecondException(new Exception());`
C. `ex = new SecondException("Second");`
D. `ex = new FirstException(new SecondException());`
E. `ex = new SecondException(new FirstException("First"));`

**正解：A、B、E**（自分の回答：A、D、E → 不正解）

### 復習ポイント

**コンストラクタは継承されない**——`SecondException`が使えるコンストラクタは、自分自身で明示的に定義した`SecondException(Throwable cause)`の1つだけ。親の`FirstException`が3つコンストラクタを持っていても、それらは`SecondException`のコンストラクタとして自動的には使えない。

選択肢ごとの判定（`javac`で検証済み）：


| 選択肢                                                   | 判定  | 理由                                                                                                                        |
| ----------------------------------------------------- | --- | ------------------------------------------------------------------------------------------------------------------------- |
| A. `new FirstException()`                             | ○   | `FirstException`の無引数コンストラクタをそのまま使用                                                                                        |
| B. `new SecondException(new Exception())`             | ○   | `Exception`は`Throwable`のサブクラスなので`SecondException(Throwable cause)`にそのまま渡せる                                                |
| C. `new SecondException("Second")`                    | ✗   | `SecondException`に`String`を受け取るコンストラクタが無い（`FirstException`にはあるが継承されない）                                                    |
| D. `new FirstException(new SecondException())`        | ✗   | 外側の`FirstException(Throwable cause)`自体は問題ないが、内側の`new SecondException()`(引数なし)に対応するコンストラクタが`SecondException`に無い            |
| E. `new SecondException(new FirstException("First"))` | ○   | 内側は`FirstException(String message)`で生成、外側は`SecondException(Throwable cause)`にその`FirstException`インスタンス(Throwableのサブクラス)を渡す |


**迷ったポイント**：Dを誤って選んでしまった。外側の`FirstException(Throwable cause)`という形自体は正しいコンストラクタ呼び出しだが、**その引数を作る内側の**`new SecondException()`**が独立してコンパイルエラーになる**点を見落とした(選択肢全体ではなく、ネストした`new`それぞれが個別にコンストラクタの妥当性チェックを受ける)。逆にCの`"Second"`を見た際に、なぜ弾かれるかの理由は正しく捉えられていた(Cは不選択で正解)。  
  
