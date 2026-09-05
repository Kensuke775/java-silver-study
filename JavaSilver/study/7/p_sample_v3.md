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
- [問題4（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）](#q4)
- [問題5（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）](#q5)
- [問題6（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）](#q6)
- [問題7（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）](#q7)
- [問題8（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）](#q8)
- [問題9（1.3 カスタム例外：`Exception`を継承した独自例外クラスの作成と意味）](#q9)
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
- [問題44](#q44)
- [問題45](#q45)

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

<a id="q4"></a>
## 問題4（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）

次のクラスのうち、チェック例外はどれですか。（1つ選択）

A. `ArithmeticException`
B. `ClassCastException`
C. `FileNotFoundException`
D. `NumberFormatException`
E. `StackOverflowError`

**回答欄**

回答：

<a id="q5"></a>
## 問題5（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）

次のうちチェック例外はどれですか。（1つ選択）

A. `ArrayIndexOutOfBoundsException`　B. `ClassNotFoundException`　C. `NoClassDefFoundError`　D. `NullPointerException`　E. `StackOverflowError`

**回答欄**

回答：

<a id="q6"></a>
## 問題6（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）

```java
public class Main {
    static void a() { throw new ArrayIndexOutOfBoundsException(); }
    static void b() { throw new ClassNotFoundException(); }
    static void c() { throw new NoClassDefFoundError(); }
    static void d() { throw new StackOverflowError(); }
}
```

どのメソッドが原因でコンパイルエラーになりますか。（1つ選択）

**回答欄**

回答：

<a id="q7"></a>
## 問題7（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）

次のうち非チェック例外はどれですか。（2つ選択）

A. `IOException`　B. `NumberFormatException`　C. `ClassNotFoundException`　D. `NoClassDefFoundError`　E. `FileNotFoundException`

**回答欄**

回答：

<a id="q8"></a>
## 問題8（1.2 例外の種類：チェック例外／非チェック例外の暗記ドリル）

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

どのメソッドが原因でコンパイルエラーになりますか。（2つ選択）

**回答欄**

回答：

<a id="q9"></a>
## 問題9（1.3 カスタム例外：`Exception`を継承した独自例外クラスの作成と意味）

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

<a id="q13"></a>
## 問題13（1.4 例外処理とは：try-catchとthrowsの違い、文章問題）

次のうち、例外処理に関する説明として正しいものはどれですか。（3つ選択）

A. `throws`を使ってメソッド宣言に例外クラスを書けば、その例外に対する処理は完了したことになる
B. `try-catch`は、例外が発生した「その場所」で例外を捕まえて処理する方法である
C. `throws`は、例外がスローされる可能性のあるメソッドやコンストラクタ自身では処理せず、呼び出し元に処理を委ねる方法である
D. チェック例外がスローされる可能性があるプログラムでも、`try-catch`と`throws`のどちらも使わずにコンパイルを成功させることができる
E. `throws`を使った場合、最終的にはどこかの呼び出し元で`try-catch`による例外処理を行う必要がある

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

<a id="q17-20"></a>
## 問題17〜20（例外クラスの継承チェーン暗記ドリル）

表7-2〜7-4の9クラス＋補助クラスの継承チェーン確認用のセット：

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

**回答欄**

回答：

### 問題18

次のうち、お互いに直接の親子関係にあるペアはどれですか。（1つ選択）

A. `ArithmeticException`と`ClassCastException`　B. `NumberFormatException`と`ArrayIndexOutOfBoundsException`　C. `FileNotFoundException`と`IOException`　D. `StackOverflowError`と`OutOfMemoryError`　E. `NullPointerException`と`ClassNotFoundException`

**回答欄**

回答：

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

このプログラムをコンパイルするとどうなりますか。

**回答欄**

回答：

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

このプログラムをコンパイル、実行するとどうなりますか。

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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
> | `SQLException` | チェック（`Exception`の直接のサブクラス、`java.sql`パッケージ） | データベースへのアクセス（JDBC経由のSQL実行など）が失敗したときに発生。`IOException`とは別系統の兄弟。 |
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
> | `IllegalStateException` | `RuntimeException`の直接のサブクラス。オブジェクトやメソッドが、今の状態では呼び出せない操作を呼ばれたときに発生（引数の値自体は正しいが、タイミング・状態が不正な場合。値そのものが不正な`IllegalArgumentException`とは区別される）。 |
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
> │   ├─ IOException ── FileNotFoundException / EOFException
> │   ├─ ReflectiveOperationException ── ClassNotFoundException
> │   ├─ SQLException
> │   └─ RuntimeException（非チェック）
> │       ├─ ArithmeticException
> │       ├─ ClassCastException
> │       ├─ NullPointerException
> │       ├─ IllegalStateException
> │       ├─ IndexOutOfBoundsException ── ArrayIndexOutOfBoundsException
> │       └─ IllegalArgumentException ── NumberFormatException
> └─ Error（非チェック）
>     ├─ LinkageError ── NoClassDefFoundError
>     └─ VirtualMachineError ── StackOverflowError / OutOfMemoryError
> ```
>
> **コツ①：途中で分岐する「hub」は6個だけ** — `IOException` / `ReflectiveOperationException` / `IndexOutOfBoundsException` / `IllegalArgumentException` / `LinkageError` / `VirtualMachineError`。これさえ覚えれば残りは全部この6個か`RuntimeException`/`Error`に直結する末端。`SQLException`と`IllegalStateException`はどちらも自身はさらに枝分かれしない末端（hubではない）。
>
> **コツ②：末端の名前は親の意味をそのまま含んでいることが多い** — `ArrayIndexOutOfBoundsException`は名前に"IndexOutOfBounds"が入っているので親が分かる。`NoClassDefFoundError`は「クラス定義が見つからない」＝リンクの失敗→`LinkageError`。
>
> **コツ③：意味でグループ化して短文にする**
> - IO系：「ファイルが見つからない(`FileNotFoundException`)のはIOの話」
> - リフレクション系：「クラスが見つからない(`ClassNotFoundException`)のはリフレクションの話」（`Class.forName("文字列")`でクラス名を実行時に探す動作。文字列なのでコンパイル時にチェックできず、失敗しうるからチェック例外）
> - DB系：「SQL実行の失敗(`SQLException`)はJDBC（DB接続）の話」。`IOException`と名前は似ているが継承関係は無い、独立した兄弟。
> - 引数系：「数値の書式がおかしい(`NumberFormatException`)＝不正な引数(`IllegalArgumentException`)の一種」
> - 状態系：「引数の値自体は正しいのに、今のタイミング・状態では呼べない(`IllegalStateException`)」＝`IllegalArgumentException`（値が悪い）とは原因が違う、`RuntimeException`直下の別枝。
> - VM系：「VMが死ぬ原因は2つだけ：スタックが溢れる(`StackOverflowError`)か、メモリが尽きる(`OutOfMemoryError`)か」→どちらも`VirtualMachineError`
>
> **コツ④：チェック例外は実質5個（＋名前ルール）と組み合わせる** — `Error`系は名前に必ず"Error"が入るので非チェックと判別しやすい。残る"...Exception"のうち、チェック例外なのは`IOException`/`FileNotFoundException`/`ReflectiveOperationException`/`ClassNotFoundException`/`SQLException`（＝IO系・リフレクション系・DB系の3グループのみ）。それ以外の"...Exception"（`RuntimeException`とその子孫、`IllegalStateException`含む）は全部非チェック。
>
> **コツ⑤：チェック／非チェックの区別は、オーバーライド時のthrowsルールにも直結する（2026-08-25追記）** — 親クラスのメソッドをオーバーライドするとき、
> - **チェック例外**（`SQLException`など）→ 親と**同じか、より狭い型（サブクラス）**でしか宣言できない。無関係な型や、より広い型は宣言できない（コンパイルエラー）。宣言自体を無くす（0個にする）のは「狭める」の極端形として常にOK。
> - **非チェック例外**（`IllegalStateException`など）→ このルールの対象外。親が何を宣言していようと関係なく、オーバーライド側は任意の`RuntimeException`系を自由に宣言できる（`IllegalStateException`が親の例外と無関係でも問題ない）。
>
> つまり「チェックか非チェックか」の区別は、catch/throwsの伝播ルールだけでなく、オーバーライド時の制約の有無にも同じ軸で効いてくる。

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

<a id="q38"></a>
## 問題38（2.1／4.1 まとめ：例外発生時の制御フロー、文章の正誤選択）

問題33〜37の一連の議論（throw発生時のジャンプ先探索、finallyの扱い、catch後の処理継続）をまとめた文章正誤問題（問題13・34と同じ形式）。

例外処理の制御フローについて、次のA〜Eのうち正しい記述をすべて選んでください。

A. `throw`が発生すると、一番近い外側の`try`に対応する`catch`から順に、型がマッチするものを探しに行く。

B. `catch`ブロックの型が実際にスローされた例外の型と一致しない場合でも、その`catch`ブロックは必ず一度は実行される。

C. マッチする`catch`で例外が処理された後は、`try-catch`の外側に書かれた後続の処理も通常通り実行される。

D. `finally`ブロックは、対応する`catch`が例外をキャッチできた場合にのみ実行され、キャッチできなかった場合は実行されない。

E. 例外がどの`catch`にもマッチしないまま呼び出し元を遡り続け、最終的に`main()`でも捕まらなかった場合、プログラムは異常終了する。

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

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

**回答欄**

回答：

<a id="q44"></a>
## 問題44

```java
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            risky();
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    static void risky() throws IOException, SQLException {
        throw new SQLException("sql-fail");
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `risky()`のように、`throws`にカンマ区切りで複数の例外型を並べて宣言することができる。

B. `risky()`は実際には`SQLException`しか投げていないため、`main()`の`catch (IOException e)`は一度も実行されない。

C. `main()`は`SQLException`をcatchしていないが、`throws SQLException`を宣言しているためコンパイルは通る。

D. このプログラムを実行すると、`SQLException`が`main()`を通じてJVMまで伝播し、プログラムは異常終了する。

E. もし`main()`の`throws SQLException`を削除すると、コンパイルエラーになる。

**回答欄**

回答：

<a id="q45"></a>
## 問題45

```java
import java.io.*;

class Base {
    void method() throws IOException {
        System.out.println("Base");
    }
}

class Sub extends Base {
    @Override
    void method() {
        System.out.println("Sub");
    }
}

public class Main {
    public static void main(String[] args) {
        Base ref = new Sub();
        try {
            ref.method();
        } catch (IOException e) {
            System.out.println("caught");
        }
    }
}
```

次のA〜Eのうち、正しい記述をすべて選んでください。

A. `Sub`の`method()`は`throws`を宣言していないが、`Base`の`method()`をオーバーライドすることに問題はない。

B. `main()`内の`ref.method();`がtry-catchで囲まれているのは、`ref`の**宣言された型（`Base`）**が`throws IOException`を持っているためであり、実際に生成されるインスタンスが`Sub`かどうかは無関係である。

C. もし`main()`のtry-catchを削除して単に`ref.method();`とだけ書くと、実行時オブジェクトは`Sub`であり実際には`IOException`を投げないにもかかわらず、コンパイルエラーになる。

D. このプログラムを実行すると、`"Sub"`が出力され、`"caught"`は出力されない。

E. もし`Base ref = new Sub();`を`Sub ref = new Sub();`に変更した場合、`ref.method();`の呼び出しをtry-catchで囲む必要はなくなる。

**回答欄**

回答：

<a id="q46"></a>
## 問題46

```java
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            methodX();
        } catch (Exception e) {
            System.out.println("Cause: " + e.getCause());
        }
        try {
            methodY();
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
        }
    }

    static void methodX() throws Exception {
        try {
            throw new EOFException("stream ended");
        } catch (IOException e) {
            throw e;
        }
    }

    static void methodY() throws IOException {
        try {
            throw new EOFException("stream ended");
        } catch (EOFException e) {
            System.out.print("logging... ");
            throw e;
        }
    }
}
```

このプログラムを実行した時の出力として正しいものを1つ選んでください。

A.
```
Cause: stream ended
logging... Message: null
```

B.
```
Cause: null
logging... Message: stream ended
```

C.
```
Cause: null
Message: logging... stream ended
```

D.
```
Cause: java.io.EOFException: stream ended
logging... Message: stream ended
```

E.
コンパイルエラーになる

**回答欄**

回答：

<a id="q47"></a>
## 問題47

```java
public class Main {
    public static void main(String[] args) {
        try {
            process();
        } catch (Exception e) {
            System.out.println("Cause: " + e.getCause());
        }
    }

    static void process() throws Exception {
        RuntimeException first = new RuntimeException("first");
        try {
            throw new IllegalStateException("second");
        } catch (IllegalStateException second) {
            RuntimeException third = new RuntimeException("third");
            throw new Exception("final", first);
        }
    }
}
```

このプログラムを実行した時の出力として正しいものを1つ選んでください。

A. `Cause: java.lang.RuntimeException: first`
B. `Cause: java.lang.IllegalStateException: second`
C. `Cause: java.lang.RuntimeException: third`
D. `Cause: null`
E. コンパイルエラーになる

**回答欄**

回答：

<a id="q48"></a>
## 問題48

```java
public class Main {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3};
        try {
            System.out.println(arr[5]);
        } catch ( /* insert code here */ ) {
            System.out.println("exception");
        }
    }
}
```

`exception`と出力するには、catchに何を入れるか。当てはまるものをすべて選んでください。

A. `ArrayIndexOutOfBoundsException e`
B. `IndexOutOfBoundsException e`
C. `NullPointerException e`
D. `RuntimeException e`
E. `NumberFormatException e`

**回答欄**

回答：

<a id="q49"></a>
## 問題49

```java
class DataException extends RuntimeException {}
class ParseDataException extends DataException {}
class SaveDataException extends DataException {}

public class Main {
    public static void main(String[] args) {
        try {
            throw new ParseDataException();
        } catch ( /* insert code here */ ) {
            System.out.println("caught");
        }
    }
}
```

`caught`と出力するには、catchに何を入れるか。当てはまるものをすべて選んでください。

A. `ParseDataException e`
B. `DataException e`
C. `SaveDataException e`
D. `RuntimeException e`
E. `Exception e`

**回答欄**

回答：

<a id="q50"></a>
## 問題50

```java
class DataException extends RuntimeException {}
class ParseDataException extends DataException {}

public class Main {
    public static void main(String[] args) {
        try {
            throw new ParseDataException();
        } catch (ParseDataException e) {
            System.out.println("A");
        } catch (DataException e) {
            System.out.println("B");
        } catch (RuntimeException e) {
            System.out.println("C");
        }
    }
}
```

実行結果として正しいものを1つ選んでください。

A. `A`
B. `B`
C. `C`
D. コンパイルエラーが発生する
E. 何も出力されずに異常終了する

**回答欄**

回答：

<a id="q51"></a>
## 問題51

```java
public class Main {
    public static void main(String[] args) {
        int total = 0;
        try {
            int divisor = 0;
            total = 100 / divisor;
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage() + ":" + total + ":" + divisor);
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `/ by zero:0:0` が出力される
B. `0:0` が出力される
C. `/ by zero` が出力される
D. `java.lang.ArithmeticException: / by zero:0:0` が出力される
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q52"></a>
## 問題52

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            int x = Integer.parseInt("abc");
            work();
        /* insert code here */
    }
    static void work() throws IOException {
        throw new IOException("boom");
    }
}
```

コンパイルを成功させるには、6行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `} catch (Exception e) {}`
B. `} catch (NumberFormatException | Exception e) {}`
C. `} catch (RuntimeException | IOException e) {}`
D. `} catch (NumberFormatException e | IOException e) {}`
E. `} catch (IOException | NumberFormatException e) {}`

**回答欄**

回答：

<a id="q53"></a>
## 問題53

```java
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Object obj = "text";
            Integer n = (Integer) obj;
            query();
        /* insert code here */
    }
    static void query() throws SQLException {
        throw new SQLException("query failed");
    }
}
```

コンパイルを成功させるには、6行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `} catch (Exception e) {}`
B. `} catch (ClassCastException | RuntimeException e) {}`
C. `} catch (ClassCastException | SQLException e) {}`
D. `} catch (ClassCastException e | SQLException e) {}`
E. `} catch (RuntimeException | SQLException e) {}`

**回答欄**

回答：

<a id="q54"></a>
## 問題54

```java
public class Main {
    public static void main(String[] args) {
        try (Logger logger = new Logger()) {
            System.out.print("X");
        } finally {
            System.out.print("Y");
        }
    }
}
class Logger implements AutoCloseable {
    public Logger() {
        System.out.print("Open");
    }
    public void close() {
        System.out.print("Close");
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `OpenXCloseY` が出力される
B. `OpenXYClose` が出力される
C. `XOpenCloseY` が出力される
D. `OpenCloseXY` が出力される
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q55"></a>
## 問題55

```java
public class Main {
    public static void main(String[] args) {
        try (Logger logger = new Logger()) {
            System.out.print("X");
            throw new RuntimeException("fail");
        } catch (RuntimeException e) {
            System.out.print("Catch");
        } finally {
            System.out.print("Y");
        }
    }
}
class Logger implements AutoCloseable {
    public Logger() { System.out.print("Open"); }
    public void close() { System.out.print("Close"); }
}
```

実行結果として正しいものを1つ選んでください。

A. `OpenXCatchCloseY`
B. `OpenXCloseCatchY`
C. `OpenXCatchYClose`
D. `OpenCloseXCatchY`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q56"></a>
## 問題56

```java
public class Main {
    public static void main(String[] args) {
        try (Logger a = new Logger("A"); Logger b = new Logger("B")) {
            System.out.print("Body");
        }
    }
}
class Logger implements AutoCloseable {
    String name;
    Logger(String name) { this.name = name; System.out.print("Open" + name); }
    public void close() { System.out.print("Close" + name); }
}
```

実行結果として正しいものを1つ選んでください。

A. `OpenAOpenBBodyCloseACloseB`
B. `OpenAOpenBBodyCloseBCloseA`
C. `OpenBOpenABodyCloseACloseB`
D. `OpenAOpenBCloseBCloseABody`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q57"></a>
## 問題57

```java
public class Main {
    public static void main(String[] args) {
        try (Logger a = new Logger("A"); Logger b = new Logger("B", true)) {
            System.out.print("Body");
        } catch (RuntimeException e) {
            System.out.print("Caught:" + e.getMessage());
        }
    }
}
class Logger implements AutoCloseable {
    String name;
    Logger(String name) { this.name = name; System.out.print("Open" + name); }
    Logger(String name, boolean fail) {
        this.name = name;
        if (fail) throw new RuntimeException("init-fail-" + name);
        System.out.print("Open" + name);
    }
    public void close() { System.out.print("Close" + name); }
}
```

実行結果として正しいものを1つ選んでください。

A. `OpenACaught:init-fail-B`
B. `OpenACloseACaught:init-fail-B`
C. `OpenAOpenBCaught:init-fail-B`
D. `Caught:init-fail-B`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q58"></a>
## 問題58

```java
public class Main {
    public static void main(String[] args) {
        try {
            int x = 10 / 2;
            System.out.print("X");
        } finally {
            System.out.print("Y");
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `X` が出力される
B. `XY` が出力される
C. `Y` が出力される
D. `YX` が出力される
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q59"></a>
## 問題59

```java
public class Main {
    public static void main(String[] args) {
        try {
            System.out.print("X");
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `X` が出力される
B. 何も出力されない
C. 実行時にエラーが発生する
D. コンパイルエラーが発生する
E. 無限ループになる

**回答欄**

回答：

<a id="q60"></a>
## 問題60

```java
public class Main {
    public static void main(String[] args) {
        try (Counter c = new Counter()) {
            System.out.print("X");
        }
    }
}
class Counter implements AutoCloseable {
    public Counter() { System.out.print("Open"); }
    public void close() { System.out.print("Close"); }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `OpenXClose` が出力される
B. `XOpenClose` が出力される
C. `OpenCloseX` が出力される
D. `X` だけが出力される
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q61"></a>
## 問題61

```java
public class Main {
    public static void main(String[] args) {
        try (Box box = new Box()) {
            System.out.print("Use1");
            System.out.print("Use2");
        }
    }
}
class Box implements AutoCloseable {
    public Box() { System.out.print("Make"); }
    public void close() { System.out.print("Discard"); }
}
```

実行結果として正しいものを1つ選んでください。

A. `MakeUse1Use2Discard`
B. `MakeDiscardUse1Use2`
C. `Use1Use2MakeDiscard`
D. `MakeUse1DiscardUse2`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q62"></a>
## 問題62

```java
public class Main {
    public static void main(String[] args) {
        try (Box box = new Box()) {
            System.out.print("Use");
        } finally {
            System.out.print("Cleanup");
        }
    }
}
class Box implements AutoCloseable {
    public Box() { System.out.print("Make"); }
    public void close() { System.out.print("Discard"); }
}
```

実行結果として正しいものを1つ選んでください。

A. `MakeUseCleanupDiscard`
B. `MakeUseDiscardCleanup`
C. `MakeCleanupUseDiscard`
D. `CleanupMakeUseDiscard`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q63"></a>
## 問題63

```java
public class Main {
    public static void main(String[] args) {
        try (Box first = new Box("1"); Box second = new Box("2")) {
            System.out.print("Use");
        }
    }
}
class Box implements AutoCloseable {
    String id;
    Box(String id) { this.id = id; System.out.print("Make" + id); }
    public void close() { System.out.print("Discard" + id); }
}
```

実行結果として正しいものを1つ選んでください。

A. `Make1Make2UseDiscard1Discard2`
B. `Make1Make2UseDiscard2Discard1`
C. `Make2Make1UseDiscard1Discard2`
D. `Make1UseDiscard1Make2Discard2`
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q64"></a>
## 問題64

```java
public class Main {
    public static void main(String[] args) {
        try (Cache cache = new Cache()) {
            System.out.print("Use");
        }
    }
}
interface Named { String name(); }
class Cache implements Named {
    public String name() { return "cache"; }
    public Cache() { System.out.print("Open"); }
    public void close() { System.out.print("Close"); }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `OpenUseClose` が出力される
B. `UseOpenClose` が出力される
C. `Use` だけが出力される
D. コンパイルエラーが発生する
E. 実行時に例外が発生する

**回答欄**

回答：

<a id="q65"></a>
## 問題65

```java
public class Main {
    public static void main(String[] args) {
        try (Logger log = new Logger(); Cache cache = new Cache()) {
            System.out.print("Use");
        }
    }
}
class Logger implements AutoCloseable {
    public Logger() { System.out.print("OpenLog"); }
    public void close() { System.out.print("CloseLog"); }
}
class Cache {
    public Cache() { System.out.print("OpenCache"); }
    public void close() { System.out.print("CloseCache"); }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `OpenLogOpenCacheUseCloseCacheCloseLog` が出力される
B. `OpenLogUseCloseLog` が出力される(Cacheは無視される)
C. コンパイルエラーが発生する
D. `OpenLogOpenCacheUse` が出力され、closeは呼ばれない
E. 実行時に例外が発生する

**回答欄**

回答：

<a id="q66"></a>
## 問題66

```java
public class Main {
    public static void main(String[] args) {
        try (Session session = new Session()) {
            System.out.print("Use");
        }
    }
}
class Session implements AutoCloseable {
    public Session() { System.out.print("Open"); }
    public void close(int code) { System.out.print("Close" + code); }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `OpenUse` が出力され、closeは呼ばれない
B. `OpenUseClose0` が出力される(codeはデフォルト値0で呼ばれる)
C. コンパイルエラーが発生する
D. `OpenUse` の後、実行時に例外が発生する
E. `implements AutoCloseable`があるため無条件でコンパイルは通る

**回答欄**

回答：

<a id="q67"></a>
## 問題67

```java
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Main().validate(null);
        } catch (RuntimeException ex) {}
    }
    public void validate(String input) throws IllegalStateException {
        if (input == null) {
            throw new IllegalStateException();
        } else {
            throw new IOException();
        }
    }
}
```

次のプログラムに関する正しい説明はどれですか。(2つ選択)

A. 5行目のcatchブロックがIllegalStateExceptionであればコンパイルが成功する
B. 5行目のcatchブロックがIOExceptionであればコンパイルが成功する
C. 7行目のthrowsと5行目のcatchブロックがIOExceptionであればコンパイルが成功する
D. 9行目で明示的にIllegalStateExceptionをスローしなくても、8行目でJVMからスローされる
E. 11行目がIOExceptionではなくIllegalArgumentExceptionであればコンパイルが成功する

**回答欄**

回答：

<a id="q68"></a>
## 問題68

```java
public class Main {
    public static void main(String[] args) {
        try {
            load();
        } catch (RuntimeException e) {
            System.out.println("handled");
        }
    }
    static void load() throws java.io.FileNotFoundException {
        throw new java.io.FileNotFoundException();
    }
}
```

このプログラムはこのままではコンパイルエラーです。どう直せば(単独で)コンパイルが通るか、当てはまるものをすべて選んでください。(他の行は変更しないものとする)

A. main()のcatchをFileNotFoundExceptionにする
B. main()のcatchをExceptionにする
C. catchはRuntimeExceptionのまま、main()にthrows FileNotFoundExceptionを追加する
D. catchブロックごと削除し、main()にthrows FileNotFoundExceptionを追加する
E. main()は変えず、load()の中身だけthrow new RuntimeException();に変える(load()のthrows宣言はそのまま)

**回答欄**

回答：

<a id="q69"></a>
## 問題69

```java
public class Main {
    public static void main(String[] args) {
        try {
            connect();
        } catch ( /* insert code here */ ) {
            System.out.println("handled");
        }
    }
    static void connect() throws java.sql.SQLException {
        throw new java.sql.SQLException();
    }
}
```

コンパイルを成功させるには、catchに何を入れるか。当てはまるものをすべて選んでください。

A. SQLException e
B. Exception e
C. Throwable e
D. RuntimeException e
E. IllegalArgumentException e

**回答欄**

回答：

<a id="q70"></a>
## 問題70

```java
public class Main {
    public static void main(String[] args) {
        process();
    }
    static void process() {
        try {
            connect();
        } catch (java.sql.SQLException e) {
            System.out.println("handled inside process");
        }
    }
    static void connect() throws java.sql.SQLException {
        throw new java.sql.SQLException();
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. handled inside process が出力される
B. コンパイルエラーが発生する(process()にthrowsが無いため)
C. コンパイルエラーが発生する(main()にthrowsが無いため)
D. 実行時にSQLExceptionが発生する
E. 何も出力されない

**回答欄**

回答：

<a id="q71"></a>
## 問題71

```java
import java.sql.SQLException;

class Super {
    public void connect() throws SQLException {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、7行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void connect() {}`
B. `public void connect() throws IllegalStateException {}`
C. `public void connect() throws java.sql.SQLException {}`
D. `public void connect() throws java.io.IOException {}`
E. `public void connect() throws Exception {}`

**回答欄**

回答：

<a id="q72"></a>
## 問題72

```java
class Super {
    public void open() throws java.io.IOException {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、5行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void open() throws java.io.FileNotFoundException {}`
B. `public void open() throws java.io.EOFException {}`
C. `public void open() throws java.io.IOException {}`
D. `public void open() throws java.sql.SQLException {}`
E. `public void open() {}`

**回答欄**

回答：

<a id="q73"></a>
## 問題73

```java
class Super {
    public void fetch() throws java.io.IOException, ClassNotFoundException {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、5行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void fetch() throws java.io.IOException {}`
B. `public void fetch() throws ClassNotFoundException {}`
C. `public void fetch() throws java.io.FileNotFoundException {}`
D. `public void fetch() throws java.io.IOException, ClassNotFoundException, ArithmeticException {}`
E. `public void fetch() throws Exception {}`

**回答欄**

回答：

<a id="q74"></a>
## 問題74

```java
class Super {
    public void run() throws Exception {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、5行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void run() {}`
B. `public void run() throws Exception {}`
C. `public void run() throws java.io.IOException {}`
D. `public void run() throws Throwable {}`
E. `public void run() throws RuntimeException {}`

**回答欄**

回答：

<a id="q75"></a>
## 問題75

```java
class Super {
    public void load() throws ClassNotFoundException {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、5行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void load() {}`
B. `public void load() throws ClassNotFoundException {}`
C. `public void load() throws java.lang.ReflectiveOperationException {}`
D. `public void load() throws java.io.IOException {}`
E. `public void load() throws Error {}`

**回答欄**

回答：

<a id="q76"></a>
## 問題76

```java
class Super {
    public void transfer() throws java.io.IOException, java.sql.SQLException {}
}
class Sub extends Super {
    @Override
    // insert code here
}
```

コンパイルを成功させるには、5行目に何を挿入するか。当てはまるものをすべて選んでください。

A. `public void transfer() throws java.io.IOException, java.sql.SQLException {}`
B. `public void transfer() throws java.io.FileNotFoundException {}`
C. `public void transfer() throws java.sql.SQLException, RuntimeException {}`
D. `public void transfer() throws java.io.IOException, ClassNotFoundException {}`
E. `public void transfer() throws Exception {}`

**回答欄**

回答：

<a id="q77"></a>
## 問題77

```java
public class Main {
    public static void main(String[] args) {
        try {
            process();
            System.out.print("Done! ");
        } catch (Exception ex) {
            System.out.print("Caught! ");
        }
    }
    static void process() throws java.sql.SQLException {
        try {
            try {
                throw new ArithmeticException();
            } catch (RuntimeException e) {
                System.out.print("Arithmetic! ");
            }
            throw new java.sql.SQLException("SQL problem! ");
        } catch (Exception e) {
            System.out.print("SQLCaught! ");
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Arithmetic! SQLCaught! Done! ` が出力される
B. `Arithmetic! SQL problem! Caught! ` が出力される
C. `Arithmetic! SQLCaught! Caught! ` が出力される
D. `Arithmetic! Caught! ` が出力される
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q78"></a>
## 問題78

```java
public class Main {
    public static void main(String[] args) {
        try {
            process();
            System.out.print("Done! ");
        } catch (Exception ex) {
            System.out.print("Caught! ");
        }
    }
    static void process() throws java.sql.SQLException {
        try {
            try {
                throw new NullPointerException();
            } catch (RuntimeException e) {
                System.out.print("NPE! ");
            }
            throw new java.sql.SQLException("SQL problem! ");
        } catch (ArithmeticException e) {
            System.out.print("SQLCaught! ");
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `NPE! SQLCaught! Done! ` が出力される
B. `NPE! Caught! ` が出力される
C. `NPE! SQLCaught! Caught! ` が出力される
D. `NPE! ` の出力後、実行時例外が発生してプログラムが異常終了する
E. コンパイルエラーが発生する

**回答欄**

回答：

<a id="q79"></a>
## 問題79

```java
public class Main {
    public static void main(String[] args) {
        try {
            test();
            System.out.print("Done! ");
        } catch (Exception ex) {
            System.out.print("Caught! ");
        }
    }
    static void test() throws java.io.IOException {
        try {
            try {
                throw new IllegalStateException();
            } catch (RuntimeException e) {
                System.out.print("State! ");
            }
            throw new java.io.FileNotFoundException("missing! ");
        } catch (RuntimeException e) {
            System.out.print("RTCaught! ");
        } catch (java.io.IOException e) {
            System.out.print("IOCaught! ");
        }
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `State! RTCaught! Done! ` が出力される
B. `State! IOCaught! Done! ` が出力される
C. `State! IOCaught! Caught! ` が出力される
D. `State! Caught! ` が出力される
E. コンパイルエラーが発生する（同じtryに複数のcatchは書けないため）

**回答欄**

回答：

<a id="q80"></a>
## 問題80

```java
class FirstException extends RuntimeException {
    FirstException() {}
    FirstException(String message) { super(message); }
}
class SecondException extends FirstException {
    // コンストラクタは一切定義されていない
}
public class Main {
    public static void main(String[] args) {
        try {
            SecondException ex;
            // insert code here
            throw ex;
        } catch(SecondException ex) {}
    }
}
```

コンパイルを成功させるには、`// insert code here`に何を挿入するか。当てはまるものをすべて選んでください。

A. `ex = new SecondException();`
B. `ex = new SecondException("msg");`
C. `ex = new FirstException();`
D. `ex = new FirstException("msg");`
E. `ex = new SecondException(new Exception());`

**回答欄**

回答：

<a id="q81"></a>
## 問題81

```java
class FirstException extends RuntimeException {
    FirstException(String message) { super(message); }
}
class SecondException extends FirstException {
    // コンストラクタは一切定義されていない
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

コンパイル、実行するとどのような結果になりますか。（1つ選択）

A. `Hello` が出力される
B. コンパイルエラーが発生する
C. `SecondException`が一度も使われていないため警告のみで、`Hello`は出力される
D. 実行時に例外が発生してプログラムが異常終了する
E. `SecondException`のクラス定義を削除すれば`Hello`が出力されるが、このままではコンパイルエラーになる（Bとは異なる理由で）

**回答欄**

回答：

<a id="q82"></a>
## 問題82

```java
class BaseException extends RuntimeException {
    BaseException(String message) { super(message); }
}
class MidException extends BaseException {
    MidException(String message) { super(message); }
    MidException(Throwable cause) { super(cause.getMessage()); }
}
class LeafException extends MidException {
    LeafException() { super(new RuntimeException("default")); }
    LeafException(Throwable cause) { super(cause); }
}
public class Main {
    public static void main(String[] args) {
        try {
            LeafException ex;
            // insert code here
            throw ex;
        } catch(LeafException ex) {}
    }
}
```

コンパイルを成功させるには、`// insert code here`に何を挿入するか。当てはまるものをすべて選んでください。

A. `ex = new LeafException();`
B. `ex = new LeafException(new Exception("boom"));`
C. `ex = new LeafException("boom");`
D. `ex = new MidException(new Exception("boom"));`
E. `ex = new BaseException("boom");`

**回答欄**

回答：

