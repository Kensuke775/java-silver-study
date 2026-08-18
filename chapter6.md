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

---

## 11. recordが実際どう役立つか：equals/hashCode/toStringの実利

### `toString()`：デバッグ・ログ出力がそのまま読める
```java
// 通常のクラス（toStringオーバーライドなし）
System.out.println(item);  // → Item@6b95977（何のデータか分からない）

// record
System.out.println(item);  // → Item[id=100, name=T-shirt]（中身が一目で分かる）
```

### `equals()`：「中身が同じなら同じもの」として比較できる
```java
Item a = new Item(100, "T-shirt");
Item b = new Item(100, "T-shirt");
a.equals(b);  // 通常のクラス（equals未実装）: false／record: true
```
別々に生成したオブジェクトでも、データが同じなら同じとみなしたい場面（API取得データとDB取得データの比較など）で正しく動く。

### `hashCode()`：`HashSet`の重複排除・`HashMap`のキー検索が正しく機能する
```java
Set<Item> items = new HashSet<>();
items.add(new Item(100, "T-shirt"));
items.add(new Item(100, "T-shirt"));
items.size();  // 通常のクラス: 2（別オブジェクト扱いで重複排除されない）／record: 1（自動的に重複排除）
```
`HashSet`/`HashMap`は内部で`hashCode()`→`equals()`の順に使って同一性を判定するため、これが正しく実装されていないと「同じデータなのに別キー扱いされる」バグが起きる。`record`ならこれが最初から正しく動く。

---

## 12. イミュータブルの本当の価値：「効率」ではなく「正しさ」

「イミュータブル＝Map/Setの計算効率を上げるため」という理解は不正確。`HashMap`/`HashSet`の計算量（O(1)など）はキーがミュータブルでも変わらない。イミュータブルが解決しているのは**速度ではなく正しさ（correctness）**。

### ミュータブルなキーで起きる事故
```java
Set<Point> visited = new HashSet<>();
Point p = new Point(1, 2);
visited.add(p);

p.x = 99;                // あとから中身を書き換えてしまった

visited.contains(p);     // false になる！ 追加したはずなのに見つからない
```
`HashSet`は追加時点の`hashCode()`をもとに格納場所を決めている。後から値を変えると格納時と検索時のハッシュ値がズレ、「コレクションの中で迷子になる」。`record`はフィールドが`final`なので、このバグが構造的に起こり得ない。

### イミュータブルの一般的なメリット（Map/Set限定ではない）
| メリット | 内容 |
|---|---|
| スレッドセーフ | 状態が変わらないので複数スレッドから同時に読んでもデータ競合が起きない |
| 予測しやすさ | 一度作ったオブジェクトは誰に渡そうと変わらないと保証される |
| 安全にキャッシュ・共有できる | `Integer`の-128〜127キャッシュ（5章）も同じ発想 |
| Map/Setのキーとして安全 | 上記の通り |

`String`や`java.time.LocalDate`が最初からイミュータブルなのも同じ理由。「Map/Setの効率化」より「**マルチスレッドや複雑なプログラムでも安全に扱えるデータを作るため**」がイミュータブル設計の本来の目的。

---

## 13. アクセサ（accessor）とは何か、そのオーバーライドの正体（chap6/10 実演）

**アクセサ＝フィールドの値を取り出すためだけのメソッド**（getter）。recordはコンポーネントごとに、コンパイラが自動でこの形のメソッドを生成する。

```java
public record Sample(int x, int y) {}
// 裏で自動生成されるアクセサ
public int x() { return x; }
public int y() { return y; }
```
`getX()`ではなく**コンポーネント名そのまま（`x()`）**という命名がrecord特有。

### オーバーライドの対象が変わっただけで、仕組みは通常の継承と同じ
| これまで（継承） | 今回（record） |
|---|---|
| 手書きした**親クラス**のメソッドを書き換える | **コンパイラが自動生成したアクセサ**を書き換える |

`public int x() { return x; }`を本体に明示的に書くと、コンパイラが裏で用意していた暗黙のアクセサを上書き（オーバーライド）することになる。

