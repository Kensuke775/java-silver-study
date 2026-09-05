### 5.2 Comparator<T>インタフェース

**教科書の例(**`chap1/19`**):**

```java
1  import java.util.Comparator;
2  import java.util.Set;
3  import java.util.TreeSet;
4
5  class SortByLength implements Comparator<String> {
6      @Override
7      public int compare(String o1, String o2) {
8          return o1.length() - o2.length();
9      }
10 }
11 public class Main {
12     public static void main(String[] args) {
13         Set<String> set1 = new TreeSet<>();
14         set1.add("Alexander"); set1.add("Bob");
15         set1.add("Casey"); set1.add("Duke");
16         System.out.println("Natural order: " + set1);
17         Set<String> set2 = new TreeSet<>(new SortByLength());
18         set2.add("Alexander"); set2.add("Bob");
19         set2.add("Casey"); set2.add("Duke");
20         System.out.println("Length order : " + set2);
21         // nullFirst()
22         Comparator<String> comp =
23                 Comparator.nullsFirst(new SortByLength());
24         Set<String> set3 = new TreeSet<>(comp);
25         set3.add("Alexander"); set3.add("Bob");
26         set3.add("Casey"); set3.add("Duke");
27         set3.add(null);
28         System.out.println("  with null  : " + set3);
29         // reverseOrder()
30         Set<String> set4 = new TreeSet<>(Comparator.reverseOrder());
31         set4.add("Alexander"); set4.add("Bob");
32         set4.add("Casey"); set4.add("Duke");
33         System.out.println("Reverse order: " + set4);
34     }
35 }
```

実行結果(javacで検証済み):

```
Natural order: [Alexander, Bob, Casey, Duke]
Length order : [Bob, Duke, Casey, Alexander]
  with null  : [null, Bob, Duke, Casey, Alexander]
Reverse order: [Duke, Casey, Bob, Alexander]
```

**説明したポイント**

- `Comparable<T>`(5.1)は「そのクラス自身に比較ルールを1つだけ埋め込む」仕組みだった。`Comparator<T>`は逆に、**クラスの外から比較ルールを後付けで渡す**仕組み。同じ`String`でも「自然順序(アルファベット順)」「長さ順」「逆順」など、目的に応じて複数の並べ方を使い分けられるのが最大の違い。

- 5-8行目: `SortByLength`は`Comparator<String>`を実装した独立クラス。`compare(o1, o2)`の中身は「2つの文字列の長さを引き算する」という比較ルール。`Comparable`の`compareTo(o)`(比較対象は1つ、暗黙的に`this`と比較)と違い、`compare(o1, o2)`は**引数が2つ**(どちらも他人同士で、`this`は関与しない)。

- 13-16行目: `set1`はコンストラクタに何も渡していない`TreeSet`。この場合は要素自身(`String`)が実装している`Comparable`(自然順序=アルファベット順)が使われる → `Alexander, Bob, Casey, Duke`。

- 17-20行目: `set2`はコンストラクタに`new SortByLength()`を渡している。`TreeSet`はこの`Comparator`があれば、要素の`compareTo()`ではなく**渡された`Comparator`の`compare()`を優先して使う**。文字数の少ない順に並ぶ → `Bob(3), Duke(4), Casey(5), Alexander(9)`。

- 22-23行目: `Comparator.nullsFirst(比較用Comparator)`は「`null`を他のどの要素よりも小さいとみなす」という追加ルールを、既存の`Comparator`(ここでは`SortByLength`)に上乗せしたComparatorを返す。`nullsFirst()`自身は`null`同士以外の比較を知らないため、`null`以外の要素をどう比較するかを引数として渡す必要がある。

- 27行目: 通常の`TreeSet`に`null`を`add()`しようとすると、比較(`compareTo()`)の際に`NullPointerException`になる(要素の`compareTo()`は`null`を想定していないため)。`nullsFirst()`を使えば、比較の**前段階で`null`かどうかを判定して弾く**ため、`null`を安全に追加・ソートできる。

- 30-33行目: `Comparator.reverseOrder()`は「自然順序の逆」で比較する`Comparator`を返す静的メソッド。要素自身の`compareTo()`を使いつつ、符号を反転させているだけ(自分で`(o2, o1)`のように引数を逆にした`compareTo()`を書くのと実質同じ)。

**セクションの核心**

`Comparable`は「そのクラス自身が持つ、たった1つの比較ルール」(内側からの実装)。`Comparator`は「クラスの外側から自由に差し込める、複数持てる比較ルール」(外付けの部品)。同じ`TreeSet`のコンストラクタでも、何も渡さなければ要素の`Comparable`(自然順序)が使われ、`Comparator`を渡せばそちらが優先される、という「上書き」の関係になっている。さらに`naturalOrder()`/`reverseOrder()`/`nullsFirst()`/`nullsLast()`のような`static`メソッドで既製の`Comparator`をすぐ組み立てられる点、`Comparator.comparingInt(a -> a[0])`のような「キー抽出だけ渡せば比較ロジックは内部で用意される」設計(前回のTreeSet<Integer[]>の疑問で確認済み)も、このセクションの延長線上にある。

**参考: `null`要素を`TreeSet`/`TreeMap`に入れたときの挙動(javacで検証済み)**

