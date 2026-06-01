# 🧰 DevToolkit — 跨平台開發工具庫

各平台可複用的 Tools / Skills / Scripts / Templates，
從實戰專案中提煉出來的通用模組。

---

## 📁 目錄結構

```
DevToolkit/
├── Android/               Android 平台工具
│   ├── apk-build/         APK 標準化建構流程 (Gradle + AGP)
│   ├── lunar-calendar/    農曆轉換庫 (1900-2100)
│   └── xiaoliuren/        小六壬核心演算法（可獨立複用）
│
├── iOS/                   iOS 平台工具（待整理）
│
├── Windows/               Windows 平台工具（待整理）
│
└── Common/                跨平台通用
    ├── scripts/           通用腳本
    └── templates/         專案模板
```

## 🔧 Android 工具清單

| 工具 | 說明 | 來源專案 |
|------|------|---------|
| `apk-build/` | 標準 APK 編譯環境 + 8 步流程 | HelloWorld / 小六壬 |
| `lunar-calendar/` | 公曆↔農曆轉換 (查找表法) | 小六壬 v1.3 |
| `xiaoliuren/` | 小六壬核心演算法（六神、斷語、五行） | 小六壬 v1.4 |

## 📌 編譯環境（Android 固定版本）

| 項目 | 版本 |
|------|------|
| Java | OpenJDK 17.0.19 |
| Gradle (wrapper) | 8.5 |
| AGP | 8.2.0 |
| Kotlin | 1.9.0 |
| compileSdk / targetSdk | 34 |
| minSdk | 21 |

## 📝 使用方式

每個工具資料夾內都有獨立的 `README.md` 說明用法，
可以直接複製到新專案中使用。

## 📋 版本紀錄

| 日期 | 版本 | 內容 |
|------|------|------|
| 2026-06-01 | v1.0 | 初版建立，匯入 Android 工具 |
