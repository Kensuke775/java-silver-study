# 6章 instanceofと継承チェーン 問題集

## 前提知識メモ

### 静的型 と 動的型

| | 静的型（宣言型） | 動的型（実体型） |
|---|---|---|
| 決まるタイミング | コンパイル時 | 実行時（`new`した瞬間） |
| 何によって決まるか | 変数の宣言（左辺の型） | 実際に生成されたオブジェクト |
| 実行中に変わるか | 変わらない | 変わらない（生成時のまま固定） |
| 例（`Animal a1 = new Bird();`） | `Animal` | `Bird` |

```java
Animal a1 = new Bird();
// 静的型: Animal（宣言通り、固定）
// 動的型: Bird（実体、これも固定）
```

### どちらが使われるかは操作の種類で決まる

| 操作 | 判定に使われる型 |
|---|---|
| フィールドへのアクセス（フィールド隠蔽） | 静的型 |
| staticメソッドの呼び出し | 静的型 |
| オーバーロードの解決 | 静的型 |
| インスタンスメソッドのオーバーライド（動的束縛） | 動的型 |
| `instanceof`の判定 | 動的型 |

### instanceofのルール

- `instanceof`は**静的型を無視し、動的型（実体）を起点に継承チェーンを上へ辿る**。指定した型が、その継承チェーン上（自分自身・親クラス・実装しているinterface）にあれば`true`
- チェーンを**下（子クラス側）にはたどらない**。実体が`Bird`なら`Animal instanceof`は`true`だが、`Penguin instanceof`（`Bird`のさらに下）は`false`
- `null instanceof AnyType`は**常に`false`**。例外（NPEなど）は発生しない。nullには型がないため、どんな型判定も成立しない
- パターンマッチング`instanceof`（`if (a instanceof Bird b)`）も同じ判定ルールに従い、一致すればその場で束縛変数（`b`）が使えるようになる

---

## 問題6-17：instanceofと継承チェーン（選択式）

```java
interface Flyable {}

class Animal {}
class Bird extends Animal implements Flyable {}
class Penguin extends Bird {}

public class Main {
    static void check(Animal a) {
        System.out.println(a instanceof Animal);
        System.out.println(a instanceof Bird);
        System.out.println(a instanceof Penguin);
        System.out.println(a instanceof Flyable);

        if (a instanceof Bird b) {
            System.out.println("pattern: " + b.getClass().getSimpleName());
        } else {
            System.out.println("pattern: no match");
        }
    }

    public static void main(String[] args) {
        Animal a1 = new Bird();
        check(a1);
        System.out.println("---");
        Animal a2 = new Penguin();
        check(a2);
        System.out.println("---");
        Animal a3 = new Animal();
        check(a3);
        System.out.println("---");
        Animal a4 = null;
        System.out.println(a4 instanceof Animal);
    }
}
```

`main`の出力として正しいものを、A〜Dから1つ選んでください。

**A.**
```
true
true
false
true
pattern: Bird
---
true
true
true
true
pattern: Penguin
---
true
false
false
false
pattern: no match
---
false
```

**B.**
```
true
true
false
true
pattern: Bird
---
true
false
true
true
pattern: Bird
---
true
false
false
false
pattern: no match
---
false
```

**C.**
```
true
true
false
true
pattern: Bird
---
true
true
true
true
pattern: Penguin
---
true
false
false
false
pattern: no match
---
true
```

**D.**
```
true
true
false
false
pattern: Bird
---
true
true
true
true
pattern: Penguin
---
true
false
false
false
pattern: no match
---
false
```

---

## 解答

正解：**A**

- `a1`（動的型`Bird`）：`Animal`→true、`Bird`→true（自分自身）、`Penguin`→false（下位なので）、`Flyable`→true（`Bird`が実装）、パターン一致→`Bird`
- `a2`（動的型`Penguin`）：`Animal`→true、`Bird`→true、`Penguin`→true（自分自身）、`Flyable`→true、パターン一致→`Penguin`
- `a3`（動的型`Animal`）：`Animal`→true、それ以外はすべて上位／無関係なのでfalse、パターン不一致→"no match"
- `a4 = null`：`null instanceof Animal`は常に`false`。NPEにはならない

すべてjavac(--release 17)/javaで実機検証済み。

---

## 実施記録

### 1回目（2026-08-22）

| 回答 | 正解 | 判定 |
|---|---|---|
| C | A | 誤り |

Cとの違いは最後の1行のみ：`a4 instanceof Animal`（`a4 = null`）を`true`と誤答。

### 迷ったポイントの詳細

**null instanceof（誤答の原因）**：`null instanceof AnyType`は常に`false`になる、という点を誤って`true`と判断した。正しくは、nullには型が存在しないため、どんな型判定も成立せず`false`。例外（NPE等）も一切発生しない。

**理解の整理（誤答後に到達した正しい結論）**：
- `instanceof`が見るのは変数の**静的型（宣言型）ではなく、動的型（実体の型）**
- 動的型を起点に継承チェーンを**上方向にのみ**辿り、指定した型が含まれていれば`true`
- 「型が2つある」（静的型と動的型）という気づきが、この誤答を正しく修正する決め手になった
