# 6章 interfaceのstaticメンバ 問題集

## 前提知識メモ

- interfaceには`static`メソッドを定義できる（Java 8〜）。本体は**必須**（`abstract`のように本体を省略することはできない）
- Java 9以降、interfaceには`private static`メソッドも定義できる。用途は「同じinterface内の他の`static`/`default`メソッドから呼ばれる内部ヘルパー」
- interfaceの`static`メソッドは、**実装クラスにも子interfaceにも継承されない**。呼び出しは必ず宣言元のinterface名を通す（`Util.square(5)`）。`Impl.square(5)`のように実装クラス名で呼ぶことはできない
- `private static`メソッドは同じinterface内からしか呼び出せない。外部から`Util.helper(5)`のように直接呼ぶとコンパイルエラー（`private`は単なるマーカーではなく実際にアクセス制御として機能する）
- interfaceの`static`メソッドは**インスタンスAPI（オーバーライドの対象）に一切参加しない**。実装クラス側が同名同シグネチャのインスタンスメソッドを定義しても、両者は無関係な別メソッドとして共存する。呼び出し方法（`インスタンス変数.method()` vs `Interface名.method()`）自体が違うため、名前空間として最初から分離されている
- `static`と`abstract`は組み合わせ不可（6-13で確認済みのルールと同じ理由）

---

## 問題6-15：interfaceのstaticメンバ（選択式）

```java
interface Util {
    private static int helper(int x) { return x * 2; }
    static int square(int x) { return helper(x) + x; }
}

class Impl implements Util {
    public int square(int x) { return x + 1; }
}

public class Main {
    public static void main(String[] args) {
        Impl i = new Impl();
        System.out.println(i.square(5));
        System.out.println(Util.square(5));
        // System.out.println(Impl.square(5));   ← ここに注目
    }
}
```

このコードと、以下の説明のうち**正しいものを3つ**選んでください。

**A.**
「`Util.square(5)`は呼び出し可能で、`helper(x)`は同じinterface内から呼ばれているので`private static`でも問題なく使える」

**B.**
「`Impl.square(5)`（コメントアウトされている行を有効化した場合）はコンパイルできる。`Util`のstaticメソッドは`Impl`に継承されるため」

**C.**
「`i.square(5)`と`Util.square(5)`は互いに無関係な別々のメソッドとして扱われる。interfaceのstaticメソッドはインスタンスAPIの一部ではないため、`Impl`が同じシグネチャのインスタンスメソッドを定義しても衝突しない」

**D.**
「`static`メソッドをinterface内に定義する場合、本体（処理内容）を省略して`abstract`メソッドのように宣言することはできない。必ず本体を書く必要がある」

**E.**
「`helper(x)`は`Util`の外部から`Util.helper(5)`のように直接呼び出せる。`private`は単にオーバーライド防止のためのマーカーに過ぎない」

---

## 解答

正解：**A, C, D**

- **A**：正しい。`private static helper()`は同じinterface内の他の`static`/`default`メソッドから普通に呼び出せる。
- **B**：誤り。interfaceの`static`メソッドは実装クラスにも子interfaceにも継承されない。呼び出しは必ず`Util.square(5)`のように宣言元のinterface名を通す必要があり、`Impl.square(5)`はコンパイルエラー（「シンボルを見つけられません」）。
- **C**：正しい。interfaceの`static`メソッドはインスタンスAPIに一切参加しないため、実装クラス側が同名同シグネチャのインスタンスメソッドを定義しても無関係な別メソッドとして共存する（検証済み：`i.square(5)`→6、`Util.square(5)`→25、両方独立に動作）。
- **D**：正しい。interfaceの`static`メソッドは常に本体必須。本体を省略すると「メソッド本体がないか、abstractとして宣言されています」エラー。
- **E**：誤り。`private`は実際にアクセス制御として機能する。`Util.helper(5)`を外部から呼ぶと「helper(int)はUtilでprivateアクセスされます」というコンパイルエラーになる。

すべてjavac(--release 17)/javaで実機検証済み。

---

## 実施記録

### 1回目（2026-08-22）

| 回答 | 正解 | 判定 |
|---|---|---|
| A, C, D | A, C, D | 正解 |

一発完答。ただしCで迷った（?）。

### 迷ったポイントの詳細

**C（迷った末に正しく選択）**：「interfaceのstaticメソッドは、同じシグネチャ（名前・引数）であっても実装クラスのインスタンスメソッドと衝突しないのか」という点で迷った。

自分で辿り着いた結論：interfaceの`static`メソッドは`インスタンス変数.method()`という呼び出し方ができず、**必ず`Interface名.method()`という形でしか直接呼び出せない**。つまり呼び出し経路（名前空間）そのものが最初から分離されている。だから同じシグネチャであっても「衝突」という概念自体が発生しない——インスタンスメソッドの世界とstaticメソッド（interface名経由）の世界が、そもそも別のレーンにいる。

この気づきは正しい。呼び出し構文の違い（`i.square(5)` vs `Util.square(5)`）がそのまま「オーバーライドの対象になるかならないか」の境界線になっている、という理解でOK。
