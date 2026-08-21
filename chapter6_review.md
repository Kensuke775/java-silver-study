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

## 今後の復習ポイント

- ArrayList/HashMapの各メソッドの戻り値の型・シグネチャ違い（`remove(int)` vs `remove(Object)`、`set()`の戻り値など）は未着手（元の24問時点でQ17〜Q23あたりを再度手を動かして確認する）
- ジェネリクスのダイヤモンド演算子の省略可否（Q23）も要復習
