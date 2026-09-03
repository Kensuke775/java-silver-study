## 目次

**問題一覧**

- [問題ex8-1](#qex8-1)
- [問題ex8-2](#qex8-2)
- [問題ex9-1](#qex9-1)
- [問題ex9-2](#qex9-2)
- [問題ex9-3](#qex9-3)
- [問題ex9-4](#qex9-4)
- [問題ex9-5](#qex9-5)
- [問題ex9-6](#qex9-6)
- [問題ex10-1](#qex10-1)
- [問題ex10-2](#qex10-2)
- [問題ex10-α](#qex10-a)
- [問題ex10-β](#qex10-b)
- [問題ex11-1](#qex11-1)
- [問題ex11-2](#qex11-2)
- [問題ex11-3](#qex11-3)
- [問題ex11-4](#qex11-4)
- [問題ex9-α](#qex9-a)
- [問題ex9-β](#qex9-b)
- [問題ex13-1](#qex13-1)
- [問題ex13-2](#qex13-2)
- [問題ex13-3](#qex13-3)
- [問題ex13-4](#qex13-4)
- [問題ex14-1](#qex14-1)
- [問題ex14-2](#qex14-2)
- [問題ex14-3](#qex14-3)
- [問題ex14-4](#qex14-4)
- [問題ex15-1](#qex15-1)
- [問題ex15-2](#qex15-2)

<a id="qex8-1"></a>
## 問題ex8-1

**要点**

- `+`演算子は左から右へ順番に評価される(左結合)
- 文字列 + 数値が現れた時点で、それ以降の`+`はすべて文字列連結として扱われる(一度文字列になったら、それ以降は数値の足し算に戻らない)
- まだ数値同士の`+`が続いている間は普通の算術加算が行われる

```java
var a = 10; var b = 20;
var c = "30";
System.out.println(c + b + a);
```

正しい出力を1つ選んでください。

A. `60`

B. `3020`

C. `302010`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：C
迷ったポイント：`c + b + a`のcが文字列であることから、c+bの時点で以降すべて文字列連結になることを見落とし、全部数値として足し算されると誤解した(60)。

---

<a id="qex8-2"></a>
## 問題ex8-2

**要点**

- 括弧で囲むと、その部分が先に評価される(通常の演算子の優先順位ルール通り)
- `a + b + c`と`(a + b) + c`は評価順序が同じなので結果も同じになる
- `a + (b + c)`は括弧内(数値+文字列)が先に文字列連結され、その結果に`a`が連結されるため、他の2パターンと異なる結果になる

```java
var a = 10; var b = 20;
var c = "30";
System.out.println(a + b + c);
System.out.println((a + b) + c);
System.out.println(a + (b + c));
```

3行の出力の組み合わせとして正しいものを1つ選んでください。

A.
```
3030
3030
102030
```

B.
```
3030
3030
3030
```

C.
```
102030
102030
102030
```

D.
```
3030
102030
3030
```

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex9-1"></a>
## 問題ex9-1

**要点**

- `final` ローカル変数は宣言時に初期化子を付けなくてもよい(blank final)
- ただし実際に値を代入できるのは、どの実行経路でも通算1回だけ
- 条件分岐なしで単純に2回代入すると、コンパイラは「複数回代入され得る」と判断してコンパイルエラーにする

以下のコードをコンパイルするとどうなるか。

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

A. コンパイルエラーは発生せず、実行結果として `Duke` が出力される
B. 7行目でコンパイルエラーになる。blank final(初期化子なし宣言)であっても、`s` への代入は1回しか許されないため
C. 6行目でコンパイルエラーになる。`final` 変数は宣言と同時に初期化子を書かなければならないため
D. 4行目でコンパイルエラーになる。`final var` という組み合わせは使用できないため

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex9-2"></a>
## 問題ex9-2

**要点**

- blank final変数は、値が代入される「前」に読み取られる可能性が少しでもあると、その時点でコンパイルエラーになる
- switch文でcaseごとに代入していても、default節がないと「levelがどのcaseにも一致しない」という経路をコンパイラは排除できない
- エラーが出る場所はswitch文自体ではなく、未初期化の可能性がある変数を実際に読み取る箇所(println)になる

以下のコードをコンパイルするとどうなるか。

```java
public class Main {
    public static void main(final String[] args) {
        final int level = 2;
        final String rank;
        switch (level) {
            case 1:
                rank = "Bronze";
                break;
            case 2:
                rank = "Silver";
                break;
            case 3:
                rank = "Gold";
                break;
        }
        System.out.println(rank);
    }
}
```

A. コンパイルは通り、実行結果は `Silver` が出力される
B. `System.out.println(rank);` の行でコンパイルエラーになる。`default`節がなく、`level`がどの`case`にも一致しない可能性をコンパイラが排除できないため
C. `switch (level)` の行でコンパイルエラーになる。`final`変数への代入は`switch`文の中では行えないため
D. `case 2:` の`rank = "Silver";`の行でコンパイルエラーになる。blank finalは複数の`case`から代入できないため

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex9-3"></a>
## 問題ex9-3

**要点**

- `if`〜`else if`〜`else`で全経路を網羅していれば、各分岐でblank finalに1回ずつ代入しても問題ない(switch文でdefaultがなかったex9-2との対比)
- 「複数の場所で代入している=即エラー」ではなく、「実行時にどのパスを通っても、代入されるのはちょうど1回」かどうかが判定基準
- `else`が漏れなく存在する(=どんな`score`の値でもどこかの分岐に必ず入る)ことがポイント
- 補足: コンパイラはscoreの実際の値(final int score = 82)を計算してどの分岐に入るか予測しているわけではなく、あくまで分岐構造が漏れなく揃っているかだけを見て判定している

以下のコードをコンパイル・実行するとどうなるか。

```java
public class Main {
    public static void main(final String[] args) {
        final int score = 82;
        final String grade;
        if (score >= 90) {
            grade = "A";
        } else if (score >= 70) {
            grade = "B";
        } else {
            grade = "C";
        }
        System.out.println(grade);
    }
}
```

A. コンパイルエラーになる。`grade`が3か所(A/B/Cの各分岐)で代入されており、blank finalは1回しか代入できないため
B. コンパイルエラーになる。`else`節があっても、コンパイラは`if`/`else if`の条件が実行時にどうなるか分からないため
C. コンパイル・実行とも問題なく完了し、`B`が出力される
D. コンパイルは通るが、実行時に`grade`が未代入として例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：正解自体は合っていたが、理由づけを「final score=82と値が確定しているのでコンパイラがそれを見て予測する」としており、実際は値の予測ではなく分岐構造(elseまで網羅されているか)による判定である点を補足説明した

---

<a id="qex9-4"></a>
## 問題ex9-4

**要点**

- `for`/`while`ループの本体は、コンパイラから見ると「0回以上、何度でも実行され得るもの」として扱われる
- ループの回数が実質1回だけになる書き方(`i < 1`など)であっても、その事実はコンパイラの確定代入判定には影響しない
- そのため、ループ内でblank finalに代入すると「複数回代入される可能性」と「一度も実行されず未代入のままになる可能性」の両方が同時に指摘されることがある

以下のコードをコンパイルするとどうなるか。

```java
public class Main {
    public static void main(final String[] args) {
        final int total;
        for (int i = 0; i < 1; i++) {
            total = 100;
        }
        System.out.println(total);
    }
}
```

A. コンパイルは通り、実行結果として`100`が出力される
B. コンパイルエラーになる。`for`ループの本体は「複数回実行され得るもの」として扱われるため`total`への代入が複数回になり得ると判断され、同時に一度もループに入らず未代入のまま使われる可能性も指摘される
C. コンパイルエラーになる。ループの条件式`i < 1`の評価結果がboolean型ではないため
D. コンパイルは通るが、実行時に`total`が未初期化のまま使われて例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(語尾に「ですか？」と自信なさげだったが正解)

---

<a id="qex9-5"></a>
## 問題ex9-5

**要点**

- ex9-3では、外側の`if`〜`else if`〜`else`に`else`があれば「全経路網羅」とみなされコンパイルが通った
- しかし「全経路網羅」は入れ子(ネスト)の内側のifにも同じ完全性が要求される。外側だけ`else`が揃っていても、内側の`if`に対応する`else`がなければ意味がない
- `score`は`readScore()`というメソッド呼び出しの戻り値であり、コンパイル時定数ではない点に注意(値そのものをコンパイラが特別扱いする余地がない、素直な例)

以下のコードをコンパイルするとどうなるか。

```java
public class Main {
    public static void main(final String[] args) {
        final int score = readScore();
        final String grade;
        if (score >= 90) {
            grade = "A";
        } else if (score >= 70) {
            if (score >= 75) {
                grade = "B+";
            }
        } else {
            grade = "C";
        }
        System.out.println(grade);
    }

    private static int readScore() {
        return 82;
    }
}
```

A. コンパイル・実行とも問題なく完了し、`B+`が出力される
B. `System.out.println(grade);`の行でコンパイルエラーになる。`else if (score >= 70)`の内側にある`if (score >= 75)`にelseがなく、`score`が70以上75未満のときに`grade`が未代入のまま外へ抜けてしまうため
C. コンパイルエラーになる。ネストした`if`文の中で`final`変数へ代入することはできないため
D. コンパイルは通るが、実行時に`grade`が未代入のまま使われて例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：「外側のif/else-if/elseにelseがある=全体として網羅済み」と判断してしまった。実際は網羅性のチェックはネストの各階層で独立に必要で、内側のif(score>=75)にelseがないため、70以上75未満の経路でgradeが未代入になり得る点を見落とした

---

<a id="qex9-6"></a>
## 問題ex9-6

**要点**

- ex9-4では、`for`ループの本体は「複数回実行され得るもの」として扱われ、ループ回数が実質1回でもblank finalの代入はエラーになった
- ところが`do`〜`while`ループでは、ループの継続条件がリテラルの`false`のとき、コンパイラは特別扱いをして「このループ本体はちょうど1回だけ実行される」と認識できる
- これは`while(true)`のような定数条件をコンパイラが特別扱いするのと同じ仕組みの応用

以下のコードをコンパイルするとどうなるか。

```java
public class Main {
    public static void main(final String[] args) {
        final int count;
        do {
            count = 100;
        } while (false);
        System.out.println(count);
    }
}
```

A. コンパイルエラーになる。`do`〜`while`の本体も`for`と同様に「複数回実行され得るもの」として扱われ、`count`への代入が複数回になり得ると判断されるため
B. コンパイル・実行とも問題なく完了し、`100`が出力される。継続条件が定数`false`であるため、コンパイラはこの`do`〜`while`の本体がちょうど1回だけ実行されると判断できる
C. コンパイルエラーになる。`while (false)`という書き方自体が文法エラーであるため
D. コンパイルは通るが、`while (false)`により本体が一度も実行されず、`count`が未初期化のまま使われて実行時に例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし(一発正解)

---

<a id="qex10-1"></a>
## 問題ex10-1

**要点**

- 配列の有効なインデックスは`0`から`length - 1`まで。それ以外(負の値、`length`以上)へのアクセスは、インデックスがリテラルであってもコンパイル時にはチェックされず、実行時に`ArrayIndexOutOfBoundsException`が発生する
- 範囲外アクセスは、代入(`array[3] = ...`)でも参照(`array[3]`を読む)でも同様に例外が発生する
- 未代入の要素は宣言した型のデフォルト値のまま(`double`なら`0.0`)

```java
double[] array = new double[3];
array[1] = 10.5; array[2] = 20.0; array[3] = 30.1;
System.out.println(array[0] + " : " + array[1] + " : " + array[2]);
```

A. `0.0 : 10.5 : 20.0`と出力される

B. コンパイルエラーになる

C. `ArrayIndexOutOfBoundsException`が実行時に発生する

D. `array[3] = 30.1;`の行は単に無視され、`30.1`は代入されない

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex10-2"></a>
## 問題ex10-2

**要点**

- 未代入の`double`配列要素はデフォルト値`0.0`のまま
- 一部の要素だけ代入しても、代入していない要素には影響しない

上のコードから`array[3] = 30.1;`の行だけを削除して実行した場合の出力を1つ選んでください。

A. `10.5 : 10.5 : 20.0`

B. `0.0 : 10.5 : 20.0`

C. `null : 10.5 : 20.0`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex10-a"></a>
## 問題ex10-α

**要点(別角度: 負のインデックス)**

- 負のインデックスも配列の範囲外であり、正の大きすぎるインデックス同様、実行時に`ArrayIndexOutOfBoundsException`が発生する
- Javaの配列に「末尾から数える」負インデックスの特別扱いは存在しない(Pythonなどとは異なる)

```java
double[] array = new double[3];
array[-1] = 5.0;
System.out.println(array[0]);
```

A. `5.0`が出力される

B. `0.0`が出力される

C. `array[-1] = 5.0;`の時点でコンパイルエラーになる

D. `array[-1] = 5.0;`の時点で`ArrayIndexOutOfBoundsException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex10-b"></a>
## 問題ex10-β

**要点(別角度: 境界値ちょうどのケース)**

- `array.length - 1`は常に有効な最後のインデックス
- `array.length`はどんな配列でも必ず無効なインデックス(1つ超えている)

```java
double[] array = new double[3];
System.out.println(array[array.length - 1]);
System.out.println(array[array.length]);
```

A. `0.0`と`0.0`が出力される

B. 1行目で`0.0`が出力された後、2行目で`ArrayIndexOutOfBoundsException`が発生する

C. 両方ともコンパイルエラーになる

D. 1行目の時点で`ArrayIndexOutOfBoundsException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex11-1"></a>
## 問題ex11-1

**要点**

- Javaでは`[]`を型の後(`int[] iAry`)にも変数名の後(`int iAry[]`、C言語スタイル)にも書ける。どちらも同じ意味
- 1つの宣言文で複数の変数を書く場合、`[]`はその変数名にだけかかる。他の変数には影響しない(`int num, iAry[];`なら`num`はただの`int`、`iAry`だけが`int[]`)

```java
int num, iAry[];
num = 5;
iAry = new int[]{1, 2, 3};
System.out.println(num + " " + iAry.length);
```

A. コンパイルエラーになる(numとiAryは両方とも配列型でなければならない)

B. `[5] 3`が出力される

C. numは配列型なのでコンパイルエラーになる

D. `5 3`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex11-2"></a>
## 問題ex11-2

**要点**

- 配列初期化子の省略記法`{...}`(`new int[]{...}`の`new int[]`を省略した書き方)は、変数宣言と同時の場合のみ使える。既存の変数への再代入では使えない

```java
String[] sAry = {"Apple", "Lemon"};
sAry = {"Grape", "Peach"};
System.out.println(sAry.length);
```

A. コンパイルエラーになる

B. `2`が出力される

C. `sAry`が`null`になる

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：A
迷ったポイント：配列初期化子の省略記法`{...}`が宣言時専用であり、既存変数への再代入には使えない(明示的に`new String[]{...}`と書く必要がある)ことを知らなかった。

---

<a id="qex11-3"></a>
## 問題ex11-3

**要点**

- `new boolean[]`のように、サイズも初期化子も指定しない配列生成はコンパイルエラー

```java
boolean bAry[] = new boolean[];
System.out.println(bAry.length);
```

A. `0`が出力される

B. `false`が出力される

C. コンパイルエラーになる

D. `NullPointerException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex11-4"></a>
## 問題ex11-4

**要点**

- 型レベルの`[]`と変数名レベルの`[]`は足し算される。`int[] a, b[];`なら`a`は`int[]`(1次元)、`b`は`int[][]`(2次元)になる

```java
int[] a, b[];
a = new int[]{1, 2};
b = new int[][]{{1, 2}, {3, 4, 5}};
System.out.println(a.length + " " + b.length + " " + b[1].length);
```

A. コンパイルエラーになる

B. `2 2 3`

C. `2 2 5`

D. `2 2 2`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex9-a"></a>
## 問題ex9-α

**要点**

- `final`変数は一度だけ値を代入できる。2回目の代入はコンパイルエラーになる
- 宣言時に初期化しない`final`変数(blank final)も許される。ただし使用前に必ず一度だけ代入されていることをコンパイラが保証できる必要がある
- `final var`のように`final`と`var`は併用できる

```java
public static void main(final String[] args) {
    final var i1 = 10;
    final int i2 = i1;
    final String s;
    s = "James";
    s = "Duke";
    System.out.println(s);
}
```

A. `James`が出力される

B. `Duke`が出力される

C. コンパイルエラーになる

D. `final var i1 = 10;`の時点でコンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex9-b"></a>
## 問題ex9-β(難題)

**要点**

- コンパイラの「一度だけ代入されているか」の判定は、実行時の実際の動作ではなく、構文上の静的解析(definite assignment analysis)で行われる
- if/elseの全分岐で確実に1回代入されるケースはOK。forループ内での代入は、実行回数に関わらずコンパイラが「0回・複数回実行される可能性」を排除できないためNG

```java
// コードA
final int x;
if (args.length > 0) {
    x = 1;
} else {
    x = 2;
}
System.out.println(x);
```

```java
// コードB
final int y;
for (int i = 0; i < 1; i++) {
    y = i;
}
System.out.println(y);
```

A. 両方ともコンパイルエラーになる

B. コードAは正常にコンパイルされるが、コードBはコンパイルエラーになる

C. 両方とも正常にコンパイルされる

D. コードAはコンパイルエラーになるが、コードBは正常にコンパイルされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex13-1"></a>
## 問題ex13-1

**要点**

- `main`メソッドの`args`は、実行時に渡されたコマンドライン引数を格納する`String`配列
- 引数を1つも渡さずに実行しても、`args`は`null`にはならず「長さ0の空配列」になる
- そのため`args[0]`のように存在しない添字へアクセスすると、コンパイルは通るが実行時に配列外アクセスの例外がスローされる

以下のコードを、コマンドライン引数を1つも指定せずに`java Main`として実行するとどうなるか。

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(args[0] + args[1] + args[2]);
    }
}
```

A. コンパイルエラーになる。呼び出し時に十分な数の引数が渡されるかどうかは、コンパイル時に検証されるため
B. 実行時に`NullPointerException`がスローされる。引数を渡さなかった場合、`args`自体が`null`になるため
C. 実行時に`ArrayIndexOutOfBoundsException`がスローされる。引数を渡さなかった場合でも`args`は長さ0の配列であり、`args[0]`へのアクセスが範囲外になるため
D. コンパイル・実行とも問題なく完了し、何も出力されずに正常終了する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex13-2"></a>
## 問題ex13-2

**要点**

- `args`が`null`になることは基本的にない(通常の`java`コマンドでの起動では、引数0個でも空配列が渡される)
- そのため`args == null`のチェックは、コマンドライン引数の有無を判定する目的では通常`false`になる
- 「引数の有無」を調べたいときは`args == null`ではなく`args.length == 0`を使うのが正しい

以下のコードを、コマンドライン引数を1つも指定せずに`java Main`として実行するとどうなるか。

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(args == null);
        System.out.println(args.length);
    }
}
```

A. `true`と`0`が2行で出力される
B. `false`と`0`が2行で出力される
C. 1行目でコンパイルエラーになる。配列を`null`と比較すること自体ができないため
D. 実行時に`NullPointerException`がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex13-3"></a>
## 問題ex13-3

**要点**

- JVMがエントリーポイントとして認識する`main`メソッドの引数の書き方には、`String[] args`以外にも`String args[]`(配列の`[]`をメソッド名側に付ける書き方)や`String... args`(可変長引数)が存在し、いずれも有効
- `main`メソッドが`public static void`かつメソッド名`main`という条件を満たさない場合、コンパイル自体は通っても、`java`コマンドでの起動時にJVMが原因を具体的に示すエラー(例: 非staticの場合は「メイン・メソッドがクラスXのstaticではありません」)を出して起動に失敗する
- コンパイルエラーと起動時エラーは別物であるという点がポイント

次の`main`メソッド宣言のうち、`javac`でのコンパイルは通るが、`java`コマンドでの実行時にエラーとなり起動に失敗するものはどれか。

A. `public static void main(String[] args)`
B. `public static void main(String args[])`(配列の`[]`をメソッド名側に付ける書き方)
C. `public void main(String[] args)`(`static`がない)
D. `public static void main(String... args)`(可変長引数)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex13-4"></a>
## 問題ex13-4

**要点**

- コマンドライン引数はどんな内容であっても常に`String`型として渡される。数字に見える文字列でも自動的に数値型へ変換されることはない
- そのため`args[0] + args[1] + args[2]`は数値の加算ではなく、文字列の連結として評価される
- 例えば`java Main 1 2 3`と実行しても、`1+2+3`の計算結果である`6`にはならず、文字列としてそのまま連結された`123`になる

以下のコードを`java Main 1 2 3`として実行すると、標準出力に何が表示されるか。

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(args[0] + args[1] + args[2]);
    }
}
```

A. `6`
B. `123`
C. コンパイルエラーになる
D. 実行時に例外がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex14-1"></a>
## 問題ex14-1

**要点**

- `String`には`append()`メソッドは存在しない(`append()`は`StringBuilder`専用)。`String`変数に対して`.append()`を呼ぶとコンパイルエラーになる

```java
String s = "Java 17";
s.append(" Silver");
int length = s.length();
int index = s.indexOf("Gold");
System.out.println(length + " : " + index);
```

A. `7 : -1`が出力される

B. コンパイルエラーになる

C. `14 : -1`が出力される

D. `NullPointerException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex14-2"></a>
## 問題ex14-2

**要点**

- `StringBuilder`はミュータブルなので、`sb.append(...)`は戻り値を代入しなくても、呼び出しただけで`sb`自身の中身が書き換わる
- `println(obj)`は内部で自動的に`obj.toString()`を呼ぶ(明示的な呼び出し不要)

```java
StringBuilder sb = new StringBuilder("Java 17");
sb.append(" Silver");
System.out.println(sb);
```

A. `Java 17`が出力される

B. コンパイルエラーになる

C. `null`が出力される

D. `Java 17 Silver`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex14-3"></a>
## 問題ex14-3

**要点**

- `String`には似た働きの`concat()`メソッドが存在するが、`String`はイミュータブルなので、`concat()`は新しい`String`を返すだけで元の変数は変化しない。反映したければ再代入が必要

```java
String s = "Java 17";
s.concat(" Silver");
System.out.println(s);
String s2 = s.concat(" Silver");
System.out.println(s2);
```

A. `Java 17`と`Java 17 Silver`が出力される

B. `Java 17 Silver`と`Java 17 Silver`が出力される

C. コンパイルエラーになる

D. `Java 17`と`Java 17`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex14-4"></a>
## 問題ex14-4

**要点**

- `String`の`length`はメソッド(`s.length()`)であり、配列の`.length`(フィールド、括弧なし)とは違う。`s.length`(括弧なし)はコンパイルエラーになる

```java
String s = "Java 17";
System.out.println(s.length);
```

A. `7`が出力される

B. `Java 17`が出力される

C. コンパイルエラーになる

D. `0`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex15-1"></a>
## 問題ex15-1

**要点**

- `StringBuilder.substring(begin, end)`は`String`の`substring()`と同じく、新しい`String`を返すだけで、呼び出し元の`StringBuilder`自体は変化しない。戻り値を使わなければ何も起きない
- `insert`と`delete`は組み合わせ次第で「元に戻る」こともある(同じ範囲に挿入してから削除すれば実質何も変わらない)

```java
StringBuilder sb = new StringBuilder();
sb.append("Gold").append("Silver");
sb.insert(4, " ").delete(4, 5);
sb.substring(0, 4);
System.out.println(sb);
```

A. `Gold Silver`が出力される

B. `Gold`が出力される

C. `GoldSilver`が出力される

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex15-2"></a>
## 問題ex15-2(難題)

**要点**

- `StringBuilder.substring()`の戻り値の型は`String`。メソッドチェーンの次に何が呼べるかは、直前のメソッドの戻り値の型で決まる(呼び出し元の`sb`自身の型ではない)
- `String`には`append()`が無いので、`substring()`の直後に`.append()`を繋ぐとコンパイルエラーになる

```java
StringBuilder sb = new StringBuilder("GoldSilver");
sb.substring(0, 4).append("Coin");
System.out.println(sb);
```

A. `GoldCoin`が出力される

B. `GoldSilverCoin`が出力される

C. コンパイルエラーになる

D. `GoldSilver`が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：C
迷ったポイント：`sb`自身が`StringBuilder`だから、そこから呼び出したメソッドを連鎖させれば全部`StringBuilder`のメソッドを呼べると思い込んでいた。実際はメソッドチェーンの次に呼べるのは、直前のメソッド(この場合`substring()`)の戻り値の型(`String`)次第であり、`String`には`append()`が存在しないためコンパイルエラーになる。

---
