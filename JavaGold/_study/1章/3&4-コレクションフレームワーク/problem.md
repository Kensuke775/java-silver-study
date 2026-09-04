# Chapter3 コレクションフレームワーク 問題集

## 目次

- [問題11-1](#問題11-1)
- [問題11-2](#問題11-2)
- [問題12-1](#問題12-1)
- [問題12-2](#問題12-2)
- [問題13-1](#問題13-1)
- [問題13-2](#問題13-2)
- [問題14-1](#問題14-1)
- [問題14-2](#問題14-2)
- [問題15-1](#問題15-1)
- [問題15-2](#問題15-2)
- [問題16-1](#問題16-1)
- [問題16-2](#問題16-2)
- [問題17-1](#問題17-1)
- [問題17-2](#問題17-2)

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

--

5要素で試して、正確な流れを確認します。

```java

public class CME5 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(10); list.add(20); list.add(30); list.add(40); list.add(50);
        System.out.println("ループ開始");
        for (Integer i : list) {
            System.out.println("  チェック中: " + i);
            if (i == 20) {
                System.out.println("  20を削除します");
                list.remove(i);
                System.out.println("  削除完了。次のnext()を待つ");
            }
        }
        System.out.println("ループ終了。これから出力する");
        System.out.println(list);
    }
}

```



```
ループ開始
  チェック中: 10
  チェック中: 20
  20を削除します
  削除完了。次のnext()を待つ
Exception in thread "main" java.util.ConcurrentModificationException

```

「ループ終了。これから出力する」の行は一度も表示されず、そのままプログラムがクラッシュしています。

**処理の流れを1ステップずつ整理すると:**

1. `for (Integer i : list)`は、内部的に`Iterator`の`hasNext()`→`next()`を繰り返し呼んでいる。
2. `10`をチェック(問題なし)。
3. `next()`で`20`を取り出してチェック → `if (i == 20)`が成立 → `list.remove(i)`を実行。この時点では**まだ何も例外は起きない**(削除自体は普通に成功する)。
4. ループの次の周回に入るため、再び`hasNext()`が呼ばれる。5要素の場合、`removeの後の状態(size=4)`と、cursorの位置がズレている(第2-最後のケースとは違い一致しない)ため`hasNext()`は`true`を返す。
5. `true`だったので`next()`が呼ばれる。**まさにこの**`next()`**の呼び出しの最初の処理として、変更検知チェック(**`checkForComodification()`**)が行われ、ここで矛盾が検出されて**`ConcurrentModificationException`**がスローされる。**
6. 例外は`for`文の外まで伝播し、11行目の`System.out.println(list)`には**一生たどり着かない**。

つまり「削除した瞬間」に例外が起きるのではなく、**削除した後、ループがもう一周しようとして次の要素を取りに行った瞬間(`next()`の呼び出し時)**に初めて検出される、というのが正確なタイミングです。今回の問題(3要素、最後から2番目を削除)では、たまたま「もう一周しようとする」前に`hasNext()`が`false`を返してループ自体が終わってしまうため、この`next()`によるチェックに一度も到達せず、例外が起きずに済んでいた、という違いになります。

**まとめ: 3要素のケースで`30`はどうなっているのか(出力方法を変えて検証)**

「削除された要素(`20`)以外は全部出力されるはず」という予想を検証すると、実は`30`は**ループの中で`i`として出力する場合でも一度も出力されない**ことが分かった。

```java
for (Integer i : list) {
    System.out.println("ループ内でi自体を出力: " + i);
    if (i == 20) list.remove(i);
}
System.out.println("ループ終了後にlistを出力: " + list);
```
```
ループ内でi自体を出力: 10
ループ内でi自体を出力: 20
ループ終了後にlistを出力: [10, 30]
```

`30`はどちらの出力方法でも「ループ処理中に見えている」ことは一度もない。理由は例外や削除ではなく、**`hasNext()`が`false`を返してループ自体がそこで正常終了してしまうから**:

1. `10`を`i`として取り出す(`cursor`: 0→1)→ 出力。
2. `20`を`i`として取り出す(`cursor`: 1→2)→ 出力 → `list.remove(20)`実行、`size`が3→2になる。
3. 次の周回に入る前に`hasNext()`が呼ばれる → 中身は`cursor(2) != size(2)`という単純な比較だけ → **一致してしまう**ため`false`を返す。
4. `false`なので`for`文はそのまま(例外を出さずに)正常終了する。`next()`は二度と呼ばれないので、変更検知のチェック自体も一度も行われない。

つまり`30`は「削除されたから消えた」のでも「例外で処理が止まった」のでもなく、**イテレータの内部カウンタ(`cursor`と`size`)がたまたま一致してしまい、「もう次はない」と誤認されて、ループの対象として一度も見てもらえなかった**だけ。最終的な`list`の中身としては(触られていないので)`30`はそのまま残っている、という「ループでは見えないが、データとしては生き残っている」という2つの視点のズレがこの問題のポイント。

(→ 5要素の場合はこのズレが起きないため、次の`next()`で`ConcurrentModificationException`が実際にスローされる。上記のトレース参照。)

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

**まとめ: equals()/hashCode()のオーバーライド有無による4パターン**

「equals()を書き換えたら中身比較になる」「hashCodeも一緒にオーバーライドしないとHashSetで重複排除できない」という2つの話を混同しやすいので、軸を分けて整理する。

- **`equals()`の戻り値**を決めるのは「`equals()`自体がオーバーライドされているか」だけ。`hashCode()`の有無は無関係。
- **`HashSet`で重複が正しく排除されるか**は「`equals()`と`hashCode()`の両方が、同じフィールドを基準に一貫してオーバーライドされているか」で決まる。

| 状態 | `equals()`を直接呼んだ結果 | `HashSet`でのサイズ |
|---|---|---|
| ①両方オーバーライド(同じフィールド基準) | `true` | 1(正しく重複排除) |
| ②equalsだけオーバーライド | `true` | 2(hashCodeが違うので別バケツに入り、equalsが呼ばれる機会が来ない) |
| ③hashCodeだけオーバーライド | `false` | 2(同じバケツには入るが、equalsが参照比較のままなので「別物」と判定される) |
| ④両方ともオーバーライドなし | `false` | 2(そもそもデフォルトのequalsが参照比較なので当然) |

`Object`のデフォルト`equals()`は参照比較(`==`と同じ)。これは「まだ誰も中身比較用に書き換えていない自作クラス」の場合の話であり、`Integer`/`String`のような標準クラスは最初から中身比較する`equals()`にオーバーライド済みなので、`new`で別インスタンスを作っても`equals()`は正しく`true`を返す(`new Integer(10).equals(new Integer(10))`→`true`)。

**関連する引っかけ: `StringBuilder`は`equals()`をオーバーライドしていない**

`String`と見た目が似ているため誤解しやすいが、`StringBuilder`は`equals()`を`Object`のデフォルト(参照比較)のまま使っている。中身が同じでも別インスタンスなら`false`になる。

```java
StringBuilder sb1 = new StringBuilder("abc");
StringBuilder sb2 = new StringBuilder("abc");
sb1.equals(sb2);                        // false(参照比較)
sb1.toString().equals(sb2.toString());  // true(Stringに変換してから比較すればOK)
```

`StringBuilder`の中身を比較したい場合は、`toString()`で`String`に変換してから`equals()`を使う必要がある(javac/javaで検証済み)。

**実装例: この`Point`に`hashCode()`を追加するとしたら**

`equals()`が比較に使っているフィールド(`x`, `y`)を、そのまま`hashCode()`の計算にも使うのが基本パターン。一番簡単なのは`Objects.hash(...)`に可変長引数でフィールドを渡す書き方。

```java
import java.util.Objects;

class Point {
    int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);   // equals()と同じフィールド(x, y)を使う
    }
}
```

これを追加すると`set.size()`は`1`(正しく重複排除)になる(javacで検証済み)。自分で計算式を書く場合の定番パターンも参考までに:

```java
@Override
public int hashCode() {
    int result = 17;
    result = 31 * result + x;
    result = 31 * result + y;
    return result;
}
```

どちらでも良いが、実務では`Objects.hash(...)`一択で書かれることが多い。大事なのは計算方法そのものではなく、「`equals()`が見ているフィールドと`hashCode()`が見ているフィールドを一致させる」という点。

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

<a id="問題14-1"></a>
## 問題14-1

```java
1  import java.util.ArrayDeque;
2  import java.util.Queue;
3
4  public class Main {
5      public static void main(String[] args) {
6          Queue<String> queue = new ArrayDeque<>();
7          queue.add("A");
8          queue.offer("B");
9          queue.offer(null);
10         System.out.println(queue.size());
11         System.out.println(queue.poll());
12     }
13 }
```

このコードを実行するとどうなるか。

A. `2`の後に`A`が出力される
B. `2`の後に`null`が出力される
C. 9行目で`NullPointerException`がスローされる
D. `offer(null)`は`false`を返すだけなので、`2`の後に`A`が出力される

**実施記録**




迷ったポイント: なし(一発正解)。「`offer`のような安全な版はfalseを返すだけで例外にはならない」という一般則の例外に、`null`要素の扱いがあることを正確に把握していた。




解説(概念): `ArrayDeque`は`null`要素を一切許容しない。`offer()`は容量オーバーなどの失敗時には`false`を返す「安全な版」だが、`null`を渡した場合だけは`add()`と同様に`NullPointerException`をスローする。理由は`poll()`/`peek()`が「空である」ことを表すために`null`を返す仕様になっており、要素として`null`を許すと「本当に空なのか、`null`という値が入っているだけなのか」を区別できなくなるため。




正解: C




あなたの回答: C

<a id="問題14-2"></a>
## 問題14-2

```java
1  import java.util.ArrayDeque;
2  import java.util.Deque;
3
4  public class Main {
5      public static void main(String[] args) {
6          Deque<Integer> deque = new ArrayDeque<>();
7          deque.add(1);
8          deque.add(2);
9          deque.push(3);
10         System.out.println(deque);
11         System.out.println(deque.poll());
12         System.out.println(deque.pop());
13         System.out.println(deque);
14     }
15 }
```

このコードを実行するとどうなるか。

A. `[3, 1, 2]` → `3` → `1` → `[2]`
B. `[1, 2, 3]` → `1` → `3` → `[2]`
C. `[3, 1, 2]` → `1` → `3` → `[2]`
D. `[1, 2, 3]` → `1` → `2` → `[3]`

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `ArrayDeque`は`Queue`と`Deque`の両方のインタフェースを実装しており、`add()`(Queue由来、末尾に追加)と`push()`(Deque由来、`addFirst()`相当で先頭に追加)が混在すると、要素の並びが直感に反する。`add(1)`, `add(2)`で`[1, 2]`、`push(3)`で先頭に追加され`[3, 1, 2]`になる。続く`poll()`(先頭取得、Queue由来)と`pop()`(先頭取得、Deque/Stack由来)は呼び方こそ違うが、どちらも「先頭から取り出す」という同じ動作をするため、`3`→`1`の順で取り出され、最終的に`[2]`が残る。




正解: A




あなたの回答: A

**まとめ: `Deque`のメソッドは「例外系」と「安全系」の2グループにきれいに分類できる**

`push`/`pop`は名前だけ見ると独立した特別な操作に見えるが、実体は`addFirst`/`removeFirst`の別名でしかないため、他のメソッドと同じ2グループのどちらかに機械的に分類できる。

```
例外系(失敗すると例外をスロー): add, addFirst, addLast, remove, removeFirst, removeLast, element, getFirst, getLast, push, pop
安全系(失敗するとnull/falseを返す): offer, offerFirst, offerLast, poll, pollFirst, pollLast, peek, peekFirst, peekLast
```

- `push()`≒`addFirst()`なので「例外系」。容量制限のあるDeque実装で満杯のときに追加しようとすると`IllegalStateException`(`ArrayDeque`はサイズ無制限なので実際にはまず起きない)。
- `pop()`≒`removeFirst()`なので「例外系」。空の状態で呼ぶと`NoSuchElementException`(問題15-2で確認済み)。

`poll()`(Queue由来、安全系)と`pop()`(Stack由来、例外系)は、どちらも「先頭から取り出す」という同じ方向の操作だが、失敗したときの挙動が違うグループに属している、という点を混同しないよう注意。

<a id="問題15-1"></a>
## 問題15-1

```java
1  import java.util.ArrayDeque;
2  import java.util.Deque;
3
4  public class Main {
5      public static void main(String[] args) {
6          Deque<Integer> deque = new ArrayDeque<>();
7          deque.addLast(1);
8          deque.addFirst(2);
9          deque.add(3);
10         System.out.println(deque);
11         System.out.println(deque.peekFirst());
12         System.out.println(deque.peekLast());
13         System.out.println(deque.removeLast());
14         System.out.println(deque);
15     }
16 }
```

このコードを実行するとどうなるか。

A. `[2, 1, 3]` → `2` → `3` → `3` → `[2, 1]`
B. `[1, 2, 3]` → `1` → `3` → `3` → `[1, 2]`
C. `[2, 1, 3]` → `2` → `3` → `1` → `[2, 3]`
D. `[2, 3, 1]` → `2` → `1` → `1` → `[2, 3]`

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `addLast(1)`で`[1]`、`addFirst(2)`で`[2,1]`、`add(3)`(=`addLast`相当)で`[2,1,3]`になる。`peekFirst()`は先頭の`2`、`peekLast()`は末尾の`3`を取り出さずに返す。`removeLast()`で末尾の`3`を削除して取得し、残りは`[2,1]`になる。




正解: A




あなたの回答: A

<a id="問題15-2"></a>
## 問題15-2

```java
1  import java.util.ArrayDeque;
2  import java.util.Deque;
3
4  public class Main {
5      public static void main(String[] args) {
6          Deque<String> stack = new ArrayDeque<>();
7          stack.push("A");
8          stack.push("B");
9          System.out.println(stack.peek());
10         System.out.println(stack.pop());
11         System.out.println(stack.pop());
12         System.out.println(stack.pop());
13     }
14 }
```

このコードを実行するとどうなるか。

A. `B` `B` `A` の後、`null`が出力される
B. `B` `B` `A` の後、`NoSuchElementException`がスローされる
C. `A` `A` `B` の後、`NoSuchElementException`がスローされる
D. コンパイルエラーになる

**実施記録**




迷ったポイント: なし(一発正解)。`peek()`がQueue由来のメソッドとして「先頭」を見る、という基準を正しく踏まえて判断できていた。




解説(概念): `push("A")`→`addFirst`相当で`[A]`、`push("B")`→さらに先頭に追加され`[B,A]`。`peek()`(=`peekFirst()`相当)は先頭の`B`を取り出さずに返す。`pop()`(=`removeFirst()`相当)を2回呼ぶと`B`→`A`の順に取り出され空になる。3回目の`pop()`は空の状態での`removeFirst()`にあたるため、`poll()`のような安全な`null`ではなく`NoSuchElementException`がスローされる。




正解: B




あなたの回答: B

<a id="問題16-1"></a>
## 問題16-1

```java
1  import java.util.HashMap;
2  import java.util.Map;
3
4  public class Main {
5      public static void main(String[] args) {
6          Map<String, Integer> map = new HashMap<>();
7          map.put("a", 1);
8          map.put(null, 100);
9          map.put(null, 200);
10         System.out.println(map);
11         System.out.println(map.get(null));
12         System.out.println(map.put("a", null));
13         System.out.println(map.get("a"));
14         System.out.println(map.containsKey("a"));
15     }
16 }
```

このコードを実行するとどうなるか。

A. `{null=200, a=1}` → `200` → `1` → `null` → `true`
B. 8行目で`NullPointerException`がスローされる
C. `{null=100, a=1}` → `100` → `1` → `null` → `false`
D. `{null=200, a=1}` → `200` → `null` → `null` → `false`

**実施記録**




迷ったポイント: なし(一発正解)。




解説(概念): `HashMap`は`null`キーを1つだけ許容する(2回目の`put(null, 200)`は上書き)。`put()`はそのキーに元々登録されていた値を返す(`put("a", null)`は上書き前の値`1`を返す)。値を`null`にしても、キー`"a"`自体はマップ内に残っているため`containsKey("a")`は`true`(値が`null`かどうかとキーの存在は別の話)。




正解: A




あなたの回答: A

<a id="問題16-2"></a>
## 問題16-2

```java
1  import java.util.HashMap;
2  import java.util.Map;
3
4  public class Main {
5      public static void main(String[] args) {
6          Map<String, Integer> map = new HashMap<>();
7          map.put("a", 1);
8          map.put("b", 2);
9          for (Map.Entry<String, Integer> e : map.entrySet()) {
10             e.setValue(e.getValue() * 10);
11         }
12         System.out.println(map);
13         Integer old = map.putIfAbsent("a", 999);
14         Integer added = map.putIfAbsent("c", 3);
15         System.out.println(old);
16         System.out.println(added);
17         System.out.println(map);
18     }
19 }
```

このコードを実行するとどうなるか。

A. `{a=10, b=20}` → `10` → `null` → `{a=10, b=20, c=3}`
B. `{a=1, b=2}` → `999` → `3` → `{a=999, b=2, c=3}`
C. `{a=10, b=20}` → `999` → `3` → `{a=999, b=20, c=3}`
D. 10行目でコンパイルエラーになる(`entrySet()`の要素は変更できない)

**実施記録**




迷ったポイント: なし(一発正解)。`putIfAbsent()`という新しく知ったメソッドの挙動も正確に踏まえられていた。




解説(概念): `entrySet()`の各`Map.Entry`は`setValue()`で値を書き換えられ、これは元の`map`本体にも反映される(9〜10行目で`a=10, b=20`になる)。`putIfAbsent(key, value)`は「そのキーがまだ存在しない場合にだけ`value`を登録する」メソッド。既にキーが存在する場合(`"a"`)は何もせず**既存の値**(`10`)を返し、キーが存在しない場合(`"c"`)は新規登録して**`null`**を返す(通常の`put()`と違い、既存キーの値を上書きしない点が特徴)。




正解: A




あなたの回答: A

<a id="問題17-1"></a>
## 問題17-1

```java
1  import java.util.Map;
2  import java.util.TreeMap;
3
4  public class Main {
5      public static void main(String[] args) {
6          Map<String, Integer> map = new TreeMap<>();
7          map.put("banana", 1);
8          map.put("apple", 2);
9          map.put("cherry", 3);
10         map.put("apple", 20);
11         System.out.println(map);
12         map.put(null, 99);
13         System.out.println(map);
14     }
15 }
```

このコードを実行するとどうなるか。

A. `{apple=20, banana=1, cherry=3}`の後、12行目で`NullPointerException`がスローされる
B. `{apple=2, banana=1, cherry=3}`の後、`{null=99, apple=2, banana=1, cherry=3}`が出力される
C. `{banana=1, apple=20, cherry=3}`の後、12行目で`NullPointerException`がスローされる
D. コンパイルエラーになる

**実施記録**




迷ったポイント: 「TreeMapはnullキーもnull値も両方禁止」と誤認していた。実際に禁止されているのはnullキーだけで、null値は問題なく設定できる(javacで`map.put("a", null)`が成功することを検証済み)。




解説(概念): `TreeMap`はキーを自然順序(`compareTo()`)で並べ替えて保持するため、比較の基準になる**キー**が`null`だと比較できず`NullPointerException`になる。値は比較に一切使われないため`null`でも問題ない。10行目`put("apple", 20)`は既存キーの値を上書きするだけで、11行目の出力はアルファベット順`{apple=20, banana=1, cherry=3}`になる。




正解: A




あなたの回答: A

<a id="問題17-2"></a>
## 問題17-2

```java
1  import java.util.LinkedHashMap;
2  import java.util.Map;
3
4  public class Main {
5      public static void main(String[] args) {
6          Map<Integer, String> map = new LinkedHashMap<>(16, 0.75f, true);
7          map.put(1, "A");
8          map.put(2, "B");
9          map.put(3, "C");
10         map.get(1);
11         System.out.println(map);
12     }
13 }
```

このコードを実行するとどうなるか。

A. `{1=A, 2=B, 3=C}`
B. `{2=B, 3=C, 1=A}`
C. `{3=C, 2=B, 1=A}`
D. コンパイルエラーになる(`LinkedHashMap`のコンストラクタにこのような3引数版は存在しない)

**実施記録**




迷ったポイント: なし(一発正解)。3引数コンストラクタ(初期容量, 負荷係数, accessOrder)の存在とaccessOrder=trueの意味を初めて知ったが、正しく推測できていた。




解説(概念): `LinkedHashMap`の3引数コンストラクタの第3引数`accessOrder`を`true`にすると、「追加した順」ではなく「最近アクセスされた順(アクセスするたびに末尾へ移動)」で並ぶようになる(デフォルトは`false`=追加順)。`get(1)`によってキー`1`が末尾に移動し、`{2=B, 3=C, 1=A}`になる。追加で検証したところ、この並べ替えは`get()`限定ではなく、既存キーへの`put()`(値の更新)でも同様に発生する。LRUキャッシュを実装する際の定番テクニック。




正解: B




あなたの回答: B