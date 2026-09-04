# Chapter5 コレクションのソート 問題集

## 目次

- [問題18-1](#問題18-1)
- [問題18-2](#問題18-2)
- [問題19-1](#問題19-1)
- [問題19-2](#問題19-2)

<a id="問題18-1"></a>
## 問題18-1

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
## 問題18-2

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

<a id="問題19-1"></a>
## 問題19-1(表1-10: compareTo()の戻り値の符号と並び順)

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
## 問題19-2(参考ボックス: null要素の扱い)

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
