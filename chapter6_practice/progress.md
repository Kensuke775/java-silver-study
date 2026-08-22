# 6章 応用問題 進捗管理

原本の6章末問題（6-1〜6-24）の番号と、`chapter6_practice/`内の応用問題ファイルの対応状況。

| 原本の番号 | テーマ | 応用ラウンドの状況 | ファイル |
|---|---|---|---|
| 6-1 | sealedの修飾子 | ✅ 済み | `sealed_permits.md` |
| 6-2 | フィールド隠蔽 vs オーバーライド | ✅ 済み | `field_hiding_override.md` |
| 6-3 | protectedの公開範囲 | ✅ 済み | `access_modifiers.md`問題1 |
| 6-4 | package-privateと別パッケージ継承 | ✅ 済み | `access_modifiers.md`問題2〜5 |
| **6-5** | **オーバーロードの判定基準** | **❌ 未実施** | ー |
| **6-6** | **privateメソッドと動的束縛** | **❌ 未実施**（原本の初回セッションで深掘り済み） | ー |
| 6-7 | コンストラクタ連鎖 | ✅ 済み | `constructors.md`問題1 |
| 6-8 | コンストラクタ呼び出し(this/super) | ✅ 済み | `constructors.md`問題2〜5 |
| 6-9〜6-12 | record関連 | ✅ 済み | `records.md` |
| 6-13, 6-14 | 抽象メソッド・defaultメソッド衝突 | ✅ 済み | `interface_abstract.md` |
| **6-15** | **interfaceのstaticメンバ** | **❌ 未実施** | ー |
| 6-16 | sealedのpermits整合性 | ✅ 済み（`sealed_permits.md`に含む） | `sealed_permits.md` |
| **6-17** | **instanceofと継承チェーン** | **❌ 未実施**（原本の初回セッションでex17を手動実験のみ） | ー |
| **6-18** | **ダウンキャストの危険性** | **❌ 未実施**（原本の初回セッションでex18を手動実験のみ） | ー |
| **6-19〜6-24** | **コレクション（ArrayList/Set/Map/generics）** | **❌ 未実施** | ー |

## 実施済みファイルの正答状況（実施記録より）

| ファイル | 実施回数 | 直近の結果 |
|---|---|---|
| `interface_abstract.md` | 1回目（2026-08-22） | 5問中3問完答（2, 4, 5）。1, 3で復習ポイントあり |
| `records.md` | 1回目（2026-08-22） | 3問中2問完答（2, 3）。1でBを見落とし |
| `sealed_permits.md` | 未実施 | ー |
| `field_hiding_override.md` | 未実施 | ー |
| `access_modifiers.md` | 未実施 | ー |
| `constructors.md` | 未実施 | ー |

## 次にやること

原本の番号順で進めるなら、次は**6-5（オーバーロードの判定基準）**の応用問題から。
