# chapter6 2周目 オリジナル問題(sample/chap6/N形式)

## 問題2-1

```java
class Item {
    private int id = 100;
    public void display() {
        System.out.println("Item id: " + id);
    }
}
class Clothes extends Item {
    private int id = 200;
    private String brand = "Java";
    @Override
    public void display() {
        super.display();
        System.out.println("Clothes id: " + id);
        System.out.println("Clothes brand: " + brand);
    }
}
class Main {
    public static void main(String[] args) {
        Item it = new Clothes();
        it.display();
    }
}
```

このプログラムをコンパイル、実行するとどうなりますか。

A. `Item id: 100` → `Clothes id: 200` → `Clothes brand: Java` の順で出力される
B. `Item id: 200` → `Clothes id: 200` → `Clothes brand: Java` の順で出力される
C. コンパイルエラーが発生する
D. `Item id: 100` → `Item id: 200` → `Clothes brand: Java` の順で出力される

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。

## 問題2-2

```java
1: class Item {
2:     private int id = 100;
3:     public void display() {
4:         System.out.println("Item id: " + id);
5:     }
6: }
7: class Clothes extends Item {
8:     private String brand = "Java";
9:     @Override
10:    public void display() {
11:        display();
12:        System.out.println("Clothes brand: " + brand);
13:    }
14: }
15: class Main {
16:    public static void main(String[] args) {
17:        Clothes c = new Clothes();
18:        c.display();
19:    }
20: }
```

このプログラムをコンパイル、実行するとどうなりますか。

A. `Item id: 100` → `Clothes brand: Java` の順で出力される
B. `StackOverflowError`が発生する
C. コンパイルエラーが発生する
D. `Clothes brand: Java`のみ出力される

### 実施記録

回答：A
正解：B
迷ったポイント：11行目の`display();`が`super.display()`と同じ意味だと思い込み、親クラスに処理が委譲されると誤解した。実際には裸の`display();`は`this.display();`と同じで、動的ディスパッチにより常にオーバーライドした`Clothes.display()`自身を呼ぶため無限再帰になる。`super.`を明示しない限り親クラスには委譲されないという点を見落とした。

疑問

初めて聞いたんだけど、そのスタックオーバーフローの、どういうことですか、それって。流れ説明してほしいです。

->メソッドを1回呼ぶたびに呼び出しスタックに作業スペース(スタックフレーム)が積まれる。11行目の`display();`は`this.display();`と同じ意味で、動的ディスパッチにより常にオーバーライドされたClothes.display()自身を呼ぶ。終了条件が無いため、display()→display()→display()...とreturnせずに呼び出しだけが積み上がり続け、スタック領域の上限に達するとJVMがStackOverflowErrorを投げて強制終了する。対してsuper.display()は親クラスの版を直接呼ぶため、そこで1回で完結してreturnし、無限再帰にならない。

疑問

自分で自分を呼んでるって、このディスプレイがこれと一緒ってことですよね。これ見抜く方法、どうやって見抜けばいいですか？

->その通り、11行目のdisplay();はthis.display();の省略形で、外側のメソッドと同じインスタンスに対して同じメソッドをもう一度呼んでいるので自己再帰。見抜く手順は3ステップ: (1)呼び出しに付いている修飾語を見る(super.なら親クラス直行で再帰にならない、インスタンス変数.〜ならそのインスタンス、無修飾なら暗黙のthis.〜)。(2)その呼び出し先の名前が外側のメソッド自身の名前と一致するか比べる。(3)一致していたら、if文などで再帰を打ち切る終了条件があるか探す。無ければ無限再帰(StackOverflowError)、あれば意図的な正常な再帰処理。

疑問

Clothies.display();のようにクラス名経由で呼んだ場合どうなりますか？

->クラス名.メソッド名()という呼び出し方は、そのメソッドがstaticの場合にのみ許される。display()はstaticが付いていないインスタンスメソッドなので、クラス名経由では呼び出せずコンパイルエラーになる(javacで検証済み: 「staticでないメソッド display()をstaticコンテキストから参照することはできません」)。つまりこの書き方に変えると、無限再帰の実行時エラーではなく、そもそもコンパイルが通らないという全く別の結果になる。

疑問

あー、スーパーって書かなきゃいけなかったのか。

->その通り。親クラスの版を明示的に呼びたい場合はsuper.display();と書く必要がある。裸のdisplay()(=this.display())は常に動的ディスパッチで自分自身(オーバーライドした版)に戻ってくるため、親に処理を委譲したいときはsuper.を省略できない。

## 問題6-1

```java
class A {
    private int x = A.init("A field", 1);
    { System.out.println("A block"); }
    A() { System.out.println("A ctor"); }
    static int init(String tag, int v) { System.out.println(tag); return v; }
}
class B extends A {
    private int y = A.init("B field", 2);
    { System.out.println("B block"); }
    B() { System.out.println("B ctor"); }
}
class C extends B {
    private int z = A.init("C field", 3);
    { System.out.println("C block"); }
    C() { System.out.println("C ctor"); }
}
class Main {
    public static void main(String[] args) { new C(); }
}
```

`new C();`を実行すると、出力はどの順序になりますか。

A. `A ctor` → `B ctor` → `C ctor` → `A field` → `B field` → `C field` → `A block` → `B block` → `C block`
B. `A field` → `A block` → `A ctor` → `B field` → `B block` → `B ctor` → `C field` → `C block` → `C ctor`
C. `C field` → `C block` → `B field` → `B block` → `A field` → `A block` → `A ctor` → `B ctor` → `C ctor`
D. `A ctor` → `A field` → `A block` → `B ctor` → `B field` → `B block` → `C ctor` → `C field` → `C block`

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。

## 問題6-2

```java
class A {
    A() { System.out.println("class A"); }
}
class B extends A {
    B(int n) { System.out.println("class B " + n); }
}
class C extends B {
    C() { System.out.println("class C"); }
}
class Main {
    public static void main(String[] args) { new C(); }
}
```

このプログラムをコンパイルするとどうなりますか。

A. `class A` → `class B 0` → `class C` の順で出力される
B. `class A` → `class C` の順で出力される(`B`のコンストラクタは呼ばれない)
C. コンパイルエラーが発生する
D. 実行時に例外がスローされる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。理由も「Bの引数なしコンストラクタが必要」という正しい方向で言語化できていた。厳密には「デフォルトコンストラクタが空だから」ではなく、`C()`内に暗黙で挿入される`super();`(引数なし)に対応する`B()`が存在しないことが原因。

## 問題3-1

