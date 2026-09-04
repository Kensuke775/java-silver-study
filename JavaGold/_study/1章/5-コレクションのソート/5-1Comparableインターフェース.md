### 5.1 Comparableインタフェース

**教科書の例(**`chap1/18`**):**

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  record Client(String name) implements Comparable<Client> {
5      @Override
6      public int compareTo(Client o) {
7          return this.name.compareTo(o.name);
8      }
9  }
10 record Person(String name) {}
11
12 public class Main {
13     public static void main(String[] args) {
14         Set<Client> set1 = new TreeSet<>();
15         set1.add(new Client("Bob"));
16         set1.add(new Client("Carol"));
17         set1.add(new Client("Alice"));
18         System.out.println(set1);
19         Set<Person> set2 = new TreeSet<>();
20     //  set2.add(new Person("Duke"));
21     }
22 }
```

実行結果(20行目のコメントを外して検証。javacで確認済み):

```
[Client[name=Alice], Client[name=Bob], Client[name=Carol]]
Exception in thread "main" java.lang.ClassCastException:
class Person cannot be cast to class java.lang.Comparable
```

**説明したポイント**

- `Set<Client>`, `Set<Person>`のように、Javaが用意した型(`Integer`, `String`)だけでなく、**自作のクラス/recordを型パラメータに指定してコレクションを作れる**。これは特別なことではなく、これまでの`List<Integer>`などと同じジェネリクスの仕組み。

- この章での`record`の使用は本題(`Comparable`)とは無関係。`record`は単に「シンプルなデータクラスを手早く書く手段」として使われているだけで、普通の`class`で書いても同じ話が成立する。

- `Client`は`Comparable<Client>`を実装し、`compareTo()`で「名前の文字列順」という比較ルールを自分で定義している → `TreeSet`に入れると自動的にそのルールでソートされる(`Alice, Bob, Carol`の順)。

- `Person`は`Comparable`を実装していない → `TreeSet`が要素を並べ替えるために`compareTo()`を呼ぼうとしても、`Person`にはそのメソッド自体が存在しないため、実行時に`ClassCastException`がスローされる。

- `Integer`や`String`を`TreeSet`/`TreeMap`に入れたときに何も意識せず自動でソートされていたのは、`TreeSet`が賢いからではなく、**`Integer`や`String`自身が最初から`Comparable`を実装済みだったから**。

**セクションの核心**

`TreeSet`(や`TreeMap`)自体は「比較する能力(ロジック)」を一切持っていない。持っているのは「比較した結果を使って並べて保持する仕組み」だけ。実際に2つを比較して大小を答える責任は、**中に入れる要素自身**が`compareTo()`として持たなければならない。この主従関係(TreeSet=比較を要求するだけ/要素側=比較ロジックの提供者)が本章の核心。「自分のクラスをTreeSet/TreeMapで自動ソートしたいなら、Comparableを実装してcompareTo()を書く義務がある」というのが結論。

**疑問**:

- 質問: `TreeSet`って標準で`Comparable`の機能が付いていたんじゃなかったっけ?

- 回答: 誤解。`TreeSet`自体は比較ロジックを持たず、要素側の`compareTo()`に丸投げしているだけ。`Integer`/`String`が動いていたのは要素側(Integer/String自身)が`Comparable`を実装済みだったから。「TreeSetに機能が付いている」のではなく「TreeSetは要素がComparableであることを前提に動く」という関係。

- 質問: `Client`も`Person`も同じ「インスタンス」なのに、なぜ片方だけ失敗するのか?

- 回答: 違いは「インスタンスかどうか」ではなく、**そのクラス自体が`Comparable`を実装しているかどうか**の1点だけ。`Client`と`Person`はどちらも`name`フィールドを持つ同じ構造のクラス/レコードだが、`Client`の定義には`implements Comparable<Client>`が書かれており、`Person`には書かれていない、という設計図(クラス定義)自体の違いが原因。

- 質問(問題18-1をめぐる気づき): `TreeSet`の重複判定は`equals()`と`compareTo()`のどちらを見ているのか?

- 回答: `compareTo()`(が`0`を返すかどうか)だけを見ており、`equals()`は一切見ていない。`record`は`equals()`を自動生成してくれるが、`TreeSet`の重複判定にはそれが使われない、という点が最大の落とし穴。`compareTo()`が一部のフィールド(例: `name`だけ)しか見ていないと、`equals()`的には別物のオブジェクト同士でも「同じ」と判定されて片方が黙って追加されない。`HashSet`(`equals()`/`hashCode()`基準)と`TreeSet`(`compareTo()`基準)とで、重複判定の土台になるメソッドが違う、という対比で覚えておくとよい。

- 質問(問題18-2をめぐる気づき): 複数の基準で比較する`compareTo()`(名前→年齢の順)は、どういう仕組みで動いているのか?

- 回答: `if (result != 0) return result;`という書き方がポイント。「1つ目の基準(名前)で差が付いた場合はそこで即座に確定して返す」が、「差が付かなかった場合(`result == 0`)だけ、その`if`の中を素通りして次の行(2つ目の基準=年齢)まで進む」という**バトンタッチ構造**になっている。名前が同じでも、この2段階目で`age`の差という`0`以外の値が返るため、`TreeSet`からは「別物」と判定され、両方とも正しく追加される。

---

## 演習問題

<a id="問題18-1"></a>
### 問題18-1

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  record Client(String name, int age) implements Comparable<Client> {
5      @Override
6      public int compareTo(Client o) {
7          return this.name.compareTo(o.name);
8      }
9  }
10
11 public class Main {
12     public static void main(String[] args) {
13         Set<Client> set = new TreeSet<>();
14         set.add(new Client("Bob", 20));
15         set.add(new Client("Bob", 30));
16         set.add(new Client("Alice", 25));
17         System.out.println(set);
18         System.out.println(set.size());
19     }
20 }
```

