## 目次

> [!IMPORTANT]
> **要点整理・参考資料**
> - [参考資料：例外/エラークラスの和訳・発生状況まとめ](#ref-summary)
> - [3.1 try-with-resources 要点整理](#ref-trywithresources)
> - [4.1 throws 要点整理](#ref-throws)

**問題一覧**

- [問題1（1.1 例外の発生：ArrayIndexOutOfBoundsExceptionの基本）](#q1)
- [問題2（1.1 例外の発生：参考コラム「switch文または式でのthrow」）](#q2)
- [問題3（1.2 例外の種類：チェック例外／非チェック例外の判別＝コンパイルが通るか）](#q3)
- [問題4〜9（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル、1.3 カスタム例外）](#q4-9)
- [問題10（応用：カスタム例外をRuntimeExceptionから継承／原因例外のラップ）](#q10)
- [問題11（1.4／2.1 try-catch-finally：catchなしのtry-finally、finallyは必ず実行される）](#q11)
- [問題12（1.3 カスタム例外の続き：Throwable causeのみのコンストラクタ＝メッセージはcauseから自動生成）](#q12)
- [問題13（1.4 例外処理とは：try-catchとthrowsの違い、文章問題）](#q13)
- [問題14（2.1 try-catch-finally：catch内のreturnがあってもfinallyは実行される、`chap7/5/Main.java`の注意点）](#q14)
- [問題15（骨太版：finallyの中のreturnはtry/catchのreturnを上書きする）](#q15)
- [問題16（2.2 複数のcatchブロック：継承関係のある例外はサブクラスを先に書く、逆順はコンパイルエラー）](#q16)
- [問題21（骨太版：複数catch＋finally＋ループの複合トレース）](#q21)
- [問題17〜20（例外クラスの継承チェーン暗記ドリル）](#q17-20)
- [問題22（2.3 multi-catch：catch変数は暗黙的にfinal＝再代入禁止）](#q22)
- [問題23（継承チェーン11クラス：状況→例外/エラークラス名の対応ドリル）](#q23)
- [問題24（2.3 multi-catchの総合ドリル：兄弟OK／再代入不可／継承関係NG、5択複数選択）](#q24)
- [問題25（2.3 multi-catchの総合ドリル：仕切り直し版）](#q25)
- [問題26（3.1 try-with-resources：要点＋エッジケースの正誤判定、5択複数選択）](#q26)
- [問題27（3.1 try-with-resourcesの応用：リソースのオープン自体が失敗した場合のクローズ挙動）](#q27)
- [問題28（発展編：tryブロック本体とclose()の両方が例外を投げた場合＝抑制された例外／suppressed exception）](#q28)
- [問題29](#q29)
- [問題30（3.1 try-with-resources：問題27の改変・未到達リソースの明示化）](#q30)
- [問題31（3.1 try-with-resources：問題28の改変・suppressedが複数溜まるケース）](#q31)
- [問題32（3.1 try-with-resources：finallyを組み込んだパターン）](#q32)
- [問題33（4.1 throws：コードベースのコンパイルエラー特定問題）](#q33)
- [問題34（4.1 throws：文章の正誤選択）](#q34)
- [問題35（4.1 throws：catch/throwsのスーパークラス指定パターン）](#q35)
- [問題36（4.1 throws：catch/throwsのワイドニング判定・5パターン一括）](#q36)
- [問題37（2.1 try-catch：catchで捕まえた後は処理が続行する、というポイント確認）](#q37)
- [問題38（2.1／4.1 まとめ：例外発生時の制御フロー、文章の正誤選択）](#q38)
- [問題39（4.1 throws：main()のthrows／非チェック例外の任意宣言／広い型でのthrows宣言、文章の正誤選択）](#q39)
- [問題40（4.1 throws：チェック例外のcatch-or-specify、呼び出しチェーン3段構成）](#q40)
- [問題41（4.1 throws：例外の変換＝チェック例外をcatchして別の非チェック例外にラップして投げ直すパターン）](#q41)
- [問題42（4.1 throws：3段の呼び出しチェーン＋型が合わないcatchが途中に紛れ込むパターン）](#q42)
- [問題43（総合：try-with-resources＋suppressed例外＋finallyの横断問題）](#q43)

<a id="q1"></a>
## 問題1（1.1 例外の発生：ArrayIndexOutOfBoundsExceptionの基本）

`sample/chap7/1/Main.java`（コマンドライン引数を使ったArrayIndexOutOfBoundsExceptionの例）に基づく内容。

```java
public class Main {
    public static void main(String[] args) {
        String[] colors = {"Red", "Green"};
        for (int i = 0; i <= colors.length; i++) {
            System.out.println(colors[i]);
        }
        System.out.println("Done");
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Red` `Green` が出力される
B. `Red` `Green` `Done` が出力される
C. `Red` `Green` が出力された後、`ArrayIndexOutOfBoundsException`がスローされてプログラムが終了する
D. コンパイルエラーが発生する
E. 何も出力されずに`ArrayIndexOutOfBoundsException`がスローされる

**解答**

正解：**C**

**補足**

- `colors.length`は`2`。ループ条件が`i <= colors.length`（`<`ではなく`<=`）になっているため、`i = 0, 1, 2`まで回ろうとする。
- `i = 0, 1`では`colors[0]="Red"`、`colors[1]="Green"`が正常に出力される。
- `i = 2`で`colors[2]`という存在しない要素にアクセスした瞬間、JVMが`ArrayIndexOutOfBoundsException`オブジェクトを生成してスローする。
- try-catchなどの例外処理が書かれていないため、この時点でプログラムは強制終了し、それ以降の`"Done"`は出力されない。
- `chap7/1/Main.java`（コマンドライン引数なしで`args[0]`にアクセス→同じ例外）と全く同じ「JVMが不正なインデックスアクセスを検知して例外オブジェクトを生成・スローする」という仕組みを、配列リテラル＋forループの形で再現した。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q2"></a>
## 問題2（1.1 例外の発生：参考コラム「switch文または式でのthrow」）

テキストの参考コラム（switch文やswitch式の`->`構文でも`throw`により例外をスローできる、第3章の復習）に基づく内容。

```java
public class Main {
    public static void main(String[] args) {
        int level = 5;
        String label = switch (level) {
            case 1 -> "Low";
            case 2 -> "Medium";
            case 3 -> "High";
            default -> throw new IllegalArgumentException("Invalid level: " + level);
        };
        System.out.println(label);
    }
}
```

次のプログラムをコンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Low` が出力される
B. `Medium` が出力される
C. `switch`式の`default`で`throw`は使用できないため、コンパイルエラーが発生する
D. `IllegalArgumentException`（メッセージ`Invalid level: 5`）がスローされ、プログラムが終了する
E. 何も出力されずに正常終了する

**解答**

正解：**D**

**補足**

- `switch`式の各分岐（`->`）は本来、値を返す（式の結果、または`yield`）必要があるが、値の代わりに`throw`で例外をスローすることも許されている（このケースでは「値を返す」という要件自体が免除される）。
- `level = 5`はどの`case`（1, 2, 3）にも一致しないため`default`に入り、`throw new IllegalArgumentException("Invalid level: " + level)`がそのまま実行される。
- `IllegalArgumentException`は`RuntimeException`のサブクラスで非チェック例外。例外処理を書かなくてもコンパイルは通り（Cは誤り）、実行時にそのままスローされてプログラムが終了する。`label`への代入や`println`は実行されない。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q3"></a>
## 問題3（1.2 例外の種類：チェック例外／非チェック例外の判別＝コンパイルが通るか）

```java
import java.io.IOException;

public class Main {
    static void checkNull() {
        throw new NullPointerException("null!");
    }
    static void checkFormat() {
        throw new NumberFormatException("format!");
    }
    static void checkIO() {
        throw new IOException("io!");
    }
    static void checkMemory() {
        throw new OutOfMemoryError("memory!");
    }

    public static void main(String[] args) {
        System.out.println("start");
    }
}
```

このプログラムをコンパイルすると、どのメソッドが原因でコンパイルエラーになりますか。（1つ選択）

A. `checkNull()`
B. `checkFormat()`
C. `checkIO()`
D. `checkMemory()`
E. コンパイルエラーは発生しない

**解答**

正解：**C**

**補足**

- `NullPointerException`・`NumberFormatException`は`RuntimeException`のサブクラス（表7-3）＝非チェック例外。`OutOfMemoryError`は`Error`のサブクラス（表7-4）＝非チェック例外。どちらも例外処理なしでコンパイルが通る。
- `IOException`は`Exception`のサブクラスで`RuntimeException`系ではない（表7-2）＝チェック例外。catchするか`throws IOException`を宣言しないと「例外IOExceptionは報告されません。スローするには、捕捉または宣言する必要があります」というコンパイルエラーになる（`javac`で検証済み）。
- ユーザーからの補足質問：`Error`系（`OutOfMemoryError`など）を自分で`new`して`throw`するのは文法的には自由（コンパイラは禁止しない）。ただし実務上、JVMレベルの致命的異常を表す想定のクラスなので、アプリケーションコードから意図的に投げることは通常ない。

**実施記録**

迷ったところ：なし。正解の上で「OutOfMemoryErrorを自分で定義（throw）するのは自由なのか」という補足質問があった。

<a id="q4-9"></a>
## 問題4〜9（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル、1.3 カスタム例外）

チェック例外・非チェック例外の区別が「覚えられない」というユーザーの要望で、暗記用に立て続けに出題したセット。

### 問題4

次のクラスのうち、チェック例外はどれですか。（1つ選択）

A. `ArithmeticException`
B. `ClassCastException`
C. `FileNotFoundException`
D. `NumberFormatException`
E. `StackOverflowError`

正解：**C**（他は全て非チェック。A・B・DはRuntimeException系、Eは Error系）

※ユーザーは本問には明示的に解答せず、「もっと出して」と問題5〜8のリクエストへ進んだ（未回答のまま次に進行）。

### 問題5

次のうちチェック例外はどれですか。（1つ選択）

A. `ArrayIndexOutOfBoundsException`　B. `ClassNotFoundException`　C. `NoClassDefFoundError`　D. `NullPointerException`　E. `StackOverflowError`

正解：**B**　ユーザー解答：B（正解）

### 問題6

```java
public class Main {
    static void a() { throw new ArrayIndexOutOfBoundsException(); }
    static void b() { throw new ClassNotFoundException(); }
    static void c() { throw new NoClassDefFoundError(); }
    static void d() { throw new StackOverflowError(); }
}
```

どのメソッドが原因でコンパイルエラーになりますか。（1つ選択）→正解：**B**（`b()`、`ClassNotFoundException`はチェック例外）　ユーザー解答：B（正解）

### 問題7

次のうち非チェック例外はどれですか。（2つ選択）

A. `IOException`　B. `NumberFormatException`　C. `ClassNotFoundException`　D. `NoClassDefFoundError`　E. `FileNotFoundException`

正解：**B, D**　ユーザー解答：B, D（正解）

### 問題8

```java
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {
    static void p() { throw new ArithmeticException(); }
    static void q() { throw new FileNotFoundException(); }
    static void r() { throw new NoClassDefFoundError(); }
    static void s() { throw new IOException(); }
}
```

どのメソッドが原因でコンパイルエラーになりますか。（2つ選択）→正解：**B, D**（`q()`と`s()`。`FileNotFoundException`・`IOException`はどちらもチェック例外）　ユーザー解答：B, D（正解）

いずれも`javac`で実際にコンパイルして検証済み。全問正解を受けて、表7-2〜7-4の早見表（Exception直下＝チェック、RuntimeException系／Error系＝非チェック）と、「Error系は名前に必ずErrorと付くので見た目で判別できる、名前だけでは区別がつかないException系のうちチェック例外3つ（ClassNotFoundException・IOException・FileNotFoundException）だけをピンポイントで覚えればいい」という暗記のコツを提示。ユーザーはこの考え方を自分の言葉で正しく再確認した。

### 問題9（1.3 カスタム例外：`Exception`を継承した独自例外クラスの作成と意味）

`sample/chap7/2/InvalidAgeException.java`（`Exception`を継承したカスタム例外の実例、4種のコンストラクタオーバーロード）に基づく内容。

```java
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

public class Main {
    static void validate(int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException("Invalid score: " + score);
        }
        System.out.println("OK: " + score);
    }

    public static void main(String[] args) {
        try {
            validate(150);
        } catch (InvalidScoreException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A. `OK: 150` が出力される
B. `Caught: Invalid score: 150` が出力される
C. `Caught: null` が出力される
D. `validate`メソッドの宣言に問題があり、コンパイルエラーになる
E. `InvalidScoreException`は`RuntimeException`を継承していないため、そもそも`throw`できずコンパイルエラーになる

#### 解答

正解：**B**

#### 補足

- 標準APIに適切な例外クラスがない場合、独自の例外クラスを作れる（＝カスタム例外）。一般的に`Exception`を継承して作る（チェック例外にする）ことで、処理し忘れをコンパイラが検知できるようにする、というのがテキストの設計意図。
- `super(message)`で渡した文字列は`Throwable`側に保存され、`e.getMessage()`でそのまま取得できる（Cのように`null`にはならない）。
- `validate`メソッドは`throws InvalidScoreException`を宣言しているので、チェック例外でもコンパイルエラーにはならない（Dは誤り）。`throws`は「投げる許可を与えるもの」ではなく「このメソッドは（チェック）例外を投げる可能性があるので呼び出し元に予告する」という宣言・義務である点をユーザーの理解と合わせて確認した。
- `RuntimeException`を継承していなくても、`Exception`（さらには`Throwable`）を継承していれば`throw`は問題なく可能（Eは誤り）。

#### 実施記録

迷ったところ：なし（一発正解）。「自分で例外クラスを作れる」「`Exception`を継承すると`Throwable`の機能一式が使える」「`throws`は処理させるための宣言」という理解をユーザー自身の言葉で確認し、`throws`が「許可」ではなく「予告・義務」である点だけ訂正した。

<a id="q10"></a>
## 問題10（応用：カスタム例外をRuntimeExceptionから継承／原因例外のラップ）

```java
class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class Main {
    static void load() {
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            throw new DataAccessException("Failed to load data", e);
        }
    }

    public static void main(String[] args) {
        try {
            load();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A. `Failed to load data` のみが出力される
B. `Failed to load data` の後に `java.lang.NumberFormatException: For input string: "abc"` が出力される
C. `load()`に`throws`宣言がないため、コンパイルエラーになる
D. `NumberFormatException`が`main`まで伝播し、`DataAccessException`はキャッチされない
E. 2行とも`null`が出力される

**解答**

正解：**B**

**補足**

- `DataAccessException`は`Exception`ではなく**`RuntimeException`を継承**しているため非チェック例外。`load()`に`throws`宣言がなくてもコンパイルは通る（Cは誤り。問題9の`Exception`継承版との対比ポイント）。
- `load()`内部の`try-catch`で`NumberFormatException`を捕まえ、それを`cause`として`DataAccessException`に包んで投げ直している（例外のラップ）。外側の`catch (DataAccessException e)`に届くのは包んだ後の`DataAccessException`であり、元の`NumberFormatException`が直接`main`まで伝播するわけではない（Dは誤り）。
- `getMessage()`は`super(message, cause)`で渡した`"Failed to load data"`をそのまま返す。`getCause()`は包んだ元の例外オブジェクトそのもの（`println`に渡すと`toString()`で`java.lang.NumberFormatException: For input string: "abc"`の形式になる）。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q11"></a>
## 問題11（1.4／2.1 try-catch-finally：catchなしのtry-finally、finallyは必ず実行される）

```java
public class Main {
    static void process(int value) {
        try {
            System.out.print("try ");
            if (value < 0) {
                throw new IllegalArgumentException("negative");
            }
            System.out.print("ok ");
        } finally {
            System.out.print("finally ");
        }
    }

    public static void main(String[] args) {
        process(-1);
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A. `try ok finally ` が出力される
B. `try finally ` が出力された後、`IllegalArgumentException`が`main`まで伝播してプログラムが終了する
C. `try ok ` のみが出力される（`finally`は実行されない）
D. `catch`がないため、コンパイルエラーになる
E. `try ` のみが出力され、`finally`は実行されずにプログラムが終了する

**解答**

正解：**B**

**補足**

- `try`は`catch`か`finally`のどちらか一方があれば構文として成立する。`catch`がないこと自体はコンパイルエラーの理由にならない（Dは誤り）。
- `value < 0`なので`try `の出力後に`throw`が実行され、`ok `には到達しない。
- **`finally`は`catch`の有無に関わらず必ず実行される**。例外がローカルで捕まらずに上位へ伝播する場合でも、伝播する「前」に`finally`ブロックの処理が実行される。
- `finally`実行後、`IllegalArgumentException`は誰にもキャッチされていないため`main`まで伝播し、そこでも捕まらずプログラムは異常終了する（`javac`/`java`で検証済み：`try finally `の後にスタックトレースが出力される）。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q12"></a>
## 問題12（1.3 カスタム例外の続き：Throwable causeのみのコンストラクタ＝メッセージはcauseから自動生成）

```java
class ConfigException extends Exception {
    public ConfigException(Throwable cause) {
        super(cause);
    }
}

public class Main {
    static void loadConfig() throws ConfigException {
        try {
            Integer.parseInt("N/A");
        } catch (NumberFormatException e) {
            throw new ConfigException(e);
        }
    }

    public static void main(String[] args) {
        try {
            loadConfig();
        } catch (ConfigException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A. `null` が出力される
B. 何も出力されない
C. `java.lang.NumberFormatException: For input string: "N/A"` が出力される
D. `ConfigException`に`String message`を受け取るコンストラクタがないため、コンパイルエラーになる
E. `loadConfig()`に`throws`宣言が必要なため、コンパイルエラーになる

**解答**

正解：**C**

**補足**

- `Throwable(Throwable cause)`コンストラクタ（表7-5）は「指定された原因**と、原因から生成した詳細メッセージ**を持つオブジェクトを生成する」。つまり`message`を明示的に渡さなくても、`cause`の`toString()`が自動的にメッセージとして設定される（Aのように`null`にはならない）。
- `loadConfig()`はすでに`throws ConfigException`を宣言しているのでコンパイルエラーにはならない（Eは誤り）。`ConfigException`が`String message`用のコンストラクタを持っていなくても、`Throwable cause`用のコンストラクタが使えれば問題ない（Dは誤り）。
- ユーザーからの補足質問：「必ず例外処理が必要なもの＝チェック例外」は、`Exception`を継承していて**かつ`RuntimeException`（またはそのサブクラス）ではない**もの、というルールを確認。`RuntimeException`自体は`Exception`の子孫だが仕様上チェック対象から除外されている。このルールは自作のカスタム例外にもそのまま当てはまる（`extends Exception`ならチェック、`extends RuntimeException`なら非チェック）。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q13"></a>
## 問題13（1.4 例外処理とは：try-catchとthrowsの違い、文章問題）

次のうち、例外処理に関する説明として正しいものはどれですか。（3つ選択）

A. `throws`を使ってメソッド宣言に例外クラスを書けば、その例外に対する処理は完了したことになる
B. `try-catch`は、例外が発生した「その場所」で例外を捕まえて処理する方法である
C. `throws`は、例外がスローされる可能性のあるメソッドやコンストラクタ自身では処理せず、呼び出し元に処理を委ねる方法である
D. チェック例外がスローされる可能性があるプログラムでも、`try-catch`と`throws`のどちらも使わずにコンパイルを成功させることができる
E. `throws`を使った場合、最終的にはどこかの呼び出し元で`try-catch`による例外処理を行う必要がある

**解答**

正解：**B, C, E**

**補足**

- B：`try-catch`は発生したその場所で捕まえて処理する方法（1.4節本文の記述通り）。
- C：`throws`は自分では処理せず、例外オブジェクトを呼び出し元に転送するだけの方法。
- E：`throws`は「転送」であって「処理の完了」ではないため、最終的にはどこかの呼び出し元で`try-catch`による処理が必要（1.4節本文に明記）。
- A：誤り。`throws`を書いただけでは処理は完了しない（Eの裏返し）。
- D：誤り。チェック例外があるプログラムは`try-catch`か`throws`のいずれかを使わないとコンパイルが成功しない。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q14"></a>
## 問題14（2.1 try-catch-finally：catch内のreturnがあってもfinallyは実行される、`chap7/5/Main.java`の注意点）

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(check());
    }
    static String check() {
        try {
            method();
            return "try-end";
        } catch (Exception e) {
            System.out.print("catch ");
            return "catch-end";
        } finally {
            System.out.println("finally");
        }
    }
    static void method() throws Exception {
        throw new Exception("boom");
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A. `catch ` のみが出力される（`finally`は実行されない）
B. `catch finally` の後、`catch-end` が出力される
C. `try-end` が出力される
D. `finally catch` の後、`catch-end` が出力される
E. `catch`ブロックに`return`があるため、`finally`はスキップされてすぐに`catch-end`が出力される

**解答**

正解：**B**

**補足**

- `method()`が例外をスローするので`catch`ブロックに移り、`"catch "`を出力してから`return "catch-end"`に到達する。
- ここがポイントで、**`catch`ブロックの中で`return`しても、メソッドが実際に呼び出し元へ値を返す前に必ず`finally`ブロックが実行される**（`chap7/5/Main.java`の`example1()`と同じ注意点）。よって`"finally"`が出力されてから、ようやく`"catch-end"`という戻り値が確定して`main`に返る。
- `javac`/`java`で検証済み：`catch finally`（1行目）→`catch-end`（2行目）。

**実施記録**

迷ったところ：なし。正解の上で「`throws`宣言されたメソッドを呼ぶ側は、必ずtry-catchで囲むかthrowsを宣言しないとコンパイルエラーになるのか」という確認質問があり、その通りと回答（問題13の「throwsで呼び出し元に転送」という選択肢とも繋がる話として整理）。

<a id="q15"></a>
## 問題15（骨太版：finallyの中のreturnはtry/catchのreturnを上書きする）

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(evaluate(0));
        System.out.println(evaluate(5));
    }

    static String evaluate(int x) {
        try {
            if (x == 0) {
                throw new ArithmeticException("zero");
            }
            return "try:" + x;
        } catch (ArithmeticException e) {
            return "catch:" + e.getMessage();
        } finally {
            if (x == 0) {
                return "finally:override";
            }
        }
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A.
```
catch:zero
try:5
```

B.
```
finally:override
try:5
```

C.
```
finally:override
finally:override
```

D. `evaluate(0)`実行時、`catch`と`finally`の両方が`return`しようとするため実行時エラーになる

E.
```
catch:zero
finally:override
```

**解答**

正解：**B**

**補足**

- `evaluate(0)`：`try`で例外がスローされ`catch`が`return "catch:zero"`をしようとするが、**`finally`ブロックの中に`return`があると、それが`catch`（や`try`）の戻り値を丸ごと上書き（破棄）する**。よって実際に返るのは`"finally:override"`のみで、`"catch:zero"`は表に出てこない。
- `evaluate(5)`：`x != 0`なので`finally`内の`if`は発火せず、`finally`は何も`return`しない。この場合は素直に`try`の`return "try:5"`がそのまま返る。
- 「一度`try`や`catch`が`return`を決めたら変えられない」という思い込みが誤答Aの原因になりやすいが、`finally`だけは後から丸ごと上書きできる特別な存在（`javac`/`java`で検証済み）。

**実施記録**

迷ったところ：なし（一発正解）。

<a id="q16"></a>
## 問題16（2.2 複数のcatchブロック：継承関係のある例外はサブクラスを先に書く、逆順はコンパイルエラー）

```java
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        try {
            if (args.length == 0) {
                throw new FileNotFoundException("no file");
            }
            throw new IOException("io problem");
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: " + e.getMessage());
        }
    }
}
```

このプログラムをコンパイルするとどうなりますか。（1つ選択）

A. `IOException: io problem` が出力される
B. `FileNotFoundException: no file` が出力される
C. `catch (FileNotFoundException e)` の行でコンパイルエラーになる
D. 実行時に `ClassCastException` がスローされる
E. コンパイルは成功するが、`FileNotFoundException`のcatchブロックには実行時に絶対到達しない

**解答**

正解：**C**

**補足**

- 複数の`catch`ブロックは上から下へ順に走査され、最初にマッチした型が実行される。
- `FileNotFoundException`は`IOException`のサブクラス（`javac`で継承チェーンを確認：`FileNotFoundException → IOException → Exception → Throwable → Object`）。継承関係のある例外は**サブクラス（狭い方）を先に、スーパークラス（広い方）を後に**書く必要がある。
- 本問は逆順（`IOException`が先、`FileNotFoundException`が後）なので、下の`FileNotFoundException`用`catch`ブロックには絶対に制御が来なくなる（`IOException`側が先に全部拾ってしまうため）。Javaはこれを**単なる警告ではなく明確なコンパイルエラー**として弾く（`javac`で確認：「例外FileNotFoundExceptionはすでに捕捉されています」）。Eのように「コンパイルは成功するが実行時に到達しない」わけではない点に注意。
- 参考：表7-2〜7-4に載っている例外クラスの中で、お互いに直接の継承関係にあるのは`FileNotFoundException`/`IOException`のペアだけ（他は全て無関係な兄弟同士）。

**実施記録**

迷ったところ：なし。「任意/必須（チェック・非チェック）の区別」と「catchの並び順のルール（継承関係の有無）」を一瞬混同したが、説明を受けて「継承関係にある場合はサブクラスを先に書く」という別軸のルールだと正しく整理し、正解した。

<a id="q21"></a>
## 問題21（骨太版：複数catch＋finally＋ループの複合トレース）

```java
public class Main {
    public static void main(String[] args) {
        String[] inputs = {"5", "abc", null};
        for (String s : inputs) {
            try {
                int len = s.length();
                int num = Integer.parseInt(s);
                System.out.println("OK:" + num);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("AIOOBE");
            } catch (NumberFormatException e) {
                System.out.println("NFE");
            } catch (RuntimeException e) {
                System.out.println("RE:" + e.getClass().getSimpleName());
            } finally {
                System.out.print("- ");
            }
        }
    }
}
```

次のプログラムをコンパイル、実行するとどうなりますか。（1つ選択）

A.
```
OK:5
- NFE
- RE:NullPointerException
- 
```

B.
```
OK:5
- NFE
- AIOOBE
- 
```

C. `OK:5` と `- NFE` の後、`catch (RuntimeException e)` の行でコンパイルエラーになる

D. `OK:5` と `- NFE` の後、`NullPointerException`がどの`catch`にもマッチせず`main`まで伝播し、プログラムが異常終了する

E. `OK:5` のみ出力され、2周目以降は例外により即座にプログラムが終了する

**解答**

正解：**A**

**補足**

- `"5"`：`s.length()`（`null`でない）も`Integer.parseInt("5")`（正しい数字）も例外なし。**出力されるのは`len`ではなく`num`**（`len`は`.length()`の結果を持つだけで、実際に`println`されているのは`Integer.parseInt(s)`の結果である`num`）なので`OK:5`。
- `"abc"`：`Integer.parseInt("abc")`が失敗し`NumberFormatException`→2番目の`catch`でキャッチ→`NFE`。
- `null`：`s.length()`（`null`の状態でメンバアクセス）で`NullPointerException`。これは`ArrayIndexOutOfBoundsException`にも`NumberFormatException`にも一致しない兄弟関係の例外だが、`NullPointerException`は`RuntimeException`の直接の子なので、一番広い最後の`catch (RuntimeException e)`が受け皿として拾う→`RE:NullPointerException`。
- `catch`の並び（`ArrayIndexOutOfBoundsException`→`NumberFormatException`→`RuntimeException`）はコンパイルエラーにならない。最初の2つは互いに無関係な兄弟同士で順不同、`RuntimeException`は両方の先祖だが**一番最後（catch-all）に置くのは常にOK**というルール（Cは誤り）。
- 各ループで`finally`が必ず実行されるため、`try`/`catch`の出力の後には毎回`- `が続く（`javac`/`java`で検証済み）。

**実施記録**

迷ったところ：`s.length()`が使われず`Integer.parseInt(s)`の結果（`num`）の方が出力される点に一瞬混乱したが、コード読解の質問を経て正しく理解。3つの入力それぞれのトレース（`OK:5`／`NFE`／`RE:NullPointerException`、いずれも`finally`込み）を自力で正確に組み立てた上でAを選択し、正解。

<a id="q17-20"></a>
## 問題17〜20（例外クラスの継承チェーン暗記ドリル）

「継承の矢印を丸暗記したい」というユーザーの要望で出題した、継承チェーン確認用のセット。表7-2〜7-4の9クラス＋補助クラスの継承チェーンを`javac`のリフレクションで検証：

```
FileNotFoundException -> IOException -> Exception -> Throwable
IOException -> Exception -> Throwable
ClassNotFoundException -> ReflectiveOperationException -> Exception -> Throwable
ArithmeticException -> RuntimeException -> Exception -> Throwable
ClassCastException -> RuntimeException -> Exception -> Throwable
NullPointerException -> RuntimeException -> Exception -> Throwable
ArrayIndexOutOfBoundsException -> IndexOutOfBoundsException -> RuntimeException -> Exception -> Throwable
NumberFormatException -> IllegalArgumentException -> RuntimeException -> Exception -> Throwable
NoClassDefFoundError -> LinkageError -> Error -> Throwable
StackOverflowError -> VirtualMachineError -> Error -> Throwable
OutOfMemoryError -> VirtualMachineError -> Error -> Throwable
```

表7-2〜7-4に載っている9クラスの中で、お互いに直接の親子関係にあるのは`FileNotFoundException`/`IOException`のペアのみ（他は全て兄弟同士）。

### 問題17

次のうち、`IndexOutOfBoundsException`のサブクラスはどれですか。（1つ選択）

A. `ArithmeticException`　B. `ArrayIndexOutOfBoundsException`　C. `ClassCastException`　D. `NullPointerException`　E. `NumberFormatException`

正解：**B**　ユーザー解答：B（正解）

### 問題18

次のうち、お互いに直接の親子関係にあるペアはどれですか。（1つ選択）

A. `ArithmeticException`と`ClassCastException`　B. `NumberFormatException`と`ArrayIndexOutOfBoundsException`　C. `FileNotFoundException`と`IOException`　D. `StackOverflowError`と`OutOfMemoryError`　E. `NullPointerException`と`ClassNotFoundException`

正解：**C**　ユーザー解答：C（正解）

### 問題19

```java
public class Main {
    public static void main(String[] args) {
        try {
            int[] arr = {};
            System.out.println(arr[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("A");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("B");
        }
    }
}
```

このプログラムをコンパイルするとどうなりますか。（1つ選択）→正解：**C**（`catch (ArrayIndexOutOfBoundsException e)`の行でコンパイルエラー。`javac`で確認：「例外ArrayIndexOutOfBoundsExceptionはすでに捕捉されています」）　ユーザー解答：C（正解）。「エラーになるのはサブクラス側（下の行）」という理解を自分の言葉で正しく確認した。

### 問題20

```java
public class Main {
    public static void main(String[] args) {
        try {
            String s = "abc";
            int x = Integer.parseInt(s);
        } catch (IllegalArgumentException e) {
            System.out.println("caught: " + e.getClass().getSimpleName());
        }
    }
}
```

このプログラムをコンパイル、実行するとどうなりますか。（1つ選択）→正解：**C**（`caught: NumberFormatException`。`NumberFormatException`は`IllegalArgumentException`のサブクラスなので、単独の`catch (IllegalArgumentException e)`で普通に捕まる。`getClass().getSimpleName()`は`catch`の宣言型ではなく実際の例外オブジェクトの型を返すので`NumberFormatException`になる。`javac`/`java`で検証済み）　ユーザー解答：**A（誤り）**。「`NumberFormatException`を明示的にキャッチしていないのでコンパイルエラー」という誤解。問題19（catchが2つあって順序が逆＝コンパイルエラー）とのルールの違いを混同していた——**catchが1つしかない場合は「下の行が無駄になる」という状況自体が発生しないため、コンパイルエラーにはならない**という違いを訂正して説明した。

**実施記録（17〜20まとめ）**

17〜19は一発正解、20は「supertypeでcatchすると常にコンパイルエラーになる」という問題19からの誤った一般化で不正解。catchブロックが1つだけの場合と2つ以上ある場合でルールの適用対象が違う、という点を訂正済み。

<a id="q22"></a>
## 問題22（2.3 multi-catch：catch変数は暗黙的にfinal＝再代入禁止）

```java
public class Main {
    public static void main(String[] args) {
        try {
            String s = args.length > 0 ? args[0] : null;
            int x = Integer.parseInt(s);
        } catch (NullPointerException | NumberFormatException e) {
            e = new RuntimeException("replaced");
            System.out.println(e.getMessage());
        }
    }
}
```

このプログラムをコンパイルするとどうなりますか。（1つ選択）

A. `replaced` が出力される
B. `e = new RuntimeException("replaced");` の行でコンパイルエラーになる
C. `catch (NullPointerException | NumberFormatException e)` の宣言自体がコンパイルエラーになる（`NullPointerException`と`NumberFormatException`が無関係のため）
D. 実行時に `ClassCastException` がスローされる
E. 何も出力されずに正常終了する

**解答**

正解：**B**

**補足**

- multi-catch（`catch (TypeA | TypeB e)`）で受け取った例外変数は**暗黙的に`final`**として扱われる。通常の単一`catch`と異なり、この変数への再代入は明示的に禁止されている。
- `javac`で確認したエラーメッセージ：「複数catchパラメータeに値を代入することはできません」。
- `NullPointerException`と`NumberFormatException`はお互い無関係の兄弟同士なので、multi-catchで組み合わせること自体は問題ない（Cは誤り。継承関係にある型同士はmulti-catchで組み合わせられないというルールはあるが、この2つはそれに該当しない）。
- 実行時に何が渡されるか（コマンドライン引数の有無）は今回無関係。これはコンパイルエラーなので、実行される前の段階で失敗する。

**実施記録**

迷ったところ：ユーザーから「引数に何が渡る予定か」という質問があったが、これは実行時の話でありコンパイルエラーには無関係と回答。その後の補足説明で、コンパイルエラーの原因（catch変数への再代入）に直接言及してしまい、実質的に答えを明かす形になった（本来はNGな進行だが、ユーザー自身が気づいて指摘）。ユーザーが理由を尋ねたため、そのまま解説して正解Bを確定。

<a id="q23"></a>
## 問題23（継承チェーン11クラス：状況→例外/エラークラス名の対応ドリル）

11クラスの継承チェーン（`javac`のリフレクションで検証済み）をユーザーが自力で書き出して確認した後、その知識を使って「状況→例外/エラー名」を当てる形式で出題。

```java
① int[] arr = new int[3];
   System.out.println(arr[5]);

② String s = null;
   s.length();

③ Object o = "hello";
   Integer i = (Integer) o;

④ int x = 10 / 0;

⑤ Class.forName("com.example.NotExist");

⑥ void recurse() { recurse(); }
   recurse();  // 呼び出し続けた結果

⑦ Integer.parseInt("abc");

⑧ new FileInputStream("not_exist.txt");
   // ファイルが存在しない場合
```

①〜⑧それぞれで発生する例外/エラークラス名を答える。

**解答**

正解：① `ArrayIndexOutOfBoundsException` ② `NullPointerException` ③ `ClassCastException` ④ `ArithmeticException` ⑤ `ClassNotFoundException` ⑥ `StackOverflowError` ⑦ `NumberFormatException` ⑧ `FileNotFoundException`

**補足**

- 11クラスの継承チェーンをユーザーが自力で書き出し、`javac`のリフレクション（`getSuperclass()`ループ）で全11個とも完全一致を確認済み。
- 併せて、マルチキャッチ（`A | B`）は無関係な兄弟要素同士でしか使えず、継承関係のある型（例：`IOException | FileNotFoundException`）を組み合わせると「複数catch文の代替をサブクラス化によって関連付けることはできません」というコンパイルエラーになることを`javac`で実証済み。逆に、catchブロックを下に重ねていく書き方（別々のcatch）は継承関係のある例外にこそ必要な技法（サブクラスを上、スーパークラスを下）であり、両者の使い分けが整理できた。

**実施記録**

迷ったところ：なし（8/8全問正解）。

<a id="ref-summary"></a>
## 参考資料：例外/エラークラスの和訳・発生状況まとめ

> [!IMPORTANT]
> ### チェック例外（Exception系）
>
> | クラス | 種別 | どういう時に発生するか |
> |---|---|---|
> | `IOException` | チェック | ファイルやネットワークなど、入出力（I/O）処理が失敗したときに発生。「Input/Output Exception」の略。 |
> | `FileNotFoundException` | チェック（`IOException`のサブクラス） | 指定したパスにファイルが存在しないときに発生。ファイルを開こうとして「そもそも無い」場合。 |
> | `ClassNotFoundException` | チェック | 文字列で指定したクラス名（`Class.forName("...")`など）で、該当するクラスをロードできないときに発生。 |
>
> ### 非チェック例外（RuntimeException系）
>
> | クラス | どういう時に発生するか |
> |---|---|
> | `NullPointerException` | `null`が入っている変数に対して、メンバ（メソッドやフィールド）にアクセスしようとしたときに発生。 |
> | `ArrayIndexOutOfBoundsException` | 配列の範囲外のインデックスにアクセスしたときに発生（例：長さ3の配列に`arr[5]`でアクセス）。 |
> | `ClassCastException` | 参照型のキャストが無効なとき（実際の型と互換性のない型へキャストしようとしたとき）に発生。 |
> | `NumberFormatException` | 文字列を数値に変換できないときに発生（例：`Integer.parseInt("abc")`）。 |
> | `ArithmeticException` | 整数をゼロで割った場合など、算術的に不正な演算をしたときに発生（`10 / 0`。`10.0 / 0`は例外にならず`Infinity`になる点に注意）。 |
>
> ### Error系（非チェック）
>
> | クラス | どういう時に発生するか |
> |---|---|
> | `NoClassDefFoundError` | コンパイル時には存在したクラスが、実行時にロードできなかったときに発生。 |
> | `StackOverflowError` | 再帰呼び出しなどが深くなりすぎて、呼び出しスタックの上限を超えたときに発生。 |
> | `OutOfMemoryError` | プログラムの実行に必要なメモリ（ヒープなど）を確保できなかったときに発生。 |
>
> ### 参考：継承チェーン上の中間クラス（表7-2〜7-4には無いが、catchの型として登場しうる）
>
> | クラス | 何のサブクラスか | 意味 |
> |---|---|---|
> | `IndexOutOfBoundsException` | `RuntimeException` | 「範囲外アクセス」全般を表す親。`ArrayIndexOutOfBoundsException`はこの一種。 |
> | `IllegalArgumentException` | `RuntimeException` | メソッドに不正な引数が渡されたことを表す親。`NumberFormatException`はこの一種。 |
> | `ReflectiveOperationException` | `Exception` | リフレクション関連の操作失敗を表す親。`ClassNotFoundException`はこの一種。 |
> | `LinkageError` | `Error` | クラス間の依存関係・リンクの問題を表す親。`NoClassDefFoundError`はこの一種。 |
> | `VirtualMachineError` | `Error` | JVM自体の異常を表す親。`StackOverflowError`・`OutOfMemoryError`はこの一種。 |
>
> ### 参考：全体の形と覚え方のコツ（2026-08-25追記）
>
> 11本の鎖をバラバラに覚えるのではなく、木構造の分岐点として覚える。
>
> ```
> Throwable
> ├─ Exception（チェック例外の起点）
> │   ├─ IOException ── FileNotFoundException
> │   ├─ ReflectiveOperationException ── ClassNotFoundException
> │   └─ RuntimeException（非チェック）
> │       ├─ ArithmeticException
> │       ├─ ClassCastException
> │       ├─ NullPointerException
> │       ├─ IndexOutOfBoundsException ── ArrayIndexOutOfBoundsException
> │       └─ IllegalArgumentException ── NumberFormatException
> └─ Error（非チェック）
>     ├─ LinkageError ── NoClassDefFoundError
>     └─ VirtualMachineError ── StackOverflowError / OutOfMemoryError
> ```
>
> **コツ①：途中で分岐する「hub」は6個だけ** — `IOException` / `ReflectiveOperationException` / `IndexOutOfBoundsException` / `IllegalArgumentException` / `LinkageError` / `VirtualMachineError`。これさえ覚えれば残りは全部この6個か`RuntimeException`/`Error`に直結する末端。
>
> **コツ②：末端の名前は親の意味をそのまま含んでいることが多い** — `ArrayIndexOutOfBoundsException`は名前に"IndexOutOfBounds"が入っているので親が分かる。`NoClassDefFoundError`は「クラス定義が見つからない」＝リンクの失敗→`LinkageError`。
>
> **コツ③：意味でグループ化して短文にする**
> - IO系：「ファイルが見つからない(`FileNotFoundException`)のはIOの話」
> - リフレクション系：「クラスが見つからない(`ClassNotFoundException`)のはリフレクションの話」（`Class.forName("文字列")`でクラス名を実行時に探す動作。文字列なのでコンパイル時にチェックできず、失敗しうるからチェック例外）
> - 引数系：「数値の書式がおかしい(`NumberFormatException`)＝不正な引数(`IllegalArgumentException`)の一種」
> - VM系：「VMが死ぬ原因は2つだけ：スタックが溢れる(`StackOverflowError`)か、メモリが尽きる(`OutOfMemoryError`)か」→どちらも`VirtualMachineError`
>
> **コツ④：チェック例外は実質4個（＋名前ルール）と組み合わせる** — `Error`系は名前に必ず"Error"が入るので非チェックと判別しやすい。残る"...Exception"のうち、チェック例外なのは`IOException`/`FileNotFoundException`/`ReflectiveOperationException`/`ClassNotFoundException`（＝IO系とリフレクション系の2ペアのみ）。それ以外の"...Exception"（`RuntimeException`とその子孫）は全部非チェック。

<a id="q24"></a>
## 問題24（2.3 multi-catchの総合ドリル：兄弟OK／再代入不可／継承関係NG、5択複数選択）

`javac`で全選択肢を検証済み。

```java
A. catch (NullPointerException | ArithmeticException e) { System.out.println("ok"); }
B. catch (IOException | FileNotFoundException e) { System.out.println("ok"); }
C. catch (NumberFormatException | ClassCastException e) { e.printStackTrace(); }
D. catch (ArithmeticException | NullPointerException e) { e = new RuntimeException(); }
E. catch (Exception | RuntimeException e) { System.out.println("ok"); }
```

コンパイルエラーになるものをすべて選ぶ形式（複数選択）。

**解答**

正解：**B, D, E**（A, Cはコンパイル通る）

**補足**

- A：無関係な兄弟同士＋再代入なし→OK。C：無関係な兄弟同士＋`e.printStackTrace()`は読み取りで再代入ではない→OK。
- B：`IOException`/`FileNotFoundException`は継承関係→エラー。D：型の組み合わせはOKだが`e = new RuntimeException()`で再代入→エラー。E：`RuntimeException`は`Exception`のサブクラス→エラー。
- 実用的な見分け方：表7-2〜7-4の11クラスの中で互いに継承関係にあるのは`FileNotFoundException`/`IOException`のペアのみ（問題16参照）。それ以外の9クラス同士の組み合わせは兄弟なので型の面では常にOK、`Exception`/`RuntimeException`/`Throwable`が絡む場合は名前から継承関係が明らかなので判別しやすい。

**実施記録**

ユーザーが「コンパイルエラーになるものを選ぶ」という設問文を読み違え、「OKなものを選ぶ」つもりでA, C, Dと回答（実質不正解：D以外は逆）。設問の向きを取り違えていたと自己申告があり、同形式で問題25を再出題。

<a id="q25"></a>
## 問題25（2.3 multi-catchの総合ドリル：仕切り直し版）

`javac`で全選択肢を検証済み。

```java
A. catch (ClassCastException | NullPointerException e) { System.out.println(e.getMessage()); }
B. catch (ArithmeticException e) { e = new ArithmeticException("re"); System.out.println(e); }
C. catch (RuntimeException | NumberFormatException e) { System.out.println("ok"); }
D. catch (NoClassDefFoundError | StackOverflowError e) { e = null; }
E. catch (Throwable | Exception e) { System.out.println("ok"); }
```

コンパイルエラーになるものをすべて選ぶ形式（複数選択）。

**解答**

正解：**C, D, E**（A, Bはコンパイル通る）

**補足**

- A：無関係な兄弟同士＋再代入なし→OK。B：**単一のcatch**（multi-catchではない）なので、変数`e`はfinalではなく再代入自由→OK（このコントラストが問題24のDとの対比ポイント）。
- C：`NumberFormatException`は`RuntimeException`のサブクラス（`IllegalArgumentException`経由）→エラー。D：型の組み合わせ（`NoClassDefFoundError`/`StackOverflowError`、共にErrorの兄弟）はOKだが`e = null`で再代入→エラー。E：`Exception`は`Throwable`のサブクラス→エラー。

**実施記録**

迷ったところ：なし。問題24での設問読み違えを自己修正した上で、C, D, E全て一発正解。

<a id="ref-trywithresources"></a>
## 3.1 try-with-resources 要点整理

> [!IMPORTANT]
> 1. **使えるリソースは`AutoCloseable`実装クラスのみ**（`Closeable`は`AutoCloseable`のサブインタフェース）。
> 2. 構文：`try (リソース1; リソース2; ...) { }` — `;`区切りで複数指定可。最後の`;`は省略可。
> 3. **クローズは宣言と逆順**（`fw1; fw2` と書けば、閉じるのは`fw2`→`fw1`）。
> 4. `AutoCloseable.close()`は`Exception`、`Closeable.close()`は`IOException`をスローしうる → 通常は例外処理が必要。メソッド名は`close()`で固定（このメソッドをオーバーライドしないと自動クローズの対象にならない）。
> 5. `var`はリソース宣言では使えるが、**`catch (var e)`は不可**（コンパイルエラー：「'var'はここでは許可されません」）。
> 6. **Java 9以降**：事前に宣言済みの変数をそのまま`try(fw)`に渡せる。ただしその変数は**渡す前の時点ですでにfinalまたは実質的final**である必要がある（渡す前に1回でも再代入していると「変数fw1が、finalでも事実上のfinalでもありません」というエラー）。
> 7. try内で使うリソース変数（事前宣言・新規宣言どちらも）は実質的final扱いになり、**try/finallyブロック内での再代入は禁止**（＝生成されてからtry()で使われ終わるまでの間、一度も再代入されてはいけない、という1本のルール）。
> 8. 通常のtry文と違い、**try-with-resourcesは`catch`も`finally`も省略してtryブロックのみで成立**する（`throws`宣言で伝播させればよい）。
> 9. 複数リソースを宣言した場合、初期化（コンストラクタ呼び出し）は**宣言順（左から右）に1つずつ**行われる。途中のリソースのコンストラクタが例外をスローした場合、**それ以降のリソースは初期化されず**、tryブロック本体にも到達しない。**その時点までに正常にオープンできていたリソースだけ**が自動クローズの対象になる（問題27参照）。

<a id="q26"></a>
## 問題26（3.1 try-with-resources：要点＋エッジケースの正誤判定、5択複数選択）

`javac`で全選択肢を検証済み（`chap7/9/Main.java`のmethodA〜Dが実例）。

```java
A. try-with-resourcesで使えるリソースは、AutoCloseableインタフェースを実装したクラスに限られる。
B. 複数のリソースを指定した場合、クローズは宣言した順序と同じ順番で行われる。
C. try()内のリソース宣言にはvarによる型推論が使えるが、catchブロックの例外変数にはvarを使えない。
D. Java 9以降では、事前に宣言した変数をtry()に指定できるが、その変数がtry()に渡す前の時点で再代入されている（finalでも実質的finalでもない）場合はコンパイルエラーになる。
E. try-with-resourcesでは、catchまたはfinallyのいずれかを必ず記述しなければならず、省略するとコンパイルエラーになる。
```

正しい記述をすべて選ぶ形式（複数選択）。

**解答**

正解：**A, C, D**（B, Eは誤り）

**補足**

- A：正しい。`AutoCloseable`（または`Closeable`）実装クラスのみ使用可。
- B：誤り。クローズは**宣言と逆順**（`javac`/`java`で検証：`A;B;C`の順で宣言→クローズは`C,B,A`の順）。
- C：正しい。リソース宣言に`var`は使えるが、`catch (var e)`はコンパイルエラー（「'var'はここでは許可されません」）。
- D：正しい。`try()`に渡す**前**に再代入していると「変数fw1が、finalでも事実上のfinalでもありません」というエラー。`chap7/9/Main.java`の`methodC`（再代入なしでそのまま渡す）との対比がポイント。ラムダ式の実質的finalルールと同じ考え方。
- E：誤り。try-with-resourcesは**tryブロックのみで成立可能**（`methodC`・`methodD`のようにcatch/finally省略可、チェック例外は`throws`で伝播させればよい）。`chap7/9/Main.java`自体`javac`でコンパイル成功済み。

**実施記録**

ユーザー解答：A, C（Dを見落とし、不正解）。「try()に渡す前の再代入」というDのエッジケースを見落とした。Dの実例（再代入後に`try(fw1)`するとコンパイルエラー）を`javac`で示して訂正。あわせて「AutoCloseableを持つリソースとはFileWriterのようなクラスか」という確認質問に回答（BufferedReader・Scanner・InputStream/OutputStream系、自作AutoCloseable実装クラスも該当）。

補足：ユーザーが`chap7/9/Main.java`の`methodD`を自分で`fw1 = new FileWriter("d.txt");`と`try(fw1)`の間に再代入を挟む形に書き換えて選択し、「これがダメってことですか？」と質問。`javac`で実際にコンパイルエラー（「変数fw1が、finalでも事実上のfinalでもありません」）になることを確認して回答。ユーザーは「中（tryブロック内）でも外（try()に渡す前）でも再代入できない」という理解を自分の言葉で正しくまとめた。

<a id="q27"></a>
## 問題27（3.1 try-with-resourcesの応用：リソースのオープン自体が失敗した場合のクローズ挙動）

`javac`/`java`で実行結果を検証済み。

```java
class Res implements AutoCloseable {
    String name;
    Res(String n, boolean fail) {
        this.name = n;
        if (fail) throw new RuntimeException("open fail:" + n);
        System.out.println("open:" + n);
    }
    public void close() { System.out.println("close:" + name); }
}

public class Main {
    public static void main(String[] args) {
        try (Res a = new Res("A", false); Res b = new Res("B", true); Res c = new Res("C", false)) {
            System.out.println("body");
        } catch (RuntimeException e) {
            System.out.println("caught:" + e.getMessage());
        }
    }
}
```

次のプログラムを実行するとどうなりますか。（1つ選択）

A.
```
open:A
open:B
close:B
close:A
caught:open fail:B
```

B.
```
open:A
close:A
caught:open fail:B
```

C.
```
open:A
body
close:A
caught:open fail:B
```

D. `b`のコンストラクタで例外がスローされるが、`a`はまだ`close()`されていないため、`close()`されないままプログラムが終了する

E.
```
open:A
open:B
open:C
close:C
close:B
close:A
body
```

**解答**

正解：**B**

**補足**

- リソースは`try()`内の宣言順（左から右）に、1つずつコンストラクタが呼ばれて初期化される。
- `a`は正常にオープン（`open:A`）。`b`のコンストラクタは`fail=true`のため、`open:B`を出力する前に`RuntimeException`をスローする＝**bは一度もオープンされていない**。
- `b`の初期化中に例外が起きたため、`c`の初期化には到達せず、tryブロック本体（`body`）にも到達しない。
- **オープンに成功したリソースだけが自動クローズの対象**になる。よってクローズされるのは`a`のみ（`close:A`）。Aの選択肢のように未オープンの`b`が`close()`されることはない。
- `RuntimeException`が伝播し`catch`でキャッチ→`caught:open fail:B`。

**実施記録**

迷ったところ：ユーザーから「複数リソースが連続で宣言されている場合、初期化はどう流れるか」「closeは必ず呼ばれるのか」「close()という名前は決まっているのか」という3つの確認質問があり、いずれも一般論の範囲で回答（今回のひねり＝オープン失敗時のクローズ対象については触れずに解答前の説明を留めた）。その上でB「open:A → close:A → caught:open fail:B」という正確なトレースを自力で組み立てて正解。

<a id="q28"></a>
## 問題28（発展編：tryブロック本体とclose()の両方が例外を投げた場合＝抑制された例外／suppressed exception）

`javac`/`java`で実行結果を検証済み。

```java
class Res implements AutoCloseable {
    public void close() throws Exception {
        System.out.println("closing");
        throw new IllegalStateException("close failed");
    }
}

public class Main {
    public static void main(String[] args) {
        try (Res r = new Res()) {
            System.out.println("body");
            throw new RuntimeException("body failed");
        } catch (Exception e) {
            System.out.println("caught:" + e.getMessage());
            for (Throwable t : e.getSuppressed()) {
                System.out.println("suppressed:" + t.getMessage());
            }
        }
    }
}
```

次のプログラムを実行するとどうなりますか。（1つ選択）

A.
```
body
closing
caught:body failed
suppressed:close failed
```

B.
```
body
closing
caught:close failed
```

C.
```
body
closing
caught:close failed
suppressed:body failed
```

D. `body`のみが出力され、`close()`は呼ばれない

E. `closing`が先に出力され、`body`は実行されない

**解答**

正解：**A**

**補足**

- `try`本体で`RuntimeException("body failed")`がスローされた後、tryブロックを抜ける際に`close()`が呼ばれ、そこでも`IllegalStateException("close failed")`がスローされる。
- **すでに主例外（try本体の例外）が存在する場合、`close()`が投げた例外はそれを上書きせず、主例外に「抑制された例外（suppressed exception）」として付加される**（`Throwable.addSuppressed()` / `getSuppressed()`）。伝播するのはあくまで`body failed`の方。
- `catch`で受け取るのは`RuntimeException("body failed")`なので`caught:body failed`。`e.getSuppressed()`で`close failed`も取得できる（`suppressed:close failed`）。
- 対比：もし`try`本体が例外を投げず`close()`だけが例外を投げた場合は、その`close()`の例外がそのまま主例外として`catch`に届く（Bのパターン）。

**実施記録**

迷ったところ：なし（一発正解）。

---

<a id="q29"></a>
## 問題29

```java
class Res implements AutoCloseable {
    String name;
    boolean failClose;
    Res(String n, boolean failOpen, boolean failClose) {
        this.name = n;
        this.failClose = failClose;
        if (failOpen) throw new RuntimeException("open fail:" + n);
        System.out.println("open:" + n);
    }
    public void close() {
        System.out.println("closing:" + name);
        if (failClose) throw new RuntimeException("close fail:" + name);
    }
}

public class Main {
    public static void main(String[] args) {
        try (Res a = new Res("A", false, false);
             Res b = new Res("B", false, true);
             Res c = new Res("C", true, false)) {
            System.out.println("body");
        } catch (RuntimeException e) {
            System.out.println("caught:" + e.getMessage());
            for (Throwable s : e.getSuppressed()) {
                System.out.println("suppressed:" + s.getMessage());
            }
        }
    }
}
```

`Res(名前, オープン時に失敗するか, クローズ時に失敗するか)`。実行結果として正しいものを選んでください。（1つ選択）

A.
```
open:A
open:B
closing:B
closing:A
caught:open fail:C
suppressed:close fail:B
```

B.
```
open:A
open:B
closing:B
closing:A
caught:close fail:B
suppressed:open fail:C
```

C.
```
open:A
open:B
closing:B
caught:open fail:C
suppressed:close fail:B
```

D.
```
open:A
open:B
closing:A
closing:B
caught:open fail:C
suppressed:close fail:B
```

E.
```
open:A
open:B
closing:B
closing:A
caught:open fail:C
```

**解答**

正解：**A**

**補足**

- 問題27（部分オープン失敗）と問題28（close()も例外を投げる）の合体パターン。
- A, Bはオープン成功。Cのコンストラクタで例外（`open fail:C`）→ この時点で**Cはopen扱いにならず、close対象にもならない**。
- 巻き戻しは「成功して開いた分だけ、開いた順の逆」＝B→A。Bのclose()が例外を投げても、**Aのclose()は独立して必ず実行される**（Bのclose失敗が他のcloseをスキップさせることはない）。
- 主例外は常に**最初に発生した例外**（`open fail:C`）。close()側の例外（`close fail:B`）は主例外を上書きせず、`addSuppressed()`で付加されるだけ。

**実施記録**

自力でトレースを最初から最後まで正確に導出し、正解Aで一発正解。唯一の質問は`e.getSuppressed()`のforループの意味（ぶら下がっている例外の数だけ回る、0個なら丸ごとスキップ）で、これは回答後のフォローアップとして回答。

<a id="q30"></a>
## 問題30（3.1 try-with-resources：問題27の改変・未到達リソースの明示化）

問題27（部分オープン失敗）を4リソース構成に変えたバリエーション。close()側の例外は絡めず、「オープン失敗後に控えているリソースは、そもそもコンストラクタすら呼ばれない」点を単独で強調した。

```java
public class Main {
    static class Plug implements AutoCloseable {
        String name;
        Plug(String name, boolean fail) {
            this.name = name;
            System.out.print("open:" + name + " ");
            if (fail) {
                throw new RuntimeException("open fail:" + name);
            }
        }
        @Override
        public void close() {
            System.out.print("close:" + name + " ");
        }
    }

    public static void main(String[] args) {
        try (Plug p1 = new Plug("P1", false);
             Plug p2 = new Plug("P2", false);
             Plug p3 = new Plug("P3", true);
             Plug p4 = new Plug("P4", false)) {
            System.out.print("body ");
        } catch (RuntimeException e) {
            System.out.print("caught:" + e.getMessage());
        }
    }
}
```

このコードを実行した結果として正しいものを選んでください。

A.
```
open:P1 open:P2 open:P3 close:P2 close:P1 caught:open fail:P3
```

B.
```
open:P1 open:P2 open:P3 open:P4 close:P2 close:P1 caught:open fail:P3
```

C.
```
open:P1 open:P2 open:P3 close:P1 close:P2 caught:open fail:P3
```

D.
```
open:P1 open:P2 open:P3 close:P3 close:P2 close:P1 caught:open fail:P3
```

E.
```
open:P1 open:P2 open:P3 body close:P2 close:P1 caught:open fail:P3
```

**解答**

正解：**A**

**補足**

- P1, P2はopen成功。P3のコンストラクタが例外を投げ、この時点で**P3はopen扱いにならない**ため、以降のリソース初期化（P4）は評価自体が行われない（open:P4は出力されない）。
- クローズは「成功して開いた分だけ、開いた順の逆」＝P2→P1。
- try本体は一度も実行されない（bodyは出力されない）。

**実施記録**

トレース自体（open:P1 open:P2 open:P3 close:P2 close:P1 caught:open fail:P3）は最初から正確に導出できていたが、回答時に選んだ記号が「B」で、自分のトレース内容（A相当）と食い違っていた。中身の理解は正しく、記号選択のミスと判断。

<a id="q31"></a>
## 問題31（3.1 try-with-resources：問題28の改変・suppressedが複数溜まるケース）

問題28（tryとclose()の両方が例外を投げる、リソース1つ）をリソース2つに拡張し、close()側の例外が2つsuppressedとして溜まるパターンにした。

```java
public class Main {
    static class Plug implements AutoCloseable {
        String name;
        boolean failClose;
        Plug(String name, boolean failClose) {
            this.name = name;
            this.failClose = failClose;
            System.out.print("open:" + name + " ");
        }
        @Override
        public void close() {
            System.out.print("close:" + name + " ");
            if (failClose) {
                throw new IllegalStateException("close fail:" + name);
            }
        }
    }

    public static void main(String[] args) {
        try (Plug p1 = new Plug("P1", true);
             Plug p2 = new Plug("P2", true)) {
            throw new RuntimeException("body fail");
        } catch (RuntimeException e) {
            System.out.print("caught:" + e.getMessage() + " ");
            for (Throwable s : e.getSuppressed()) {
                System.out.print("suppressed:" + s.getMessage() + " ");
            }
        }
    }
}
```

このコードを実行した結果として正しいものを選んでください。

A.
```
open:P1 open:P2 close:P2 close:P1 caught:body fail suppressed:close fail:P2 suppressed:close fail:P1
```

B.
```
open:P1 open:P2 close:P2 close:P1 caught:body fail suppressed:close fail:P1 suppressed:close fail:P2
```

C.
```
open:P1 open:P2 close:P2 close:P1 caught:body fail suppressed:close fail:P2
```

D.
```
open:P1 open:P2 close:P2 close:P1 caught:close fail:P2 suppressed:body fail suppressed:close fail:P1
```

E.
```
open:P1 open:P2 close:P1 close:P2 caught:body fail suppressed:close fail:P1 suppressed:close fail:P2
```

**解答**

正解：**A**

**補足**

- 主例外は常に**最初に発生した例外**（tryボディの`body fail`）。close()側の例外はどちらも主例外を上書きせず、`addSuppressed()`で付加されるだけ。
- close()はオープンの逆順（P2→P1）で実行される。両方とも例外を投げるが、**片方のclose失敗がもう片方のcloseをスキップさせることはない**（P1のcloseも独立して必ず実行される）。
- `getSuppressed()`の並び順は宣言順でもopen順でもなく、**close実行時に例外が実際に発生した順**＝P2→P1。

**実施記録**

迷ったところ：なし。トレースを完全に自力で導出し一発正解。回答後、suppressedの並び順の根拠（先に例外が発生した方が先に入るのか）を確認する質問があり、close実行順＝発生順がそのまま反映される旨を回答。

<a id="q32"></a>
## 問題32（3.1 try-with-resources：finallyを組み込んだパターン）

これまでの try-with-resources 問題（27〜31）はcatchのみでfinallyが無かったため、finallyを追加して実行順を確認する問題。`sample/chap7/12/Main.java`（close()自体が例外を投げるケース）を見た流れで作成。

```java
public class Main {
    static class Item implements AutoCloseable {
        String name;
        boolean fail;
        Item(String name, boolean fail) {
            this.name = name;
            this.fail = fail;
            System.out.print("open:" + name + " ");
            if (fail) {
                throw new RuntimeException("open fail:" + name);
            }
        }
        @Override
        public void close() {
            System.out.print("close:" + name + " ");
        }
    }

    public static void main(String[] args) {
        try (Item i1 = new Item("I1", false);
             Item i2 = new Item("I2", true)) {
            System.out.print("body ");
        } catch (RuntimeException e) {
            System.out.print("caught:" + e.getMessage() + " ");
        } finally {
            System.out.print("finally");
        }
    }
}
```

このコードを実行した結果として正しいものを選んでください。

A.
```
open:I1 open:I2 close:I1 caught:open fail:I2 finally
```

B.
```
open:I1 open:I2 close:I1 finally caught:open fail:I2
```

C.
```
open:I1 open:I2 body close:I1 caught:open fail:I2 finally
```

D.
```
open:I1 open:I2 close:I1 close:I2 caught:open fail:I2 finally
```

E.
```
open:I1 open:I2 finally
```

**解答**

正解：**A**

**補足**

- I2のコンストラクタで例外→I2はopen扱いにならない。close対象は成功して開いたI1のみ。
- try本体は一度も実行されない（bodyは出力されない）。
- **finallyはcatchブロックの処理が終わった後、一番最後に実行される**（try-with-resourcesのclose()を含めても、この順序自体は通常のtry-catch-finallyと変わらない）。

**実施記録**

迷ったところ：なし。直前に`sample/chap7/12/Main.java`（close()自体が例外を投げてcatch→finallyの順になるケース）を自分で実行して確認した流れで、finally入りのtry-with-resourcesを試したいと自分から要望し、一発正解。

<a id="q33"></a>
## 問題33（4.1 throws：コードベースのコンパイルエラー特定問題）

`javac`で検証済み。

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        try {
            obj.stepA();
        } catch (Exception e) {
            System.out.println("caught A:" + e.getMessage());
        }
        try {
            obj.stepC();
        } catch (IOException e) {
            System.out.println("caught C:" + e.getMessage());
        }
        obj.stepD();
    }

    public void stepA() throws IOException {
        stepB();
    }

    public void stepB() throws Exception {
        throw new IOException("from B");
    }

    public void stepC() throws IOException {
        try {
            stepB();
        } catch (Exception e) {
            throw new IOException("wrapped:" + e.getMessage());
        }
    }

    public void stepD() throws RuntimeException {
        System.out.println("D ok");
    }
}
```

このコードには1箇所だけコンパイルエラーがあります。どの行が原因か選んでください。（1つ選択）

A. `stepA()`内の `stepB();`
B. `stepB()`内の `throw new IOException("from B");`
C. `stepC()`の try ブロック内の `stepB();`
D. `main()`内の `obj.stepD();`（try-catchもthrowsも無いまま呼んでいる）
E. コンパイルエラーは無い（全て正しい）

**解答**

正解：**A**

**補足**

- `stepB()`は`throws Exception`（広い宣言）。`stepA()`は`throws IOException`（狭い宣言）で、`stepB()`をtry-catchせずそのまま呼んでいる。コンパイラは`stepB()`の中身（実際はIOExceptionしか投げていない）を見ず、**宣言された型（Exception）だけ**を見るため、「`stepA`の狭いthrows宣言では受け止めきれない」と判定してコンパイルエラーになる。`javac`のエラーメッセージも該当行を指す：「例外Exceptionは報告されません。スローするには、捕捉または宣言する必要があります」。
- `stepC()`は`stepB()`呼び出しを`catch (Exception e)`で囲んでいる（宣言と同じ広さで受け止めている）ので問題ない（Cは誤り）。
- `stepD()`は`throws RuntimeException`（非チェック例外）。非チェック例外は呼び出し元でのcatch/throwsが任意なので、`main()`でそのまま呼び出しても問題ない（Dは誤り）。

**実施記録**

ユーザー解答：D（誤り）。「`throws RuntimeException`の非チェック例外をtry-catchもthrowsも無いまま呼んでいる」ことがコンパイルエラーの原因だと誤解していたが、非チェック例外は無条件で自由に呼び出せるため実際は問題なし。真のエラー箇所は、狭いthrows宣言（`IOException`）で広いthrows宣言（`Exception`）のメソッドをcatchせずに呼んでいた`stepA()`だった。この誤答をきっかけに、catch/throwsの型指定と変数代入の互換性ルール（サブクラス→スーパークラスはOK、逆はNG）が同じ基準で判定されている、という整理を行った。

<a id="ref-throws"></a>
## 4.1 throws 要点整理

> [!IMPORTANT]
> - **throwsは例外処理のもう1つの方法**：try-catchが「その場で処理する」のに対し、throwsは「例外がスローされる可能性があるメソッド」に付けて、**処理を呼び出し元に委ねる**仕組み。
> - 構文：`戻り値の型 メソッド名(引数リスト) throws 例外クラスの型 {}`。`,`区切りで複数指定可。指定できるのは`Throwable`のサブクラス。
> - **main()にもthrows指定は可能**（コンパイルも通る）。ただしmain()の呼び出し元はJVMであり、実際に例外がスローされればそこでプログラムが終了するだけ。
> - **非チェック例外はthrows指定が任意**（`throws RuntimeException`と書いてもいい）。呼び出し元での処理も任意のまま。
> - **throwsには実際にスローする例外の「スーパークラス」も指定できる**（例：内部で`IOException`を投げるが、throwsには`Exception`と書く＝OK）。
> - 逆に、**呼び出し先のthrowsで宣言された型より狭い型しかthrowsしていない場合はコンパイルエラー**（呼び出し先が`throws Exception`なのに、呼び出し元が`throws IOException`だけでは不十分）。
> - メリット：複数メソッドで似たような例外処理を書く代わりに、throwsで委譲を連鎖させ、呼び出し元（例：main）で1箇所にまとめてtry-catchできる（図7-5のコード重複削減パターン）。

<a id="q34"></a>
## 問題34（4.1 throws：文章の正誤選択）

コードのトレースではなく、throwsの概念を文章の正誤選択形式で確認する問題（問題13と同じ形式）。問題33（コードベースのthrowsコンパイルエラー特定問題）は保留中のまま、別形式でこちらを先に出題。

throwsについて、次のA〜Eのうち正しい記述をすべて選んでください。

A. throwsは、例外が発生したその場で処理を書く方法ではなく、処理を呼び出し元に委ねるための仕組みである。

B. throwsに指定できる例外クラスは、そのメソッド内で実際にスローされる例外と完全に同じ型でなければならず、スーパークラスを指定することはできない。

C. 非チェック例外（RuntimeExceptionなど）は、throwsに指定してもしなくても、呼び出し元での例外処理は任意のままである。

D. main()メソッドにthrowsを指定することはできない。

E. あるメソッドAが、throwsにExceptionを指定した別のメソッドBを、try-catchで囲まずに呼び出す場合、メソッドA自身のthrowsにも、Bのthrowsと同等以上に広い例外を指定する必要がある。

**解答**

正解：**A, C, E**

**補足**

- B：throwsには実際にスローする例外の**スーパークラス**を指定できる（例：内部で`IOException`を投げるが、throwsには`Exception`と書いてもよい）。「完全に同じ型でなければならない」は誤り。
- D：main()にもthrowsは指定可能でコンパイルも通る。ただしmain()の呼び出し元はJVMのため、実際にスローされればそこでプログラムが終了するだけ。
- E：問題33で検証済みのルールを文章化したもの（呼び出し先のthrowsで宣言された型より狭い型しか呼び出し元がカバーしていない場合はコンパイルエラー）。

**実施記録**

迷ったところ：なし。A, C, Eで一発正解。

<a id="q35"></a>
## 問題35（4.1 throws：catch/throwsのスーパークラス指定パターン）

`javac`で検証済み。

```java
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Main obj = new Main();
        obj.taskA();
        obj.taskB();
        obj.taskC();
    }

    void fetchData() throws Exception {
        throw new SQLException("db down");
    }

    void taskA() {
        try {
            fetchData();
        } catch (Exception e) {
            System.out.println("A caught:" + e.getMessage());
        }
    }

    void taskB() {
        try {
            fetchData();
        } catch (SQLException e) {
            System.out.println("B caught:" + e.getMessage());
        }
    }

    void taskC() {
        try {
            fetchData();
        } catch (RuntimeException e) {
            System.out.println("C caught:" + e.getMessage());
        }
    }
}
```

どのメソッドが原因でコンパイルエラーになりますか。（2つ選択）

A. `taskA()`
B. `taskB()`
C. `taskC()`
D. `fetchData()`
E. コンパイルエラーは発生しない

**解答**

正解：**B, C**

**補足**

- `taskA()`：`catch (Exception e)`。`fetchData()`の宣言（`throws Exception`）と**同じ広さ**で受け止めているのでOK。
- `taskB()`：`catch (SQLException e)`。`fetchData()`は`throws Exception`（広い宣言）なのに、catchは`SQLException`（狭い）だけ。コンパイラは実際にスローされる型ではなく**宣言された型（Exception）**を基準に見るため、狭いcatchでは受け止めきれずエラーになる。
- `taskC()`：`catch (RuntimeException e)`。`RuntimeException`は`Exception`のサブクラスだが、`fetchData()`が宣言しているチェック例外`Exception`自体は`RuntimeException`ではないので、この方向のcatchは型として噛み合わず全くカバーできない。エラーになる。
- `fetchData()`自体（D）はどの呼び出し制約も破っておらず、`throws Exception`という宣言として正当。コンパイルエラーの原因はあくまで**呼び出し側**（taskB/taskC）のcatchが宣言に対して狭すぎること。

**実施記録**

ユーザー解答：C, D（部分的に誤り）。Cは正解だが、Bを見落とし、代わりにD（`fetchData()`自体）を選んだ。エラーの原因は宣言している側（`fetchData()`）ではなく、呼び出し側のcatch/throwsが宣言の広さと噛み合っているかどうかである点を訂正した。

<a id="q36"></a>
## 問題36（4.1 throws：catch/throwsのワイドニング判定・5パターン一括）

`javac`で検証済み。

```java
import java.io.IOException;

public class Main {
    void loadRaw() throws IOException {
        throw new IOException("raw fail");
    }

    void loadA() throws IOException {
        loadRaw();
    }

    void loadB() throws Exception {
        loadRaw();
    }

    void loadC() {
        try {
            loadRaw();
        } catch (IOException e) {
            System.out.println("C:" + e.getMessage());
        }
    }

    void loadD() {
        try {
            loadRaw();
        } catch (Exception e) {
            System.out.println("D:" + e.getMessage());
        }
    }

    void loadE() {
        try {
            loadRaw();
        } catch (RuntimeException e) {
            System.out.println("E:" + e.getMessage());
        }
    }
}
```

`loadA()`〜`loadE()`のうち、コンパイルエラーになるものをすべて選んでください。

A. `loadA()`
B. `loadB()`
C. `loadC()`
D. `loadD()`
E. `loadE()`

**解答**

正解：**E**

**補足**

- `loadA()`：`throws IOException`（`loadRaw()`と同じ広さ）で受け止めている。try-catchが無くてもOK——チェック例外は「catchする」か「throwsで宣言する」のどちらか一方で足りる（問題13参照）。
- `loadB()`：`throws Exception`（`loadRaw()`より広い）で受け止めている。より広い型で宣言するのもOK。
- `loadC()`：`catch (IOException e)`（同じ広さ）でOK。
- `loadD()`：`catch (Exception e)`（より広い）でOK。
- `loadE()`：`catch (RuntimeException e)`だけ、`loadRaw()`の宣言（`IOException`、チェック例外）とワイドニングの関係にならない（`RuntimeException`は`IOException`の継承ツリーの外側＝親でも子でもない）→エラー。

**実施記録**

ユーザー解答：E（正解）。「loadA/loadBはtry-catchが無いが、throws宣言があるので大丈夫」という理解を自力で確認。また「loadAとloadBはどちらが呼ばれるのか」という質問があったが、この2つは互いに呼び出し関係のない独立したメソッドで、いずれも`loadRaw()`を個別に呼んでいるだけ（このコード自体に`main()`は無く、実行順ではなく各メソッド単体のコンパイル可否を判定する問題だった）と回答して整理した。

<a id="q37"></a>
## 問題37（2.1 try-catch：catchで捕まえた後は処理が続行する、というポイント確認）

「例外が投げられたらそこで処理が全部終わる」という誤解（問題1のような**未catch**の例だけを見て一般化してしまったもの）を、ループ処理で確認する問題。`javac`/`java`で検証済み。

```java
public class Main {
    public static void main(String[] args) {
        String[] inputs = {"10", "abc", "20"};
        for (String s : inputs) {
            try {
                int num = Integer.parseInt(s);
                System.out.println("parsed:" + num);
            } catch (NumberFormatException e) {
                System.out.println("skip:" + s);
            }
        }
        System.out.println("done");
    }
}
```

次のプログラムを実行するとどうなりますか。（1つ選択）

A. `parsed:10` `skip:abc` `parsed:20` `done` の順に出力される
B. `parsed:10` `skip:abc` の後、`done`は出力されずプログラムが終了する
C. `parsed:10` が出力された直後、`NumberFormatException`によりプログラムが終了する（`skip:abc`以降は出力されない）
D. `parsed:10` `skip:abc` `done` が出力される（3個目の`"20"`は処理されない）
E. コンパイルエラーが発生する

**解答**

正解：**A**

**補足**

- `"10"`：`Integer.parseInt("10")`成功→`parsed:10`。
- `"abc"`：`NumberFormatException`が発生するが`catch`で捕まる→`skip:abc`。**ここで処理は終わらない**。`catch`ブロックを抜けた後、`for`ループは普通に次の周（`"20"`）へ進む。
- `"20"`：`Integer.parseInt("20")`成功→`parsed:20`。
- ループが終わった後、`main()`の最後に書かれている`System.out.println("done")`も普通に実行される。
- 問題27・29・30・32（try-with-resourcesでコンストラクタが例外を投げる系）で「そこで処理が止まる」ように見えたのは、例外そのものが処理を止めていたのではなく、**`catch`/`finally`ブロックの直後に`main()`がたまたま終わっていた**（後ろに書くコードが無かった）だけ。`catch`で捕まえた後は、後ろにコードがあれば普通に実行され続ける。

**実施記録**

ユーザー解答：A（正解）。「例外＝そこでプログラムが止まる」という誤解の出どころが、過去のtry-with-resources問題（27/29/30/32）で`catch`/`finally`の直後に`main()`が終わっていたことだったと自己分析。実際は「その後にコードがあれば続く」だけで、`catch`が処理を止めているわけではないと整理した。

<a id="q38"></a>
## 問題38（2.1／4.1 まとめ：例外発生時の制御フロー、文章の正誤選択）

問題33〜37の一連の議論（throw発生時のジャンプ先探索、finallyの扱い、catch後の処理継続）をまとめた文章正誤問題（問題13・34と同じ形式）。

例外処理の制御フローについて、次のA〜Eのうち正しい記述をすべて選んでください。

A. `throw`が発生すると、一番近い外側の`try`に対応する`catch`から順に、型がマッチするものを探しに行く。

B. `catch`ブロックの型が実際にスローされた例外の型と一致しない場合でも、その`catch`ブロックは必ず一度は実行される。

C. マッチする`catch`で例外が処理された後は、`try-catch`の外側に書かれた後続の処理も通常通り実行される。

D. `finally`ブロックは、対応する`catch`が例外をキャッチできた場合にのみ実行され、キャッチできなかった場合は実行されない。

E. 例外がどの`catch`にもマッチしないまま呼び出し元を遡り続け、最終的に`main()`でも捕まらなかった場合、プログラムは異常終了する。

**解答**

正解：**A, C, E**

**補足**

- A：内側の`try`から順に外側へ、型がマッチする`catch`を探すという探索順（ネストしたtry-catchの例で確認済み）。
- C：`catch`で処理が完了した後は、`try-catch`の外側の後続コードも普通に実行される（問題37で確認済み。「例外＝即終了」ではない）。
- E：どの`catch`にもマッチしないまま`main()`まで伝播した場合のみプログラムは異常終了する（問題1などの実例）。
- B：誤り。型が一致しない`catch`ブロックは一度も実行されず、完全にスキップされる。
- D：誤り。`finally`は`catch`がキャッチできたかどうかに関係なく必ず実行される（問題11・14・15・32で確認済み）。

**実施記録**

迷ったところ：なし。A, C, Eで一発正解。

<a id="q39"></a>
## 問題39（4.1 throws：main()のthrows／非チェック例外の任意宣言／広い型でのthrows宣言、文章の正誤選択）

```java
public class Main {
    public static void main(String[] args) throws Exception {
        stepA();
        stepB();
    }

    static void stepA() throws RuntimeException {
        System.out.println("A ok");
    }

    static void stepB() throws Exception {
        throw new RuntimeException("B unexpected");
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `main()`に`throws Exception`と書いてもコンパイルは通る。

B. `stepA()`の`throws RuntimeException`を削除しても、コンパイル結果・実行結果はどちらも変わらない。

C. `stepB()`は実際には`RuntimeException`（非チェック例外）しか投げていないため、`throws Exception`という宣言自体が誤りで、コンパイルエラーになる。

D. `main()`内で`stepB()`をtry-catchで囲んでいなくても、`main()`自身が`throws Exception`を宣言しているためコンパイルは通る。

E. このプログラムを実行すると、`"A ok"`が出力された後、`RuntimeException`が`main()`を通じてJVMまで伝播し、プログラムは異常終了する。

**解答**

正解：**A, B, D, E**

**補足**

- C：誤り。`throws`宣言は「実際に投げている例外そのもの」ではなく「投げる可能性がある型」を書けばよく、実際より広い型（`Exception`）を宣言しても問題ない。ここが引っかけ。
- B：`RuntimeException`は非チェック例外なので、`throws`宣言の有無はコンパイル・実行のどちらにも影響しない（javacで実際に削除して再検証済み）。
- 応用パターン：`main()`の宣言を`throws IOException`に変えると（`stepB()`は`throws Exception`のまま）、`stepB();`の呼び出し行でコンパイルエラーになる（`エラー: 例外Exceptionは報告されません`）。チェック例外の「catch or specify」ルールは呼び出しチェーンの各段で毎回チェックされ、途中でcatchされない限り、より上位の呼び出し元も同じかそれを包含できる型を`throws`する必要がある。ユーザーコード側でこのチェックが必要な最後の地点が`main()`で、その先（JVM）はコンパイル時チェックの対象外。

**実施記録**

迷ったところ：A, B, Cを選択（Cが誤り）。DとEを見落とした。Cについて「`stepB()`が実際にはRuntimeExceptionしか投げていないのに`throws Exception`と宣言するのはおかしい」と誤解していたが、javacで検証してコンパイルが通ることを確認して訂正。

<a id="q40"></a>
## 問題40（4.1 throws：チェック例外のcatch-or-specify、呼び出しチェーン3段構成）

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            outer();
        } catch (IOException e) {
            System.out.println("caught in main: " + e.getMessage());
        }
    }

    static void outer() throws IOException {
        middle();
    }

    static void middle() throws IOException {
        inner();
    }

    static void inner() throws IOException {
        throw new IOException("boom");
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `outer()`と`middle()`はどちらも自分では`throw`していないが、`inner()`が投げる可能性のある`IOException`を伝播させるためには`throws IOException`の宣言が必要である。

B. `middle()`の`throws IOException`を削除すると、`outer()`のコンパイルでエラーになる。

C. `main()`は`throws IOException`を宣言していないが、`try-catch`で`IOException`を捕捉しているためコンパイルは通る。

D. このプログラムを実行すると、`"caught in main: boom"`が出力される。

E. `inner()`の`throws IOException`宣言を削除すると、`inner()`メソッド自体がコンパイルエラーになる（`throw`している型を宣言していないため）。

**解答**

正解：**A, C, D, E**

**補足**

- B：誤り。`middle()`の`throws IOException`を削除すると、エラーは`outer()`ではなく**`middle()`自身**（`inner();`の呼び出し行）で起きる。チェック例外は「対処されていないその場所そのもの」がエラー地点になり、1つ上の呼び出し元まで遡ってエラーになるわけではない、という点が引っかけ。
- E：`throw`文で直接投げているチェック例外も、`throws`宣言かtry-catchで対処していなければ、その`throw`文の行自体でコンパイルエラーになる（javacで検証済み：`throw new IOException("boom");`の行でエラー）。

**実施記録**

迷ったところ：A, C, Dは正解したが、Eを見落とした（`inner()`のthrows削除時のエラー発生箇所を意識していなかった）。

<a id="q41"></a>
## 問題41（4.1 throws：例外の変換＝チェック例外をcatchして別の非チェック例外にラップして投げ直すパターン）

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            outer();
        } catch (Exception e) {
            System.out.println("caught in main: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }

    static void outer() throws Exception {
        try {
            middle();
        } catch (IOException e) {
            throw new IllegalStateException("wrapped: " + e.getMessage());
        }
    }

    static void middle() throws IOException {
        inner();
    }

    static void inner() throws IOException {
        throw new IOException("boom");
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `outer()`の`throws Exception`宣言を削除しても、コンパイル結果・実行結果はどちらも変わらない。

B. `main()`の`catch (Exception e)`は、`outer()`から投げられる`IllegalStateException`を型が異なるため捕捉できない。

C. このプログラムを実行すると、`"caught in main: IllegalStateException - wrapped: boom"`が出力される。

D. `middle()`や`inner()`が投げる`IOException`は`outer()`内のtry-catchで処理されているため、`outer()`自身は`throws IOException`を宣言する必要がない。

E. もし`outer()`をtry-catchなしの単純な伝播（`static void outer() throws IOException { middle(); }`）に戻した場合、`main()`側の`catch (Exception e)`はそのまま`IOException`も捕捉できる。

**解答**

正解：**A, C, D, E**

**補足**

- B：誤り。`IllegalStateException → RuntimeException → Exception`という継承関係があるため、`IllegalStateException`のインスタンスは常に`Exception`型にアップキャストでき、`catch (Exception e)`で問題なく捕捉できる（実行結果でも確認済み）。
- A：`outer()`が実際に投げているのは非チェック例外`IllegalStateException`のみで、`IOException`は内部のtry-catchで既に処理し尽くされている。つまり`throws Exception`は元々不要な「飾り」で、削除しても結果は変わらない（javacで検証済み）。
- 「チェック例外をcatchして意味の対応する非チェック例外に変換して投げ直す」exception translationという実務でもよくあるパターンだった。

**実施記録**

迷ったところ：A, C, Eは正解したが、Bを誤って選択し、Dを見落とした。継承関係によるアップキャストでの捕捉（B）と、内部で処理済みならthrows不要（D）の理解が曖昧だった。

<a id="q42"></a>
## 問題42（4.1 throws：3段の呼び出しチェーン＋型が合わないcatchが途中に紛れ込むパターン）

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            level1();
        } catch (IOException e) {
            System.out.println("caught in main: " + e.getMessage());
        }
    }

    static void level1() throws IOException {
        try {
            level2();
        } catch (NullPointerException e) {
            System.out.println("level1 caught NPE");
        }
    }

    static void level2() throws IOException {
        try {
            level3();
        } catch (ArithmeticException e) {
            System.out.println("level2 caught arithmetic");
        }
    }

    static void level3() throws IOException {
        throw new IOException("deep boom");
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `level1()`と`level2()`はそれぞれtry-catchを持っているが、catchしている型（`NullPointerException`、`ArithmeticException`）が`IOException`と無関係なため、どちらも`throws IOException`を宣言しないとコンパイルエラーになる。

B. このプログラムを実行すると、`"level1 caught NPE"`や`"level2 caught arithmetic"`は一切出力されず、`"caught in main: deep boom"`だけが出力される。

C. `level2()`の`catch (ArithmeticException e)`を`catch (IOException e)`に変更すると、`level3()`が投げた例外は`level2()`で捕まり、`main()`側のcatchは実行されなくなる。

D. `level1()`の`throws IOException`宣言を削除すると、コンパイルエラーになる。

E. `main()`の`catch (IOException e)`を`catch (Exception e)`に変えても、同じように`"caught in main: deep boom"`が出力される。

**解答**

正解：**A, B, C, D, E**（全部正しい）

**補足**

- A：`NullPointerException`・`ArithmeticException`は非チェック例外なので、そのcatchの存在自体は`IOException`への対処にはならない。`level1()`・`level2()`はそれぞれ独立して`throws IOException`が必要（片方だけでは不十分。javacで個別に検証済み）。
- 非チェック例外は「try内で実際に発生しうるか」をコンパイラがチェックしないため、明らかに発生しないtry内にも自由にcatchを書ける。一方チェック例外は「try内で実際に発生しうるか」を厳密にチェックされる（`level1()`のthrows削除時に、`main()`側の`catch (IOException e)`まで道連れでエラーになったのはこのため）。
- 例外は`throw`地点から呼び出し元へ1段ずつ遡り、型がマッチする最初のcatchで止まる。型の合わないcatchは完全にスキップされる（B・C）。

**実施記録**

迷ったところ：B, C, D, Eは正解したが、Aを見落とした（`level1()`・`level2()`それぞれ個別にthrowsが必要という点への意識が薄かった）。

<a id="q43"></a>
## 問題43（総合：try-with-resources＋suppressed例外＋finallyの横断問題）

```java
public class Main {
    static class Resource implements AutoCloseable {
        String name;
        Resource(String name) {
            this.name = name;
            System.out.print("open:" + name + " ");
        }
        @Override
        public void close() {
            System.out.print("close:" + name + " ");
            throw new IllegalStateException("close-fail:" + name);
        }
    }

    public static void main(String[] args) {
        try {
            process();
        } catch (RuntimeException e) {
            System.out.print("caught:" + e.getMessage() + " ");
            for (Throwable sup : e.getSuppressed()) {
                System.out.print("suppressed:" + sup.getMessage() + " ");
            }
        } finally {
            System.out.println("done");
        }
    }

    static void process() {
        try (Resource r1 = new Resource("R1"); Resource r2 = new Resource("R2")) {
            throw new RuntimeException("body-fail");
        }
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `process()`は`RuntimeException`しか投げていないため、`throws`宣言は不要であり、実際に付いていなくてもコンパイルは通る。

B. リソースは`r1`→`r2`の順にオープンされ、close()は逆順（`r2`→`r1`）で呼ばれる。

C. `close()`が投げる`IllegalStateException`は、tryブロック本体で発生した`RuntimeException`（`"body-fail"`）に対する**抑制された例外（suppressed exception）**として扱われ、`main()`側でcatchされる例外自体は`"body-fail"`の方である。

D. `e.getSuppressed()`で取得できる配列の順序は、close()が呼ばれた順（`r2`→`r1`）と一致する。

E. `main()`の`catch`で例外を処理した後も、`finally`ブロックは実行されるため、最終的に`"done"`という出力が必ず末尾に来る。

**解答**

正解：**A, B, C, D, E**（全部正しい）

**補足**

- 実際の出力は`open:R1 open:R2 close:R2 close:R1 caught:body-fail suppressed:close-fail:R2 suppressed:close-fail:R1 done`（javacで検証済み）。`finally`は`catch`の後に必ず実行されるため、`done`は改行付きで一番最後に来る。
- 今まで別々に扱ってきた「try-with-resourcesのclose順」「suppressed例外」「finally」「throwsの要不要（非チェック例外なら省略可）」を1つのコードに統合した総合問題だった。

**実施記録**

迷ったところ：なし。A〜E全問正解。ただし手書きのトレースで末尾の`finally`（"done"）を書き漏らしていた点は要注意。
