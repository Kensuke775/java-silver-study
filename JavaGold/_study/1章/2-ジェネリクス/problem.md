# Chapter1 オリジナル問題集(2周目)

## 目次

- [問題1-1](#問題1-1)
- [問題1-2](#問題1-2)
- [問題2-1](#問題2-1)
- [問題2-2](#問題2-2)
- [問題3-1](#問題3-1)
- [問題3-2](#問題3-2)
- [問題4-1](#問題4-1)
- [問題4-2](#問題4-2)
- [問題5-1](#問題5-1)
- [問題5-2](#問題5-2)
- [問題6-1](#問題6-1)
- [問題6-2](#問題6-2)
- [問題7-1](#問題7-1)
- [問題7-2](#問題7-2)
- [問題8-1](#問題8-1)
- [問題8-2](#問題8-2)
- [問題9-1](#問題9-1)
- [問題9-2](#問題9-2)
- [問題10-1](#問題10-1)
- [問題10-2](#問題10-2)
- [問題10-3](#問題10-3)

## 問題1-1

```java
1  public class Main {
2      public static void main(String[] args) {
3          Integer a = 127;
4          Integer b = 127;
5          Integer c = 128;
6          Integer d = 128;
7          Integer e = Integer.valueOf(128);
8          Integer f = new Integer(128);
9
10         System.out.println(a == b);
11         System.out.println(c == d);
12         System.out.println(c == e);
13         System.out.println(c == f);
14         System.out.println(c.equals(f));
15     }
16 }
```

このコードの実行結果として正しいものを1つ選べ。

A. `true` `true` `true` `true` `true`
B. `true` `false` `false` `false` `true`
C. `false` `false` `false` `false` `true`
D. `true` `false` `false` `false` `false`

**実施記録**

迷ったポイント: Integerキャッシュを「Stringプールのように同じ値なら常に同一インスタンス」と誤認していた。実際はオートボクシング/`valueOf`経由かつ-128〜127の範囲のみキャッシュ対象。`new Integer(...)`は値に関わらず常に新規インスタンス。

解説(概念): `==`は参照比較、`equals`は値比較。キャッシュ範囲外(128)では`valueOf`経由でも毎回別インスタンスになる。

正解: B

あなたの回答: A

## 問題1-2

```java
1  public class Main {
2      public static void main(String[] args) {
3          int i = 1;
4          double d = 2.0;
5          boolean flag = true;
6
7          System.out.println(flag ? i : d);
8
9          Integer wrap = null;
10         int result = flag ? 10 : wrap;
11         System.out.println(result);
12     }
13 }
```

このコードを実行するとどうなるか。

A. `1` と `10` が出力される
B. `1.0` と `10` が出力される
C. `1.0` が出力された後、`NullPointerException` がスローされる
D. `1` が出力された後、`NullPointerException` がスローされる

**実施記録**

迷ったポイント: 三項演算子の「式全体の型が二項数値昇格で決まる」ことと「実行時に両方の枝が評価される」ことを混同していた。`int`/`Integer`が混在する三項演算子では式の型は`int`に決まるが、unboxingが実際に起きるのは実行時に選ばれた枝のみ。`flag`が`true`ならリテラル`10`(既にint)が選ばれ、`wrap`(null)は評価されないためNPEは発生しない。

解説(概念): 三項演算子は実行時にどちらの場合も選ばれた1つの枝しか評価しない。コンパイラは両枝の型だけを見て式全体の型を静的に決める(int/double混在ならdouble、int/Integer混在ならint)。1行目は選ばれた`i`(int)がdoubleへ暗黙変換されて`1.0`になり、2行目は選ばれた`10`が既にintなので変換不要でそのまま`10`になる。

正解: B

あなたの回答: C

**三項演算子内で起きてることのイメージ**

```java
if (flag) {
    println((double) i);   // 選ばれた側はdoubleに変換してから使う
} else {
    println(d);
}
```

`flag ? 10 : wrap`ならこう:

```java
if (flag) {
    println(10);           // 変換不要なのでそのまま
} else {
    println(wrap.intValue());  // ここでwrapがnullならNPE
}
```

- 質問4: int/Integer混在では具体的にどちらが変換されるのか?
- 回答4: 必ずInteger側がアンボクシングされてintになる(intがボクシングされてIntegerになることはない)。int同士の数値プリミティブが型違い(int/long等)の場合はさらに通常の数値昇格(狭い方を広い方に合わせる)が追加でかかる。丸暗記というより「①ラッパー混在なら必ずアンボクシング側に倒す ②その後は普通の数値昇格ルールを適用する」の2ステップで説明できる。
- 質問5(まとめ): 結局「両方評価される」わけではなく、コンパイル時にtrue用/false用それぞれの式(必要な変換込み)が別々に用意されていて、実行時にflagで選ばれた片方だけが評価される、という理解でよいか?
- 回答5: その理解で正しい。バイトコード(javap -c)で確認済み: ifeqで分岐し、選ばれなかった側の命令列(変換命令を含む/含まないに関わらず)は一切実行されない。



## 問題2-1

```java
1  public class Main {
2      public static void main(String[] args) {
3          Byte b = 100;
4          Short s = 100;
5          Long l = 100;
6          Double d = 100;
7          Float f = 100.0f;
8          System.out.println("ok");
9      }
10 }
```

このコードをコンパイルするとどうなるか、正しいものを1つ選べ。

A. すべての行が問題なくコンパイルできる
B. 3行目と4行目でコンパイルエラーになる
C. 5行目と6行目でコンパイルエラーになる
D. 3〜6行目すべてでコンパイルエラーになる

**実施記録**

迷ったポイント: オートボクシングは元のプリミティブ型と完全一致するラッパー型にしかならない、という原則と、int定数がbyte/short/charの範囲に収まる場合は縮小変換(narrowing)してからボクシングする特別ルール(JLS 5.2)があることを混同していた。int→long/doubleのような拡大変換(widening)とボクシングの組み合わせは許可されないため、5・6行目のみエラーになる。

解説(概念): `Byte b = 100;`・`Short s = 100;`は「int定数100をbyte/shortへ縮小変換してからボクシング」という特別ルートが認められているためコンパイルが通る。一方`Long l = 100;`・`Double d = 100;`は「int→long/doubleへの拡大変換+ボクシング」であり、これは許可されないためエラーになる。long/doubleに代入したい場合は`100L`・`100.0`のようにリテラル自体の型を合わせる必要がある。

正解: C

あなたの回答: D

## 問題2-2

```java
1  import java.util.HashMap;
2  import java.util.Map;
3
4  public class Main {
5      public static void main(String[] args) {
6          Map<String, Integer> scores = new HashMap<>();
7          scores.put("alice", 90);
8          scores.put("bob", 80);
9
10         int total = 0;
11         for (String name : new String[]{"alice", "bob", "carol"}) {
12             total += scores.get(name);
13         }
14         System.out.println(total);
15     }
16 }
```

このコードを実行するとどうなるか。

A. `170` が出力される
B. `170` が出力された後、`NullPointerException` がスローされる
C. `carol` を処理しようとしたタイミングで `NullPointerException` がスローされる
D. コンパイルエラーになる

**実施記録**

迷ったポイント: `System.out.println(total)`がループの外に1回だけ書かれていることを見落とし、「途中経過が出力されてから例外になる」と誤認した。実際は`total += scores.get(name)`をループ内で繰り返しているだけで、`println`にはまだ到達していない。

解説(概念): `Map.get`はキーが存在しないと`null`を返す。`Integer`型の`null`を`int`に加算しようとすると`intValue()`が呼ばれてNPEになる。3回目の反復(`"carol"`)でこれが起き、`println`に到達する前に処理が中断するため、何も出力されないまま例外がスローされる。

正解: C

あなたの回答: B

## 問題3-1

```java
// Box.java
1  public class Box {
2      private Object obj;
3      public void set(Object obj) { this.obj = obj; }
4      public Object get() { return obj; }
5  }
```

```java
// Main.java
1  public class Main {
2      public static void main(String[] args) {
3          Box box = new Box();
4          box.set("Java");
5          Object o = box.get();
6          box.set(10);
7          String s = (String) o;
8          Integer i = (Integer) box.get();
9          int n = i + 1;
10         System.out.println(s + n);
11     }
12 }
```

このコードを実行するとどうなるか。

A. `Java11` が出力される
B. 7行目で `ClassCastException` がスローされる
C. 8行目で `ClassCastException` がスローされる
D. コンパイルエラーになる

**実施記録**

迷ったポイント: なし(一発正解)。5行目はString→Objectの暗黙アップキャスト、7行目はObject→Stringの明示ダウンキャストという理解、および`o`が4行目時点の"Java"という参照のコピーを保持しており6行目の`box.set(10)`の影響を受けない、という理解が正しかった。

解説(概念): 参照型変数はオブジェクトそのものではなく参照(コピー可能な値)を持つ。`o`は`box`の内部フィールドと別々に`"Java"`への参照を保持しているため、`box`の中身を後から変えても`o`経由のキャストには影響しない。

正解: A

あなたの回答: A

## 問題3-2

```java
1  public class Main {
2      public static void main(String[] args) {
3          Box box = new Box();
4          box.set(10);
5          box.set("100");
6          int total = (Integer) box.get() + 1;
7          System.out.println(total);
8      }
9  }
```

(Boxクラスは問題3-1と同じもの)

このコードを実行するとどうなるか。

A. `101` が出力される
B. `6行目` で `ClassCastException` がスローされる
C. `NumberFormatException` がスローされる
D. コンパイルエラーになる

**実施記録**

迷ったポイント: ClassCastExceptionとNumberFormatExceptionを混同していた。`(Integer) box.get()`は単なるキャスト演算子であり、`Integer.parseInt`のような文字列解析メソッドではない。キャストはオブジェクトの実際の型がキャスト先と互換性があるかだけを見ており、中身の変換・解析はしない。

解説(概念): 実行時に`box.get()`が返すのは`"100"`という`String`オブジェクト。`String`は`Integer`ではないため、キャストの時点で内容を見ずに`ClassCastException`がスローされる。数値として解析したい場合は`Integer.parseInt((String) box.get())`のように明示的に解析メソッドを呼ぶ必要があり、その場合のみ内容不正で`NumberFormatException`になりうる。

正解: B

あなたの回答: C

## 問題4-1

```java
// Box.java
1  class Box<T> {
2      private T obj;
3      public void set(T obj) { this.obj = obj; }
4      public T get() { return obj; }
5  }
```

```java
// Main.java
1  public class Main {
2      public static void main(String[] args) {
3          Box<String> box1 = new Box<String>();
4          box1.set("Gold");
5          Box raw = box1;
6          raw.set(100);
7          System.out.println("before get");
8          String s = box1.get();
9          System.out.println(s);
10     }
11 }
```

このコードをコンパイル・実行するとどうなるか。

A. コンパイルエラーになる
B. `before get` が出力された後、8行目で `ClassCastException` がスローされる
C. `before get` が出力された後、6行目に戻って `ClassCastException` がスローされる
D. `before get` `Gold` の順に問題なく出力される

**実施記録**

迷ったポイント: raw型(`Box raw = box1;`)経由での`set`呼び出しがコンパイルエラーになると誤認していた。実際はunchecked警告が出るだけでコンパイルは通る。ジェネリクスは実行時に型情報が消去される(type erasure)ため、raw型経由の呼び出しはコンパイル時の型チェックを迂回してしまう。

解説(概念): 危険なのは`raw.set(100)`の時点ではなく、型どおりに使おうとした`box1.get()`(8行目)の方。コンパイラは`get()`の戻り値を`String`にキャストするコードを暗黙に埋め込んでおり、実際の中身が`Integer`だとそこで`ClassCastException`が起きる。raw型を使うと安全に見える箇所で例外が起きるのが最大の罠。

正解: B

あなたの回答: A

## 問題4-2

```java
1  public class Main {
2      public static void main(String[] args) {
3          Box<? extends Number> box1 = new Box<Integer>();
4          Number n = box1.get();
5          box1.set(10);
6
7          Box<? super Integer> box2 = new Box<Number>();
8          box2.set(10);
9          Number n2 = box2.get();
10
11         Box<Integer> box3 = new Box<Integer>();
12         box3.set(10);
13         int i = box3.get();
14     }
15 }
```

(Boxクラスは問題4-1と同じもの)

コンパイルするとどうなるか。

A. すべて問題なくコンパイルできる
B. 5行目のみコンパイルエラーになる
C. 9行目のみコンパイルエラーになる
D. 5行目と9行目でコンパイルエラーになる

**実施記録**

迷ったポイント: なし(ワイルドカードの説明を踏まえて一発正解)。

解説(概念): `? extends Number`はset()がnull以外禁止(実際の型が不明なため書き込み不可)、`? super Integer`はget()の戻り値がObjectとしてしか保証されない(実際の上位型が不明なため具体型への代入は不可)。PECS(Producer Extends, Consumer Super)の原則通り。

正解: D

あなたの回答: D

## 問題5-1

```java
1  public class Main {
2      public static void method(Box<String> box) { System.out.println("called"); }
3      public static Box<Integer> create() { return new Box<>(); }
4
5      public static void main(String[] args) {
6          Box<String> b1 = new Box<>();
7          method(new Box<>());
8          Box<Integer> b2 = create();
9          Box<> b3 = new Box<String>();
10     }
11 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる
B. 6行目でコンパイルエラーになる
C. 7行目でコンパイルエラーになる
D. 9行目でコンパイルエラーになる

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): ダイヤモンド演算子`<>`はインスタンス生成側(右辺)でしか省略できない。変数宣言側(左辺)の型パラメータは省略不可なので、`Box<> b3 = ...`は「ジェネリクスのルール違反」以前にそもそも構文として成立しない(`型の開始が不正です`という構文エラー)。6・7・8行目は右辺の`<>`が、それぞれ変数宣言・メソッド引数・メソッド戻り値という「ターゲット型」から`T`を推論できるため問題なくコンパイルできる。

正解: D

あなたの回答: D

## 問題5-2

```java
1  public class Main {
2      public static void main(String[] args) {
3          Box raw = new Box<>();
4          raw.set("A");
5          raw.set(1);
6          Object result = raw.get();
7          System.out.println(result);
8          System.out.println(result instanceof Integer);
9          System.out.println(result instanceof String);
10     }
11 }
```

このコードをコンパイル・実行するとどうなるか。

A. `String`と`Integer`を混在させているのでコンパイルエラーになる
B. `1` `true` `false` が出力される(警告は出るがコンパイルは通る)
C. 5行目で`ClassCastException`がスローされる
D. `A` `false` `true` が出力される

**実施記録**

迷ったポイント: 「型が定まっていないから何でも入る」という理解と、「Object型がInteger型になる」という向きの誤りがあった。正しくは、raw型は消去後の`Object`引数として受け取るためコンパイラの型チェックが効かなくなっているだけ(オートボクシングは通常通り発生)。また`Integer`は`Object`のサブタイプなので`Object result = ...`はキャスト不要の普通のアップキャストであり、「ObjectがIntegerになる」わけではない。

解説(概念): `raw.set("A")`→`raw.set(1)`の順で呼び出すたびに内部の`obj`フィールド(実体はObject)が丸ごと置き換わる。`raw`はraw型なのでunchecked警告は出るがコンパイルは通り、最後にsetした`Integer(1)`が`raw.get()`で返る。

正解: B

あなたの回答: B

## 問題6-1

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      <T> List<T> foo() {
6          return new ArrayList<T>();
7      }
8      public static void main(String[] args) {
9          Main obj = new Main();
10         List<String> list = obj.foo();
11         list.add(10);
12         System.out.println(list);
13     }
14 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる。実行すると`[10]`が出力される
B. 10行目でコンパイルエラーになる
C. 11行目でコンパイルエラーになる
D. 11行目で`ClassCastException`がスローされる(実行時)

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): `List<String> list = obj.foo();`のターゲット型(左辺の`List<String>`)から`foo()`の`T`が`String`に確定する。`list`は`List<String>`として静的に型付けされるため、`list.add(10)`は`String`が期待される場所に`int`(オートボクシングしても`Integer`)を渡そうとしてコンパイルエラーになる。

正解: C

あなたの回答: C

## 問題6-2

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static <E> void bar(List<E> list) {
6          for (E e : list) System.out.println(e);
7      }
8      public static void main(String[] args) {
9          List<Integer> list = new ArrayList<>();
10         list.add(1);
11         <Integer>bar(list);
12     }
13 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる。実行すると`1`が出力される
B. 11行目でコンパイルエラーになる
C. 5行目でコンパイルエラーになる
D. コンパイルは通るが、実行時に例外がスローされる

**実施記録**

迷ったポイント: 「staticメソッドは同じクラス内なら修飾子なしで呼べるか」という論点と混同していた。`bar(list);`(型引数なし)なら同じクラス内なので修飾子なしで普通に呼べる。しかし`<Integer>bar(list);`のように明示的な型引数を書く場合は、Javaの構文上その前に必ず修飾子(クラス名や`this`)が必要というルールがあり、それに違反しているのが今回のエラー原因。

解説(概念): 明示的型引数付きのメソッド呼び出しは`修飾子.<型引数>メソッド名(...)`という形が文法上必須。`Main.<Integer>bar(list)`や`this.<Integer>bar(list)`なら通るが、修飾子なしの`<Integer>bar(list)`は構文エラー(`式の開始が不正です`)になる。

正解: B

あなたの回答: A

## 問題7-1

```java
1  class Foo<T extends Number> {
2      T value;
3      Foo(T value) { this.value = value; }
4      double doubled() { return value.doubleValue() * 2; }
5  }
6  class Bar<T, X extends T> {}
7  public class Main {
8      public static void main(String[] args) {
9          Foo<Integer> f1 = new Foo<>(10);
10         Foo<String> f2 = new Foo<>("10");
11         Bar<Number, Integer> b1 = new Bar<>();
12         Bar<Object, String> b2 = new Bar<>();
13         System.out.println(f1.doubled());
14     }
15 }
```

このコードをコンパイルするとどうなるか。

A. 10行目のみでコンパイルエラーになる
B. 10行目と12行目でコンパイルエラーになる
C. 11行目と12行目でコンパイルエラーになる
D. すべて問題なくコンパイルできる

**実施記録**

迷ったポイント: `Bar<T, X extends T>`の`T`と、`Foo<T extends Number>`の`T`を同一視し、`Bar`側の`T`にも「Numberでなければならない」という制約が及ぶと誤認していた。型パラメータ名はクラスごとにローカルな名前であり、`Bar`自身は`T`に境界を宣言していない(暗黙に`T extends Object`)ため、`Bar<Object, String>`(12行目)は`X(String)`が`T(Object)`のサブタイプという条件を満たし問題なくコンパイルできる。

解説(概念): 境界`extends Number`は`Foo`というクラスの`T`にのみ有効なローカルな制約で、別クラス`Bar`の同名の型パラメータ`T`には一切継承されない。`Bar<T, X extends T>`で制約があるのは`X`(`T`のサブタイプでなければならない)だけで、`T`自体には境界がないため任意の型を入れられる。10行目`Foo<String>`だけが`Foo`自身の境界`T extends Number`に違反してコンパイルエラーになる。

正解: A

あなたの回答: B

## 問題7-2

```java
1  class MultiBound<T extends Number & Comparable<T>> {
2      T value;
3      MultiBound(T value) { this.value = value; }
4      int compareToZero(T zero) { return value.compareTo(zero); }
5  }
6  public class Main {
7      public static void main(String[] args) {
8          MultiBound<Integer> m1 = new MultiBound<>(5);
9          System.out.println(m1.compareToZero(0));
10         MultiBound<String> m2 = new MultiBound<>("abc");
11     }
12 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる。実行すると`1`が出力される
B. 4行目でコンパイルエラーになる
C. 10行目で実行時に`ClassCastException`がスローされる
D. 10行目でコンパイルエラーになる

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): `T extends Number & Comparable<T>`は「`Number`のサブタイプであること」と「`Comparable<T>`を実装していること」の両方を満たさなければならないAND条件(複数境界)。`String`は`Comparable<String>`は実装しているが`Number`のサブタイプではないため、10行目の時点でこの境界条件に違反しコンパイルエラーになる。なお複数境界を書く場合、クラス(`Number`)は必ず先頭に置き、インタフェース(`Comparable<T>`)はその後に`&`で並べる、という順序の制約もある。

正解: D

あなたの回答: D

## 問題8-1

```java
1  public class Main {
2      static <T extends Comparable<T>> void method(T t1, T t2) {
3          if (t1.compareTo(t2) > 0) System.out.println(t1);
4      }
5      public static void main(String[] args) {
6          method(10, 20);
7          method(10, 20L);
8          method("a", "b");
9      }
10 }
```

このコードをコンパイルするとどうなるか。

A. 7行目でコンパイルエラーになる
B. すべて問題なくコンパイルできる。実行すると何も出力されない
C. 6,7,8行目すべてでコンパイルエラーになる
D. 7行目で実行時に例外がスローされる

**実施記録**

迷ったポイント: 仮引数の型が`long`のように固定されている場合はint→longのワイドニングが起きるが、今回の仮引数は型パラメータ`T`(参照型のみを受け付ける箱)であるため、プリミティブは先にオートボクシングされてから`T`にはめ込まれる、という順序を見落としていた。`10`は`Integer`に、`20L`は`Long`にそれぞれ独立にボクシングされ、ワイドニングが介在する余地がない。

解説(概念): `method(T t1, T t2)`は`t1`と`t2`が同じ1つの`T`であることを要求する。`Integer`と`Long`はどちらも`Number`のサブタイプというだけで互いにサブタイプ関係にない「兄弟」同士のため、単一の`T`として推論できず7行目でコンパイルエラーになる(`推論変数Tには、不適合な境界があります: 等価制約 Integer,Long`)。

正解: A

あなたの回答: B

## 問題8-2

```java
1  public class Main {
2      static <T> void unboundedMethod(T t1, T t2) {
3          if (t1.compareTo(t2) > 0) System.out.println(t1);
4      }
5      static <T extends Comparable<T>> void boundedMethod(T t1, T t2) {
6          if (t1.compareTo(t2) > 0) System.out.println(t1);
7      }
8      public static void main(String[] args) {
9          boundedMethod(5, 3);
10         unboundedMethod(5, 3);
11     }
12 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる
B. 6行目でコンパイルエラーになる
C. 3行目でコンパイルエラーになる
D. 3行目と6行目でコンパイルエラーになる

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): 境界なしの`<T>`は暗黙に`T extends Object`であり、`Object`は`compareTo`メソッドを持たないため3行目は`シンボルを見つけられません`でコンパイルエラーになる。一方`<T extends Comparable<T>>`は境界により`compareTo`の存在が保証されるため6行目は問題なくコンパイルできる。

正解: C

あなたの回答: C

## 問題9-1

```java
1  public class Main {
2      public static void main(String[] args) {
3          Number[] nArr = new Integer[3];
4          nArr[0] = 10;
5          nArr[1] = 3.14;
6          System.out.println(nArr[0]);
7      }
8  }
```

このコードを実行するとどうなるか。

A. コンパイルエラーになる
B. `10`が出力される
C. 4行目で`ArrayStoreException`がスローされる
D. 5行目で`ArrayStoreException`がスローされる

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): 配列は共変(`Number[] nArr = new Integer[3];`はコンパイルが通る)だが、実体の配列は`Integer`専用のまま。5行目`nArr[1] = 3.14`は静的には`Number`への代入に見えても、JVMは配列に値を格納する瞬間に実体の要素型と実際に格納しようとした値の型を照合しており、`Integer`専用配列に`Double`を入れようとしたことが検出されて`ArrayStoreException`がスローされる。ジェネリクスの`List<Number>`と違い、配列は実行時まで型情報を保持しているためこのチェックが可能。

正解: D

あなたの回答: D

## 問題9-2

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static void addNull(List<?> list) {
6          list.add(null);
7      }
8      static void printAll(List<? extends Number> list) {
9          for (Number n : list) System.out.print(n);
10     }
11     public static void main(String[] args) {
12         List<Integer> iList = new ArrayList<>();
13         iList.add(1);
14         iList.add(2);
15         addNull(iList);
16         printAll(iList);
17         System.out.println();
18         System.out.println(iList.size());
19     }
20 }
```

このコードを実行するとどうなるか。

A. `12null` の後に `3` が出力される
B. コンパイルエラーになる
C. `12` の後に`NullPointerException`がスローされる
D. `12` の後に `2` が出力される

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): `List<?>`は要素の追加が`null`のみ許可されるため`addNull`は問題なくコンパイル・実行できる。`List<? extends Number>`は実際の要素型が何であれ`Number`として取り出すことが安全に保証されているため、拡張for文で`Number n`として取り出せる。要素が`null`のときも`System.out.print`は`NullPointerException`を投げず文字列`"null"`として出力する。最終的に`iList`には`1, 2, null`の3要素が入っているため`size()`は`3`。

正解: A

あなたの回答: A

## 問題10-1

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static void testUpperBnd(List<? extends Number> list) {
6          list.add(null);
7          Number n = list.get(0);
8      }
9      static void testLowerBnd(List<? super Number> list) {
10         list.add(Integer.valueOf(10));
11         Object o = list.get(0);
12     }
13     public static void main(String[] args) {
14         List<Object> oList = new ArrayList<>();
15         List<Integer> iList = new ArrayList<>();
16         testUpperBnd(oList);
17         testLowerBnd(iList);
18     }
19 }
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる
B. 16行目のみコンパイルエラーになる
C. 16行目と17行目でコンパイルエラーになる
D. 17行目のみコンパイルエラーになる

**実施記録**

迷ったポイント: なし(一発正解)。

解説(概念): `testUpperBnd`は`List<? extends Number>`を要求するが、16行目で渡した`List<Object>`は`Object`が`Number`のサブタイプではないため不適合。`testLowerBnd`は`List<? super Number>`を要求するが、17行目で渡した`List<Integer>`は`Integer`が`Number`のスーパータイプではないため不適合。どちらも境界条件に違反しコンパイルエラーになる。

正解: C

あなたの回答: C

## 問題10-2

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static <T> void copy(List<? super T> dest, List<? extends T> src) {
6          for (T t : src) dest.add(t);
7      }
8      public static void main(String[] args) {
9          List<Integer> src = new ArrayList<>();
10         src.add(1); src.add(2);
11
12         List<Number> dest = new ArrayList<>();
13         copy(dest, src);
14         System.out.println(dest);
15
16         List<Integer> dest3 = new ArrayList<>();
17         List<Number> numSrc = new ArrayList<>();
18         numSrc.add(1.0);
19         copy(dest3, numSrc);
20         System.out.println(dest3);
21     }
22 }
dest . dest Number以下 src Integer以上　Intのみ
dest3 . dest Int以下　src Num以上　候補なし
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる。実行すると`[1, 2]`のみ出力される
B. 13行目でコンパイルエラーになる
C. 19行目で実行時に`ClassCastException`がスローされる
D. 19行目でコンパイルエラーになる

**実施記録**

迷ったポイント: `List<? super T> dest, List<? extends T> src`という2引数から`T`を逆算する際、「`extends`側の実引数(Integer)からT≧Integer、`super`側の実引数(Number)からT≦Number」という不等式の向きを直感と逆に感じ、13行目の方がエラーになると誤認していた。実際は13行目(`T≧Integer`かつ`T≦Number`→T=Integerで両立)は成立し、19行目(`T≧Number`かつ`T≦Integer`→NumberはIntegerより上位なので重なる`T`が存在しない)の方が矛盾する。「`?`が先に決まっていて`T`を逆算する場面では、`extends`→T以上、`super`→T以下、という不等号の向きになる」という理解が必要だった。

解説(概念): `List<? extends T>`は「実際の型はTかそれ以下」(実際の型 ≦ T)という関係なので、実際の型が分かっている状態でTを逆算すると`T ≧ 実際の型`になる。`List<? super T>`は「実際の型はTかそれ以上」(実際の型 ≧ T)なので、逆算すると`T ≦ 実際の型`になる。13行目は`src=Integer`→T≧Integer、`dest=Number`→T≦Number で`T=Integer`が両立するためOK。19行目は`src=Number`(numSrc)→T≧Number、`dest=Integer`(dest3)→T≦Integer で、NumberはIntegerより上位のため両方を満たす`T`が存在せずコンパイルエラーになる。

正解: D

あなたの回答: B

## 問題10-3

```java
1  import java.util.ArrayList;
2  import java.util.List;
3
4  public class Main {
5      static <T> void copy(List<? super T> dest, List<? extends T> src) {
6          for (T t : src) dest.add(t);
7      }
8      public static void main(String[] args) {
9          List<Integer> iList = new ArrayList<>();
10         iList.add(1);
11         List<Double> dList = new ArrayList<>();
12         dList.add(2.0);
13         List<Number> nList = new ArrayList<>();
14         List<Object> oList = new ArrayList<>();
15
16         copy(nList, iList);
17         copy(oList, dList);
18         copy(iList, dList);
19         copy(nList, dList);
20     }
21 }

N >= T && T >= I T
O >= T && T >= D T
I >= T && T >= D F
N >= T && T >= D T
```

このコードをコンパイルするとどうなるか。

A. すべて問題なくコンパイルできる
B. 17行目のみコンパイルエラーになる
C. 18行目のみコンパイルエラーになる
D. 18行目と19行目でコンパイルエラーになる

**実施記録**

迷ったポイント: なし(問題10-2の不等式の考え方を踏まえて一発正解)。

解説(概念): 16行目(`src=Integer`→T≧Integer, `dest=Number`→T≦Number)は`T=Integer`で両立しOK。17行目(`src=Double`→T≧Double, `dest=Object`→T≦Object)は`T=Double`で両立しOK。18行目(`src=Double`→T≧Double, `dest=Integer`→T≦Integer)は`IntegerとDouble`が互いに親子関係のない「兄弟」型のため両立する`T`が存在せずエラー。19行目(`src=Double`→T≧Double, `dest=Number`→T≦Number)は`Double`が`Number`の子なので`T=Double`で両立しOK。エラーになるのは18行目のみ。

正解: C

あなたの回答: C