このコードを実行するとどうなるか。

A. `[Client[name=Alice, age=25], Client[name=Bob, age=20]]`の後、`2`が出力される

B. `[Client[name=Alice, age=25], Client[name=Bob, age=20], Client[name=Bob, age=30]]`の後、`3`が出力される

C. 15行目で`ClassCastException`がスローされる

D. `[Client[name=Alice, age=25], Client[name=Bob, age=30]]`の後、`2`が出力される

**実施記録**




迷ったポイント: `TreeSet`の重複判定を`HashSet`と同じ「`equals()`(recordなら値が完全一致するか)」だと誤認していた。実際は`TreeSet`は`equals()`を一切見ておらず、**`compareTo()`が`0`を返すかどうか**だけで重複を判定する。`compareTo()`が`name`しか見ていないため、`age`が違っても`Client("Bob",20)`と`Client("Bob",30)`は「同じ」と判定され、後から追加した方(`age=30`)が黙って追加されない。




解説(概念): `TreeSet`/`TreeMap`は「`compareTo()`(または`Comparator`)との整合性」が`equals()`と一致していない場合、`equals()`的には別物でも重複として弾かれてしまう、という有名な注意点。今回`compareTo()`が`name`だけを基準にしているため、`age`だけが違う2つの`Client`は「同じ」とみなされ、`set.size()`は`2`(`Alice`, `Bob(20)`)になる。`Bob(30)`を追加しようとした15行目自体は例外を起こさず、何も起きずに(戻り値`false`で)スキップされる。




正解: A




あなたの回答: B

<a id="問題18-2"></a>
### 問題18-2

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  record Client(String name, int age) implements Comparable<Client> {
5      @Override
6      public int compareTo(Client o) {
7          int result = this.name.compareTo(o.name);
8          if (result != 0) return result;
9          return this.age - o.age;
10     }
11 }
12
13 public class Main {
14     public static void main(String[] args) {
15         Set<Client> set = new TreeSet<>();
16         set.add(new Client("Bob", 30));
17         set.add(new Client("Bob", 20));
18         set.add(new Client("Alice", 25));
19         System.out.println(set);
20     }
21 }
```

このコードを実行するとどうなるか。

A. `[Client[name=Alice, age=25], Client[name=Bob, age=20], Client[name=Bob, age=30]]`

B. `[Client[name=Alice, age=25], Client[name=Bob, age=30]]`

C. `[Client[name=Bob, age=30], Client[name=Bob, age=20], Client[name=Alice, age=25]]`

D. コンパイルエラーになる(同じcompareTo内で2つの基準を使うことはできない)

**実施記録**




迷ったポイント: 減算(`this.age - o.age`)の`this`/`o`どちらが先に評価されるか、正負がどちら向きの並び順になるかの対応で迷ったが、「正なら`this`が後ろ、負なら`this`が前」という規則を踏まえて正しく結論できていた。




解説(概念): `this`は比較の主導権を握る側(呼び出し元)、`o`は比較相手。`this.age - o.age`が正なら`this`の方が大きい(後ろに来る)、負なら`this`の方が小さい(前に来る)。18-2では`Bob(30)`が先に追加され、その後`Bob(20)`が追加される際、新しい要素(`Bob(20)`)を`this`として既存の`Bob(30)`と比較すると`20-30=-10`(負)となり`Bob(20)`が前に来る。結果は名前順、同名内は年齢の昇順で`[Alice(25), Bob(20), Bob(30)]`になる。




正解: A




あなたの回答: A

---

<a id="問題19-1"></a>
### 問題19-1(表1-10: compareTo()の戻り値の符号と並び順)

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  record Score(String player, int point) implements Comparable<Score> {
5      @Override
6      public int compareTo(Score o) {
7          return o.point - this.point;
8      }
9  }
10
11 public class Main {
12     public static void main(String[] args) {
13         Set<Score> set = new TreeSet<>();
14         set.add(new Score("A", 10));
15         set.add(new Score("B", 30));
16         set.add(new Score("C", 20));
17         System.out.println(set);
18     }
19 }
```

