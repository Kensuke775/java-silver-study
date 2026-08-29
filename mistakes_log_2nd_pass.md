# 間違いログ（「2周目」オリジナル問題）

chapterN_practice/problems_v1.md・problems_v2.md（2周目トラック）で不正解だった問題と、「不安」タグが付いた問題の一覧。

## ❌ 不正解だった問題

| 章 | 問題 | 回答 | 正解 |
|---|---|---|---|
| 2 | [問題2](chapter2_practice/problems_v1.md#q2) | E, I, J | B, E, I |
| 2 | [問題7](chapter2_practice/problems_v1.md#q7) | D | A |
| 2 | [問題9](chapter2_practice/problems_v1.md#q9) | B, C, E | D, F |
| 2 | [問題15](chapter2_practice/problems_v1.md#q15) | D | B |
| 2 | [問題22](chapter2_practice/problems_v1.md#q22) | B, C, F | B, D, F |
| 2 | [問題25](chapter2_practice/problems_v1.md#q25) | A | E |
| 2 | [問題5-2](chapter2_practice/problems_v1.md#q5-2) | E, F | C, F |
| 2 | [問題6-2](chapter2_practice/problems_v1.md#q6-2) | B, C | B, C, E |
| 2 | [問題7-2](chapter2_practice/problems_v1.md#q7-2) | A, C, D, E | A, D |
| 2 | [問題7-4](chapter2_practice/problems_v1.md#q7-4) | B | C |
| 2 | [問題8-2](chapter2_practice/problems_v1.md#q8-2) | A, B, C, D | B, D, E |
| 2 | [問題11-1](chapter2_practice/problems_v1.md#q11-1) | A, B, C, E | A, C, E |
| 2 | [問題11-2](chapter2_practice/problems_v1.md#q11-2) | B, C, D | B, C, D, E |
| 2 | [問題14-1](chapter2_practice/problems_v1.md#q14-1) | C, E | A, C, E |
| 2 | [問題14-2](chapter2_practice/problems_v1.md#q14-2) | B, C, E | B, D, E |
| 2 | [問題14-α](chapter2_practice/problems_v1.md#q14-alpha) | A, B, C, E, F, G | A, B, C, E, G |
| 2 | [問題ex8-1](chapter2_practice/problems_v2.md#qex8-1) | A | C |
| 2 | [問題ex9-5](chapter2_practice/problems_v2.md#qex9-5) | A | B |
| 2 | [問題ex11-2](chapter2_practice/problems_v2.md#qex11-2) | B | A |
| 2 | [問題ex15-2](chapter2_practice/problems_v2.md#qex15-2) | B | C |
| 3 | [問題2-2](chapter3_practice/problems_v1.md#q2-2) | A | B |
| 3 | [問題2-3](chapter3_practice/problems_v1.md#q2-3) | A | B |
| 3 | [問題4-4](chapter3_practice/problems_v1.md#q4-4) | A | B |
| 3 | [問題12-2](chapter3_practice/problems_v1.md#q12-2) | D | C |
| 3 | [問題ex3-3](chapter3_practice/problems_v1.md#qex3-3) | B | C |
| 3 | [問題ex3-4](chapter3_practice/problems_v1.md#qex3-4) | C | B |
| 3 | [問題ex4-3](chapter3_practice/problems_v1.md#qex4-3) | B | D |
| 3 | [問題ex7-4](chapter3_practice/problems_v1.md#qex7-4) | B | C |
| 3 | [問題5-2](chapter3_practice/problems_v1.md#q5-2) | B | D |
| 3 | [問題17-2](chapter3_practice/problems_v1.md#q17-2) | B | C |
| 3 | [問題17-4](chapter3_practice/problems_v1.md#q17-4) | D | A |
| 3 | [問題19-4](chapter3_practice/problems_v1.md#q19-4) | A | B |
| 3 | [問題21-4](chapter3_practice/problems_v1.md#q21-4) | B | A |
| 3 | [問題ex9-2](chapter3_practice/problems_v1.md#qex9-2) | A | B |
| 3 | [問題ex12-4](chapter3_practice/problems_v1.md#qex12-4) | A | C |
| 3 | [問題ex16-4](chapter3_practice/problems_v1.md#qex16-4) | D | C |

## ⚠️ 不安だった問題

正誤に関わらず、「迷ったポイント」欄に「不安」というタグを含めた問題。

| 章 | 問題 | 迷ったポイント |
|---|---|---|
| 3 | [問題2-3](chapter3_practice/problems_v1.md#q2-3) | 不安。「`b++`が暗黙的にキャストされる」という理解自体は正しかった(ただし最初shortと言い間違えた点は口頭で訂正済み、byteとshortは別の型)。ただしそのキャストが「128をそのまま収める」のではなく、byteの表現範囲(-128〜127)を超えた分がオーバーフローして下限の-128に折り返る、という点を見落としていた。追加検証: `(byte)(b + 1)`のように暗黙キャストを手動で書くと、同じ`127 + 1`でも`int`のまま(キャストなし)なら`128`になるが、`byte`にキャストした瞬間だけラップアラウンドして`-128`になる(`byte b=127; byte b_2=(byte)(b+1); System.out.println(127+1); System.out.println(b_2);` → `128` / `-128`) |
| 3 | [問題6-4](chapter3_practice/problems_v1.md#q6-4) | 不安。「100がbyte型、200がshort型でキャッシュの有無が型によって決まる」という誤った理由づけをしていた。実際は100/200はどちらも`int`であり、キャッシュされるかどうかは元の型ではなく、オートボクシングされる**値**が-128〜127の範囲に収まるかどうかで決まる点を訂正説明した |
| 3 | [問題8-1](chapter3_practice/problems_v1.md#q8-1) | 不安。追加検証: `+`は`==`より優先順位が高いので、`"..." + s1 == s2`のように括弧なしで書くと`("..." + s1) == s2`(文字列連結の結果とs2を参照比較)という意味になり、`s1.equals(s2)`を書いたつもりでも全く別の式になる(`System.out.println("s1.equals(s2) : " + s1 == s2);` → `false`。括弧を付けた`"s1.equals(s2) : " + s1.equals(s2)`なら意図通り`true`) |
| 3 | [問題8-4](chapter3_practice/problems_v1.md#q8-4) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題10-1](chapter3_practice/problems_v1.md#q10-1) | 不安。「`float f = 10;`の10はdoubleを経由してfloatになるのか」という質問があり、実際は`int→double`を経由せず直接`int→float`のワイドニングが行われる点、`double`が登場するのは次の行の`float→double`代入時である点を補足説明した |
| 3 | [問題10-2](chapter3_practice/problems_v1.md#q10-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題10-3](chapter3_practice/problems_v1.md#q10-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題10-4](chapter3_practice/problems_v1.md#q10-4) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題12-1](chapter3_practice/problems_v1.md#q12-1) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題12-2](chapter3_practice/problems_v1.md#q12-2) | 不安。「アンボクシングでNullPointerExceptionが起きる」という理解自体は正しかったが、発生箇所を「println実行時」だと考えており、実際はアンボクシングが起きる`int i = obj;`の代入の行そのもので例外が発生する点(println行までは到達しない)を見落としていた |
| 3 | [問題12-3](chapter3_practice/problems_v1.md#q12-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題12-4](chapter3_practice/problems_v1.md#q12-4) | 不安。「文字列を`final`で宣言すれば結果が変わるか」という質問があり、`final`は変数の再代入を禁止するだけでオブジェクト自体の挙動やメソッド呼び出し結果には影響しないため、`final`有無に関わらず同じ例外が発生する点を検証の上で補足説明した(`final String s = "Duke"; s.charAt(10);`でも同じ例外を確認済み) |
| 3 | [問題14-2](chapter3_practice/problems_v1.md#q14-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題14-3](chapter3_practice/problems_v1.md#q14-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題16-2](chapter3_practice/problems_v1.md#q16-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題18-2](chapter3_practice/problems_v1.md#q18-2) | 不安。「コンパイルエラーになる基準/ならない基準」について質問があり、Javaのコンパイラは型の整合性のみを静的にチェックし、参照がnullかどうかという値の妥当性は(ソースコード上明らかにnullでないと読み取れる場合でも)一切考慮せず常に実行時任せになる、という一般原則を補足説明した(参考として、Java21以降の`case null ->`構文にも軽く触れた) |
| 3 | [問題18-4](chapter3_practice/problems_v1.md#q18-4) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題20-1](chapter3_practice/problems_v1.md#q20-1) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題20-2](chapter3_practice/problems_v1.md#q20-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題20-3](chapter3_practice/problems_v1.md#q20-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題20-4](chapter3_practice/problems_v1.md#q20-4) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex4-3](chapter3_practice/problems_v1.md#qex4-3) | 不安。「コンパイルエラーか実行時エラーか曖昧」と自覚した上でコンパイルエラー側を選んだが、実際はコンパイラが変数の宣言型(Object[])しか見ておらず、Integer は Object のサブクラスなので代入自体は型として正当と判断されコンパイルは通る。配列の実体(String[])との食い違いはJVMが実行時にしか検知できないため、ArrayStoreExceptionは実行時にスローされる、という「コンパイル時は宣言型ベース、実行時は実体ベース」の2段階チェックのズレを補足説明した |
| 3 | [問題ex11-3](chapter3_practice/problems_v1.md#qex11-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex11-4](chapter3_practice/problems_v1.md#qex11-4) | 不安。「varが推論できる/できない」の分かれ目を「配列の外側の型と中身」という捉え方で混同しかけたため、`{1,2,3}`単体は独立した型を持つ「式」ではなく、宣言時に左辺の型と組み合わさったときだけ意味を持つ特殊な初期化子であり、`new int[]{...}`にして初めて独立した型を持つ式になる、という点を補足説明した |
| 3 | [問題ex15-2](chapter3_practice/problems_v1.md#qex15-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex15-3](chapter3_practice/problems_v1.md#qex15-3) | 不安。「実行時に例外がスローされる」という方向性は正しく選べたが、正確な例外クラス名(StringIndexOutOfBoundsException)ははっきり覚えていなかった |
| 3 | [問題ex15-4](chapter3_practice/problems_v1.md#qex15-4) | 不安(正解ではあったが、本人から不安の申告あり)。関連してチャット内で、toString()/replace()/substring()などが「一度作られたオブジェクトが共有プールに保存されて使い回される」という誤解が生じたため、文字列プールはリテラル専用の仕組みであり、実行時にメソッドから返される文字列は(equals()の結果が同じでも)毎回別オブジェクトとして生成される点を、v4==v5の参照比較(false)を示して補足説明した |
| 3 | [問題ex15-8](chapter3_practice/problems_v1.md#qex15-8) | 不安、チェック。「同じオブジェクト(sb1)から呼んでいるなら同じ結果になるのでは」という仮説を検証したところ誤りで、toString()は呼び出し元が同じでも毎回新しいオブジェクトを作ることを確認した |
| 3 | [問題ex19-1](chapter3_practice/problems_v1.md#qex19-1) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex19-2](chapter3_practice/problems_v1.md#qex19-2) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex19-3](chapter3_practice/problems_v1.md#qex19-3) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex19-4](chapter3_practice/problems_v1.md#qex19-4) | 不安(正解ではあったが、本人から不安の申告あり) |
| 3 | [問題ex19-5](chapter3_practice/problems_v1.md#qex19-5) | 不安(正解ではあったが、本人から不安の申告あり)。ex19-4(後置i--)との対比として、ユーザー自身の希望で追加 |
| 3 | [問題5-4](chapter3_practice/problems_v1.md#q5-4) | 不安。 |
| 3 | [問題9-1](chapter3_practice/problems_v1.md#q9-1) | 不安。配列の代入方法が曖昧だった。追加検証: `null`の配列を`println()`にそのまま渡すと、例外にはならず文字列`"null"`がそのまま出力される(`println(Object)`が呼ばれ、内部で`String.valueOf(obj)`のように扱われるため。`int[] arr = null; System.out.println(arr);` → `null`) |
| 3 | [問題9-2](chapter3_practice/problems_v1.md#q9-2) | 不安。 |
| 3 | [問題9-3](chapter3_practice/problems_v1.md#q9-3) | 不安。 |
| 3 | [問題11-4](chapter3_practice/problems_v1.md#q11-4) | 不安。 |
| 3 | [問題13-1](chapter3_practice/problems_v1.md#q13-1) | 不安。 |
| 3 | [問題13-4](chapter3_practice/problems_v1.md#q13-4) | 不安。 |
| 3 | [問題17-1](chapter3_practice/problems_v1.md#q17-1) | 不安。 |
| 3 | [問題17-2](chapter3_practice/problems_v1.md#q17-2) | 不安。エスケープシーケンスは「バックスラッシュの記号ごと画面に出る」のではなく「別の意味に変換されて、変換後の結果だけが残る」仕組みだということを理解しておらず、`\`自体も一緒に表示されると誤解していた。 |
| 3 | [問題17-4](chapter3_practice/problems_v1.md#q17-4) | 不安。チェック。`(b = 99) > 0`という代入を含む式がそもそも構文として合法かどうか自信が持てず、コンパイルエラーだと誤答した。実際は代入式自体は合法な`boolean`式としてコンパイルが通るが、`a <= 10`がfalseのため短絡評価で右側が丸ごと評価されず、bは初期値0のまま変わらない。 |
| 3 | [問題19-2](chapter3_practice/problems_v1.md#q19-2) | 不安。 |
| 3 | [問題19-4](chapter3_practice/problems_v1.md#q19-4) | 不安。`default`がソースコード上で先頭に書かれているため、そこから実行が始まると誤解していた。実際は一致するcase("B")があるので、defaultは経由せず直接case "B"から実行が始まる。defaultが使われるのは、どのcaseにも一致しなかった場合だけ。 |
| 3 | [問題21-2](chapter3_practice/problems_v1.md#q21-2) | 不安。 |
| 3 | [問題ex6-1](chapter3_practice/problems_v1.md#qex6-1) | 不安。 |
| 3 | [問題ex13-3](chapter3_practice/problems_v1.md#qex13-3) | 不安。代入がif評価の「後」に起きるのか「最中」に起きるのか曖昧だった(正しくは代入という行為そのものが条件の値を決めている、代入と評価は同時)。 |
| 3 | [問題ex18-1](chapter3_practice/problems_v1.md#qex18-1) | 不安。 |

