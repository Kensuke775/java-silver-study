# 第1章 Javaの概要と簡単なJavaプログラムの作成

対応する教科書ページ: 1〜30ページ
サンプル格納場所（Mac）: `~/Downloads/sample/chap1/`
学習環境: macOS（Apple Silicon）／ JDK 17（Homebrewの`openjdk@17`、17.0.20）

---

## 1. Javaの概要

- Javaは Sun Microsystems 社が開発（1996年1月リリース）。2010年に Oracle 社が買収。現在は JCP（Java Community Process）というコミュニティで仕様を決定
- 「プログラミング言語としてのJava」と「プラットフォームとしてのJava」の2つの側面がある
- 実行環境は **JVM**（Java Virtual Machine）が提供
- 仕様は Java SE（Standard Edition）／ Java EE（Enterprise Edition）／ Java ME（Micro Edition）の3エディション

### 1.1 Java言語の特徴

- プログラミングパラダイムとして**オブジェクト指向**を採用。「データと、そのデータを処理するコードのみをクラスにまとめ、他の必要なクラスと相互作用をすることで全体の処理を進める」という考え方（詳細は第5章）

### 1.2 実行環境の特徴

- ソースプログラムをコンパイルした**バイトコード**がJVM上で動く
- 一度作成したソースプログラムは異なる環境（Windows/macOS/Linux）でもそのまま実行できる（プラットフォーム非依存）
- ただし**JVM自体はプラットフォームに適したものが必要**（Windows用JVM、macOS用JVM、Linux用JVMはそれぞれ別物）

**バイトコードの補足（Q&Aで確認した内容）**
- 「バイト」の語源は8ビット単位のこと。JVMの命令（オペコード）が1バイト単位でエンコードされているため「バイトコード」と呼ぶ
- 32bit/64bitはCPU/OSのアーキテクチャの話で、バイトコードとは別軸の概念（混同しやすいので注意）
- `.class`ファイル先頭の`0xCAFEBABE`は、そのファイルがclassファイルであることを示すマジックナンバー

---

## 2. Java環境とJDK

### 2.1 Java環境とは

- **JRE**（Java Runtime Environment、Java実行環境）＝ JVM ＋ クラスライブラリ
- **JDK**（Java SE Development Kit、Java開発環境）＝ JRE ＋ 開発用ツール（javac, java等）
- 包含関係：`JDK ⊃ JRE ⊃ JVM`
- Oracle社が提供する2つのJDK：Oracle JDK（ライセンス: Oracle No-Fee Terms and Conditions、個人利用無料）／ OpenJDK（Oracleビルド、ライセンス: GNU GPL v2.0）

### 2.2 環境セットアップ

- 本書はWindows 10 + Oracle JDK 17.0.5前提で解説しているが、**試験対象は「JDK 17（メジャーバージョン）」の言語仕様**なので、細かいパッチバージョン（17.0.5等）を厳密に合わせる必要はない
- JDKリリースモデル：半年に1度（3月・9月）フィーチャーリリースでバージョンが上がる。アップデートリリースは年4回（1・4・7・10月）で脆弱性対策・バグ修正のみ。**LTS**（Long Term Support、長期メンテナンス）は数年単位。直近のLTSは2021年9月リリースのJDK 17

**実際に使っている環境（このMac）**

```bash
java -version
javac -version
# → openjdk version "17.0.20" (Homebrew)
```

Homebrewでのインストール手順（sudo不要な方法）：

```bash
brew install openjdk@17
```

※`oracle-jdk@17`のHomebrew caskは2025-10-29付けで非推奨化されており、かつ`.pkg`インストールに管理者パスワードの対話入力が必要なため、`openjdk@17`フォーミュラを採用した。Oracle JDK 17とOpenJDK 17はJDK 17以降コードベースが共通で、挙動・試験内容に差はない。

---

## 3. コマンドラインでのJavaプログラムのコンパイルと実行

### 3.1 プログラム実行までの流れ

