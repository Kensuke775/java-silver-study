# 第7章 章末問題（原本7-1〜7-16）復習ログ

`problems_v1.md`はClaude作成のオリジナル問題（問題1〜）用。こちらのv2は、教科書の章末問題（原本7-1〜7-16）の答え合わせと、間違えた問題の復習記録専用。

<a id="grading-2026-08-25"></a>
## 採点結果（2026-08-25）

8/16 正解。

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| 7-1 | AD | A、D | ✓ |
| 7-2 | BCD | B、C、D | ✓ |
| 7-3 | CD | C、D | ✓ |
| 7-4 | AD | B、D | ✗ |
| 7-5 | D | E | ✗ |
| 7-6 | B | B | ✓ |
| 7-7 | D | D | ✓ |
| 7-8 | BE | A、E | ✗ |
| 7-9 | BCD | B、C、D | ✓ |
| 7-10 | C | E | ✗ |
| 7-11 | D | D | ✓ |
| 7-12 | BC | C、E | ✗ |
| 7-13 | ACE | A、B、C | ✗ |
| 7-14 | A | A | ✓ |
| 7-15 | E | B | ✗ |
| 7-16 | ADE | A、B、E | ✗（javacで検証済み、[chap7/15](../../sample/chap7/16/Main.java)の`FirstException`/`SecondException`と同一） |

間違えた8問：7-4, 7-5, 7-8, 7-10, 7-12, 7-13, 7-15, 7-16。ここから1問ずつ復習していく。

---

<a id="q7-4-review"></a>
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

| catch型 | 結果 |
|---|---|
| `ClassCastException` | 無関係な兄弟 → キャッチ不可、異常終了 |
| `NumberFormatException` | 自分自身 → キャッチ成功 |
| `NullPointerException` | 無関係な兄弟 → キャッチ不可、異常終了 |
| `RuntimeException` | 祖先 → キャッチ成功 |
| `ArithmeticException` | 無関係な兄弟 → キャッチ不可、異常終了 |
| `IllegalArgumentException`（選択肢外で検証） | 祖先 → キャッチ成功 |
| `Exception`（選択肢外で検証） | 祖先 → キャッチ成功 |

**迷ったポイント**：`NullPointerException`を選んでしまった（`s`が`null`だから`NullPointerException`が飛ぶと誤解）。実際は`Integer.parseInt()`の内部実装が、引数が`null`の場合も「解析できない文字列」として扱い`NumberFormatException`を投げる（`NullPointerException`は投げない）。
