# 6章 sealed / permits 問題集

## 前提知識メモ

- 修飾子の並び順は自由（`sealed abstract`でも`abstract sealed`でも同じ）
- `permits`は省略可能。ただし「直接の子が全員、同一ソースファイル内に揃っている」ときだけ。パッケージが同じかは無関係で、何人いても省略できる（1人限定ではない）
- `permits`に書けるのは**直接の子のみ**。孫（間接の子孫）を書くと無効
- sealedの直接の子には`final`・`sealed`・`non-sealed`のいずれかが必須
- `permits`を省略しても、sealedの強制力（部外者を拒否する強さ）は明示時と完全に同じ。単なる省略記法にすぎない

## 問題1

以下A〜Fのうち、**コンパイルが成功するものをすべて**選んでください。

**A.**
```java
public sealed class Shape permits Circle, Square {}
final class Circle extends Shape {}
final class Square extends Shape {}
```

**B.**
```java
sealed abstract class Vehicle permits Car {}
non-sealed class Car extends Vehicle {}
```

**C.**
```java
abstract sealed class Animal permits Dog {}
final class Dog extends Animal {}
```

**D.**
```java
sealed class Fruit {}
final class Apple extends Fruit {}
final class Banana extends Fruit {}
```

**E.**
```java
sealed class Bird permits HouseSparrow {}
final class Sparrow extends Bird {}
final class HouseSparrow extends Sparrow {}
```

**F.**
```java
sealed class Beverage permits Coffee {}
class Coffee extends Beverage {}
```

---

## 解答

| 問題 | 正解 |
|---|---|
| 1 | A, B, C, D |

すべてjavac(--release 17)で実機検証済み。