```java
1: class Base {
2:     protected void greet(int x) {
3:         System.out.println("Base greet");
4:     }
5: }
6: class Derived extends Base {
7:     @Override
8:     void greet(int x) {
9:         System.out.println("Derived greet");
10:     }
11: }
12: public class Main {
13:     public static void main(String[] args) {
14:         Base b = new Derived();
15:         b.greet(1);
16:     }
17: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、実行すると"Derived greet"と出力される
B. コンパイルは成功するが、実行時に例外がスローされる
C. 8行目でコンパイルエラーになる
D. 14行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。デフォルト(パッケージプライベート)アクセスは`protected`より範囲が狭いため、オーバーライドでアクセス権限を弱めることになりコンパイルエラーになるという理由も正しく言語化できていた。

## 問題3-2

```java
1: class Vehicle {
2:     static String kind() {
3:         return "Vehicle";
4:     }
5:     String describe() {
6:         return "I am a " + kind();
7:     }
8: }
9: class Car extends Vehicle {
10:     static String kind() {
11:         return "Car";
12:     }
13: }
14: public class Main {
15:     public static void main(String[] args) {
16:         Vehicle v = new Car();
17:         System.out.println(v.describe());
18:         System.out.println(v.kind());
19:     }
20: }
```

このコードを実行した場合、17行目と18行目の出力はどうなるか。

A. I am a Vehicle / Vehicle
B. I am a Car / Car
C. I am a Car / Vehicle
D. I am a Vehicle / Car

### 実施記録

回答：D
正解：A
迷ったポイント：`kind()`が`static`であるため多態性(オーバーライド)が働かず、`v.kind()`も`describe()`内部の`kind()`呼び出しも、実行時の実体(`Car`)ではなく参照の宣言型/呼び出し元クラス(`Vehicle`)基準でコンパイル時に確定するという点を理解できていなかった。`Car`側の`kind()`再定義は「隠蔽(hiding)」であり「上書き(override)」ではないため、`Vehicle`型経由で呼ぶ限り常に`Vehicle`側が呼ばれる。

## 問題4-1

```java
class Super {
    int x = 10;
    void printX() {
        System.out.println("Super.printX x=" + x);
    }
}
class Sub extends Super {
    int x = 20;
    @Override
    void printX() {
        System.out.println("Sub.printX x=" + x);
    }
}
public class Main {
    public static void main(String[] args) {
        Super s = new Sub();
        System.out.println("s.x=" + s.x);
        s.printX();
        Sub sub = new Sub();
        System.out.println("sub.x=" + sub.x);
        System.out.println("((Super)sub).x=" + ((Super) sub).x);
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. s.x=20 / Sub.printX x=20 / sub.x=20 / ((Super)sub).x=20
B. s.x=10 / Sub.printX x=20 / sub.x=20 / ((Super)sub).x=10
C. s.x=10 / Super.printX x=10 / sub.x=20 / ((Super)sub).x=10
D. s.x=10 / Sub.printX x=20 / sub.x=20 / ((Super)sub).x=20

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。

疑問

((Super)sub)この書き方初めて見ました。

->明示的キャスト(アップキャスト)の構文`(型名)式`。`sub`(実体はSub型)をSuper型として扱うよう指示するもので、SubはSuperのサブクラスなので安全にコンパイルが通る。キャスト後は式全体の型がSuperになるため、フィールドアクセス`.x`は参照の型(Super)基準で解決されるフィールド隠蔽のルールにより`Super.x`(10)が返る。メソッド呼び出しの場合はキャストしても動的ディスパッチで実際のオブジェクトのオーバーライド済みメソッドが呼ばれる点が対照的。

疑問

Superっていう書き方はOKなんですね

->Javaの識別子は大文字・小文字を区別するため、クラス名`Super`(大文字S)と予約語`super`(全て小文字、親クラスを指すキーワード)は別物として扱われ、両方とも使える。ただし可読性の観点で紛らわしいため実務では避けるのが一般的(今回はサンプルコード用に使用)。予約語自体をクラス名にすること(`class super {}`)はコンパイルエラーになる。

## 問題4-2

```java
class Animal {
    String name = "Animal";
    static String category() {
        return "Generic";
    }
    void speak() {
        System.out.println(name + " makes a sound (" + category() + ")");
    }
}
class Dog extends Animal {
    String name = "Dog";
    static String category() {
        return "Canine";
    }
}
public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        a.speak();
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. Dog makes a sound (Canine)
B. Animal makes a sound (Canine)
C. Dog makes a sound (Generic)
D. Animal makes a sound (Generic)

### 実施記録

回答：D
正解：D
迷ったポイント：なし(一発正解)。

## 問題5-1

```java
1: class Parent {
2:     public final void methodA() { System.out.println("Parent A"); }
3:     public static final void methodB() { System.out.println("Parent B"); }
4: }
5: class Child extends Parent {
6:     @Override
7:     public void methodA() { System.out.println("Child A"); }
8: }
9: class Test {
10:    public static void main(String[] args) {
11:        new Child().methodA();
12:    }
13: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`Child A`と出力される
B. コンパイルは成功し、`Parent A`と出力される
C. 6行目でコンパイルエラーになる
D. 7行目でコンパイルエラーになる

### 実施記録

回答：D
正解：D
迷ったポイント：なし(一発正解)。

## 問題5-2

```java
1: class Parent {
2:     public final void methodA() { System.out.println("Parent A"); }
3:     public static final void methodB() { System.out.println("Parent B"); }
4: }
5: class Child extends Parent {
6:     public static void methodB() { System.out.println("Child B"); }
7: }
8: class Test {
9:     public static void main(String[] args) {
10:        Parent.methodB();
11:        Child.methodB();
12:    }
13: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`Parent B` → `Child B`の順で出力される
B. コンパイルは成功し、`Parent B` → `Parent B`の順で出力される(`Child.methodB()`は隠蔽されず親のまま呼ばれる)
C. 6行目でコンパイルエラーになる
D. 実行時に例外がスローされる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。「staticだから多態性が働かないだけで再定義自体は許される」という誤解に陥らず、finalが付いたstaticメソッドは隠蔽自体が禁止される点を正しく判断できていた。

## 問題7-1

```java
class Item {
    private int id;
    Item(int id) {
        id = id;
    }
    public void display() {
        System.out.println("Item id: " + id);
    }
}
class Clothes extends Item {
    private String brand;
    Clothes(int id, String brand) {
        super(id);
        this.brand = brand;
    }
    @Override
    public void display() {
        super.display();
        System.out.println("Clothes brand: " + brand);
    }
}
class Main {
    public static void main(String[] args) {
        Clothes c = new Clothes(100, "Duke's Clothes");
        c.display();
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. Item id: 0 / Clothes brand: Duke's Clothes
B. Item id: 100 / Clothes brand: Duke's Clothes
C. コンパイルエラーが発生する
D. Item id: 0 / Clothes brand: null

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。

## 問題7-2

```java
1: class Item {
2:     private int id;
3:     Item() {
4:         this(0);
5:         System.out.println("Item() no-arg");
6:     }
7:     Item(int id) {
8:         this.id = id;
9:         System.out.println("Item(int) id=" + id);
10:    }
11:    public void display() {
12:        System.out.println("Item id: " + id);
13:    }
14: }
15: class Clothes extends Item {
16:    private String brand;
17:    Clothes(String brand) {
18:        this.brand = brand;
19:        super();
20:        System.out.println("Clothes(String) brand=" + brand);
21:    }
22:    @Override
23:    public void display() {
24:        super.display();
25:        System.out.println("Clothes brand: " + brand);
26:    }
27: }
28: class Main {
29:    public static void main(String[] args) {
30:        Clothes c = new Clothes("Duke's Clothes");
31:        c.display();
32:    }
33: }
```

このプログラムをコンパイルするとどうなるか。

A. 3行目でコンパイルエラーになる(this(0);は先頭文でなければならない)
B. コンパイルは成功し、"Item(int) id=0"→"Item() no-arg"→"Clothes(String) brand=Duke's Clothes"の順に出力される
C. 19行目でコンパイルエラーになる(super();がコンストラクタの先頭文でない)
D. コンパイルは成功するが、実行時にスタックオーバーフローが発生する

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。

## 問題8-1

```java
record Item(int id, String name) {
    Item {
        if (id < 0) throw new IllegalArgumentException("negative id");
    }
}
class Main {
    public static void main(String[] args) {
        Item a = new Item(100, "T-shirt");
        Item b = new Item(100, "T-shirt");
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a.hashCode() == b.hashCode());
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. `true` / `true` / `true`
B. `false` / `false` / `false`
C. `false` / `true` / `true`
D. `true` / `false` / `true`

### 実施記録

回答：B
正解：C
迷ったポイント：「hashCodeはインスタンスごとの製品番号のようなもの」という直感で考え、別インスタンスなら異なる値になるはずと誤解した(`Object`のデフォルトのhashCode()についてはその直感は正しい)。しかしrecordは`equals()`/`hashCode()`を自動生成する際、全コンポーネント(id, name)の値だけを見て計算するよう再定義するため、同じ値を持つ別インスタンス同士は`equals()`がtrueになり、それに伴い`hashCode()`も一致する(equals/hashCodeコントラクト)。`a == b`(参照の同一性)と`a.equals(b)`(構造的な等価性)を混同していた。

疑問

ハッシュコードって一回何か教えてもらっていいですか？

->hashCode()はオブジェクトを整数値で代表させたもので、主にHashMap/HashSetなどハッシュベースのコレクションで要素を格納・検索するためのバケット番号を決めるのに使われる。Objectクラスのデフォルト実装はオブジェクトの同一性(メモリ上の位置)を元にした値で、実質インスタンスごとにほぼユニークな番号になる(「製品番号」のイメージはデフォルト実装に対しては正しい)。Javaには「equals()がtrueを返す2つのオブジェクトはhashCode()も同じ値を返さなければならない」という規約(コントラクト)があり、これに従わないとHashMap/HashSetが正しく動作しなくなる。recordはequals()を「全コンポーネントの値が一致するか」で自動生成するため、それと矛盾しないようhashCode()も全コンポーネントの値から計算するように自動生成される。つまり普通のクラスの「デフォルトの同一性ベース」から「値ベース」に作り変えられている点が違い。

## 今回のhashCodeの話に当てはめると

「一致しないこともあるかもしれない」という選択肢を選ぶべきかどうかは、**どちらの方向の話をしているか**で真逆になります。

- **「equalsがtrueなら、hashCodeも必ず一致する」** → これは**規約で保証されている**ので、「一致しないこともある」と書いてあったら**それは誤り**(こちらは断定的な選択肢が正解)
- **「中身が違うオブジェクト同士は、hashCodeも必ず違う」** → これは**保証されていない**(ハッシュ衝突の可能性)ので、「一致しないこともある」ではなく「**一致することもある(衝突がありうる)**」と書いてあれば、そちらが正解

つまり「一致しない可能性がある」という言い回しが正解になるのは**後者の方向の問いに限られ**、前者の方向で同じ言い回しが出てきたら逆に誤りの選択肢です。

## 問題8-2

```java
record Item(int id, String name) {
    Item {
        name = name.toUpperCase();
        System.out.println("in compact ctor, name() = " + name());
    }
}
class Main {
    public static void main(String[] args) {
        Item item = new Item(100, "shirt");
        System.out.println("item.name() = " + item.name());
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. `in compact ctor, name() = SHIRT` / `item.name() = SHIRT`
B. `in compact ctor, name() = null` / `item.name() = SHIRT`
C. `in compact ctor, name() = shirt` / `item.name() = SHIRT`
D. コンパイルエラーが発生する

### 実施記録

回答：A
正解：B
迷ったポイント：compact constructor内で`name = name.toUpperCase();`と書いた時点でフィールドも即座に更新されると誤解した。実際には`name = ...`はパラメータ変数自体への再代入であり、コンパイラが挿入する「パラメータの最終値をフィールドに自動代入する処理」はcompact constructorの本体が全部終わったあとに実行される。そのため本体の途中でアクセサ`name()`を呼ぶと、まだ自動代入前のフィールドのデフォルト値(参照型なので`null`)が返る。本体を抜けた後は正しく`SHIRT`になる。

メモ name呼び出しにすると大文字で出力される。

つまり同じ「`name`」という文字列でも、`()`が付くかどうかで「フィールドを読みに行く(アクセサ経由)」のか「今いるスコープのローカル変数をそのまま読む」のかが全く変わる、という点が今回の対比のポイントです。ローカル変数は代入した瞬間からずっとその値を持ち続けるので、フィールドの自動代入タイミングとは無関係に、書いた通りの値がそのまま見えます。

## 問題9-1

```java
1: record Clothes(int id, String brand) {
2:     Clothes {
3:         this.id = id > 0 ? id : 0;
4:     }
5: }
6: public class Main {
7:     public static void main(String[] args) {
8:         Clothes c = new Clothes(-5, "Duke");
9:         System.out.println(c);
10:    }
11: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`Clothes[id=-5, brand=Duke]`と出力される
B. コンパイルは成功し、`Clothes[id=0, brand=Duke]`と出力される
C. 8行目でコンパイルエラーになる
D. 3行目でコンパイルエラーになる

### 実施記録

回答：D
正解：D
迷ったポイント：なし(一発正解)。

## 問題9-2

```java
1: record Clothes(int id, String brand) {
2:     private int count;
3:     static int totalCreated = 0;
4:     Clothes {
5:         totalCreated++;
6:     }
7: }
8: public class Main {
9:     public static void main(String[] args) {
10:        Clothes c = new Clothes(1, "Duke");
11:        System.out.println(Clothes.totalCreated);
12:    }
13: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`1`と出力される
B. 2行目でコンパイルエラーになる(レコード内のインスタンスフィールドは許されず、staticのみ許される)
C. 3行目でコンパイルエラーになる(staticフィールドはレコード内で許されない)
D. 5行目でコンパイルエラーになる(コンパクトコンストラクタ内でstaticフィールドは変更できない)

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。

## 問題10-1

```java
1: public record Sample(int x, int y) {
2:     private double a = 100;
3:     private static double b = 200.0;
4:     public double calc() { return x + y; }
5:     public static double getB() { return b; }
6:     @Override public int x() { return x; }
7:     @Override
8:     public String toString() {
9:         return "Sample record... x:" + x + ", y:" + y;
10:    }
11: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 2行目でコンパイルエラーになる
C. 3行目でコンパイルエラーになる
D. 6行目でコンパイルエラーになる

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。

疑問

コード内って、確かスタティックしか宣言できなかったと思うんだけど、メソッドに関しては普通に宣言できるんでしたっけ？

->recordのbody内で禁止されているのは非staticのインスタンスフィールドだけ。インスタンスメソッド(calc()のようなstaticが付いていない普通のメソッド)は問題なく宣言できる。フィールドはインスタンスごとに独立した状態を持つため、コンポーネント以外に隠れた状態を追加できてしまいrecordの「不変な値の入れ物」という設計思想に反するのに対し、メソッドは状態を持たずコンポーネントの値(または自動生成されたアクセサ)を使って計算するだけなので制限する理由がない。



まとめると、recordのbody内で宣言できるもの/できないものは:

- **禁止**: 非staticのインスタンスフィールド(コンポーネント以外の隠れた状態を持たせられないため)
- **OK**: staticフィールド(`private static double b = 200.0;`のような、全インスタンス共有の状態はrecordの「値の入れ物」という設計思想に反しないため)
- **OK**: インスタンスメソッド(`calc()`のような、状態を持たず計算するだけのもの)
- **OK**: staticメソッド(`getB()`のような、インスタンスに紐づかないもの)

「禁止されているのは非staticのインスタンスフィールドだけ」というのがrecordの制約の本質で、メソッド(static/非static問わず)やstaticフィールドは通常のクラスと同じ感覚で自由に宣言できます。

## 問題10-2

```java
1: public record Sample(int x, int y) {
2:     private static double b = 200.0;
3:     public double calc() { return x + y; }
4:     public static double getB() { return b; }
5:     @Override public int x() { return x; }
6:     @Override double y(double z) { return 1.0; }
7:     @Override
8:     public String toString() {
9:         return "Sample record... x:" + x + ", y:" + y;
10:    }
11: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 5行目でコンパイルエラーになる
C. 6行目でコンパイルエラーになる
D. 9行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。

疑問

引数の型が違えば、確かオーバーライドにはならなかった気がするんだけど、これ。

->その通り。オーバーライドが成立する条件の一つに「シグネチャ(引数の型・数)が完全に一致すること」がある。y(double z)は引数を1つ取るが、オーバーライドしようとしている自動生成アクセサはy()(引数なし)。引数が違うためこれはオーバーライドではなく別の新しいメソッド(オーバーロード)扱いになる。オーバーロードなら@Overrideは使えないため、「そもそもオーバーライドしている親のメソッドが存在しない」という理由でコンパイルエラーになる。

## 問題11-1

```java
1: abstract class Shape {
2:     protected String name;
3:     Shape(String name) {
4:         this.name = name;
5:     }
6:     abstract double area();
7:     void printInfo() {
8:         System.out.println(name + ": area=" + area());
9:     }
10: }
11: class Circle extends Shape {
12:     private double radius;
13:     Circle(double radius) {
14:         super("Circle");
15:         this.radius = radius;
16:     }
17: }
18: public class Main {
19:     public static void main(String[] args) {
20:         Circle c = new Circle(2.0);
21:         c.printInfo();
22:     }
23: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. 11行目でコンパイルエラーになる
B. コンパイルは成功し、`Circle: area=0.0`と出力される
C. 13行目でコンパイルエラーになる
D. 20行目でコンパイルエラーになる

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。
解説：`Shape`は`area()`を抽象メソッドとして宣言しているため、抽象メソッドを1つでも持つクラスは`abstract`でなければならないというルールに従っている。そのサブクラスは「抽象メソッドをすべて実装して具象クラスになる」か「サブクラス自身も`abstract`と宣言する」かのどちらかを選ばなければならない。`Circle`は`area()`を一切オーバーライドしておらず、かつ`abstract`も付いていないため、どちらの条件も満たせず11行目(`Circle`のクラス宣言そのもの)でコンパイルエラーになる。コンストラクタや`main`側の処理自体は文法的に問題ないが、クラス定義が成立しないためそれより先には進めない。

## 問題11-2

```java
1: abstract class Greeter {
2:     protected abstract String greet();
3:     void sayHello() {
4:         System.out.println(greet() + ", World!");
5:     }
6: }
7: public class Main {
8:     public static void main(String[] args) {
9:         Greeter g = new Greeter() {
10:            String greet() {
11:                return "Hi";
12:            }
13:        };
14:        g.sayHello();
15:    }
16: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. 9行目でコンパイルエラーになる
B. コンパイルは成功し、`Hi, World!`と出力される
C. 10行目でコンパイルエラーになる
D. 14行目でコンパイルエラーになる

### 実施記録

回答：A
正解：C
迷ったポイント：匿名クラスでの`greet()`のオーバーライドも通常のクラス継承と同じ「アクセス修飾子を弱めてはいけない」ルールが適用されることを見落とし、9行目(匿名クラスの生成そのもの)でエラーになると考えた。
解説：`new Greeter() { ... }`という匿名クラス定義自体は正しい構文で問題ない。エラーの原因は10行目の`String greet()`(デフォルト/パッケージプライベート)が、親の`protected abstract String greet()`よりアクセス範囲を狭めていること。匿名クラスでのメソッド定義であっても、通常のクラス継承と同じ「オーバーライドでアクセス修飾子を弱めてはいけない」ルールが適用される(問題3-1と同じルールの別パターン)。

疑問

11の2で初めて知ったんですけど、アブストラクトってインスタンスになれるんですね。

->正確には抽象クラス自体は今もインスタンス化できない(`new Greeter();`単体は「Greeterはabstractです。インスタンスを生成することはできません」というコンパイルエラーになる。javacで検証済み)。`new Greeter() { ... }`は匿名クラス構文で、「Greeterをその場で継承した名前のない新しいサブクラスを定義し、そのサブクラスのインスタンスを作る」という「継承+インスタンス化」を1文で同時に行う特殊構文。中の`{ }`で未実装の抽象メソッドを実装しているからこそ、この無名のサブクラスは具象クラスとして成立しインスタンス化できる。abstractがインスタンスになれるのではなく、abstractを継承した無名の具象サブクラスのインスタンスができていて、変数の型としてabstractクラスが使われている、というのが正確な理解。

疑問

匿名構文ってあんまり教材とかで出てこなかったんだけど、これってどういうものなんですか？

->匿名クラスは「クラス定義」と「インスタンス化」を1つの式にまとめて書く構文。`親クラスまたはインターフェース 変数名 = new 親クラス名(コンストラクタ引数) { メソッドのオーバーライド/実装 };`という形で、その場限りのサブクラス(実装)を名前を付けずに作れる。典型的な用途はJava 8以前の`Runnable`や`Comparator`のような、1つのメソッドだけ実装すればよい場面。Java 8以降、抽象メソッドが1つだけの関数型インターフェースに対してはラムダ式(`() -> ...`)がより簡潔な代替として使えるようになったが、ラムダは関数型インターフェース専用で、abstractクラス(今回のGreeterのような)や抽象メソッドが2つ以上あるインターフェースには使えず、匿名クラスが今でも必要になる。教材で扱いが薄めだったのは、ラムダに置き換わって使用頻度が下がった影響かもしれない(未検証の推測)。

疑問

あれわかんねえな。アブストラクトってインスタンス作れないけど、アブストラクトの継承した匿名でクラスを作ってるってことになるってことですか、これは。だから中でグリートを宣言して、一応メソッドをオーバーライドか。で、これ、オーバーライドするときのそのメソッド名が、プロテクテッド以上でないとコンパイルエラーになるから、そこで、ってことか。

->その理解でおおむね合っている。整理すると: (1)Greeter(abstractクラス)自体はインスタンス化できない。(2)new Greeter() { ... }で、その場でGreeterを継承した名前のない匿名サブクラスを作り、それをインスタンス化している。(3)その匿名クラスの中でgreet()を宣言している=親の抽象メソッドgreet()をオーバーライドしている。(4)オーバーライドする側のアクセス修飾子は、親(protected abstract String greet())と同じか、それより広い範囲でなければならない。匿名クラス側は何も付けていない=デフォルト(パッケージプライベート)で、protectedより狭いため10行目でコンパイルエラーになる。「protected以上でないと」という言い方は厳密には「protected、またはそれより広いpublic」で、デフォルトやprivateのようにprotectedより狭いものはNG、という意味。

## 問題12-1

```java
1: abstract class Transport {
2:     private int speed;
3:     public Transport(int speed) { this.speed = speed; }
4:     public int getSpeed() { return speed; }
5:     public abstract void move();
6: }
7: class Airplane extends Transport {
8:     public Airplane(int speed) { super(speed); }
9:     @Override
10:    public void move() {
11:        System.out.println("Airplane: " + getSpeed());
12:    }
13: }
14: abstract class Car extends Transport {
15:     public Car(int speed) { super(speed); }
16: }
17: class Sedan extends Car {
18:     public Sedan(int speed) { super(speed); }
19:     @Override
20:     public void move() {
21:         System.out.println("Sedan: " + getSpeed());
22:     }
23: }
24: public class Main {
25:     public static void main(String[] args) {
26:         Transport[] items = { new Airplane(900), new Sedan(120), new Car(80) };
27:         for (Transport t : items) {
28:             t.move();
29:         }
30:     }
31: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、Airplane→Sedan→Carの順に`move()`が実行される
B. 14行目でコンパイルエラーになる
C. 17行目でコンパイルエラーになる
D. 26行目でコンパイルエラーになる

### 実施記録

回答：B
正解：D
迷ったポイント：14行目の`abstract class Car extends Transport {`は、Carがabstractのまま宣言され続けている限りそれ自体では絶対にコンパイルエラーにならない、という点を見落とした。「抽象メソッドを持つクラスはabstractでなければならない」というルールには従っているため、move()を実装しなくても問題ない。
解説：14行目の`abstract class Car extends Transport {`は**それ自体では絶対にコンパイルエラーになりません**。`Car`はここで`abstract`と宣言されたままなので、「抽象メソッドを持つクラスはabstractでなければならない」というルールにちゃんと従っています。move()を実装しなくても、Carが引き続きabstractである限り何の問題もありません。

「実装クラス(具象クラス)にするならabstractを外さないとダメ」という理解は正しいのですが、逆に言えば**abstractのまま留まっている限りはmove()を実装しなくても合法**、というのがポイントです。実際にエラーが起きるのは26行目の`new Car(80)`で、abstractクラスを直接インスタンス化しようとしている箇所です(11-1・11-2と同じく「abstractクラスは直接newできない」というルール)。



```java
abstract class Car extends Transport {
    public Car(int speed) { super(speed); }
}

class Honda extends Car {
    public Honda(int speed) { super(speed); }
    public void move() {
        System.out.println("Sedan: " + getSpeed());
    }
}

```

abstractはmoveを宣言しなくても素通りさせることができる。



## 問題12-2

```java
1: abstract class Transport {
2:     private int speed;
3:     public Transport(int speed) { this.speed = speed; }
4:     public int getSpeed() { return speed; }
5:     public abstract void move();
6: }
7: class Airplane extends Transport {
8:     public Airplane(int speed) { super(speed); }
9:     @Override
10:    public void move() {
11:        System.out.println("Airplane: " + speed + "km/h");
12:    }
13: }
14: public class Main {
15:     public static void main(String[] args) {
16:         new Airplane(900).move();
17:     }
18: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`Airplane: 900km/h`と出力される
B. 11行目でコンパイルエラーになる
C. 2行目でコンパイルエラーになる
D. 16行目でコンパイルエラーになる

### 実施記録

回答：C
正解：B
迷ったポイント：「abstractは継承目的で定義されるものだからprivateは定義できない」と考えたが誤り。abstractクラスであることとフィールドのアクセス制御は無関係で、abstractクラスも普通のクラスと同様にprivateフィールドを持てる(2行目は問題ない)。
解説：「abstractは継承目的で定義されるものだからprivateは定義できない」という理解は誤りです。abstractクラスも普通のクラスと同様に`private`フィールドを持てます(2行目はコンパイルエラーになりません)。抽象クラスであることと、フィールドのアクセス制御は無関係です。

実際にエラーになるのは11行目です。`speed`は`Transport`で`private`宣言されているため、サブクラス`Airplane`から**直接名前でアクセスすることはできません**(privateメンバーは継承先のクラスからは見えない)。だからこそ4行目の`getSpeed()`のような公開アクセサメソッド経由でアクセスする必要があります。

## 問題13-1

```java
1: public interface Test {
2:     int EXCELLENT = 100;
3:     private int BELOW_AVERAGE = 60;
4:     void foo();
5: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 2行目でコンパイルエラーになる
C. 3行目でコンパイルエラーになる
D. 4行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。
解説：インタフェースのフィールドは暗黙的に`public static final`(定数)として扱われる。`private`を明示的に付けることは許されず、`private`という修飾子自体がインタフェースのフィールド宣言では使用不可(エラー: 「修飾子privateをここで使用することはできません」)。

## 問題13-2

```java
interface Test {
    int EXCELLENT = 100;
    void foo();
}
class Sample implements Test {
    int EXCELLENT = 999;
    @Override public void foo() {}
}
class Main {
    public static void main(String[] args) {
        Sample s = new Sample();
        Test t = s;
        System.out.println(s.EXCELLENT);
        System.out.println(t.EXCELLENT);
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. `999` / `999`
B. `100` / `100`
C. `999` / `100`
D. `100` / `999`

### 実施記録

回答：A
正解：C
迷ったポイント：`Sample.EXCELLENT`(999)は`Test.EXCELLENT`(100)を隠蔽(hiding)しているだけで、オーバーライドではない(フィールドはそもそもオーバーライドの対象にならない)点を見落とした。フィールドアクセスはメソッドと違って動的ディスパッチが働かず、参照の宣言型で静的に決まる。`s`はSample型なので`s.EXCELLENT`はSample側の999、`t`はTest型として宣言されているので`t.EXCELLENT`はTest側の100になる。実体が同じSampleインスタンスでも、アクセスに使った変数の宣言型によって参照先が変わる(以前の問題3-2のstaticメソッド隠蔽と同じ構造)。
解説：「実体(Sample)基準で常に同じ値になるはず」という考え方が誤りの原因。フィールドの隠蔽は「参照の宣言型」で決まる、というルールを意識する必要がある。

疑問

隠蔽ってそういうことか。これ定数だから最大にいうてできないんですよね。だからこれ隠蔽になってるんですね。ローカル変数参照的な感じになってるってことですか？それもExcellentっていう型を使って中に値を入れて、それを参照できるようにしているってこと？

->「finalだから隠蔽になる」は誤り。フィールドの隠蔽はfinalかどうかと無関係で、Javaでは「フィールドは常に隠蔽の対象、メソッドだけがオーバーライドの対象」という一般ルールがある(final無しの普通のPerent/Child継承クラスのintフィールドでも同じ隠蔽が起きることをjavacで検証済み)。「ローカル変数のシャドーイング的」というイメージは方向性としては近いが、実際は「アクセスに使った変数(参照)の宣言型によって、コンパイル時にどちらのフィールドを見るか確定する」というルールであり、ローカル変数のような実行時のスコープ探索とは異なる。また「EXCELLENTという型」は言い方が誤りで、EXCELLENTは型ではなくフィールド(定数)の名前。

疑問

確かインターフェイスってファイナルがついてなかったでしたっけ？

->その記憶は正しい。インタフェースのフィールドはpublic・staticに加えてfinalも暗黙的に付与される(`int EXCELLENT = 100;`は実質`public static final int EXCELLENT = 100;`と同じ)。ただしこれは「finalだから隠蔽になる」という話とは独立した別の事実であり、finalの有無に関わらずフィールドは常に隠蔽される、という前の疑問への回答と矛盾しない。

疑問

ファイナルなのに、こう隠蔽ができるのは何でなんですか？(SampleがTest.EXCELLENTと同名のint EXCELLENT = 999;を宣言できる理由)

->finalは「そのフィールドそのものへの再代入を禁止する」だけであり、「別のクラスが同じ名前で全く別の新しいフィールドを宣言すること」自体は禁止していない。Test.EXCELLENT(100)とSample.EXCELLENT(999)は名前が同じなだけで完全に別々の独立したフィールドであり、Sampleの宣言はTest.EXCELLENTを書き換えているのではなく、Sampleという別クラスの中に新しいフィールドを1個作っているだけ。finalが保証しているのは「Test.EXCELLENTという特定のフィールド自体は一度初期化されたら変更できない」ことだけで、これは今も守られている(Test.EXCELLENTは今でも100のまま)。「同じ名前を使う」ことと「同じフィールドを書き換える」ことは別問題という点がポイント。

疑問

メソッドとか以外の場合っていうのは、基本的にその静的な型を見てメンバーの変数を取得してるって考えは変えなくてもいいですか？

->その考え方で問題ない、良い一般化。整理すると: インスタンスメソッド(通常のメソッド)だけが動的ディスパッチ(実行時の実体の型を見る)の対象で、それ以外(フィールド、staticメソッド、staticフィールド)は全て、コンパイル時に決まる「参照の宣言型(静的型)」を見て解決される。問題3-2(staticメソッドkind())・問題4-1(フィールドx)・問題13-2(インタフェース定数フィールドEXCELLENT)がすべてこの一般化に当てはまる。「これはインスタンスメソッドの呼び出しか？」を確認するのが最速の判定方法(Yesなら実体基準、それ以外なら宣言型基準)。

## 問題14-1

```java
1: interface Eatable {
2:     void showCalories();
3: }
4: class Item {
5:     String name;
6:     Item(String name) { this.name = name; }
7: }
8: class Food extends Item implements Eatable {
9:     private int calories;
10:    public Food(String name, int calories) {
11:        super(name);
12:        this.calories = calories;
13:    }
14:    @Override
15:    void showCalories() {
16:        System.out.println(name + ": " + calories + "kcal/100g");
17:    }
18: }
19: public class Main {
20:     public static void main(String[] args) {
21:         Food f = new Food("Chocolate", 500);
22:         f.showCalories();
23:     }
24: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. 8行目でコンパイルエラーになる
B. コンパイルは成功し、`Chocolate: 500kcal/100g`と出力される
C. 21行目でコンパイルエラーになる
D. 15行目でコンパイルエラーになる

### 実施記録

回答：D
正解：D
迷ったポイント：なし(一発正解)。理由も「インターフェースのメソッドはpublicが付いているので、実装側でそれより狭くなる」という正しい方向で言語化できていた。
解説：インターフェースのメソッドは修飾子を省略すると暗黙的に`public abstract`になる。実装側(15行目)でアクセス修飾子を書かないとデフォルト(パッケージプライベート)アクセスになり、`public`より狭くなってしまう。クラス継承と同様、インターフェースの実装でもアクセス修飾子を弱めることは許されないため、15行目でコンパイルエラーになる。

## 問題14-2

```java
1: interface Eatable {
2:     default void showInfo() {
3:         System.out.println("Eatable info");
4:     }
5: }
6: interface Sellable {
7:     default void showInfo() {
8:         System.out.println("Sellable info");
9:     }
10: }
11: class Food implements Eatable, Sellable {
12:     String name = "Chocolate";
13: }
14: public class Main {
15:     public static void main(String[] args) {
16:         Food f = new Food();
17:         f.showInfo();
18:     }
19: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. 11行目でコンパイルエラーになる
B. コンパイルは成功し、`Eatable info`と出力される
C. コンパイルは成功し、`Sellable info`と出力される
D. 17行目でコンパイルエラーになる

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。理由も「片方をオーバーライドしなければならず、それをしていないためクラス宣言時点でコンパイルエラーになる」という正しい方向で言語化できていた。
解説：`Food`は`Eatable`と`Sellable`の両方を実装しており、どちらも同じシグネチャの`showInfo()`というデフォルトメソッドを持っている。コンパイラはどちらか片方を自動選択せず、「関連しない(unrelated)デフォルトメソッドを2つ継承している」状態を許さない。曖昧さを解消するには`Food`クラス自身で`showInfo()`を明示的にオーバーライドする必要があり、それをしていないため11行目のクラス宣言自体でコンパイルエラーになる(菱形継承問題の一種)。

## 問題15-1

```java
1: interface Foo {
2:     void foo();
3: }
4: interface Bar {
5:     int foo();
6:     void bar();
7: }
8: interface Baz extends Foo, Bar {
9:     void baz();
10: }
11: public class Concrete implements Baz {
12:     @Override public void foo() {}
13:     @Override public void bar() {}
14:     @Override public void baz() {}
15: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 5行目でコンパイルエラーになる
C. 8行目でコンパイルエラーになる
D. 12行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：「メソッドのオーバーライドの解決をその都度していかないとコンパイルエラーになる」という理由付けをしたが、これは不正確。`Foo.foo()`(戻り値void)と`Bar.foo()`(戻り値int)は戻り値の型が互換性のない根本的な矛盾であり、オーバーライドで解決できる類のものではない(Bazに`void foo();`を明示的に追加してもエラーが消えないことをjavacで検証済み)。1つのメソッドは同時にvoidとintを返せないため、8行目でBazがFoo/Barを両方継承しようとした時点で解決不可能なエラーになる。
解説：オーバーライドで解決可能な「defaultメソッドの衝突」(問題15-2)と、オーバーライドでは解決不可能な「戻り値の型が非互換な抽象メソッドの衝突」(本問)は見た目が似ていても性質が異なる点に注意。

## 問題15-2

```java
1: interface Foo {
2:     default void foo() { System.out.println("Foo foo"); }
3: }
4: interface Bar {
5:     default void foo() { System.out.println("Bar foo"); }
6:     void bar();
7: }
8: interface Baz extends Foo, Bar {
9:     void baz();
10: }
11: public class Concrete implements Baz {
12:     @Override public void bar() {}
13:     @Override public void baz() {}
14: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 2行目でコンパイルエラーになる
C. 8行目でコンパイルエラーになる
D. 11行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。ただし理由付け「引数が一緒だからオーバーライドしなきゃいけない」はやや不正確。
解説：`Foo.foo()`と`Bar.foo()`は戻り値の型が両方`void`で完全に一致しており矛盾はない(この点が問題15-1と異なる)。問題は両方が`default`実装を持ち、どちらを使うべきかコンパイラが自動で決められないという**曖昧さ**。この曖昧さは`Baz`または`Concrete`側で明示的に`foo()`をオーバーライドして「どちらを使うか」を指定すれば解決できる(15-1のような根本的な矛盾とは異なり、オーバーライドで解決可能)。

## 問題16-1

```java
1: interface A {
2:     default void x() {
3:         System.out.println("A#x");
4:     }
5: }
6: interface B extends A {
7:     default void x() {
8:         System.out.println("B#x");
9:     }
10: }
11: class Impl implements A, B {
12: }
13: public class Main {
14:     public static void main(String[] args) {
15:         new Impl().x();
16:     }
17: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`A#x`と出力される
B. コンパイルは成功し、`B#x`と出力される
C. 11行目でコンパイルエラーになる
D. 15行目でコンパイルエラーになる

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。理由付けで「動的束縛」という言葉を使っていたが、正確には「サブインターフェース優先の解決ルール」であり、コンパイル時に決まる話である点はやや不正確だった。
解説：`B`は`A`を継承したより具体的な(specific)インターフェースなので、Javaコンパイラは自動的に`B`側の`x()`を「実質的な既定実装」として選ぶ。これはコンパイル時に決まるルールで、実行時に複数の候補から動的に選ぶ「動的束縛」とは異なるが、結果的に見た目は似た振る舞いになる。したがってコンパイルは成功し、`B#x`が出力される。

## 問題16-2

```java
1: interface A {
2:     default void x() {
3:         System.out.println("A#x");
4:     }
5: }
6: class Base {
7:     public void x() {
8:         System.out.println("Base#x");
9:     }
10: }
11: class Impl extends Base implements A {
12: }
13: public class Main {
14:     public static void main(String[] args) {
15:         new Impl().x();
16:     }
17: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`A#x`と出力される
B. 11行目でコンパイルエラーになる
C. コンパイルは成功し、`Base#x`と出力される
D. 15行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：最終的な回答はCで正解だったが、括弧内の理由付けは「ImplがX()を持っていないので15行目でコンパイルエラーになるのでは」という逆方向(コンパイルエラー説)の内容で、回答と矛盾していた。
解説：`Impl`は`x()`を自分で宣言していないが問題ない。`Impl`は`Base`を継承しているため`Base`の`public void x()`をそのまま受け継いでいる。「クラス(スーパークラス経由も含む)の実装は、インターフェースのdefaultメソッドより常に優先される」というルールがあるため、`Impl`の実質的な`x()`は`Base`のものになり、11行目のクラス宣言も15行目の呼び出しもどちらもコンパイルエラーにはならない。実行すると`Base#x`が出力される。

疑問

よりインターフェースの場合は、より具体的な型にとどまるってことですか？じゃあ、動的束縛によってサブクラス一番その枝先のところに行くと思いきや、インターフェイスのせいでその枝先まで行かずに中間ぐらいで止まるってことがあるってこと？

->「途中で止まる」というイメージは不正確。通常のクラス継承の動的ディスパッチは「実際のオブジェクトの型から見て一番近いオーバーライドされたメソッド」が呼ばれるだけで、一番下のクラスがオーバーライドしていなければ自然と1つ上の親のメソッドが使われる(これは「途中で止まっている」のではなく、単にそこにしか実装がないだけ)。インターフェースのdefaultメソッド解決は実はこれとは別の独立したルール。あるクラスが複数の由来(複数インターフェース、あるいはクラス+インターフェース)からdefaultメソッドを受け継いでいて自身は何もオーバーライドしていないとき、どれを採用するか決める優先順位ルールが別途存在する: (1)クラスの実装はインターフェースのdefaultより常に勝つ(問題16-2)。(2)クラス由来が無ければ、より具体的な(サブ)インターフェースが勝つ(問題16-1)。つまり「枝先まで行きたいのに途中で止まる」のではなく、interfaceはextendsのような一本道の継承チェーンではなく複数同時実装できる網目状の構造なので、「どちらが優先か」を決める専用ルールが別途必要になる、というのが正確なイメージ。

## 問題17-1

```java
1: interface Foo {
2:     static void statMethod() {
3:         System.out.println("Foo#statMethod()");
4:     }
5: }
6: interface Bar extends Foo {
7:     default void x() {
8:         System.out.print("a call from Bar#x()... ");
9:         Foo.statMethod();
10:    }
11: }
12: class Concrete implements Bar {
13:     public void y() {
14:         System.out.print("a call from Concrete#y()... ");
15:         Bar.statMethod();
16:     }
17: }
18: public class Main {
19:     public static void main(String[] args) {
20:         Concrete c = new Concrete();
21:         c.x();
22:         c.y();
23:     }
24: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 9行目でコンパイルエラーになる
C. 15行目でコンパイルエラーになる
D. 20行目でコンパイルエラーになる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。
解説：インタフェースのstaticメソッドはサブインタフェースに継承されない。`Foo`のstaticメソッドを`Bar`経由(`Bar.statMethod()`)で呼び出すことはできず、必ず宣言元のインタフェース名(`Foo.statMethod()`)を使う必要がある。これはstaticメソッド特有の制約で、`default`メソッドは普通に継承される。

## 問題17-2

```java
1: interface Foo {
2:     static void statMethod() {
3:         System.out.println("Foo#statMethod()");
4:     }
5: }
6: interface Bar extends Foo {
7:     default void x() {
8:         System.out.print("a call from Bar#x()... ");
9:         Foo.statMethod();
10:    }
11: }
12: class Concrete implements Bar {
13:     public void y() {
14:         System.out.print("a call from Concrete#y()... ");
15:         Foo.statMethod();
16:     }
17: }
18: public class Main {
19:     public static void main(String[] args) {
20:         Concrete c = new Concrete();
21:         c.x();
22:         c.y();
23:         c.statMethod();
24:     }
25: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 9行目でコンパイルエラーになる
C. 15行目でコンパイルエラーになる
D. 23行目でコンパイルエラーになる

### 実施記録

回答：A
正解：D
迷ったポイント：「普通のクラスのstaticメソッドと同じ感覚で、インスタンス経由でも呼べるはず」と考えた。実際、普通のクラス(継承したstaticメソッドを子クラスのインスタンス経由で呼ぶ`c.helper()`のようなケース)ではコンパイルが通ることをjavacで検証済みであり、その理解自体は正しい。しかし**インタフェース**のstaticメソッドはルールが異なり、インスタンス参照経由での呼び出しが明示的に禁止されている。`c.statMethod()`は`Concrete`型の変数`c`からの呼び出しだが、`statMethod()`はインタフェース`Foo`の静的メソッドなのでコンパイルエラーになる。
解説：クラスのstaticメソッドとインタフェースのstaticメソッドは挙動が異なる。インタフェースのstaticメソッドは常に宣言元のインタフェース名を通してのみ呼び出せる、という専用ルールとして覚える必要がある。

## 問題18-1

```java
1: interface Foo {
2:     default void a() {
3:         common();
4:         System.out.println("default a()");
5:     }
6:     static void y() {
7:         statCommon();
8:         System.out.println("static y()");
9:         common();
10:    }
11:    private void common() {
12:        System.out.print("common(): ");
13:    }
14:    private static void statCommon() {
15:        System.out.print("statCommon(): ");
16:    }
17: }
18: public class Main implements Foo {
19:     public static void main(String[] args) {
20:         Foo.y();
21:     }
22: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. 3行目でコンパイルエラーになる
B. コンパイルは成功し、`statCommon(): static y()common():` と出力される
C. 20行目でコンパイルエラーになる
D. 9行目でコンパイルエラーになる

### 実施記録

回答：B
正解：D
迷ったポイント：`common()`(private)と`statCommon()`(private static)を同列に扱い、どちらも`static y()`から問題なく呼べると考えた。
解説：9行目の`common();`が原因でコンパイルエラーになる。`y()`は`static`メソッドだが、`common()`は`private`な**インスタンスメソッド**。インスタンスメソッドは(privateであっても)呼び出すために暗黙の`this`(どのインスタンスに対して呼ぶか)が必要だが、`static`メソッドの中には`this`が存在しない。これは通常のクラスでも起きる「staticコンテキストから非staticメソッドは呼べない」という基本ルールが、インターフェースのprivateメソッドにもそのまま適用されているだけ(問題2-2の疑問で確認した「staticでないメソッドをstaticコンテキストから参照することはできません」と同じエラー)。一方7行目の`statCommon();`は同じく`private`だが**static同士の呼び出し**なので問題なく、`static y()`から`private static`メソッドを呼ぶこと自体は合法。

## 問題18-2

```java
1: interface Foo {
2:     default void a() {
3:         common();
4:         System.out.println("default a()");
5:     }
6:     private void common() {
7:         System.out.print("common(): ");
8:     }
9: }
10: public class Main implements Foo {
11:     public static void main(String[] args) {
12:         Foo t = new Main();
13:         t.common();
14:     }
15: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功し、`common():` と出力される
B. 13行目でコンパイルエラーになる
C. 6行目でコンパイルエラーになる
D. 3行目でコンパイルエラーになる

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。
解説：`common()`はインターフェース`Foo`の中で`private`宣言されているため、インターフェース自身の内部(default/private/staticメソッドの実装内)からしか呼び出せない。実装クラス`Main`から`t.common()`のようにインスタンス経由でアクセスしようとしても、`private`はクラス外(実装クラスも含む)から一切見えないため、13行目でコンパイルエラーになる。

## 問題19-1

```java
// package com.a
1: package com.a;
2: public abstract sealed class Shape
3:                 permits Circle, Triangle, Square {
4:     public abstract double calcArea();
5: }
```

```java
// package com.b
1: package com.b;
2: import com.a.Shape;
3: public final class Heart extends Shape {}
```

`Heart`を含む全ソースをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. `Shape.java`側でコンパイルエラーになる
C. `Heart.java`側でコンパイルエラーになる
D. 実行時に例外がスローされる

### 実施記録

回答：B
正解：C
迷ったポイント：「シール対象にHeartが入っていない」という理解自体は正しかったが、エラーが出る場所を逆に考えた。`Shape.java`の`permits Circle, Triangle, Square`という宣言自体は文法的に何の問題もない(許可リストに何を書くか/書かないかはShape側の自由)。エラーが出るのは、許可されていないのに`extends Shape`しようとした`Heart.java`側(エラー:「クラスはシール・クラスShapeを拡張できません('permits'句に指定されていないためです)」)。
解説：「指定元(Shape)が悪い」ではなく「指定されていないのに拡張しようとした側(Heart)が悪い」という向きで覚える必要がある。

## 問題19-2(※問題設計に不備があり無効)

問題19-1の`Heart.java`を修正せず、`Shape.java`側だけ以下のように変更した(`import com.b.Heart;`を追加し、`permits`にも`Heart`を追加)。

```java
1: package com.a;
2: import com.b.Heart;
3: public abstract sealed class Shape
4:                 permits Circle, Triangle, Square, Heart {
5:     public abstract double calcArea();
6: }
```

この状態でコンパイルするとどうなるか。

A. コンパイルは成功する
B. `Shape.java`側でコンパイルエラーになる
C. `Heart.java`側でコンパイルエラーになる
D. `Circle.java`/`Triangle.java`/`Square.java`側でコンパイルエラーになる

### 実施記録

回答：C(calcArea()未実装を指摘)
出題時の想定正解：B
実際にjavacで検証した結果：**BとCの両方が同時に正しい**(2つの独立したコンパイルエラーが同時発生することが判明)。①`Shape.java`4行目: 「名前のないモジュールのクラスは別のパッケージのシール・クラスを拡張できません」(module-info.javaを使わないプロジェクトでは、sealedクラスの許可された派生クラスは同じパッケージ内でなければならないという制約に、importで名前解決できても抵触する)。②`Heart.java`3行目: `calcArea()`(抽象メソッド)未実装によりHeartがabstractでない具象クラスとして不成立、という別のエラーも同時に発生。
この問題は選択肢をB/Cの二者択一にしてしまった設計ミスであり、ユーザーの回答Cは着眼点として完全に正しかった。記録上は無効問題として扱う。

## 問題20-1

```java
1: public sealed class Shape
2:           /* permits Circle, Triangle, Square */ {}
3: final class Circle extends Shape {}
4: class Triangle extends Shape {}
5: sealed class Square extends Shape {}
6: final class Cube extends Square {}
```

（元のコードから4行目の`non-sealed`修飾子を外した状態）このコードをコンパイルするとどうなるか。

A. コンパイルは通り、正常に実行される
B. 4行目でコンパイルエラーになる
C. 3行目でコンパイルエラーになる
D. 6行目でコンパイルエラーになる

### 実施記録

回答：B
正解：B
迷ったポイント：なし(一発正解)。
解説：4行目の`Triangle`が`sealed`クラス`Shape`を直接継承しているのに`sealed`・`non-sealed`・`final`のいずれの修飾子も付いていないため、その宣言自体（4行目）がコンパイルエラーになります。sealedクラスの直接のサブクラスは、この3つの修飾子のどれか一つを必ず持たなければなりません。

疑問

Aインポートはパッケージとかもしなくていいっていう状態でコンパイルするってことですよね、つまり。

->その通り。Shape.javaとRectangle.java(問題20-2)はどちらも同じデフォルトパッケージ（パッケージ宣言なし）に置かれている想定なので、importなしで同じ型（Triangleなど）を参照できる。javacで複数ファイルを同時にコンパイルする際、同一パッケージ内のクラスはお互いを暗黙に参照できるため。

## 問題20-2

```java
1: public sealed class Shape
2:           /* permits Circle, Triangle, Square */ {}
3: final class Circle extends Shape {}
4: non-sealed class Triangle extends Shape {}
5: sealed class Square extends Shape {}
6: final class Cube extends Square {}
```

```java
// 別ファイル Rectangle.java
final class Rectangle extends Triangle {}
```

Shape.javaとRectangle.javaを一緒にコンパイルするとどうなるか。

A. コンパイルは通り、正常に実行される
B. Rectangle.javaでコンパイルエラーになる
C. Triangleの宣言側（Shape.java）でコンパイルエラーになる
D. Shapeの宣言側（1行目）でコンパイルエラーになる

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。
解説：`Triangle`は`non-sealed`修飾子付きでShapeを継承しているため、sealedの制約が解除され、通常のクラスと同じく別ファイルからでも自由に継承できます。sealedの「継承を許可するクラスを限定する」効果は、`non-sealed`が付いたサブクラス以降には及びません。

## 問題22-1

```java
1: class Super {}
2: class Sub extends Super {}
3: class Other extends Super {}
4: public class Main {
5:     public static void main(String[] args) {
6:         Super s = new Other();
7:         Sub sub = (Sub) s;
8:         System.out.println("ok");
9:     }
10: }
```

このコードをコンパイル・実行するとどうなるか。

A. コンパイルは成功し、`ok`と出力される
B. 7行目でコンパイルエラーになる
C. コンパイルは成功するが、7行目で実行時例外がスローされる
D. コンパイルは成功するが、9行目で実行時例外がスローされる

### 実施記録

回答：C
正解：C
迷ったポイント：なし(一発正解)。
解説：7行目の`(Sub) s`というキャスト自体は、コンパイラが変数`s`の宣言型（Super）だけを見て判断する。`Sub`は`Super`のサブクラスなので「実行時にひょっとしたら`Sub`型かもしれない」という可能性を否定できず、文法上は妥当なキャストとしてコンパイルは通る。しかし実行時の実体は`Other`であり`Sub`ではないため、7行目で`ClassCastException`がスローされる。

## 問題22-2

```java
1: interface Foo {}
2: final class Bar {}
3: public class Main {
4:     static void check(Bar b) {
5:         Foo f = (Foo) b;
6:     }
7:     public static void main(String[] args) {}
8: }
```

このコードをコンパイルするとどうなるか。

A. コンパイルは成功する
B. 5行目でコンパイルエラーになる
C. 2行目でコンパイルエラーになる
D. 4行目でコンパイルエラーになる

### 実施記録

回答：B
正解：B
迷ったポイント：「BarがFooをimplementsしていないから」という理由付けをしたが、これだけでは不十分。問題22-1で見た通り、「今implementsしていない」だけでは即座にコンパイルエラーにはならず、通常は将来のサブクラスがFooを実装する可能性を残してコンパイルを通し実行時チェックに委ねる。
解説：コンパイルエラーになる本当の理由は`Bar`が`final`クラスであること。`final`クラスはサブクラスを一切作れないため、「将来`Bar`のサブクラスが`Foo`を実装する」という可能性そのものが存在しない。コンパイラはこの場合に限り「`Bar`と`Foo`は絶対に互換性がない」と静的に断定できるため、実行を待たずに5行目でコンパイルエラーになる。もし`Bar`が`final`でなければ、22-1と同じ仕組みでコンパイルは通り実行時に判定される。

## 問題22-3

```java
1: interface Foo {}
2: class Bar {}
3: public class Main {
4:     static void check(Bar b) {
5:         Foo f = (Foo) b;
6:     }
7:     public static void main(String[] args) {}
8: }
```

問題22-2から`Bar`の`final`修飾子だけを外した。このコードをコンパイルするとどうなるか。

A. コンパイルは成功する
B. 5行目でコンパイルエラーになる
C. 2行目でコンパイルエラーになる
D. 4行目でコンパイルエラーになる

### 実施記録

回答：A(javacで検証し、コンパイルが通ることを確認した上での正解)
正解：A
迷ったポイント：問題22-2の解説をした直後にユーザーから「finalがなくても普通にダメなんじゃないか」という疑問が出たため、実際にjavacで`final`を外して検証し直した。結果はコンパイル成功で、当初の解説（final非finalによる可否の違い）が正しかったことを確認した。
解説：`Bar`が`final`でない場合、コンパイラは「将来`Bar`を継承して`Foo`を実装するサブクラスが現れる可能性」を否定できないため、`(Foo) b`というキャストはコンパイルを通す。実際にそのようなサブクラスのインスタンスが渡されなければ、実行時に`ClassCastException`がスローされる（問題22-1と同じ「宣言型で可能性があるかどうかだけをコンパイル時に判定し、実際の適合性は実行時に検証する」という仕組み）。問題22-2の`final class Bar`との違いは、`final`の有無がこの「将来の可能性」を左右する点にある。

## 問題21-1

```java
1: sealed interface Foo permits Bar {}
2: sealed interface Bar extends Foo permits Baz {}
3: final interface Baz extends Bar {}
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 1行目でコンパイルエラーになる
C. 2行目でコンパイルエラーになる
D. 3行目でコンパイルエラーになる

### 実施記録

回答：A
正解：D
迷ったポイント：`interface`と`final`は組み合わせられない(エラー: 「修飾子interfaceとfinalの組合せは不正です」)という制約を見落とした。インタフェースは「実装を強制する契約」という性質上`final`(継承・実装禁止)と矛盾するため、継承を禁止したいインタフェースには`sealed`(許可リストで限定)を使う必要がある。
解説：1・2行目の`sealed`宣言自体は正しく、エラーは3行目の`final interface`という組み合わせ単体の問題。

## 問題21-2

```java
1: sealed interface Item permits Food {
2:     double TAX_RATE = 0.1;
3:     int calcPrice();
4: }
5: record Food(String name, int price) implements Item {
6:     @Override
7:     public int calcPrice() {
8:         return (int)(price * (1+TAX_RATE));
9:     }
10: }
11: record Drink(String name, int price) implements Item {
12:     @Override
13:     public int calcPrice() {
14:         return (int)(price * (1+TAX_RATE));
15:     }
16: }
17: public class Main {
18:     public static void main(String[] args) {
19:         Food food = new Food("Chocolate", 750);
20:         System.out.println(food.name() + ": " + food.calcPrice() + "yen");
21:     }
22: }
```

このコードをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. 1行目でコンパイルエラーになる
C. 5行目でコンパイルエラーになる
D. 11行目でコンパイルエラーになる

### 実施記録

回答：D
正解：D
迷ったポイント：なし(一発正解)。
解説：`sealed interface Item permits Food`は`Food`だけを許可しており、`Drink`は許可リストに入っていないため、`implements Item`しようとした11行目でコンパイルエラーになる。recordであってもクラスと同様にsealedインタフェースのpermitsルールは変わらず適用される。

## 問題23-1(com.aパッケージのShape/Square/Cubeを利用)

```java
// package com.a
public abstract class Shape {
    public abstract double calcArea();
    @Override
    public String toString() {
        return getClass().getName() + ": " + calcArea() + " sq cm";
    }
}
public class Square extends Shape {
    double side = 5.0;
    @Override
    public double calcArea() { return Math.pow(side, 2.0); }
}
public class Cube extends Square {
    @Override
    public double calcArea() { return 6 * Math.pow(side, 2.0); }
    public double calcVolume() { return Math.pow(side, 3.0); }
}
```

```java
// Main.java(デフォルトパッケージ)
import com.a.*;
public class Main {
    public static void main(String[] args) {
        Shape obj = new Cube();
        System.out.println("Cube : " + obj.calcArea());
        System.out.println("Shape: " + obj.toString());
    }
}
```

このプログラムを実行すると、出力はどうなるか。

A. `Cube : 150.0` / `Shape: com.a.Cube: 150.0 sq cm`
B. `Cube : 150.0` / `Shape: com.a.Shape: 25.0 sq cm`
C. `Cube : 25.0` / `Shape: com.a.Cube: 150.0 sq cm`
D. コンパイルエラーが発生する

### 実施記録

回答：A
正解：A
迷ったポイント：なし(一発正解)。
解説：`obj`はShape型として宣言されているので、`obj.side`のようにSquareで定義された`side`フィールドに直接アクセスしようとするとコンパイルエラーになる(javacで検証済み)という理解も正しい。ただし本問には`obj.side`は出てこず、`obj.calcArea()`/`obj.toString()`(どちらもShapeで宣言済み)だけを呼んでいるためコンパイル・実行できる。`calcArea()`は動的ディスパッチで実際のCube版(150.0)が呼ばれ、`toString()`内部の`calcArea()`呼び出しも同様にCube版が呼ばれるため`Shape: com.a.Cube: 150.0 sq cm`になる。

## 問題23-2

```java
// Main.java(上記Shape/Square/Cubeはそのまま)
import com.a.*;
public class Main {
    public static void main(String[] args) {
        Shape obj = new Cube();
        System.out.println("Cube : " + obj.calcArea());
        System.out.println("Shape: " + obj.toString());
        System.out.println("Volume: " + obj.calcVolume());
    }
}
```

このプログラムをコンパイルした結果として正しいものはどれか。

A. コンパイルは成功する
B. `Shape.java`側でコンパイルエラーになる
C. `Main.java`側でコンパイルエラーになる
D. 実行時に`ClassCastException`がスローされる

### 実施記録

回答：A
正解：C
迷ったポイント：`calcVolume()`はCubeだけが持つメソッドでShapeには存在しないため、`obj.calcVolume()`は`Main.java`側でコンパイルエラーになる(エラー: 「シンボルを見つけられません／シンボル: メソッド calcVolume()／場所: タイプShapeの変数 obj」)という点を見落とした。呼び出すには`((Cube) obj).calcVolume()`のような明示的なダウンキャストが必要。
解説：「Shape型の変数からはShapeで宣言されたメンバーしか見えない」というルールは、フィールドだけでなくメソッドにも同様に適用される(23-1の`obj.side`の議論と同じ構造)。