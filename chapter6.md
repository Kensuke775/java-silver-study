# 第6章 継承とインタフェース

対応する教科書ページ: 6章（継承とインタフェース, p.267〜342）
学習環境: macOS（Apple Silicon）／ JDK 17（Homebrewの`openjdk@17`、17.0.20）
サンプル: `~/Study/java-study/sample/chap6/`

---

## 1. クラスの継承は1つだけ、インタフェースは複数実装できる

```java
class Sub extends A, B {}              // ✗ コンパイルエラー：extendsは1つしか書けない
class Sub extends A implements X, Y {} // ○ extendsは1つ、implementsは複数OK
```

- Javaのクラスは**単一継承**。複数のクラスを継承できてしまうと、2つの親に同名メンバがあった場合にどちらを継承すべきか曖昧になる（**ダイヤモンド問題**）ため禁止されている
- インタフェースは複数`implements`できる
- すべてのクラスは、`extends`を書かなくても最終的に`java.lang.Object`を暗黙的に継承している（継承チェーンの終点）

---

## 2. `@Override`は「オーバーライドを発生させる」ものではない（chap6/2 実演）

```java
class Super {
    X method(int a, String b) { return new X(); }
}
class Sub3 extends Super {
    @Override
    void method(int a, String b) {}       // ✗ 戻り値がXとの共変関係にないためエラー
    X method(String b, int a) {}          // オーバーライドではなく無関係な別メソッド（引数順が違う）
}
```

オーバーライドが成立するかどうかは、**メソッドのシグネチャ（名前・引数リスト・戻り値の型・アクセス修飾子の広さ）が一致しているかだけ**で機械的に決まり、`@Override`の有無とは無関係。

`@Override`の本当の役割は**コンパイラへの確認依頼**：「これはオーバーライドのつもりです、成立していなければエラーにしてください」。

| | `@Override`あり | `@Override`なし |
|---|---|---|
| シグネチャが正しく一致 | オーバーライド成立 | オーバーライド成立（同じ） |
| シグネチャがズレている（タイポ等） | **コンパイルエラーで即発覚** | エラーにならず、気づかず別メソッドとして追加される |

`@Override`を付けずにミスをすると、意図した親メソッドの上書きに失敗しても、ただの無関係な新規メソッドとして静かにコンパイルが通ってしまう。これが実務で必ず`@Override`を付けるべき理由。

---

## 3. `main`はクラス自体がpublicでなくても実行できる（chap6/3 実演）

```java
class Parent {
    public void methodA(int i) {}
}
class Child extends Parent {
    @Override
    public void methodA(int i) {}
    public static void main(String[] args) {
        new Child().methodA(1);
    }
}
```

`java ClassName`で実行する条件は「**そのクラス自体がpublicであること**」ではなく「**`main`メソッドが`public static void main(String[] args)`であること**」だけ。クラスの可視性とは無関係。

- 1つの`.java`ファイルには`public`なトップレベルクラスは1つまで、かつファイル名と一致する必要がある
- 同じファイル内の他のクラスはpublicを諦めれば（デフォルトアクセスのままで）、この制約と無関係に普通に実行できる

実行コマンドの注意点：`java Child`（クラス名が後）。`Child java`のように語順を逆にすると、シェルは「`Child`という名前のコマンドを実行しようとして」失敗する。

---

## 4. フィールドの隠蔽 vs インスタンスメソッドのオーバーライド（chap6/4 実演）

```java
class Super {
    int x = 1;
    void instMethod() { System.out.println("Super#instMethod()"); }
}
class Sub extends Super {
    double x = 1.0;
    void instMethod() { System.out.println("Sub#instMethod()"); }
}
Super obj = new Sub();   // アップキャスト：変数の型はSuper、中身の実体はSub
```

`obj`は「変数の型（宣言型）はSuper、実際に生成されたオブジェクトの中身はSub」という状態（**アップキャスト**）。`Sub`は`Super`の一種（IS-A）なので許される。

