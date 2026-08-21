# 6章 応用問題まとめ（追加復習2）

`chapter6_summary.md`（原本24問）に対する補完版。応用問題（問題6-1発展）で扱った、原本には出てこなかった論点をまとめる。

## 応用問題6-1：sealed / permits の細かいルール

問題：以下A〜Fのうちコンパイルが成功するものをすべて選べ。

```java
// A（成功）
public sealed class Shape permits Circle, Square {}
final class Circle extends Shape {}
final class Square extends Shape {}

// B（成功）
sealed abstract class Vehicle permits Car {}
non-sealed class Car extends Vehicle {}

// C（成功）
abstract sealed class Animal permits Dog {}
final class Dog extends Animal {}

// D（成功）
sealed class Fruit {}
final class Apple extends Fruit {}
final class Banana extends Fruit {}

// E（失敗）
sealed class Bird permits HouseSparrow {}
final class Sparrow extends Bird {}
final class HouseSparrow extends Sparrow {}

// F（失敗）
sealed class Beverage permits Coffee {}
class Coffee extends Beverage {}
```

**正解：A, B, C, D**（javacで実機検証済み）

| # | 論点 | 結論 |
|---|---|---|
| B, C | 修飾子の並び順 | `sealed abstract` でも `abstract sealed` でもコンパイラは同じものとして扱う。順序は自由（原本にはなかった観点） |
| D | `permits`省略の正確な条件 | 「子が1つのときだけ省略可」ではない。**直接の子クラスが全員、同一ソースファイル（.java）内に揃っていれば**、何個いても省略可。パッケージが同じかどうかは無関係 |
| E | `permits`に書けるのは直接の子のみ | `Bird → Sparrow → HouseSparrow` のように孫を`permits`に書くのは無効。中間の`Sparrow`が`permits`から漏れている扱いになりコンパイルエラー |
| F | 直接の子には修飾子必須 | sealedクラスの直接の子は`final`・`sealed`・`non-sealed`のいずれかを**必ず**明示する。無指定は「sealed、non-sealedまたはfinal修飾子が必要です」でエラー |

### 「permits省略＝sealedの意味が薄れる」は誤解

`permits`を省略しても、**閉じた継承階層としての強制力はまったく変わらない**。実験で確認：

```java
// pkg/Fruit.java
package pkg;
sealed class Fruit {}
final class Apple extends Fruit {}
final class Banana extends Fruit {}

// pkg/Grape.java（同じパッケージ、別ファイル）
package pkg;
final class Grape extends Fruit {}
```

→ 結果：`Grape.java`はコンパイルエラー。
```
エラー: クラスはシール・クラスFruitを拡張できません('permits'句に指定されていないためです)
```

同じパッケージにいても、`Fruit.java`と別ファイルにいる時点で「仲間」とは認識されない。`Grape`を正式に許可するには2択のみ：
1. `Fruit`側に `permits Apple, Banana, Grape` と明示的に書く
2. `Grape`を`Fruit.java`の中に書く

→ **結論：`permits`省略は「同一ファイル内で完結しているなら、コンパイラが代わりにリストを組み立ててくれる」という省略記法にすぎない。閉じている度合い（部外者を拒否する強さ）は明示時と完全に同じ。**

## extends と implements の使い分け

- **クラスを継承する**→ `extends`（`sealed class`であっても、クラスである以上は`extends`）
- **インタフェースを実装する**→ `implements`

`sealed class Fruit {}` の子が `extends Fruit` になるのは、`Fruit`が`interface`ではなく`class`だから。`sealed interface`を実装する側であれば`implements`になる（この違いは継承元が`class`か`interface`かだけで決まり、`sealed`の有無とは無関係）。
