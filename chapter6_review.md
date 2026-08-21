# 6章 振り返り（継承とインタフェース）

教科書6章末問題（問題6-1〜6-24）を解いた記録。1回目の自己採点結果と、間違えた箇所を中心に再テストした結果、そのやり取りの中で出た疑問点をまとめる。

---

## 1回目の結果：10/24（部分正解5、不正解9）

弱点が集中していたテーマ：
- **static/privateメンバのアクセス規則**（Q6, Q15）
- **オーバーライドの成立条件**（戻り値の型・アクセス範囲）（Q5, Q14）
- **sealed / permitsの整合性**（Q12, Q16）
- **ArrayList / HashMapの各メソッドの戻り値・シグネチャ**（Q17, Q18, Q20, Q23）

間違えた問題：1, 2, 3, 7, 14, 15, 17, 18, 20（不正解）／5, 10, 12, 16, 23（部分正解）

---

## 再テスト（問1〜15）：14/15

唯一の間違いはQ15（`E`を選んだが正解は`C`）。他は全問正解に改善。

---

## テーマ別の理解メモ

### ① フィールドの隠蔽 vs メソッドのオーバーライド（Q2）

```java
class Top { int x = 1; int y = 2; }
class Middle extends Top {
    double x = 3.5;
    void update() { super.x = 40; }
}
class Bottom extends Middle {
    void update() { super.update(); y = 50; }
}
Middle obj = new Bottom();
obj.update();
System.out.println(obj.x + ":" + obj.y);  // 3.5:50
```

- **メソッド呼び出し**：実体の型（動的束縛／ポリモーフィズム）で決まる → `obj.update()`は実体がBottomなのでBottomの`update()`が動く
- **フィールドアクセス**：宣言型（静的束縛）で決まる → `obj.x`はobjの宣言型`Middle`が持つ`x`（3.5）が選ばれる
- `Middle`が独自に`double x`を宣言しているため、Top由来のxとMiddle由来のxは**メモリ上2つの別フィールド**として存在する（＝隠蔽）。`super.x = 40`はTop側だけを書き換える
- `y`はMiddle/Bottomに独自定義が無いため、継承した唯一のTop由来のyがそのまま更新される

**`super.method()`は「直接の親クラスの実装を1回呼ぶだけ」**であり、「継承チェーンの一番上から順に自動で全部実行される」わけではない。上位まで遡って実行させたければ、各階層のメソッドがそれぞれ明示的に`super`を呼ぶ必要がある。

### ② オーバーロードの判定基準は「シグネチャ」であって「戻り値」ではない（Q5）

```java
class Super { public int func() { return 0; } }
class Sub extends Super {
    public void func() {}  // ✗ コンパイルエラー
}
```

- オーバーロードの成立条件は**メソッド名＋引数リスト（型・数・順序）の違い**のみ。戻り値の型は判定に一切関与しない
- 引数リストが親と完全に一致している時点で、これは「オーバーライドか、さもなくば違反」の扱いになる
- オーバーライドが成立するには戻り値の型が同じか共変（サブタイプ）である必要があり、`int`→`void`は共変ではないため**宣言自体がコンパイルエラー**になる

### ③ privateメソッドはポリモーフィズムの対象外（Q6）

```java
class Parent {
    private static void methodA() {...}
    private void methodB() {...}
}
class Child extends Parent {
    public static void methodA() {...}
    public void methodB() {...}
}
Parent obj = new Child();
Child.methodA();  // OK
obj.methodB();    // ✗ 21行目でコンパイルエラー
```

- privateメソッドは**サブクラスに継承されない**。Childの同名メソッドはParentのものと完全に無関係な別メソッド
- 継承されない＝オーバーライドという概念が成立しない＝**動的束縛（実体の型を見る）の対象外**
- `obj.methodB()`は宣言型`Parent`の`methodB()`（private）を見に行き、これは`Main`（外部クラス）からアクセス不可なため、**実体がChildかどうかに関係なくコンパイル時点で弾かれる**
- 対照的に、Q2の`update()`のようなpublicメソッドは動的束縛の対象なので実体（Bottom）の実装が呼ばれる。「private/static/フィールドは宣言型で静的に決まる」「public/protected/package-privateのインスタンスメソッドは実体型で動的に決まる」という区別が本質

### ④ 別パッケージ間の継承とprotected（Q4）

