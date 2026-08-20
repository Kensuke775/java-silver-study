# 6章 追加問題集（クラス宣言・sealed・ネストクラス）

chapter6.md の内容を踏まえた、オリジナルの練習問題集。すべて実際に`javac`でコンパイル確認済み。

---

## 第1部：クラス宣言の基本修飾子

### 問題1

次のクラス宣言のうち、正しいものはどれですか。（2つ選択）

```
A. private static class Widget {}
B. final abstract class Gadget {}
C. public final class Sprocket {}
D. sealed public class Cog {}
E. sealed abstract class Gizmo permits Bolt {}
   final class Bolt extends Gizmo {}
```

**正解：C, E**

- A：トップレベルクラスに`private`・`static`は指定不可（`public`かデフォルトのみ）
- B：`abstract`と`final`は矛盾するため同時指定不可
- C：単一のアクセス修飾子＋`final`で正しい宣言
- D：`sealed`と宣言した以上、`permits`または同一ファイル内の継承先クラスが必須。Cogにはどちらもなく「シール・クラスにはサブクラスを含める必要があります」でエラー
- E：`permits`で直接の子`Bolt`を指定し、`Bolt`にも`final`が正しく付いている

---

### 問題2

次のうち正しいものはどれですか。（2つ選択）

```
A. final public class Foo {}
B. public public class Bar {}
C. public private class Baz {}
D. class public Qux {}
E. class Outer {
       static abstract class Inner {}
   }
```

**正解：A, E**

- A：修飾子同士の順番は自由（`class`より前ならOK）
- B：同じ修飾子を2回書くと「修飾子が繰り返されています」エラー
- C：異なるアクセス修飾子（public/private）を2つ重ねると不可
- D：修飾子は必ず`class`より前に書く必要がある
- E：ネストしたクラスなら`static`が使える（トップレベル限定の制約とは別）

---

## 第2部：sealed / non-sealed / permits の基本

### 問題3

次のクラス／インタフェース宣言のうち、正しいものはどれですか。（2つ選択）

```
A. sealed interface Shape permits Circle {}
   non-sealed class Circle implements Shape {}

B. public sealed record Point(int x, int y) permits Point3D {}
   final class Point3D extends Point {}

C. sealed class Animal permits Dog {}
   non-sealed class Dog extends Animal {}
   class Puppy extends Dog {}

D. abstract sealed class Tool permits Hammer {}
   sealed class Hammer extends Tool permits ClawHammer {}
   class ClawHammer extends Hammer {}

E. sealed class Vehicle permits Car, Truck {}
   final class Car extends Vehicle {}
```

**正解：A, C**

- A：シールインタフェースの実装クラスに`non-sealed`が正しく付いている
- B：`record`には`permits`句自体を書けない（構文エラー）。recordは暗黙final＝継承者を持てないため、sealedの仕組みと根本的に矛盾する
- C：`Dog`が`non-sealed`で継承制限を再開放しているので、孫の`Puppy`は修飾子なしで自由に継承できる
- D：`ClawHammer`に`final`/`sealed`/`non-sealed`のいずれも無い
- E：`permits`に`Truck`と書いてあるが、`Truck`というクラス自体が存在しない（`Car`しか定義されていない）

**補足**：`non-sealed`は「継承を制限しない」という意味（誤解しやすいが「継承できなくなる」ではなく逆）。`extends`または`implements`で直接シールされた型と繋がっている場合にのみ使える。

---

## 第3部：sealedの発展ルール（直接の子のみ／同一パッケージ制限）

### 問題4

```
A. package p1;
   public sealed class Shape permits Circle {}
   // 別ファイル、同じ package p1
   public final class Circle extends Shape {}

B. package p1;
   public sealed class Shape permits p2.Square {}
   // 別ファイル、package p2
   public final class Square extends Shape {}

C. sealed class Shape permits SmallSquare {}
   non-sealed class Square extends Shape {}
   final class SmallSquare extends Square {}

D. sealed class Bird permits Sparrow {}
   non-sealed class Sparrow extends Bird {}
   class HouseSparrow extends Sparrow {}

E. sealed final class Foo permits Bar {}
   final class Bar extends Foo {}
```

**正解：A, D**