### アクセサとして成立するための条件：仮引数なし＋戻り値の型が一致
```java
@Override public int x() { return x; }              // ○：仮引数なし、戻り値int一致 → アクセサのオーバーライド成立
@Override double y(double z) { return 1.0; }         // ✗：仮引数z あり → アクセサ不成立、無関係な別メソッド扱い
```
仮引数を1つでも持たせると、コンポーネント`y`本来のアクセサ（`int y()`）とシグネチャが一致しなくなり、「アクセサの上書き」として認識されない（`@Override`を外せば、ただの新規メソッドとしてはコンパイルが通る）。

**仮引数（parameter）と実引数（argument）の対比**：仮引数はメソッド**定義側**の「空の受け皿」（例：`double z`）、実引数は呼び出し側が実際に渡す**具体的な値**（例：`s.y(5.0)`の`5.0`）。仮引数が無いアクセサは、呼び出す側も`s.x()`のように何も渡さず空の括弧で呼ぶだけになる。

### 注意：`trim()`は自動生成メンバーではない
`toString()`/`equals()`/`hashCode()`/アクセサは**recordが自動生成するメンバー**でオーバーライド対象になるが、`brand.trim()`のような処理は**`String`クラスの通常のメソッド**で、コンストラクタの中身で値を加工するために使っているだけ。両者は別軸の話（オーバーライド対象 vs コンストラクタ内の処理）。

---

## 14. コンパクトコンストラクタでは`this.フィールド = 値`が禁止（実機で検証、chap6/9実演）

```java
record Clothes(int id, String brand) {
    Clothes {
        id = id > 0 ? id : 0;
        this.brand = brand.trim();   // ← コンパイルエラー
    }
}
```
実際にコンパイルすると：
```
Test.java:21: エラー: final変数brandに値を代入することはできません
        this.brand = brand.trim();
```
コンパクトコンストラクタでは、コンポーネントフィールドへの`this.`を使った直接代入ができない。値を書き換えたいときは、仮引数自体（`id`, `brand`）を上書きする（`brand = brand.trim();`のように`this.`を付けない）。処理が終わった後、コンパイラが自動で全コンポーネント分の`this.フィールド = 値`を末尾に追加してくれる。

---

## 15. recordの標準コンストラクタは、record自体と同じかそれ以上に公開されていないといけない（実機で検証）

```java
public record Sample(int x, int y) {
    Sample {           // 修飾子なし＝デフォルトアクセス
        ...
    }
}
```
実際にコンパイルすると：
```
Sample.java:8: エラー: レコードSampleに無効な標準コンストラクタがあります
  ((public)より強いアクセス権限を割り当てようとしました)
```
`public record Sample`に対し、標準（正規）コンストラクタがデフォルトアクセス（publicより狭い）のままだとエラーになる。修正は`public Sample { ... }`のように明示する。

### 通常のクラスとの違い
```java
public class Item {
    private Item() {}                       // クラスはpublicでもコンストラクタはprivateでOK
    public static Item create() { return new Item(); }
}
```
通常のクラスでは、クラス自体の公開範囲とコンストラクタの公開範囲は**独立**しており、Singletonパターンや静的ファクトリメソッドのように意図的にコンストラクタを狭くすることが可能。

recordだけこの制約があるのは、recordが「ヘッダーのコンポーネントがそのままオブジェクトの中身」という**透明性（transparent data carrier）**を前提にした型だから、という理解（※この設計意図についての明示的な教科書記述は未確認、言語仕様からの推測）。標準コンストラクタが型より狭いと、「型は公開されているのに`new`で直接生成する手段が無い」という矛盾が起きるため、型の公開度とその基本的な生成手段の公開度を一致させる強制ルールになっている。

---

## 16. 三項演算子の条件に`int`は使えない（JavaScriptとの対比）

```java
x = x ? x : 0;   // ✗ コンパイルエラー：不適合な型: intをbooleanに変換できません
```
JavaScriptでは`x ? x : 0`は**truthy/falsy判定**（`0`, `null`, `undefined`, `NaN`, `""`などがfalsy）で動作するが、Javaには**この暗黙変換が一切ない**。`if`/`while`/三項演算子の条件部分は必ず`boolean`型の式でなければならない。

```java
x = (x != 0) ? x : 0;   // ○：明示的にboolean式にする
```