- `javac`（**java** + **c**ompiler の略）：`.java`（人間用ソースコード）を`.class`（JVM用バイトコード）に変換するコンパイラ
- `java`：コンパイル済みの`.class`をJVM上で実行するコマンド
- `javac`と`java`は**別コマンド**（javacはjavaコマンドの一種、ではない）
- 流れ：`Hello.java` → `javac`でコンパイル → `Hello.class`（バイトコード）生成 → `java`で実行 → 標準出力に結果が出る
- コンパイルエラー時はソースを修正して再コンパイル、実行時エラー時はデバッグして再コンパイル

**重要な認識：`java`は「以前コンパイルしたデータ」をそのまま実行するだけ**

| | 通常実行（`java ClassName`） | ソースファイルモード（`java File.java`） |
|---|---|---|
| 何を実行するか | ディスク上の既存`.class`（＝過去にコンパイルしたもの） | その場でメモリ上にコンパイルした最新のバイトコード |
| ソースの変更は反映される？ | されない（再コンパイルしない限り） | 毎回反映される |

`java ClassName`はソース（`.java`）を一切見ておらず、既存の`.class`を無条件に読み込むだけ。ソースを編集しても`javac`で再コンパイルしない限り実行結果には反映されない（3.5節の「ハマりやすい罠」で実際に踏んだ）。

### 3.2 ソースファイルの作成

- `main`メソッドはJavaアプリケーション実行に必須。宣言は`public static void main(String[] args) {}`の形に固定
  - `public`・`static`：修飾子（メソッドそのものではない）
  - `void`：戻り値なしを示す型指定
  - `String[] args`：引数。`args`という名前は決まりではなく慣習（argumentsの略）で自由に変更可。`String... args`という書き方（`...`＝ドット3つ）も可
- 文（statement）の末尾は`;`が必須。`{ }`で囲まれた範囲が**ブロック**
- コメントは`//`（1行）と`/* */`（複数行）の2種類。コンパイル後の`.class`には含まれない
- `/** */`は**ドキュメンテーションコメント**（javadocコマンドでHTML化できる）

### 3.3〜3.4 コンパイルと実行（chap1/1 実演）

`chap1/1/Hello.java`は基本形の`public class Hello { public static void main(String[] args) { System.out.println("Hello world"); } }`（3行目にコンパイルエラーの実演コメントあり）。

```bash
cd ~/Downloads/sample/chap1/1
ls                 # Hello.java があることを確認（本書の dir に相当。Macには dir コマンドは無い）
javac Hello.java   # コンパイル → Hello.class が生成される
ls                 # Hello.class が増えていることを確認
java Hello          # 実行（拡張子は付けない）→ "Hello world" と出力
```

コンパイルエラー例（本書10ページ）：`String`を`string`と小文字にすると

```
Hello.java:2: エラー: シンボルを見つけられません
```

というエラーになる。エラー位置は「ファイル名:行数:」と`^`（ハット）で示される。**インデントは半角スペース/タブ、大文字小文字も区別**（`Main()`と`main()`は別物）。

### 3.5 ソースファイルとクラスファイルの関係（chap1/2 実演）

`chap1/2/Main.java`には`public class Main`と`class Sample`（修飾子なし）の**2クラス**が1ファイルに定義されている。

```bash
cd ~/Downloads/sample/chap1/2
javac Main.java
ls                  # Main.class と Sample.class の両方が生成される（クラス単位でファイルが分かれる）
java Main           # 実行成功 → "Main" と出力
java Sample         # 実行時エラー：Sampleクラスにmainメソッドが無いため
```

ポイント：
- 1つのソースファイルに複数クラスを宣言可能
- `public`が付いたクラスは**ソースファイル名と一致させる必要がある**（例：`Sample`にpublicを付けると`Main.java`内では宣言できずコンパイルエラー）
- `public`付きクラスは1ソースファイルに1つのみ
- 実務上は1ソースファイルに1クラスが一般的
- **mainメソッドは「1ファイルにつき1つまで」という制限はない。各クラスがそれぞれ独立してmainメソッドを持てる。** `java Sample`が失敗するのは「複数main禁止」ではなく、単にSampleクラス側にmainが定義されていないから（Sampleにもmainを追加すれば`java Sample`も実行できるようになる）

