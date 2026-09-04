### 4.4 Mapインタフェース: HashMap

**教科書の例(**`chap1/16`**):**

```java
1  import java.util.Collection;
2  import java.util.HashMap;
3  import java.util.Map;
4  import java.util.Set;
5
6  public class Main {
7      public static void main(String[] args) {
8          Map<Integer, String> map = new HashMap<>();
9          map.put(0, "Zero"); map.put(10, "Zero");
10         map.put(20, "Twenty");
11         System.out.println(map);
12         System.out.println(" put(10, \"Ten\") : "
13                             + map.put(10, "Ten"));
14         System.out.println(" remove(20)     : " + map.remove(20));
15         System.out.println(" remove(30)     : " + map.remove(30));
16         System.out.println(" containsKey(20): "
17                             + map.containsKey(20));
18         System.out.println(" containsValue(\"Ten\"): "
19                             + map.containsValue("Ten"));
20         Set<Integer> keyset = map.keySet();
21         Collection<String> values = map.values();
22         Set<Map.Entry<Integer, String>> entryset = map.entrySet();
23         System.out.println("Keys     : " + keyset);
24         System.out.println("Values   : " + values);
25         System.out.println("Map.Entry: " + entryset);
26         System.out.print("Map.Entry... ");
27         for (Map.Entry<Integer, String> e : entryset) {
28             System.out.print(e.getKey() + ":" + e.getValue() + " ");
29         }
30     }
31 }
```

実行結果(javacで検証済み):

```
{0=Zero, 20=Twenty, 10=Zero}
 put(10, "Ten") : Zero
 remove(20)     : Twenty
 remove(30)     : null
 containsKey(20): false
 containsValue("Ten"): true
Keys     : [0, 10]
Values   : [Zero, Ten]
Map.Entry: [0=Zero, 10=Ten]
Map.Entry... 0:Zero 10:Ten
```

**説明したポイント**

`Map<K, V>`は`List`/`Set`と違い**キーと値のペア**でデータを持つコレクション(`Collection`インタフェースは継承していない、別系統のインタフェース)。キーは重複不可、値は重複可。

- 9行目: `put(0, "Zero")`, `put(10, "Zero")`のように、**値が同じでもキーが違えば別々に登録**できる。9〜10行目の3つの`put()`で`{0=Zero, 20=Twenty, 10=Zero}`という中身になる(`HashMap`なので並び順は保証されない)。

- 12〜13行目: `put()`は**そのキーに元々登録されていた値を戻り値として返す**メソッド。`put(10, "Ten")`はキー`10`の値を`"Zero"`から`"Ten"`に上書きし、上書き前の値`"Zero"`が戻り値として返る。

- 14行目: `remove(20)`はキー`20`のエントリを削除し、**削除された値**(`"Twenty"`)を返す。

- 15行目: `remove(30)`のように存在しないキーを指定すると、何も削除されず戻り値は`null`になる(例外にはならない)。

- 16〜17行目: `containsKey(20)`は、14行目で既に`remove`済みのため`false`。

- 18〜19行目: `containsValue("Ten")`は、13行目で`"Ten"`に上書き済みのため`true`。

- 20〜22行目: `Map`の中身を3種類の見方で取り出せる。
  - `keySet()`: キーだけを集めた`Set<K>`(キーは重複しないので`Set`)。
  - `values()`: 値だけを集めた`Collection<V>`(値は重複しうるので`Set`ではなく`Collection`)。
  - `entrySet()`: キーと値のペア(`Map.Entry<K, V>`)を集めた`Set`。

- 27〜29行目: `entrySet()`を拡張for文で回すと、各要素は`Map.Entry<K, V>`型になり、`getKey()`/`getValue()`でキー・値それぞれを取り出せる。`Map`自体は拡張for文で直接回せない(`Map`は`Iterable`を実装していない)ため、`entrySet()`/`keySet()`/`values()`のいずれかを経由する必要がある。

**セクションの核心**

`Map`は「キー→値」の対応表であり、`List`/`Set`とは異なる「もう1系統」のコレクションという位置づけ(`Collection`インタフェースを継承していない)。`keySet()`/`values()`/`entrySet()`は、`Map`が内部に持つデータを**それぞれ別の切り口(Set/Collection/Set)で覗くための窓**であり、これらを経由することで初めて拡張for文などのコレクション操作が可能になる。

---

### `HashMap` / `LinkedHashMap` / `TreeMap`の違い(`chap1/17`)

`Set`における`HashSet`/`LinkedHashSet`/`TreeSet`(4.2)の関係が、`Map`の実装クラスにもそのまま対応する。違いのポイントは`Set`のときと同じく**「順序」**。

**教科書の例(**`chap1/17`**):**

```java
1  import java.util.LinkedHashMap;
2  import java.util.Map;
3  import java.util.TreeMap;
4
5  public class Main {
6      public static void main(String[] args) {
7          // LinkedHashMap
8          Map<Integer, String> map1 = new LinkedHashMap<>();
9          map1.put(2, "Two"); map1.put(0, "Zero");
10         map1.put(3, "Three"); map1.put(1, "One");
11         System.out.println("LinkedHashMap: " + map1);
12         // TreeMap
13         Map<Integer, String> map2 = new TreeMap<>();
14         map2.put(2, "Two"); map2.put(0, "Zero");
15         map2.put(3, "Three"); map2.put(1, "One");
16         System.out.println("TreeMap      : " + map2);
17     }
18 }
```

