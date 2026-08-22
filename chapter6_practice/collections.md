# 6章 コレクション（ArrayList/List/Map/Arrays）問題集

## 前提知識メモ

### ArrayListの基本操作

| メソッド | 意味 | 境界条件 |
|---|---|---|
| `add(E element)` | 末尾に追加 | 常にOK |
| `add(int index, E element)` | indexの位置に挿入（既存要素は後ろにシフト） | `0 〜 size()`（末尾＝`size()`も含む）まではOK。それを超えると`IndexOutOfBoundsException` |
| `set(int index, E element)` | indexの要素を置き換え。**戻り値は置き換え前の古い要素** | サイズは変わらない |
| `remove(int index)` | インデックス指定で削除 | intリテラルを渡すとこちらが優先される（オーバーロード解決） |
| `remove(Object o)` | 値を検索して削除 | `Integer.valueOf(x)`のように明示的にオブジェクト化すればこちらが呼ばれる |

`ArrayList<Integer>`は`Integer`（参照型）を保持するため`null`も格納できる。

### `Arrays.asList` / `List.of` / `ArrayList` の可変性比較

| 操作 | `new ArrayList<>()` | `Arrays.asList(...)` | `List.of(...)` |
|---|---|---|---|
| `get(index)` | ✅ | ✅ | ✅ |
| `set(index, value)` | ✅ | ✅（配列の中身を書き換えるだけ） | ❌`UnsupportedOperationException` |
| `add(value)` | ✅ | ❌`UnsupportedOperationException`（サイズ固定） | ❌`UnsupportedOperationException` |
| `remove(value)` | ✅ | ❌`UnsupportedOperationException`（サイズ固定） | ❌`UnsupportedOperationException` |
| `null`要素 | ✅ | ✅ | ❌`NullPointerException` |

**覚え方**：可変性の強さは `ArrayList（全部OK） > Arrays.asList（setだけOK） > List.of（全部NG）`。

- `ArrayList`：普通の配列リスト。何でもできる。
- `Arrays.asList`：配列をラップしただけなので、**サイズを変える操作（add/remove）だけ禁止**。中身の置き換え（set）は配列自体の書き換えなのでOK。
- `List.of`：完全に不変であることを保証する専用クラス。**一切の変更操作を禁止**。

**重要**：`new ArrayList<>(List.of(...))`のように**コピーして新しいArrayListを作れば**、元がどれであっても完全に可変な独立したリストになる。「可変かどうか」はデータの出どころではなく、**そのリストの実体が実際にどのクラスのインスタンスか**で決まる（`final`がクラスを書いた人の設計で決まるのと同じ構造）。

### `ConcurrentModificationException`（CME）

拡張for文は内部的に`Iterator`を使う。イテレータは`next()`が呼ばれるたびに「自分を経由しないでリストが変更されていないか」を`modCount`でチェックしており、`nums.remove(...)`のようにイテレータを経由せず直接リストを変更すると、次の`next()`呼び出し時に`ConcurrentModificationException`が発生する。

これは「リストが可変かどうか」とは無関係（可変なリストでも、ループ中の直接変更はCMEになる）。安全に削除したい場合は`Iterator.remove()`または`removeIf()`を使う。

**拡張for文の正体**：`for (Integer n : nums) { ... }`は、コンパイラが以下のような`Iterator`を使うコードに自動的に書き換える（`javap -c`のバイトコードで`List.iterator()`/`Iterator.hasNext()`/`Iterator.next()`の呼び出しを確認済み）。

```java
Iterator<Integer> it = nums.iterator();
while (it.hasNext()) {
    Integer n = it.next();
    ...
}
```

つまり`next()`はソースコードに書かなくても、拡張for文が裏で毎回呼んでいる。`nums.remove(...)`のようにこの`it`を経由しない変更を行うと、次の`next()`呼び出し時に不整合が検出されCMEが発生する。

**CMEを避ける3つの書き方**（すべて検証済み・例外なし）：

| 書き方 | なぜ安全か |
|---|---|
| 通常の`for (int i = 0; i < nums.size(); i++)` | `Iterator`を使わずindexを直接操作するので、そもそもチェック対象にならない |
| `Iterator`自身の`it.remove()` | イテレータ経由の変更なので、イテレータ側が整合性を保てる |
| `nums.removeIf(条件)` | 内部で安全な削除処理を行う専用メソッド |