| アクセス対象 | 何によって決まるか | 結果 |
|---|---|---|
| `obj.x`（フィールド） | **変数の宣言型（Super）** ＝静的束縛 | `Super`の`x`（`1`）が使われる |
| `obj.instMethod()`（インスタンスメソッド） | **実際のオブジェクトの中身（Sub）** ＝動的束縛 | `Sub#instMethod()`が呼ばれる |

この非対称性（フィールドは静的束縛、インスタンスメソッドは動的束縛）が、フィールドに「オーバーライド」ではなく「隠蔽（シャドーイング）」という別の言葉が使われる理由。

### 実務での位置づけ
- **メソッドの動的束縛（ポリモーフィズム）**：`List<String> list = new ArrayList<>();`のように、実務で日常的に使われる。「インタフェース（親の型）でプログラミングする」設計の基本
- **フィールドの隠蔽**：親子で同名フィールドを持たせること自体、読み手を混乱させるアンチパターン。試験で問われるのはトラップ回避のため

---

## 5. 多段階継承のコンストラクタチェーン（chap6/6 実演）

```java
class A { A() { System.out.println("class A"); } }
class B extends A { B() { System.out.println("class B"); } }
class C extends B { C() { System.out.println("class C"); } }
```

`new C();`とすると、`class A` → `class B` → `class C`の順に出力される。`C()`の先頭には暗黙の`super()`（＝`B()`呼び出し）が、`B()`の先頭にも暗黙の`super()`（＝`A()`呼び出し）が自動的に挿入されているため。

**重要な誤解ポイント**：これは「AとBのオブジェクトが別々に3つ作られる」わけではない。ヒープ上に生成されるのは**Cのインスタンス1つだけ**。その1つのオブジェクトの中に、A相当・B相当・C相当の状態が層状に含まれており、初期化処理が親から子の順に実行されているだけ。`C`は構造的にA・Bの部分を含むため、A・Bの初期化を飛ばして`C`だけを作ることは構造上不可能。

### `println(obj)`で出るデフォルトの文字列（`C@6b95977`など）
```
getClass().getName() + "@" + Integer.toHexString(hashCode())
```
`toString()`をオーバーライドしていないクラスは、`Object`のデフォルト実装（クラス名＋ハッシュコードの16進数）が使われる。バグではなく仕様通りの挙動。読みやすくしたい場合は`toString()`を独自にオーバーライドする。

---

## 6. `super(引数)`によるスーパークラスコンストラクタへの委譲（chap6/7 実演）

```java
class Item {
    private int id;
    Item(int id) { this.id = id; }   // 引数ありのみ。引数なし版は存在しない
}
class Clothes extends Item {
    private String brand;
    Clothes(int id, String brand) {
        super(id);          // 明示的に呼ばないとコンパイルエラー
        this.brand = brand;
    }
}
```

コンストラクタを1つでも明示的に定義すると、コンパイラは自動生成のデフォルトコンストラクタ（引数なし）を用意しなくなる。`Item`には引数ありコンストラクタしかないため、`Clothes`側で何も書かないと、暗黙に挿入されようとする`super();`（引数なし）が呼び出し先を見つけられずコンパイルエラーになる。**スーパークラスに引数なしコンストラクタが無い場合は、必ずサブクラス側で`super(...)`を明示する必要がある。**

### `super(...)`と`this(...)`の対比（5章の復習と接続）
| 呼び出し | 呼び出し先 | 目的 |
|---|---|---|
| `super(id);` | 親クラスのコンストラクタ | 親の初期化処理に値を渡して委譲 |
| `this(0, "T-shirt");` | 同じクラス内の別のコンストラクタ | 自クラス内のより多くの処理をする別コンストラクタに委譲 |

共通ルール：どちらも**コンストラクタの先頭文でなければならない**。**`this(...)`と`super(...)`は同じコンストラクタ内で同時には使えない**（先頭文は1つだけ）。

---