```java
package ex6.foo;
public class One {
    public int a = 1;
    int b = 2;          // package-private
    protected int c = 3;
}
package ex6.bar;
import ex6.foo.One;
public class Two extends One {
    int calc() { return a + b + c; }  // ✗ bでコンパイルエラー
}
```

- `import`は「短い名前で書けるようにする」だけの機能で、**パッケージを同一にする効果は無い**
- protectedは「同一パッケージ」または「**別パッケージであっても継承関係にあるサブクラス**」からアクセス可能
- package-private（デフォルト）は継承していても**同一パッケージでなければアクセス不可**
- 「継承していれば何でも見える」わけではなく、protectedだけが持つ特別な公開範囲、という点がこの問題の核心

### ⑤ recordの継承制約（Q9, Q12）

- 全レコードは暗黙的に`java.lang.Record`を継承するが、**`extends Record`と明示的に書くのは構文エラー**（`record Sample() extends Record {}`はNG）
- 一方で`record Record() {}`のように、`Record`という識別子をレコード自身の名前として使うのは問題ない（`record`は文脈依存キーワード、`Record`は単なるクラス名として扱われる）
- **レコードは他のクラスをextendsできない**：コンパイラが自動で`extends java.lang.Record`を埋め込むため、単一継承ルール上、他クラスを同時に継承する余地が無い
- **レコードは暗黙final＝他のクラスから継承されない**：レコードの`equals()`/`hashCode()`/`toString()`はコンポーネント（宣言したフィールド）だけを根拠に自動生成される。サブクラス化を許すと隠れた追加状態が持ててしまい、この同一性の保証が壊れるため、設計上封じられている
- レコードは**インタフェースの実装（implements）は可能**（継承＝extendsとは別の話）

### ⑥ sealedクラス／インタフェースの`permits`整合性（Q12, Q16）

- sealedクラスの`permits`にレコードを指定することは**不可能**（上記の通りレコードは他クラスをextendsできないため）。レコードをsealed階層に参加させたい場合は、sealedインタフェースを作ってレコードにimplementsさせる設計にする
- sealedの型（クラス・インタフェース問わず）を継承・実装する側に許される修飾子は**`final` / `sealed` / `non-sealed`の3択が必須**（「sealedまたはnon-sealedのどちらか」ではない。`final`が抜けている選択肢は誤り）
- `permits`に書けるのは**直接の子のみ**。孫を指定したり、`permits`に列挙したクラスが実在しない場合はコンパイルエラー

### ⑦ インタフェースの抽象メソッドは暗黙public（Q13）

```java
interface X { void methodX(); }        // 暗黙 public abstract
abstract class Base implements X {
    void methodA() {}                   // package-private のまま（暗黙publicにはならない）
    abstract void methodB();
}
class Derived extends Base {
    public void methodX() {}            // publicを明示しないとアクセス範囲を狭めることになりNG
    void methodB() {}                   // Baseの宣言どおりpackage-privateでOK
}
```

- **インタフェース由来**の抽象メソッドは暗黙`public`。オーバーライド時に`public`を省略すると、アクセス範囲を狭めたことになりコンパイルエラー
- **抽象クラス自身**が宣言する抽象メソッドは、書いたとおりのアクセス修飾子（ここではpackage-private）のままでよい
- 「由来がインタフェースか抽象クラスか」でオーバーライド時に要求されるアクセス修飾子が変わる、という点が本質

### ⑧ インタフェースのstaticメンバ：フィールドとメソッドで扱いが違う（Q15）

```java
interface Test {
    String TEST_NAME = "Test";      // 暗黙 public static final
    static double calc() { ... }    // static メソッド
}
class Main implements Test {
    Main m = new Main();
    m.TEST_NAME;    // OK（フィールドはインスタンス経由も可）
    m.calc();       // ✗ NG（staticメソッドはインタフェース名経由必須）
    Test.calc();    // OK
}
```

- interfaceのstatic変数は暗黙`public static final`で、**インスタンス経由でもインタフェース名経由でもアクセス可能**
- interfaceのstaticメソッドは**インタフェース名経由でのみ**呼び出し可能。インスタンス経由（`m.calc()`）はコンパイルエラー
- 「staticフィールドはインスタンスOK、staticメソッドはインスタンスNG」という非対称ルールに注意

---

### ⑨ ダウンキャストの危険性とinstanceofガード（Q18）