### `Map`のAPI

`Map`インターフェースに`add`メソッドは存在しない。キー・バリューのペアを追加するには`put(key, value)`を使う。

### `Arrays.mismatch`

2つの配列を先頭から比較し、最初に不一致（`.equals()`で判定）が見つかったインデックスを返す。全て一致すれば`-1`。`String[]`は配列の共変性により`Object[]`が要求される箇所にそのまま渡せる。

### recordと`HashSet`

`record`は全フィールドに基づいて`equals`/`hashCode`が自動生成される。そのため、別インスタンスでもフィールドの値が全て同じなら**等価**とみなされ、`HashSet`では重複要素として扱われる（追加されない）。

---

## 問題6-19：ArrayListの基本操作

```java
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(1); scores.add(2); scores.add(null);
        scores.add(3, 3);
        scores.set(3, 10);
        System.out.println(scores);
    }
}
```

- A. `[1, 2, null, 3]`
- B. `[1, 2, null, 10]`
- C. コンパイルエラーになる（`ArrayList<Integer>`に`null`は追加できないため）
- D. 実行時に例外が発生する（`add(3, 3)`の時点でインデックスが範囲外のため）

**正解：B**。`null`はOK。`add(3,3)`はindex==size()なので末尾追加として合法（`[1,2,null,3]`）。`set(3,10)`で置き換え（`[1,2,null,10]`）。

---

## 問題6-20：recordのリスト操作・`set`の戻り値

```java
import java.util.ArrayList;
record Item(int no, String name) {}
public class Main {
    public static void main(String[] args) {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(1, "Laptop"));
        items.add(new Item(2, "Mobile"));
        items.add(new Item(2, "Phone"));
        for (Item i : items) {
            System.out.print(i.no() + ", ");
        }
        System.out.println(items.set(1, new Item(3, "Smartwatch")));
    }
}
```

- A. `1, 2, 2, Item[no=3, name=Smartwatch]`
- B. `1, 2, 2, Item[no=2, name=Mobile]`
- C. コンパイルエラーになる
- D. `1, 2, 2, Item[no=2, name=Phone]`

**正解：B**。`set(1, ...)`は置き換え前の古い要素（index1＝`Mobile`）を返す。`Item(3, "Smartwatch")`の`3`は新しいインスタンスの単なる`no`フィールド値であり、リストの位置や個数とは無関係。

---

## 問題6-21：`List.remove(int)` vs `remove(Object)`

```java
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(1, 2);
        System.out.println();
        numbers.add(1, 3);
        System.out.println(numbers.remove(1));
    }
}
```

- A. `1`（インデックス1の要素として`3`を削除するが、印字されるのはインデックス値の`1`）
- B. `3`（インデックス1に位置する要素`3`が削除され、その値が返る）
- C. `1`（値として`1`を検索し、それを削除。戻り値は削除の成否ではなく削除された値そのもの）
- D. コンパイルエラーになる（オーバーロードが曖昧なため）

**正解：B**。`remove(1)`の`1`はint型リテラルなのでオートボクシングされず`remove(int index)`が優先される。`numbers`は`[1,3,2]`になっており、index1の`3`が削除されて返る。

---

## 問題6-22：`Map`のAPI

```java
import java.util.HashMap;
import java.util.Map;
public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.add("UK", "United Kingdom");
        map.add("DE", "Germany");
        map.add("FR", "France");
        System.out.println(map);
    }
}
```

- A. `{UK=United Kingdom, DE=Germany, FR=France}`
- B. コンパイルエラーになる（`Map`インターフェースに`add`メソッドは存在しないため）
- C. 実行時に例外が発生する（重複したキーの追加はできないため）
- D. `{DE=Germany, FR=France}`（`UK`は上書きされて消える）

**正解：B**。`Map`は`put(key, value)`を使う。`add`は存在しない（`javac`で「シンボルを見つけられません」エラーを確認済み）。

---

## 問題6-24：`Arrays.mismatch`

```java
import java.util.Arrays;
public class Main {
    public static void main(String[] args) {
        Object[] oArr = {"100", new String("200"), 300};
        String[] sArr = {"100", "200", "300"};
        int value = Arrays.mismatch(sArr, oArr);
        System.out.println(value);
    }
}
```