- A：同一パッケージ内で`permits`に直接の子を正しく指定
- B：`permits`で指定できる継承先は、シールクラスと**同じパッケージ（モジュール）**内でなければならない。別パッケージだと「別のパッケージのシール・クラスを拡張できません」エラー
- C：`Shape`の`permits`が`SmallSquare`（孫）を指しているが、**`permits`に書けるのは直接の子のみ**。直接の子`Square`が`permits`に入っていないため二重にエラー
- D：`permits`が直接の子`Sparrow`を正しく指定。`Sparrow`が`non-sealed`で再開放しているので孫の`HouseSparrow`は修飾子不要
- E：`sealed`と`final`は同時指定不可（矛盾）

---

### 問題5（3階層のsealedチェーン）

```
A. sealed class A permits B {}
   sealed class B extends A permits C {}
   final class C extends B {}

B. sealed class A permits B {}
   sealed class B extends A permits C {}
   final class D extends B {}

C. sealed class A permits B {}
   non-sealed class B extends A {}
   sealed class C extends B permits D {}
   final class D extends C {}

D. sealed class A permits B, C {}
   final class B extends A {}
   class C extends A {}

E. sealed class A permits B {}
   final class B extends A {}
   class G extends B {}
```

**正解：A, C**

- A：3階層とも各段階で`permits`が直接の子を正しく指し、最後が`final`で閉じている
- B：`B`の`permits`は`C`なのに実際は`D`が継承 → `C`というクラス自体が存在せずエラー
- C：`B`が`non-sealed`で再開放した後、その子`C`が改めて`sealed`をかけ直すのはOK。**一度開放しても、下の代で再び制限をかけ直せる**
- D：直接の子`C`に修飾子が無い
- E：`final`な`B`をさらに`G`が継承しようとして矛盾

---

## 第4部：implements・record・多重実装

### 問題6

```
A. sealed interface Shape permits Circle {}
   record Circle(double r) implements Shape {}

B. interface Flyable {}
   sealed interface Swimmable permits Fish {}
   final class Fish implements Swimmable {}
   final class Duck implements Flyable, Swimmable {}

C. sealed interface Vehicle permits Car {}
   sealed interface Car extends Vehicle permits SportsCar {}
   final class SportsCar implements Car {}

D. sealed interface Speaker permits Robot {}
   class Robot implements Speaker {}

E. sealed interface Base permits Impl {}
   non-sealed interface Mid extends Base {}
   final class Impl implements Mid {}
```

**正解：A, C**

- A：`record`は暗黙的に`final`なので、`sealed interface`実装時に追加の修飾子は不要
- B：`Duck`が`Swimmable`（sealed）を実装しているが、`Swimmable`の`permits`には`Fish`しかなく`Duck`は許可されていない
- C：インタフェースも`sealed`→`sealed`（さらにpermits）→`final`という形でチェーンを正しく閉じられる（クラスと同じ理屈）
- D：`Robot`に`final`/`sealed`/`non-sealed`が無い
- E：`Impl`は`Base`を直接実装しておらず、間に`Mid`を挟んでいる。`permits`は直接の実装／継承先のみを指す

---

## 第5部：abstract × record

### 問題7

```
A. abstract record Point(int x, int y) {}

B. interface Sound { String make(); }
   record Cat(String name) implements Sound {}

C. interface Sound { String make(); }
   record Dog(String name) implements Sound {
       public String make() { return name + ": Woof"; }
   }

D. abstract class Animal { abstract String sound(); }
   class Lion extends Animal {}

E. abstract class Animal { abstract String sound(); }
   abstract class Bird extends Animal {}
```

**正解：C, E**

- A：レコードは暗黙`final`なので`abstract`と矛盾（修飾子abstractとfinalの組合せは不正）
- B：`record`は`abstract`にできない＝常に具象クラス扱いなので、インタフェースの抽象メソッド`make()`を実装する義務から逃れられない
- C：`make()`を正しく実装しているのでOK
- D：具象クラス`Lion`が親の抽象メソッド`sound()`を実装していない
- E：`Bird`自身も`abstract`にしているので、抽象メソッドを実装せずに済む

**要点**：インタフェースの抽象メソッドも、抽象クラスの`abstract`メソッドも、「未実装なら具象クラス（recordも含む）は必ずオーバーライドしなければならない」というルールは共通。逃げ道は自分自身も`abstract`にすること（recordには使えない）。

