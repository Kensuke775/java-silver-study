# 6章 ダウンキャストの危険性 問題集

## 前提知識メモ

| 項目 | 内容 |
|---|---|
| ダウンキャストとは | 親クラス型の変数を、より具体的な子クラス型として扱うための明示的なキャスト（`(Dog) a`） |
| コンパイル時チェック | instanceofと同じルール。静的型同士が継承関係にあれば可、無関係なクラス同士（兄弟クラスなど）なら常にコンパイルエラー（finalの有無は無関係。単一継承のため） |
| 実行時チェック | コンパイルが通っても、実際の**動的型**がキャスト先と一致しなければ`ClassCastException`が発生する |
| 安全な書き方 | キャスト前に`instanceof`でチェックする、またはパターンマッチング`instanceof`（`if (a instanceof Dog d)`）でキャストと変数束縛を同時に行う |

### コンパイルエラーになるか判定する手順（①②③）

`(T) S`（Sはキャストされる式の**宣言された型＝静的型**、Tはキャスト先の型）について、以下の順で判定する。

```
① SとTは継承関係にある？（extends/implements、間接的なものも含む）
   YES → コンパイルOK（実行時に実際の動的型で成功/失敗が決まる）
   NO  → ②へ

② SとTは両方ともクラス？
   YES → 常にコンパイルエラー（finalの有無は無関係。単一継承のため両立不可）
   NO（片方以上がinterface）→ ③へ

③ クラス側はfinal？
   final     → コンパイルエラー（将来もそのinterfaceを実装するサブクラスが現れ得ないため）
   non-final → コンパイルOK（将来のサブクラスが実装する可能性を否定できないため）
   ※両方interfaceなら常にコンパイルOK（interfaceはfinalにできず、複数実装も自由なため）
```

**注意点**：①のSは「その変数が最後に代入された値の型」ではなく、あくまで**宣言時の型（静的型）**。直前に何が代入されたかはコンパイラは見ない。

| S\Tの組み合わせ | ①関連あり | ①無関係 |
|---|---|---|
| class vs class | コンパイルOK（実行時判定） | 常にコンパイルエラー |
| class vs interface | コンパイルOK（実行時判定） | class側final→エラー／non-final→コンパイルOK（実行時判定） |
| interface vs interface | コンパイルOK（実行時判定） | 常にコンパイルOK（実行時判定） |

### アップキャストとダウンキャストの違い

```java
interface Runner { void run(); }
class Task implements Runner { public void run() { ... } }

Task t = new Task();
Runner r1 = t;              // アップキャスト：キャスト演算子省略可、常に成功
Runner r2 = (Runner) t;     // 明示的に書いても同じ意味
```

| | 向き | キャスト演算子 | コンパイル | 実行時 |
|---|---|---|---|---|
| アップキャスト（子→親/interface） | 型を広げる | 省略可能 | 常にOK | 常に成功 |
| ダウンキャスト（親/interface→子） | 型を絞り込む | 必須 | 上記①②③の判定が必要 | 動的型次第で成功/`ClassCastException` |

---

## 問題6-18（1）：動的型とダウンキャストの成否（選択式）

```java
class Animal {}
class Dog extends Animal {}
class Cat extends Animal {}

public class Main {
    public static void main(String[] args) {
        Animal a = new Dog();
        Dog d = (Dog) a;              // ①
        System.out.println("OK1: " + d.getClass().getSimpleName());

        Animal a2 = new Cat();
        Dog d2 = (Dog) a2;            // ②
        System.out.println("OK2: " + d2.getClass().getSimpleName());
    }
}
```

`main`を実行した結果として正しいものを、A〜Dから1つ選んでください。

**A.**
```
OK1: Dog
OK2: Cat
```

**B.**
```
OK1: Dog
（②の行で ClassCastException が発生してプログラムは異常終了する）
```

**C.** コンパイルエラーになる（`Animal`型を`Dog`型にキャストすることはできないため）

**D.**
```
OK1: Dog
OK2: Dog
```

---

## 問題6-18（2）：兄弟クラス同士のダウンキャスト（選択式）

```java
class Animal {}
class Dog extends Animal {}
class Cat extends Animal {}

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat();
        Dog d = (Dog) c;   // ここに注目
        System.out.println(d);
    }
}
```

この`Dog d = (Dog) c;`について、正しい説明をA〜Cから1つ選んでください。

**A.** コンパイルは通る。実行時に`ClassCastException`が発生する。

**B.** コンパイルできない。`Dog`と`Cat`はどちらも`Animal`のサブクラスだが、互いに無関係（兄弟）なクラス同士であり、単一継承のため両立しえないとコンパイラが静的に判定するため。

