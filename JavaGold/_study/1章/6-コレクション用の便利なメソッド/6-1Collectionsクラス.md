### 6.1 Collectionsクラス

**教科書の例(**`chap1/20`**):**

```java
1  import java.util.ArrayList;
2  import java.util.Collections;
3  import java.util.List;
4
5  public class Main {
6      public static void main(String[] args) {
7          List<Integer> list = new ArrayList<>();
8          Collections.addAll(list, 1, 10, 5, 0);
9          System.out.println("addAll() : " + list);
10         Collections.reverse(list);
11         System.out.println("reverse(): " + list);
12         Collections.sort(list);
13         System.out.println("sort()   : " + list);
14         System.out.println("binarySearch(): "
15                     + Collections.binarySearch(list, 10));
16         List rawList = new ArrayList();
17         Collections.addAll(rawList, 0, "One", 1.5);
18         System.out.println("rawList  : " + rawList);
19      // Collections.sort(rawList);  // ClassCastException
20     }
21 }
```

実行結果(javacで検証済み):

```
addAll() : [1, 10, 5, 0]
reverse(): [0, 5, 10, 1]
sort()   : [0, 1, 5, 10]
binarySearch(): 3
rawList  : [0, One, 1.5]
```

**説明したポイント**

- `Collections`は、`List`や`Set`などのコレクション自体に対して便利な操作を提供する**static メソッドの集まり**(`Collections`自身はインスタンス化しない、ユーティリティクラス)。似た名前の`Collection`(単数形、`List`/`Set`の親インタフェース)と混同しないよう注意。

- 8行目: `Collections.addAll(list, 1, 10, 5, 0)`は、可変長引数で渡した複数の要素をまとめて`list`に追加する。`list.add(1); list.add(10); ...`を1行で書けるショートカット。

- 10行目: `Collections.reverse(list)`は、リストの**現在の並び順をそのまま逆転**させる(ソートではない点に注意)。追加順`[1, 10, 5, 0]`を逆にするので`[0, 5, 10, 1]`。

- 12行目: `Collections.sort(list)`で初めて昇順ソート`[0, 1, 5, 10]`になる。この`sort()`は要素(`Integer`)の`Comparable`(自然順序)を使う版で、5.1で学んだ`T extends Comparable<? super T>`という型パラメータの境界を持つメソッド。

- 14-15行目: ソート済みの`list`に対して`Collections.binarySearch(list, 10)`を実行し、`10`のインデックス`3`を取得(二分探索。事前にソートが必須、という前提はここでも同じ)。

- 16-18行目: `List rawList = new ArrayList()`は型パラメータを付けない**raw型**。`Integer`, `String`, `Double`という全く異なる型を1つのリストに混在させて追加できてしまう(コンパイラの型チェックが効かない)。

- 19行目のコメントアウトを外すと`Collections.sort(rawList)`は`ClassCastException`になる(`Integer`と`String`を比較しようとして失敗する)。これは以前確認した「`Collections.sort()`はコンパイル時に`Comparable`境界をチェックする」という話と矛盾しない。**raw型を使うとその型チェック自体が無効化される**ため、コンパイルは通ってしまい、実行時に初めて失敗する。

**セクションの核心**

`Collections`クラスは「コレクションに対してよく使う定型処理」をstaticメソッドとしてまとめて提供している(`addAll`/`reverse`/`sort`/`binarySearch`など)。この章で既に学んだ`Comparable`/`Comparator`は、その裏側で`Collections.sort()`のようなメソッドから実際に呼び出される「比較ロジックの提供者」という位置づけになる。またraw型の例は、ジェネリクス(2章)の型安全性がコンパイル時のチェックに依存していること、それを迂回するとどうなるかを再確認させる内容になっている。

**参考: 二分探索(binary search)はソート済み前提のアルゴリズム(Java固有ではない)**

`Collections.binarySearch()`は「真ん中の要素と比較して探索範囲を半分に絞り込む」というアルゴリズムで動作するため、**リストが事前に昇順ソートされていることが絶対条件**。ソートされていないリストに使うと、正しい結果は保証されない。

- 見つかった場合: そのままインデックスを返す(`0`以上の値)
- 見つからなかった場合: `-(挿入すべき位置 + 1)`という**負の値**を返す(`-1`以下の値)。単に`-挿入位置`にしないのは、挿入位置が`0`のケースだと`-0 = 0`になり、「先頭に挿入すべき(見つからなかった)」と「インデックス0で見つかった」が区別できなくなるため。`+1`することで、戻り値が`0`以上か負かだけで成功/失敗を確実に判定できるようにしている。挿入位置に戻したい場合は`-result - 1`で逆算できる。