これは意図的な設計判断。C言語で`if (x = 5)`（代入）と`if (x == 5)`（比較）のタイプミスがint↔boolean変換の緩さのせいでバグとして紛れ込んでいた反省から、Javaは`boolean`と`int`を完全に別の型として扱い、暗黙変換を禁止している。

---

## 17. `main`はサブクラス側にも書けるが、単一エントリーポイントが望ましい

```java
class Clothes extends Item {
    public static void main(String[] args) { ... }  // 文法上は合法
}
```
`main`は特別な構文ではなく「`public static void main(String[] args)`というシグネチャを持つ、ただのstaticメソッド」。JVMは`java クラス名`で指定されたクラスにこのシグネチャがあるかを探すだけなので、継承関係とは無関係にどのクラスにも書ける。

ただし`java Main`と`java Clothes`は別々の起動コマンドであり、**同時に並行実行されるわけではない**（別々の独立したプロセス）。複数のクラスに`main`を分散させる本当のリスクは並行実行ではなく、**「このプログラムはどこから始まるのか」が読み手にとって曖昧になる**こと。起動口を1つに絞ることで「そこから上から下へ一方向にデータが流れる」設計になり、コードを追いやすくなる（**単一エントリーポイント**の原則）。

---

## 18. 抽象クラス・抽象メソッドの目的（chap6/12実演）

```java
abstract class Transport {
    private int speed;
    public Transport(int speed) { this.speed = speed; }
    public int getSpeed() { return speed; }
    public abstract void move();
}
class Airplane extends Transport {
    public Airplane(int speed) { super(speed); }
    @Override public void move() { System.out.println("Airplane: flying at " + getSpeed() + "km/h"); }
}
```

`abstract`は「共通の型として扱いたいが、単体では存在させたくない・存在させる意味がない概念」を表現する仕組み。

- **`abstract class`**：`new Transport(300)`のように単体でインスタンス化できなくする。「乗り物」という概念自体は実在せず、実在するのは常に「飛行機」「船」のような具体的な乗り物だけ、という設計を強制する
- **`abstract void move();`**：本体が書けない（サブクラスごとに移動方法が全く違うため）。サブクラス側に実装を強制し、実装し忘れをコンパイルエラーで検出できる（`Airplane is not abstract and does not override abstract method move()`）

`abstract class Car extends Transport`のように、`move()`を実装しないまま更に別の抽象クラスを挟むことも可能（`Car`自身もabstractのままになる）。

ポリモーフィズムとの繋がり：`Transport t = new Airplane(900); t.move();`のように、`Transport`型であれば中身が何であろうと`move()`が必ず呼べることをコンパイラが保証してくれる。

---

## 19. インタフェースの定数・メソッドの暗黙修飾子（chap6/13実演、javapで実証）

```java
public interface Test {
    int EXCELLENT = 100;
    public int VERY_GOOD = 90;
    static int GOOD = 80;
    final int AVERAGE = 70;
//  private int BELOW_AVERAGE = 60;     // privateは指定できない
//  public static final int VERY_POOR;  // 定数の初期化は必須
    void foo();
    public int bar();
    abstract boolean baz();
//  protected double qux();             // protectedは指定できない
//  final String quux();                // finalは指定できない
}
```

- フィールド：書いても書かなくても常に`public static final`。`private`は指定不可、初期化は必須
- 本体の無いメソッド：常に`public abstract`。`protected`・`final`は指定不可
- ただし`default`/`static`メソッド（本体を持つ）は`abstract`にはならず`public`のみ付与される。`private`メソッドだけは逆に`public`が付かない例外

`javap`で実際に確認すると：
```
public interface Test {
  public static final int EXCELLENT;
  ...
  public abstract void foo();
  ...
}
```
`javap -v`（verbose）を使うと、`ConstantValue: int 100`のように**定数の実際の値まで**確認できる（デフォルトの`javap`はシグネチャのみで値は表示しない）。

### `javap`とは
JDK標準の**クラスファイル逆アセンブラ**。ソース(`.java`)が無くても、コンパイル済み`.class`の構造（フィールド・メソッド・コンストラクタ）を確認できる。
```bash
javap クラス名          # シグネチャのみ
javap -v クラス名        # 値・定数プールまで含めて詳細表示
javap -p クラス名        # privateメンバーも表示
javap -c クラス名        # バイトコードレベルで表示
```

