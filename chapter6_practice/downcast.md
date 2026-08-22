# 6章 ダウンキャストの危険性 問題集

## 前提知識メモ

| 項目 | 内容 |
|---|---|
| ダウンキャストとは | 親クラス型の変数を、より具体的な子クラス型として扱うための明示的なキャスト（`(Dog) a`） |
| コンパイル時チェック | instanceofと同じルール。静的型同士が継承関係にあれば可、無関係なクラス同士（兄弟クラスなど）なら常にコンパイルエラー（finalの有無は無関係。単一継承のため） |
| 実行時チェック | コンパイルが通っても、実際の**動的型**がキャスト先と一致しなければ`ClassCastException`が発生する |
| 安全な書き方 | キャスト前に`instanceof`でチェックする、またはパターンマッチング`instanceof`（`if (a instanceof Dog d)`）でキャストと変数束縛を同時に行う |

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
