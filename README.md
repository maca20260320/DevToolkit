# 🧰 DevToolkit — 跨平台開發工具庫

從實戰專案中提煉出來的可複用資產。
嚴格區分 **Tools（程式碼）** 和 **Skills（流程知識）**。

---

## 📐 分類規則

```
DevToolkit/
├── {Platform}/
│   ├── Tools/      ← 可直接複用的程式碼檔案
│   └── Skills/     ← 流程知識、最佳實踐、排錯指南
```

### Tools = 什麼？

**可直接複製到專案中使用的程式碼檔案。**

| 類型 | 副檔名 | 範例 |
|------|--------|------|
| 程式碼模組 | `.kt` `.swift` `.py` `.js` | LunarCalendar.kt |
| 設定模板 | `.gradle` `.xml` `.json` | build.gradle.template |
| 腳本 | `.sh` `.bat` `.ps1` | build.sh |
| 資源檔 | `.png` `.svg` | ic_launcher_foreground.png |

**命名規則：**
- 檔名保持原始格式（不做額外包裝）
- 每個 Tool 資料夾內可放相關檔案組
- 不放 README——README 屬於 Skills

### Skills = 什麼？

**流程知識文件，描述「怎麼做」和「為什麼」。**

| 類型 | 副檔名 | 範例 |
|------|--------|------|
| 操作流程 | `.md` | apk-build.md |
| 排錯指南 | `.md` | debug-checklist.md |
| 環境設定 | `.md` | env-setup.md |
| 架構說明 | `.md` | architecture.md |

**命名規則：**
- 檔名用 kebab-case：`apk-build.md`
- 內容包含：用途、步驟、注意事項、版本紀錄
- 可引用 Tools 中的具體檔案路徑

### 怎麼判斷歸類？

```
問自己：「這個東西能不能直接 import / include / copy 到專案？」
  → 能 → Tools
  → 不能，是知識/流程 → Skills
```

---

## 📁 目錄總覽

### Android

**Tools/**
| 檔案 | 說明 | 依賴 |
|------|------|------|
| `lunar-calendar/LunarCalendar.kt` | 公曆↔農曆轉換 (1900-2100) | 無 |
| `lunar-calendar/ChineseHour.kt` | 十二時辰對照表 | 無 |
| `lunar-calendar/SolarTerm.kt` | 二十四節氣計算 | 無 |
| `xiaoliuren/XiaoLiuRen.kt` | 小六壬核心演算法 | 無 |
| `xiaoliuren/AlmanacData.kt` | 農民曆宜忌數據 | 無 |

**Skills/**
| 檔案 | 說明 |
|------|------|
| `apk-build.md` | APK 標準化編譯流程 (8步+環境版本) |
| `lunar-calendar.md` | 農曆庫使用說明+已知限制 |
| `xiaoliuren.md` | 小六壬演算法說明+六神一覽 |

### iOS / Windows / Common

待整理，結構同上：`{Platform}/Tools/` + `{Platform}/Skills/`

---

## 🔧 編譯環境（Android 固定版本）

| 項目 | 版本 |
|------|------|
| Java | OpenJDK 17.0.19 |
| Gradle (wrapper) | 8.5 |
| AGP | 8.2.0 |
| Kotlin | 1.9.0 |
| compileSdk / targetSdk | 34 |
| minSdk | 21 |

---

## 📝 新增資產流程

1. 判斷類型：Tools or Skills？
2. 放到正確的 `{Platform}/Tools/` 或 `{Platform}/Skills/`
3. 更新本 README 的目錄總覽
4. Git commit + push

---

## 📋 版本紀錄

| 日期 | 版本 | 內容 |
|------|------|------|
| 2026-06-01 | v1.0 | 初版，匯入 Android Tools + Skills |
| 2026-06-01 | v1.1 | 重組目錄，Tools/Skills 嚴格分離 |