```java
interface Browser { default void browse() { print(" Browsing.."); } }
class MobilePhone implements Browser {
    public void call() { print(" Calling.."); }
    public void browse() { print(" Just scrolling.."); }  // オーバーライド
}
class Laptop implements Browser {}  // browse()は上書きしていない → defaultのまま

Browser br = new MobilePhone();
br.browse();                         // "Just scrolling.."（overrideが呼ばれる）
br = new Laptop();
br.browse();                         // "Browsing.."（defaultがそのまま呼ばれる）
MobilePhone mp = (MobilePhone)br;    // ✗ 実体はLaptop → ClassCastException
mp.call();
```

- MobilePhoneとLaptopは、どちらも`Browser`を実装しているだけの**兄弟関係**（Q17と同じ構造）
- `Browser br = new MobilePhone()`のような「具体的な型→抽象的な型」への代入は**アップキャスト**で、キャスト構文なしで自動的に許可される（安全だから）
- `MobilePhone mp = (MobilePhone)br`のような「抽象的な型→具体的な型」は**ダウンキャスト**で、明示的なキャスト構文が必須。コンパイラは「型としてはあり得る」という理由だけで通してしまうが、**実際にその型かどうかは実行時まで保証されない**
- 通常の型ミスマッチはコンパイル時に発覚するが、**キャストの型ミスマッチはそのコードが実際に実行されるまで発覚しない**。条件分岐やループの奥に埋まっていると、特定の条件のときだけ本番で突然`ClassCastException`が飛ぶ、という事故になり得るのが実務でキャストが警戒される理由
- **対策**：いきなりキャストせず、先に`instanceof`で確認する（Q17のパターンマッチング構文がそのまま使える）

```java
if (br instanceof MobilePhone mp) {  // 実体を確認してから
    mp.call();                        // ここに入れば絶対に安全
}
```

「型が合っているかどうかの判定を、コンパイラに任せず自分で握ってしまう」のがキャストの本質。ミスがあっても実行するまで気づけない、という点を常に意識する。

### ⑩ instanceofが見るのは「縦のライン」だけ（Q17の発展）

```java
// 元のコード：OneとTwoは兄弟
class One extends Zero {}
class Two extends Zero implements X {}
// → new Two() は instanceof One が false（共通の親Zeroを持つだけの他人）

// 書き換えたコード：One→Twoの一直線チェーン
class One extends Zero {}
class Two extends One implements X {}
// → new Two() は instanceof Zero も instanceof One も true（縦に繋がっているので全部is-a）
```

`instanceof`は「実体のクラスから見て、縦方向（祖先、または実装しているインタフェース）に辿り着けるか」だけを見る。共通の祖先を持つだけの「兄弟」同士は無関係。`extends`の相手を1つ変えるだけで、ツリー構造が「兄弟」から「一直線の親子」に変わり、結果が丸ごと変わる。

### ⑪ 用語の整理：オーバーライドとオーバーロードは別物

- **オーバーライド**：親（クラス／interfaceのdefault実装）と**同じシグネチャ**のメソッドを、サブクラス側で上書き・再定義すること
- **オーバーロード**：**同じ名前で引数リストが違う**メソッドを複数定義すること

カタカナで似ているが全くの別概念。Q18の`MobilePhone`が`Browser`の`default browse()`を上書きしているのはオーバーライド。

### ⑫ アップキャストとダウンキャストの違い、キャストの危険性（Q18）

| | アップキャスト | ダウンキャスト |
|---|---|---|
| 方向 | 具体的な型 → 抽象的な型 | 抽象的な型 → 具体的な型 |
| キャスト構文 | 不要（暗黙・自動） | 必須（`(型)`を明示） |
| 安全性 | 常に保証される | 実行時まで保証されない |

```java
Browser br = new MobilePhone();      // 暗黙のアップキャスト（キャスト構文なし）
MobilePhone mp = (MobilePhone)br;    // 明示的なダウンキャスト（実体がLaptopならClassCastException）
```

- 通常の型ミスマッチはコンパイル時に発覚するが、**キャストの型ミスマッチはそのコードが実際に実行されるまで発覚しない**。条件分岐やループの奥に埋まっていると、特定条件のときだけ本番で突然落ちる事故になり得る
- 対策：いきなりキャストせず`if (br instanceof MobilePhone mp) { ... }`で先に確認してから使う

### ⑬ ArrayListのadd/set/removeの引数・戻り値の違い（Q19〜Q21）

