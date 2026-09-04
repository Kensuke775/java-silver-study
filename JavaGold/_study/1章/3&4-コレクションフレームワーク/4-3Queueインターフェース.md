### 4.3 Queueインタフェース: ArrayDeque

**教科書の例(**`chap1/14`**):**

```java
1  import java.util.ArrayDeque;
2  import java.util.Queue;
3
4  public class Main {
5      public static void main(String[] args) {
6          Queue<String> queue = new ArrayDeque<>();
7          queue.add("First"); queue.offer("Second");
8          System.out.println("peek(): " + queue.peek()); // First
9          System.out.println("size(): " + queue.size()); // 2
10         System.out.println("poll(): " + queue.poll()); // First
11         System.out.println("poll(): " + queue.poll()); // Second
12         System.out.println("size(): " + queue.size()); // 0
13         System.out.println("peek(): " + queue.peek()); // null
14      // System.out.println("element(): " + queue.element());
15     }
16 }
```

**説明したポイント**

`Queue<E>`は**先入れ先出し(FIFO)**でデータを扱うコレクション。`ArrayDeque`はその代表的な実装クラス(両端キュー/スタックとしても使えるが、今回は`Queue`として利用)。

- 7行目: `add()`と`offer()`はどちらも要素を追加するメソッドで、動きとしては同じ(末尾に追加)。違いは**失敗したときの挙動**(下記表参照)。

- 8〜9行目: `peek()`は先頭の要素を**取り出さずに**覗き見るだけ。`size()`はまだ2のまま。

- 10〜11行目: `poll()`は先頭の要素を**取り出して削除**する。追加した順(`First`→`Second`)に取り出される、まさにFIFO。

- 12〜13行目: 2回`poll()`した後は空になり、`size()`は`0`。この状態で`peek()`を呼ぶと、例外にはならず**`null`が返る**。

- 14行目のコメントアウトを外すと、`element()`は`peek()`と同じく「先頭を覗くだけ(取り出さない)」メソッドだが、**空の場合の挙動が**`peek()`**と違い、**`NoSuchElementException`**がスローされる**(javacで検証済み)。

**メソッドの対応表(戻り値の型・成功時/失敗時の値、javacで検証済み)**


| 操作        | 例外を投げる版     | 戻り値の型     | 成功時         | 失敗時(空/満杯)                            | 安全な版       | 戻り値の型     | 成功時         | 失敗時(空/満杯) |
| --------- | ----------- | --------- | ----------- | ------------------------------------ | ---------- | --------- | ----------- | --------- |
| 追加        | `add(e)`    | `boolean` | `true`      | `IllegalStateException`(容量制限がある実装のみ) | `offer(e)` | `boolean` | `true`      | `false`   |
| 削除して取得    | `remove()`  | `E`       | 削除した先頭要素    | `NoSuchElementException`             | `poll()`   | `E`       | 削除した先頭要素    | `null`    |
| 覗き見(取得のみ) | `element()` | `E`       | 先頭要素(削除しない) | `NoSuchElementException`             | `peek()`   | `E`       | 先頭要素(削除しない) | `null`    |


**実際に動かした出力例**(`add`/`offer`の戻り値、空の状態での`remove`/`element`の例外も含めて検証)

```java
Queue<String> queue = new ArrayDeque<>();
boolean r1 = queue.add("First");
boolean r2 = queue.offer("Second");
System.out.println("add() 戻り値: " + r1);        // true
System.out.println("offer() 戻り値: " + r2);      // true

String p1 = queue.peek();
String e1 = queue.element();
System.out.println("peek() 戻り値: " + p1);        // First
System.out.println("element() 戻り値: " + e1);    // First(peekと同じ。取り出してはいない)
System.out.println("size(): " + queue.size());     // 2 (peek/elementは取り出さないのでサイズ不変)

String poll1 = queue.poll();
String poll2 = queue.poll();
System.out.println("poll() 1回目: " + poll1);      // First
System.out.println("poll() 2回目: " + poll2);      // Second
System.out.println("size(): " + queue.size());     // 0

System.out.println("空でpeek(): " + queue.peek()); // null(例外にならない)

// 空の状態でremove()/element()を呼ぶとどちらも例外
queue.remove();    // NoSuchElementException
queue.element();   // NoSuchElementException
```

出力結果:

```
add() 戻り値: true
offer() 戻り値: true
peek() 戻り値: First
element() 戻り値: First
size(): 2
poll() 1回目: First
poll() 2回目: Second
size(): 0
空でpeek(): null
空でremove(): NoSuchElementException
空でelement(): NoSuchElementException
```

`ArrayDeque`はサイズ無制限なので`add()`と`offer()`はどちらも`true`を返す(容量制限のある実装、例えば`ArrayBlockingQueue`で容量オーバー時に差が出る)が、「空の状態から取り出す/覗く」場合は`remove()`/`element()`が例外、`poll()`/`peek()`が`null`、という違いは`ArrayDeque`でも常に表れる。`element()`は`remove()`と同様、空なら例外になる点に注意(`peek()`と混同しないこと)。

**セクションの核心**

同じ操作(追加/削除/覗き見)に対して「例外を投げる版」と「null/false等の安全な値を返す版」の2種類が用意されている、という設計思想がポイント。「空かどうか分からない状況でとりあえず取得を試みたい」なら`poll()`/`peek()`、「空であってはならない(空なら異常事態として検知したい)」場面なら`remove()`/`element()`を使う、という使い分けになる。

