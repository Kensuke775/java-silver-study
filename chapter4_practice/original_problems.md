## 目次

**問題一覧**

- [原本4-1](#q4-1)
- [原本4-2](#q4-2)
- [原本4-3](#q4-3)
- [原本4-4](#q4-4)
- [原本4-5](#q4-5)
- [原本4-6](#q4-6)
- [原本4-7](#q4-7)
- [原本4-8](#q4-8)
- [原本4-9](#q4-9)
- [原本4-10](#q4-10)
- [原本4-11](#q4-11)
- [原本4-12](#q4-12)
- [原本4-13](#q4-13)
- [原本4-14](#q4-14)
- [原本4-15](#q4-15)
- [原本4-16](#q4-16)

<a id="q4-1"></a>
## 原本4-1

```java
public class Main {
    public static void main(String[] args) {
        int x = 10;
        while ( /* insert code here */ ) {
            System.out.print(x-- + " ");
        }
    }
}
```

次の実行結果とするためには、4行目にどのコードを挿入しますか。（1つ選択）

実行結果：`10 9 8 7 6 5 4 3 2 1 0`

A. `x <= 10`
B. `x <= 10 && x >= 0`
C. `x < 10 && x > 0`
D. `x <= 10 || x >= 0`
E. `--x < 10 && x > 0`

**実施記録**

回答：B
正解：B
迷ったポイント：なし

<a id="q4-2"></a>
## 原本4-2

次の記述に関して、コンパイルエラーになるものはどれですか。（2つ選択）

A.
```java
double d = 2.0;
while (d < 10) {
    System.out.print(d++);
}
```
B.
```java
int i = 100;
while (i - 10 < i) {
    System.out.print("*");
}
```
C.
```java
while (!true) {
    System.out.print("*");
}
```
D.
```java
int i = 3;
while ((i > 0 ? i++ : i--) < 3) {
    System.out.print("*");
}
```
E.
```java
String J = "J";
do {
    System.out.print("*");
} while (!("J".equals(J)))
```

**実施記録**

回答：C, E
正解：C, E
迷ったポイント：なし

<a id="q4-3"></a>
## 原本4-3

```java
public class Main {
    public static void main(String[] args) {
        int[] array = {10, 20, 30, 40, 50, 60};
        int count = array.length;
        while(count > 1) {
            count--;
            System.out.println(array[--count]);
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `ArrayIndexOutOfBoundsException`がスローされる
B. `60 40 20` が出力される
C. `50 30 10` が出力される
D. `60 50 40 30 20` が出力される

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q4-4"></a>
## 原本4-4

次の配列があります。

```java
int[] array = {1, 2, 3, 4, 5};
```

すべての要素を出力する記述はどれですか。（3つ選択）

A.
```java
for (int i = 0; i <= array.length ; ) {
    i++;
    System.out.print(array[i]);
}
```
B.
```java
int i = 0;
for (; i < array.length; i++) {
    System.out.print(array[i]);
}
```
C.
```java
for (int i : array)
    System.out.print(array[i]);
```
D.
```java
for (int i : array)
    System.out.print(i);
```
E.
```java
for (int i : array) {
    System.out.print(i);
    i++;
}
```

**実施記録**

回答：A, D, E
正解：B, D, E
迷ったポイント：Aを正しいと誤判定した。`i++`を先にしてから`print(array[i])`するため`array[0]`が一度も出力されず、最後は`array[5]`にアクセスして`ArrayIndexOutOfBoundsException`（検証：`2345`まで出力後に例外）。Bを見落とした——ふつうのインデックス方式で`array[0]`〜`array[4]`まで正しく全部出力できる素直な正解だった。

<a id="q4-5"></a>
## 原本4-5

実行方法：`>java Main 10 20 30`

```java
public class Main {
    public static void main(String[] args) {
        int v = 0, w = 0;
        for (String s : args) {
            v += ++w + Integer.parseInt(s);
        }
        System.out.println(v);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 66が出力される
B. 63が出力される
C. 12360が出力される
D. 110220330が出力される
E. 010120230が出力される

**実施記録**

回答：A
正解：A
迷ったポイント：なし

<a id="q4-6"></a>
## 原本4-6

```java
public class Main {
    public static void main(String[] args) {
        char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
        int a = 0, b = 1;
        for (a = chars.length - b; a > b; a -= 2) {
            System.out.print(chars[a]);
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. ecが出力される
B. dbが出力される
C. fdが出力される
D. fdbが出力される

**実施記録**

回答：D
正解：C
迷ったポイント：`a=1`のとき条件`a > b`（`1 > 1`）は`false`でループが終わる点を見落とし、`a >= b`と混同してもう1文字（`b`＝chars[1]='b'）まで出力すると誤った。実際の出力は`fd`のみ（検証済み）。

<a id="q4-7"></a>
## 原本4-7

```java
public class Main {
    public static void main(String[] args) {
        String[] array = {"A ", "B "};
        for (final String s : array) {
            s += s.toLowerCase();
            System.out.print(s);
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `a a b b` が出力される
B. `A a B b` が出力される
C. `a b` が出力される
D. `A B a b` が出力される
E. コンパイルエラーが発生する

**実施記録**

回答：E
正解：E
迷ったポイント：なし

<a id="q4-8"></a>
## 原本4-8

```java
public class Main {
    public static void main(String[] args) {
        do System.out.print("*");
        while (false);

        while (1 == 2)
            System.out.print("*");
    }
}
```

このプログラムを正常にコンパイルするためには、どのような修正を行いますか。（1つ選択）

A. 何も変更する必要はない
B. doはブロックの`{}`を省略できないため、3行目を以下に変更する
   `do { System.out.print("*"); }`
C. 4行目のfalseをtrueにする
D. 5行目で`int i = 1;`を宣言し、6行目の`1`を`i`にする

**実施記録**

回答：D
正解：D
迷ったポイント：なし

<a id="q4-9"></a>
## 原本4-9

```java
public class Main {
    public static void main(String[] args) {
        int x = 10;
        while (x != 0) {
            System.out.print(x);
            x--;
        }
    }
}
```

3〜7行目を置き換えたときに、元のプログラムと同じ実行結果になるものはどれですか。（2つ選択）

A.
```java
for (int x : 10) {
    System.out.print(x);
}
```
B.
```java
int x;
for (x = 10; !(x == 0); --x) {
    System.out.print(x);
}
```
C.
```java
for (int x = 10; x < 10; x--) {
    System.out.print(x);
}
```
D.
```java
for (int x = 10; x > 0; ) {
    x--;
    System.out.print(x);
}
```
E.
```java
int x = 10;
for (; x > 0; ) {
    System.out.print(x);
    x--;
}
```

**実施記録**

回答：D, E
正解：B, E
迷ったポイント：Dを誤って含めた。「先にx--してから print」の順（原文は「先にprint、後でx--」）で、実際に検証すると`9876543210`となり原文の`10987654321`とズレる別物だった。Bを見落とした——`!(x==0)`は`x!=0`と同義で、print→--xの順序も原文と同じなので完全一致する。

<a id="q4-10"></a>
## 原本4-10

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 2; ) {
            i++;
            int j;
            for (j = 0; j < i; j++) {
            }
            System.out.print(j + " ");
        }
        System.out.println(":" + i);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 8行目でコンパイルエラーが発生する
B. 10行目でコンパイルエラーが発生する
C. `0 1 2 :3` が出力される
D. `1 2 3 :3` が出力される
E. 次が出力される
   ```
   :0
   0 :1
   ```
F. 次が出力される
   ```
   0 :1
   0 1 :2
   ```

**実施記録**

回答：A
正解：B
迷ったポイント：8行目の`j`は内側for文の初期化`j=0`が必ず1回実行されるため未代入の問題はなく、実際にコンパイルエラーになるのは10行目——`i`はfor文の初期化部で宣言された変数のためスコープがそのfor文の中に限定され、for文の外の10行目で参照するとエラーになる点を見落とした（検証：`シンボルを見つけられません: 変数i`）。

<a id="q4-11"></a>
## 原本4-11

```java
public class Main {
    public static void main(String[] args) {
        int[][] array = new int[2][];
        array[0] = new int[3];
        array[1] = new int[]{1, 2};
        for (int i = 0; i < 2; i++) {
            array[0][i] = i + 1;
        }
        for (int ar[] : array) {
            for (int a : ar)
                System.out.print(a);
            System.out.print("_");
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `120_12_` が出力される
B. `111_12_` が出力される
C. `1_1_1_1_2` が出力される
D. `12_12_`が出力される
E. `ArrayIndexOutOfBoundsException`がスローされる

**実施記録**

回答：A
正解：A
迷ったポイント：なし

<a id="q4-12"></a>
## 原本4-12

```java
public class Main {
    public static void main(String[] args) {
        char[] chars = new char[]{'A', 'P', 'P', 'L', 'E'};
        int value = 0;
        for (char c : chars) {
            value += switch (c) {
                case 'A': yield 1;
                case 'P': yield 2;
                default: yield 3;
            };
        }
        System.out.println(value);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 0が出力される
B. 3が出力される
C. 11が出力される
D. 22が出力される

**実施記録**

回答：C
正解：C
迷ったポイント：なし

<a id="q4-13"></a>
## 原本4-13

```java
public class Main {
    public static void main(String[] args) {
        String text = "Hello";
        int index = 1;
        while (index < text.length()) {
            switch (text.charAt(index)) {
                case 'H':
                    System.out.print(0);
                case 'e':
                    System.out.print(1);
                    break;
                case 'l':
                    System.out.print(2);
                    index++;
                    continue;
                case 'o':
                    System.out.print(3);
            }
            index++;
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. 1が出力される
B. 1223が出力される
C. 01223が出力される
D. 011223が出力される
E. 無限ループになる
F. コンパイルエラーが発生する

**実施記録**

回答：F
正解：B
迷ったポイント：switch内でcontinueを使うこと自体は合法（continueは常に一番近いループに効き、switchには影響されない）で、コンパイルエラーにはならない点を見落とした。実際の出力は`1223`（indexが1から始まるため0番目の'H'は一度もチェックされない）。

<a id="q4-14"></a>
## 原本4-14

```java
public class Main {
    public static void main(String[] args) {
        String[][] letters = {{"A ", "B ", "C ", "D "}
                             , {"E ", "F ", "G ", "H "}};
        for (String[] letter : letters) {
            // insert code here
        }
    }
}
```

次の実行結果とするには、6行目にどのコードを挿入しますか。（2つ選択）

実行結果：`A B C D E F G H`

A.
```java
for (String l : letters) {
    System.out.println(l);
}
```
B.
```java
for (String[] l : letters) {
    System.out.println(l);
}
```
C.
```java
for (String l : letter) {
    System.out.print(l);
}
```
D.
```java
for (int i = 0; i < letter.length; i++) {
    System.out.print(letter[i]);
}
```
E.
```java
for (int i = 0; i < letter[i].length(); i++) {
    System.out.print(letter[i]);
}
```

**実施記録**

回答：C, D
正解：C, D
迷ったポイント：なし（原文コードで外側の2次元配列が`letters`、拡張for文で取り出した1行分の変数が`letter`という紛らわしい命名になっている点に注意——タイプミスではなく原文通り）。

<a id="q4-15"></a>
## 原本4-15

```java
public class Main {
    public static void main(String[] args) {
        String[] cities = {"Paris ", null, "Tokyo ", "Rio ", null};
        for (String city : cities) {
            int count = 0;
            if (city == null) {
                count++;
                continue;
            }
            System.out.print(city);
        }
        System.out.println(", N/A:" + count);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Paris Tokyo Rio`が出力される
B. `Paris null Tokyo Rio null , N/A:2` が出力される
C. `Paris Tokyo Rio , N/A:2` が出力される
D. `ArrayIndexOutOfBoundsException`がスローされる
E. コンパイルエラーが発生する

**実施記録**

回答：E
正解：E
迷ったポイント：なし

<a id="q4-16"></a>
## 原本4-16

```java
public class Main {
    public static void main(String[] args) {
        String[][] str = {{"A", "B", "C"}, {"D", "E", "F"}};
        outer:
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                if (i == 1) break outer;
                if (j == 2) continue outer;
                System.out.print(str[i][j]);
            }
        }
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. ABが出力される
B. DEが出力される
C. ADが出力される
D. ABDEが出力される

**実施記録**

回答：A
正解：A
迷ったポイント：なし
