# 6章 インタフェース × abstract 総合問題集

今日の応用問題（問題6-13/14系）をまとめた通し問題集。各問題で「コンパイルが成功するものをすべて」選ぶ。答えは一番下の「解答」セクションにまとめてあるので、先に全部解いてから確認すること。

## 前提知識メモ

- interfaceの抽象メソッドは暗黙的に`public`。実装側で`public`を書かないとコンパイルエラー
- 継承のルール：クラス同士は`extends`1つだけ／クラスがインタフェースを実装するのは`implements`（複数可）／**インタフェース同士は`extends`で複数継承可能**
- オーバーライドかオーバーロードかを決めるのは名前＋引数リストだけ。戻り値は一切関与しない
- オーバーライドで戻り値が非共変（互換性なし）だとコンパイルエラー
- **abstractは「未完成であることを許された、正直な自己申告」**。abstractクラスは抽象メソッドを実装しなくてよいが、それが許されるのは「単なる未実装（先送り可能）」のときだけ。「無関係な複数のdefaultメソッドの衝突（矛盾）」はabstractであっても自動解決されず、明示的な再宣言（`public abstract 戻り値の型 メソッド名();`）が必須
- 「最も具体的なdefaultメソッドが自動的に選ばれる」というルールがある。片方がもう片方をオーバーライドした関係（`extends`で繋がっている）なら衝突ではなく自動解決される。無関係な者同士のdefaultは自動解決されない

---

## 問題1

**A.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default String move() { return "swim"; }
}
class Duck implements Flyer, Swimmer {
}
```

**B.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default String move() { return "swim"; }
}
class Duck implements Flyer, Swimmer {
    public String move() { return "fly+swim"; }
}
```

**C.**
```java
interface Animal {
    default String move() { return "move"; }
}
interface Flyer extends Animal {}
interface Swimmer extends Animal {}
class Duck implements Flyer, Swimmer {
}
```

**D.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default String move(int speed) { return "swim " + speed; }
}
class Duck implements Flyer, Swimmer {
}
```

**E.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default int move() { return 1; }
}
class Duck implements Flyer, Swimmer {
    public String move() { return "override"; }
}
```

**F.**
```java
interface Animal {
    void speak();
}
class Dog implements Animal {
    void speak() {
        System.out.println("Woof");
    }
}
```

---

## 問題2

**A.**
```java
interface Animal {
    default String move() { return "move"; }
}
interface Flyer extends Animal {
    default String move() { return "fly"; }
}
interface Swimmer extends Animal {}
class Duck implements Flyer, Swimmer {
}
```

**B.**
```java
interface Flyer {
    default String fly() { return "fly"; }
}
interface Swimmer {
    default String swim() { return "swim"; }
}
interface Duck extends Flyer, Swimmer {
}
```

**C.**
```java
interface Flyer {
    default String move(int speed) { return "fly " + speed; }
}
interface Swimmer {
    default String move(String style) { return "swim " + style; }
}
class Duck implements Flyer, Swimmer {
}
```

**D.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    String move();
}
class Duck implements Flyer, Swimmer {
}
```

**E.**
```java
interface Animal {
    void speak();
}
abstract class Dog implements Animal {
}
```

**F.**
```java
interface Animal {
    void speak();
}
class Dog implements Animal {
    protected void speak() {
        System.out.println("Woof");
    }
}
```

---

## 問題3

**A.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    String move();
}
abstract class Duck implements Flyer, Swimmer {
    public abstract String move();
}
```

**B.**
```java
abstract class Dog {
    private abstract void speak();
}
```

