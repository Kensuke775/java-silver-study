# 6章 応用問題まとめ（追加復習2）

`chapter6_summary.md`（原本24問）に対する補完版。応用問題（問題6-1発展）で扱った、原本には出てこなかった論点をまとめる。

## 応用問題6-1：sealed / permits の細かいルール

問題：以下A〜Fのうちコンパイルが成功するものをすべて選べ。

```java
// A（成功）
public sealed class Shape permits Circle, Square {}
final class Circle extends Shape {}
final class Square extends Shape {}

// B（成功）
sealed abstract class Vehicle permits Car {}
non-sealed class Car extends Vehicle {}

// C（成功）
abstract sealed class Animal permits Dog {}
final class Dog extends Animal {}

// D（成功）
sealed class Fruit {}
final class Apple extends Fruit {}
final class Banana extends Fruit {}

// E（失敗）
sealed class Bird permits HouseSparrow {}
final class Sparrow extends Bird {}
final class HouseSparrow extends Sparrow {}

// F（失敗）
sealed class Beverage permits Coffee {}
class Coffee extends Beverage {}
```

**正解：A, B, C, D**（javacで実機検証済み）

| # | 論点 | 結論 |
|---|---|---|
| B, C | 修飾子の並び順 | `sealed abstract` でも `abstract sealed` でもコンパイラは同じものとして扱う。順序は自由（原本にはなかった観点） |
| D | `permits`省略の正確な条件 | 「子が1つのときだけ省略可」ではない。**直接の子クラスが全員、同一ソースファイル（.java）内に揃っていれば**、何個いても省略可。パッケージが同じかどうかは無関係 |
| E | `permits`に書けるのは直接の子のみ | `Bird → Sparrow → HouseSparrow` のように孫を`permits`に書くのは無効。中間の`Sparrow`が`permits`から漏れている扱いになりコンパイルエラー |
| F | 直接の子には修飾子必須 | sealedクラスの直接の子は`final`・`sealed`・`non-sealed`のいずれかを**必ず**明示する。無指定は「sealed、non-sealedまたはfinal修飾子が必要です」でエラー |

### 「permits省略＝sealedの意味が薄れる」は誤解

`permits`を省略しても、**閉じた継承階層としての強制力はまったく変わらない**。実験で確認：

```java
// pkg/Fruit.java
package pkg;
sealed class Fruit {}
final class Apple extends Fruit {}
final class Banana extends Fruit {}

// pkg/Grape.java（同じパッケージ、別ファイル）
package pkg;
final class Grape extends Fruit {}
```

→ 結果：`Grape.java`はコンパイルエラー。
```
エラー: クラスはシール・クラスFruitを拡張できません('permits'句に指定されていないためです)
```

同じパッケージにいても、`Fruit.java`と別ファイルにいる時点で「仲間」とは認識されない。`Grape`を正式に許可するには2択のみ：
1. `Fruit`側に `permits Apple, Banana, Grape` と明示的に書く
2. `Grape`を`Fruit.java`の中に書く

→ **結論：`permits`省略は「同一ファイル内で完結しているなら、コンパイラが代わりにリストを組み立ててくれる」という省略記法にすぎない。閉じている度合い（部外者を拒否する強さ）は明示時と完全に同じ。**

## extends と implements の使い分け

- **クラスを継承する**→ `extends`（`sealed class`であっても、クラスである以上は`extends`）
- **インタフェースを実装する**→ `implements`

`sealed class Fruit {}` の子が `extends Fruit` になるのは、`Fruit`が`interface`ではなく`class`だから。`sealed interface`を実装する側であれば`implements`になる（この違いは継承元が`class`か`interface`かだけで決まり、`sealed`の有無とは無関係）。

## 応用問題6-2：フィールド隠蔽とオーバーライドの3階層ネスト

