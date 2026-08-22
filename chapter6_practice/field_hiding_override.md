# 6章 フィールド隠蔽 × オーバーライド 問題集

## 前提知識メモ

- フィールドアクセスは常に**宣言型基準**（動的束縛なし）。3階層あっても同じ
- メソッド内の無修飾フィールドは、呼び出し元の型ではなく、**そのメソッドが定義されているクラス自身**のフィールドを指す
- `super.メソッド()`は**直近の親（1階層）だけ**を遡る。祖先を連鎖して全部実行されるわけではない
- static変数・staticメソッドの解決も宣言型基準

## 問題1

```java
class Base {
    int x = 10;
    static String label = "Base";

    int getX() {
        return x;
    }

    static String getLabel() {
        return label;
    }
}

class Middle extends Base {
    int x = 20;
    static String label = "Middle";

    int getX() {
        return x;
    }
}

class Leaf extends Middle {
    int x = 30;

    int getX() {
        return x + super.getX();
    }
}

public class Main {
    public static void main(String[] args) {
        Base b = new Leaf();
        Middle m = new Leaf();
        Leaf l = new Leaf();

        System.out.println(b.x);          // ①
        System.out.println(m.x);          // ②
        System.out.println(l.x);          // ③
        System.out.println(b.getX());     // ④
        System.out.println(m.getX());     // ⑤
        System.out.println(b.label);      // ⑥
        System.out.println(Middle.label); // ⑦
    }
}
```

①〜⑦の出力をそれぞれ答えてください。

---

## 解答

```
① 10
② 20
③ 30
④ 50
⑤ 50
⑥ Base
⑦ Middle
```

`Leaf.getX()` = `this.x(30) + super.getX()(Middle.getX()が返す20)` = 50。`super.getX()`は直近の親Middleだけを呼び、Baseまで連鎖しない。`b.getX()`と`m.getX()`は宣言型が違っても実体が同じLeafなら同じ結果になる。

javac/javaで実機検証済み。