### `Test.EXCELLENT`という書き方との違い（要注意）
```java
System.out.println(Test.EXCELLENT);  // ← .javaファイルの中で書く構文（インタフェース名.定数名でアクセス）
```
これは**Javaのソースコード内の構文**であり、ターミナルコマンドの`javap`とは無関係。`javap Test.EXCELLENT`のようにコマンドの引数として渡すことはできない（`javap`はクラス名だけを受け取る）。

---

## 20. 同一パッケージなら`public`が無くても普通に使える（chap6/13実演）

```java
// Run.java
interface Test { int EXCELLENT = 100; }   // publicなし＝デフォルトアクセス
public class Run { public static void main(String[] a) { System.out.println(Test.EXCELLENT); } }

// Main.java（別ファイル）
class Sample implements Test { ... }       // Testをそのまま使えている
public class Main { ... }
```
`Run.java`と`Main.java`はどちらも`package`宣言が無い＝**同じデフォルトパッケージ**に属する。デフォルトアクセスは同一パッケージ内なら誰でも使えるので、別ファイルでも`import`なしで普通に`implements`・参照できる。「1ファイルにつきpublicなトップレベル型は1つまで」というファイル単位の制約と、「デフォルトアクセスは同一パッケージ内ならOK」というアクセス修飾子の制約は別ルール。

同様に、chap6/19の`Cube extends Square`では、`Square`のデフォルトアクセスフィールド`side`が、同じ`com.a`パッケージにいる`Cube`から何も書かなくてもそのまま継承・使用できていた（`private`以外なら継承先で意識不要）。

---

## 21. `toString()`/`getClass()`はrecord専用ではなく全クラス共通（chap6/19実演）

```java
public abstract sealed class Shape permits Circle, Triangle, Square {
    public abstract double calcArea();
    @Override
    public String toString() {
        return getClass().getName() + ": " + calcArea() + " sq cm";
    }
}
```
`toString()`は`java.lang.Object`が持つメソッドで、**全クラスが標準で継承している**（recordはこれを自動でオーバーライドしてくれるだけで、`toString()`自体はrecord専用機能ではない）。通常のクラスでも自分で明示的にオーバーライドすれば同様にカスタマイズできる。

`getClass().getName()`は2段階のメソッドチェーン：
- `getClass()` → `Object`が持つメソッド。呼ぶと**実行時の実際のクラス**を表す`Class`オブジェクトが返る
- `.getName()` → その`Class`オブジェクト自身が持つメソッド（`java.lang.Class`のメソッド）。完全修飾名を文字列で返す

`System.out.println(new Circle())` → `println`が`toString()`を自動で呼ぶ → `Shape`の`toString()`が実行される → その中で`calcArea()`（`Circle`の実装）と`getClass().getName()`（`com.a.Circle`）が呼ばれる、という多段階の連鎖で最終的な出力が組み立てられる。

---

## 22. defaultメソッドの衝突解決：`インタフェース名.super.メソッド名()`（chap6/16実演）

```java
interface A { default void x() { System.out.println("A"); } }
interface B extends A { default void x() { System.out.println("B"); } }
interface C extends A { default void x() { System.out.println("C"); } }
public class Test2 implements B, C {
    @Override
    public void x() { B.super.x(); }   // Bの実装を明示的に使う
}
```
`Test2`は`B`と`C`の両方から`x()`を継承しているが、両方とも独自の`default`実装を持つため**衝突**する（Eclipse等のエラー: "Duplicate default methods named x... are inherited from the types C and B"）。継承チェーンが複数あるインタフェースでは、単純な`super.x()`ではどちらを指すか曖昧なので、**`インタフェース名.super.メソッド名()`という専用構文で名指しする**必要がある。

### 制約：直接implements/extendsしている相手にしか使えない
```java
public class Test2 implements B, C {
    public void x() { A.super.x(); }   // ✗ コンパイルエラー：Aは内部クラスを囲みません
}
```
`Test2`が直接`implements`しているのは`B`と`C`のみ。`A`は`B`/`C`のさらに親（間接的な祖先）であり、`Test2`から見て直接の相手ではないため、`A.super`という書き方自体が構文的に許されない。祖先の実装をそのまま使いたいだけで衝突していない場合（例：`Test2 implements B`だけで`B`自身が`x()`を持たない場合）は、何もオーバーライドせず放置すれば自動的に`A`の実装が届く。

