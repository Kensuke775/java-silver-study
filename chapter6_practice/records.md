# 6章 record 総合問題集

今日出したrecord応用問題（問題6-9系）をまとめた通し問題集。各問題で「コンパイルが成功するものをすべて」選ぶ。答えは一番下の「解答」セクションにまとめてあるので、先に全部解いてから確認すること。

## 前提知識メモ

- recordヘッダーの丸括弧`(...)`は、コンポーネントが0個でも省略不可（`record Empty() {}`はOK、`record Empty {}`はエラー）
- コンストラクタは3パターンあり、`this.x=x`の要不要が全部違う
  - コンストラクタを書かない → 正準コンストラクタが丸ごと自動生成される（`this.x=x`等は不要）
  - **コンパクトコンストラクタ**（`Point { ... }`、引数リストなし） → `this.x=x`のようなフィールドへの直接代入は**禁止**（書くとエラー）。パラメータ`x`自体の書き換え（`x = Math.abs(x);`）は合法
  - **標準（長形式）の正準コンストラクタ**（`Point(int x, int y) { ... }`、引数リストあり） → `this.x=x`等の代入が**必須**（書かないとエラー）
- 非正準コンストラクタ（コンポーネントと違う引数の数）は、最初の行で必ず`this(...)`によって正準コンストラクタに委譲しなければならない。直接`this.x=x`と書くのは不可
- `this(0, y)`のような委譲は、名前ではなく**位置**で引数が対応する（1番目の引数→x、2番目の引数→y）
- recordに追加できるのは`static`フィールド・`static`メソッド・アクセサメソッドのオーバーライド・ネストしたrecord・ローカルrecord（メソッド内で定義、暗黙的にstatic）。追加できないのはコンポーネント以外の**インスタンス**フィールドだけ（初期値の有無に関わらず不可）
- recordのアクセサメソッド（`x()`など）は暗黙的に`public`のみ（`static`でも`final`でもない）。だからオーバーライド可能。interfaceのフィールドが暗黙`public static final`になるのとは別ルールなので混同しないこと

---

## 問題1

**A.**
```java
record Point(int x, int y) {
    int z;
}
```

**B.**
```java
record Point(int x, int y) {
    static int count = 0;
}
```

**C.**
```java
record Point(int x, int y) {
    Point(int x) {
        this.x = x;
        this.y = 0;
    }
}
```

**D.**
```java
record Point(int x, int y) {
    Point(int x) {
        this(x, 0);
    }
}
```

**E.**
```java
record Point(int x, int y) {
    public int x() {
        return Math.abs(x);
    }
}
```

**F.**
```java
class Outer {
    record Inner(int v) {}
}
```

---

## 問題2

**A.**
```java
record Point(int x, int y) {
    int z = 0;
}
```

**B.**
```java
record Point(int x, int y) {
    static int sum(Point p) {
        return p.x() + p.y();
    }
}
```

**C.**
```java
record Point(int x, int y) {
    Point(int y) {
        this.y = y;
        this.x = 0;
    }
}
```

**D.**
```java
record Point(int x, int y) {
    Point(int y) {
        this(0, y);
    }
}
```

**E.**
```java
record Point(int x, int y) {
    public int y() {
        return y * 2;
    }
}
```

**F.**
```java
class Outer {
    void method() {
        record Local(int v) {}
        Local l = new Local(5);
        System.out.println(l.v());
    }
}
```

---

## 問題3

**A.**
```java
record Point(int x, int y) {
    Point {
        x = Math.abs(x);
    }
}
```

**B.**
```java
record Empty() {}
```

**C.**
```java
record Empty {}
```

**D.**
```java
record Point(int x, int y) {
    Point(int x, int y) {
        if (x < 0) throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
    }
}
```

**E.**
```java
record Point(int x, int y) {
    Point {
        if (x < 0) throw new IllegalArgumentException();
    }
}
```

**F.**
```java
record Point(int x, int y) {
    public Point {
        this.x = x;
        this.y = y;
    }
}
```

---

## 解答

| 問題 | 正解 |
|---|---|
| 1 | B, D, E, F |
| 2 | B, D, E, F |
| 3 | A, B, D, E |

すべてjavac(--release 17)で実機検証済み。

---

## 実施記録

### 1回目（2026-08-22）

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| 1 | D, E, F | B, D, E, F | Bが抜けている |
| 2 | B, D, E, F(Cは迷った末に除外) | B, D, E, F | 正解 |
| 3 | A, B, D, E | A, B, D, E | 正解 |

3問中2問完答（2, 3）。1でBを見落とした。

### 迷ったポイントの詳細

**問題1-B（見落とし）**：`record Point(int x, int y) { static int count = 0; }` — recordに`static`フィールドを追加できるかどうか。**できる**。recordで禁止されているのは「コンポーネント以外の**インスタンス**フィールド」だけで、`static`フィールドはインスタンスの状態を持たないため問題なく追加できる。見落としの原因は「recordはコンポーネント以外のフィールドを持てない」というルールを、`static`フィールドにも一律に適用してしまったこと。「インスタンスフィールドだけがダメ」という条件を忘れないこと。

**問題2-C（迷った末に正しく除外）**：`record Point(int x, int y) { Point(int y) { this.y = y; this.x = 0; } }` — 非正準コンストラクタ（コンポーネントと引数の数が違う）が、`this(...)`委譲を使わず直接`this.x`・`this.y`に代入しているケース。**コンパイルエラー**（「コンストラクタが標準でないため、先頭文がクラスPointの他のコンストラクタを呼び出す必要があります」）。非正準コンストラクタは、最初の行で必ず`this(...)`によって正準コンストラクタに委譲しなければならない。正しく除外できていた。