---

### `ArrayDeque`を`Deque`本来の使い方・Stackとして使う(`chap1/15`)

**教科書の例(**`chap1/15`**):**

```java
1  import java.util.ArrayDeque;
2  import java.util.Deque;
3
4  public class Main {
5      public static void main(String[] args) {
6          // Double-ended queue
7          Deque<String> deque = new ArrayDeque<>();
8          deque.add("A"); deque.addFirst("B"); deque.addLast("C");
9          System.out.println("-- Deque -- " + deque);
10         System.out.println("remove()     : " + deque.remove());
11         System.out.println("removeFirst(): " + deque.removeFirst());
12         System.out.println("removeLast() : " + deque.removeLast());
13         System.out.println("isEmpty()    : " + deque.isEmpty());
14         // Stack
15         Deque<String> stack = new ArrayDeque<>();
16         stack.push("First"); stack.push("Second");
17         System.out.println("-- Stack -- " + stack);
18         System.out.println("pop()    : " + stack.pop());
19         System.out.println("pop()    : " + stack.pop());
20         System.out.println("isEmpty(): " + stack.isEmpty());
21     }
22 }
```

実行結果(javacで検証済み):

```
-- Deque -- [B, A, C]
remove()     : B
removeFirst(): A
removeLast() : C
isEmpty()    : true
-- Stack -- [Second, First]
pop()    : Second
pop()    : First
isEmpty(): true
```

**説明したポイント**

- `Queue`のメソッド(`add`/`remove`など、`First`/`Last`が付かないもの)は「暗黙に`Last`側へ追加、`First`側から取得」という、`Deque`の機能のうち片方の組み合わせだけを固定して使っているにすぎない。`add("A")`は実質`addLast("A")`と同じ。

- 8行目: `add("A")`→末尾に追加、`addFirst("B")`→先頭に追加、`addLast("C")`→末尾に追加、という順で操作すると、結果は`[B, A, C]`になる(`add`は`addLast`と同じ側に追加するため、`A`と`C`はどちらも末尾側に入り、後から追加された`C`が一番後ろに来る)。

- 10〜12行目: `remove()`(Queue由来、実質`removeFirst()`と同じ)、`removeFirst()`、`removeLast()`をそれぞれ呼ぶと、`B`→`A`→`C`の順に取り出され、最終的に空になる。

- 15〜16行目: `push("First")`→先頭に追加(`addFirst`相当)、続けて`push("Second")`→さらにその先頭に追加。結果は`[Second, First]`(後から`push`したものが一番前に来る、スタックらしい積み上がり方)。

- 18〜19行目: `pop()`(`removeFirst`相当)を2回呼ぶと、後から積んだ`Second`が先に取り出され、続いて`First`が取り出される。まさに**LIFO(後入れ先出し)**の動き。

**セクションの核心**

`ArrayDeque`は1つのクラスでありながら、使うメソッドの組み合わせ次第で「`Queue`(FIFO)」にも「`Deque`(両端自由)」にも「`Stack`(LIFO)」にも姿を変える。`Queue`風のメソッド(`add`/`poll`など)は「末尾に追加・先頭から取得」の1パターンに固定された`Deque`の一部、`push`/`pop`は「先頭に追加・先頭から取得」という別の1パターンに固定された、また別の`Deque`の一部、という捉え方をすると、メソッドが多く見えても整理しやすい。

**疑問(問題14-2・15-1・15-2をめぐる深掘り)**:

- 質問: `add`と`push`はどちらも「追加」なのに、なぜ結果の並びが変わるのか?
- 回答: `add`は末尾側(`addLast`相当)、`push`は先頭側(`addFirst`相当)に追加するという、**追加する端が逆**だから。`poll`(Queue由来)と`pop`(Stack由来)はどちらも先頭側から取得する点で同じだが、追加する側が違うため、`add`+`poll`の組み合わせ(FIFO)と`push`+`pop`の組み合わせ(LIFO)で取り出される順序が変わる。

- 質問: `Queue`だけを使っていると「末尾から取り出す」という選択肢自体が存在しないのはなぜか?
- 回答: `Queue`インタフェースには`removeLast()`のような「Last」を明示するメソッドが定義されていないため。「末尾から取り出したい」場合は`Queue`の範囲を超えて`Deque`が追加提供する`removeLast()`/`pollLast()`を明示的に呼ぶ必要がある。

**参考: `Deque`の`descendingIterator()`と`Iterator`の`next()`**

`descendingIterator()`は`Deque`専用のメソッドで、「末尾側から先頭側へ向かって」辿る`Iterator`を返す(通常の`iterator()`は先頭→末尾の順)。

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.add(1); deque.add(2); deque.add(3);   // [1, 2, 3]
Iterator<Integer> it = deque.descendingIterator();
while (it.hasNext()) {
    System.out.print(it.next() + " ");
}
// 出力: 3 2 1
```

`Iterator`の`next()`は「今のカーソル位置の要素を取り出して、カーソルを1つ次に進める」というメソッド。`Queue`の`poll()`のように元のコレクションから要素を削除するわけではなく、あくまで`Iterator`という別オブジェクトが内部に持つ「今どこまで読んだか」というカーソル位置が進むだけで、`deque`本体の中身は変わらない。`hasNext()`(次があるか確認)と`next()`(取得しつつ1つ進める)をペアで使うのが基本形。

---

疑問点や、実際に出た問題で迷ったところがあれば、随時ここに追記していきます。