## 7. 教科書問題5-7の再検証：`this.`忘れによるシャドーイング事故

```java
public Item(int id, String name) {
    id = id;      // this. が無い！ パラメータへの自己代入で終わる
    name = name;  // 同上
}
```
`this(2, "Book")`自体は正しく`Item(int, String)`を呼び出せている。しかし**呼ばれた先のコンストラクタ本体で`this.`を書き忘れている**ため、パラメータの`id`/`name`とインスタンスフィールドの`id`/`name`が同名でシャドーイングし、`id = id;`は「パラメータへの自己代入」にしかならず、インスタンスフィールドには一切値が届かない。

結果、`new Item(1, "Apple")`で呼んでも`this(2, "Book")`経由で呼んでも、フィールドはデフォルト値のまま（`id=0, name=null`）。出力は`0:null 0:null `（選択肢C）。**「コンストラクタが正しく呼ばれているか」と「呼ばれた先で正しくフィールドに代入されているか」は別問題**、という点が今回の教訓。

---

## 8. パッケージをまたいだ継承と`protected`アクセスの実験（chap6/1 実演）

`Item`（`package a`）を、別パッケージ（デフォルトパッケージ）の`Sub`から継承してprotectedフィールドにアクセスできるか、実際に動かして検証した。

```java
// a/Item.java
package a;
public class Item {                 // ★①クラス自体もpublicが必要
    protected int id = 100;
}

// Sub.java（デフォルトパッケージ）
import a.Item;                       // ★②別パッケージのクラスをimport

public class Sub extends Item {
    public void subPrintItem(){
        System.out.println("test-protected" + id);   // 継承経由の無修飾アクセスはOK
    }
    public static void main(String[] args) {
        Sub s = new Sub();
        s.subPrintItem();
    }
}
```
実行結果：`test-protected100`

### ハマったポイント
1. `protected`をフィールドに付けるだけでは不十分。**クラス自体（Item）もpublicでないと、別パッケージから型自体が見えない**
2. `Item i = new Item(); i.subPrintItem();`のように**親クラス型の変数からサブクラス限定のメソッドは呼べない**（継承は「子が親を受け継ぐ」一方向で、親は子の存在を知らない）
3. **名前付きパッケージから、デフォルト（無名）パッケージのクラスはimportできない**。そのため`Sub`の呼び出しテストは`Sub`自身の`main`から行う必要がある
4. `javac -d .`は**コンパイル後の`.class`の置き場所を`package`宣言に合わせて自動生成するだけ**で、元の`.java`ソース自体は動かない。ソースをpackage構造に合わせて配置するのは手動作業

### アクセス修飾子の可視性まとめ（再掲）
| 修飾子 | 同一クラス | 同一パッケージ | 別パッケージのサブクラス | 別パッケージの無関係クラス |
|---|---|---|---|---|
| `private` | ○ | ✗ | ✗ | ✗ |
| デフォルト | ○ | ○ | ✗ | ✗ |
| `protected` | ○ | ○ | ○（継承経由のみ） | ✗ |
| `public` | ○ | ○ | ○ | ○ |

---

## 9. レコードクラス（`record`）（chap6/8 実演、教科書p.294〜302）

```java
public record Item(int id, String name) {}
```

### 何のためにあるか
Java 16で正式導入。**イミュータブル（不変）なデータの入れ物を書くときの定型作業（ボイラープレートコード）を消すため**。`record`が無い場合、private final フィールド＋コンストラクタ＋getter＋`equals()`/`hashCode()`/`toString()`のオーバーライドをすべて手で書く必要があったが、`record`宣言1行でこれらが自動生成される。

### JavaScriptのオブジェクトとの違い
「データをまとめて持ち運ぶ」という発想は近いが、決定的に異なる点：

| 観点 | JSのオブジェクト | Javaのrecord |
|---|---|---|
| 型 | 動的型付け | 静的型付け（コンポーネントの型が固定） |
| 可変性 | 自由に書き換え可能 | **イミュータブル**。生成後は変更不可 |
| フィールド取得 | `item.id`（直接アクセス） | `item.id()`（自動生成された**メソッド**呼び出し） |