**C.** 問題なく実行できる。`Cat`も`Animal`のサブクラスなので、`Dog`にもキャストできる。

---

## 応用問題（教材ex18・パターン1）

```java
interface Browser {
    default void browse() {
        System.out.print(" Browsing..");
    }
}
class MobilePhone implements Browser {
    public void call() {
        System.out.print(" Calling..");
    }
    public void browse() {
        System.out.print(" Just scrolling..");
    }
}
class Laptop implements Browser {}

public class Main {
    public static void main(String[] args) {
        Browser br = new MobilePhone();
        br.browse();
        br = new Laptop();
        br.browse();
        MobilePhone mp = (MobilePhone)br;
        mp.call();
    }
}
```

`MobilePhone mp = (MobilePhone)br;`の行について、正しいものを選んでください。

- A. コンパイルエラーになる（`MobilePhone`と`Laptop`は無関係な兄弟クラスだから）
- B. コンパイルは通るが、実行時に`ClassCastException`が発生する
- C. コンパイルは通り、実行時も正常に動作する（`mp.call()`が実行される）
- D. コンパイルエラーになる（`Browser`型の変数を`MobilePhone`型にダウンキャストすること自体ができないから）

**正解：B**。`MobilePhone`と`Laptop`自体は無関係な兄弟クラスだが、判定に使うのは`br`の**宣言型**`Browser`と`MobilePhone`の関係。`MobilePhone implements Browser`なので継承関係あり→コンパイルOK。実行時、`br`の中身は直前に代入された`Laptop`（`MobilePhone`ではない）→`ClassCastException`。

---

## 応用問題（教材ex18・パターン2）

```java
interface Browser {
    default void browse() {
        System.out.print(" Browsing..");
    }
}
class MobilePhone implements Browser {
    public void call() {
        System.out.print(" Calling..");
    }
    public void browse() {
        System.out.print(" Just scrolling..");
    }
}
class Laptop extends MobilePhone {}

public class Main {
    public static void main(String[] args) {
        Browser br = new MobilePhone();
        br.browse();
        Laptop lp = (Laptop)br;
        lp.browse();
    }
}
```

`Laptop lp = (Laptop)br;`の行について、正しいものを選んでください。

- A. コンパイルエラーになる（`Browser`と`Laptop`は無関係だから）
- B. コンパイルは通るが、実行時に`ClassCastException`が発生する
- C. コンパイルは通り、実行時も正常に動作する（`lp.browse()`が実行される）
- D. コンパイルエラーになる（継承チェーンが`MobilePhone`までしか届いておらず、`Laptop`には届いていないから）

**正解：B**。`Laptop extends MobilePhone implements Browser`で`Laptop`は`Browser`の子孫（間接的）→コンパイルOK。実行時、`br`の中身は`MobilePhone`（`Laptop`ではない）→`ClassCastException`。

---

## 応用問題（3階層バージョン）

```java
interface Playable {
    default void play() {
        System.out.print("Playing..");
    }
}
class Instrument implements Playable {
    void tune() {
        System.out.print(" Tuning..");
    }
}
class StringInstrument extends Instrument {
    void pluck() {
        System.out.print(" Plucking..");
    }
}
class Guitar extends StringInstrument {
    void strum() {
        System.out.print(" Strumming..");
    }
}

public class Main {
    public static void main(String[] args) {
        Playable p = new StringInstrument();
        p.play();
        Guitar g = (Guitar)p;
        g.strum();
    }
}
```

`Guitar g = (Guitar)p;`の行について、正しいものを選んでください。

- A. コンパイルエラーになる（`Playable`と`Guitar`は継承チェーンが直接つながっていないから）
- B. コンパイルは通るが、実行時に`ClassCastException`が発生する
- C. コンパイルは通り、実行時も正常に動作する（`g.strum()`が実行される）
- D. コンパイルエラーになる（`p`の実際の中身が`StringInstrument`であり、`Guitar`ではないとコンパイラが検出するから）

**正解：B**。`Guitar extends StringInstrument extends Instrument implements Playable`で3階層辿って`Guitar`は`Playable`の子孫→コンパイルOK。実行時、`p`の中身は`StringInstrument`（`Guitar`ではない）→`ClassCastException`。

---

## 応用問題（コンパイルエラーパターン混合・4問）

選択肢は4問共通：
- ① コンパイルエラーになる
- ② コンパイルは通り、実行時も正常に動作する
- ③ コンパイルは通るが、実行時に`ClassCastException`が発生する