**ハマりやすい罠**：ソースを編集しても、**再コンパイルしない限り実行結果には反映されない**。「メインメソッドが見つからない」等のエラーが直したはずなのに出続ける場合、まず`javac`をやり直したか確認する（③実行の前に②コンパイルに必ず戻る、という図1-4のループを徹底する）。

### 3.6 ソースファイルモードでの実行（chap1/3 実演、Java 11以降）

`javac`を介さず、`java`コマンドで`.java`を直接実行できる機能。コンパイル結果はメモリ上のみに保持され、ディスクに`.class`は残らない。

> **メモ**：javaコマンドの後ろに拡張子付きのソースファイル名を指定すると、ソースファイルモードになる。ソースファイルをコンパイルした結果は、メモリ上に保持されるため、ディスク上にクラスファイルは残らない。HelloとSampleの2つのクラスのうち、最初のクラスHelloが実行された結果、出力が行われる。

```bash
cd ~/Downloads/sample/chap1/3
java Hello.java     # ソースファイルモード。javaコマンドなのに拡張子.javaを付ける点に注意（通常のjava実行と逆）
# → "Source-file mode" と出力（Hello.class は生成されない）
```

条件：
- プログラム全体が1つのソースファイルに定義されている単一ファイルであること
- **複数クラスがある場合、ファイル内の最初のクラスが起動する**（後続のクラスはmainがあっても実行されない）
- ソースファイルモードでは、**ソースファイル名とpublicクラス名が不一致でも実行できる**（`javac`では不可、実行のみ許可される特例）

**実演：`chap1/3/Main.java`（中身は`public class Hello`、ファイル名はMain.java）**（実際に確認済み）

```bash
cd ~/Downloads/sample/chap1/3
java Main.java
# → 成功: "Source-file mode - Main.java" と出力

javac Main.java
# → 失敗: Main.java:1: エラー: クラス Helloはpublicであり、ファイルHello.javaで宣言する必要があります
```

ソースファイルモード（`java`直接実行）だけがファイル名とpublicクラス名の不一致を許容する特例で、`javac`による通常コンパイルではこの不一致は常にコンパイルエラーになる。

**実演：クラスの並び順を入れ替えると挙動が変わる**（実際に確認済み）

```java
// Sampleを先頭に置いた場合
class Sample {
}

public class Hello {
    public static void main(String[] args) {
        System.out.println("Source-file mode");
    }
}
```

```bash
java Hello.java
# → エラー: クラスにmain(String[])メソッドが見つかりません: Sample
# 先頭のSampleにmainが無いため、Helloのmainまでは辿り着かない
```

もしSample側にもmainを追加していれば、先頭に来た方（この場合Sample）のmainが実行され、Helloは無視される。「最初のクラスだけが実行される」というルールが、常に一貫している。

```bash
cd ~/Downloads/sample/chap1/3
java Main.java      # 中身は public class Hello だが、ファイル名は Main.java。ソースファイルモードなら実行できる
```

---

## 4. パッケージ宣言とインポート

### 4.1〜4.2 パッケージ／パッケージ化（chap1/4 実演）

- **パッケージ**：クラスを整理する仕組み。「クラスを役割ごとに分けて管理」「クラス名の衝突回避」「アクセス制御が柔軟になる」というメリット
- 宣言は`package パッケージ名;`。**ソースファイルの先頭**（コメント・空白を除く）に記述し、1ファイルにつき1つのみ。サブパッケージは`.`（ドット）で区切る
- パッケージ宣言のないクラスは**無名パッケージ**に所属（＝すべてのクラスは必ず何らかのパッケージに属する）

```bash
cd ~/Downloads/sample/chap1/4
javac Main.java     # コンパイルは成功する（package com.se; と宣言されたMain.java）
java Main            # 実行時エラー：NoClassDefFoundError（パッケージ化されたクラスは完全修飾名で呼ぶ必要がある）
java com.se.Main     # これも失敗する。ディレクトリ構造がパッケージ宣言(com/se)と一致していないため
```