---

## 第6部：staticネストクラス vs 非static内部クラス

### 問題8

```
A. class House {
       String address = "Tokyo";
       class Room {
           void show() { System.out.println(address); }
       }
   }
   class Main {
       public static void main(String[] args) {
           House.Room r = new House().new Room();
           r.show();
       }
   }

B. class House {
       String address = "Tokyo";
       class Room {
           void show() { System.out.println(address); }
       }
   }
   class Main {
       public static void main(String[] args) {
           House.Room r = new House.Room();
           r.show();
       }
   }

C. class House {
       static class Blueprint {
           void show() { System.out.println("Blueprint"); }
       }
   }
   class Main {
       public static void main(String[] args) {
           House.Blueprint b = new House.Blueprint();
           b.show();
       }
   }

D. class House {
       String address = "Tokyo";
       static class Blueprint {
           void show() { System.out.println(address); }
       }
   }

E. class House {
       static class Blueprint {
           void show() { System.out.println("Blueprint"); }
       }
   }
   class Main {
       public static void main(String[] args) {
           House h = new House();
           House.Blueprint b = h.new Blueprint();
           b.show();
       }
   }
```

**正解：A, C**

- A：非staticの内部クラスは`outer.new Inner()`で正しく生成
- B：非staticの内部クラスを外側のインスタンス無しで`new House.Room()`と直接生成しようとしてエラー（「囲うインスタンスが必要です」）
- C：staticなネストクラスは`new Outer.Nested()`で直接生成できる（外側のインスタンス不要）
- D：staticなネストクラスから、外側の非staticフィールド（`address`）には直接アクセスできない
- E：staticなネストクラスに対して`outer.new Nested()`という（非static専用の）構文を使おうとしてエラー

**まとめ**
| | 非staticの内部クラス | staticのネストクラス |
|---|---|---|
| 生成方法 | `outer.new Inner()` | `new Outer.Nested()` |
| 外側の非staticフィールド | アクセスできる | アクセスできない |
| 外側インスタンスが先に必要か | 必要 | 不要 |

---

### 問題9（Outer.this と super の違い）

```
A. class Bank {
       String name = "MUFG";
       class Account {
           String name = "Savings";
           void show() {
               System.out.println(Bank.this.name);
           }
       }
   }
   class Main {
       public static void main(String[] args) {
           Bank b = new Bank();
           Bank.Account a = b.new Account();
           a.show();
       }
   }

B. class Bank {
       String name = "MUFG";
       class Account {
           void show() {
               System.out.println(super.name);
           }
       }
   }

C. class Bank {
       static class Branch {
           String name = "Shibuya";
           void show() { System.out.println(name); }
       }
   }
   class Main {
       public static void main(String[] args) {
           Bank.Branch br = new Bank.Branch();
           br.show();
       }
   }

D. class Bank {
       String name = "MUFG";
       static class Branch {
           void show() { System.out.println(Bank.this.name); }
       }
   }

E. class Bank {
       class Account {
           class Card {
               void show() { System.out.println(Bank.this); }
           }
       }
   }
   class Main {
       public static void main(String[] args) {
           Bank b = new Bank();
           Bank.Account a = b.new Account();
           Bank.Account.Card c = a.new Card();
           c.show();
       }
   }
```

**正解：A, C, E**（3つとも正しい。出題時に「2つ選択」としたのは設計ミス）

- A：`Bank.this.name`で、内部クラス自身の`name`ではなく外側の`Bank`インスタンスの`name`を明示的に指定できる
- B：`super`は継承（`extends`）の親クラスを指す構文であり、内部クラスと外側インスタンスの関係とは無関係。`Object`に`name`は無いのでエラー
- C：staticなネストクラス自身のフィールドに普通にアクセスしているだけなのでOK
- D：`Branch`はstaticなので、外側のインスタンスと紐付いておらず、`Bank.this`という概念自体が存在しない
- E：`Bank.this`だけ（`.フィールド名`を付けない）で、外側の`Bank`インスタンスそのもの（オブジェクトへの参照）を指す。`println`に渡すと`toString()`が呼ばれる

**重要な区別**
- `super` → 継承関係（is-a）の親クラスを指す
- `Outer.this` → 内部クラスが持つ、外側のインスタンスへの参照を指す（全く別の仕組み）
