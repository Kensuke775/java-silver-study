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

### instanceofのコンパイル時チェック（型の組み合わせで結果が変わる）

`instanceof`は実行時判定の前に、コンパイラが「この2つの型は絶対に両立しえない」と**静的に証明できるか**をチェックする。証明できれば実行前にコンパイルエラーになる。

| 比較の種類 | 結果 | 理由 |
|---|---|---|
| クラス vs クラス（継承関係あり） | ✅ コンパイル可（実行時判定） | 片方がもう片方のサブクラスなら両立しうる |
| クラス vs クラス（無関係） | ❌ 常にコンパイルエラー | Javaは単一継承。無関係な2クラスを両方継承した子は誰にも作れない。**finalの有無は無関係** |
| クラス（non-final） vs interface（未実装） | ✅ コンパイル可（実行時判定） | 将来そのクラスのサブクラスがinterfaceを実装するかもしれない、という可能性を否定できない |
| クラス（final） vs interface（未実装） | ❌ コンパイルエラー | finalなのでサブクラスを作れない＝将来も実装されえないと証明できる |

**重要**：「final」が効くのは**クラス vs interfaceの組み合わせだけ**。クラス同士の無関係判定にはfinalは一切関与しない（finalがなくても、無関係なクラス同士は単一継承の理屈だけで常にコンパイルエラーになる）。

また、`String`や`Integer`のような標準ライブラリの型がなぜfinalだったり無関係だったりするかは、**問題のコード自体には書かれていない**。JDK自身の宣言（`public final class String`、`public final class Integer extends Number`など）に由来する背景知識であり、`javap`で実機確認できる。

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

---

## 2回目ラウンド：instanceofのコンパイル時チェック（final・クラスvsinterface）

### 問題A：String vs Integer

```java
String s = "hi";
System.out.println(s instanceof Integer);
```

**E.** コンパイルできる。instanceofはどんな型の組み合わせでも実行時にチェックされ、単にfalseが返る。

**F.** コンパイルできない。StringとIntegerはどちらもクラス同士で無関係（`String`は`Object`直下、`Integer`は`Number`経由で`Object`）なため、単一継承の原理からコンパイラが「絶対に一致しえない」と静的に判定してエラーにする。

正解：**F**

### 問題B：Vehicle / Robot / Movable

```java
class Vehicle {}
final class Robot {}
interface Movable {}

public class Main {
    static void check1(Vehicle v) {
        System.out.println(v instanceof Robot);    // ①
    }
    static void check2(Robot r) {
        System.out.println(r instanceof Movable);  // ②
    }
    static void check3(Vehicle v) {
        System.out.println(v instanceof Movable);  // ③
    }
}
```

**A.** ①②③すべてコンパイルエラー。

**B.** ①はコンパイルエラー（`Vehicle`と`Robot`は無関係なクラス同士なので、finalの有無に関わらず両立不可）。②もコンパイルエラー（`Robot`がfinalなので、将来`Movable`を実装するサブクラスが現れる可能性がなく証明できる）。③はコンパイル可能（`Vehicle`はfinalではないので、将来のサブクラスが`Movable`を実装する可能性を否定できず実行時判定になる）。

正解：**B**

### 問題C：Tool / Hammer / Nail / Sharp

```java
class Tool {}
class Hammer extends Tool {}
final class Nail {}
interface Sharp {}

public class Main {
    static void checkA(Tool t) {
        System.out.println(t instanceof Hammer);   // ①
    }
    static void checkB(Tool t) {
        System.out.println(t instanceof Nail);      // ②
    }
    static void checkC(Nail n) {
        System.out.println(n instanceof Sharp);      // ③
    }
    static void checkD(Tool t) {
        System.out.println(t instanceof Sharp);       // ④
    }
}
```

**A.** ①コンパイル可（実行時判定）／②コンパイルエラー／③コンパイルエラー／④コンパイル可（実行時判定）

正解：**A**（①`Hammer`は`Tool`のサブクラスなので両立しうる／②`Tool`と`Nail`は無関係なクラス同士なので常にエラー／③`Nail`はfinalなので`Sharp`を将来も実装しえないと証明できエラー／④`Tool`はfinalではないので実行時判定）

すべてjavac(--release 17)/javaで実機検証済み。

### 実施記録（2回目・2026-08-22）

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| A（String vs Integer） | E | F | 誤り |
| B（Vehicle/Robot/Movable） | A | B | 誤り |
| C（Tool/Hammer/Nail/Sharp） | A | A | 正解 |

### 迷ったポイントの詳細

**問題A（誤答）**：`instanceof`は「どんな型の組み合わせでも常に実行時判定」と誤解していた。正しくは、コンパイラがまず「この2つの型は絶対に両立しえないか」を静的にチェックし、証明できればコンパイル時エラーになる。

誤答後、「そもそもfinalってどこに書いてあるんですか」「StringとIntegerが無関係ってどこに書いてあるんですか」という的確な疑問が出た。答えは両方とも「問題のコード自体には一切書かれておらず、JDK自身の宣言（`public final class String`、`public final class Integer extends Number`）に由来する背景知識」。`javap`で実機確認して裏付けた。

**問題B（誤答）**：「クラス vs クラス」と「クラス vs interface」を同じルールで考えてしまい、③（`Vehicle` vs `Movable`）も①②と同様に無条件でエラーになると判断した。

実際には：
- **クラス vs クラス**（①）：無関係なら**finalの有無に関係なく**常にエラー（単一継承のため）
- **クラス vs interface**（②③）：**finalかどうかで結果が変わる**。finalなクラスは「これ以上サブクラスが生まれない終着点」なので将来もinterfaceを実装しえないと証明できてエラーになるが、non-finalなクラスは将来のサブクラスの可能性を否定できずコンパイルが通る

この2種類のルールを混同しないことが今回の核心的な学び。

**問題C（正解、ただし③で迷いながら回答）**：「ネイルがファイナルで、これ以上...ここはわかんねえけど」と迷いつつも、finalクラスはサブクラス化できない＝将来もinterfaceを実装しえないという結論には正しくたどり着けた。問題Bの誤答を踏まえて、クラスvsinterfaceの判定にfinalが効くという理解が定着してきている。