二分探索が有利になるかどうかは「要素数の規模」よりも「**すでにソート済みか**」「**同じリストに何度も検索をかけるか**」の方が重要な判断基準になる。未ソートのリストに対して1回しか検索しないなら、ソートのコスト(`O(n log n)`)込みで考えると線形探索(`O(n)`)の方が有利な場合もある。逆に、ソート済みのリストに繰り返し検索をかけるなら、二分探索(`O(log n)`)が圧倒的に有利。

この「事前ソート必須」「未ソート/一回きりの検索なら線形探索で十分」という性質は**アルゴリズムそのものの前提条件**であり、Java固有の話ではない。JavaScript、Python、C++など、どの言語で二分探索を実装・使用しても同じ制約が付いて回る。

---

## 演習問題

<a id="問題20-1"></a>
### 問題20-1

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
### 問題20-2

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
### 問題21-1

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
### 問題21-2

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
### 問題22-1

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
### 問題22-2

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

---

**疑問**:

- 質問: 降順に並んだリストに`binarySearch()`を使うとどうなるのか?
- 回答: `Collections.binarySearch(list, key)`(Comparatorなし版)は**要素の自然順序(昇順)**を前提に動くため、降順リストに使うと見つかるはずの要素が見つからず、誤った結果になる(検証: `[10, 8, 6, 4, 2, 0]`で`4`を検索→`-1`という誤った値)。降順で使いたい場合は、`sort()`時と`binarySearch()`時に**同じComparator**(`Comparator.reverseOrder()`など)を一貫して渡せば正しく動く(同条件で検証→`4`が正しくインデックス3で見つかった)。結論として「昇順必須」というより「**ソート時と検索時の比較基準を一致させる**」がルールの本体。

- 質問: 見つかった場合のインデックスにも`+1`のような加工が入るのか?
- 回答: 入らない。`+1`(正確には`-(挿入位置+1)`)の加工が入るのは**見つからなかった場合(戻り値が負)のときだけ**。見つかった場合(戻り値が`0`以上)は、そのまま普通の0始まりインデックスがそのまま返る。

- 質問: 問題20-1で、たまたまリストが最初から昇順に並んでいたら`sort()`を呼ばなくても`binarySearch()`は正しく動くのか?
- 回答: 動く。`binarySearch()`が正しく動く条件は「`sort()`というメソッドを呼んだかどうか」ではなく「**リストが実際に昇順に並んでいるという状態**」そのもの。`sort()`はその状態を作るための手段の1つに過ぎない。

- 質問: `Collections`クラスと`Arrays`クラスの違いは何か? `Collections`だけが二分探索を持っているのか?
- 回答: 二分探索は**両方に存在する**(`Collections.binarySearch(list, key)`と`Arrays.binarySearch(arr, key)`)。両クラスは「対象が`List`/`Set`か、配列か」が違うだけの対になった存在で、ソート・二分探索など似た機能を並行して持っている。配列(プリミティブOK・ジェネリクス非対応)と`List`(参照型限定・ジェネリクス対応)という構造の違いがあるため、専用の道具箱が別々に用意されている。

- 質問: `Map`は`Collections`/`Collection`の中でどういう位置づけか?
- 回答: `Map<K, V>`は`Collection<E>`インタフェースを継承していない、**別系統のデータ構造**(`List`/`Set`/`Queue`/`Deque`はすべて`Collection`の仲間だが、`Map`だけ違う)。ただし`Collections`(ユーティリティクラス)自体は`Collections.unmodifiableMap()`のように`Map`用のメソッドも提供している。`Map`の`entrySet()`/`keySet()`/`values()`が、`Map`の中身を`Collection`系のビューとして取り出すための橋渡し役になっている。

- 質問: 配列と`List`の大まかな違いは「配列はプリミティブ型を持てるが`List`は持てない」でいいか?
- 回答: それに加えて「**サイズが固定か可変か**」も同じくらい重要な違い。配列=プリミティブOK・サイズ固定、`List`=プリミティブNG(オートボクシング必須)・サイズ可変、の2軸で覚える。`Arrays.asList()`が返す`List`が`add()`できず`UnsupportedOperationException`になるのは、配列由来の「サイズ固定」という制約を引き継いでいるため。