```java
class Base {
    int x = 10;
    static String label = "Base";
    int getX() { return x; }
    static String getLabel() { return label; }
}
class Middle extends Base {
    int x = 20;
    static String label = "Middle";
    int getX() { return x; }
}
class Leaf extends Middle {
    int x = 30;
    int getX() { return x + super.getX(); }
}

public class Main {
    public static void main(String[] args) {
        Base b = new Leaf();
        Middle m = new Leaf();
        Leaf l = new Leaf();

        System.out.println(b.x);          // 10
        System.out.println(m.x);          // 20
        System.out.println(l.x);          // 30
        System.out.println(b.getX());     // 50
        System.out.println(m.getX());     // 50
        System.out.println(b.label);      // Base
        System.out.println(Middle.label); // Middle
    }
}
```

（javac/javaで実機検証済み。全問正解）

| トリック | 結論 |
|---|---|
| `super.getX()`の遡り範囲 | **直近の親（1階層）だけ**を呼ぶ。祖先を連鎖して全部実行されるわけではない（`Leaf`→`super`で止まり、`Middle.getX()`が`Base.getX()`をさらに呼んだりはしない） |
| メソッド内の無修飾フィールド`x`の解決基準 | 呼び出し元の宣言型ではなく、**そのメソッドが定義されているクラス自身のフィールド**を指す。`Leaf.getX()`内の`x`は常に`Leaf.x`、`Middle.getX()`内の`x`は常に`Middle.x` |
| フィールド・static解決 | `b.x`・`m.x`・`l.x`・`b.label`・`Middle.label`は全部**宣言型基準**（動的束縛は一切効かない） |
| メソッド解決 | `b.getX()`と`m.getX()`は宣言型が違っても、実体が同じ`Leaf`なら**同じ結果**（動的束縛はメソッドにしか効かない） |

→ `Leaf.getX()` = `this.x(30) + super.getX()(Middle.getX()が返す20)` = **50**。宣言型がBaseかMiddleかは無関係で、実体がLeafである限り同じ50になる。

## 応用問題6-3：protectedの「別パッケージ＋サブクラス」アクセスは参照の型で決まる

```java
// p1/Animal.java
package p1;
public class Animal {
    protected String sound = "Some sound";
}
// p1/Cat.java
package p1;
public class Cat extends Animal {}

// p2/DogA.java（成功）
package p2;
public class DogA extends Animal {
    void bark() { System.out.println(this.sound); }
}
// p2/DogB.java（成功）
package p2;
public class DogB extends Animal {
    void bark(DogB other) { System.out.println(other.sound); }
}
// p2/DogC.java（失敗）
package p2;
public class DogC extends Animal {
    void bark(Animal other) { System.out.println(other.sound); } // NG
}
// p2/DogD.java（失敗）
package p2;
public class DogD extends Animal {
    void bark(Cat other) { System.out.println(other.sound); } // NG
}
```

（javacで実機検証済み。A, B成功／C, D失敗）

**JLS 6.6.2.1のルール**：別パッケージのサブクラスから継承したprotectedメンバーにアクセスするには、「継承しているかどうか」だけでは不十分で、**アクセスに使う式の型が、アクセスしている側のクラス自身かそのサブタイプでなければならない**。

| | 経由した型 | 判定 | 理由 |
|---|---|---|---|
| A | `this`（自分自身） | ○ | 自分自身の型 |
| B | `DogB`（自分自身） | ○ | 自分自身の型 |
| C | `Animal`（親クラス型） | ✗ | 実体がDogCでも、コンパイル時の型が親だと不可 |
| D | `Cat`（無関係な兄弟クラス） | ✗ | Catは`Animal`のサブクラスだが、`DogD`自身でもそのサブタイプでもない |

同一パッケージ内であればこの制約自体がかからず、`Animal`型や`Cat`型経由でも普通にアクセスできる。「package内アクセス」と「別package・サブクラス経由アクセス」は別ルールが重なっている、という構造。

**補足（`this`について）**：`this`は「省略可能な引数」ではなく、インスタンスメソッド呼び出し時にJVMが暗黙で結びつける、構文上書けない特別な参照。`this.sound`と`sound`の違いは「`this.`という表記を省略できるかどうか」であって、`this`そのものの有無を選べるわけではない。

## 応用問題6-4：package-privateメソッドは「別パッケージを挟んだ瞬間」だけオーバーライドが切れる

### 第1弾：2階層（Base → Sub）