### 自動生成される内容（図6-13）
```java
public final class Item extends Record {      // java.lang.Recordを継承したfinalクラス
    private final int id;                       // private finalなコンポーネントフィールド
    private final String name;
    public Item(int id, String name) {           // 標準コンストラクタ
        this.id = id;
        this.name = name;
    }
    public int id() { return id; }                // publicなアクセサメソッド
    public String name() { return name; }
    public boolean equals(Object obj) { ... }      // equals/hashCode/toStringも自動生成
    public int hashCode() { ... }
    public String toString() { ... }
}
```

- **finalクラス**：`record`は暗黙的にfinal。継承不可（`java.lang.Record`を継承しているため他クラスを継承できない。ただしインタフェースの実装は可）
- **イミュータブル**：setterに相当するメソッドは生成されない
- コンポーネント名に使えない名前：`clone`, `finalize`, `getClass`, `hashCode`, `notify`, `notifyAll`, `toString`, `wait`（Objectの引数なしメソッドと衝突するため）

### `final`ルールとの整合性（重要な確認ポイント）
「`record`では代入しなくてよい特別ルールがある」ように見えるが、実際にはルール自体は変わっていない。標準コンストラクタが自動的に`this.id = id;`のような代入を行っているだけ。

**コンパクトコンストラクタ**（引数リストを省略し、バリデーション等の処理のみ書く書き方）ではさらに顕著：
```java
record Clothes(int id, String brand) {
    Clothes {                              // コンパクトコンストラクタ
        id = id > 0 ? id : 0;              // 仮引数idを書き換え（バリデーション相当）
        // brand には何もしていない
    }
}
```
- コンパクトコンストラクタでは**`this.`を使った直接代入は禁止**（書けない）
- 処理が終わった後、**コンパイラが自動で全コンポーネント分の`this.フィールド = 値`を末尾に追加**する（この時`id`は書き換え済みの値、`brand`は元のまま）
- コンパクトコンストラクタはrecord専用の書き方で、通常のクラスでは使えない

つまり「final は必ず代入されなければならない」という原則自体はrecordでも変わらず、**コンパイラが代わりに代入処理を保証してくれている**だけ。通常のクラスでこの自動化は起きないので、`final`フィールドは引き続き自分で明示的に代入する必要がある（5章の`final String title;`未初期化エラーと直結する話）。

---

## 10. Objectクラスのメソッド（equals/hashCode/toString）のオーバーライドの意味（教科書p.293コラム）

コラムの「管理対象となるオブジェクトのクラス定義でequals()メソッドとあわせてオーバーライドを行います」という一文の**主語はプログラマ**。「hashCode()というメソッド自体が何かをオーバーライドする」という意味ではなく、「**自分が定義するクラス（例：Item）の中で、Objectから継承したデフォルトのequals()/hashCode()を、独自の内容に書き換える**」という意味。

### なぜ書き換えが必要か
`Object`のデフォルトの`equals()`は`==`と同じ**参照比較**（メモリ上の同一オブジェクトかどうか）しか行わない。
```java
Item a = new Item(100, "T-shirt");
Item b = new Item(100, "T-shirt");
a.equals(b);  // デフォルトのままだと false（別オブジェクトだから）
```
中身のデータが同じでも「別々に`new`した別オブジェクト」なのでfalseになる。「idとnameが同じなら等しいとみなしたい」なら、`equals()`を独自ロジックにオーバーライドする必要がある。

`java.util.HashMap`/`HashSet`は「`hashCode()`でおおまかな置き場所を絞り込み、`equals()`で最終判定する」という仕組みで動くため、この2つを正しくオーバーライドしていないと、データの中身が同じでも別々のキーとして扱われてしまう。**`record`がequals/hashCode/toStringを自動生成してくれるのは、まさにこの手作業を省略するため。**