- A. `-1`（2つの配列は完全に一致するため）
- B. `2`（インデックス2で初めて不一致が見つかるため）
- C. コンパイルエラーになる（`String[]`と`Object[]`は型が異なり渡せないため）
- D. `0`（インデックス0の"100"同士も型が異なるため不一致とみなされる）

**正解：B**。index0,1は`.equals()`で一致（参照は違っても中身が同じならOK）。index2は`"300"`(String) vs `300`(Integer)で型が異なり不一致。

---

## 難問1：不変リストのAPI差分

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> fixed = Arrays.asList(1, 2, 3);
        fixed.set(0, 100);
        System.out.println(fixed);

        List<Integer> immutable = List.of(1, 2, 3);
        immutable.add(4);
        System.out.println(immutable);
    }
}
```

- A. `[100, 2, 3]`が出力された後、`immutable.add(4)`で`UnsupportedOperationException`が発生する
- B. `fixed.set(0, 100)`の時点で`UnsupportedOperationException`が発生する
- C. `[100, 2, 3]`と`[1, 2, 3, 4]`が両方出力され、正常終了する
- D. コンパイルエラーになる

**正解：A**。`Arrays.asList`の`set`は許可されるので`[100, 2, 3]`が出力される。`List.of`の`add`は禁止されているため、その行で例外が発生し以降の出力には到達しない。

---

## 難問2：拡張for文中の要素削除

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        for (Integer n : nums) {
            if (n == 2) {
                nums.remove(n);
            }
        }
        System.out.println(nums);
    }
}
```

- A. `[1, 3, 4, 5]`が出力され、正常終了する
- B. `ConcurrentModificationException`が発生する
- C. `[1, 2, 3, 4, 5]`（削除が反映されない）が出力される
- D. コンパイルエラーになる

**正解：B**。`nums`は`new ArrayList<>(...)`で作られた完全に可変なリストだが、拡張for文のイテレータを経由せず直接`remove`しているため、次の`next()`呼び出し時に`ConcurrentModificationException`が発生する。

---

## 難問3：recordと`HashSet`の重複判定

```java
import java.util.*;

record Point(int x, int y) {}

public class Main {
    public static void main(String[] args) {
        Set<Point> points = new HashSet<>();
        points.add(new Point(1, 2));
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        System.out.println(points.size());
    }
}
```

- A. `3`（`new`で作った別インスタンスなので別要素として扱われる）
- B. `2`（recordは自動生成された`equals`/`hashCode`でフィールドの値が同じなら等価とみなされる）
- C. `1`（`HashSet`は常に1つしか要素を持てない）
- D. コンパイルエラーになる（`record`は`HashSet`の要素にできないため）

すべてjavac(--release 17)/javaで実機検証済み。

---

## 解答まとめ

| 問題 | 正解 |
|---|---|
| 6-19 | B |
| 6-20 | B |
| 6-21 | B |
| 6-22 | B |
| 6-24 | B |
| 難問1 | A |
| 難問2 | B |
| 難問3 | B |

---

## 実施記録

### 1回目（2026-08-22）

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| 6-19 | B | B | 正解 |
| 6-20 | C（1回目）→B（2回目） | B | 迷いあり |
| 6-21 | B | B | 正解 |
| 6-22 | B | B | 正解 |
| 6-24 | B | B | 正解 |
| 難問1 | A | A | 正解 |
| 難問2 | C（誤答） | B | 誤り |
| 難問3 | B | B | 正解 |

### 迷ったポイントの詳細

**6-20（一時的な誤答）**：`items.set(1, new Item(3, "Smartwatch"))`の`3`という値を見て、「リストの何番目か」という位置情報と混同しかけた。正しくは、`Item`の`no`フィールドは新しく作ったインスタンスの単なるデータであり、リストのインデックスや要素数とは一切連動しない。

**難問2（誤答）**：「拡張for文はループ変数への再代入をしても元の配列を書き換えない」というルールと、「ループ中にコレクションのメソッド（`remove`）を直接呼ぶと`ConcurrentModificationException`になる」というルールを混同した。前者はプリミティブ配列の値コピーの話、後者はイテレータの`modCount`チェックの話で、全く別の仕組み。また`nums`が`List.of`由来だから不変なのでは、という誤解もあったが、`new ArrayList<>(...)`でコピーした時点で完全に可変な別クラスのインスタンスになっているため、CMEの原因は不変性ではなくループ中の直接変更というタイミングの問題だった。
