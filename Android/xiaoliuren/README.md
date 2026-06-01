# 🔮 XiaoLiuRen — 小六壬核心演算法

傳統小六壬占卜的純 Kotlin 實現。

## 功能

| 方法 | 說明 |
|------|------|
| `timeDivination(month, day, hour)` | 時間起卦 |
| `numberDivination(number)` | 數字起卦（直接除6） |
| `numberDigitSumDivination(number)` | 數字起卦（各位相加） |
| `getShenName(n)` | 六神名稱 |
| `getShenColor(n)` | 六神顏色 |
| `getJiXiongText(n)` | 吉凶文字 |
| `getDuanYu(n)` | 六神斷語 |
| `getWuXing(n)` | 五行 |
| `getFangWei(n)` | 方位 |

## 六神一覽

| # | 名稱 | 五行 | 方位 | 吉凶 |
|---|------|------|------|------|
| 1 | 大安 | 木 | 東方 | 吉 |
| 2 | 留連 | 木 | 北方 | 平 |
| 3 | 速喜 | 火 | 南方 | 吉 |
| 4 | 赤口 | 金 | 西方 | 凶 |
| 5 | 小吉 | 水 | 中央 | 吉 |
| 6 | 空亡 | 土 | 四方 | 凶 |

## 使用方式

將 `XiaoLiuRen.kt` 複製到你的 Android 專案中。

```kotlin
// 時間起卦：農曆五月十五日，午時(7)
val result = XiaoLiuRen.timeDivination(5, 15, 7)
// → (5+15+7) % 6 = 1 → 大安

// 數字起卦
val result2 = XiaoLiuRen.numberDivination(12345)
// → 12345 % 6 = 3 → 速喜
```
