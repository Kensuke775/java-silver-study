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
