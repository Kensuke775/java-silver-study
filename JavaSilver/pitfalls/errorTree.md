# Throwableクラス階層(エラーツリー)

`Throwable`から下の継承関係を木構造で整理したもの。Silver/Gold試験でよく出てくる範囲までカバー(java.lang, java.io, java.util, java.time)。

```
Throwable
├── Error (システム/JVMレベルの致命的な問題。基本的にcatchして回復すべきでない)
│   ├── VirtualMachineError
│   │   ├── OutOfMemoryError            (メモリ不足)
│   │   ├── StackOverflowError          (再帰の深さ超過などでスタック溢れ)
│   │   └── InternalError
│   ├── LinkageError
│   │   ├── NoClassDefFoundError        (実行時にクラス定義が見つからない)
│   │   ├── ExceptionInInitializerError (静的初期化子/staticフィールド初期化中の例外をラップ)
│   │   └── UnsupportedClassVersionError(class ファイルのバージョン不一致)
│   └── AssertionError                  (assert文の条件がfalse)
│
└── Exception (プログラムで対処しうる問題)
    │
    ├── IOException                        (チェック例外)
    │   ├── FileNotFoundException          (指定ファイルが存在しない)
    │   ├── EOFException                   (ストリームの末尾に予期せず到達)
    │   ├── UnsupportedEncodingException    (未知の文字エンコーディング)
    │   └── InterruptedIOException
    │
    ├── ReflectiveOperationException           (チェック例外, リフレクション系の親)
    │   ├── ClassNotFoundException             (コンパイル時にクラスを探せない)
    │   ├── NoSuchMethodException
    │   ├── NoSuchFieldException
    │   ├── IllegalAccessException
    │   └── InstantiationException
    │
    ├── CloneNotSupportedException         (Cloneable未実装でclone()を呼んだ)
    ├── InterruptedException               (スレッドの待機中に割り込まれた)
    ├── java.text.ParseException           (文字列の解析失敗)
    │
    └── RuntimeException (非チェック例外。catch/throws宣言は任意)
        │
        ├── NullPointerException           (null参照へのアクセス)
        ├── ArithmeticException            (0除算など算術エラー、int/0のみ。0.0はNaN/Infinityで例外にならない)
        ├── ClassCastException             (不正なキャスト)
        ├── ArrayStoreException            (配列の共変性で実行時に型不一致の要素を格納)
        ├── NegativeArraySizeException     (負のサイズで配列生成)
        ├── TypeNotPresentException
        │
        ├── IllegalArgumentException       (引数が不正)
        │   ├── NumberFormatException      (文字列→数値の変換失敗、Integer.parseInt等)
        │   └── DateTimeException          (java.time、日付/時刻の値が不正)
        │
        ├── IllegalStateException          (オブジェクトの状態的に呼べないメソッドを呼んだ)
        │
        ├── IndexOutOfBoundsException      (添字が範囲外、配列/文字列/Listの共通の親)
        │   ├── ArrayIndexOutOfBoundsException
        │   └── StringIndexOutOfBoundsException
        │
        ├── UnsupportedOperationException  (List.of()等、不変コレクションへのadd/remove)
        ├── ConcurrentModificationException(拡張for中にコレクションを直接変更)
        ├── NoSuchElementException         (Iterator.next()を要素の無い状態で呼んだ)
        │   └── InputMismatchException     (Scannerで型不一致の入力を読もうとした)
        └── UncheckedIOException           (IOExceptionをラップした非チェック版、java.io)
```

## 押さえておくポイント

チェック例外か非チェック例外かの境界線は「`RuntimeException`の子孫かどうか」で決まる。`RuntimeException`より上(`Exception`直下や`IOException`/`ReflectiveOperationException`系統)はすべてチェック例外、`RuntimeException`以下はすべて非チェック例外。

`Error`系はそもそも「プログラム側の`catch`で回復させる想定のものではない」という位置づけなので、`Exception`とは別の枝として扱う(構文上は`catch (Throwable t)`などで捕まえられてしまうが、意味的にやるべきではない)。

`IndexOutOfBoundsException`は配列・文字列どちらの添字エラーの共通の親であり、`ArrayIndexOutOfBoundsException`と`StringIndexOutOfBoundsException`はその子(兄弟同士で、互いに親子関係はない)。

引っかけやすいポイント:

- `NumberFormatException`は`IllegalArgumentException`のサブタイプ(`RuntimeException`直下ではない)。`catch (IllegalArgumentException e)`で`NumberFormatException`も一緒に拾える。
- `UncheckedIOException`は`IOException`のサブタイプ**ではない**。`RuntimeException`直下で、`IOException`は原因(`getCause()`)として保持するだけ。「IOがつくから`IOException`系統」と思い込むと間違える。
- `NoSuchElementException`はコレクション操作(`Iterator`)由来だが`RuntimeException`直下であり、`IndexOutOfBoundsException`の仲間ではない。
- オーバーライドで宣言できる例外は「親のチェック例外と同じ型かそのサブタイプ」のみ。`RuntimeException`系統は無条件でどれでも宣言/未宣言でOK(このツリー全体が「狭める/広げる」の判定材料になる)。