このコードを実行するとどうなるか。

A. `[Score[player=A, point=10], Score[player=C, point=20], Score[player=B, point=30]]`(昇順)

B. `[Score[player=B, point=30], Score[player=C, point=20], Score[player=A, point=10]]`(降順)

C. 追加した順のまま`[Score[player=A, point=10], Score[player=B, point=30], Score[player=C, point=20]]`

D. コンパイルエラーになる

**実施記録**




迷ったポイント: `o.point - this.point`という「引き算の順番が通常(`this - o`)と逆」になっている点を見落とし、通常通りの昇順になると誤認していた。表1-10のルール(`this > o`なら正の値→`o→this`の順)に当てはめると、`this`側が大きいほど後ろに回されるため、結果的に`point`の大きい順(降順)になる。




解説(概念): `compareTo()`の引き算の順序(`this - o`か`o - this`か)を入れ替えるだけで、通常の昇順ロジックが丸ごと反転し降順になる。`A(10)`を`this`、`B(30)`を`o`とすると`o.point-this.point=20`(正)となり、表1-10の「this > o → 正 → o→thisの順」に従って`B`が`A`より先に来る。同様の関係が他の組み合わせにも成り立ち、最終的に`point`の大きい順`[B(30), C(20), A(10)]`になる。




正解: B




あなたの回答: A

<a id="問題19-2"></a>
### 問題19-2(参考ボックス: null要素の扱い)

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  record Client(String name) implements Comparable<Client> {
5      @Override
6      public int compareTo(Client o) {
7          if (o == null) return 1;
8          return this.name.compareTo(o.name);
9      }
10 }
11
12 public class Main {
13     public static void main(String[] args) {
14         Set<Client> set = new TreeSet<>();
15         set.add(new Client("Bob"));
16         set.add(null);
17         System.out.println(set);
18     }
19 }
```

`compareTo()`側で`null`を明示的に処理するようにした場合、このコードを実行するとどうなるか。

A. `compareTo()`がnullを処理しているので、`[Client[name=Bob], null]`のように問題なく追加される

B. 16行目で`NullPointerException`がスローされる(`compareTo()`が呼ばれる前に`TreeMap`自身がnullチェックをしているため)

C. 16行目で`NullPointerException`がスローされる(`compareTo()`内の`if`文でスローされるため)

D. コンパイルエラーになる

**実施記録**




迷ったポイント: 「TreeSetにnullを入れるのはダメ」という直感は正しかったが、それを「コンパイルエラーになる」という形で理解していた。実際は`add(null)`という呼び出し自体は文法上完全に正しく(型パラメータが参照型である以上nullは代入可能)、必ずコンパイルは通る。禁止されているように見える挙動は、`TreeMap`(TreeSetの内部実装)が実行時に自分でnullチェックをして例外を投げているだけで、コンパイル時の制約ではない。




解説(概念): `TreeMap.put()`は要素を追加する際、`compareTo()`を呼ぶより前の段階で`Objects.requireNonNull(key)`のようなチェックを自分で行っている。そのため`compareTo()`側で`null`をどう処理するように書いても(7行目のif文があってもなくても)、その処理に到達する前に`NullPointerException`がスローされる。「文法上は許される(コンパイルは通る)が、実行時にそのクラスの実装によって弾かれる」という、raw型や配列共変性などでも見た構図と同じパターン。




正解: B




あなたの回答: D

---

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。