`Comparable`側には`null`を安全に扱うための仕組みが一切ない。`nullsFirst()`/`nullsLast()`を使わない限り、どのパターンでも最終的に`NullPointerException`になるが、**どこで例外が起きるか(原因)が異なる**。

| 使っているもの | 結果 | 例外の発生元 |
|---|---|---|
| `Comparable`(自然順序、Comparatorなし) | `NullPointerException` | `TreeMap`側の事前チェック(`Objects.requireNonNull()`)。`compareTo()`は呼ばれる前に落ちる |
| 自作`Comparator`(`SortByLength`など)を渡すが`nullsFirst`/`nullsLast`でラップしていない | `NullPointerException` | `TreeMap`側のチェックは通過するが、自作`compare()`の中身(`o1.length()`など)が`null`に対するメソッド呼び出しで落ちる |
| `Comparator.nullsFirst()`/`nullsLast()`でラップ済み | 例外にならず、`null`を最小/最大として正しくソートできる | ― |

自然順序(`Comparable`)の場合と自作`Comparator`の場合とで、同じ`NullPointerException`でもスタックトレースの発生元が全く違う点に注意(前者は`TreeMap.put()`内、後者は自作の`compare()`内)。`nullsFirst()`/`nullsLast()`は、比較ロジックの実行より**前の段階**で`null`かどうかを判定して安全に振り分ける、という役割を担っている。

---

## 演習問題

<a id="問題19-3"></a>
### 問題19-3

```java
1  import java.util.Comparator;
2  import java.util.Set;
3  import java.util.TreeSet;
4
5  class SortByLength implements Comparator<String> {
6      @Override
7      public int compare(String o1, String o2) {
8          return o1.length() - o2.length();
9      }
10 }
11
12 public class Main {
13     public static void main(String[] args) {
14         Set<String> set = new TreeSet<>(
15                 Comparator.nullsLast(new SortByLength()));
16         set.add("Alexander");
17         set.add("Bob");
18         set.add(null);
19         set.add("Casey");
20         System.out.println(set);
21     }
22 }
```

このコードを実行するとどうなるか。

A. `[null, Bob, Casey, Alexander]`
B. `[Bob, Casey, Alexander, null]`
C. 18行目で`NullPointerException`がスローされる
D. コンパイルエラーになる

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `Comparator.nullsLast(比較用Comparator)`は「`null`を他のどの要素よりも大きいとみなす」というルールを既存の`Comparator`(`SortByLength`)に上乗せする。文字数の少ない順(`Bob(3), Casey(5), Alexander(9)`)に並び、`null`は一番大きい扱いなので最後尾に来る → `[Bob, Casey, Alexander, null]`。`nullsFirst()`との違いは`null`を「最小」とみなすか「最大」とみなすかだけで、仕組みは同じ。




正解: B




あなたの回答: B

<a id="問題19-4"></a>
### 問題19-4

```java
1  import java.util.Arrays;
2  import java.util.Comparator;
3
4  public class Main {
5      public static void main(String[] args) {
6          String[] arr = {"Duke", "bob", "Alexander", "casey"};
7          Arrays.sort(arr, Comparator.reverseOrder());
8          System.out.println(Arrays.toString(arr));
9      }
10 }
```

このコードを実行するとどうなるか。

A. `[Duke, casey, bob, Alexander]`
B. `[Alexander, Duke, bob, casey]`
C. `[casey, bob, Duke, Alexander]`
D. `[Alexander, bob, casey, Duke]`

**実施記録**




迷ったポイント: `String`の自然順序(辞書順)における大文字・小文字の扱いを誤解していた(大文字小文字を区別せず単純なアルファベット順になると思い込んでいた)。実際には文字コード(Unicode)順で比較されるため、**すべての大文字(A-Z)はすべての小文字(a-z)より小さい**(`'A'=65`〜`'Z'=90` < `'a'=97`〜`'z'=122`)。




解説(概念): まず自然順序(昇順)で並べると、大文字始まりの`Alexander`, `Duke`が小文字始まりの`bob`, `casey`より必ず先に来る → `[Alexander, Duke, bob, casey]`。`Comparator.reverseOrder()`はこの自然順序をそのまま逆転させるので、`[casey, bob, Duke, Alexander]`になる。単純に「アルファベット順を逆にすればいい」と考えると`D`のような大文字/小文字混在の誤答を作りやすいが、まず自然順序(大文字が先)を正確に出してから逆転させる、という2段階で考える必要がある。




正解: C




あなたの回答: A

**参考: `Arrays.sort(配列, Comparator)`の引数**

問題19-4で初めて登場した`Arrays.sort(arr, Comparator.reverseOrder())`のような2引数版`Arrays.sort()`について。

- 質問: 第一引数と第二引数は、第一引数がその対象の配列で、第二引数がその方法(比較ルール)なんですかね?
- 回答: その通り。`Arrays.sort(配列, Comparator)`の第1引数は「並び替え対象の配列」、第2引数は「その並び替えに使う比較ルール(`Comparator`)」。`Collections.sort(list, comparator)`(`List`用)と全く同じ役割分担で、対象が配列か`List`かが違うだけ。

---

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。
