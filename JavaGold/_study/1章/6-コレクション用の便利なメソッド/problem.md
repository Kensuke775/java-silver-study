# Chapter6 コレクション用の便利なメソッド 問題集

## 目次

- [問題20-1](#問題20-1)
- [問題20-2](#問題20-2)
- [問題21-1](#問題21-1)
- [問題21-2](#問題21-2)
- [問題22-1](#問題22-1)
- [問題22-2](#問題22-2)

<a id="問題20-1"></a>
## 問題20-1

```java
1  import java.util.ArrayList;
2  import java.util.Collections;
3  import java.util.List;
4
5  public class Main {
6      public static void main(String[] args) {
7          List<Integer> list = new ArrayList<>();
8          Collections.addAll(list, 1, 10, 5, 0);
9          System.out.println(Collections.binarySearch(list, 5));
10         Collections.sort(list);
11         System.out.println(list);
12     }
13 }
```

9行目を実行するとどうなるか。

A. `2`(`5`は`list`の2番目(インデックス2)にあるので、正しく見つかる)

B. 例外にはならないが、ソート前なので保証されない値(このJDKでは`-2`)が返る

C. 実行時に`IllegalStateException`がスローされる

D. 9行目でコンパイルエラーになる

**実施記録**




迷ったポイント: なし(一発正解)。「たまたま追加時点で既に昇順だった場合はsort()を呼ばなくても正しく動くのか」という関連質問を追加でした。




解説(概念): `binarySearch()`が正しく動く条件は「`sort()`を呼んだかどうか」ではなく「リストが実際に昇順に並んでいるかどうか」という**状態**そのもの。今回は`addAll(list, 1, 10, 5, 0)`の時点で昇順になっていないため、9行目(まだ`sort()`前)の`binarySearch(list, 5)`は前提条件を満たさず、例外にはならないものの保証されない値(`-2`)を返す。もし最初から`[0, 1, 5, 10]`のように昇順で追加していれば、`sort()`を呼ばなくても正しく`2`が返る(別途javacで検証済み)。




正解: B




あなたの回答: B

<a id="問題20-2"></a>
## 問題20-2

```java
1  import java.util.ArrayList;
2  import java.util.Collections;
3  import java.util.List;
4
5  public class Main {
6      public static void main(String[] args) {
7          List rawList = new ArrayList();
8          Collections.addAll(rawList, 5, "Two", 1.5, 3);
9          System.out.println("before: " + rawList);
10         Collections.sort(rawList);
11         System.out.println("after: " + rawList);
12     }
13 }
```

10行目を実行するとどうなるか。

A. `after: [1.5, 3, 5, Two]`(数値が先、文字列が後の順にソートされる)

B. 10行目で`ClassCastException`(`class java.lang.String cannot be cast to class java.lang.Integer`)がスローされる

C. 10行目で`ClassCastException`(`class java.lang.Integer cannot be cast to class java.lang.String`)がスローされる

D. 10行目でコンパイルエラーになる(raw型に対して`Collections.sort()`は呼べない)

**実施記録**




迷ったポイント: 例外自体は発生すると正しく予測できたが、`ClassCastException`のメッセージの向き(どちらの型がどちらにキャストできないと言われるか)を`before: [5, Two, 1.5, 3]`という表面上の並び順から誤って推測した(先頭の`5`(Integer)が失敗する側だと考え、`String cannot be cast to Integer`を選んでしまった)。




解説(概念): 実際の例外は`class java.lang.Integer cannot be cast to class java.lang.String`(スタックトレースは`at java.lang.String.compareTo()`)。これは「`String`側の`compareTo(String other)`に`Integer`が渡された」ことを意味する。ソートアルゴリズム(TimSort)が内部でどの要素同士をどの順番で比較するかは、リストの見た目の並び順だけから単純に予測できるものではないため、「先頭の要素が原因になるはず」という推測は根拠が薄い。raw型を使うとコンパイル時の型チェックが無効化される(19行目のコメントを外さなくても、`Collections.sort()`自体は文法上呼べてしまう)ため、実行時に初めて`compareTo()`の中で型不一致が発覚する、という点も併せて確認。




正解: C




あなたの回答: B

<a id="問題21-1"></a>
## 問題21-1

```java
1  import java.util.Arrays;
2
3  public class Main {
4      public static void main(String[] args) {
5          int[] a = {1, 2, 3, 4, 5};
6          int[] b = {1, 2};
7          System.out.println(Arrays.compare(a, b));
8          System.out.println(Arrays.mismatch(a, b));
9      }
10 }
```

7行目・8行目を実行するとどうなるか。

A. `-1` → `2`

B. `3` → `2`

C. `1` → `5`

D. `0` → `-1`

**実施記録**




迷ったポイント: `Arrays.mismatch()`が「共通のprefixがある場合、短い方の配列の長さ(=食い違いが起きたインデックス)を返す」という仕様を逆に覚えており、「長い方の長さが返る」と誤って推測した(`b`の長さ2ではなく`a`の長さ5を選んでしまった)。`Arrays.compare()`についても、prefixが完全一致した場合は単純な`-1`/`0`/`1`ではなく、実際の**長さの差**がそのまま返るという点を未検証だった。




解説(概念): `a={1,2,3,4,5}`と`b={1,2}`は、インデックス0,1は値が一致し、インデックス2で`b`側に要素が存在しないことで食い違いが発生する。`mismatch()`はこの「食い違いが起きた位置」である**2**(=短い方`b`の長さ)を返す。`compare()`は、共通部分が全て一致しているため、要素同士の`Integer.compare()`ではなく**配列の長さの差**(`5 - 2 = 3`)を返す。要素の値そのものが途中で食い違うケース(例: `{1,9}`と`{1,5}`)では`Integer.compare()`相当の`-1`/`0`/`1`になるが、prefix一致で長さだけ違うケースでは挙動が変わる、という点がこの問題のひっかけ。




正解: B




あなたの回答: C

<a id="問題21-2"></a>
## 問題21-2

```java
1  import java.util.Arrays;
2  import java.util.Comparator;
3
4  public class Main {
5      public static void main(String[] args) {
6          String[] strArray = {"D", "U", "K", "E"};
7          Arrays.sort(strArray, Comparator.naturalOrder());
8          System.out.println("sort()    : " + Arrays.toString(strArray));
9          System.out.println("strArray  : " + strArray);
10         Object[] objArray = {"Java", 17};
11         Arrays.sort(objArray);
12     }
13 }
```

このコードを実行するとどうなるか。

A. `sort()    : [D, E, K, U]` → `strArray  : [D, E, K, U]` → 11行目で`ClassCastException`

B. `sort()    : [D, E, K, U]` → `strArray  : [Ljava.lang.String;@なんらかの16進数` → 11行目で`ClassCastException`

C. `sort()    : [D, E, K, U]` → `strArray  : [Ljava.lang.String;@なんらかの16進数` → 11行目でコンパイルエラー

D. 7行目でコンパイルエラーになる(`Arrays.sort()`にComparatorを渡す版は存在しない)

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `Arrays.sort(Object[] a)`(Comparatorなし版)は、`Collections.sort(List<T>)`と違ってコンパイル時に`Comparable`境界のチェックが一切ない(引数の型が単なる`Object[]`のため)。そのため`{"Java", 17}`のような型混在の配列でもコンパイルは問題なく通り、実行時に内部で`compareTo()`を呼ぼうとして`String`と`Integer`が噛み合わず`ClassCastException`になる。`println(strArray)`(配列を直接出力)は、配列自体が`toString()`をオーバーライドしていないためハッシュ表記になる(以前確認した`Integer[]`の`TreeSet`の件と同じ理屈)。




正解: B




あなたの回答: B

<a id="問題22-1"></a>
## 問題22-1

```java
1  import java.util.Arrays;
2  import java.util.List;
3
4  public class Main {
5      public static void main(String[] args) {
6          String[] str = {"A", "B", "C"};
7          List<String> list1 = Arrays.asList(str);
8          str[0] = "Alice";
9          list1.set(2, "Carol");
10         System.out.println("list1: " + list1);
11         System.out.println("str  : " + Arrays.toString(str));
12     }
13 }
```

10行目・11行目を実行するとどうなるか。

A. `list1: [Alice, B, Carol]` → `str  : [A, B, C]`

B. `list1: [A, B, Carol]` → `str  : [Alice, B, Carol]`

C. `list1: [Alice, B, Carol]` → `str  : [Alice, B, Carol]`

D. 9行目で`UnsupportedOperationException`がスローされる

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `Arrays.asList(str)`が返す`list1`は、渡した配列`str`をコピーせずそのまま参照する「配列のビュー」。`list1`と`str`は同じメモリ上のデータを見ているだけなので、どちらか一方を変更するともう一方にも反映される(双方向)。`str[0]="Alice"`(配列側の変更)も`list1.set(2,"Carol")`(List側の変更)も同じ実体に対する変更なので、最終的に`list1`と`str`は両方とも`[Alice, B, Carol]`という同じ内容になる。




正解: C




あなたの回答: C

<a id="問題22-2"></a>
## 問題22-2

```java
1  import java.util.ArrayList;
2  import java.util.Arrays;
3
4  public class Main {
5      public static void main(String[] args) {
6          ArrayList<Long> list3 = Arrays.asList(10L);
7          System.out.println(list3);
8      }
9  }
```

6行目を実行するとどうなるか。

A. `[10]`と出力される

B. 6行目で`ClassCastException`がスローされる

C. 6行目でコンパイルエラーになる

D. 問題なく実行でき、`list3.add(20L)`も成功する

**実施記録**




迷ったポイント: なし(一発正解)。「そもそもこの書き方(`ArrayList<Long> list3 = Arrays.asList(10L);`)は構文として書けるのか」という関連質問を追加でした。




解説(概念): 構文自体は普通の変数宣言(「型 変数名 = 式;」)で問題ない。原因は型の不一致: `Arrays.asList()`の戻り値の型は`List<T>`(インタフェース型、しかも実体は`java.util.ArrayList`とは別の非公開クラス)であり、`List`は`ArrayList`の親(より広い型)。「`ArrayList`型の値を`List`型の変数に入れる」(`List<Long> x = new ArrayList<>();`)は成立するが、その逆方向(`List`型の値を、より限定的な`ArrayList`型の変数に直接代入する)はコンパイラが許さない。javacのエラーメッセージも「型変数Tのインスタンスが存在しないので、List<T>はArrayList<Long>に適合しません」という型不一致の趣旨だった。




正解: C




あなたの回答: C