原因：Javaはすべてのクラスを**完全修飾名**（FQCN, Fully Qualified Class Name = パッケージ名＋クラス名）で認識する。パッケージ宣言と同じディレクトリ階層を用意してクラスファイルを格納する必要がある。

### 4.3 パッケージ化されたクラスのコンパイルと実行（chap1/5 実演、`-d`オプション）

`javac -d`で、パッケージ宣言と同じディレクトリ構造を自動生成しつつコンパイルできる。

```bash
cd ~/Downloads/sample/chap1/5/classes
javac -d . ../sources/Main.java     # -d でクラスファイルの出力先ディレクトリを指定
find . -name "*.class"              # com/se/Main.class, com/se/Sample.class が生成されているか確認
java com.se.Main                    # 完全修飾名で実行 → 成功（"Main"と出力）
```

### 4.4 クラスパス（同じchap1/5で`-cp`/`-classpath`実演）

**クラスパス**：コンパイル・実行時にクラスを検索するためのパス。

**核心：`-cp`を付けなくても、Javaは常に「カレントディレクトリ」をデフォルトのクラスパスとして探している。** `-cp`が省略できていた過去の例（chap1/1〜3）は「クラスパスが不要だった」のではなく、**デフォルトのクラスパス（カレントディレクトリ）の中に、たまたま探しているクラスがそのままの位置にあった**だけ。

```
chap1/5/
├── sources/Main.java
└── classes/com/se/Main.class   ← 実体はここ（chap1/5から見て1階層深い）
```

```bash
cd ~/Downloads/sample/chap1/5
java com.se.Main
# → 失敗。デフォルトのクラスパス(=今いるchap1/5)を起点に com/se/Main.class を探すが、
#    実体は classes/com/se/Main.class なので見つからない

java -cp classes com.se.Main
# → 成功。-cp で探索の起点を classes/ に変更したことで、
#    classes/com/se/Main.class が正しく見つかる

java -classpath classes com.se.Main  # -classpath でも同じ意味。省略形が -cp
```

**よくある構文ミス**：`-cp`/`-classpath`のフラグを付け忘れると、パスの文字列がクラス名として解釈されてしまう。

```bash
java classes com.se.Main
# エラー: メイン・クラスclassesを検出およびロードできませんでした
# 「classesという名前のクラスを実行しろ」という指示だと誤解釈され、com.se.Mainは単なる引数扱いになる
```

**カレントディレクトリへの依存をなくす方法（絶対パス指定）**

`-cp`に**絶対パス**を渡せば、実行時のカレントディレクトリに一切依存せず、指定したパスを起点に探しに行く（実際に確認済み：ホームディレクトリから実行しても成功した）。

```bash
cd ~   # 全く関係ない場所にいても
java -cp /Users/ikebatakensuke/Downloads/sample/chap1/5/classes com.se.Main
# → 成功（Main と出力）
```

一方、`-cp classes`のように**相対パス**で指定した場合は、なお「今いる場所を起点にした相対位置」として解釈されるため、カレントディレクトリが変わると探す場所も変わってしまう（＝相対パスではカレントディレクトリへの依存が残る）。完全に場所を問わず実行したいなら絶対パスを使うのが確実。

※OSのCLASSPATH環境変数を設定する方法もあるが、`-cp`/`-classpath`オプションが指定された場合はそちらが優先される。

**相対パス版 vs 絶対パス版（まとめ）**

| | コマンド | 実行できる場所 |
|---|---|---|
| 相対パス版 | `cd ~/Downloads/sample/chap1/5`<br>`java -cp classes com.se.Main` | `chap1/5`にいる時だけ成功（`classes`は「今いる場所から見て」の相対位置） |
| 絶対パス版 | `java -cp /Users/ikebatakensuke/Downloads/sample/chap1/5/classes com.se.Main` | **どこにいても成功**（`~`から実行しても確認済み。`/`から始まる完全な住所なので、今いる場所に依存しない） |

**コマンドライン上では「2つの独立した情報」を渡している**

```bash
java -cp classes         com.se.Main
        └─①classpath──┘   └─②クラス名──┘
```

①（`-cp`の値）と②（実行するクラス名）は、**スペースで区切られた別々の引数**であり、1本につながった1つのパスではない。それぞれ記法も別物。

