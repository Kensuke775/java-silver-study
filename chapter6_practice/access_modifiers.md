# 6章 アクセス修飾子 × パッケージ境界 問題集

## 前提知識メモ

- `protected`の別パッケージ・サブクラス経由アクセスは、**アクセスに使う式の型**が、アクセスしている側のクラス自身かそのサブタイプでなければ不可。親クラス型・無関係な兄弟クラス型を経由するとNG
- package-privateメソッドは、**別パッケージを挟んだ継承リンクだけ**が「無関係な別メソッド」化する。継承リンク1本ごとに判定され、他の健全なリンクには影響しない。一度切れたリンクの先で別リンクが繋がっていても、元の枠（vtableスロット）には戻らない
- `protected`以上（`protected`・`public`）なら、子孫である限り何階層・何パッケージ跨いでもオーバーライドの連鎖が保たれる。これはpackage-private特有の弱点であり、`protected`以上では起きない
- 親のメソッドが子から見えない（private/別パッケージのpackage-private）→同名でもエラーなしの無関係な別メソッド。**見える（protected以上）→必ずオーバーライドの試みとして扱われ、アクセスを狭めるとコンパイルエラー**
- アクセス修飾子の広さの順序：`private < 無印(default) < protected < public`。オーバーライドは元以上の広さでなければならない

---

## 問題1：protectedの別パッケージ・サブクラス経由アクセス

```java
// p1/Animal.java
package p1;
public class Animal {
    protected String sound = "Some sound";
}
// p1/Cat.java
package p1;
public class Cat extends Animal {}

// p2/DogA.java
package p2;
public class DogA extends Animal {
    void bark() { System.out.println(this.sound); }
}
// p2/DogB.java
package p2;
public class DogB extends Animal {
    void bark(DogB other) { System.out.println(other.sound); }
}
// p2/DogC.java
package p2;
public class DogC extends Animal {
    void bark(Animal other) { System.out.println(other.sound); }
}
// p2/DogD.java
package p2;
public class DogD extends Animal {
    void bark(Cat other) { System.out.println(other.sound); }
}
```

A〜Dのうち、**コンパイルが成功するものをすべて**選んでください。

---

## 問題2：package-privateメソッドと別パッケージ継承（2階層）

```java
// p1/Base.java
package p1;
public class Base {
    void greet() {
        System.out.println("Base.greet");
    }
    public void callGreet() {
        greet();
    }
}
// p2/Sub.java（Baseとは別パッケージ）
package p2;
public class Sub extends Base {
    void greet() {
        System.out.println("Sub.greet");
    }
}
// p2/Main.java
package p2;
public class Main {
    public static void main(String[] args) {
        Base b = new Sub();
        b.greet();       // ①コンパイルできる？
        b.callGreet();    // ②コンパイルできるとして、出力は？
        Sub s = new Sub();
        s.greet();        // ③出力は？
    }
}
```

①〜③を答えてください。また、もし`Base`と`Sub`が**同一パッケージ**だった場合、②の出力はどう変わるか答えてください。

---

## 問題3：package-privateの3階層継承チェーン（中間で境界）

```java
// p1/A.java
package p1;
public class A {
    void foo() { System.out.println("A.foo"); }
    public void run() { foo(); }
}
// p1/B.java（Aと同じパッケージ）
package p1;
public class B extends A {
    void foo() { System.out.println("B.foo"); }
}
// p2/C.java（Bとは別パッケージ）
package p2;
public class C extends B {
    void foo() { System.out.println("C.foo"); }
}
// p2/Main.java
package p2;
public class Main {
    public static void main(String[] args) {
        A a = new C();
        a.run();      // ①
        C c = new C();
        c.foo();      // ②
    }
}
```

①②の出力を答えてください。

---

## 問題4：package-privateの3階層継承チェーン（先頭で境界）

```java
// p1/A.java
package p1;
public class A {
    void foo() { System.out.println("A.foo"); }
    public void run() { foo(); }
}
// p2/B.java（Aとは別パッケージ）
package p2;
public class B extends A {
    void foo() { System.out.println("B.foo"); }
}
// p2/C.java（Bと同じパッケージ）
package p2;
public class C extends B {
    void foo() { System.out.println("C.foo"); }
}
// p2/Main.java
package p2;
public class Main {
    public static void main(String[] args) {
        A a = new C();
        a.run();     // ①
        B b = new C();
        b.foo();     // ②
        C c = new C();
        c.foo();     // ③
    }
}
```

①②③の出力を答えてください。

---

## 問題5：protectedなら何階層・何パッケージ跨いでも繋がるか

```java
// p1: Animal
package p1;
public class Animal {
    protected void speak() { System.out.println("Animal.speak"); }
}
// p2: Dog extends Animal（別パッケージ、speak()は一切オーバーライドせず、ただ継承するだけ）
package p2;
public class Dog extends Animal {}
// p3: Puppy extends Dog（Animalからもp2のDogからも別パッケージ。ここでspeak()をオーバーライド）
package p3;
public class Puppy extends Dog {
    protected void speak() { System.out.println("Puppy.speak"); }
}
// p3/Main.java
package p3;
public class Main {
    public static void main(String[] args) {
        Animal a = new Puppy();
        a.announce(); // ← Animalにannounce()があり、内部でspeak()を呼ぶと仮定して出力を答えよ
    }
}
```

`Dog`が何もオーバーライドしていないのに、`Puppy`のオーバーライドは有効になるか？ 出力を答えてください。

---

## 問題6：オーバーライドでアクセス範囲を狭めるとどうなるか

問題5と同じ構成で、`Puppy`側だけ`speak()`を`private`にすると、コンパイルは通るか？ 通らない場合、理由も答えてください。

```java
private void speak() { System.out.println("Puppy.speak"); }
```

---

## 解答

**問題1**：A, B（C, Dは親クラス型・兄弟クラス型を経由しているためNG）

**問題2**：①コンパイル不可（package-privateは別パッケージから不可視）／②`Base.greet`（`Sub.greet()`はオーバーライドとして成立しないため静的解決）／③`Sub.greet`／同一パッケージなら②は`Sub.greet`になる（動的束縛が効く）

**問題3**：①`B.foo`（A→Bは同一パッケージで健全、B→Cは別パッケージで切れる。実体がCでも、この枠を最後に正式に上書きしているのはB）／②`C.foo`

**問題4**：①`A.foo`（A→Bが別パッケージで切れる。B→Cが同一パッケージで繋がっていても、Aの枠とは無関係な別系統）／②`C.foo`／③`C.foo`

**問題5**：`a.announce()` → `Puppy.speak`。`protected`は直接の子だけでなく、何階層先の子孫にも及ぶため、Dogが何もしていなくてもPuppyのオーバーライドは有効

**問題6**：コンパイルエラーになる。「Puppyのspeak()はAnimalのspeak()をオーバーライドできません（protectedより弱いアクセス権限を割り当てようとしました）」。親のメソッドが子から見える（protected）場合、同名メソッドは必ずオーバーライドの試みとして扱われ、アクセスを狭めることは許されない

すべてjavac/javaで実機検証済み。