実行結果(javacで検証済み。比較用に`HashMap`でも同じ`put`順を試した結果も追加):

```
LinkedHashMap: {2=Two, 0=Zero, 3=Three, 1=One}
TreeMap      : {0=Zero, 1=One, 2=Two, 3=Three}
HashMap      : {0=Zero, 1=One, 2=Two, 3=Three}   ← 参考(サンプルには無い)
```

**説明したポイント**

- **`HashMap`**: 順序を保証しない(内部のハッシュ値に基づいて格納される)。ただし今回のように**キーが小さい連番の`Integer`だと、たまたまソートされたような並びに見えてしまう**ことがある(内部的なハッシュ値の並びが偶然一致するだけで、`Integer`キー以外や値が大きくなると崩れる)。「順序を保証しない」=「常にバラバラに見える」という意味ではない点に注意(`HashSet`のときと同じ落とし穴)。

- **`LinkedHashMap`**(8〜11行目): `HashMap`のサブクラス。ハッシュベースの性質は同じだが、**`put()`した順序をそのまま保持する**。`{2=Two, 0=Zero, 3=Three, 1=One}`という、まさに追加した順で出力されている。

- **`TreeMap`**(13〜16行目): キーを**自動的にソートして**格納する(`Integer`キーなので自然順序=数値の昇順、`{0, 1, 2, 3}`の順)。`TreeSet`と同様、キーの型が自然順序付け(`Comparable`)に対応していないとエラーになる(または`Comparator`を別途渡す必要がある)。

**セクションの核心**

`Map`の3実装クラスの使い分けは`Set`と全く同じ発想で選べる。「順序はどうでもいい・パフォーマンス重視」なら`HashMap`、「追加した順を保ちたい」なら`LinkedHashMap`、「キーで自動的にソートしておきたい」なら`TreeMap`。`Set`の対応するキー集合(`keySet()`)も、それぞれ元の`Map`の実装に応じた順序で並ぶ。

---

### `Hashtable`(レガシー実装、`HashMap`の同期化版)

`List`における`ArrayList`⇔`Vector`の関係と同じ位置づけで、`Map`にも`HashMap`⇔`Hashtable`という対応がある(4-1の「参考: コレクションのレガシー実装」で予告していたもの)。

- コレクションフレームワーク登場(Java 1.2)以前からある**レガシークラス**。`Map`インタフェースを実装しているが、内部の設計は古い。

- `HashMap`と同じくキー→値の対応を保持するが、各メソッドが`synchronized`されているため**スレッドセーフ**。ただしその分パフォーマンスは`HashMap`より劣る。

- **`HashMap`との決定的な違い**: `Hashtable`は**`null`キー・`null`値のどちらも一切許容しない**。`put(null, ...)`も`put(..., null)`も両方`NullPointerException`になる(javacで検証済み)。`HashMap`は`null`キーを1つだけ、`null`値は複数許容していたのと対照的。

```java
Map<String, Integer> ht = new Hashtable<>();
ht.put("a", 1);
ht.put(null, 100);   // NullPointerException
ht.put("b", null);   // NullPointerException
```

**セクションの核心**

`Vector`/`Hashtable`はどちらも「昔からある、メソッドが最初から`synchronized`されているレガシークラス」という共通の位置づけ。`Hashtable`固有の注意点は「`null`を一切受け付けない」という点で、`HashMap`のつもりで`null`を扱うコードをそのまま`Hashtable`に置き換えると実行時エラーになる、というのが試験・実務どちらでも引っかかりやすいポイント。

---

## 演習問題

<a id="問題16-1"></a>
### 問題16-1

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
### 問題16-2

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
### 問題17-1

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
### 問題17-2

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

---

**参考: `merge()`**

`merge(key, value, 関数)`は、キーの有無に応じて動きが変わる。

```java
Map<String, Integer> map = new HashMap<>();
map.put("a", 5);
map.merge("a", 10, (oldV, newV) -> oldV + newV);   // キーあり → 関数を実行: 5+10=15
map.merge("b", 100, (oldV, newV) -> oldV + newV);  // キーなし → 関数は呼ばれず、100がそのまま登録される
System.out.println(map);  // {a=15, b=100}
```

- **キーが既に存在する場合**: 渡した関数が`(既存の値, 新しく渡した値)`という引数で呼ばれ、その戻り値で上書きされる。
- **キーが存在しない場合**: 関数は一切呼ばれず、渡した値がそのまま新規登録される(`oldV`が`null`になって関数に渡される、ということはない)。

`putIfAbsent()`(キーが無い時だけ登録)と似ているが、`merge()`は「キーが**既にある場合**に、既存の値と新しい値を**合成する処理**を書ける」点が異なる(合計・上書き・大きい方を残す、など関数を自由に書ける)。

---

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。