**C.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default String move() { return "swim"; }
}
abstract class Duck implements Flyer, Swimmer {
}
```

**D.**
```java
interface Flyer {
    default String move() { return "fly"; }
}
abstract class Bird implements Flyer {
    public abstract String move();
}
```

**E.**
```java
abstract class Dog {
    abstract final void speak();
}
```

**F.**
```java
interface Animal {
    private abstract void speak();
}
```

---

## 問題4

**A.**
```java
interface Animal {
    void speak();
}
abstract class Dog implements Animal {
    protected abstract void speak();
}
```

**B.**
```java
abstract class Dog {
    static abstract void speak();
}
```

**C.**
```java
interface Animal {
    void speak();
}
abstract class Dog implements Animal {
    public abstract void speak();
}
```

**D.**
```java
interface Animal {
    void speak();
}
abstract class Dog implements Animal {
    abstract void speak();
}
```

**E.**
```java
abstract class Dog {
    abstract void speak();
    abstract void speak(String message);
}
```

**F.**
```java
interface Animal {
    void speak();
}
abstract class Dog implements Animal {
}
```

---

## 問題5（総合）

前提：
```java
interface Flyer {
    default String move() { return "fly"; }
}
interface Swimmer {
    default String move() { return "swim"; }
}
interface Walker extends Flyer {
    default String move() { return "walk"; }
}
```

**A.**
```java
abstract class Animal implements Walker, Swimmer {
}
```

**B.**
```java
abstract class Animal implements Walker, Swimmer {
    public abstract String move();
}
```

**C.**
```java
class Animal implements Walker, Swimmer {
}
```

**D.**
```java
class Animal implements Walker, Flyer {
}
```

---

## 解答

| 問題 | 正解 |
|---|---|
| 1 | B, C, D |
| 2 | A, B, C, E |
| 3 | A, D |
| 4 | C, E, F |
| 5 | B, D |

すべてjavac(--release 17)で実機検証済み。

---

## 実施記録

### 1回目（2026-08-22）

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| 1 | B, C, D, F | B, C, D | Fが誤り（余分） |
| 2 | A(迷った), B, C, E | A, B, C, E | 正解 |
| 3 | A(迷った), B(迷った), D | A, D | Bが誤り（余分） |
| 4 | C, E, F | C, E, F | 正解 |
| 5 | B, D | B, D | 正解 |

5問中3問完答（2, 4, 5）。1, 3で余分な選択肢を1つずつ含めてしまった。2, 4, 5は迷いなく正解、1と3は要復習。

### 迷ったポイントの詳細

**問題2-A（迷ったが正解）**：`Animal`のdefault `move()`を`Flyer`がオーバーライドし、`Swimmer`は`Animal`のものをそのまま継承しているだけ、というケース。defaultメソッドが2つの経路から来ているので「衝突している」と早合点しそうになるが、実際は**「最も具体的な方（Flyer側）が自動的に選ばれる」**ため衝突にならず、何もオーバーライドしなくてもコンパイルが通る。「defaultが複数関わっている＝即衝突」ではない、という点が引っかかりどころ。

**問題3-A（迷ったが正解）**：`Flyer`のdefault `move()`と`Swimmer`のabstract `move()`が衝突している状態を、abstractクラス側で`public abstract String move();`と**明示的に再宣言するだけ**（中身は書かない）で解決できるかどうか。答えは「できる」。abstractクラスは矛盾をそのまま放置できないが、「私はabstractとして扱います」と意思表示すれば矛盾を解消できる。

**問題3-B（迷った上に誤り）**：`private abstract void speak();`のように、`private`と`abstract`を組み合わせられるかどうか。**常に不可**（コンパイルエラー：「修飾子abstractとprivateの組合せは不正です」）。理由は、`private`メソッドはそもそもオーバーライドされない（継承されない）性質を持つため、「実装を子クラスに強制する」というabstractの目的そのものと根本的に矛盾するから。`abstract`と`final`の組み合わせも同じ理屈で禁止されている（`final`＝オーバーライド禁止 vs `abstract`＝オーバーライド必須、で矛盾）。

#### なぜprivateだけがabstractと組み合わせられないのか

`abstract`が意味しているのは、**「このメソッドは、いずれかのサブクラスが必ずオーバーライドして中身を埋めなければならない」という制約**です。

この制約が成立するには、**サブクラスからそのメソッドが見えていること**が大前提です。見えていなければ、そもそもオーバーライドしようがありません。

| アクセス修飾子 | サブクラスから見えるか |
|---|---|
| `protected` | 見える（パッケージが違っても、サブクラスであれば見える） |
| 無印（package-private） | 見える（同一パッケージ内のサブクラスなら） |
| `public` | 見える（常に） |
| **`private`** | **見えない（サブクラスであっても、privateは絶対に見えない）** |

`private`だけが「どんなサブクラスからも、絶対に見えない」というアクセス修飾子です。つまり`private abstract`と書くことは、

> 「このメソッドは必ずサブクラスがオーバーライドしなければならない（`abstract`）」
> でも同時に
> 「このメソッドはサブクラスからは絶対に見えない（`private`）」

という、**2つの相反する要求を同時に出している**ことになります。オーバーライドする側は、対象のメソッドが見えていなければ何もできません。この矛盾は`protected`・無印・`public`のどれでも起きず、`private`のときだけ発生するので、コンパイラは`private abstract`の組み合わせを問答無用で禁止しています。

`abstract`という言葉は「私を継承して完成させて」という約束なので、**それを継承・完成させることを不可能にしてしまう修飾子（`private`か`final`）とは絶対に共存できない**、と覚えておくと今後も迷わない。

（実機検証済み：`protected abstract`・`public abstract`・無印`abstract`はいずれも単体のabstractクラスでは問題なくコンパイルできる）

### defaultキーワードはinterfaceの中でしか使えない（忘れがちな点）

```java
interface Animal {
    default String move() { return "move"; }
}
interface Flyer extends Animal {
    default String move() { return "fly"; }   // Animalのmove()をオーバーライド
}
interface Swimmer extends Animal {
    default String move() { return "swim"; }  // Swimmerも別にオーバーライド
}
class Duck implements Flyer, Swimmer {
    public String move() { return "fly"; }     // ← 衝突を解決するクラス側のメソッド
}
```

**この形は衝突する**。`Flyer`も`Swimmer`も**両方が独立して**`Animal.move()`をオーバーライドしているため、「最も具体的な方が自動的に選ばれる」ルールが使えない（このルールは片方だけがオーバーライドしている場合にしか働かない）。`Duck`側で明示的に解決する必要がある。

このとき、`Duck`側の解決メソッドに`default`は**書けない**（`修飾子defaultをここで使用することはできません`というコンパイルエラーになる）。

| 場所 | 実装ありのメソッドをどう表すか |
|---|---|
| interfaceの中 | `default`キーワードを付ける（付けないと暗黙的にabstract） |
| クラスの中 | 何も付けず、ただ`{}`本体を書くだけ（デフォルトで実装済み扱い） |

interfaceの中では、メソッドは何も書かなければ暗黙的に抽象メソッドになるため、「実装ありですよ」と区別する目印として`default`が必要になる。一方クラスの中では、本体`{}`を書けばそれだけで実装済みメソッドとして扱われるので、区別する目印自体が不要＝`default`という単語がそもそも使えない。**「default＝interface専用のキーワード」**として覚えておく。

（実機検証済み）
