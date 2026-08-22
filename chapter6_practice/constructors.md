# 6章 コンストラクタ連鎖 × フィールド初期化タイミング 問題集

## 前提知識メモ

- フィールド初期化のタイミングは「**`super()`が完全に終わったあと、コンストラクタ本体の前**」。親のコンストラクタからオーバーライド可能メソッドを呼ぶと、子のフィールドが未初期化(デフォルト値)の状態でそのメソッドが動いてしまう
- 「コンストラクタからオーバーライド可能メソッドを呼ぶな」は、直接呼んでいるかは無関係。呼び出しチェーンのどこかに`private`/`static`/`final`でないメソッドが紛れていれば同じ危険がある
- インスタンス初期化ブロック（`{ }`、staticなし）はインスタンス生成のたびに実行される。フィールド初期化子とインスタンス初期化ブロックは、**書かれている順番通りに上から交互に実行される**
- `this(...)`/`super(...)`は普通のメソッド呼び出しと同じ：呼ぶ→相手の処理が全部終わる→呼んだ場所の続きに戻ってくる。「最後に実行される」のではなく「呼ばれた瞬間に割り込む」
- `this(...)`委譲時、フィールド初期化・インスタンス初期化ブロックは**最終的にsuper()を呼ぶことになるコンストラクタでだけ1回**実行される（委譲元・委譲先で二重実行はされない）
- `extends`を書いていないクラスも、必ず`java.lang.Object`を暗黙的に継承している。だからどんなクラスのコンストラクタにも、明示か暗黙かを問わず`this(...)`か`super(...)`のどちらかが1行目にある
- `this.field = param`は、そのオブジェクトのフィールドそのものへの永続的な代入。委譲元に戻ってきてもリセットされない

---

## 問題1：コンストラクタからオーバーライド可能メソッドを呼ぶ危険性

```java
class A {
    A() {
        System.out.println("A()");
        init();
    }
    void init() {
        System.out.println("A.init");
    }
}

class B extends A {
    int value = 10;

    B() {
        System.out.println("B() value=" + value);
    }

    void init() {
        System.out.println("B.init value=" + value);
    }
}

public class Main {
    public static void main(String[] args) {
        new B();
    }
}
```

`new B();`の出力を順番通りに答えてください。

---

## 問題2：this()委譲とフィールド初期化・インスタンス初期化ブロックの順序

```java
class A {
    int x = init("field x");
    { System.out.println("instance initializer block"); }

    A() {
        this(100);
        System.out.println("A()");
    }

    A(int v) {
        System.out.println("A(int) v=" + v);
    }

    static int init(String label) {
        System.out.println(label);
        return 1;
    }
}

public class Main {
    public static void main(String[] args) {
        new A();
    }
}
```

`new A();`の出力を順番通りに答えてください。

---

## 問題3：this()の2段階委譲

```java
class A {
    { System.out.println("block1"); }
    int y = mark("field y");
    { System.out.println("block2"); }

    A() {
        this(1);
        System.out.println("A()");
    }

    A(int v) {
        this("go");
        System.out.println("A(int) v=" + v);
    }

    A(String s) {
        System.out.println("A(String) s=" + s);
    }

    static int mark(String label) {
        System.out.println(label);
        return 1;
    }
}

public class Main {
    public static void main(String[] args) {
        new A();
    }
}
```

`new A();`の出力を順番通りに答えてください。

---

## 問題4：this.field = param パターン（選択式）

```java
class Product {
    int price = 10;
    { System.out.println("block: price=" + price); }

    Product() {
        this(50);
        System.out.println("Product() price=" + price);
    }

    Product(int price) {
        this.price = price;
        System.out.println("Product(int) price=" + price);
    }
}

public class Main {
    public static void main(String[] args) {
        new Product();
    }
}
```

`new Product();`の出力として正しいものを、A〜Dから1つ選んでください。

**A.**
```
block: price=10
Product(int) price=50
Product() price=50
```

**B.**
```
block: price=0
Product(int) price=50
Product() price=50
```

**C.**
```
block: price=10
Product(int) price=50
Product() price=10
```

**D.**
```
block: price=0
Product(int) price=50
Product() price=0
```

---

## 問題5：フィールド・インスタンス初期化ブロックが4つ交互に並ぶパターン

```java
class Item {
    String name = tag("name field");
    { System.out.println("init block A"); }
    int price = tag2("price field", 100);
    { System.out.println("init block B"); }

    Item() {
        this("no name");
        System.out.println("Item()");
    }

    Item(String n) {
        System.out.println("Item(String) n=" + n);
    }

    static String tag(String label) {
        System.out.println(label);
        return "x";
    }
    static int tag2(String label, int v) {
        System.out.println(label);
        return v;
    }
}

public class Main {
    public static void main(String[] args) {
        new Item();
    }
}
```

`new Item();`の出力を順番通りに答えてください。

---

## 解答

**問題1**
```
A()
B.init value=0
B() value=10
```
`super.getX()`ならぬ`init()`の動的束縛でB.initが呼ばれるが、Bのフィールド初期化はまだ行われていないためvalueは0のまま。

**問題2**
```
field x
instance initializer block
A(int) v=100
A()
```

**問題3**
```
block1
field y
block2
A(String) s=go
A(int) v=1
A()
```

**問題4**：A

**問題5**
```
name field
init block A
price field
init block B
Item(String) n=no name
Item()
```

すべてjavac/javaで実機検証済み。
