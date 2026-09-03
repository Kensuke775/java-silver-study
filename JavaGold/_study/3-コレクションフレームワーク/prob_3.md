# Chapter3 コレクションフレームワーク 問題集

## 目次

- [問題11-1](#問題11-1)
- [問題11-2](#問題11-2)
- [問題12-1](#問題12-1)
- [問題12-2](#問題12-2)
- [問題13-1](#問題13-1)
- [問題13-2](#問題13-2)



<a id="問題11-1"></a>
## 問題11-1

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static <T> void tryRemove(List<T> list, T value) {
6          list.remove(value);
7      }
8      public static void main(String[] args) {
9          List<Integer> list = new ArrayList<>();
10         list.add(10);
11         list.add(20);
12         list.add(30);
13         tryRemove(list, 20);
14         System.out.println(list);
15         list.remove(0);
16         System.out.println(list);
17     }
18 }
```

このコードを実行するとどうなるか。

A. `[10, 30]`の後に`[30]`が出力される
B. 13行目で`IndexOutOfBoundsException`がスローされる
C. `[10, 20, 30]`のまま変化せず出力される
D. コンパイルエラーになる

**実施記録**




迷ったポイント: `tryRemove`内部の`list.remove(value)`の`value`が`int`のように見えて、`remove(int index)`(インデックス版)が呼ばれると誤認していた。実際は`value`のコンパイル時の型は型パラメータ`T`(実質`Object`)であり、オーバーロード解決はコンパイル時の型だけで決まるため`remove(Object)`(値指定版)が選ばれる。




解説(概念): `List.remove()`には`remove(int index)`と`remove(Object o)`の2つのオーバーロードがある。引数のコンパイル時の型が`int`ならインデックス版、参照型(`Integer`や型パラメータ`T`など)なら値版が選ばれる。`tryRemove(list, 20)`は`20`がまず`T`(Integer)へオートボクシングされてから渡され、メソッド内部では`T`型の`value`として扱われるため値版`remove(Object)`が呼ばれ、値`20`(インデックス1の要素)が削除されて`[10, 30]`になる。続く15行目`list.remove(0)`は直接のint リテラルなのでインデックス版が選ばれ、インデックス0の要素(`10`)が削除されて`[30]`になる。




正解: A




あなたの回答: B

<a id="問題11-2"></a>
## 問題11-2

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      public static void main(String[] args) {
6          List<Integer> list = new ArrayList<>();
7          list.add(10); list.add(20); list.add(30);
8          for (Integer i : list) {
9              if (i == 20) list.remove(i);
10         }
11         System.out.println(list);
12     }
13 }
```

このコードを実行するとどうなるか。

A. `[10, 30]` が出力される
B. `ConcurrentModificationException` がスローされる
C. `[10, 20, 30]` が出力される(削除されない)
D. コンパイルエラーになる

**実施記録**




迷ったポイント: なし(一発正解)。「最後から2番目の要素を削除する場合は例外が起きない」という`ArrayList`のIteratorの挙動を正確に把握していた。




解説(概念): 拡張for文は内部で`Iterator`の`hasNext()`/`next()`を使う。`hasNext()`は`cursor != size`だけを見ており、変更検知(`ConcurrentModificationException`)は`next()`が呼ばれたときにしかチェックされない。`[10,20,30]`(size=3)でインデックス1(値20)を削除すると`size`が2になり、この時点で`cursor`はすでに2なので`cursor == size`が成立し`hasNext()`が`false`を返してループが例外を起こさずに正常終了する。もし4要素で同様に(最後から2番目ではない位置を)削除すると、`cursor != size`のままなので次の`next()`で`ConcurrentModificationException`が実際にスローされる(javacで両パターンとも検証済み)。




正解: A




あなたの回答: A

<a id="問題12-1"></a>
## 問題12-1

```java
1  import java.util.Set;
2  import java.util.TreeSet;
3
4  public class Main {
5      public static void main(String[] args) {
6          Set<Integer> set = new TreeSet<>();
7          set.add(5);
8          set.add(3);
9          set.add(5);
10         set.add(null);
11         System.out.println(set);
12     }
13 }
```

このコードを実行するとどうなるか。

A. `[3, 5]` が出力される
B. `[5, 3, 5, null]` が出力される
C. 10行目で`NullPointerException`がスローされる
D. コンパイルエラーになる

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `TreeSet`は自然順序付け(`Comparable.compareTo()`)で要素を整列しながら保持するコレクション。要素を追加するたびに既存要素と比較を行うため、`compareTo()`を呼び出せない`null`を追加しようとすると`NullPointerException`がスローされる。`Set`はそもそも重複を許さないため9行目の`set.add(5)`(2回目)は無視され、最終的に`[3, 5]`になるはずだったが、10行目で例外が起き出力(12行目)には到達しない。




正解: C




あなたの回答: C

<a id="問題12-2"></a>
## 問題12-2

```java
1  import java.util.LinkedHashSet;
2  import java.util.Set;
3
4  public class Main {
5      public static void main(String[] args) {
6          Set<Integer> set = new LinkedHashSet<>();
7          boolean a1 = set.add(3);
8          boolean a2 = set.add(1);
9          boolean a3 = set.add(3);
10         boolean a4 = set.add(null);
11         boolean a5 = set.add(null);
12         System.out.println(set);
13         System.out.println(a1 + " " + a2 + " " + a3 + " " + a4 + " " + a5);
14         boolean r1 = set.remove(3);
15         boolean r2 = set.remove(3);
16         System.out.println(set);
17         System.out.println(r1 + " " + r2);
18     }
19 }
```

このコードを実行するとどうなるか。

A. `[3, 1, null]` → `true true false true false` → `[1, null]` → `true false`
B. `[1, 3, null]` → `true true true true true` → `[1, null]` → `true true`
C. `[3, 1, null]` → `true true false true false` → `[]` → `true true`
D. コンパイルエラーになる(Setにnullは追加できないため)

**実施記録**




迷ったポイント: `set.remove(3)`について、`List`の`remove(int index)`/`remove(Object o)`オーバーロード問題(問題11-1)と同じ「コンパイル時の型による分岐」が起きると誤って想定していた。実際は`Set`はインデックスという概念を持たないため`remove(Object o)`という1種類のオーバーロードしか存在せず、常に値としての削除になる(`List`特有の引っかけであり`Set`には当てはまらない)。




解説(概念): `LinkedHashSet`は重複を許さず、挿入順序を保持するコレクション。`add()`は新規追加に成功したときのみ`true`を返し、既存の値(2回目の`3`、2回目の`null`)を追加しようとすると何もせず`false`を返す。`Set`にも`null`は1つだけ追加でき(`TreeSet`と違い`HashSet`/`LinkedHashSet`は`null`を許容)、`remove()`も削除に成功すれば`true`、対象が存在しなければ`false`を返す。




正解: A




あなたの回答: A

<a id="問題13-1"></a>
## 問題13-1

```java
1  import java.util.HashSet;
2  import java.util.Set;
3
4  class Point {
5      int x, y;
6      Point(int x, int y) { this.x = x; this.y = y; }
7      @Override
8      public boolean equals(Object o) {
9          if (!(o instanceof Point)) return false;
10         Point p = (Point) o;
11         return this.x == p.x && this.y == p.y;
12     }
13 }
14
15 public class Main {
16     public static void main(String[] args) {
17         Set<Point> set = new HashSet<>();
18         set.add(new Point(1, 2));
19         set.add(new Point(1, 2));
20         System.out.println(set.size());
21         System.out.println(new Point(1, 2).equals(new Point(1, 2)));
22     }
23 }
```

このコードを実行するとどうなるか。

A. `2`の後に`true`が出力される
B. `1`の後に`true`が出力される
C. `2`の後に`false`が出力される
D. コンパイルエラーになる(`equals`だけをオーバーライドすることはできない)

**実施記録**




迷ったポイント: `equals()`をオーバーライドすれば`add()`が中身を比較して重複を検出してくれると誤認していた。実際は`HashSet`の重複判定は「`hashCode()`で同じバケツに入るか」→「同じバケツ内でのみ`equals()`比較」という2段階であり、`hashCode()`をオーバーライドしていないと(デフォルトはインスタンスごとに異なる値)、`equals()`的には等しいオブジェクト同士でも別バケツに入り`equals()`が一度も呼ばれず重複として検出されない。




解説(概念): `equals()`と`hashCode()`は必ずセットでオーバーライドする、というオブジェクト契約(`equals`が等しいなら`hashCode`も等しくなければならない)が破られている例。18〜19行目の2つの`Point`は`equals()`的には等しいが、`hashCode()`(デフォルト実装)が異なるため別々のバケツに格納され、`set.size()`は`2`のままになる。一方21行目は`HashSet`を経由せず直接`equals()`を呼んでいるだけなので、正しく`true`が返る。




正解: A




あなたの回答: B

<a id="問題13-2"></a>
## 問題13-2

```java
1  import java.util.HashSet;
2  import java.util.Set;
3
4  record Pair(int[] values) {}
5
6  public class Main {
7      public static void main(String[] args) {
8          int[] arr = {1, 2, 3};
9          Pair p1 = new Pair(arr);
10         Pair p2 = new Pair(arr);
11         Pair p3 = new Pair(new int[]{1, 2, 3});
12         System.out.println(p1.equals(p2));
13         System.out.println(p1.equals(p3));
14         Set<Pair> set = new HashSet<>();
15         set.add(p1);
16         set.add(p2);
17         set.add(p3);
18         System.out.println(set.size());
19     }
20 }
```

このコードを実行するとどうなるか。

A. `true` `true` `1`
B. `true` `false` `2`
C. `false` `false` `3`
D. `true` `true` `3`

**実施記録**




迷ったポイント: recordの自動生成equalsは常に「中身(値)を比較してくれる」と思い込んでいたが、配列型フィールドだけは例外。配列自体は`equals()`をオーバーライドしていない(Object由来のまま=参照比較)ため、recordの`equals()`がフィールドを`Objects.equals()`で比較する際、配列フィールドについては中身ではなく参照が比較される。




解説(概念): `p1`と`p2`は同じ配列インスタンス`arr`を共有しているため`equals()`は`true`。`p3`は中身は同じ`{1,2,3}`だが別の配列インスタンスなので`equals()`は`false`。`HashSet`への追加も同じ基準で判定されるため、`p1`/`p2`は重複として1つにまとまり、`p3`は別要素として追加され、最終的に`set.size()`は`2`になる。recordのフィールドに配列を使うと「値で比較される」という前提が崩れる、という代表的な落とし穴。




正解: B




あなたの回答: A