独自の処理で解決したい場合は、委譲せず新しく書けばよい：
```java
public void x() { System.out.println("D"); }   // BともCとも無関係な独自実装
```

---

## 23. staticメソッドはインタフェースで継承・オーバーライドされない（chap6/17実演）

```java
interface Foo {
    static void statMethod() { System.out.println("Foo#statMethod()"); }
}
interface Bar extends Foo {
    default void x() { Foo.statMethod(); }   // 呼べるのは宣言元の名前経由だけ
}
class Concrete implements Bar {
//  Bar.statMethod();    // ✗ サブインタフェース名経由でも呼べない
}
// c.statMethod();       // ✗ インスタンス参照経由でも呼べない
```
`static`メソッドは、インタフェースであろうとクラスであろうと**オーバーライドという概念が存在しない**（`@Override`を付けようとするとエラー：「staticメソッドは@Overrideで注釈付けすることはできません」）。

| | `default`メソッド | `static`メソッド |
|---|---|---|
| 継承されるか | される（`obj.x()`で呼べる） | **されない** |
| オーバーライドできるか | できる | **できない** |
| 呼び出し方 | インスタンス経由 | **宣言したインタフェース名経由のみ**（`Foo.statMethod()`） |

サブインタフェースに同名の`static`メソッドを（`@Override`なしで）定義すると、それは「上書き」ではなく**完全に無関係な、そのインタフェース独自の別メソッド**として共存する（`Foo.statMethod()`と`Bar.statMethod()`はそれぞれ別物として両方呼べる）。

---

## 24. `equals`/`hashCode`/`toString`はインタフェースのdefaultメソッドにできない（chap6/16実演）

```java
interface Test3 {
    public default boolean equals(Object obj) { return false; }  // コンパイルエラー
}
```
```
エラー: インタフェースTest3のデフォルト・メソッドequalsはjava.lang.Objectのメンバーをオーバーライドします
```
`equals`/`hashCode`/`toString`は、**すべてのクラスが必ず`Object`から具象実装を継承している**メソッド。`default`メソッドの優先順位ルールは「クラス（親クラス含む）が持つメソッドは常にインタフェースの`default`より優先される」なので、インタフェース側に`default`版を用意しても実装クラス側は常に`Object`由来の実装を優先してしまい、**絶対に呼ばれることがない**。無意味で紛らわしいコードになるため、コンパイル時に禁止されている。

---

## 25. インタフェースを実装するメソッドは`public`が必須（chap6/16実演）

```java
interface Foo { default void x() {} }
class Test implements Foo {
    void x() { ... }   // ✗ エラー：(public)より弱いアクセス権限を割り当てようとしました
}
```
Javaの一般ルールとして、**オーバーライドする側は元のメソッドよりアクセス範囲を狭くできない**。インタフェースのメソッド（`abstract`でも`default`でも）は必ず`public`なので、実装側も`public`以上でなければならない。「今回たまたま他ファイルから呼ばれるから」ではなく、「`Foo`型として扱われた場合に、誰から見ても必ず呼べることを保証する契約だから」という理解が正確。

---

## 26. インタフェース自体・defaultメソッドに`final`を付けられない（chap6/21実演）

```java
final interface Simple {}   // ✗ 修飾子interfaceとfinalの組合せは不正です
interface Foo {
    final default void bar() {}   // ✗ これもエラー（修飾子finalをここで使用することはできません）
}
```
本体の無いメソッドに`final`が付けられない理由は「`abstract`（実装必須）と`final`（オーバーライド禁止）が矛盾するから」で説明できるが、**`default`メソッド（本体があり`abstract`ではない）に`final`を付けてもエラーになる**ため、それだけでは説明しきれない。実際には**インタフェースのメソッドには、本体の有無にかかわらずそもそも`final`という修飾子自体が使えない**という、より広い言語仕様上の制約。固定値を表現したいなら、フィールド（`public static final`の定数）を使うしかない。

---

## 27. ソースルートとパッケージの対応関係（chap6/19, 21, 23実演）