```java
void add(int index, E element)   // 挿入。範囲は 0〜size（sizeちょうどなら末尾に追加できる）
boolean add(E e)                 // 末尾に追加。常にtrue
E set(int index, E element)      // 置き換え。範囲は 0〜size-1（既存の要素のみ）。戻り値は置き換え「前」の要素
E remove(int index)              // インデックス指定で削除。戻り値は削除された要素そのもの
boolean remove(Object o)         // 値指定で削除。戻り値は削除できたかどうか
```

- `add()`は末尾なら新しい枠を作れるが、`set()`は既存の要素の上書き専用で新しい枠は作れない（範囲外は`IndexOutOfBoundsException`）。ArrayListは隙間を自動でnull埋めしたりしない
- `set()`の戻り値は`boolean`ではなく、置き換え前にそこにあった要素そのもの
- `numbers.remove(1)`の`1`はint型リテラルなので、`remove(int index)`（インデックス版）が呼ばれる。値としての`1`を消したい場合は`numbers.remove(Integer.valueOf(1))`のように明示的にIntegerを渡す必要がある。この曖昧さは**数値系ラッパークラス（Integer, Long等）のリストでだけ**起こり、`List<String>`などでは発生しない（Stringはintに変換しようがないため）

### ⑭ コレクション操作メソッドの一覧：add/put, remove（List/Set/Map）

| コレクション | 追加 | 削除 | 削除の戻り値 |
|---|---|---|---|
| List | `add(e)` | `remove(int index)` | 削除された要素そのもの |
| List | 〃 | `remove(Object o)` | 削除できたか（`boolean`） |
| Set | `add(e)` | `remove(Object o)` | 削除できたか（`boolean`） |
| Map | `put(key, value)` | `remove(Object key)` | 紐づいていた値（無ければ`null`） |

- Mapに`add()`というメソッドは存在しない（`put()`のみ）。存在しないメソッドを呼ぼうとするとコンパイルエラー
- Setの`add()`はboolean戻り値に意味がある（重複していたら`false`）。Listの`add(e)`は常に`true`なので実質意味がない
- 配列は`println()`でそのまま出力できず`Arrays.toString()`が必要だが、Map/List/Setは自前の`toString()`を持っているので`println()`だけで中身が読める形式になる

### ⑮ ジェネリクスは配列と違って不変（共変ではない）（Q23）

```java
// 配列は共変：コンパイルは通るが実行時に事故る
Object[] objs = new String[3];   // ○ String[]はObject[]のサブタイプとして扱われる
objs[0] = 42;                    // ○ コンパイルは通ってしまう
                                  // ✗ 実行時に ArrayStoreException（実体はString[3]専用の箱だから）

// ジェネリクスは不変：そもそもコンパイルが通らない
List<Object> list = new ArrayList<String>();  // ✗ コンパイルエラー
```

- 配列は「実行時に型チェックする仕組み（ArrayStoreException）」を持つが、ジェネリクスは型消去（実行時に型情報が消える）のためその仕組みが使えない。だから設計上わざと**不変**にして、危険な代入をコンパイル時点で防いでいる
- ダイヤモンド演算子`<>`で型指定を省略できるのは**右辺のみ**（`List<String> list = new ArrayList<>();`）。左辺（`List<> list = ...`）は省略不可
- `<E>`は**クラス／メソッドを定義する側**が使う型パラメータのプレースホルダー（`class Box<E> {...}`のような場面専用）。オブジェクトを生成する使う側の場面（`new ArrayList<E>()`など）で書くのは誤り
- 型パラメータを一切書かない`ArrayList list = new ArrayList();`（raw型）は非推奨だが文法上はコンパイルが通る

### ⑯ Arrays.mismatch()の比較基準はequals()（Q24）

```java
Object[] oArr = {"100", new String("200"), 300};
String[] sArr = {"100", "200", "300"};
Arrays.mismatch(sArr, oArr);  // → 2
```

- 各要素を`equals()`で比較し、最初に不一致となるインデックスを返す（全一致なら-1）
- `new String("200")`と`"200"`は参照（`==`）としては別オブジェクトだが、`equals()`で比較しているので値が同じなら一致扱いになる
- `"300"`（String）と`300`（Integer）は型自体が違うため`equals()`がfalse → ここが最初の不一致

## 今後の復習ポイント

- 6章末問題（Q1〜Q24）は一通り再確認済み。次章（7章：例外処理）の学習に進む