- 質問: `Arrays.asList()`の中身は参照型なのか?
- 回答: 基本的には参照型だが、**渡し方によって挙動が変わる**罠がある。①`Arrays.asList(8, 11, 17)`のように個別の`int`リテラルを可変長引数で渡すと、各要素がオートボクシングされ`List<Integer>`(要素3個)になる。②`int[] intArr`のように**プリミティブ型配列そのもの**を渡すと、`int`はオートボクシングされないが配列自体は参照型(`Object`のサブクラス)なので、`T=int[]`と解釈され、**配列全体を1個の要素とする`List<int[]>`(要素1個)**になってしまう。③`String[]`のように元から参照型の配列を渡した場合は、`T=String`と正しく解釈され要素がバラバラの`List<String>`(要素3個)になる。プリミティブ型配列をそのまま`asList()`に渡すと要素数が「1」になる、というのがGold試験頻出の落とし穴。

**参考: `Arrays.compare()`/`Arrays.mismatch()`の長さ違い配列に対する挙動(問題21-1で確認)**

`a={1,2,3,4,5}`, `b={1,2}`のように、片方がもう片方の**prefix(先頭部分)と完全一致**している場合、`compare()`/`mismatch()`は要素の値同士を比較する通常のケースとは異なる挙動になる。

- `Arrays.mismatch(a, b)` → **短い方の配列の長さ**(=食い違いが起きたインデックス)を返す。今回は`b`の長さ`2`。「長い方の長さが返る」という誤解をしやすいので注意(直感的には長い方`a`のどこかで違いが出た、と考えがちだが、実際には短い方`b`が先に尽きた地点が「食い違い」として扱われる)。
- `Arrays.compare(a, b)` → 共通部分が完全一致しているため、要素同士の`Integer.compare()`(`-1`/`0`/`1`)ではなく、**配列の長さの差**(`5 - 2 = 3`)がそのまま返る。要素の値自体が途中で食い違う通常のケース(例: `{1,9}`と`{1,5}`)では`-1`/`0`/`1`に丸められた値が返るのと対照的。

- 質問: `ArrayList<Long> list3 = Arrays.asList(10L);`のような書き方はそもそも構文として書けるのか?
- 回答: 構文自体は普通の変数宣言(「型 変数名 = 式;」)で問題ない。実際に落ちる原因は**型の不一致**。`ArrayList`を新しく作るときの基本形は`new ArrayList<>()`(コンストラクタを直接呼ぶ)だが、`Arrays.asList()`は`new`を伴わない**staticメソッド呼び出し**であり、戻り値の型も`List<T>`(インタフェース)で、しかも実体は`java.util.ArrayList`とは別の非公開クラス。「`List`型の値を、より限定的な`ArrayList`型の変数に直接代入する」ことをコンパイラが許さないため、コンパイルエラーになる。

- 質問: `new`をいつ書いて、いつ書かないのかがよくわからない。
- 回答: 判断基準は「コンストラクタを直接呼ぶか、メソッド(staticメソッド含む)を呼ぶか」。`new ArrayList<>()`のように自分でインスタンスを直接生成するときは`new`が必要。一方`Arrays.asList()`や`Collections.sort()`のような**メソッド**を呼ぶときは、呼び出す側は`new`を書かない(そのメソッドの内部で実際には`new`が使われていても、それは呼び出し側からは見えない)。`asList()`は「`new`する処理をメソッドの中に隠して、呼び出し側から`new`を書かなくて済むようにしている」静的ファクトリメソッドという設計パターンの一種。

- 質問: `List`は可変(mutable)なのか?
- 回答: 「どうやって作ったListか」によって3段階に分かれる、というのが検証結果(javacで確認済み)。

  | 作り方 | サイズ変更(`add`/`remove`) | 中身の変更(`set`) |
  |---|---|---|
  | `new ArrayList<>()` | OK(自由に増減できる) | OK |
  | `Arrays.asList(...)` | NG(`UnsupportedOperationException`) | OK |
  | `List.of(...)`(Java 9以降) | NG | NG(`UnsupportedOperationException`) |

  `ArrayList`という**クラス名**自体に可変性が保証されているのではなく、`new ArrayList<>()`で作った本物の`ArrayList`インスタンスか、`Arrays.asList()`/`List.of()`のような別の制限された実装を返すメソッドで作ったものか、によって同じ`List`型の変数でも挙動が全く異なる。

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。
