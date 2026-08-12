# 第5章 クラスとメソッドの基礎

対応する教科書ページ: （要確認・進行中）
学習環境: macOS（Apple Silicon）／ JDK 17（Homebrewの`openjdk@17`、17.0.20）
サンプル: `~/Downloads/sample/chap5/`

---

## 1. プリミティブ型 vs 参照型と`null`の許容

Javaの変数は2種類に分かれ、`null`を入れられるかどうかはこの区別だけで決まる（型の種類は無関係）。

- **プリミティブ型**（int, double, boolean, charなど）：箱の中に**値そのもの**が直接入っている。`null`（＝何も指さない状態）を表現する方法がなく、`int[] numbers = {1, null, 3};`のように書くと**コンパイルエラー**（`incompatible types: <null> cannot be converted to int`）。
- **参照型**（String, 配列, クラスのインスタンス全般）：箱の中には値の実体ではなく「実体が置かれている場所（住所）」が入っている。「どこも指していない」＝`null`を表現できる。

```java
int[] numbers = {1, null, 3};    // NG：値そのものを入れる箱にnullは入らない
Integer[] nums = {1, null, 3};   // OK：Integerは参照型（intのラッパークラス）だから住所を入れる箱
```

`Integer`は`int`の値を包んだクラス（オブジェクト）＝参照型なので、`null`を許容できる。「Stringだから」ではなく「参照型だから」が正しい理由づけ。

### 1.1 コンパイルエラーと実行時エラーの違い（重要）

```java
int x = null;              // コンパイルエラー（ビルド時に検出、実行すらできない）

String city = null;
city.equals(null);          // コンパイルは通る。実行すると NullPointerException（実行時エラー）
city == null;                // 常に安全。エラーにならない
```

| コード | エラーの種類 | 発生タイミング |
|---|---|---|
| `int x = null;` | コンパイルエラー | ビルド時 |
| `city.equals(null)`（cityがnull） | 実行時例外（NPE） | 実行してその行に到達した瞬間 |
| `city == null` | エラーなし | 常に安全 |

`==`は参照型同士でも「住所が同じか」を比較するだけの演算子であり、メソッド呼び出しではないためnullでも安全。`.equals()`はメソッド呼び出しなので、呼び出し元がnullだと即NPE。

---

## 2. Integerのキャッシュ範囲（-128〜127）と`==`／`.equals()`

```java
Integer x = 1000;
Integer y = 1000;
x == y;          // false（別オブジェクトの住所を比較している）
x.equals(y);     // true（値を比較している）
```

`Integer`同士の`==`は住所比較。中身が同じ値でも、別々に作られたオブジェクトなら`false`になる。**値を比較したいときは常に`.equals()`を使う**。

### 2.1 -128〜127だけは例外（IntegerCache）

`Integer`クラスは内部に`IntegerCache`を持ち、オートボクシング時（`Integer x = 1;`や`Integer.valueOf(1)`）、**-128〜127の範囲の値だけ、新しいオブジェクトを作らずキャッシュ済みの同じオブジェクトを使い回す**。

実際に検証済み（JDK 17）：

```
126 -> true   (同じオブジェクト)
127 -> true   (同じオブジェクト)
128 -> false  (別オブジェクト)
129 -> false  (別オブジェクト)
130 -> false  (別オブジェクト)
```

JVMの内部フラグ`AutoBoxCacheMax = 128`（`-XX:+PrintFlagsFinal`で確認可能）とも一致。

**重要な注意点**：これは「型が参照型として特殊になる」わけではなく、**Integerクラスというライブラリ実装の最適化**にすぎない。null許容のルール（参照型かどうかだけで決まる）とは完全に無関係の、別次元の話。「-128〜127だけnullの例外」という繋げ方はしない。値が何であれ`Integer`型ならnullは常に許容される。

**実務・試験どちらでも安全な運用**：`Integer`同士の値比較は範囲を暗記するより、常に`.equals()`を使う。

---

## 3. 可変長引数（varargs）の実体は配列

```java
public void method(int i, int... j) {
    for (int v : j) { System.out.print(v); }
}
```

`型... 変数名`は**コンパイラが内部的に`型[] 変数名`として扱うための糖衣構文（シンタックスシュガー）**。実際に検証済み：