```java
// p1/Base.java
package p1;
public class Base {
    void greet() { System.out.println("Base.greet"); }  // package-private
    public void callGreet() { greet(); }
}

// p2/Sub.java（Baseとは別パッケージ）
package p2;
public class Sub extends Base {
    void greet() { System.out.println("Sub.greet"); }    // package-private
}
```

`Base b = new Sub(); b.callGreet();` の結果：

| 状況 | `Sub.greet()`はオーバーライドとして成立するか | `b.callGreet()`の出力 |
|---|---|---|
| BaseとSubが**別パッケージ** | ✗ 不成立（`Base`から`Sub`のgreet()は無関係の別メソッド） | `Base.greet`（静的解決） |
| BaseとSubが**同一パッケージ**だったら | ○ 成立する | `Sub.greet`（動的束縛） |

（javac/javaで両パターンとも実機検証済み）

### 第2弾：3階層（A → B → C、途中にパッケージ境界）

```java
// p1/A.java
package p1;
public class A {
    void foo() { System.out.println("A.foo"); }   // package-private
    public void run() { foo(); }
}
// p1/B.java（Aと同じパッケージp1）
package p1;
public class B extends A {
    void foo() { System.out.println("B.foo"); }    // package-private
}
// p2/C.java（Bとは別パッケージp2）
package p2;
public class C extends B {
    void foo() { System.out.println("C.foo"); }    // package-private
}
```

```java
A a = new C();
a.run();       // → "B.foo"
C c = new C();
c.foo();       // → "C.foo"
```

（javac/javaで実機検証済み）

**`a.run()`が実行されたときの流れ（`run()`はAで定義されていて、その中の`foo()`が動的束縛の対象になる）**

```
継承リンクごとに「オーバーライドが成立するか」を1本ずつ判定する:

  A.foo() --p1内、同一パッケージ--> B.foo()   ○ オーバーライド成立
  B.foo() --p1→p2、別パッケージ---> C.foo()   ✗ オーバーライド不成立（無関係な別メソッド）

  実体はCのオブジェクトだが、foo()という"枠"を最後に正式に上書きしているのはB。
  Cはその枠を上書きしておらず、C.foo()はCパッケージ内だけで使える別枠。

  → a.run() 内の foo() 呼び出しは、動的束縛でこの枠を辿った結果 B.foo() に着地する。
```

**この問題の核心（誤解しやすいポイント）**：「継承の途中に別パッケージが1回でも挟まったら、それ以降は全部静的解決になる」わけではない。**オーバーライドの成立・不成立はリンク単位（親子1組ごと）で判定される**。`A→B`のリンクは同一パッケージなので健全に生きたまま、`B→C`のリンクだけが切れる。動的束縛は「実体のクラスが持つ、最も末端の“正式なオーバーライド”」を辿るので、Cが不正規（別枠）である以上、その一つ手前のB.foo()が使われる。

### 「オーバーライドが切れる」のはpackage-private特有の弱点

上と全く同じA/B/Cの構造で、`foo()`の修飾子だけを`protected`に変えると結果が変わる。

```java
protected void foo() { ... }  // A, B, C すべてprotectedに変更
```
```java
A a = new C();
a.run();  // → "C.foo"（package-privateのときは"B.foo"だった）
```

（javac/javaで実機検証済み）

`protected`は「別パッケージであってもサブクラスなら継承・オーバーライドできる」ためのアクセス修飾子なので、`C`は`B`（延いては`A`）を正式に継承でき、`A→B→C`のリンクが全部繋がったまま動的束縛が効く。`public`も同じ結果になる。

| アクセス修飾子 | 別パッケージを挟んでもオーバーライドの連鎖は繋がるか |
|---|---|
| `private` | 継承自体されないので論外（常に不成立） |
| package-private（無指定） | ✗ 別パッケージを1回でも挟むとそこで切れる |
| `protected` | ○ サブクラスである限り、パッケージが違っても繋がる |
| `public` | ○ 常に繋がる |

**「オーバーライドの連鎖が途中で切れる」現象はpackage-private特有の弱点であり、`protected`以上のアクセス修飾子では起きない。**