**問1**
```java
class Fruit {}
class Vehicle {}
Fruit f = new Fruit();
Vehicle v = (Vehicle) f;
```

**問2**
```java
final class Coin {}
interface Spinnable {}
Coin c = new Coin();
Spinnable s = (Spinnable) c;
```

**問3**
```java
class Wheel {}
interface Spinnable {}
Wheel w = new Wheel();
Spinnable s = (Spinnable) w;
```

**問4**
```java
interface Chargeable {}
interface Portable {}
Chargeable c = new Chargeable() {};  // 匿名クラスでChargeableだけを実装
Portable p = (Portable) c;
```

**正解**

| 問 | S | T | ①関連 | ②③ | 結果 |
|---|---|---|---|---|---|
| 1 | Fruit | Vehicle | 無関係 | 両方class | **①コンパイルエラー** |
| 2 | Coin(final) | Spinnable | 無関係 | class側final | **①コンパイルエラー** |
| 3 | Wheel(non-final) | Spinnable | 無関係 | class側non-final | コンパイルOK→**③実行時CCE** |
| 4 | Chargeable | Portable | 無関係 | 両方interface | コンパイルOK→**③実行時CCE** |

---

## 最終問題（アップキャスト・ダウンキャスト混在）

```java
interface Flyable {
    default void fly() {
        System.out.print("Flying..");
    }
}
class Bird implements Flyable {
    void chirp() {
        System.out.print(" Chirp..");
    }
}
class Eagle extends Bird {
    void hunt() {
        System.out.print(" Hunting..");
    }
}
class Penguin implements Flyable {}

public class Main {
    public static void main(String[] args) {
        Bird b = new Eagle();
        Flyable f = b;
        Eagle e = (Eagle) f;
        e.hunt();
        System.out.println();

        Flyable f2 = new Penguin();
        Bird b2 = (Bird) f2;
        b2.chirp();
    }
}
```

このコードの実行結果として正しいものを、A〜Dから選んでください。

- A. `Eagle e = (Eagle) f;`の行でコンパイルエラーになる（`Flyable`と`Eagle`は無関係だから）
- B. `Bird b2 = (Bird) f2;`の行でコンパイルエラーになる（`Bird`と`Penguin`は無関係な兄弟クラスだから）
- C. コンパイルは通り、`Hunting..`が出力された後、`Bird b2 = (Bird) f2;`の行で`ClassCastException`が発生する
- D. コンパイルは通り、`Hunting.. Chirp..`が出力されて正常終了する

**正解：C**。`Eagle e = (Eagle) f;`は`Eagle extends Bird implements Flyable`で継承関係あり→コンパイルOK、実際の中身も`Eagle`なので成功（`Hunting..`）。`Bird b2 = (Bird) f2;`は`Bird implements Flyable`で継承関係あり→コンパイルOK、だが実際の中身は`Penguin`（`Bird`とは無関係な兄弟）→`ClassCastException`。

すべてjavac(--release 17)/javaで実機検証済み。

---

## 解答

**問題1**：正解は**B**。①は`a`の動的型が`Dog`なので成功（`OK1: Dog`）。②は`a2`の動的型が`Cat`なのに`Dog`へキャストしようとするため、コンパイルは通る（`Animal`と`Dog`は静的に継承関係があるため）ものの、実行時に`ClassCastException`が発生する。

**問題2**：正解は**B**。`Dog`と`Cat`は共に`Animal`のサブクラスだが、`Dog`と`Cat`**同士**は継承関係にない（兄弟クラス）。無関係なクラス同士のキャストは、finalの有無に関わらず単一継承の原理により常にコンパイルエラーになる（6-17のinstanceofコンパイル時チェックと同じルール）。

すべてjavac(--release 17)/javaで実機検証済み。

---

## 実施記録

### 1回目（2026-08-22）

| 問題 | 回答 | 正解 | 判定 |
|---|---|---|---|
| 1 | B | B | 正解 |
| 2 | A | B | 誤り |

### 迷ったポイントの詳細

**問題2（誤答）**：`Dog`と`Cat`がどちらも`Animal`のサブクラスであることから、「継承関係がある＝コンパイルは通ってCCEになる」と判断してしまった。

正しくは、判定に使うのは**キャストの両辺（`Dog`と`Cat`）同士が継承関係にあるかどうか**であり、共通の親（`Animal`）を持っているかどうかではない。`Dog`と`Cat`は互いに無関係な兄弟クラスなので、直近のinstanceofラウンドで整理した「クラス vs クラスが無関係なら、finalの有無に関わらず常にコンパイルエラー」というルールがそのまま当てはまり、実行時まで到達せずコンパイルの時点で弾かれる。