```java
method(1, 2, 3, 4);   // j.getClass() = [I（int配列）, j.length = 3
method(1, arr);        // 配列を直接渡してもOK。同じく [I, length = 3
method(1);              // j.getClass() = [I, j.length = 0（nullではなく空配列）
```

- `method(1, 2, 3, 4)`のように個別の引数で呼ぶと、コンパイラが自動で`new int[]{2,3,4}`を組み立てて渡す。
- 呼び出し側は配列を作らなくてよい利便性を得られるが、メソッド内部では**最初から最後まで普通の配列**として扱われる（`.length`, 拡張for文, 添字アクセスすべて通常通り使える）。
- varargs部分は0個でも合法（空配列になるだけで、コンパイルエラーにはならない）。**必須なのはvarargsより前にある固定引数の分だけ**（上記なら`i`の1個のみ）。

---

## 4. メソッドのオーバーロード解決の優先順位

```java
static void method(int i, int... j) { ... }
static void method(int i, int j) { ... }

method(100, 200);   // 固定2引数版が呼ばれる
method(1, 2, 3);     // varargs版が呼ばれる
method(100);          // varargs版が呼ばれる（j = 空配列）
```

「渡した引数の個数で自動的に振り分けられる」わけではなく、**Javaのオーバーロード解決には優先順位（フェーズ）がある**。

1. **フェーズ1**：ボックス化もvarargsも使わず、そのまま型が完全一致するメソッドがあるか
2. **フェーズ2**：オートボクシング／アンボクシングを使えば一致するメソッドがあるか
3. **フェーズ3**：それでも見つからなければvarargsメソッドを候補にする（最後の手段）

`method(100, 200)`は`method(int, int)`がフェーズ1で完全一致するため即決定。varargs版はフェーズ3まで検討される前に候補から外れる。`method(1, 2, 3)`は個数が合う固定引数メソッドが存在しないため、フェーズ1・2で候補がゼロになり、フェーズ3でvarargs版が採用される。

---

## 5. パッケージ宣言とディレクトリ構造

```java
package com;

public class Item { ... }
```

- `package com;`と書くと、そのクラスの正式名称は`Item`ではなく**`com.Item`**になる。パッケージは名前空間であり、**同名クラスの衝突を防ぐ**ための仕組み。
- パッケージ名とフォルダ構成は一致させる規約がある（`package com;`なら`com`という名前のフォルダの中にファイルを置く）。
- 別パッケージのクラスを使うには`import`が必要（例：`import com.Item;`）。書かないと「シンボルが見つからない」でコンパイルエラー。
- パッケージ構造があるプロジェクトのコンパイル・実行は、単純な`javac Main.java`では済まず、出力先（`-d`）やクラスパス（`-cp`）を意識する必要がある。

```bash
cd sources
javac -d ../classes Main.java com/Item.java
java -cp ../classes Main
```

---

## 6. IDEのエラー表示とjavacの実際のコンパイルエラーの違い

サンプル教材（`~/Downloads/sample/`）は`package`宣言なしのファイルが章ごとに大量にあり、同名クラス（`Sample`が8箇所、`Main`も多数）が無名パッケージに散らばっている。

- VS CodeのJava拡張機能（Language Server、Eclipse JDTベース）が、ワークスペース全体を1つの無名パッケージとして解析しようとすると、**同名クラスの衝突により誤った型解決・誤ったエラー表示**が起きることがある（実際に`setNum(int)`が「undefined」と表示されたが、`javac`で直接コンパイル・実行すると問題なく動作するケースを確認）。
- IDEのエラーメッセージ（`The method X is undefined for the type Y`という言い回し）はEclipse JDT特有のもので、javac本来のエラー（`cannot find symbol`）とは文言が異なる。**IDEの赤い波線＝実際のコンパイルエラーとは限らない**。
- 確実な検証方法は、対象フォルダでターミナルから直接`javac`・`java`を実行すること。

```bash
cd ~/Downloads/sample/chap5/2
javac Main.java Sample.java && java Main
```

**根本的な対策**：この教材のように同名クラスが大量にある場合、各章・各問題ごとにパッケージ宣言を分ける（本章5節）ことで、IDE側の型解決の混乱自体を避けられる。

---

## 疑問点・要復習（随時追記）

- IDE上で`setNum(int)`だけがエラー表示され`getNum()`はエラーにならなかった非対称性の正確な原因は未特定（javac上は問題なし。IDEの言語サーバー側のキャッシュ/解決順序の問題と推測されるが未検証）
