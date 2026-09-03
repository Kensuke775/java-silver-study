## 目次

**問題一覧**

- [問題1-1](#q1-1)
- [問題1-2](#q1-2)
- [問題1-3](#q1-3)
- [問題1-4](#q1-4)
- [問題3-1](#q3-1)
- [問題3-2](#q3-2)
- [問題3-3](#q3-3)
- [問題3-4](#q3-4)
- [問題4-1](#q4-1)
- [問題4-2](#q4-2)
- [問題4-3](#q4-3)
- [問題4-4](#q4-4)
- [問題4-5](#q4-5)
- [問題4-6](#q4-6)
- [問題2-1](#q2-1)
- [問題2-2](#q2-2)
- [問題2-3](#q2-3)
- [問題2-4](#q2-4)
- [問題5-1](#q5-1)
- [問題5-2](#q5-2)
- [問題5-3](#q5-3)
- [問題5-4](#q5-4)
- [問題7-1](#q7-1)
- [問題7-2](#q7-2)
- [問題7-3](#q7-3)
- [問題7-4](#q7-4)
- [問題8-1](#q8-1)
- [問題8-2](#q8-2)
- [問題8-3](#q8-3)
- [問題8-4](#q8-4)
- [問題9-1](#q9-1)
- [問題9-2](#q9-2)
- [問題9-3](#q9-3)
- [問題9-4](#q9-4)
- [問題10-1](#q10-1)
- [問題10-2](#q10-2)
- [問題10-3](#q10-3)
- [問題10-4](#q10-4)
- [問題11-1](#q11-1)
- [問題11-2](#q11-2)
- [問題11-3](#q11-3)
- [問題11-4](#q11-4)
- [問題11-5](#q11-5)
- [問題12-1](#q12-1)
- [問題12-2](#q12-2)
- [問題12-3](#q12-3)
- [問題12-4](#q12-4)
- [問題13-1](#q13-1)
- [問題13-2](#q13-2)
- [問題ex1-1](#qex1-1)
- [問題ex1-2](#qex1-2)
- [問題ex1-3](#qex1-3)
- [問題ex1-4](#qex1-4)
- [問題ex5-1](#qex5-1)
- [問題ex5-2](#qex5-2)
- [問題ex3-1](#qex3-1)
- [問題ex3-2](#qex3-2)
- [問題ex3-3](#qex3-3)
- [問題ex3-4](#qex3-4)
- [問題ex5-3](#qex5-3)
- [問題ex5-4](#qex5-4)
- [問題ex6-1](#qex6-1)
- [問題ex6-2](#qex6-2)
- [問題ex6-3](#qex6-3)
- [問題ex6-4](#qex6-4)
- [問題ex7-1](#qex7-1)
- [問題ex7-2](#qex7-2)
- [問題ex8-1](#qex8-1)
- [問題ex8-2](#qex8-2)
- [問題ex8-3](#qex8-3)
- [問題ex8-4](#qex8-4)
- [問題ex9-1](#qex9-1)
- [問題ex9-2](#qex9-2)
- [問題ex10-1](#qex10-1)
- [問題ex10-2](#qex10-2)
- [問題ex11-1](#qex11-1)
- [問題ex11-2](#qex11-2)
- [問題ex12-1](#qex12-1)
- [問題ex12-2](#qex12-2)
- [問題ex12-3](#qex12-3)
- [問題ex13-1](#qex13-1)
- [問題ex13-2](#qex13-2)
- [問題ex14-1](#qex14-1)
- [問題ex14-2](#qex14-2)
- [問題ex15-1](#qex15-1)
- [問題ex15-2](#qex15-2)
- [問題ex16-1](#qex16-1)
- [問題ex16-2](#qex16-2)

<a id="q1-1"></a>
## 問題1-1

**要点**

- `while`ループは、条件式が`true`である間、本体を繰り返し実行する
- 1つ目のループは「出力してから増やす」、2つ目のループは「先に増やしてから出力する」という順序の違いがある
- `print`は改行なし、`println`は改行ありという違いも出力の見た目に影響する

```java
public class Main {
    public static void main(String[] args) {
        int a = 1;
        while (a < 5) {
            System.out.print(a);
            a++;
        }
        System.out.println("\n-----");
        int b = 1;
        while (b <= 5) {
            b++;
            System.out.print(b);
        }
    }
}
```

A.
```
1234
-----
23456
```
B.
```
1234
-----
12345
```
C.
```
12345
-----
23456
```
D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q1-2"></a>
## 問題1-2

**要点**

- `while`は条件を先にチェックしてから本体を実行する。条件が最初から`false`なら、本体は一度も実行されない
- `do`〜`while`は本体を先に実行してから条件をチェックする。条件が最初から`false`でも、本体は必ず1回は実行される

```java
public class Main {
    public static void main(String[] args) {
        int x = 10;
        while (x < 5) {
            System.out.println("while: " + x);
            x++;
        }
        int y = 10;
        do {
            System.out.println("do-while: " + y);
            y++;
        } while (y < 5);
    }
}
```

A. 何も出力されない
B. `while: 10`と`do-while: 10`の両方が出力される
C. `do-while: 10`だけが出力される
D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：C
迷ったポイント：`while`は条件を先にチェックするため、最初から条件がfalseなら本体が一度も実行されない(`"while: 10"`は出力されない)という点を見落とし、両方出力されると誤解した

---

<a id="q1-3"></a>
## 問題1-3

**要点**

- `while`ループの本体の中で、条件式に関わる変数(`i`)を一切変化させていないと、条件が永遠に`true`のままになり、ループが終わらない(無限ループ)
- 今回のコードは`i++`のような更新処理が抜けているため、`i`はずっと`0`のままで、`i < 5`が永遠に成立し続ける

```java
public class Main {
    public static void main(String[] args) {
        int i = 0;
        while (i < 5) {
            System.out.println(i);
        }
    }
}
```

A. `0`から`4`まで出力されて正常終了する
B. コンパイルエラーになる
C. `0`が5回出力されて正常終了する
D. 無限ループになり、`0`を出力し続けたままプログラムが終了しない

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q1-4"></a>
## 問題1-4

**要点**

- `continue`は、それ以降のループ本体の処理をスキップして、次の周回の条件チェックへ戻る(ループ自体は終わらない)
- `break`(ループを完全に抜ける)とは違い、`continue`はあくまで「その回だけスキップ」という挙動
- `while(i < 5)`の条件チェックは、そのイテレーションの本体に入る**前**の`i`の値で行われる。本体内で`i++`によりすでに更新された`i`が使われる点とズレがある

```java
public class Main {
    public static void main(String[] args) {
        int i = 0;
        while (i < 5) {
            i++;
            if (i == 3) {
                continue;
            }
            System.out.print(i);
        }
    }
}
```

A. `12345`
B. `1245`
C. `124`
D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：B
迷ったポイント：`i`が`5`になった瞬間にループが終わると誤解し、最後の`5`の出力を見落とした。実際は`i=4`の時点(まだ`4<5`でtrue)でループに入り、本体内で`i++`により`i`が`5`になったあとに出力されるため、`5`もきちんと出力される。「ループに入るかどうかの判定に使うiの値」と「本体内で実際に使われる(更新済みの)iの値」がズレる、switch(i--)の議論と似た構造の見落とし

---

<a id="q3-1"></a>
## 問題3-1

**要点**

- `do`〜`while`は本体を先に実行してから条件をチェックする、という基本は変わらない
- 2つ目の`do`〜`while`には波括弧`{}`がなく、`System.out.print(b + " ");`だけが本体になる(コメントアウトされた`// b += 2;`は本体には含まれない、ただのコメント)
- `while ((b += 2) < 10) ;`のように、条件式の中に代入演算子`+=`を書くこともできる。この副作用は、条件がチェックされるたびに毎回実行される

```java
public class Main {
    public static void main(String[] args) {
        int a = 1;
        do {
            System.out.print(a + " ");
            a += 2;
        } while (a < 10);
        System.out.println("\n----");
        int b = 1;
        do
            System.out.print(b + " ");
            // b += 2;
        while ((b += 2) < 10) ;
    }
}
```

A. `1 3 5 7 9` / `----` / `1 3 5 7 9 11`
B. `1 3 5 7 9` / `----` / コンパイルエラーになる
C. `1 3 5 7 9` / `----` / `1 3 5 7 9`
D. `1 3 5 7 9` / `----` / 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q3-2"></a>
## 問題3-2

**要点**

- `do`〜`while`は「本体を実行→条件をチェック」を繰り返す。条件のチェックには`(b += 2)`という副作用のある式が使われている
- ループが終わる(条件が`false`になる)瞬間も、この`b += 2`自体は実行されてから判定される。つまり最後に出力された`b`の値(`9`)と、ループ終了後の`b`の実際の値は一致しない
- ループを抜けた後の`b`は、最後に出力した値よりも2大きい値になっている

```java
public class Main {
    public static void main(String[] args) {
        int b = 1;
        do
            System.out.print(b + " ");
        while ((b += 2) < 10) ;
        System.out.println();
        System.out.println("final b = " + b);
    }
}
```

A. `1 3 5 7 9 ` の次の行に `final b = 11`
B. `1 3 5 7 9 ` の次の行に `final b = 9`
C. `1 3 5 7 9 ` の次の行に `final b = 10`
D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q3-3"></a>
## 問題3-3

**要点**

- `do`〜`while`に波括弧がない場合、本体として扱われるのは直後の1文だけ
- コメントアウトされていた`b += 2;`を有効化すると、`do`の本体は`System.out.print(...)`の1文だけで完結し、続く`b += 2;`は宙に浮いた文になり、その後の`while(...)`とうまく繋がらなくなる
- 結果として構文エラーになる(「無限ループになりそう」という直感は自然だが、実行に至る前のパース段階で崩れる)

```java
public class Main {
    public static void main(String[] args) {
        int b = 1;
        do
            System.out.print(b + " ");
            b += 2;
        while ((b += 2) < 10) ;
    }
}
```

A. コンパイル・実行とも問題なく完了する
B. 実行時に例外がスローされる
C. コンパイルは通るが、無限ループになる
D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：D
迷ったポイント：不安。「無限ループになりそう」という直感の方向に引っ張られ、波括弧のないdo-whileの本体が直後の1文だけである(2文目以降は本体に含まれず宙に浮く)という構文レベルの制約を見落とし、実行に至る前のコンパイルエラーになる点を見誤った

---

<a id="q3-4"></a>
## 問題3-4

**要点**

- 開始値が変わると、条件式の副作用によってループの回数や最終的な変数の値も変わる
- `b=2`から始めると、`2, 4, 6, 8`まで出力され、`b += 2`が最後に`10`になった時点で`10 < 10`が`false`になりループを抜ける
- ループ終了後の`b`は最後に出力された値(`8`)より2大きい`10`になる

```java
public class Main {
    public static void main(String[] args) {
        int b = 2;
        do
            System.out.print(b + " ");
        while ((b += 2) < 10) ;
        System.out.println();
        System.out.println("final b = " + b);
    }
}
```

A. `1 3 5 7 9 ` の次の行に `final b = 11`
B. `2 4 6 8 ` の次の行に `final b = 10`
C. `2 4 6 8 10 ` の次の行に `final b = 10`
D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q4-1"></a>
## 問題4-1

**要点**

- `while(a > 10)`は、`a=1`の時点で条件がすでに`false`(1は10より大きくない)なので、本体は一度も実行されない
- 一方`do`〜`while`は本体を先に実行するので、条件が最初から`false`でも1回だけ実行される
- 以前の問題1-2と同じ構造(`while`と`do-while`の違い)を、`<`ではなく`>`という別の比較演算子で確認するパターン

```java
public class Main {
    public static void main(String[] args) {
        int a = 1;
        while(a > 10) {
            System.out.print(a + " ");
            a--;
        }
        System.out.println("\n----");
        int b = 1;
        do {
            System.out.print(b + " ");
            b--;
        } while (b > 10);
    }
}
```

A. 何も出力されない
B. `1 `の後に`\n----\n1 `が出力される
C. `\n----\n`のみ出力され、その後何も出力されない
D. `\n----\n`の後に`1 `が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q4-2"></a>
## 問題4-2

**要点**

- ラベル(`outer:`)を`for`文の直前に付けると、`break ラベル名;`でそのラベルが指すループを直接抜けられる(内側のループだけでなく、外側のループも一気に抜ける)
- `break;`(ラベルなし)は一番内側のループだけを抜けるのに対し、`break outer;`は`outer`というラベルが付いたループごと抜ける

```java
public class Main {
    public static void main(String[] args) {
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    break outer;
                }
                System.out.print(i + "" + j + " ");
            }
        }
    }
}
```

A. `00 `
B. `00 10 20 `
C. `00 01 `
D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：A
迷ったポイント：ラベル付き`break`(`break outer;`)自体が有効な構文であることを見落とし、コンパイルエラーになると誤解した。実際は複数階層のループを一気に抜けるための正当な機能であり、`i=0,j=0`で"00 "を出力したあと`j=1`で外側ループごと抜けるため出力は"00 "のみになる

---

<a id="q4-3"></a>
## 問題4-3

**要点**

- `while (a < 10)`で、本体の中で`a--`のように条件が満たされる方向とは逆に変数を変化させてしまうと、条件がいつまでも`true`のままになり、無限ループになる
- `a`は1から0、-1、-2…とどんどん小さくなっていくが、`10`未満という条件は永遠に満たされ続ける

```java
public class Main {
    public static void main(String[] args) {
        int a = 1;
        while (a < 10) {
            System.out.print(a + " ");
            a--;
        }
    }
}
```

A. `1`から`9`まで出力されて正常終了する
B. コンパイルエラーになる
C. 無限ループになる
D. 実行時に`StackOverflowError`がスローされる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：C
迷ったポイント：`a++`(増加方向)であれば1から9まで出力されて正常終了する、というのと混同し、`a--`(条件とは逆方向への変化)でも同様に正常終了すると誤解した。実際は`a`が減り続ける限り`a < 10`は永遠に満たされ続けるため無限ループになる

---

<a id="q4-4"></a>
## 問題4-4

**要点**

- `continue ラベル名;`は、そのラベルが指すループの次の周回に直接ジャンプする(内側のループだけをスキップするのではなく、外側のループの次の反復に進む)
- `continue;`(ラベルなし)は一番内側のループの次の周回に進むだけだが、`continue outer;`は外側のループの次の`i`にまで一気に進む

```java
public class Main {
    public static void main(String[] args) {
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 1) {
                    continue outer;
                }
                System.out.print(i + "" + j + " ");
            }
        }
    }
}
```

A. `00 01 02 10 11 12 20 21 22 `
B. `00 10 20 `
C. `00 `
D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q4-5"></a>
## 問題4-5

**要点**

- `x--`のように減らしていく操作でも、条件が`x > 5`のように「大きい方が真」であれば、減らすことは条件を満たさなくする方向になるので、いずれ正常にループが終わる(4-3の逆方向トラップとの対比)
- `break search;`は`for`文につけたラベルだが、その内側にある`while`ループも含めて一気に抜けられる(ラベルは直接の外側のループだけでなく、その内側全体を対象にする)

```java
public class Main {
    public static void main(String[] args) {
        int count = 0;
        search:
        for (int i = 0; i < 3; i++) {
            int x = 10;
            while (x > 5) {
                if (i == 1) {
                    break search;
                }
                count++;
                x--;
            }
        }
        System.out.println(count);
    }
}
```

A. `5`
B. `10`
C. `0`
D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q4-6"></a>
## 問題4-6

**要点**

- `continue search;`は`break search;`と違い、外側の`for`ループ自体は終わらせず、次の`i`へ進むだけ
- `i==1`のときだけ内側`while`を打ち切って`i=2`へ進み、`i=2`では通常通り`count`が加算され続ける
- 4-5(`break search`、結果5)と結果を比較することでbreak/continueの違いを確認する問題

```java
public class Main {
    public static void main(String[] args) {
        int count = 0;
        search:
        for (int i = 0; i < 3; i++) {
            int x = 10;
            while (x > 5) {
                if (i == 1) {
                    x--;
                    continue search;
                }
                count++;
                x--;
            }
        }
        System.out.println(count);
    }
}
```

A. `5`
B. `10`
C. `15`
D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q2-1"></a>
## 問題2-1

**要点**

- `while(条件)`は、ループの中で条件を変化させる処理(カウンタの更新など)を書き忘れると、条件が永遠に`true`のままになり無限ループになる

```java
int a = 5;
while (a > 0) {
    System.out.print(a);
}
```

A. `5`だけ出力されて終了する

B. `5`を無限に出力し続け、プログラムは終了しない

C. コンパイルエラーになる

D. 何も出力されずに終了する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q2-2"></a>
## 問題2-2

**要点**

- `while(条件) 文;`のように波括弧を付けない場合、直後の1文だけが本体になる

```java
int b = 5;
while (b >= 0)
    b--;
System.out.print(b);
```

A. `0`

B. `5`

C. コンパイルエラーになる

D. `-1`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q2-3"></a>
## 問題2-3

**要点**

- `while(条件);`のようにセミコロンを直後に書くと、それだけで1つの「空文」がループ本体として扱われる。直後の`{ }`ブロックはループとは無関係な別の独立したブロックになり、無限ループが終わらない限り一生到達しない

```java
int a = 5;
while (a > 0);
{
    System.out.print(a);
    a--;
}
```

A. 何も出力されずに無限ループになる(ブロックに一度も到達しない)

B. `5`が1回だけ出力されて終了する

C. `5`から`1`まで出力されて終了する

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：A
迷ったポイント：`{ }`ブロックがそのままループ本体になっていると読んでしまった。実際はwhileの直後のセミコロンが独立した1つの文(空文)として本体を確定させてしまうため、後ろの`{ }`はループには一切関与しない。

---

<a id="q2-4"></a>
## 問題2-4

**要点**

- `do-while`は、条件を先に確認せず、まず1回本体を実行してから条件を判定する。最初から条件がfalseでも、本体は必ず1回は実行される

```java
int c = 0;
do {
    System.out.print(c);
    c++;
} while (c < 0);
```

A. 何も出力されない

B. 無限ループになる

C. `0`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q5-1"></a>
## 問題5-1

**要点**

- `for (初期化; 条件; 更新)`の実行順序は「初期化(最初の1回だけ) → 条件判定 → 本体 → 更新 → 条件判定 → …」の繰り返し

```java
for (int i = 0; i < 5; i++) {
    System.out.print(i);
}
```

A. `12345`

B. `0123`

C. コンパイルエラーになる

D. `01234`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q5-2"></a>
## 問題5-2

**要点**

- `for`の初期化部分で宣言した変数は、そのfor文の中でしか使えない(スコープがfor文全体に限定される)。ループの外で参照しようとするとコンパイルエラーになる

```java
for (int i = 0; i < 5; i++) {
    System.out.print(i);
}
System.out.println(i);
```

A. コンパイルエラーになる

B. `01234` の後に `5`

C. `01234` の後に `4`

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q5-3"></a>
## 問題5-3

**要点**

- 初期化・更新部分はカンマ区切りで複数の変数を同時に扱える

```java
for (int i = 0, j = 10; i < 3; i++, j--) {
    System.out.println(i + ":" + j);
}
```

A. `0:10`のみ

B. `0:10` / `1:10` / `2:10`

C. `0:10` / `1:9` / `2:8`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：不安。

---

<a id="q5-4"></a>
## 問題5-4

**要点**

- 変数をfor文の外で宣言しておけば、ループが終わった後もその変数を参照できる
- ループが終了するのは条件が`false`になった時点。`i++`による更新は条件判定の前に行われるため、ループを抜けた時点での`i`の値は「最後に画面に表示された値」より1大きい

```java
int i;
for (i = 0; i < 5; i++) {
    System.out.print(i);
}
System.out.println();
System.out.println("after: " + i);
```

A. `after: 4`

B. `after: 5`

C. コンパイルエラーになる

D. `after: 0`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：B
迷ったポイント：ループを抜けた時点でのiの値を、「最後に画面に表示された値(4)」と同じだと誤解した。実際は更新処理(i++)が条件判定の前に行われるため、ループを抜けた時点でiはすでに5になっている。チェック。

---

<a id="q7-1"></a>
## 問題7-1

**要点**

- 拡張for文(`for (型 変数名 : 配列)`)は、配列の各要素を先頭から順に取り出して処理する

```java
int[] array = {1, 2, 3};
for (int e : array) {
    System.out.print(e + " ");
}
```

A. `3 2 1 `

B. コンパイルエラーになる

C. `1 2 3 `

D. `NullPointerException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q7-2"></a>
## 問題7-2

**要点**

- 拡張for文のループ変数(`e`)は、配列の要素のコピーを受け取るだけ。ループ内で`e`を書き換えても、元の配列の中身には一切影響しない

```java
int[] array = {1, 2, 3};
for (int e : array) {
    e = e * 100;
}
for (int e : array) {
    System.out.print(e + " ");
}
```

A. `1 2 3 `

B. `100 200 300 `

C. コンパイルエラーになる

D. `0 0 0 `

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：不安。

---

<a id="q7-3"></a>
## 問題7-3

**要点**

- 拡張for文の対象が`null`の配列だと、要素を取り出そうとする前の段階で`NullPointerException`が発生する

```java
int[] array = null;
for (int e : array) {
    System.out.print(e + " ");
}
```

A. 何も出力されずに終了する

B. コンパイルエラーになる

C. 無限ループになる

D. `NullPointerException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：不安。

---

<a id="q7-4"></a>
## 問題7-4

**要点**

- 拡張for文のループ変数も、通常のfor文と同じくそのループの中だけでしか使えない(スコープが限定される)

```java
int[] array = {1, 2, 3};
for (int e : array) {
    System.out.print(e + " ");
}
System.out.println(e);
```

A. `1 2 3 `の後に`3`

B. コンパイルエラーになる

C. `1 2 3 `の後に`0`

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q8-1"></a>
## 問題8-1

**要点**

- 拡張for文の型には`var`も使える
- 配列を逆順に処理したい場合は、通常のfor文で`fruits.length - 1`から`0`まで(境界含む`>=0`)減らしながらアクセスする

```java
String[] fruits = {"Apple", "Banana", "Coconut"};
for (var v : fruits)
    System.out.print(v + " ");
System.out.println("\n----");
for (int i = fruits.length - 1; i >= 0; i--) {
    System.out.print(fruits[i] + " ");
}
System.out.println("\n----");
for (String fruit : fruits) {
    System.out.print(fruit.charAt(0));
}
```

A. `Apple Banana Coconut`の後に改行、`Coconut Banana Apple`、`ABC`

B. `Apple Banana Coconut \n----`、`Coconut Banana Apple \n----`、`ABC`

C. コンパイルエラーになる

D. `Apple Banana Coconut \n----`、`Apple Banana Coconut \n----`、`ABC`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：不安。varの部分。

---

<a id="q8-2"></a>
## 問題8-2

**要点**

- 拡張for文のループ変数の型は、配列の要素の型と一致(または代入可能)していなければならない。`String[]`に対して`char`型のループ変数を使おうとするとコンパイルエラーになる

```java
String[] fruits = {"Apple", "Banana", "Coconut"};
for (char c : fruits) {
    System.out.print(c);
}
```

A. `ABC`

B. `Apple Banana Coconut`のまま出力される

C. 実行時に例外が発生する

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：D
迷ったポイント：不安。Stringの要素からcharへの変換が実行時エラー(例外)になると誤解した。実際はループ変数の型と配列要素の型の不一致は、そもそもコンパイル時に検出される(実行時まで進まない)。

---

<a id="q8-3"></a>
## 問題8-3

**要点**

- 拡張for文のループ変数宣言には初期化子を書けない。`for (String fruit = "" : fruits)`のような書き方は構文エラーになる

```java
String[] fruits = {"Apple", "Banana", "Coconut"};
for (String fruit = "" : fruits) {
    System.out.println(fruit + " ");
}
```

A. コンパイルエラーになる

B. 空文字列が3回出力される

C. `Apple` `Banana` `Coconut`が出力される

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：A
迷ったポイント：不安。`= ""`という初期化子が「初期値として使われるだけで、その後fruitsの要素で上書きされていく」という動作だと誤解した。実際はこの初期化子自体が拡張for文の構文として許されておらず、その場で構文エラーになる。

---

<a id="q8-4"></a>
## 問題8-4

**要点**

- ループの境界条件(`>=0`か`>0`か)を間違えると、意図しない範囲だけが処理される

```java
String[] fruits = {"Apple", "Banana", "Coconut"};
for (int i = fruits.length - 1; i > 0; i--) {
    System.out.print(fruits[i] + " ");
}
```

A. `Apple Banana Coconut `

B. `Coconut Banana Apple `

C. `Coconut Banana `

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：不安。

---

<a id="q9-1"></a>
## 問題9-1

**要点**

- for文で1〜15を出力、`i<10`かどうかで空白の数を変え、`i%5==0`のときだけ`" ..."`を追記して改行する処理

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 15; i++) {
            if (i < 10) {
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
            if (i % 5 == 0) {
                String s = " ...";
                System.out.println(s);
            }
        }
    }
}
```

A. `"..."`の前に空白がないバージョン

B. 10も2桁揃えのまま空白2つのバージョン

C. 次の通り
```
  1  2  3  4  5 ...
  6  7  8  9 10 ...
 11 12 13 14 15 ...
```

D. print/printlnの区別を誤解した、1行ずつ改行されるバージョン

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q9-2"></a>
## 問題9-2

**要点**

- 元のコード末尾の`if (i % 5 == 0) { ... }`ブロックの外に`System.out.println(s);`を追加した場合の挙動
- `s`はifブロック内でのみ宣言・代入されているため、ブロックの外(スコープ外)では参照できない

```java
for (int i = 1; i <= 15; i++) {
    if (i < 10) {
        System.out.print("  " + i);
    } else {
        System.out.print(" " + i);
    }
    if (i % 5 == 0) {
        String s = " ...";
        System.out.println(s);
    }
    System.out.println(s);   // ← ここに追加
}
```

A. コンパイルエラーになる(シンボルを見つけられません: 変数 s)

B. コンパイルは通るが、`i%5!=0`の周では`s`が`null`として出力される

C. コンパイルは通るが、`i%5!=0`の周では`s`が前回の値を保持して出力される

D. コンパイルは通り、`i%5==0`の周だけ`...`が2回出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q9-3"></a>
## 問題9-3

**要点**

- `String s;`(初期化なし)を`if (i<10) ... else ...`より前、forループ本体の先頭に移動し、`i%5==0`のブロック内でのみ代入・使用するケース
- 9-2との対比: ブロック外で「宣言」するだけならOK、ブロック外で「使用」するとエラー、という違いを確認する問題
- 明確代入(definite assignment)は「宣言位置」ではなく「実際に読み取る位置で代入済みかどうか」で判定される

```java
for (int i = 1; i <= 15; i++) {
    String s;
    if (i < 10) {
        System.out.print("  " + i);
    } else {
        System.out.print(" " + i);
    }
    if (i % 5 == 0) {
        s = " ...";
        System.out.println(s);
    }
}
```

A. コンパイルエラーになる(sが未初期化の可能性があるため)

B. コンパイルエラーになる(sのスコープがfor文全体で重複するため)

C. コンパイルは通るが実行時に`NullPointerException`が発生する

D. コンパイルは通り、元のプログラムと全く同じ出力になる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q9-4"></a>
## 問題9-4

**要点**

- 元のコードの条件を`i<10`から`i<=10`に変更した場合の出力トレース

```java
for (int i = 1; i <= 15; i++) {
    if (i <= 10) {
        System.out.print("  " + i);
    } else {
        System.out.print(" " + i);
    }
    if (i % 5 == 0) {
        String s = " ...";
        System.out.println(s);
    }
}
```

A. 元のまま(10だけ空白1つ)のバージョン

B. 次の通り
```
  1  2  3  4  5 ...
  6  7  8  9  10 ...
 11 12 13 14 15 ...
```

C. 3行目の先頭空白が抜けているバージョン

D. `"..."`の前の空白が抜けているバージョン

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q10-1"></a>
## 問題10-1

**要点**

- 2次元配列は「配列の配列」なので、拡張forも通常forも入れ子(ネスト)にして各行・各列を処理する

```java
int[][] array = {{1, 9}, {2, 8}, {3, 7}};
for (int[] x : array) {
    for (int y : x) {
        System.out.print(y);
    }
    System.out.print(" ");
}
```

A. `19 28 37 `

B. `123456789`のように全部繋がって出力される

C. コンパイルエラーになる

D. `1 9 2 8 3 7 `

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q10-2"></a>
## 問題10-2

**要点**

- ジャグ配列(行ごとに長さが違う配列)でも、拡張forは各行のlengthを自動的に扱ってくれるので、正しく全要素を処理できる

```java
int[][] array = {{1}, {2, 8}, {3, 7, 9}};
for (int[] x : array) {
    for (int y : x) {
        System.out.print(y);
    }
    System.out.print(" ");
}
```

A. コンパイルエラーになる

B. `1 2 8 3 7 9 `

C. `1 28 379 `

D. 実行時に例外が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q10-3"></a>
## 問題10-3

**要点**

- 通常forで内側のループの上限に、うっかり外側の`array.length`(行数)を使ってしまうと、行によって実際の列数が違うジャグ配列では`ArrayIndexOutOfBoundsException`が発生する。`array[i].length`(その行自身の長さ)と混同しないこと

```java
int[][] array = {{1}, {2, 8}, {3, 7, 9}};
for (int i = 0; i < array.length; i++) {
    for (int j = 0; j < array.length; j++) {
        System.out.print(array[i][j]);
    }
    System.out.print(" ");
}
```

A. `1 28 379 `

B. コンパイルエラーになる

C. `123456789`のように全要素が出力される

D. `1`が出力された後、`ArrayIndexOutOfBoundsException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：D
迷ったポイント：`array.length`(行数)と`array[i].length`(その行自身の長さ)を混同し、前問(10-2、拡張forが自動でジャグ配列を正しく処理していた)と同じ結果になると誤解した。実際は`array.length`を内側ループの上限に使うと行ごとの実際の要素数を無視することになり、要素数の少ない行で範囲外アクセスが起きる。

---

<a id="q10-4"></a>
## 問題10-4

**要点**

- 拡張forのループ変数への代入は元の配列を書き換えない(コピーだから)。通常forで`array[i][j] = ...`のようにインデックス経由で代入した場合だけ、元の配列が実際に書き換わる

```java
int[][] array = {{1, 9}, {2, 8}};
for (int[] x : array) {
    for (int y : x) {
        y = y * 100;
    }
}
for (int i = 0; i < array.length; i++) {
    for (int j = 0; j < array[i].length; j++) {
        array[i][j] = array[i][j] * 10;
    }
}
for (int[] x : array) {
    for (int y : x) {
        System.out.print(y + " ");
    }
}
```

A. `100 900 200 800 `

B. `10 90 20 80 `

C. `1000 9000 2000 8000 `

D. `1 9 2 8 `

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q11-1"></a>
## 問題11-1

**要点**

- `while (true)`の無限ループを`break`で抜ける基本パターン。`break`は`i++`より前に実行されるので、ループを抜けた時点の`i`の値に注意

```java
public class Main {
    public static void main(String[] args) {
        int i = 0;
        while (true) {
            System.out.print(i);
            if (i == 3) {
                break;
            }
            i++;
        }
        System.out.println("\n----");
        System.out.println("i : " + i);
    }
}
```

A. `012` / `----` / `i : 2`

B. `0123` / `----` / `i : 4`

C. `01234` / `----` / `i : 4`

D. `0123` / `----` / `i : 3`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="q11-2"></a>
## 問題11-2

**要点**

- `break;`の直後、同じifブロック内に文を追加した場合の挙動(到達不能コード)

```java
if (i == 3) {
    break;
    System.out.println("break!");
}
```

A. コンパイルは通り、実行時には`"break!"`は出力されない

B. コンパイルエラーになる(この文に制御が移ることはありません)

C. コンパイルは通るが、無限ループになり出力が止まらなくなる

D. コンパイルエラーになる(breakの後にセミコロンが必要です)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：不安。

---

<a id="q11-3"></a>
## 問題11-3

**要点**

- 11-2との対比。文をifブロックの外(ただしwhileループの中、breakの対象になるifブロックのすぐ後)に置いた場合は到達可能(`i!=3`の周では実行される)なのでコンパイルエラーにならない
- `break`文は`if`文自体とは独立に、それを囲む`while`ループを直接抜ける

```java
public static void main(String[] args) {
    int i = 0;
    while (true) {
        System.out.print(i);
        if (i == 3) {
            break;
        }
        System.out.print("!");
        i++;
    }
    System.out.println("\n----");
    System.out.println("i : " + i);
}
```

A. `0!1!2!3` / `----` / `i : 3`

B. `0!1!2!3!` / `----` / `i : 3`

C. コンパイルエラーになる(到達不能コードのため)

D. `0!1!2!` / `----` / `i : 2`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：不安。ユーザーコメント「if部分はbreakと関係なくて、外側のループ部分を抜けるはず」→break文はif文自体とは独立に、それを囲むwhileループを直接抜けるという理解で正しい。

---

<a id="q11-4"></a>
## 問題11-4

**要点**

- `i++`をif文より前(ループ先頭)に移動した場合の出力トレース

```java
public static void main(String[] args) {
    int i = 0;
    while (true) {
        i++;
        System.out.print(i);
        if (i == 3) {
            break;
        }
    }
    System.out.println("\n----");
    System.out.println("i : " + i);
}
```

A. `0123` / `----` / `i : 3`

B. `123` / `----` / `i : 4`

C. `123` / `----` / `i : 3`

D. `1234` / `----` / `i : 4`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q11-5"></a>
## 問題11-5

**要点**

- `final`な変数が「コンパイル時定数式」として扱われる(＝到達不能コード判定でfalseリテラルと同じ扱いになる)には、`final`であることに加えて初期値自体もコンパイル時定数式でなければならない(JLS 4.12.4)。メソッド呼び出しの結果で初期化すると、たとえ`final`でも条件を満たさない

```java
public class Main {
    static int getZero() {
        return 0;
    }

    public static void main(String[] args) {
        final boolean boo = (getZero() == 1);
        while (boo) {
            System.out.println("hello");
        }
        System.out.println("end");
    }
}
```

A. コンパイルエラーになる(到達不能コードのため)

B. コンパイルは通り、`"hello"`は出力されず`"end"`だけが出力される

C. コンパイルは通り、`"hello"`が無限に出力される

D. コンパイルエラーになる(finalな変数はメソッドの戻り値で初期化できないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="q12-1"></a>
## 問題12-1

**要点**

- `continue`を使った基本パターン。`i%3==0`のとき`"skip!"`を出力してその周の残り(`println(i)`)をスキップする

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0) {
                System.out.println("skip!");
                continue;
            }
            System.out.println(i);
        }
    }
}
```

A. `skip! 1 2 skip! 4 5 skip! 7 8 skip!`

B. `skip! 0 1 2 skip! 3 4 5 skip! 6 7 8 skip! 9`

C. `1 2 4 5 7 8`

D. `0 1 2 skip! 4 5 skip! 7 8 skip!`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q12-2"></a>
## 問題12-2

**要点**

- `continue;`の直後、同じifブロック内に文を追加した場合の挙動(到達不能コード。11-2の`break`版と対になる問題)

```java
if (i % 3 == 0) {
    System.out.println("skip!");
    continue;
    System.out.println("after continue");
}
```

A. コンパイルは通り、実行時には`"after continue"`は出力されない

B. コンパイルは通るが、`"after continue"`が実行されて無限ループになる

C. コンパイルエラーになる(この文に制御が移ることはありません)

D. コンパイルエラーになる(continueの後にセミコロンが必要です)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="q12-3"></a>
## 問題12-3

**要点**

- 二重のforループの内側で(ラベルなしの)`continue`を使うと、常に最も内側のループだけが対象になる(外側には一切影響しない)ことを確認する問題

```java
public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j % 3 == 0) {
                    System.out.println("skip!");
                    continue;
                }
                System.out.println("i=" + i + ", j=" + j);
            }
        }
    }
}
```

A. `skip! i=0,j=1 skip! i=1,j=1 skip! i=2,j=1`

B. `skip! i=0,j=1 i=0,j=2 skip! i=1,j=1 i=1,j=2 skip! i=2,j=1 i=2,j=2`

C. `skip!`が3回出力されるだけで終了する

D. コンパイルエラーになる(continueの対象となるループが曖昧なため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：不安。

---

<a id="q12-4"></a>
## 問題12-4

**要点**

- 元のコードの条件を`i%3==0`から`i%3==1`に変更した場合の出力トレース

```java
for (int i = 0; i < 10; i++) {
    if (i % 3 == 1) {
        System.out.println("skip!");
        continue;
    }
    System.out.println(i);
}
```

A. `skip! 1 2 skip! 4 5 skip! 7 8 skip!`

B. `0 1 skip! 3 4 skip! 6 7 skip! 9`

C. `skip! 0 1 2 skip! 3 4 5 skip! ...`

D. `0 skip! 2 3 skip! 5 6 skip! 8 9`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：不安。

---

<a id="q13-1"></a>
## 問題13-1

**要点**

- `continue outer;`はouterループの次の周に進む(内側ループを丸ごとスキップ)
- `break outer;`は内側・外側両方のループを即座に抜ける

```java
outer:
for (int i = 0; true; i++) {
    for (int j = 0; j < 5; j++) {
        if (j == 3) {
            System.out.println("i:" + i + " skip!");
            continue outer;
        }
        if (i == 3) {
            System.out.println("break!");
            break outer;
        }
        System.out.println("i:" + i + " j:" + j);
    }
}
```

このプログラムを実行したとき、出力の最後の2行はどうなりますか。

A. `"i:2 skip!"` の次に `"break!"`

B. `"i:3 j:0"` の次に `"break!"`

C. `"break!"` の次に `"i:2 skip!"`

D. 無限ループになり終了しない

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="q13-2"></a>
## 問題13-2

**要点**

- ラベルなしの`continue`は最も内側のループにしか作用しない

問題13-1のコードで、8行目の`continue outer;`を`continue;`(ラベルなし)に変更しました。

```java
if (j == 3) {
    System.out.println("i:" + i + " skip!");
    continue;   // outerを外した
}
```

この変更によって、出力はどう変化しますか。

A. 出力は変化しない(`continue`と`continue outer`は今回のコードでは同じ意味になるため)

B. `"i:X skip!"`の直後に`"i:X j:4"`という行が新たに出力されるようになる

C. コンパイルエラーになる(ラベルなしのcontinueは多重ループの中では使えないため)

D. 無限ループになり、`"break!"`が出力されなくなる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex1-1"></a>
## 問題ex1-1

**要点**

- `x--`は後置デクリメントなので、printにはデクリメント前の値が渡り、その後で`x`が1減る
- 空欄の条件は`x > 0`

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

条件が`x > 0`のとき、実行結果はどうなりますか。

A. `10 9 8 7 6 5 4 3 2 1 0`

B. `9 8 7 6 5 4 3 2 1 0`

C. `10 9 8 7 6 5 4 3 2 1`

D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex1-2"></a>
## 問題ex1-2

**要点**

- ex1-1と同じコードで、条件だけ`x >= 0`に変更した場合の出力トレース

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

条件が`x >= 0`のとき、実行結果はどうなりますか。

A. `10 9 8 7 6 5 4 3 2 1 0`

B. `10 9 8 7 6 5 4 3 2 1`

C. `9 8 7 6 5 4 3 2 1 0`

D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex1-3"></a>
## 問題ex1-3

**要点**

- ex1-1と同じコードで、条件だけ`x < 20`に変更した場合
- `x`はどんどん減っていくので、`x < 20`は負の値になってもずっと成り立ち続ける。理論上は`int`のオーバーフローでいつか`Integer.MAX_VALUE`にラップアラウンドして`false`になるが、現実的な時間では終了しない(実質的な無限ループ)

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

条件が`x < 20`のとき、実行結果はどうなりますか。

A. 通常通りfalseになった時点で即座に終了する

B. コンパイルエラーになる

C. `10 9 8 ... -2147483648` まで出力されて正常終了する

D. 実質的な無限ループになる(理論上はintのオーバーフローでいつかInteger.MAX_VALUEにラップアラウンドしてfalseになるが、現実的な時間では終了しない)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex1-4"></a>
## 問題ex1-4

**要点**

- ex1-1と同じコードで、条件だけ`x != 0`に変更した場合
- `x`は1ずつしか減らないので必ずちょうど0を通過する。したがって`x != 0`は`x > 0`(ex1-1)と全く同じ結果になる。ステップ幅が1でない場合は`x != 0`は0を飛び越えて無限ループになる危険がある

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

条件が`x != 0`のとき、実行結果はどうなりますか。

A. 無限ループになる

B. `10 9 8 7 6 5 4 3 2 1`

C. `10 9 8 7 6 5 4 3 2 1 0`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex5-1"></a>
## 問題ex5-1

**要点**

- `for (String s : args)`はコマンドライン引数を1つずつ取り出す拡張for文
- `v += ++w + Integer.parseInt(s)` は「wを先に増やしてから使う」処理

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

これを`java Main 5 10`として実行したとき、出力される値はどれですか。

A. `15`

B. `16`

C. `18`

D. `20`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex5-2"></a>
## 問題ex5-2

**要点**

- 前置(`++w`)と後置(`w++`)で複合代入の結果が変わる

問題ex5-1のコードで、`v += ++w + Integer.parseInt(s);`を`v += w++ + Integer.parseInt(s);`(後置インクリメントに変更)にしました。

```java
v += w++ + Integer.parseInt(s);
```

同じく`java Main 5 10`として実行したとき、出力される値はどれですか。

A. `16`

B. `18`(変わらない)

C. `20`

D. `NumberFormatException`が発生する

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex3-1"></a>
## 問題ex3-1

**要点**

- `while(count > 1) { count--; array[--count]... }`は1周につき`count`を2回デクリメントする

```java
public class Main {
    public static void main(String[] args) {
        int[] array = {10, 20, 30, 40, 50, 60};
        int count = array.length;
        while(count > 1) {
            count--;
            System.out.print(array[--count] + " ");
        }
    }
}
```

A. `60 40 20`

B. `10 30 50`

C. `20 40 60`

D. `50 30 10`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex3-2"></a>
## 問題ex3-2

**要点**

- 配列を6要素→5要素(奇数)に変更、条件は`count > 1`のまま
- 5要素(奇数)だと`count`は`5→3→1`で止まり(`count=1`で`count>1`が偽になる)、アクセスするインデックスは`3→1`の2回だけ。index 0は一度も参照されない

```java
int[] array = {10, 20, 30, 40, 50};
int count = array.length;
while(count > 1) {
    count--;
    System.out.print(array[--count] + " ");
}
```

A. `50 30 10`

B. `40 20`

C. `40 20 10`

D. `30 10`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex3-3"></a>
## 問題ex3-3

**要点**

- ex3-2と同じ5要素の配列のまま、条件を`count > 1`から`count > 0`に変更
- `count=1`でもループに入ってしまう。`count--`で`0`、`array[--count]`で`--count`が`-1`になり`array[-1]`アクセスで`ArrayIndexOutOfBoundsException`

```java
int[] array = {10, 20, 30, 40, 50};
int count = array.length;
while(count > 0) {
    count--;
    System.out.print(array[--count] + " ");
}
```

A. `40 20 10`(正常終了)

B. `40 20 0`

C. `40 20`まで出力された後、`ArrayIndexOutOfBoundsException`が発生する

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex3-4"></a>
## 問題ex3-4

**要点**

- ex3-1と同じ6要素の配列のまま、条件を`count > 1`から`count > 0`に変更
- ex3-3と同じ条件変更だが、配列が6要素(偶数)だと`count`はちょうど`0`で止まり、`count>0`も`count>1`も同じタイミングで偽になるため例外は発生しない。同じバグでも配列の要素数の偶奇によって表面化するかどうかが変わる、というのがex3-3との対比のポイント

```java
int[] array = {10, 20, 30, 40, 50, 60};
int count = array.length;
while(count > 0) {
    count--;
    System.out.print(array[--count] + " ");
}
```

A. `50 30 10`(元のプログラムと同じ、例外は発生しない)

B. `50 30 10`まで出力された後、`ArrayIndexOutOfBoundsException`が発生する

C. `50 30`まで出力された後、`ArrayIndexOutOfBoundsException`が発生する

D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex5-3"></a>
## 問題ex5-3(難問)

**要点**

- `System.out.println(v)`はfor文が終わったあとの1行だけ
- ループ中に例外が発生すると、その時点で処理は中断され、以降のコードは一切実行されない

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

これを`java Main 5 abc 10`として実行しました。標準出力(コンソール)には何が表示されますか。

A. 何も出力されずにクラッシュする

B. `"6"`が出力されてからクラッシュする

C. `"abc"`が出力されてからクラッシュする

D. `NumberFormatException`はキャッチされないためコンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex5-4"></a>
## 問題ex5-4(難問)

**要点**

- 負の数を含む引数でも計算の流れは同じ(`Integer.parseInt`は符号付き文字列を正しく解釈する)

同じコードを`java Main -2 7 -5`として実行したとき、出力される値はどれですか。

A. `4`

B. `-2`

C. `6`

D. `8`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex6-1"></a>
## 問題ex6-1(難問)

**要点**

- `for (a = chars.length - b; a > b; a -= 2)`は、`a`と`b`をfor文の外で既に宣言済みの変数を再利用しており(forの初期化式で新規宣言していない)、ステップ幅が2という点がトリッキー

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

A. `fd`

B. `fe`

C. `df`

D. `fdb`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex6-2"></a>
## 問題ex6-2(難問)

**要点**

- `int a = 0, b = 1;`を`int a = 0, b = 2;`に変更した場合の出力トレース

```java
char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
int a = 0, b = 2;
for (a = chars.length - b; a > b; a -= 2) {
    System.out.print(chars[a]);
}
```

A. `ed`

B. `fd`

C. `e`

D. コンパイルエラーになる(bが更新されていないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex6-3"></a>
## 問題ex6-3(難問)

**要点**

- `b`は1のまま、ループ条件を`a > b`から`a >= b`に変更した場合の出力トレース

```java
char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
int a = 0, b = 1;
for (a = chars.length - b; a >= b; a -= 2) {
    System.out.print(chars[a]);
}
```

A. `fd`

B. `fdc`

C. `dfb`

D. `fdb`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex6-4"></a>
## 問題ex6-4(難問)

**要点**

- `int a = 0, b = 1;`を`int a = 0, b = -1;`に変更した場合の挙動
- `b`への負値代入自体はコンパイルエラーにならない点も引っかけ

```java
char[] chars = {'a', 'b', 'c', 'd', 'e', 'f'};
int a = 0, b = -1;
for (a = chars.length - b; a > b; a -= 2) {
    System.out.print(chars[a]);
}
```

A. 何も出力されず正常終了する

B. `ArrayIndexOutOfBoundsException`が発生する

C. 無限ループになる

D. コンパイルエラーになる(bに負の値は代入できないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex7-1"></a>
## 問題ex7-1(難問)

**要点**

- 拡張for文の変数に`final`を付けると、ループ内での再代入(`s += ...`など)はコンパイルエラーになる

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

このコードをコンパイルするとどうなりますか。

A. コンパイルは通り、`"AaBb "`のような出力になる

B. 実行時に`UnsupportedOperationException`が発生する

C. コンパイルエラーになる:「finalな変数は拡張for文で使用できません」という専用の構文エラーになる

D. コンパイルエラーになる:「変数sはすでに代入されている可能性があります」というエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：不安。正解だったが自信がなかった。

---

<a id="qex7-2"></a>
## 問題ex7-2(難問)

**要点**

- `final`を外せば拡張for文内での再代入はコンパイル上問題ないが、`s`はarray要素のコピー(値渡し)であり、再代入しても元の配列は変わらない

問題ex7-1のコードから`final`を外しました。

```java
String[] array = {"A ", "B "};
for (String s : array) {
    s += s.toLowerCase();
    System.out.print(s);
}
```

実行結果として正しいものはどれですか。

A. `"A a B b "`と出力されるが、`array`の中身は元の`"A "`、`"B "`のまま変わらない

B. `"A a B b "`と出力され、`array`の中身も`"A a "`、`"B b "`に変わる

C. コンパイルエラーになる(finalを外しても拡張for文の変数は再代入できないため)

D. `"A A a B B b "`と出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex8-1"></a>
## 問題ex8-1(難問)

**要点**

- `1 == 2`はリテラル同士の比較でコンパイル時定数式としてfalseに確定するため、`while (1 == 2)`は`while (false)`と同じ到達不能コード扱いになる

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

A. コンパイルは通り、`"*"`が1回だけ出力される(do-whileの分のみ)

B. コンパイルエラーになる(この文に制御が移ることはありません)

C. コンパイルは通り、`"*"`は一度も出力されない

D. コンパイルエラーになる(1 == 2はboolean型ではないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：不安。

---

<a id="qex8-2"></a>
## 問題ex8-2(難問)

**要点**

- `while(1==2)`の本体に波括弧`{}`を付けてブロックにしても、到達不能コード判定のルール自体は変わらない

```java
do System.out.print("*");
while (false);

while (1 == 2) {
    System.out.print("*");
}
```

A. コンパイルは通り、`"*"`が1回だけ出力される

B. コンパイルは通り、`"*"`は一度も出力されない

C. コンパイルエラーになる(波括弧の対応が取れていないため)

D. コンパイルエラーになる(この文に制御が移ることはありません、波括弧を付けても変わらない)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex8-3"></a>
## 問題ex8-3(難問)

**要点**

- `final int a=1, b=2;`はどちらも定数変数なので、`a + 1 == b`はコンパイル時定数式として畳み込まれ、`1+1==2` → `true`に確定する
- `while(false)`は「本体」が到達不能でエラーになるが、条件がコンパイル時定数式で`true`に確定し、かつループ対象の`break`が一つもない場合は逆に「ループの後に続く文」の方が到達不能コードとして扱われる(ループ本体自体は到達可能なのでエラーにならない)

```java
public class Main {
    public static void main(String[] args) {
        final int a = 1, b = 2;
        while (a + 1 == b) {
            System.out.print("*");
        }
        System.out.print("end");
    }
}
```

A. コンパイルエラーになる(while文の後のSystem.out.print("end");が到達不能コードとして扱われる)

B. コンパイルは通り、無限に"*"が出力され続ける

C. コンパイルエラーになる(while行自体が到達不能コードとして扱われる)

D. コンパイルは通り、"*"は一度も出力されずに"end"が出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：A
迷ったポイント：「コンパイルは通る」という前提自体が誤りで、そもそもコンパイルが通らないという点を見落としていた。

---

<a id="qex8-4"></a>
## 問題ex8-4(難問)

**要点**

- `x, y`が`final`でないため`x + 1 == y`はコンパイル時定数式にならない。コンパイラは値を静的に追跡しないため到達不能コードの特例は一切適用されず、while行も後続のend行もコンパイルエラーにならない
- ただし実行時には`x+1==y`(`1+1==2`)は実際に`true`なので、正真正銘の無限ループになる

問題ex8-3の`a, b`を`final`ではない`x, y`に変えただけで、他はex8-3と同じです。

```java
int x = 1, y = 2;
while (x + 1 == y) {
    System.out.print("*");
}
System.out.print("end");
```

A. 問題ex8-3と同じで、`System.out.print("end");`の行がコンパイルエラーになる

B. コンパイルエラーになる(x, yがfinalでないため+演算子が使えない)

C. コンパイルは全て通るが、実行すると"*"を無限に出力し続ける無限ループになる

D. コンパイルは通り、"end"だけが出力される

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex9-1"></a>
## 問題ex9-1(難問)

**要点**

- while文は条件を満たさなければループ本体を一度も実行しない

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

このコードの条件を`x > 0`に変え、`x`の初期値を`-5`にしました。

```java
int x = -5;
while (x > 0) {
    System.out.print(x);
    x--;
}
```

このループは何回実行されますか。

A. 0回(ループ本体は一度も実行されない)

B. 5回

C. 無限ループになる

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex9-2"></a>
## 問題ex9-2(難問)

**要点**

- 終了条件`x != 0`とデクリメントの向きが噛み合っていないと、目標値からどんどん遠ざかる無限ループになる

元のコード(条件`x != 0`、`x--`のまま)で、`x`の初期値だけを`-3`に変えました。

```java
int x = -3;
while (x != 0) {
    System.out.print(x);
    x--;
}
```

このプログラムの動作として正しいものはどれですか。

A. `"-3-2-1"`と出力されて終了する

B. 無限ループになる(xが0から遠ざかっていくため)

C. 実行時に例外がスローされる

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex10-1"></a>
## 問題ex10-1(難問)

**要点**

- for文の初期化式で宣言した変数(`for (int i = ...)`)のスコープは、そのfor文自体(条件式・更新式・ループ本体)に限られ、for文の外からは参照できない

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

このコードをコンパイルするとどうなりますか。

A. コンパイルは通り、`"1 2 :2"`のような出力になる

B. 実行時に`ArrayIndexOutOfBoundsException`が発生する

C. コンパイルエラーになる:「シンボルを見つけられません」というエラーになる(iのスコープがfor文の外に及ばないため)

D. 無限ループになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex10-2"></a>
## 問題ex10-2(難問)

**要点**

- `i`をfor文の外で宣言すればスコープ問題は解消し、あとは通常のトレース(内側for文が終わった時点でのjの値)を追う問題になる

問題ex10-1のコードで、`i`を外側のfor文の外で宣言するように直しました。

```java
int i = 0;
for (; i < 2; ) {
    i++;
    int j;
    for (j = 0; j < i; j++) {
    }
    System.out.print(j + " ");
}
System.out.println(":" + i);
```

このコードを実行すると、出力はどうなりますか。

A. `"2 1 :2"`

B. `"1 1 :2"`

C. `"2 2 :2"`

D. `"1 2 :2"`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：不安。正解だったが難しく感じ自信がなかった。

---

<a id="qex11-1"></a>
## 問題ex11-1(難問)

**要点**

- `int ar[]`のようなC形式の配列宣言(識別子の後に`[]`を付ける書き方)は有効な構文
- `new int[3]`のように`int`配列を`new`しただけの要素は自動的に`0`で初期化される(明示的に代入していない`array[0][2]`も`0`になる)

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

A. `12_12_`

B. `123_12_`

C. コンパイルエラーになる(int ar[]という書き方は無効なため)

D. `120_12_`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：D
迷ったポイント：`new int[3]`で明示代入しなかった要素(`array[0][2]`)が「存在しない/空欄」になると誤解しており、実際にはint配列の未代入要素は自動的に0で初期化される(`array[0] = {1, 2, 0}`)という点を見落としていた。

---

<a id="qex11-2"></a>
## 問題ex11-2(難問)

**要点**

- 参照型配列(`int[][]`の各要素である`int[]`)の未代入要素の初期値は`null`(要素数0の配列ではなく、配列インスタンスへの参照が存在しない状態)
- 拡張for文が`null`に対して`.length`アクセスの段階で`NullPointerException`を出す

問題ex11-1のコードから、`array[1] = new int[]{1, 2};`の行を削除し、`array[1]`に何も代入しないようにしました。

```java
int[][] array = new int[2][];
array[0] = new int[3];
for (int i = 0; i < 2; i++) {
    array[0][i] = i + 1;
}
for (int ar[] : array) {
    for (int a : ar)
        System.out.print(a);
    System.out.print("_");
}
```

A. `120_`が出力され、そのまま正常終了する

B. `120_`の後に`ArrayIndexOutOfBoundsException`が発生する

C. `120_`の後に`NullPointerException`が発生する

D. コンパイルエラーになる(array[1]に値を代入していないため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：C
迷ったポイント：不安。「未代入の配列要素」を「空の配列([]、要素数0)」と誤解しており、実際には参照型配列の未代入要素の初期値はnull(要素数0の配列ではなく、配列インスタンスへの参照が存在しない状態)であるという点を見落としていた。

---

<a id="qex12-1"></a>
## 問題ex12-1(難問)

**要点**

- コロン形式の`switch`式でも`yield`で値を返せる

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

A. `8`

B. `9`

C. `11`

D. `13`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex12-2"></a>
## 問題ex12-2(難問)

**要点**

- `yield`は`break`と同じくその場で`switch`式を即終了する
- `case 'X':`には`yield`が無いのでフォールスルーして`case 'Y':`の`yield "Y"`まで落ちる(副作用の`print`も実行される)。`case 'Y'`に直接マッチした場合は`case 'X'`の`print`は経由しない

```java
public class Main {
    public static void main(String[] args) {
        char[] chars = new char[]{'X', 'Y', 'Z', 'X'};
        String result = "";
        for (char c : chars) {
            result += switch (c) {
                case 'X':
                    System.out.print("x!");
                case 'Y':
                    yield "Y";
                case 'Z':
                    yield "Z";
                default:
                    yield "?";
            };
        }
        System.out.println(result);
    }
}
```

A. `x!YYZY`

B. `x!x!YYYY`

C. コンパイルエラーになる(caseブロックの並び順が不正なため)

D. `x!x!YYZY`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex12-3"></a>
## 問題ex12-3(難問)

**要点**

- `case 'A': case 'E':`は間に文が無いグループ化なので、`'A'`も`'E'`も副作用なしで直接`yield 5`
- `'P'`は`System.out.print("p!")`を実行後フォールスルーして`default`の`yield 1`へ。`'L'`は`default`に直接マッチするため、`'P'`の`print`文は経由せず副作用なしで`yield 1`。同じ`default`に辿り着く経路でも、フォールスルー経由か直接マッチかで副作用の有無が変わる

```java
public class Main {
    public static void main(String[] args) {
        char[] chars = new char[]{'A', 'P', 'P', 'L', 'E'};
        int value = 0;
        for (char c : chars) {
            value += switch (c) {
                case 'A':
                case 'E':
                    yield 5;
                case 'P':
                    System.out.print("p!");
                default:
                    yield 1;
            };
        }
        System.out.println(value);
    }
}
```

A. `p!p!13`

B. `p!p!p!16`

C. `p!11`

D. コンパイルエラーになる(case 'A': case 'E': のように何も書かず並べるのは不正なため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex13-1"></a>
## 問題ex13-1(難問)

**要点**

- switch文で`break`がないと次のcaseにフォールスルーする
- `continue`はwhile文の条件判定に直接戻る(直後の`index++`は実行されない)

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

このコードの出力はどれですか。

A. `1234`

B. `1223`

C. `12233`

D. `123`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex13-2"></a>
## 問題ex13-2(難問)

**要点**

- `index=0`から始めると最初に'H'のcaseに入り、breakがないため'e'のcaseへフォールスルーして"0"と"1"の2文字が続けて出力される
- index=1以降の挙動はex13-1と同一

問題ex13-1のコードで、`index`の初期値を`0`に変えました(それ以外は同じ)。

```java
String text = "Hello";
int index = 0;
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
```

このコードの出力はどれですか。

A. `011223`

B. `01223`

C. `0112233`

D. `001223`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：A
迷ったポイント：「ex13-1と同じでは」という直感から、先頭に0を1文字足しただけの"01223"を選んでしまった。実際には'H'のcaseにbreakがないため'e'のcaseへフォールスルーし、"0"に続けて"1"も出力される(1文字ではなく2文字増える)。index=1以降はex13-1と同じ"1223"が続くため、正しくは"01"+"1223"="011223"。

---

<a id="qex14-1"></a>
## 問題ex14-1(難問)

**要点**

- `continue outer;`は`l.equals("C ")`が成立した時点(1行目の"C "を処理する直前)で外側forの次の周へジャンプする。1行目は"D "を処理せずに打ち切られ、2行目は一致する要素が無いので最後まで通常通り処理される

```java
public class Main {
    public static void main(String[] args) {
        String[][] letters = {{"A ", "B ", "C ", "D "}
                            , {"E ", "F ", "G ", "H "}};
        String result = "";
        outer:
        for (String[] letter : letters) {
            for (String l : letter) {
                if (l.equals("C ")) {
                    continue outer;
                }
                result += l;
            }
        }
        System.out.println(result);
    }
}
```

A. `A B C D E F G H`

B. `A B E F G H`

C. コンパイルエラーになる(ラベル付きcontinueの位置が不正なため)

D. `A B`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：B
正解：B
迷ったポイント：なし

---

<a id="qex14-2"></a>
## 問題ex14-2(難問)

**要点**

- 拡張for文の変数`letter`への再代入(`letter = new String[]{"Z "}`)は、その周だけのローカルなコピーを別のオブジェクトに向け直しているだけで、元の`letters`配列の要素には一切影響しない。その後の`letter[0]="Y "`もその新しく作った別配列を書き換えているだけ。だから`letters[0][0]`は最初のまま`"A "`、`letters[0].length`も元の4のまま

```java
public class Main {
    public static void main(String[] args) {
        String[][] letters = {{"A ", "B ", "C ", "D "}
                            , {"E ", "F ", "G ", "H "}};
        for (String[] letter : letters) {
            letter = new String[]{"Z "};
            letter[0] = "Y ";
        }
        System.out.print(letters[0][0]);
        System.out.print(letters[0].length);
    }
}
```

A. `Z4`

B. `Y4`

C. `A4`

D. コンパイルエラーになる(拡張for文の変数への再代入は禁止されているため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex15-1"></a>
## 問題ex15-1(難問)

**要点**

- 元のソース(`int count = 0;`がfor文の中で宣言されている)はfor文の外で`count`を参照しておりコンパイルエラーになる(変数スコープの問題)
- `count`をfor文の外に出せばコンパイルは通り、`continue`によりnull要素はカウントのみされて出力はスキップされる

`count`をfor文の外で宣言するように直しました。

```java
String[] cities = {"Paris ", null, "Tokyo ", "Rio ", null};
int count = 0;
for (String city : cities) {
    if (city == null) {
        count++;
        continue;
    }
    System.out.print(city);
}
System.out.println(", N/A:" + count);
```

このコードの出力はどれですか。

A. `Paris Tokyo Rio , N/A:1`

B. `Paris null Tokyo Rio null, N/A:2`

C. `Paris Tokyo Rio , N/A:2`

D. コンパイルエラーになる

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：C
正解：C
迷ったポイント：なし

---

<a id="qex15-2"></a>
## 問題ex15-2(難問)

**要点**

- `System.out.print(city)`はcityがString型でnullでも例外を投げず、文字列"null"をそのまま出力する
- null自体には前後にスペースが付かないため、直前直後の文字列と隙間なく連結される("Paris "の後にすぐ"null"、その後"Tokyo "が続く)

問題ex15-1のコードから、`continue;`だけを取り除きました。

```java
String[] cities = {"Paris ", null, "Tokyo ", "Rio ", null};
int count = 0;
for (String city : cities) {
    if (city == null) {
        count++;
    }
    System.out.print(city);
}
System.out.println(", N/A:" + count);
```

このコードの出力はどれですか。

A. `Paris Tokyo Rio , N/A:2`(変化なし)

B. 実行時に`NullPointerException`が発生する

C. `Paris null Tokyo Rio null, N/A:2`(nullの前後にスペースあり)

D. `Paris nullTokyo Rio null, N/A:2`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：なし

---

<a id="qex16-1"></a>
## 問題ex16-1(難問)

**要点**

- `i==2`(3行目)になるまでは通常通り処理が続く。0行目はA,Bを出力後`j==2`で`continue outer`→1行目へ。1行目も同様にD,Eを出力後`continue outer`→2行目へ。2行目に入った瞬間`i==2`が真になり`break outer`で即終了

```java
public class Main {
    public static void main(String[] args) {
        String[][] str = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}};
        outer:
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                if (i == 2) break outer;
                if (j == 2) continue outer;
                System.out.print(str[i][j]);
            }
        }
    }
}
```

A. `ABDE`

B. `ABDEGH`

C. `AB`

D. コンパイルエラーになる(break outerの位置が不正なため)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：A
正解：A
迷ったポイント：なし

---

<a id="qex16-2"></a>
## 問題ex16-2(難問)

**要点**

- ラベルなしの`break`/`continue`は最も内側のループにしか効かない。0行目は`j==2`の`continue`が最後の要素で発生するだけなのでラベル付きの場合と同じ挙動(AB出力)。1行目に入ると`i==1`で`break`(ラベルなし)が発火するが、これは内側の`for(j...)`ループだけを抜ける動作なので外側の`for(i...)`ループはそのまま継続し2行目に進む。2行目は通常通りG,Hを出力。結果"AB"+"GH"="ABGH"。ラベル付きの場合(1行目で全体が終了しABのみ)との対比がポイント

問題ex16-1の3行版のまま、`break outer`/`continue outer`をラベルなしの`break`/`continue`に変更しました。

```java
public class Main {
    public static void main(String[] args) {
        String[][] str = {{"A", "B", "C"}, {"D", "E", "F"}, {"G", "H", "I"}};
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str[i].length; j++) {
                if (i == 1) break;
                if (j == 2) continue;
                System.out.print(str[i][j]);
            }
        }
    }
}
```

A. `AB`

B. `ABDEGH`

C. `ABGHI`

D. `ABGH`

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>

**実施記録**

回答：D
正解：D
迷ったポイント：不安。

---
