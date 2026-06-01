# 🔮 XiaoLiuRen Skill — 小六壬演算法使用指南

## 用途

需要在 Android 專案中加入小六壬占卜功能時參考。

## 功能

| 方法 | 說明 |
|------|------|
| `timeDivination(month, day, hour)` | 時間起卦 |
| `numberDivination(number)` | 數字起卦（直接除6） |
| `numberDigitSumDivination(number)` | 數字起卦（各位相加） |
| `getShenName(n)` | 六神名稱 |
| `getDuanYu(n)` | 六神斷語 |

## 使用方式

將 `Tools/xiaoliuren/` 下的兩個 .kt 檔案複製到專案中：

```
YourProject/app/src/main/java/com/yourpkg/
├── XiaoLiuRen.kt    ← 核心演算法
└── AlmanacData.kt   ← 農民曆數據
```

## 範例

```kotlin
// 時間起卦：農曆五月十五日，午時
val result = XiaoLiuRen.timeDivination(5, 15, 7)
// → (5+15+7) % 6 = 1 → 大安

// 數字起卦
val result2 = XiaoLiuRen.numberDivination(12345)
// → 12345 % 6 = 3 → 速喜
```

## 六神一覽

| # | 名稱 | 五行 | 方位 | 吉凶 |
|---|------|------|------|------|
| 1 | 大安 | 木 | 東方 | 吉 |
| 2 | 留連 | 木 | 北方 | 平 |
| 3 | 速喜 | 火 | 南方 | 吉 |
| 4 | 赤口 | 金 | 西方 | 凶 |
| 5 | 小吉 | 水 | 中央 | 吉 |
| 6 | 空亡 | 土 | 四方 | 凶 |

## 依賴

需搭配 `lunar-calendar/` 工具取得農曆月日。

## 相關 Tools

- `../Tools/xiaoliuren/XiaoLiuRen.kt`
- `../Tools/xiaoliuren/AlmanacData.kt`