Javaの言語仕様には「このフォルダがルートです」という自動判定機能はない。ルートは常に**明示的に指定するか、javacを実行する場所（カレントディレクトリ）そのものが暗黙のルートになる**。

```
sources/
  com/
    Main.java       ← package com;      （ルートから1階層下）
    a/
      Circle.java   ← package com.a;    （ルートから2階層下）
```
**パッケージ名のドットの数＝ファイルからルートまで遡る階層数**、という対応関係で機械的に判定できる。ファイルの`package`宣言は、そのファイルの物理的な置き場所と必ず一致していなければならない（一致していないと、javac/IDEの言語サーバーいずれからも「宣言された空のパッケージは、パッケージXであるべき」という趣旨のエラーになる）。

### 「同じパッケージ」は階層の深さではなく完全一致が条件
```
com/a/Circle.java  → package com.a;
com/b/Heart.java   → package com.b;
```
どちらも「ルートから2階層下」という同じ深さだが、フォルダ自体が違う（`a`と`b`）ので**別パッケージ**。「階層が同じ」は同じパッケージの条件にならない。「ルートから見て完全に同一のフォルダパスに属している」ことだけが条件。

### コンパイル・実行コマンドのオプション整理
```bash
javac -d classes sources/com/Main.java              # -d：.classの出力先
javac -d classes -sourcepath sources sources/com/Main.java   # -sourcepath：importの解決に使うソースの探索起点
javac -cp classes sources/com/Main.java             # -cp（コンパイル時）：既にコンパイル済みの.classを参照先として追加
java -cp classes com.Main                            # -cp（実行時）：常に「.classの置き場所」を指す。.javaを指すことは無い
```
`javac`には「importで参照している別クラスが`.class`として見つからなければ、ソースパス上の`.java`を自動で探してコンパイルする」機能があるため、`-cp`で明示的に事前コンパイルを参照する方法（教科書の①②の2段階方式）と、単に`javac Main.java`だけで依存も自動解決させる方法の、どちらでも同じ結果になる。前者はソースと成果物の分離（`-d`で`classes/`にまとめる）に向いた書き方、後者は単純だが`.class`が`.java`と同じ場所に混在する。

`-cp`は**別の演習フォルダの成果物を再利用する**こともできる（例：`chap6/23`のコードが`chap6/19/classes`をそのまま参照）。複数の場所を指定したい場合は`:`（Windowsは`;`）でつなげる（`-cp 19/classes:23`）。

### `java`コマンドの引数は「クラス名は1つだけ」
```bash
java -cp 19/classes 23 Main   # ✗ "23"がクラス名として解釈され失敗。"Main"は無視される(args扱い)
java -cp 19/classes:23 Main   # ○ 複数の場所は-cpの中でコロン連結し、末尾は常にクラス名1つ
```

---

## 28. シールクラスの`permits`：別パッケージ不可、同一ファイルなら省略可能（chap6/19, 20実演）

```java
package com.a;
import com.b.Heart;
public abstract sealed class Shape permits Circle, Triangle, Square, Heart {}  // Heartはcom.bパッケージ
```
```
エラー: 名前のないモジュールのクラスShapeは別のパッケージのシール・クラスを拡張できません
（Permitted type Heart in an unnamed module should be declared in the same package com.a of declaring type Shape）
```
**`permits`リストに書けるサブタイプは、シールクラス自身と同じパッケージにいなければならない**（名前のないモジュールの場合）。これは`Heart.java`を実際にコンパイルするかどうかとは無関係で、`Shape.java`単体をコンパイルしただけでエラーになる（`Shape`の宣言自体がルール違反のため）。同じ一覧性を保つための制約。

### 同一ファイル内なら`permits`を省略できる
```java
public sealed class Shape /* permits Circle, Triangle, Square */ {}
final class Circle extends Shape {}
non-sealed class Triangle extends Shape {}
sealed class Square extends Shape {}
```
`permits`をコメントアウトしても、サブクラスが**全部同じファイル内**に揃っていればコンパイルが通る。`javap -v`で確認すると、`PermittedSubclasses: Circle, Triangle, Square`という属性がちゃんと自動生成されている——コンパイラが同一ファイル内の`extends Shape`を検出して暗黙的に許可リストを組み立てている。別ファイル（別パッケージ）に分かれている場合だけ、明示的な`permits`が必須になる。