| | 記法 | 役割 |
|---|---|---|
| ①classpath（ファイルシステムのパス） | `/`区切り（相対 or 絶対） | クラスを探し始める**起点**を指定 |
| ②クラス名（Javaの完全修飾名） | `.`区切り | **何を**探すか（パッケージ+クラス名）を指定 |

Javaが内部で①と②を組み合わせて`classes/com/se/Main.class`のような実ファイルを探しに行く。ユーザー側は「スラッシュで書き始めてどこかでドットに切り替える」という1本のパスを書いているのではなく、**役割の異なる2つの独立した文字列を、スペースで区切って渡している**だけ、という点がポイント。

`-cp`のフラグ自体を付け忘れると、①が存在しないことになり、②の文字列（`classes`）がクラス名として誤解釈されてエラーになる（前述の`java classes com.se.Main`の失敗例）。

### 4.5 インポート（chap1/6 実演）

異なるパッケージのクラスを使うには、①`import`宣言　②使用時に完全修飾名を指定　のいずれかが必要。同じパッケージ内のクラス同士は指定不要。

```bash
cd ~/Downloads/sample/chap1/6/classes
javac -d . ../sources/*.java    # sources配下の全.javaを一括コンパイル（*.java でまとめて指定可能）
java com.se.Main                # Foo, Bar の display() を呼び出し、"Foo" "Bar" と出力される
```

`chap1/6/sources/Main.java`のポイント：
- `import com.se.sub.Foo;` でインポートした`Foo`は、クラス名のみで使用可能（`Foo s = new Foo();`）
- インポートしていない`Bar`は、使用のたびに完全修飾名が必要（`com.se.sub.Bar b = new com.se.sub.Bar();`）
- ルール：ソースファイルの先頭、package宣言がある場合はその後に記述。パッケージ全体のインポートは`import java.util.*;`のようにクラス名の代わりに`*`を指定（`import java.util.*.*;`のようにパッケージ名の省略はNG。サブパッケージは自動的に含まれない）

### 4.6 標準APIのパッケージ

**標準API**（Application Programming Interface）＝ Javaが提供する実行可能な部品（クラスライブラリ）。

| パッケージ名 | 説明 | 主なクラス/インタフェース |
|---|---|---|
| `java.lang` | Java言語の基本クラス。**インポート不要で使用可能** | String, Integer, Math, Object |
| `java.io` | ファイル等へのデータ入出力機能 | PrintStream, Writer, Reader |
| `java.util` | コレクションフレームワーク等の便利機能 | *List*, *Set*, *Map*, ArrayList |
| `java.util.function` | ラムダ式の型となる関数型インタフェース | *Function*, *Consumer*, *Predicate*, *Supplier* |

（斜体はインタフェース、それ以外はクラス）

---

## 1章 練習問題・解説の要点（間違えやすいポイント）

### 問題1-1：JDKで提供されるツールはどれか（2つ選択）

**正解：B（JVM）, D（コンパイラ）**

JDK ＝ JRE（JVM＋クラスライブラリ）＋ 開発ツール（javac等）という構成（2.1節参照）。Aの「Java SE」は仕様であってツールではない。Cの「IDE」はJDK提供物ではない（Eclipse/IntelliJ等は別途インストールするもの）。Eの「main()メソッド」はプログラムに書くものであり、ツールではない。

### 問題1-2：実行時に呼ばれるmainメソッドとして正しいもの（3つ選択）

**正解：A, E, F**

mainメソッドは次の**5要素すべてが必須**、1つでも欠けると実行時の入口として認識されない。

- `public`（外部＝JVMからアクセス可能である必要がある）
- `static`（インスタンス化せず呼び出す必要がある）
- `void`（戻り値なし）
- メソッド名は`main`（小文字で完全一致。`Main`など大文字が混じると別物）
- 引数は**Stringの配列**（3通りの書き方が同じ意味になる）
  - `String[] args`（モダンな配列表記）
  - `String args[]`（旧式の配列表記、`[]`を変数名の後ろに置く）
  - `String... args`（可変長引数）

