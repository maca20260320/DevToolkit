# 🌙 Lunar Calendar — 農曆轉換庫

公曆 ↔ 農曆轉換，支援 1900-2100 年。

## 功能

- `solarToLunar(year, month, day)` → 農曆日期
- 天干地支年/月/日
- 生肖
- 農曆月/日中文名

## 使用方式

將 `LunarCalendar.kt` 和 `ChineseHour.kt` 複製到你的 Android 專案中。

```kotlin
// 公曆轉農曆
val lunar = LunarCalendar.solarToLunar(2026, 6, 1)
println("${lunar.yearGanZhi} ${lunar.month}月${lunar.day}日")
// → 丙午年 五月初六

// 取得農曆月名
val name = LunarCalendar.getMonthName(5, false)  // "五月"

// 取得農曆日名
val dayName = LunarCalendar.getDayName(15)  // "十五"

// 取得生肖
val sx = LunarCalendar.getShengXiao(2026)  // "馬"
```

## ⚠️ 已知限制

- 1900 年前或 2100 年後會拋出 `IllegalArgumentException`
- SolarTerm.kt 使用近似天文演算法，精度 ±1 天

## 修復紀錄

- v1.3: 修復 `lunarYearDays` 位元遍歷 bug（step 1 → shl）
- v1.3: 修復月份迭代邏輯（閏月後跳月）
