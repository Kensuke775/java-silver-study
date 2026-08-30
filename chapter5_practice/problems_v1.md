## 目次

**問題一覧**

- [問題1-1](#q1-1)
- [問題1-2](#q1-2)
- [問題3-1](#q3-1)
- [問題3-2](#q3-2)
- [問題4-1](#q4-1)
- [問題4-2](#q4-2)
- [問題5-1](#q5-1)
- [問題5-2](#q5-2)
- [問題6-1](#q6-1)
- [問題6-2](#q6-2)
- [問題7-1](#q7-1)
- [問題7-2](#q7-2)
- [問題8-1](#q8-1)
- [問題8-2](#q8-2)
- [問題9-1](#q9-1)
- [問題9-2](#q9-2)
- [問題10-1](#q10-1)
- [問題10-2](#q10-2)
- [問題11-1](#q11-1)
- [問題11-2](#q11-2)
- [問題13-1](#q13-1)
- [問題13-2](#q13-2)
- [問題12-1](#q12-1)
- [問題12-2](#q12-2)
- [問題14-1](#q14-1)
- [問題14-2](#q14-2)
- [問題15-1](#q15-1)
- [問題15-2](#q15-2)
- [問題16-1](#q16-1)
- [問題16-2](#q16-2)
- [問題17-1](#q17-1)
- [問題17-2](#q17-2)
- [問題ex18-1](#qex18-1)
- [問題ex18-2](#qex18-2)

<a id="q1-1"></a>
## 問題1-1

```java
public class Item {
    String name;
    String getName() {
        return name;
    }
    void setName(String name) {
        name = name;
    }
}

public class Main {
    public static void main(String[] args) {
        Item item1 = new Item();
        item1.setName("Sweater");
        System.out.println("item1 : " + item1.getName());
    }
}
```

このコードの実行結果はどれか。

A. item1 : Sweater

B. item1 : null

C. コンパイルエラーになる

D. NullPointerExceptionがスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：`name = name;`が仮引数への自己代入にしかならず、フィールドが更新されないことに気づけなかった(`this.`がないと動かない点)

---

<a id="q1-2"></a>
## 問題1-2

```java
public class Item {
    String name;
    String getName() {
        return name;
    }
    void setName(String n) {
        this.name = n;
    }
}

public class Main {
    public static void main(String[] args) {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setName("Sweater");
        item2 = item1;
        item2.setName("T-shirt");
        System.out.println("item1 : " + item1.getName());
        System.out.println("item2 : " + item2.getName());
    }
}
```

このコードの実行結果はどれか。

A. item1 : Sweater / item2 : T-shirt

B. item1 : T-shirt / item2 : T-shirt

C. item1 : Sweater / item2 : Sweater

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q3-1"></a>
## 問題3-1

```java
class Item {
    private String name;
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
}
```
```java
Item item1 = new Item();
item1.name = "Sweater";
```

`Item`クラスの`name`フィールドを`private`に変更した(getName/setNameの中身は変更なし)。このとき`item1.name = "Sweater";`の行はどうなるか。

A. コンパイルエラーになる

B. "Sweater"が正常に設定され、出力に影響しない

C. 実行時に`IllegalAccessException`がスローされる

D. "Sweater"は設定されるが、`getName()`を呼ぶと`null`が返る

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q3-2"></a>
## 問題3-2

```java
class Item {
    String name;
    String getName() {
        return name;
    }
    void setName(String name) {
        name = name;
    }
}
```
```java
Item item1 = new Item();
Item item2 = new Item();

item1.name = "Sweater";
String name = item1.getName();
item2.setName("T-shirt");
System.out.println("item1 : " + name);
System.out.println("itme2 : " + item2.getName());
```

`setName`の中身を`this.name = name;`から`name = name;`に変更した(フィールドは`private`にせずそのまま)。この場合の出力はどうなるか。

A. `item1 : Sweater` / `itme2 : T-shirt`

B. `item1 : Sweater` / `itme2 : null`

C. `item1 : null` / `itme2 : null`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q4-1"></a>
## 問題4-1

```java
class Item {
    String name;
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    public static void main(String[] args) {
        Item item = new Item();
        item.setName(null);
        System.out.println("item : " + item.getName());
        System.out.println(item.getName() == null);
    }
}
```

このコードの実行結果はどれか。

A. コンパイルエラーになる

B. 1行目でNullPointerExceptionがスローされる

C. item : null / true

D. item : / true

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：チェック

---

<a id="q4-2"></a>
## 問題4-2

```java
class Item {
    String name;
    String getName() {
        return name;
    }
    void setName(String name) {
        this.name = name;
    }
    public static void main(String[] args) {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setName("Jacket");
        item2.setName("Jacket");
        System.out.println(item1 == item2);
        System.out.println(item1.equals(item2));
        System.out.println(item1.getName().equals(item2.getName()));
    }
}
```

このコードの実行結果はどれか。

A. true / true / true

B. false / false / true

C. false / true / true

D. true / false / false

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q5-1"></a>
## 問題5-1

`com/Item.java`(パッケージ`com`):
```java
package com;

public class Item {
    String name;
     String getName() {
        return name;
    }
     void setName(String name) {
        this.name = name;
    }
}
```

`Main.java`(デフォルトパッケージ、`com`とは別ディレクトリ):
```java
import com.Item;

class Main {
    public static void main(String[] args) {
        Item item = new Item();
        System.out.println(item.getName());
    }
}
```

元のソースでは`Item`クラスに修飾子が付いていなかったが、ここでは`public class Item`に変更した(フィールド・メソッドはそのまま修飾子なし)。この場合どうなるか。

A. 問題なくコンパイル・実行でき、`null`が出力される

B. `new Item()`の行でコンパイルエラーになる

C. `item.getName()`の行でコンパイルエラーになる

D. 実行時に`IllegalAccessError`がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：importの書き方自体に一瞬迷ったが、結果的には正解できた

---

<a id="q5-2"></a>
## 問題5-2

`com/Item.java`(パッケージ`com`):
```java
package com;

public class Item {
    public String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```

`Main.java`(デフォルトパッケージ、`com`とは別ディレクトリ):
```java
class Main {
    public static void main(String[] args) {
        com.Item item = new com.Item();
        item.setName("Sweater");
        System.out.println(item.getName());
    }
}
```

`Item`クラス・フィールド・メソッドすべてに`public`を付け、Main側では`import com.Item;`を書かずに`com.Item`という完全修飾名だけで直接使っている。この場合どうなるか。

A. `import`がないためコンパイルエラーになる

B. `Sweater`が出力される

C. `null`が出力される

D. 実行時に`ClassNotFoundException`がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：`com.Item`のような完全修飾名での直接使用に馴染みがなく、importなしでは使えないと誤って判断した。良いエッジケースだったとのこと。チェック。

---

<a id="q6-1"></a>
## 問題6-1

```java
class Item {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
public class Main {
    public static void main(String[] args) {
        Item item = new Item();
        item.setName("T-shirt");
        System.out.println("item : " + item.getName());
        item.name = "Sweater";
        System.out.println("item : " + item.name);
    }
}
```

このコードの実行結果はどれか。

A. item : T-shirt / item : Sweater

B. コンパイルエラーになる

C. 実行時に例外がスローされる

D. item : T-shirt とだけ表示される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(ただしクラス修飾子とメンバー修飾子の役割の違いを確認する質問あり)

---

<a id="q6-2"></a>
## 問題6-2

```java
class Item {
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public boolean sameName(Item other) {
        return this.name.equals(other.name);
    }
}
public class Main {
    public static void main(String[] args) {
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setName("T-shirt");
        item2.setName("T-shirt");
        System.out.println(item1.sameName(item2));
    }
}
```

このコードの実行結果はどれか。

A. コンパイルエラーになる(otherのnameにアクセスできない)

B. true

C. false

D. NullPointerExceptionがスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q7-1"></a>
## 問題7-1

```java
public class Sample {
    public void printItem(String s) {}
    public void printItem(String[] s, int i) {}
    public boolean printItem(int i, String[] s) { return true; }
    void printItem() {}
    public void printItem(String item) {}
}
```

元のサンプルではコメントアウトされていた5番目のメソッド`public void printItem(String item) {}`を有効にした。このクラスはどうなるか。

A. コンパイルエラーになる(1行目のメソッドと引数の型が重複するため定義できない)

B. 問題なくコンパイルできる(オーバーロードとして成立する)

C. コンパイルエラーになる(引数名`item`が予約語として扱われるため)

D. 実行時に例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：A
迷ったポイント：コンパイルエラーになることは分かったが、選択肢の理由文の中身までは確認せず選んでしまった。実際は引数名ではなく引数の型(String)の重複が原因。チェック。

---

<a id="q7-2"></a>
## 問題7-2

```java
public class Sample {
    public void printItem(String s) {}
    public void printItem(String[] s, int i) {}
    public boolean printItem(int i, String[] s) { return true; }
    public void printItem(int i, String[] s) {}
    void printItem() {}
}
```

3行目のメソッドと同じ引数`(int i, String[] s)`を持つが、戻り値の型だけ`void`に変えた新しいメソッドを追加した。このクラスはどうなるか。

A. 問題なくコンパイルできる(戻り値の型が違うので別メソッドとして扱われる)

B. 3行目のメソッドが上書きされ、`boolean`が`void`に変わる

C. `boolean`から`void`への変更自体が許可されないためコンパイルエラーになる

D. コンパイルエラーになる(引数の型が同じで戻り値だけ異なるメソッドは区別されない)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q8-1"></a>
## 問題8-1

```java
class Item {
    private String name;
    public void setName(String name) { this.name = name; }
    public void printItem() {
        System.out.println("Item : " + name);
    }
    public void printItem(String mark) {
        System.out.print(mark);
        System.out.print(" Item : " + name);
        System.out.println(" " + mark);
    }
    public void printItem(String mark, int num) {
        String line = "";
        for (int i = 0; i < num; i++) {
            line += mark;
        }
        printItem(line);
    }
}
public class Main {
    public static void main(String[] args) {
        Item item = new Item();
        item.setName("T-shirt");
        item.printItem("#", 0);
        item.printItem("#", -2);
    }
}
```

このコードの実行結果はどれか。

A. 何も出力されない

B. ` Item : T-shirt `(前後に半角スペース)が2回出力される

C. `# Item : T-shirt #`が2回出力される

D. 1回目は何も出力されず、2回目は`# Item : T-shirt #`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：ループが0回・負数の場合に「何も出力されない」と誤解した。実際はline=""のままprintItem("")が呼ばれ、前後スペースだけの行が出力される

---

<a id="q8-2"></a>
## 問題8-2

```java
class Item {
    private String name;
    public void setName(String name) { this.name = name; }
    public void printItem() {
        System.out.println("Item : " + name);
    }
    public void printItem(String mark) {
        System.out.print(mark);
        System.out.print(" Item : " + name);
        System.out.println(" " + mark);
    }
    public void printItem(String mark, int num) {
        String line = "";
        for (int i = 0; i < num; i++) {
            line += mark;
        }
        printItem(line);
    }
}
public class Main {
    public static void main(String[] args) {
        Item item = new Item();
        item.setName("T-shirt");
        item.printItem(null);
    }
}
```

このコードの実行結果はどれか。

A. コンパイルエラーになる(呼び出す printItem が曖昧)

B. `null Item : T-shirt null`

C. `NullPointerException`がスローされる

D. ` Item : T-shirt `(markの部分は何も表示されない)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(本人コメント: 難しい)

---

<a id="q9-1"></a>
## 問題9-1

```java
public class Sample {
    public void method(char... c) { ... }
    public void method(String s, int... i) { ... }
    public void method(int i, int... j) { ... }
    public void method(int i, int j) { ... }
    public void method(String... s, int i) {}
}
```

元のコードでコメントアウトされていた5番目のメソッド`public void method(String... s, int i) {}`を有効にした。このクラスはどうなるか。

A. 問題なくコンパイルできる

B. コンパイルエラーになる(可変長引数は最後のパラメータでなければならない)

C. コンパイルエラーになる(同じ型の可変長引数を複数のメソッドで使えないため)

D. 実行時に例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：可変長引数の位置制約(最後のパラメータでなければならない)を知らなかった

---

<a id="q9-2"></a>
## 問題9-2

```java
public class Sample {
    public void method(char... c) { ... }
    public void method(String s, int... i) { ... }
    public void method(int i, int... j) {
        System.out.print("method(int i, int... j) : " + i + ":");
        for (int v : j) { System.out.print(v); }
        System.out.println();
    }
    // method(int i, int j) は削除した

    public static void main(String... args) {
        Sample s = new Sample();
        s.method(100, 200);
    }
}
```

4番目のメソッド`method(int i, int j)`(引数の数が完全一致する非可変長引数版)を削除した。この状態で`s.method(100, 200);`を呼び出すとどうなるか。

A. コンパイルエラーになる(呼び出しがどのメソッドか曖昧で決定できないため)

B. 実行時に`NoSuchMethodError`がスローされる

C. `method(int i, int... j) : 100:200`が出力される

D. `method(char... c) : `の呼び出しとして解釈され、文字化けした出力になる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q10-1"></a>
## 問題10-1

```java
public class Sample {
    public void method(int i) { System.out.print("int : " + i); }
    public void method(long l) { System.out.print("long : " + l); }
    public void method(float f) { System.out.print("float : " + f); }
    public void method(double d) { System.out.print("double : " + d); }
    public void method(Integer i) { System.out.print("Integer : " + i); }
    public void method(int... i) { System.out.print("int... : " + i[0]); }
    public void method(byte b) { System.out.print("byte : " + b); }
    public static void main(String... args) {
        Sample s = new Sample();
        s.method(100);
    }
}
```

元のサンプルではコメントアウトされていた`method(int i)`を有効にした(他のメソッドはそのまま)。この状態で`s.method(100);`の出力はどうなるか。

A. `long : 100`(引数なしのメソッドが優先されるルールは変わらないため)

B. コンパイルエラーになる(オーバーロードの候補が曖昧になるため)

C. `int... : 100`(可変長引数が優先される)

D. `int : 100`(完全に型が一致するメソッドが最優先される)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q10-2"></a>
## 問題10-2

```java
public class Sample {
    public void method(float f) { System.out.print("float : " + f); }
    public void method(double d) { System.out.print("double : " + d); }
    public void method(Integer i) { System.out.print("Integer : " + i); }
    public void method(int... i) { System.out.print("int... : " + i[0]); }
    public void method(byte b) { System.out.print("byte : " + b); }
    public static void main(String... args) {
        Sample s = new Sample();
        s.method(100);
    }
}
```

元のサンプルにあった`method(long l)`を削除した(int版はコメントアウトのまま)。この状態で`s.method(100);`の出力はどうなるか。

A. `float : 100.0`が出力される(floatの方がdoubleよりも「狭い」ため、より具体的なメソッドとして優先される)

B. `double : 100.0`が出力される(より安全な拡大変換が優先されるため)

C. コンパイルエラーになる(floatとdoubleのどちらのメソッドを呼ぶか曖昧になるため)

D. `long : 100`が出力される(内部的に自動でlong型が生成されるため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q11-1"></a>
## 問題11-1

```java
public class Item {
    private int id;
    private String name;
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public void printItem() {
        System.out.println("Item id:" + id + " name:" + name);
    }
    public static void main(String[] args) {
        Item item = new Item();
        item.printItem();
    }
}
```

このコードの実行結果はどれか。

A. Item id:0 name:null が出力される

B. コンパイルエラーになる

C. NullPointerExceptionがスローされる

D. Item id: name: が出力される(idもnameも空)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：独自のコンストラクタを1つでも定義すると暗黙の引数なしコンストラクタが生成されなくなる点に気づけなかった

---

<a id="q11-2"></a>
## 問題11-2

```java
public class Item {
    private int id;
    private String name;
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Item(String name) {
        System.out.println("before this()");
        this(0, name);
    }
    public void printItem() {
        System.out.println("Item id:" + id + " name:" + name);
    }
    public static void main(String[] args) {
        Item item = new Item("Jacket");
        item.printItem();
    }
}
```

このコードの実行結果はどれか。

A. before this() / Item id:0 name:Jacket

B. Item id:0 name:Jacket (before this()は出力されない)

C. コンパイルエラーになる

D. スタックオーバーフローで実行時エラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q13-1"></a>
## 問題13-1

```java
public class Sample {
    int value = 100;
    static int commonValue = 500;
    public void printValue() {
        System.out.println("instance value : " + value);
    }
    public static void printCommonValue() {
        System.out.println("static common value : " + commonValue);
    }
}
public class Main {
    public static void main(String[] args) {
        Sample s1 = new Sample();
        Sample s2 = new Sample();
        s1.commonValue = 999;
        s2.printCommonValue();
        Sample.printCommonValue();
        System.out.println(s1.commonValue == s2.commonValue);
    }
}
```

このコードの実行結果はどれか。

A. static common value : 500 / static common value : 999 / false

B. static common value : 999 / static common value : 999 / true

C. コンパイルエラーになる(s1.commonValueへの代入が不正)

D. static common value : 999 / static common value : 500 / false

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：B
迷ったポイント：参照変数経由でのstatic変数への代入がコンパイルエラーになると誤解した。実際は許可されており、かつインスタンス間で共有される実体が更新される

---

<a id="q13-2"></a>
## 問題13-2

```java
public class Sample {
    int value = 100;
    static int commonValue = 500;
    public void printValue() {
        System.out.println("instance value : " + value);
    }
    public static void printCommonValue() {
        System.out.println("static common value : " + commonValue);
    }
    public static void printBoth() {
        System.out.println(value);
        System.out.println(commonValue);
    }
}
```

このコードの実行結果はどれか。

A. コンパイルは通り、printBoth()呼び出し時にNullPointerExceptionがスローされる

B. コンパイルエラーになる

C. 問題なくコンパイルされ、value=0, commonValue=500 が出力される

D. valueの行だけコンパイルエラーになり、commonValueの行はコンパイルされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q12-1"></a>
## 問題12-1

```java
public class Item {
    private int id;
    private String name;
    public Item() {
        System.out.println("before this()");
        this(0, "T-shirt");
    }
    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public void printItem() {
        System.out.println("Item id:" + id + " name:" + name );
    }
    public static void main(String[] args) {
        new Item().printItem();
    }
}
```

元のコードでは`this(0, "T-shirt");`がコンストラクタの先頭文だったが、その前に`System.out.println("before this()");`を追加した。このクラスはどうなるか。

A. 問題なくコンパイル・実行でき、`before this()`が先に出力される

B. 実行時に`StackOverflowError`がスローされる

C. コンパイルエラーになる(`this()`呼び出しはコンストラクタの先頭文でなければならない)

D. コンパイルエラーになる(`this()`は`Item()`コンストラクタ内で2回以上呼び出せないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q12-2"></a>
## 問題12-2

```java
public class Item {
    private int id;
    private String name;
    public Item() {
        this(0, "T-shirt");
    }
    public Item(int id, String name) {
        this();
        this.id = id;
        this.name = name;
    }
    public void printItem() {
        System.out.println("Item id:" + id + " name:" + name );
    }
    public static void main(String[] args) {
        new Item().printItem();
    }
}
```

`Item(int, String)`コンストラクタの先頭に`this();`を追加した(`Item()`は元のまま`this(0, "T-shirt");`を呼ぶ)。このクラスはどうなるか。

A. `Item(int, String)`が呼ばれた後に`Item()`が呼ばれ、無限ループになり実行時に`StackOverflowError`がスローされる

B. 問題なくコンパイルでき、`id:0 name:T-shirt`のインスタンスが生成される

C. コンパイルエラーになる(`this()`は1つのクラスに付き1回しか定義できないため)

D. コンパイルエラーになる(コンストラクタ同士が循環して呼び出し合っている)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：D
迷ったポイント：循環呼び出しは実行時のStackOverflowErrorになると思ったが、実際はコンパイル時に静的に検出される。チェック。

---

<a id="q14-1"></a>
## 問題14-1

```java
class Test {
    static int num;
    String s;
    static {
        num = 100;
        System.out.println("static Initializer... num : " + num);
    }
    {
        this.s = "Test";
        System.out.println("Initializer... s : " + s);
    }
    public Test(String s) {
        this.s = s;
        System.out.println("Constructor... s : " + s);
    }
}
public class Main {
    static { System.out.println("static Initializer... Main"); }
    public static void main(String[] args) {
        new Test("A");
        new Test("B");
    }
}
```

このコードの実行結果はどれか。

A. static Initializer... Main / static Initializer... num : 100 / Initializer... s : Test / Constructor... s : A / static Initializer... num : 100 / Initializer... s : Test / Constructor... s : B

B. static Initializer... Main / static Initializer... num : 100 / Initializer... s : Test / Constructor... s : A / Initializer... s : Test / Constructor... s : B

C. static Initializer... num : 100 / static Initializer... Main / Initializer... s : Test / Constructor... s : A / Initializer... s : Test / Constructor... s : B

D. static Initializer... Main / Initializer... s : Test / Constructor... s : A / static Initializer... num : 100 / Initializer... s : Test / Constructor... s : B

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q14-2"></a>
## 問題14-2

```java
class Test {
    static int num;
    {
        System.out.println("Initializer... s : " + s);
        this.s = "Test";
    }
    String s;
    static {
        num = 100;
        System.out.println("static Initializer... num : " + num);
    }
    public Test(String s) {
        this.s = s;
        System.out.println("Constructor... s : " + s);
    }
}
public class Main {
    public static void main(String[] args) {
        new Test("A");
    }
}
```

このコードの実行結果はどれか。

A. Initializer... s : null / static Initializer... num : 100 / Constructor... s : A

B. コンパイルエラーになる

C. Initializer... s : Test / static Initializer... num : 100 / Constructor... s : A

D. NullPointerExceptionがスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：フィールド宣言より前の初期化子から、そのフィールドを読み取ると前方参照エラーになる点に気づけず、単にデフォルト値nullが読めると誤解した

---

<a id="q15-1"></a>
## 問題15-1

```java
public class Main {
    public static void main(String[] args) {
        int[] val1 = {100};
        int[] val2 = val1;
        val2[0] = 200;
        Main obj = new Main();
        obj.methodA(val2);
        System.out.println("val1[0] : " + val1[0]);
        System.out.println("val2[0] : " + val2[0]);
    }
    public void methodA(int[] val3) {
        val3[0] += val3[0];
        System.out.println("val3[0] : " + val3[0]);
    }
}
```

`val1`と`val2`を`int`から`int[]`に変えた(`val2 = val1;`で同じ配列を参照する)。この状態で実行した場合の3行の出力はどうなるか。

A. `val3[0] : 400` / `val1[0] : 400` / `val2[0] : 400`

B. `val3[0] : 400` / `val1[0] : 200` / `val2[0] : 400`

C. `val3[0] : 400` / `val1[0] : 100` / `val2[0] : 200`

D. `val3[0] : 200` / `val1[0] : 200` / `val2[0] : 200`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：A
迷ったポイント：val1とval2の参照共有(val2[0]=200でval1[0]も200になる)までは理解できたが、メソッドに渡したval3も同じ配列を参照している点(要素の書き換えが全員に反映される)を見落とした

---

<a id="q15-2"></a>
## 問題15-2

```java
public class Main {
    public static void main(String[] args) {
        int[] val1 = {100};
        int[] val2 = val1;
        val2[0] = 200;
        Main obj = new Main();
        obj.methodA(val2);
        System.out.println("val1[0] : " + val1[0]);
        System.out.println("val2[0] : " + val2[0]);
    }
    public void methodA(int[] val3) {
        val3 = new int[]{999};
        System.out.println("val3[0] : " + val3[0]);
    }
}
```

`methodA`の中身を「要素を書き換える」のではなく「`val3`に新しい配列を代入する」に変えた。この場合の3行の出力はどうなるか。

A. `val3[0] : 999` / `val1[0] : 999` / `val2[0] : 999`

B. `val3[0] : 200` / `val1[0] : 200` / `val2[0] : 200`

C. `val3[0] : 999` / `val1[0] : 200` / `val2[0] : 200`

D. コンパイルエラーになる(メソッド内でパラメータに新しい配列を代入することはできないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q16-1"></a>
## 問題16-1

```java
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3};
        Main obj = new Main();
        obj.methodB(array);
        System.out.println("main() array : " + Arrays.toString(array));
    }
    public void methodB(int[] ary) {
        ary[1] = 5;
        ary = new int[]{9, 9, 9};
        ary[0] = 100;
        System.out.println("methodB() ary : " + Arrays.toString(ary));
    }
}
```

このコードの実行結果はどれか。

A. methodB() ary : [100, 9, 9] / main() array : [1, 5, 3]

B. methodB() ary : [100, 9, 9] / main() array : [100, 9, 9]

C. methodB() ary : [100, 5, 9] / main() array : [1, 5, 3]

D. methodB() ary : [100, 9, 9] / main() array : [1, 2, 3]

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q16-2"></a>
## 問題16-2

```java
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        int[] array = {1, 2, 3};
        Main obj = new Main();
        obj.methodB(array);
        System.out.println("main() array : " + Arrays.toString(array));
    }
    public void methodB(int[] ary) {
        modify(ary);
        System.out.println("methodB() ary : " + Arrays.toString(ary));
    }
    public void modify(int[] a) {
        a[0] = 99;
        a = new int[]{0, 0, 0};
    }
}
```

このコードの実行結果はどれか。

A. methodB() ary : [99, 2, 3] / main() array : [99, 2, 3]

B. methodB() ary : [0, 0, 0] / main() array : [0, 0, 0]

C. methodB() ary : [99, 2, 3] / main() array : [1, 2, 3]

D. methodB() ary : [0, 0, 0] / main() array : [99, 2, 3]

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q17-1"></a>
## 問題17-1

```java
public class Main {
    public static void main(String[] args) {
        Item item1 = new Item("T-shirt");
        Item item2 = new Item("T-shirt");
        item2.name = "Jeans";
        System.out.println("item1, item2 : "
                        + item1.name + ", " + item2.name);
        System.out.println("item1 == item2 : "
                        + (item1 == item2));
    }
}
class Item {
    String name;
    Item(String name) { this.name = name; }
}
```

元のコードでは`Item item2 = item1;`(同じ参照を代入)だったが、代わりに`Item item2 = new Item("T-shirt");`(同じ引数で別インスタンスを生成)に変えた。この場合の出力はどうなるか。

A. `item1, item2 : Jeans, Jeans` / `item1 == item2 : true`

B. コンパイルエラーになる(同じ引数で2つのオブジェクトを生成できないため)

C. `item1, item2 : T-shirt, Jeans` / `item1 == item2 : true`

D. `item1, item2 : T-shirt, Jeans` / `item1 == item2 : false`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q17-2"></a>
## 問題17-2

```java
public class Main {
    public static void main(String[] args) {
        Item item1 = new Item("Jeans");
        Item item3 = new Item("Jeans");
        System.out.println("item1.equals(item3) : " + item1.equals(item3));
        System.out.println("item1.name.equals(item3.name) : " + item1.name.equals(item3.name));
    }
}
class Item {
    String name;
    Item(String name) { this.name = name; }
}
```

`Item`クラスは`equals()`メソッドをオーバーライドしていない。`item1`と`item3`は別インスタンスだが、`name`フィールドの値は同じ`"Jeans"`。この場合の出力はどうなるか。

A. `item1.equals(item3) : false` / `item1.name.equals(item3.name) : true`

B. `item1.equals(item3) : true` / `item1.name.equals(item3.name) : true`

C. `item1.equals(item3) : true` / `item1.name.equals(item3.name) : false`

D. `item1.equals(item3) : false` / `item1.name.equals(item3.name) : false`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

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