正解の内訳：A=`String[] args`、E=`String... s`（配列名は任意）、F=`String args[]`。不正解の理由：B=`String args`（配列になっていない）、C=`static`が抜けている、D=`void`が抜けている。

**関連して検証した追加ルール**
- `java.lang.String[] args`のように完全修飾名で書いても有効（`String`は`java.lang.String`の短縮形にすぎないため）
- `String... args[]`（可変長引数`...`と旧式配列`[]`の併用）は**コンパイルエラー**：「旧式の配列表記法は可変引数パラメータでは使用できません」。3つの書き方は独立した別パターンであり、組み合わせ不可

### 問題1-3：Javaアプリケーションの実行方法として正しいもの（2つ選択）

**正解：A（`java Main`）, B（`java Main.java`）**

`java`（実行）と`javac`（コンパイル）で、拡張子の付け方が正反対になる点がポイント。

| コマンド | 対象 | 拡張子 |
|---|---|---|
| `javac`（コンパイル） | ソースファイル | `.java`を**付ける**（必須） |
| `java`（通常実行） | クラス名 | 拡張子を**付けない**（`.class`はNG） |
| `java`（ソースファイルモード） | **ソースファイル名**（中のクラス名とは無関係） | `.java`を付ける |

- C（`java Main.class`）が誤りな理由：`.class`を付けると、Javaは「`Main.class`という名前のクラス」を探そうとして`ClassNotFoundException`になる（実際に検証済み）。「コンパイルしているように見えるから」という判断基準は誤りで、正しくは拡張子の有無が原因
- D, E, F（`javac Main` / `javac Main.java` / `javac Main.class`）はすべて`javac`＝コンパイルのコマンドであり、そもそも「実行方法」の問いに対する答えとして成立しない
- 参考：もし「コンパイルの正しいコマンドは？」だった場合はE（`javac Main.java`）が正解。D（`javac Main`）は拡張子なしでエラー、F（`javac Main.class`）は「無効なフラグ」でエラー（実際に検証済み）

### 問題1-4：コンパイルが成功するファイル（3つ選択）

**正解：A, C, D**

判断基準は「先頭のクラスがファイル名と一致しているか」ではなく、**「`public`が付いたクラスの名前が、ファイル名と一致しているか」**のみ。位置（先頭かどうか）は無関係。

- `public`クラスが**ある**場合 → そのクラス名とファイル名が一致していないとコンパイルエラー
- `public`クラスが**1つもない**場合 → ファイル名との一致は一切不問（実際にHello.javaの中に`class Hello{} class Main{} class Test{}`と書いても全部コンパイル成功することを検証済み）
- 1ファイルに`public`クラスは**1つまで**。2つあればその時点で即アウト

| 選択肢 | ファイル名 | 内容 | 判定 |
|---|---|---|---|
| A | Hello.java | `class Hello{}` `class Main{}` `class Test{}` | publicなし→不問→成功 |
| B | Hello.java | `class Hello{}` `public class Main{}` | publicの`Main`とファイル名`Hello`が不一致→失敗 |
| C | Hello.java | `public class Hello{}` | publicとファイル名が一致→成功 |
| D | Main.java | `public class Main{}` `class Hello{}` | publicの`Main`とファイル名`Main`が一致（位置は2番目でも関係ない）→成功 |
| E | Main.java | `public class Hello{}` `public class Main{}` | publicクラスが2つ→即アウト |

**なぜpublicなしならファイル名不問なのか**：`public`は「外部から名前で探せる」という約束のための制約。`public`が無いクラスは外部から探されない前提なので、ファイル名との一致を強制する理由がそもそも無い。

### 問題1-5：パッケージの説明として誤っているもの（2つ選択）

**正解：A, D**

