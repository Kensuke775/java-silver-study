## 目次

**問題一覧**

- [原本3-1](#q3-1)
- [原本3-2](#q3-2)
- [原本3-3](#q3-3)
- [原本3-4](#q3-4)
- [原本3-5](#q3-5)
- [原本3-6](#q3-6)
- [原本3-7](#q3-7)
- [原本3-8](#q3-8)
- [原本3-9](#q3-9)
- [原本3-10](#q3-10)
- [原本3-11](#q3-11)
- [原本3-12](#q3-12)
- [原本3-13](#q3-13)
- [原本3-14](#q3-14)
- [原本3-15](#q3-15)
- [原本3-16](#q3-16)
- [原本3-17](#q3-17)
- [原本3-18](#q3-18)
- [原本3-19](#q3-19)
- [原本3-20](#q3-20)

<a id="q3-1"></a>
## 原本3-1

次の記述に関して、コンパイルエラーになるものはどれですか。（2つ選択）

A. `short a = 11.2;`
B. `byte b = 100;`
C. `int c = 'c';`
D. `double d = 3.5F;`
E. `long e = 13.0;`
F. `float f = 200L;`

**実施記録**

回答：A, E
正解：A, E
迷ったポイント：なし

<a id="q3-2"></a>
## 原本3-2

```java
public class Main {
    public static void main(String[] args) {
        int a = 10; double b = 2.0;
        var v = 2 + a / b * 5;
        // insert code here
    }
}
```

実行結果をtrueとするためには、5行目にどのコードを挿入しますか。（1つ選択）

A. `System.out.println(v == 1.2);`
B. `System.out.println(v == 30.0);`
C. `System.out.println(v == 30);`
D. `System.out.println(v == 27);`

**実施記録**

回答：D
正解：D
迷ったポイント：なし

<a id="q3-3"></a>
## 原本3-3

```java
public class Main {
    public static void main(String[] args) {
        int x = 5, y = 1;
        System.out.println(++x + x++ + y++ + ++y);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 14
B. 15
C. 16
D. 17

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q3-4"></a>
## 原本3-4

```java
public class Main {
    public static void main(String[] args) {
        byte data = 10;
        int[] array = {data, (int)10.5};
        System.out.println(array[0] < array[1]);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. true
B. false
C. 10
D. 4行目でコンパイルエラーが発生する

**実施記録**

回答：D
正解：B
迷ったポイント：`byte`はint配列の初期化子内で自動的に`int`へ拡大変換されるためコンパイルエラーにはならない点を見落とした。`array[0]=10, array[1]=(int)10.5=10`で`10 < 10`は`false`。

<a id="q3-5"></a>
## 原本3-5

```java
public class Main {
    public static void main(String[] args) {
        System.out.print(" value:" + 10 + 5);
        System.out.print(" value:" + 10 * 2 - 5);
        System.out.print(" value:" + 5 + 10 * 2);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `value:105 value:15 value:25`
B. `value:15 value:15 value:520`
C. `value: 105 value15 value:520`
D. 4行目でコンパイルエラーが発生する

**実施記録**

回答：D
正解：D
迷ったポイント：なし

<a id="q3-6"></a>
## 原本3-6

```java
public class Main {
    public static void main(String[] args) {
        int a = 10, b = 10, x, y;
        x = ++a;
        y = b--;
        int val = y < --x ? x++ : ++y;
        System.out.println("val:" + val);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. val:9
B. val:10
C. val:11
D. val:12

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q3-7"></a>
## 原本3-7

```java
public class Main {
    public static void main(String[] args) {
        int a = 5, b = 10;
        int x = a >= b ? a += 2 : a < b : b -= 2 ? b * 2;
        System.out.println(x);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 4行目でコンパイルエラーが発生する
B. 実行時例外がスローされる
C. 7が出力される
D. 8が出力される

**実施記録**

回答：A
正解：A
迷ったポイント：なし

<a id="q3-8"></a>
## 原本3-8

```java
public class Main {
    public static void main(String[] args) {
        String J = "J";
        String s1 = "Java";
        String s2 = J + "ava";
        String s3 = "Ja" + "va";
        String s4 = new String("Java");
        String s5 = new String("Java");
        // insert code here
    }
}
```

実行結果をtrueとするためには、9行目にどのコードを挿入しますか。（3つ選択）

A. `System.out.println(s1 == s2);`
B. `System.out.println(s1 == s3);`
C. `System.out.println(s4 == s5);`
D. `System.out.println(s4.equals(s5));`
E. `System.out.println(s1 == s2.intern());`

**実施記録**

回答：B, D, E
正解：B, D, E
迷ったポイント：なし

<a id="q3-9"></a>
## 原本3-9

```java
public class Main {
    public static void main(String[] args) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("Java");
        sb1.append("Java");
        String s1 = "Java";
        String s2 = sb2.toString();
        System.out.println(sb1 == sb2);
        System.out.println(sb1.equals(sb2));
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(sb1));
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. trueが1つ出力される
B. trueが2つ出力される
C. trueが3つ出力される
D. 7行目でコンパイルエラーが発生する
E. 12行目でコンパイルエラーが発生する

**実施記録**

回答：C
正解：A
迷ったポイント：実際の出力は`false/false/false/true/false`でtrueは1つだけだった。`StringBuilder`は`equals()`をオーバーライドしていないため中身ではなく参照比較になる点、`String.equals()`は比較相手が`String`型でなければ中身が同じでも`false`を返す点の2つを見落とした。

<a id="q3-10"></a>
## 原本3-10

次の記述に関して、コンパイルエラーになるものはどれですか。（1つ選択）

A. `Integer v1 = 100;`
B. `double v2 = Double.parseDouble(1.23);`
C. `long v3 = Integer.parseInt("300");`
D. `char v4 = "Duke".charAt(5);`
E. `boolean v6 = Boolean.parseBoolean("tRUE");`

**実施記録**

回答：D
正解：B
迷ったポイント：Dの`"Duke".charAt(5)`（"Duke"は長さ4）は実行時に`StringIndexOutOfBoundsException`が起きるだけでコンパイルは通る点を見落とした。実際のコンパイルエラーはBの`Double.parseDouble(1.23)`——`parseDouble`は`String`引数のメソッドで`double`リテラルをそのまま渡すことはできない。

<a id="q3-11"></a>
## 原本3-11

```java
public class Main {
    public static void main(String[] args) {
        var v = 10;
        if (v == 10.5)
            System.out.println(10.5);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 10.5が表示される
B. 何も表示されない
C. 3行目でコンパイルエラーが発生する
D. 4行目でコンパイルエラーが発生する

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q3-12"></a>
## 原本3-12

```java
public class Main {
    public static void main(String[] args) {
        int a = 1, b = 2, x = 5, y = 10;
        var v = 0;
        if ((++a == b) | ((x *= 2) == y)) v = x;
        else v = y;
        System.out.println("v:" + v);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. v:0が出力される
B. v:2が出力される
C. v:5が出力される
D. v:10が出力される

**実施記録**

回答：D
正解：D
迷ったポイント：なし

<a id="q3-13"></a>
## 原本3-13

実行方法：`>java Main Java`

```java
public class Main {
    public static void main(String[] args) {
        int length = args.length;
        if (length = 2) {
            System.out.println("Welcome");
        } else {
            System.out.println("Bye");
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 3行目でコンパイルエラーが発生する
B. 4行目でコンパイルエラーが発生する
C. Welcomeが出力される
D. Byeが出力される

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q3-14"></a>
## 原本3-14

```java
public class Main {
    public static void main(String[] args) {
        String[] array = {"H", "e", "l", "l", "o"};
        String text = "";
        if (array[0] == "H") {
            text += array[0];
        }
        if (!(array[2] == "e")) {
            text += "e";
        } else if (array[4].equalsIgnoreCase("O")) {
            text += "o";
        } else {
            text += "!";
        }
        System.out.println(text);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. Hが出力される
B. Heが出力される
C. H!が出力される
D. Heoが出力される
E. 8行目でコンパイルエラーが発生する

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q3-15"></a>
## 原本3-15

```java
public class Main {
    public static void main(String[] args) {
        var v1 = "Hello Java SE";
        var v2 = new StringBuilder("Hello Java SE");
        var v3 = v1.replace("Hello", "Hi");
        var v4 = v2.replace(0, 5, "Hi").toString();
        String s = "";
        if (v1 == v3) {
            if (v1 == v4) s = "v1, v3, v4";
        } else {
            if (v3.equals(v4))
                s = "v3, v4";
            s = "N/A";
        }
        System.out.println(s);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 何も出力されない
B. v1, v3, v4が出力される
C. v3, v4が出力される
D. N/Aが出力される

**実施記録**

回答：D
正解：D
迷ったポイント：なし

<a id="q3-16"></a>
## 原本3-16

```java
public class Main {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        if (x < 100)
            System.out.print("A");
        else if (x <= 100 && x >= 50)
            System.out.print("B");
        else
            System.out.println("C");
    }
}
```

次のプログラムを正しく説明しているものはどれですか。（2つ選択）

A. `>java Main 100` と実行すると、Bが出力される
B. `>java Main 50` と実行すると、Bが出力される
C. 100より大きい数で実行すると、Cが出力される
D. 50より小さい数で実行すると、Cが出力される
E. Bが出力されることはない

**実施記録**

回答：A, C
正解：A, C
迷ったポイント：なし

<a id="q3-17"></a>
## 原本3-17

switch構文の式に設定できるデータ型はどれですか。（4つ選択）

A. byte
B. short
C. long
D. Integer
E. String
F. Boolean

**実施記録**

回答：A, B, C, E
正解：A, B, D, E
迷ったポイント：`long`をswitchに使えると誤判定した（実際は`constant label of type long is not compatible with switch selector type long`でコンパイルエラー）。ラッパークラス`Integer`（自動アンボクシングで使用可）を見落とした。`Boolean`も同様に不可。

<a id="q3-18"></a>
## 原本3-18

```java
public class Main {
    public static void main(String[] args) {
        final char a = 'a';
        char k = 'k';
        int val = 0;
        switch ("Black".charAt(3)) {
            case a:
                val += 1; break;
            case a + 2:
                val += 2;
            case k:
                val += 3;
        }
        System.out.println(val);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 6が出力される
B. 5が出力される
C. 9行目でコンパイルエラーが発生する
D. 11行目でコンパイルエラーが発生する

**実施記録**

回答：B
正解：D
迷ったポイント：fallthroughのトレース自体（'c'にマッチ→case a+2でval+=2→breakなしでcase kに落ちてval+=3→合計5）は正しく追えていたが、`case k:`のkは`final`が付いていない変数であり、switchのcaseラベルはコンパイル時定数式でなければならないというルールに違反して11行目でコンパイルエラーになる点を見落とした（プログラムは実行されない）。

<a id="q3-19"></a>
## 原本3-19

```java
public class Main {
    public static void main(String[] args) {
        int i = 5;
        switch (i--) {
            case 4 -> i -= 2;
            case 3 -> { i *= 2; }
            default -> --i;
        }
        System.out.println(i);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 3が出力される
B. 2が出力される
C. 1が出力される
D. 6行目でコンパイルエラーが発生する

**実施記録**

回答：A
正解：A
迷ったポイント：なし

<a id="q3-20"></a>
## 原本3-20

```java
public class Main {
    public static void main(String[] args) {
        String country = "US FR JP".substring(3, 5);
        String currency = switch (country) {
            case "US": yield "USD";
            case "DE", "FR": yield "EUR";
            case "JP": yield "JPY";
            case "UK": yield "GBP";
        };
        System.out.println(currency);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. EURが出力される
B. 3行目でコンパイルエラーが発生する
C. 4行目でコンパイルエラーが発生する
D. 6行目でコンパイルエラーが発生する

**実施記録**

回答：C
正解：C
迷ったポイント：なし
