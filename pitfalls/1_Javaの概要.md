# 落とし穴1：Javaの概要と簡単なJavaプログラムの作成

基本ルールに対する例外パターン・引っかかりやすいポイントだけを集めたメモ。問題演習で見つけたものを都度追加していく。`pitfalls/`配下に章ごとのファイルを置く構成。

<a id="pitfall-public-class-filename-position-irrelevant"></a>
## 1. `public`クラスとファイル名の一致は必須だが、「クラスの並び順」は無関係

原本1-4より。

```java
// ファイル名：Main.java
public class Main {}
class Hello {}
```

このように`public`クラスが**2番目**に書かれていてもコンパイルは成功する。「先頭のクラスがファイル名と一致していないといけない」という思い込みは誤り——判断基準は**「`public`が付いたクラスの名前とファイル名が一致しているか」**だけで、そのクラスがファイル内のどこに書かれているかは無関係。

さらに、`public`クラスが**1つも無い**場合はファイル名との一致は一切不問（実際に`Hello.java`の中に`class Hello{} class Main{} class Test{}`とだけ書いても全部コンパイル成功することを検証済み）。1ファイルに`public`クラスは1つまでで、2つあればその時点で即アウト。

---

<a id="pitfall-package-manages-classfile-not-source"></a>
## 2. パッケージが管理するのは「クラスファイル」であり、「同じパッケージ名の使い回し」はむしろ正常

原本1-5より。

「パッケージはソースファイルを管理する仕組み」という説明は誤り——管理対象は**クラスファイル**。またパッケージ名は複数の異なるファイル間で使い回してよく、それこそがパッケージ本来の使い方（同じパッケージに複数クラスをまとめる仕組みそのもの）：

```java
// Foo.java
package com.se.sub;
public class Foo {}

// Bar.java（同じパッケージ名を別ファイルで再宣言している）
package com.se.sub;
public class Bar {}
```

これはコンパイルエラーにならない。逆に**同じパッケージ内**で同名クラスを2つ定義すると「クラスa.Sampleが重複しています」というエラーになる——「パッケージが違えば同名クラス可、パッケージが同じなら同名クラス不可」という軸で覚える。

---

<a id="pitfall-package-import-order-and-wildcard-scope"></a>
## 3. package宣言は必ずimportより先、`*`はクラス名部分にしか使えない

原本1-6より。

```java
import com.a.*;
package com.p;      // ← 順番が逆でコンパイルエラー
```

```java
package com.p;
import com.*.*;      // ← パッケージ名の途中を`*`で省略するのはNG
```

```java
package com.*;       // ← package宣言自体には`*`は絶対に使えない
import com.a.Sample;
```

ルールは4つ：①package宣言とimport宣言が両方ある場合はpackage宣言が先　②`*`はimportの**末尾のクラス名部分のみ**省略可能（サブパッケージ部分の省略や、package宣言自体でのワイルドカードは不可）　③package宣言は1ファイルに1つのみ　④import宣言は1ファイルに複数指定してよい（特定クラスのimportとワイルドカードimportの混在もOK）。