- A（誤り）：「ソースファイルを管理する仕組み」→ パッケージが管理するのは**クラスファイル**であり、ソースファイルではない
- B（正しい説明）：「クラスファイルを管理する仕組み」
- C（正しい説明）：「1つのソースファイルに1つのパッケージ宣言のみできる」
- D（誤り）：「複数の異なるソースファイルに、同じ名前のパッケージ宣言は記述できない」→ 逆で、**同じ名前のパッケージ宣言を複数の異なるファイルに書くのは普通にできる（むしろ必須）**。同じパッケージに複数クラスをまとめる仕組みそのものだから（`chap1/6`のFoo.javaとBar.javaが両方とも`package com.se.sub;`なのが実例）
- E（正しい説明）：「パッケージ宣言が異なっていれば、同じ名前のクラスを宣言できる」→ 実際に検証済み：パッケージ`a`と`b`にそれぞれ`Sample`という同名クラスを作っても、完全修飾名が`a.Sample`/`b.Sample`と異なるため共存できる

**CとDの軸の違い（混同注意）**
- C：**1つのファイルの中**でpackage宣言を複数書けるか → 書けない
- D：**別々のファイル間**で同じパッケージ名を使い回せるか → 使い回せる（Dの「できない」が誤り）

**関連して検証**：逆に**同じパッケージ内**で同じクラス名を2つ定義すると「クラスa.Sampleが重複しています」というエラーになる（パッケージが異なる場合のみ同名クラスが共存できる、パッケージが同じなら重複でNG）。

### 問題1-6：パッケージ宣言とインポート宣言について正しい記述（2つ選択、すべて先頭で記述）

**正解：A, F**

ルール：①両方の宣言がある場合はパッケージ宣言を先に記述する　②インポート宣言はクラス名の部分のみ`*`で省略可能　③パッケージ宣言は1ファイルに1つのみ　④インポート宣言は1ファイルに複数指定可能

- A（正）：`package com.p; import com.a.*;` → 順番も`*`の使い方も正しい
- B（誤）：`import com.a.*; package com.p;` → package宣言とimport宣言の順序が逆でコンパイルエラー
- C（誤）：`package com.p; import com.*.*;` → `import com.*.*;`はNG。`*`はパッケージ名の途中（サブパッケージ部分）の省略には使えず、末尾のクラス名部分のみ有効
- D（誤）：`package com.*; import com.a.Sample;` → **`package`宣言では`*`は絶対に使えない**（package宣言は必ず1つの確定した名前を書く必要があり、ワイルドカードの概念自体が存在しない）
- E（誤）：`package com.p1; package com.p2; import com.a.*;` → package宣言を2回書いておりコンパイルエラー（1ファイルにつき1つのみ）
- F（正）：`package com.p; import com.a.Sample; import com.a1.*; import com.a2.*;` → package宣言は1つ、import文は複数行に分けて構わない（特定クラスのimportとワイルドカードimportの混在もOK）。「大文字（クラス名）が混じっているから怪しい」という直感的判断は根拠にならない

**関連して検証した追加ルール**
- `import *;`（パッケージ名を一切書かず`*`だけ）は文法エラー：「`<identifier>`がありません」。`*`は必ず`パッケージ名.`の後ろにのみ置ける、パッケージ名自体を省略する機能ではない

---

## Mac操作コマンド早見表（本書はWindows前提のため要変換）

| 用途 | 本書の表記（Windows） | Macでの実際 |
|---|---|---|
| ディレクトリ区切り | `¥` | `/` |
| プロンプト | `>` | `$` |
| ディレクトリの中身一覧 | `dir` | `ls` |
| ディレクトリ移動 | `cd C:¥sample¥chap1¥1` | `cd ~/Downloads/sample/chap1/1` |
| コンパイル | `javac Hello.java` | 同じ（共通） |
| 実行 | `java Hello` | 同じ（共通） |

---

## 環境メモ

- JDK: Homebrewの`openjdk@17`（17.0.20）。`java`/`javac`はこのバージョンを指すようPATH解決済み
- サンプルプログラム格納場所: `~/Downloads/sample/chap1/`（元zip: `~/Downloads/Javase17_silver_sample_v7.zip`）
- **文字コード注意**：zip内の`.java`/`.txt`は元がShift-JIS(CP932)エンコード。標準の解凍だと日本語コメントが文字化けするため、UTF-8に変換し直して格納している（対応済み、381ファイル変換）

## 疑問点・要復習（随時追記）

-
