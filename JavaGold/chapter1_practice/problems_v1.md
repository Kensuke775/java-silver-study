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

<a id="問題2-1"></a>
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

<a id="問題2-2"></a>
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

<a id="問題3-1"></a>
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

<a id="問題3-2"></a>
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

<a id="問題4-1"></a>
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

<a id="問題4-2"></a>
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

<a id="問題5-1"></a>
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

<a id="問題5-2"></a>
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

<a id="問題6-1"></a>
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

<a id="問題6-2"></a>
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
