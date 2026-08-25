## 目次

**問題一覧**

- [原本2-1](#q2-1)
- [原本2-2](#q2-2)
- [原本2-3](#q2-3)
- [原本2-4](#q2-4)
- [原本2-5](#q2-5)
- [原本2-6](#q2-6)
- [原本2-7](#q2-7)
- [原本2-8](#q2-8)
- [原本2-9](#q2-9)
- [原本2-10](#q2-10)
- [原本2-11](#q2-11)
- [原本2-12](#q2-12)
- [原本2-13](#q2-13)
- [原本2-14](#q2-14)
- [原本2-15](#q2-15)

<a id="q2-1"></a>
## 原本2-1

次の記述に関して、コンパイルエラーになるものはどれですか。（2つ選択）

A. `int a = 100;`
B. `int b = "100";`
C. `int c = 0B1111011;`
D. `int d = 0123;`
E. `int e = 0xcafe;`
F. `int f = null;`

**実施記録**

回答：B, F
正解：B, F
迷ったポイント：なし

<a id="q2-2"></a>
## 原本2-2

次の記述に関して、コンパイルエラーになるものはどれですか。（2つ選択）

A. `float f1 = 3.14F, float f2 = 0.123F;`
B. `double d1, d2 = 0.123;`
C. `int i1 = -10_000;`
D. `int i2 = 10_000_000_000;`
E. `long l1 = 1_000_000_000;`
F. `long l2 = 1L;`

**実施記録**

回答：A, D
正解：A, D
迷ったポイント：なし

<a id="q2-3"></a>
## 原本2-3

次の記述に関して、コンパイルエラーになるものはどれですか。（3つ選択）

A. `byte b1 = 0b_1_100_100;`
B. `int i1 = 0_1234____567;`
C. `long l1 = 0x95_02F_900L;`
D. `float f1 = 1._234567;`
E. `float f2 = 3.1415_F;`
F. `double d1 = 1.23e1_23;`

**実施記録**

回答：A, D, E
正解：A, D, E
迷ったポイント：なし

<a id="q2-4"></a>
## 原本2-4

次の記述に関して、コンパイルが成功するものはどれですか。（2つ選択）

A. `c1 = 'a', c2 = 'b';`
B. `char c3 = 'a';`
C. `char c4 = "b";`
D. `String s1 = true;`
E. `String s2 = "¥"";`
F. `String s3 = '¥n';`

**実施記録**

回答：B, E
正解：B, E
迷ったポイント：なし

<a id="q2-5"></a>
## 原本2-5

次の出力を行うプログラムの処理として、適切なものはどれですか。（2つ選択）

出力結果：
```
<html>
    <body>
          <h1>"Java Silver"</h1>
    </body>
</html>
```

```java
public class Main {
    public static void main(String[] args) {
        String text = "";

        System.out.println(text);
    }
}
```

A. 4行目で次の代入を行う。
```
text = """
        <html>¥n<body>¥n<h1>"Java Silver"</h1>¥n</body>¥n</html>
       """;
```
B. 4行目で次の代入を行う。
```
text = """ <html>
              <body>
                  <h1>"Java Silver"</h1>
               </body>
        </html>
        """;
```
C. 4行目で次の代入を行う。
```
text = """
        <html>
              <body>
                 <h1>"Java Silver"</h1>
              </body>
        </html> """;
```
D. 4行目で次の代入を行う。
```
text = """
        <html>¥
              <body>¥
                  <h1>¥"Java Silver¥"</h1>¥
              </body>¥
        </html>
       """;
```
E. 5行目を次のように変更する。
```
System.out.println("""
        <html>
            <body>
                  <h1>¥"Java Silver¥"</h1>
            </body>
        </html>
        """);
```
F. 5行目を次のように変更する。
```
System.out.println(
                    "<html>" +
                    "   <body>" +
                    "       <h1>¥"Java Silver¥"</h1>" +
                    "   </body>" +
                    "</html>");
```

**実施記録**

回答：C, E
正解：C, E
迷ったポイント：なし

<a id="q2-6"></a>
## 原本2-6

次のうち識別子として使用できないものはどれですか。（1つ選択）

A. `_var`
B. `truefalse`
C. `$int`
D. `Int`
E. `Java17`
F. `17Java`

**実施記録**

回答：F
正解：F
迷ったポイント：なし

<a id="q2-7"></a>
## 原本2-7

次のvarを使用した記述に関して、コンパイルが成功するものはどれですか。（2つ選択）

A. `var v1, v2;`
B. `var v3 = 1.23, var v4 = 3.14;`
C. `var v5 = 1996;`
D. `final var v6 = "";`
E. `var v7 = null;`
F. `var v8 = new int[3] {10, 20, 30};`

**実施記録**

回答：C, D
正解：C, D
迷ったポイント：なし

<a id="q2-8"></a>
## 原本2-8

```java
public class Main {
    public static void main(String[] args) {
        var a = 10; var b = 20;
        var c = "30";
        System.out.println(c + b + a);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 3行目でコンパイルエラーが発生する
B. 5行目でコンパイルエラーが発生する
C. `60`
D. `3030`
E. `302010`

**実施記録**

回答：E
正解：E
迷ったポイント：なし

<a id="q2-9"></a>
## 原本2-9

```java
public class Main {
    public static void main(final String[] args) {
        final var i1 = 10;
        final int i2 = i1;
        final String s;
        s = "James";
        s = "Duke";
        System.out.println(s);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 2行目でコンパイルエラーが発生する
B. 4行目でコンパイルエラーが発生する
C. 5行目でコンパイルエラーが発生する
D. 6行目でコンパイルエラーが発生する
E. 7行目でコンパイルエラーが発生する
F. `James`が出力される

**実施記録**

回答：D
正解：E
迷ったポイント：blank final（初期化子なしのfinal変数）は宣言後に1回だけ代入できる、という点を見落とした。6行目の`s = "James"`はその1回目でOK、7行目の`s = "Duke"`が2回目の代入となりそこでコンパイルエラー。

<a id="q2-10"></a>
## 原本2-10

```java
public class Main {
    public static void main(String[] args) {
        double[] array = new double[3];
        array[1] = 10.5; array[2] = 20.0; array[3] = 30.1;
        System.out.println(array[0] + ":" + array[1] + ":" + array[2]);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 4行目でコンパイルエラーが発生する
B. `10.5 : 20.0 : 30.1` が出力される
C. `0.0 : 10.5 : 20.0` が出力される
D. `ArrayIndexOutOfBoundsException`がスローされる

**実施記録**

回答：A
正解：D
迷ったポイント：配列の添字範囲チェックはコンパイル時ではなく実行時に行われる点を見落とした。`array`は長さ3（有効index 0〜2）なので`array[3]`への代入はコンパイルは通り、実行時に`ArrayIndexOutOfBoundsException`がスローされる。

<a id="q2-11"></a>
## 原本2-11

```java
public class Main {
    public static void main(String[] args) {
        int num, iAry[];
        String[] sAry = {"Apple", "Lemon"};
        boolean bAry[] = new boolean[];
        num = 100;
        iAry = new int[] {10, 20, 30, num};
        System.out.println(iAry.length + " : " + sAry.length + " : " + bAry.length);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 3行目でコンパイルエラーが発生する
B. 5行目でコンパイルエラーが発生する
C. 7行目でコンパイルエラーが発生する
D. `4 : 2 : 0` が出力される
E. `3 : 2 : 0` が出力される

**実施記録**

回答：C
正解：B
迷ったポイント：5行目の`boolean bAry[] = new boolean[];`（サイズも初期化子もない不正な配列生成式）でコンパイルエラーになる点を見落とし、7行目を誤って選んだ。7行目自体（`num`を使った配列初期化）は問題なく、そもそも5行目で止まるため7行目には到達しない。

<a id="q2-12"></a>
## 原本2-12

次の記述に関して、コンパイルが成功するものはどれですか。（4つ選択）

A. `String s1[][] = { {"a", "b"}, {"d", "e"} };`
B. `String[][] s2 = new String[3][];`
C. `String[][] s3 = new String[][2];`
D. `String s4[][] = new String[][]{};`
E. `String[][] s5 = {{}, {}};`

**実施記録**

回答：A, B, C, E
正解：A, B, D, E
迷ったポイント：Cの`new String[][2]`を正しいと誤判定した（多次元配列生成は左側の次元から順に指定する必要があり、1次元目を省略して2次元目だけ指定するのは不可）。Dの`new String[][]{}`（空の2次元配列）を見落とした。

<a id="q2-13"></a>
## 原本2-13

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(args[0] + args[1] + args[2]);
    }
}
```

実行結果：`Java17 Hello World!`

次の実行結果とするために、適切なものはどれですか。（2つ選択）

A. `>java Main Java 17 Hello World!`
B. `>java Main Java "17 " "Hello World!"`
C. `>java Main "Java" "17 " "¥"Hello World!¥""`
D. `>java Main.java Java 17 " Hello" " World!"`
E. `>java Main.java Java 17 " Hello World!"`
F. `>java Main.java "Java17 Hello World!"`

**実施記録**

回答：B, E
正解：B, E
迷ったポイント：なし

<a id="q2-14"></a>
## 原本2-14

```java
public class Main {
    public static void main(String[] args) {
        String s = "Java 17";
        s.append(" Silver");
        int length = s.length();
        int index = s.indexOf("Gold");
        System.out.println(length + " : " + index);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `7 : -1` が出力される
B. `14 : 0` が出力される
C. 4行目でコンパイルエラーが発生する
D. 6行目でコンパイルエラーが発生する

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q2-15"></a>
## 原本2-15

```java
public class Main {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("Gold").append("Silver");
        sb.insert(4, " ").delete(4, 5);
        sb.substring(0, 4);
        System.out.println(sb);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Gold`が出力される
B. `Silver`が出力される
C. `GoldSilver`が出力される
D. `Gold Silver`が出力される

**実施記録**

回答：C
正解：C
迷ったポイント：なし
