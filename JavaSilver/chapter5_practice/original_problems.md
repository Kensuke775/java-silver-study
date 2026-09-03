## 目次

**問題一覧**

- [原本5-1](#q5-1)
- [原本5-2](#q5-2)
- [原本5-3](#q5-3)
- [原本5-4](#q5-4)
- [原本5-5](#q5-5)
- [原本5-6](#q5-6)
- [原本5-7](#q5-7)
- [原本5-8](#q5-8)
- [原本5-9](#q5-9)
- [原本5-10](#q5-10)
- [原本5-11](#q5-11)
- [原本5-12](#q5-12)
- [原本5-13](#q5-13)
- [原本5-14](#q5-14)
- [原本5-15](#q5-15)
- [原本5-16](#q5-16)
- [原本5-17](#q5-17)
- [原本5-18](#q5-18)

<a id="q5-1"></a>
## 原本5-1

```java
public class Test {
    int num1 = 0;
    var num2 = 10;
    private String num3;
    protected String[] array;
    public static final long value;
}
```

メンバ変数の宣言として、コンパイルが成功するものはいくつありますか。（1つ選択）

A. すべて
B. 4つ
C. 3つ
D. 2つ
E. 1つ

**実施記録**

回答：B
正解：C
迷ったポイント：varがメンバ変数に使えないことと、static final変数の初期化必須ルールの両方を見落とした

<a id="q5-2"></a>
## 原本5-2

```java
public class Product {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

このクラスのインスタンス化を行う記述はどれですか。（2つ選択）

A. `new Product() = null;`
B. `Product p = new Product();`
C. `Product p; p = new Product();`
D. `Product p, p = new Product();`
E. `Product p = new Product.setName("Test");`

**実施記録**

回答：B, C
正解：B, C
迷ったポイント：なし

<a id="q5-3"></a>
## 原本5-3

メソッド宣言として適切な記述はどれですか。（2つ選択）

A. `static public methodA() {}`
B. `public protected String[] methodB(int id) {}`
C. `void methodC(int id, String id) {}`
D. `void methodD() {}`
E. `private String methodE(int id) { System.out.println(id); }`
F. `public static String methodF(int i, String s) { return "methodF"; }`

**実施記録**

回答：D, F
正解：D, F
迷ったポイント：なし

<a id="q5-4"></a>
## 原本5-4

```java
package a;
public class Foo {
    /* x */ int val;
    /* y */ int getVal() { return val; }
    /* z */ void setVal(int val) {this.val = val; }
}
```

```java
package b;
import a.Foo;
public class Main {
    public static void main(String[] args) {
        Foo obj = new Foo();
        obj.setVal(3);
        System.out.println(obj.getVal());
    }
}
```

Fooクラスを適切にカプセル化し、プログラムが正常に動作するための修正はどれですか。（1つ選択）

A. 3行目のxをpublicにし、4行目のyと5行目のzをprivateにする
B. 3行目のxをprivateにし、4行目のyと5行目のzをpublicにする
C. 3行目のxをprivateにする
D. 4行目のyと5行目のzをprotectedにする
E. 何も変更する必要はない

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q5-5"></a>
## 原本5-5

カプセル化のメリットとして正しい説明はどれですか。（2つ選択）

A. オブジェクトのデータを安全に保持できる
B. オブジェクトのライフサイクルを管理できる
C. オブジェクトを生成しなくてもメンバにアクセスできる
D. オブジェクトの生成と初期化を効率よく行うことができる
E. オブジェクトのデータの整合性を保つことができる

**実施記録**

回答：A, E
正解：A, E
迷ったポイント：なし

<a id="q5-6"></a>
## 原本5-6

```java
public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        double v = obj.x(10);
    }
    // insert code here
}
```

6行目に定義し、コンパイルが成功するメソッドはどれですか。（1つ選択）

A. `public float x(int a) { return a * 0.5F; }`
B. `public void x(int b) { return 100.0; }`
C. `public double x(int...c) { double x = c[0]; }`
D. `public void x(int x, double y) { double z = x + y; }`
E. `public double x() { return 10.0; }`

**実施記録**

回答：A
正解：A
迷ったポイント：なし

<a id="q5-7"></a>
## 原本5-7

```java
public class Item {
    private int id;
    private String name;
    public Item() {
        this(2, "Book");
    }
    public Item(int id, String name) {
        id = id;
        name = name;
    }
    public void display() {
        System.out.print(id + ":" + name + " ");
    }
    public static void main(String[] args) {
        new Item(1, "Apple").display();
        new Item().display();
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `1:Apple 2:Book` が出力される
B. `0:null 2:Book` が出力される
C. `0:null 0:null` が出力される
D. `1:Apple 0:null` が出力される
E. `1:null 2:null` が出力される

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q5-8"></a>
## 原本5-8

```java
public class Test {
    String text = "A";
    void method() {
        text = "B";
    }
    void method(String t) {
        String text = "C";
        System.out.print(text);
    }
    public static void main(String[] args) {
        String text = "D";
        Test t = new Test();
        t.method("E");
        System.out.print(t.text);
        t.method();
        System.out.print(text);
        System.out.print(t.text);
    }
}
```

次のプログラムをコンパイル、実行すると、どのような結果になりますか。（1つ選択）

A. `ABDA` が出力される
B. `CADB` が出力される
C. `DECA` が出力される
D. `EADA` が出力される
E. 当てはまるものはない

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q5-9"></a>
## 原本5-9

```java
class Sample1 { Sample1() {} }
class Sample2 {}
class Sample3 { void Sample3(int i) {} }
class Sample4 { Sample4(String s, int i) {} }
```

コンパイルするとデフォルトコンストラクタが生成されるクラスはどれですか。（1つ選択）

A. Sample1
B. Sample2
C. Sample3
D. Sample4
E. Sample2とSample3
F. Sample2とSample4

**実施記録**

回答：E
正解：E
迷ったポイント：なし

<a id="q5-10"></a>
## 原本5-10

```java
public class Employee {
    int id;
    String name;
    public void Employee(int id) {
        this.id = id;
    }
    public Employee(String name, int id) {
        this.name = name;
        this(id);
    }
    public void showEmployeeInfo() {
        System.out.println(id + ":" + name);
    }
}
class Main {
    public static void main(String[] args) {
        Employee emp = new Employee();
        emp.showEmployeeInfo();
    }
}
```

コンパイルが成功し、`0:Duke` と出力するために必要な修正はどれですか。（3つ選択）

A. 2、3行目にprivateを付与する
B. 4行目のvoidを取る
C. 7行目のString nameとint idの定義順を入れ替える
D. 8、9行目の処理順を入れ替える
E. 17行目を `Employee emp = new Employee("Duke");` にする
F. 17行目を `Employee emp = new Employee("Duke", 0);` にする

**実施記録**

回答：C, D, F
正解：B, D, F
迷ったポイント：4行目の`void`が付いたままだと`Employee(int id)`はコンストラクタでなく普通のメソッド扱いになり、`this(id)`の呼び出し先が見つからずコンパイルエラーになる点を見落とした（Cの「引数の定義順入れ替え」では解決しない）

<a id="q5-11"></a>
## 原本5-11

```java
public class Test {
    public void foo(int x) {}
    // insert code here
}
```

3行目に定義し、コンパイルが成功するメソッドはどれですか。（4つ選択）

A. `void foo(Integer... x) {}`
B. `public void bar(int x) {}`
C. `boolean foo(int x, int y) { return false; }`
D. `public boolean bar(var x, var y) { return false; }`
E. `public int foo(String s, int x) {return s.length() + x; }`
F. `public String foo(int i) { return String.valueOf(i); }`

**実施記録**

回答：A, B, C, E
正解：A, B, C, E
迷ったポイント：なし

<a id="q5-12"></a>
## 原本5-12

```java
class Test {
    int x; int y;
    Test(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void print() {
        System.out.print(x + ":" + y);
    }
    public static void main(String[] args) {
        Test t = new Test(6, 9);
        System.out.print(x + ":" + y);
    }
}
```

単独で修正し、`6:9` の出力となるものはどれですか。（2つ選択）

A. 3行目のコンストラクタをpublicにする
B. 12行目を `System.out.print(this.x + ":" + this.y);` にする
C. 2行目を `static int x; static int y;` にする
D. 12行目の出力を `t.print();` の呼び出しに変更する
E. 10行目のstaticを取る

**実施記録**

回答：C, D
正解：C, D
迷ったポイント：なし

<a id="q5-13"></a>
## 原本5-13

```java
public class Sample {
    public Sample() {
        this(1);
        System.out.print(0);
    }
    public Sample(int x) {
        this(x, 2);
        this();
        System.out.print(x);
    }
    public Sample(int x, int y) {
        System.out.print(x + "" + y);
    }
    public static void main(String[] args) {
        new Sample(3);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `1210` が出力される
B. `323` が出力される
C. `01332` が出力される
D. プログラムが終了しなくなる
E. コンパイルエラーが発生する

**実施記録**

回答：E
正解：E
迷ったポイント：なし

<a id="q5-14"></a>
## 原本5-14

```java
public class Test {
    public int foo(int x, int y) {
        return x * y;
    }
    public int foo(int... x) {
        int z = 0;
        for (int y : x) { z += y; }
        return z;
    }
    public void bar(Integer i) {
        System.out.print(i);
    }
    public void bar(float i) {
        System.out.print(i);
    }
    public void bar(String... s) {
        System.out.println(s.length);
    }
    public static void main(String... args) {
        Test t = new Test();
        System.out.print(t.foo(10, 2));
        t.bar(7);
        t.bar();
    }
}
```

次のプログラムをコンパイル、実行すると、どのような結果になりますか。（1つ選択）

A. `207.00` が出力される
B. `127.00` が出力される
C. `2070` が出力される
D. `1270` が出力される
E. `ArrayIndexOutOfBoundsException`がスローされる
F. コンパイルエラーが発生する

**実施記録**

回答：C
正解：A
迷ったポイント：`t.bar(7)`のオーバーロード解決を`bar(Integer)`（boxing）だと思ったが、実際は`bar(float)`が選ばれる。intからfloatへの暗黙の拡大変換（widening）はboxingより優先度が高いため

<a id="q5-15"></a>
## 原本5-15

```java
public class Main {
    public static void main(Sample s) {
        s.val++;
        System.out.print(s.val);
    }
    public static int main(int i) {
        return i * i;
    }
    public static void main(String[] args) {
        Sample s = new Sample();
        main(s);
        int num = main(10);
        System.out.print(s.val + num);
    }
}
class Sample { int val = 10; }
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `10` が出力される
B. `11` が出力される
C. `100` が出力される
D. `11100` が出力される
E. `11111` が出力される

**実施記録**

回答：D
正解：E
迷ったポイント：`main`は3つとも通常のメソッドとしてオーバーロード可能で、JVMのエントリポイントは`main(String[])`だけという点を踏まえて処理を追えていなかった

<a id="q5-16"></a>
## 原本5-16

```java
public class Counter {
    static int count;
    public void Counter(int count) { this.count = count; }
    public void reset() { count = 0; }
    public static void update(int c) { count += c; }
    public static void main(String[] args) {
        Counter c1 = new Counter();
        c1.update(3);
        Counter.reset();
        count--;
        new Counter().count++;
        Counter c2 = new Counter();
        c2.update(1);
        System.out.println(Counter.count);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `1` が出力される
B. `-1` が出力される
C. `2` が出力される
D. 9行目でコンパイルエラーが発生する
E. 11行目でコンパイルエラーが発生する

**実施記録**

回答：A
正解：D
迷ったポイント：9行目`Counter.reset()`が原因。`reset()`はstaticでないインスタンスメソッドなので、クラス名から呼び出すとコンパイルエラーになる点を見落とした

<a id="q5-17"></a>
## 原本5-17

```java
public class Main {
    public static void main(String[] args) {
        Fruit[] fruits = { new Fruit("Lemon"), new Fruit("Kiwi"), new Fruit("Lime")};
        method(fruits);
        fruits[1] = null;
        for (var f : fruits)
            if(f != null) System.out.print(f.name + " ");
    }
    public static void method(Fruit[] x) {
        for (var f : x)
            if(f.name.length() == 5) f = null;
    }
}
class Fruit {
    String name;
    Fruit(String name) { this.name = name; }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Lime` が出力される
B. `Kiwi` が出力される
C. `Lemon Lime` が出力される
D. `Kiwi Lime` が出力される
E. `NullPointerException`がスローされる

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q5-18"></a>
## 原本5-18

```java
public class Main {
    public static void main(String[] args) {
        Item obj1 = new Item();
        Item obj2 = new Item();
        method(obj1);
        obj1 = new Item();
        obj2 = null;            // here
    }
    public static void method(Item obj) {
        obj = new Item();
    }
}
class Item {}
```

7行目の処理が終了したタイミングで、ガベージコレクタの対象となるオブジェクトはいくつありますか。（1つ選択）

A. 1つ
B. 2つ
C. 3つ
D. 4つ
E. 対象になるものはない

**実施記録**

回答：C
正解：C
迷ったポイント：なし
