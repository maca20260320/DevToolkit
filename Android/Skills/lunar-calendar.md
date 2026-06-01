# 🌙 Lunar Calendar Skill — 農曆轉換庫使用指南

## 用途

需要在 Android 專案中加入農曆轉換功能時參考。

## 功能

- `solarToLunar(year, month, day)` → 農曆日期 + 天干地支
- 十二時辰映射
- 二十四節氣計算

## 使用方式

將 `Tools/lunar-calendar/` 下的三個 .kt 檔案複製到專案中：

```
YourProject/app/src/main/java/com/yourpkg/
├── LunarCalendar.kt    ← 公曆↔農曆
├── ChineseHour.kt      ← 時辰對照
└── SolarTerm.kt        ← 節氣計算
```

## 範例

```kotlin
val lunar = LunarCalendar.solarToLunar(2026, 6, 1)
println("${lunar.yearGanZhi} ${lunar.month}月${lunar.day}日")
// → 丙午年 五月初六

val hour = ChineseHour.getHourInfo(14)  // 未時
val term = SolarTerm.getCurrentSolarTerm(2026, 6, 1)
```

## ⚠️ 已知限制

- 範圍：1900-2100 年（超出拋 IllegalArgumentException）
- SolarTerm 精度 ±1 天

## 修復紀錄

- v1.3: `lunarYearDays` 位元遍歷 bug 修復（step 1 → shl）
- v1.3: 月份迭代復月份迭代（閏月後跳月）

## 相關 Tools

- `../Tools/lunar-calendar/LunarCalendar.kt`
- `../Tools/lunar-calendar/ChineseHour.kt`
- `../Tools/lunar-calendar/SolarTerm.kt`
