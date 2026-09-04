### 4.2 Setインタフェース: HashSet / LinkedHashSet / TreeSet

**教科書の例(**`chap1/12`**):**



```java
 1  import java.util.HashSet;
 2  import java.util.LinkedHashSet;
 3  import java.util.Set;
 4  import java.util.TreeSet;
 5
 6  public class Main {
 7      public static void main(String[] args) {
 8          Set<Integer> set1 = new HashSet<>();    // HashSet
 9          boolean add1 = set1.add(3);
10         boolean add2 = set1.add(3);
11         set1.add(null); set1.add(null);
12         set1.add(2); set1.add(1);
13         System.out.println("HashSet: " + set1);
14         System.out.println(" add1: " + add1 + ", add2: " + add2);
15         boolean rmv1 = set1.remove(3);
16         boolean rmv2 = set1.remove(3);
17         System.out.println(" rmv1: " + rmv1 + ", rmv2: " + rmv2);
18         // LinkedHashSet
19         Set<Integer> set2 = new LinkedHashSet<>();
20         set2.add(3); set2.add(3);
21         set2.add(null); set2.add(null);
22         set2.add(2); set2.add(1);
23         System.out.println("LinkedHashSet: " + set2);
24         // TreeSet
25         Set<String> set3 = new TreeSet<>();
26         set3.add("Duke"); set3.add("James"); set3.add("Alice");
27         System.out.println("TreeSet: " + set3);
28     }
29  }
```

**説明したポイント**

`Set<E>`は**重複を許さない**コレクション。3つの実装クラスの違いは「**順序**」がポイントになる。

- **HashSet(8〜17行目)**: 順序を保証しない(内部のハッシュ値に基づいて格納されるため、`add()`した順とは無関係な順で並ぶ)。
  - `null`は1つだけ許容(2回目の`add(null)`は無視される)。
  - 重複値(`3`を2回`add`)も2回目は無視され、`add()`は成功したかどうかを`boolean`で返す(`add1=true`, `add2=false`)。
  - 同様に`remove()`も削除できたかを`boolean`で返す(`rmv1=true`, `rmv2=false`、1回目で既に消えているため)。
- **LinkedHashSet(19〜23行目)**: `HashSet`のサブクラス。重複禁止などの性質は同じだが、**追加した順序を保持する**。
- **TreeSet(25〜27行目)**: 要素を**自動的にソートして**格納する(`Alice, Duke, James`のようにアルファベット順になる)。

---

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。