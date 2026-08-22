# 6章 オーバーロード解決順序 問題集

## 前提知識メモ

- オーバーロードかオーバーライドかを決めるのは名前＋引数リストだけ。戻り値は一切関与しない
- Javaのオーバーロード解決には優先順位（フェーズ）がある
  1. **フェーズ1**：ボクシング・可変長引数(varargs)を使わない、そのまま or ワイドニング変換だけで一致するものを探す
  2. **フェーズ2**：ボクシング／アンボクシングを許可して一致するものを探す
  3. **フェーズ3**：可変長引数(varargs)まで許可して一致するものを探す
  - 早い段階で候補が見つかれば、それ以降のフェーズは見に行かない
- 複数の候補が同じフェーズで成立する場合、**より具体的な型**（サブタイプ）が優先される（例：`Integer`と`Object`なら`Integer`が勝つ）
- `int`は直接`Object`と互換なわけではなく、`int → Integer（オートボクシング） → Object（ワイドニング参照変換）`という2段階を経由して初めて互換になる
- `null`は全ての参照型に代入可能なため、同格の複数の参照型オーバーロードが並ぶと**曖昧（ambiguous）でコンパイルエラー**になる。優先順位で解決できない
- `super.method(...)`の呼び出しも、通常の呼び出しと同じオーバーロード解決ルールに従う。**サブクラス側だけに存在するオーバーロード（親クラスにない引数の組み合わせ）は`super`経由では絶対に呼べない**
- サブクラスは、親の特定のオーバーロードをオーバーライドしつつ、同時に親にはない新しいオーバーロードを追加することもできる（オーバーライドとオーバーロードの追加は共存可能）

---

## 問題1：ワイドニング・ボクシング・varargsの優先順位

**パート1**
```java
public class Main {
    static void show(long x) { System.out.println("long"); }
    static void show(Integer x) { System.out.println("Integer"); }
    static void show(Object x) { System.out.println("Object"); }
    static void show(int... x) { System.out.println("varargs"); }

    public static void main(String[] args) {
        show(5);
    }
}
```
`show(5);`はどのオーバーロードが呼ばれますか？

**パート2**
```java
public class Main {
    static void show(Integer x) { System.out.println("Integer"); }
    static void show(Object x) { System.out.println("Object"); }
    static void show(int... x) { System.out.println("varargs"); }

    public static void main(String[] args) {
        show(5);
    }
}
```
パート1から`long`版を削除しました。`show(5);`はどのオーバーロードが呼ばれますか？

**パート3**
```java
public class Main {
    static void show(String s) { System.out.println("String"); }
    static void show(StringBuilder sb) { System.out.println("StringBuilder"); }

    public static void main(String[] args) {
        show(null);
    }
}
```
`show(null);`はコンパイルできますか？できない場合、理由も答えてください。

**パート4（参考）**
パート2からさらに`Integer`版も削除し、`Object`と`varargs`だけにした場合、`show(5);`はどちらが呼ばれますか？

---

## 問題2：super × オーバーロード・オーバーライドの共存

```java
class Base {
    void show(int x) { System.out.println("Base.show(int) " + x); }
    void show(String s) { System.out.println("Base.show(String) " + s); }
}

class Sub extends Base {
    @Override
    void show(int x) { System.out.println("Sub.show(int) " + x); }

    void show(double d) { System.out.println("Sub.show(double) " + d); }

    void test() {
        super.show(5);      // ①
        super.show("hi");   // ②
        show(5);             // ③
        show(3.14);           // ④
    }
}

public class Main {
    public static void main(String[] args) {
        new Sub().test();
    }
}
```

①〜④の出力を順番通りに答えてください。

**追加問題**：もし`test()`の中に`super.show(3.14);`という行を追加したら、コンパイルは通りますか？ 通らない場合、理由も答えてください。

---

## 解答

**問題1**
- パート1：`long`（フェーズ1でワイドニングだけで一致する候補が見つかるため、それ以上のフェーズは見ない）
- パート2：`Integer`（フェーズ2でIntegerとObjectどちらも候補になるが、より具体的なIntegerが優先される）
- パート3：コンパイルできない（`show`の参照はあいまい。`String`と`StringBuilder`はどちらも`null`と互換で、優先順位をつけられない）
- パート4：`Object`（`int→Integer→Object`の2段階変換で唯一の候補になる）

**問題2**
```
Base.show(int) 5
Base.show(String) hi
Sub.show(int) 5
Sub.show(double) 3.14
```
`super.show(...)`は親クラス自身が持つオーバーロードのみを対象にオーバーロード解決される（オーバーライドされたSubの実装は無視して、Baseの実装を直接呼ぶ）。`show(...)`（superなし）は通常通り、実体であるSubのメンバーから解決される。

**追加問題**：コンパイルできない。
```
エラー: showに適切なメソッドが見つかりません(double)
  メソッド Base.show(int)は使用できません(精度が失われる可能性があるdoubleからintへの変換)
  メソッド Base.show(String)は使用できません(doubleをStringに変換できません)
```
`show(double)`は`Sub`だけが持つオーバーロードであり、`Base`には存在しない。`super`は必ず親クラス自身のメンバーしか見ないため、サブクラス限定のオーバーロードには絶対に到達できない。

すべてjavac/javaで実機検証済み。
