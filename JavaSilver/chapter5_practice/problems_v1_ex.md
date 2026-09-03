## 目次

**問題一覧**

- [問題ex1-1](#qex1-1)
- [問題ex1-2](#qex1-2)
- [問題ex2-1](#qex2-1)
- [問題ex2-2](#qex2-2)
- [問題ex4-1](#qex4-1)
- [問題ex4-2](#qex4-2)
- [問題ex6-1](#qex6-1)
- [問題ex6-2](#qex6-2)
- [問題ex7-1](#qex7-1)
- [問題ex7-2](#qex7-2)
- [問題ex8-1](#qex8-1)
- [問題ex8-2](#qex8-2)
- [問題ex9-1](#qex9-1)
- [問題ex9-2](#qex9-2)
- [問題ex10-1](#qex10-1)
- [問題ex10-2](#qex10-2)
- [問題ex11-1](#qex11-1)
- [問題ex11-2](#qex11-2)
- [問題ex12-1](#qex12-1)
- [問題ex12-2](#qex12-2)
- [問題ex13-1](#qex13-1)
- [問題ex13-2](#qex13-2)
- [問題ex14-1](#qex14-1)
- [問題ex14-2](#qex14-2)
- [問題ex15-1](#qex15-1)
- [問題ex15-2](#qex15-2)
- [問題ex16-1](#qex16-1)
- [問題ex16-2](#qex16-2)
- [問題ex17-1](#qex17-1)
- [問題ex17-2](#qex17-2)
- [問題ex18-1](#qex18-1)
- [問題ex18-2](#qex18-2)
- [問題ex18-3](#qex18-3)
- [問題ex18-4](#qex18-4)



<a id="qex1-1"></a>
## 問題ex1-1

```java
public class Test {
    int num1 = 0;
    var num2 = 10;
    private String num3;
    protected String[] array;
    public static final long value;
}
```

次のクラス定義を見て、コンパイルエラーとなる箇所は(javacが一度に報告する数ではなく、それぞれの行が単独でも規則違反になるかどうかで)独立して何箇所あるか。

A. 0箇所(このクラスは正常にコンパイルできる)

B. 1箇所

C. 2箇所

D. 3箇所

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：C
迷ったポイント：うっかり(varのフィールド宣言エラーだけでなく、初期化されていないstatic finalフィールドも独立した規則違反であることを見落とした)

---



<a id="qex1-2"></a>
## 問題ex1-2

```java
public class Test {
    int num1 = 0;
    int num2 = 10;
    private String num3;
    protected String[] array;
    public static final long value;

    static {
        value = 100;
    }

    public static void main(String[] args) {
        var num2 = 20;
        System.out.println(num1 + num2 + value);
    }
}
```

この場合、コンパイル結果はどうなるか。

A. 正常にコンパイルでき、実行できる

B. コンパイルエラーになる(原因: num1へのアクセス)

C. コンパイルエラーになる(原因: ローカル変数num2がフィールドnum2と同名のため)

D. コンパイルエラーになる(原因: valueの初期化方法)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---



<a id="qex2-1"></a>
## 問題ex2-1

`sample/chap5/ex2/Product.java`は元々フィールド+getter/setterのみでmainがないため、`Main`クラスを追加した拡張版です。

```java
class Product {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public void setName(Object name) { this.name = "OBJ:" + name; }
}
public class Main {
    public static void main(String[] args) {
        Product p = new Product();
        p.setName("Widget");
        p.setName(null);
        System.out.println(p.getName());
    }
}
```

実行結果はどれか。

A. Widget

B. OBJ:null

C. null

D. コンパイルエラー(setNameの参照はあいまいです)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

---



<a id="qex2-2"></a>
## 問題ex2-2

```java
class Product {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public void setName(StringBuilder name) { this.name = name.toString(); }
}
public class Main {
    public static void main(String[] args) {
        Product p = new Product();
        p.setName(null);
        System.out.println(p.getName());
    }
}
```

実行結果はどれか。

A. null

B. コンパイルエラー(setNameの参照はあいまいです)

C. NullPointerExceptionが発生する

D. 何も出力されない(空文字)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：問題ex2-1(StringはObjectのサブタイプなのでnullでも解決可能)との対比で、String/StringBuilderは無関係な型なのでnullがあいまいになる点を誤解していた

(javac確認済みエラーメッセージ: 「setNameの参照はあいまいです / Productのメソッド setName(String)とProductのメソッド setName(StringBuilder)の両方が一致します」)

---



<a id="qex4-1"></a>
## 問題ex4-1

`sample/chap5/ex4`はpackage a(Foo)とpackage b(Main)の2ファイル構成です。以下はFooのアクセス修飾子をprotectedに変更した改変版です。

`a/Foo.java`(パッケージ`a`):

```java
package a;
public class Foo {
    protected int val;
    protected int getVal() { return val; }
    protected void setVal(int val) { this.val = val; }
}
```

`b/Main.java`(パッケージ`b`、`Foo`を継承):

```java
package b;
import a.Foo;
public class Main extends Foo {
    void test(Foo other) {
        this.val = 10;
        other.val = 20;
        System.out.println(this.val + ", " + other.val);
    }
    public static void main(String[] args) {
        Main m = new Main();
        m.test(new Foo());
    }
}
```

コンパイル結果として正しいものはどれか。

A. 正常にコンパイルされ、実行結果は "10, 20" と出力される

B. 6行目と7行目の両方でコンパイルエラーになる(いずれも "valはFooでprotectedアクセスされます")

C. 6行目のみコンパイルエラーになる

D. 5行目でコンパイルエラーになる(自身のフィールドにthisでアクセスしているため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：this.valはOKだがother.val(Foo型変数経由)はNGという、protectedの「サブクラス自身の型を通じたアクセスのみ許可」という制限を見落としていた

(javac確認済み: b/Main.javaの6行目・7行目で「valはFooでprotectedアクセスされます」エラー、5行目はエラーなし)



疑問

プロテクテッドを返しても、インスタンスはその他のクラスのインスタンスを使うことができないってことですかね。

->その理解はほぼ合っています。ただ正確には「protectedな継承メンバーは、`this`(または自分自身の型として扱えるsubtypeの参照)経由でのみアクセスできる」というルールです。subclassのメソッド内であっても、別のインスタンスをsuperclass型の変数として受け取った引数(例: `other.val`、`other`の宣言型がsuperclass)からはアクセスできず、「valはFooでprotectedアクセスされます」という趣旨のコンパイルエラーになります。一方`this.val`(自分自身への参照)は同じsubclassメソッド内で問題なくアクセスできます。「他のクラスのインスタンスを使えない」というより、「アクセスする側の参照の型がsubtypeでなければならない」という制約です。

疑問

パブリックであっても無理なのかな、それって。

->publicにはこの制約はありません。publicメンバーはパッケージをまたいでいても、どんな型の参照経由でアクセスしても問題なくアクセスできます。上記の制約はprotectedをパッケージをまたいで継承した場合に限られる特有のルールです。

---



<a id="qex4-2"></a>
## 問題ex4-2

`a/Foo.java`(パッケージ`a`、アクセス修飾子なし=デフォルトアクセス):

```java
package a;
public class Foo {
    int val;
    int getVal() { return val; }
    void setVal(int val) { this.val = val; }
}
```

`b/Bar.java`(パッケージ`b`、`Foo`を継承):

```java
package b;
import a.Foo;
public class Bar extends Foo {
    void test() {
        setVal(5);
        System.out.println(getVal());
    }
    public static void main(String[] args) {
        new Bar().test();
    }
}
```

コンパイル結果として正しいものはどれか。

A. 正常にコンパイルされ、実行結果は5と出力される

B. 5行目・6行目ともに「publicではありません」というコンパイルエラーになる

C. 5行目のみコンパイルエラーになる(6行目は問題ない)

D. 5行目・6行目ともに「シンボルを見つけられません」というコンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし(一発正解)

(javac確認済み: b/Bar.javaの5行目・6行目で「シンボルを見つけられません」エラー。問題ex4-1のprotectedと違い、デフォルトアクセスは他パッケージからは継承していても不可視扱いになるため、エラーの種類自体が異なる点がポイント)

---



<a id="qex6-1"></a>
## 問題ex6-1

```java
public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        double v = obj.x(10);
    }
    // insert code here ← ここにA〜Dのいずれか1つだけを挿入
}
```

A. `int x(short a) { return a; }`

B. `int x(int... a) { return a[0]; }`

C. `String x(int a) { return String.valueOf(a); }`

D. `int x(Integer a) { return a; }`

コンパイルエラーになるものをすべて選べ。

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A, C
迷ったポイント：戻り値の型がdoubleに暗黙変換できるかを見落とした(Stringは代入不可)

---



<a id="qex6-2"></a>
## 問題ex6-2

```java
public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        double v = obj.x(10);
        System.out.println(v);
    }
    static int x(long a) { return (int)(a * 2); }
    int x(int a) { return a * 3; }
}
```

実行結果はどうなるか。

A. `30.0`

B. `20.0`

C. オーバーロードが曖昧でコンパイルエラーになる

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---



<a id="qex7-1"></a>
## 問題ex7-1

```java
public class Item {
    private int id;
    private String name;
    public Item() {
        this(2, "Book");
    }
    public Item(int id, String name) {
        this.id = id;
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

実行結果はどれか。

A. 1:null 2:null

B. 1:Apple 2:Book

C. 0:null 0:null

D. 1:Apple 2:null

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし(一発正解)

(javac/java確認済み: 出力「1:null 2:null 」。this.id=idは正しく代入されるがname=nameはthis.なしの自己代入バグが残る点がポイント)

---



<a id="qex7-2"></a>
## 問題ex7-2

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
    public Item(Item other) {
        this.id = other.id;
        this.name = other.name;
    }
    public void display() {
        System.out.print(id + ":" + name + " ");
    }
    public static void main(String[] args) {
        Item original = new Item(5, "Pen");
        Item copy = new Item(original);
        original.display();
        copy.display();
    }
}
```

実行結果はどれか。

A. 5:Pen 5:Pen

B. 5:Pen 0:null

C. 0:null 0:null

D. コンパイルエラー(コンストラクタのオーバーロードがあいまいです)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

(javac/java確認済み: 出力「0:null 0:null」。コピーコンストラクタ自体はthis.付きで正しく書かれているが、コピー元originalが元のバグ付きコンストラクタ(id=id; name=name;)で生成されているため既にid:0,name:nullであり、その壊れた値がそのまま正確にコピーされる点がポイント)

---



<a id="qex8-1"></a>
## 問題ex8-1

```java
public class Test {
    String text = "A";
    void method() {
        text = "B";
    }
    void method(String t) {
        text = t;
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

実行結果はどれか。

A. `CADB`

B. `EEDB`

C. `EADB`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：回答の記入ミス(method(String t)内のtextはローカル宣言がなくフィールドを指す、という理解自体は正しかった)

---



<a id="qex8-2"></a>
## 問題ex8-2

```java
public class Test {
    String text = "A";
    void method(String text) {
        this.text = text;
        text = "C";
        System.out.print(text);
        System.out.print(this.text);
    }
    public static void main(String[] args) {
        Test t = new Test();
        t.method("E");
        System.out.print(t.text);
    }
}
```

実行結果はどれか。

A. `CCC`

B. `CEE`

C. `EEE`

D. コンパイルエラーになる(仮引数とフィールドの名前が同じため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---



<a id="qex9-1"></a>
## 問題ex9-1

```java
class Sample3 {
    void Sample3(int i) {}
}
public class Main {
    public static void main(String[] args) {
        Sample3 s1 = new Sample3();
        Sample3 s2 = new Sample3(5);
    }
}
```

コンパイル結果として正しいものはどれか。

A. 正常にコンパイルされる

B. 6行目でコンパイルエラーになる(Sample3()が存在しないため)

C. 6行目・7行目の両方でコンパイルエラーになる

D. 7行目のみコンパイルエラーになる(Sample3(int)というコンストラクタは存在しないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし(一発正解)

(javac確認済みエラー: 7行目「クラス Sample3のコンストラクタ Sample3は指定された型に適用できません」。void Sample3(int i){}は戻り値の型を持つため通常のメソッドでありコンストラクタではない点がポイント。6行目はエラーなし)

---



<a id="qex9-2"></a>
## 問題ex9-2

```java
class Sample4 {
    Sample4(String s, int i) {}
}
public class Main {
    public static void main(String[] args) {
        Sample4 obj = new Sample4();
    }
}
```

コンパイル結果として正しいものはどれか。

A. 6行目でコンパイルエラーになる(Sample4()というコンストラクタは存在しないため)

B. 正常にコンパイルされる

C. コンパイルは通るが実行時にエラーになる

D. Sample4クラス自体の定義でコンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし(一発正解)

(javac確認済みエラー: 6行目「クラス Sample4のコンストラクタ Sample4は指定された型に適用できません」。明示的にコンストラクタを定義すると暗黙のデフォルトコンストラクタが生成されない点がポイント。ex9-1との対比: 戻り値の型の有無でコンストラクタかどうかが決まる)

---



<a id="qex10-1"></a>
## 問題ex10-1

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

このコードのエラーの原因として正しい記述をすべて選べ。

A. `Employee(int id)`は戻り値の型が`void`のため、コンストラクタではなく通常のメソッドとして扱われる

B. `this(id);`が、コンストラクタ内の他の文より前(先頭)に記述されていないためエラーになる

C. 仮に`this(id);`を先頭に置いたとしても、引数`int`一つに対応するコンストラクタが存在しないためエラーになる

D. `new Employee()`は、`Employee(int id)`が実質的な引数なしコンストラクタとして機能するため正常に呼び出せる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A, B, C
正解：A, B, C
迷ったポイント：なし

(javac確認済み: 「thisの呼出しはコンストラクタの先頭文である必要があります」、「コンストラクタEmployeeは指定された型に適用できません(期待値: String,int / 検出値: int)」、「コンストラクタEmployeeは指定された型に適用できません(期待値: String,int / 検出値: 引数がありません)」の3エラーを再現。B・Cはエラーメッセージで直接裏付け、Aはthis(id)がEmployee(int)に解決されずEmployee(String,int)としか解決されない点から裏付け)



疑問

Cの「仮にthis(id);を先頭に置いたとしても、引数int一つに対応するコンストラクタが存在しないためエラーになる」というのが何を指しているか(mainの中のnew Employee()のことか、それともEmployee(int id)というvoidのメソッドのことか)分からない。

->Cが指しているのは`public void Employee(int id) { this.id = id; }`の方。`this(id);`は「同じクラスの、int一つを引数に取るコンストラクタを呼び出す」構文だが、`Employee(int id)`は戻り値の型`void`が付いているためコンストラクタではなく通常のメソッドとして扱われる(コンストラクタは戻り値の型を書いてはいけないというルールのため)。よってクラスに実在するコンストラクタは`Employee(String name, int id)`の1つだけで、`Employee(int)`という形のコンストラクタはそもそも存在せず、`this(id);`は行き先を見つけられずエラーになる。mainの`new Employee()`の話(引数なしコンストラクタ不在)とは別の指摘であり、Cとは無関係。

---



<a id="qex10-2"></a>
## 問題ex10-2

```java
public class Employee {
    int id;
    String name;
    public Employee(int id) {
        this.id = id;
        System.out.println("A:" + id);
    }
    public Employee(String name, int id) {
        this(id);
        this.name = name;
        this.id = id + 1;
        System.out.println("B:" + this.id);
    }
    public void showEmployeeInfo() {
        System.out.println(id + ":" + name);
    }
}
class Main {
    public static void main(String[] args) {
        Employee emp = new Employee("Yamada", 100);
        emp.showEmployeeInfo();
    }
}
```

実行結果はどれか。

A. `A:100` / `B:101` / `101:Yamada`

B. `A:100` / `B:100` / `100:Yamada`

C. `B:101` / `A:100` / `101:Yamada`

D. コンパイルエラーになる(`this(id);`がコンストラクタの先頭文になっていないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

(javac/java確認済み: 出力「A:100」「B:101」「101:Yamada」)

---



<a id="qex11-1"></a>
## 問題ex11-1

```java
public class Test {
    public void foo(int x) {}
    // insert code here ← ここにA〜Dのいずれか1つだけを挿入
}
```

A. `public int foo(int x) { return x; }`

B. `public void foo(int y) { }`

C. `private void foo(long x) { }`

D. `public void foo(int... x) { }`

コンパイルエラーになるものをすべて選べ。

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A, B
正解：A, B
迷ったポイント：なし

(javac確認済み: A「メソッドfoo(int)はすでに定義されています」、B「メソッドfoo(int)はすでに定義されています」でエラー、C・Dはコンパイル成功)

---



<a id="qex11-2"></a>
## 問題ex11-2

```java
public class Test {
    public void foo(int x, int... y) {}
    // insert code here ← ここにA〜Dのいずれか1つだけを挿入
}
```

A. `public void foo(int x, int[] y) { }`

B. `public void foo(int x, Integer... y) { }`

C. `public int foo(int x, int y) { return x; }`

D. `public void foo(long x, int... y) { }`

コンパイルエラーになるものをすべて選べ。

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

(javac確認済み: A「foo(int,int[])とfoo(int,int...)の両方を宣言することはできません」でエラー、B・C・Dはコンパイル成功)

---



<a id="qex12-1"></a>
## 問題ex12-1

```java
class Test {
    static int x; int y;
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

コンパイル結果として正しいものはどれか。

A. 正常にコンパイルされ、6:9と出力される

B. 12行目でエラーになる(yの参照で失敗する)

C. 12行目でエラーになる(xの参照で失敗する)

D. 12行目でエラーになる(xとy両方の参照で失敗する)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：B
迷ったポイント：xはstaticフィールドなのでstaticコンテキスト(main)から修飾子なしで参照してもエラーにならない点を見落とし、xとy両方がエラーになると誤解した

(javac確認済みエラー: 12行目「staticでない変数 yをstaticコンテキストから参照することはできません」。xの参照はエラーなし)

---



<a id="qex12-2"></a>
## 問題ex12-2

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
    static void staticPrint() {
        System.out.print(x + ":" + y);
    }
    public static void main(String[] args) {
        Test t = new Test(6, 9);
        t.print();
        t.staticPrint();
    }
}
```

コンパイル結果として正しいものはどれか。

A. 正常にコンパイルされ、6:9 6:9と出力される

B. staticPrint()はt(インスタンス)経由で呼ばれているため問題なくコンパイルされる

C. 11行目でエラーになる(xとy両方の参照が失敗する)

D. 11行目でエラーになる(xの参照のみ失敗する)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

(javac確認済みエラー: 11行目でxとy両方に「staticでない変数〜をstaticコンテキストから参照することはできません」エラー。t.staticPrint()とインスタンス経由で呼び出しても、メソッド自身の宣言がstaticである以上、本体はstaticコンテキスト扱いになる点がポイント。ex12-1との対比: 修飾子なしアクセスの可否はフィールド/メソッド自身がstaticかどうかで決まり、呼び出し方法には依存しない)

---



<a id="qex13-1"></a>
## 問題ex13-1

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

実行結果はどれか。

A. `3212120`のような文字列が出力される

B. コンパイルエラーになる(`this();`がコンストラクタの先頭文になっていないため)

C. 実行時に`StackOverflowError`が発生する

D. コンパイルエラーになる(引数`(int, int)`に対応するコンストラクタが存在しないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(一発正解)

(javac確認済みエラー: 「thisの呼出しはコンストラクタの先頭文である必要があります」。`this(x, 2);`の後に`this();`を置くと、後者が先頭文ではなくなるためエラーになる点がポイント)

---



<a id="qex13-2"></a>
## 問題ex13-2

```java
public class Test {
    public void func() {
        func(1);
        System.out.print(0);
    }
    public void func(int x) {
        func(x, 2);
        func();
        System.out.print(x);
    }
    public void func(int x, int y) {
        System.out.print(x + "" + y);
    }
    public static void main(String[] args) {
        new Test().func(3);
    }
}
```

同じ構造を、コンストラクタの`this()`呼び出しではなく普通のメソッドの呼び出しに置き換えた。実行結果はどれか。

A. `3212120`が出力される

B. コンパイルエラーになる

C. `StackOverflowError`が発生するまで再帰的に呼び出しが続く

D. `321`が出力されて正常終了する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

(javac/java確認済み: コンパイルは通り、実行すると`func()`→`func(1)`→`func(x,2)`出力後に`func()`が再度呼ばれ無限再帰、`StackOverflowError`で終了。ex13-1との対比: コンストラクタの`this()`は「先頭文・1回のみ」という制約があるためコンパイル時に弾かれるが、普通のメソッド呼び出しにはその制約がなく実行時エラーになる)

---



<a id="qex14-1"></a>
## 問題ex14-1

```java
public class Ex14a {
    public int foo(int x, int y) {
        return x * y;
    }
    public int foo(int... x) {
        int z = 0;
        for (int y : x) { z += y; }
        return z;
    }
    public void bar(Integer... i) {
        System.out.println("Integer varargs");
    }
    public void bar(long... i) {
        System.out.println("long varargs");
    }
    public static void main(String... args) {
        Ex14a t = new Ex14a();
        System.out.print(t.foo(10, 2));
        t.bar(7);
    }
}
```

このコードの結果はどれか。

A. 20を出力した後、long varargsが呼び出される

B. 20を出力した後、Integer varargsが呼び出される

C. 19行目でコンパイルエラーになる(barの参照があいまい)

D. 18行目でコンパイルエラーになる(fooの参照があいまい)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：C
迷ったポイント：bar(Integer...)とbar(long...)はどちらもフェーズ3(ボックス化+可変長引数)でしか適用できず、IntegerとlongはJavaのオーバーロード決定基準上どちらも「より特定的」とは判定されないためあいまいエラーになる

(javac確認済みエラー: 19行目「barの参照はあいまいです / bar(Integer...)とbar(long...)の両方が一致します」。foo(10,2)はfoo(int,int)にフェーズ1で確定するためあいまいにならず、Dは誤り)



疑問

そうなったんですか。インテージャーとロングって優劣関係ないんですね。優劣関係表でちょっと教えてくれませんか。

->オーバーロード解決は3フェーズ(フェーズ1: widening/厳密一致のみ、フェーズ2: ボクシングあり、フェーズ3: 可変長引数あり)で行われ、優劣比較は同じフェーズ内でのみ発生します。プリミティブのwidening連鎖(byte→short→int→long→float→double)は一本道の全順序なので、同じ連鎖上にある型同士(例: long vs float)は必ず優劣がつきます。しかしIntegerとLongのような無関係なラッパー型同士には変換経路も継承関係もないため、同じフェーズで並ぶと優劣がつかず、あいまいエラーになります。これがex14-1(`Integer...` vs `long...`)とex2(`String` vs `StringBuilder`へのnull)の共通点です。

疑問

そのさ、粒度の問題って、JavaSilver出てくるかな?

->「オーバーロード解決の優先順位」と「無関係な参照型同士でのあいまいエラー」という基本概念自体はSilverの出題範囲内ですが、ex14-1のような「可変長引数同士(Integer... vs long...)」という組み合わせでのあいまいは、かなり深掘りした応用パターンです。本試験がこの具体的な組み合わせをそのまま出すかは未確認です。個人の見立てとしては、本試験はもう少しシンプルな1〜2パターン(null引数のあいまい、boxing vs widening の優先順位)に絞られることが多い印象です。

---



<a id="qex14-2"></a>
## 問題ex14-2

```java
public class Ex14b {
    public void bar(long i) {
        System.out.println("long:" + i);
    }
    public void bar(Long i) {
        System.out.println("Long:" + i);
    }
    public void bar(Object... o) {
        System.out.println("Object varargs:" + o.length);
    }
    public static void main(String... args) {
        Ex14b t = new Ex14b();
        t.bar(7);
    }
}
```

このコードの結果はどれか。

A. long:7 と出力される

B. Long:7 と出力される

C. Object varargs:1 と出力される

D. 13行目でコンパイルエラーになる(barの参照があいまい)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし(bar(long)はフェーズ1(ボックス化・可変長引数なしのwidening)だけで適用可能なため、そこで解決が確定し、bar(Long)やbar(Object...)とは比較対象にすらならずあいまいにならない。ex14-1との対比がポイント)

(javac/java確認済み: 出力「long:7」)

---



<a id="qex15-1"></a>
## 問題ex15-1

```java
public class Main {
    public static void main(Sample s) {
        s.val++;
        System.out.print(s.val);
    }
    public static int main(int i) {
        return i * i;
    }
    static void main(String[] args) {
        Sample s = new Sample();
        main(s);
        int num = main(10);
        System.out.print(s.val + num);
    }
}
class Sample { int val = 10; }
```

元のコードから、`String[] args`を受け取るmainメソッドの`public`修飾子だけを外した(他のオーバーロードmainはそのまま)。`javac Main.java`および`java Main`の結果として正しいものはどれか。

A. コンパイルエラーになる(エントリポイントのmainには`public`が必須なため)

B. コンパイルは通るが、`java Main`実行時に「メイン・メソッドが見つかりません」というエラーで起動できない

C. 問題なくコンパイル・実行でき、`11111`が出力される

D. コンパイルは通るが、`java Main`実行時に`NoSuchMethodError`がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(一発正解)

(javac/java確認済み: コンパイルは通るが`java Main`実行時に「エラー: メイン・メソッドがクラスMainで見つかりません。次のようにメイン・メソッドを定義してください: public static void main(String[] args)」で起動失敗。エントリポイントの`public`欠如はコンパイルエラーではなく、JVM起動時のエラーになる点がポイント)



疑問

スタティックメソッドの呼び出し方法がかなり曖昧なんですよね。独立してるから、このメインの10ってやったとしても、アクセス方法間違ってるんじゃないかなって思ったんですよね。

->その理解は逆です。**静的(static)メソッドはインスタンスなしで呼び出せるのが特徴**であり、「インスタンスからしか呼び出せない」のは*非静的(インスタンス)メソッド*の方です。

- 静的メソッド: `クラス名.メソッド名(引数)` で呼べる。さらに**同じクラス内から呼ぶ場合はクラス名すら省略でき、ただの**`main(s)`**のようなベタ書きで呼べる**(暗黙的に`Main.main(s)`として解決される)。
- インスタンスメソッド: 呼び出すには必ずレシーバ(`obj.method()`のobj部分)が要る。静的メソッドをインスタンス経由(`obj.staticMethod()`)で呼ぶことも文法上は許可されているが、推奨されない書き方というだけ。

このコードの`main(s)`・`main(10)`は、呼び出し元の`main(String... args)`自体が静的メソッドで、**同じ**`Main`**クラス内の静的メソッド同士の呼び出し**なので、クラス名修飾なしのベタ呼び出しで完全に正当です。オーバーロード解決も、引数の型(`Sample`か`int`か)で一意に決まるため曖昧さもありません。「アクセス方法を間違えているのでは」という懸念は当たらず、javac/javaで検証済みの通り問題なくコンパイル・実行できます。





---



<a id="qex15-2"></a>
## 問題ex15-2

```java
public class Main {
    public static void main(Sample s) {
        s.val++;
        System.out.print(s.val);
    }
    public static int main(int i) {
        return i * i;
    }
    public static void main(String... args) {
        Sample s = new Sample();
        main(s);
        int num = main(10);
        System.out.print(s.val + num);
    }
}
class Sample { int val = 10; }
```

今度は`public`は残したまま、引数を`String[] args`から可変長引数`String... args`に変えた。`java Main`の結果として正しいものはどれか。

A. コンパイルエラーになる(オーバーロードされた`main(int i)`と曖昧になるため)

B. コンパイルは通るが、可変長引数はエントリポイントとして認識されず起動できない

C. 問題なくエントリポイントとして認識され、`11111`が出力される

D. `main(Sample s)`が優先的に呼び出され、`main(String... args)`は実行されない

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

(javac/java確認済み: 出力「11111」。`String... args`はバイトコード上`String[]`に消去(erasure)されるため、`public static void main(String[])`と同一シグネチャとしてJVMのエントリポイントに認識される点がポイント。ex15-1との対比: `public`の有無は起動可否に直結するが、`[]`と`...`の書き方の違いは起動可否に影響しない)

---



<a id="qex16-1"></a>
## 問題ex16-1

```java
public class Counter {
    static int count;
    public void Counter(int count) { this.count = count; }
    public void reset() { count = 0; }
    public static void update(int c) { count += c; }
    public static void main(String[] args) {
        Counter c1 = new Counter(5);
        c1.reset();
        c1.update(3);
        System.out.println(Counter.count);
    }
}
```

このコードの結果はどれか。

A. 7行目でコンパイルエラーになる(Counterに該当するコンストラクタが存在しない)

B. 5が出力される

C. 8が出力される

D. 3が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし(一発正解)

(javac確認済み: 7行目「クラス Counterのコンストラクタ Counterは指定された型に適用できません。期待値: 引数がありません / 検出値: int」。`public void Counter(int count)`はクラス名と同名だが戻り値void付きのため、あくまで通常メソッドでコンストラクタとして扱われない点がポイント)



疑問

その問題の、形式的になんか3つ数字選択するっていうのと、1つコンパイルエラーだから、多分数字について聞かれてる可能性が高いじゃないですか、そうなると。

->選択肢の形(数字3つ+コンパイルエラー1つ)から正解を推測するのは危険です。実際ex16-1はその逆を証明していて、「数字が多数派だから正解っぽい」という思い込みで解くと外れる設計になっています。良く作られた問題(本試験含む)は、わざと「もっともらしい数字」を複数並べて実際はコンパイルエラーになるという引っかけを仕込みますし、逆に「コンパイルエラー」の選択肢を3つ並べて実際は正常に動く、という逆パターンも普通にあります。選択肢の内訳は出題者が難易度調整のために自由に決めているだけで、正解の分布とは無関係です。「暗黙のデフォルトコンストラクタは引数を受け取れない」のような具体的なルールに立ち返って判断するのが正しいアプローチです。

---



<a id="qex16-2"></a>
## 問題ex16-2

```java
public class Counter {
    static int count = 10;
    public Counter() {}
    public void reset() { count = 0; }
    public static void update(int c) { count += c; }
    public static void main(String[] args) {
        Counter c1 = new Counter();
        c1.update(5);
        c1.reset();
        new Counter().count++;
        Counter c2 = new Counter();
        c2.update(count);
        System.out.println(Counter.count);
    }
}
```

このコードの結果はどれか。

A. 0

B. 2

C. 1

D. 15

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(一発正解)

(javac/java確認済み: 出力「2」。count: 10→c1.update(5)で15→c1.reset()で0→new Counter().count++(匿名インスタンス経由の静的フィールドインクリメント、合法)で1→c2.update(count)は引数countが呼び出し前に評価され1、そこに+1されて2)

---



<a id="qex17-1"></a>
## 問題ex17-1

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
            if(f.name.length() == 5) f.name = "REMOVED";
    }
}
class Fruit {
    String name;
    Fruit(String name) { this.name = name; }
}
```

このコードの結果はどれか。

A. Lemon Lime 

B. Kiwi Lime 

C. コンパイルエラーになる

D. REMOVED Lime 

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：D
迷ったポイント：拡張for文はループ変数に「参照値のコピー」を代入するだけで配列自体はコピーしない。`f = null`(参照先の差し替え)は元の配列に影響しないが、`f.name = "REMOVED"`(参照先オブジェクトのフィールド書き換え)は同じオブジェクトを指しているため元の配列側からも変化が見える。この2つを混同して誤答(不安+チェック)

(javac/java確認済み: 出力「REMOVED Lime 」)

疑問

拡張構文は配列のコピーだったと思うんだけど。配列を引数として渡すと、iとして書き換えると、確か元の配列も書き換えられるようなインデックス指定だと気がしました。

->拡張for文がコピーするのは「配列」ではなく「各要素の参照値」です。ループ変数fは配列の要素と同じオブジェクトを指す参照のコピーであり、`f = null`のようにfという変数自体を差し替えても元の配列には影響しませんが、`f.name = "REMOVED"`のようにfが指す先のオブジェクトの中身を書き換えると、同じオブジェクトを指している配列側からもその変化が見えます。「fを差し替える」と「fの指す先の中身をいじる」は別物、という点がポイントです。索引for文の`x[i] = ...`が元の配列に影響する、という理解はex17-2の通り正しいです。

疑問

なるほど。参照だから、参照をいじって中身を変えるのと差し替えるのは全く別の話ってことですね。だから参照さえあれば代入は可能だから、1はまかり通るってことですね。

->その理解で合っています。より正確には、`f.name = "REMOVED"`(参照が指す先のオブジェクトのフィールドへの代入)はオブジェクトが1つしか存在せず、`f`もfruits配列の要素もその同じオブジェクトを指しているため、どこから代入してもオブジェクトの中身は変わり全員に見えます。一方`f = null`(参照変数f自体への代入)はfというラベルの向き先を変えるだけでオブジェクト自体には何も起きません。「参照さえあれば代入できる」というより「代入先がオブジェクトの中身なのか変数自体なのか」で影響範囲が変わる、という区別が正確です。

疑問

変数自体であれば、そのコピーした変数であるから、そのローカル変数が先に来るから、そのローカル変数を書き換えることになって、結局元の配列は書き換わらないっていうような意味合いですよね。

->その理解で正確です。`f = null`は、fという**ローカル変数**(=配列の要素が持っていた参照値をコピーしただけの別物)を書き換えているにすぎません。書き換わるのはfというラベルの向き先だけで、配列の要素(fruits[0]という「箱」)自体は一切触られていないので、元の配列は変わりません。

---



<a id="qex17-2"></a>
## 問題ex17-2

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
        for (int i = 0; i < x.length; i++)
            if(x[i].name.length() == 5) x[i] = null;
    }
}
class Fruit {
    String name;
    Fruit(String name) { this.name = name; }
}
```

このコードの結果はどれか。

A. Lime 

B. Lemon Lime 

C. Kiwi Lime 

D. 何も出力されない

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし(一発正解)

(javac/java確認済み: 出力「Lime 」。索引for文の`x[i] = null`は引数で渡された配列そのものの要素を直接書き換えるため、呼び出し元の`fruits`にも反映される)

---



<a id="qex18-1"></a>
## 問題ex18-1

```java
public class Main {
    public static void main(String[] args) {
        Item obj1 = new Item();
        Item obj2 = new Item();
        Item[] arr = new Item[1];
        arr[0] = obj1;
        obj1 = null;
        method(obj2);
        obj2 = new Item();
        arr = null;
        // ここまで実行した時点
    }
    public static void method(Item obj) {
        obj = new Item();
    }
}
class Item {}
```

この時点(コメント行)で、GCの対象になっているItem型のインスタンスはいくつか。

A. 1個

B. 2個

C. 3個

D. 4個

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：C
迷ったポイント：配列要素経由の間接参照(arr[0]=obj1)がある間はAがGC対象にならず、arr自体がnullになった時点で連鎖的に対象になる、という間接参照のタイミングを数え間違えた

---



<a id="qex18-2"></a>
## 問題ex18-2

```java
class Node {
    Node next;
}
public class Main {
    public static void main(String[] args) {
        Node n1 = new Node();
        Node n2 = new Node();
        n1.next = n2;
        n2.next = n1;
        n1 = null;
        n2 = null;
        // ここまで実行した時点
    }
}
```

この時点(コメント行)で、GCの対象になっているNode型のインスタンスはいくつか。

A. 0個(お互いを参照し合っているのでGC対象にならない)

B. 1個

C. 2個

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---



<a id="qex18-3"></a>
## 問題ex18-3

```java
public class Main {
    static Item keep;
    public static void main(String[] args) {
        Item obj1 = new Item();
        Item obj2 = new Item();
        keep = obj1;
        obj1 = null;
        obj2 = null;
        // ここまで実行した時点
    }
}
class Item {}
```

この時点(コメント行)で、GCの対象になっているItem型のインスタンスはいくつか。

A. 0個

B. 1個(obj2が指していたインスタンスのみ)

C. 1個(obj1が指していたインスタンスのみ、staticフィールドkeepは無関係)

D. 2個

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(一発正解)

(javac/java確認済み: コンパイル・実行とも問題なし。staticフィールドkeepがobj1の指すインスタンスを保持し続けるため、obj1をnullにしてもそのインスタンスはGC対象にならない点がポイント。obj2の指すインスタンスは他に参照がなくGC対象になる)

---



<a id="qex18-4"></a>
## 問題ex18-4

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Item obj = new Item();
        }
        // ループを抜けた直後、ここまで実行した時点
    }
}
class Item {}
```

この時点(コメント行)で、GCの対象になっているItem型のインスタンスはいくつか。

A. 0個(ループ内で使われている間はGC対象にならないため、ループを抜けた後もobjが最後の値を保持している)

B. 1個(最後のイテレーションで作られた1つだけが対象)

C. 3個(ループの各イテレーションで生成された3つのインスタンスすべてが、ループを抜けた時点で参照を失いGC対象になる)

D. コンパイルエラーになる(ループ内でobjを再宣言しているため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし(一発正解)

(javac/java確認済み: コンパイル・実行とも問題なし。ローカル変数objはforループの各イテレーションのブロックスコープで再生成され、次のイテレーションに移る/ループを抜ける時点でそのイテレーションのobjへの参照は失われるため、3回のnew Item()すべてが最終的にGC対象になる点がポイント)

---

