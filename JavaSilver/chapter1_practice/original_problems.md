## 目次

**問題一覧**

- [原本1-1](#q1-1)
- [原本1-2](#q1-2)
- [原本1-3](#q1-3)
- [原本1-4](#q1-4)
- [原本1-5](#q1-5)
- [原本1-6](#q1-6)

<a id="q1-1"></a>
## 原本1-1

JDKで提供されるツールはどれですか。（2つ選択）

A. Java SE
B. JVM
C. IDE
D. コンパイラ
E. main()メソッド

**実施記録**

回答：B, D
正解：B, D
迷ったポイント：なし

<a id="q1-2"></a>
## 原本1-2

Javaアプリケーションの実行時に呼ばれるメソッドとして、正しいものはどれですか。（3つ選択）

A. `public static void main(String[] args) {}`
B. `public static void main(String args) {}`
C. `public void main(String[] args) {}`
D. `public static main(String[] args) {}`
E. `public static void main(String... s) {}`
F. `public static void main(String args[]) {}`

**実施記録**

回答：A, E, F
正解：A, E, F
迷ったポイント：なし

<a id="q1-3"></a>
## 原本1-3

Javaアプリケーションの実行方法として、正しいものはどれですか。（2つ選択）

A. `>java Main`
B. `>java Main.java`
C. `>java Main.class`
D. `>javac Main`
E. `>javac Main.java`
F. `>javac Main.class`

**実施記録**

回答：A, B
正解：A, B
迷ったポイント：なし

<a id="q1-4"></a>
## 原本1-4

次のソースファイルのうち、コンパイルが成功するファイルはどれですか。（3つ選択）

A. ソースファイル名：Hello.java
```java
class Hello {}
class Main {}
class Test {}
```

B. ソースファイル名：Hello.java
```java
class Hello {}
public class Main {}
```

C. ソースファイル名：Hello.java
```java
public class Hello {}
```

D. ソースファイル名：Main.java
```java
public class Main {}
class Hello {}
```

E. ソースファイル名：Main.java
```java
public class Hello {}
public class Main {}
```

**実施記録**

回答：C, D, E
正解：A, C, D
迷ったポイント：Aを見落とし（publicクラスが1つもないファイルでも非publicクラスだけならコンパイルできる）、Eを誤って含めた（1ファイルにpublicトップレベルクラスは1つまでというルール違反でコンパイルエラー）。

<a id="q1-5"></a>
## 原本1-5

パッケージの説明として誤っているものはどれですか。（2つ選択）

A. ソースファイルを管理する仕組み
B. クラスファイルを管理する仕組み
C. 1つのソースファイルに1つのパッケージ宣言のみできる
D. 複数の異なるソースファイルに、同じ名前のパッケージ宣言は記述できない
E. パッケージ宣言が異なっていれば、同じ名前のクラスを宣言できる

**実施記録**

回答：C, E
正解：A, D
迷ったポイント：C・Eはどちらも正しい記述なのに誤りだと判断してしまった。実際に誤っているのはA（パッケージが管理するのは実態としてはクラス〈ファイル〉であり、Bの方が正しい説明）とD（同じパッケージ名を複数の異なるソースファイルで使うのはむしろ普通に可能）。

<a id="q1-6"></a>
## 原本1-6

パッケージ宣言とインポート宣言について、正しい記述はどれですか。すべての記述はソースファイルの先頭で行われているものとします。（2つ選択）

A.
```java
package com.p;
import com.a.*;
```
B.
```java
import com.a.*;
package com.p;
```
C.
```java
package com.p;
import com.*.*;
```
D.
```java
package com.*;
import com.a.Sample;
```
E.
```java
package com.p1;
package com.p2;
import com.a.*;
```
F.
```java
package com.p;
import com.a.Sample;
import com.a1.*;
import com.a2.*;
```

**実施記録**

回答：A, D
正解：A, F
迷ったポイント：Dを正しいと誤判定した（パッケージ宣言にワイルドカードは使えない）。Fを見落とした（package宣言の後に単一クラス指定＋ワイルドカード×2のimportを並べるのは有効）。
