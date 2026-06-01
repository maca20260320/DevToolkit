# 🔨 APK Build Skill — 標準化編譯流程

從零建構 Android APK 的標準流程，經過 HelloWorld → 小六壬 驗證。

## 用途

需要新開 Android 專案或重新建構 APK 時參考此流程。

## 環境需求

| 項目 | 版本 | 說明 |
|------|------|------|
| Java | OpenJDK 17.0.19 | `brew install openjdk@17` |
| Gradle | 8.5 (wrapper) | 自動下載 |
| AGP | 8.2.0 | build.gradle |
| Kotlin | 1.9.0 | build.gradle |
| compileSdk | 34 | Android SDK |
| minSdk | 21 | Android 5.0+ |

## 8 步流程

```
1. mkdir MyApp && cd MyApp
2. 建立 build.gradle, settings.gradle, gradle.properties
3. 設定 Gradle Wrapper (gradle-wrapper.properties + 腳本)
4. 建立 app/build.gradle → 參考 Tools/apk-build/build.gradle.template
5. 建立 AndroidManifest.xml
6. 撰寫 MainActivity.kt + layout XML
7. ./gradlew clean assembleDebug
8. 複製 APK → 交付
```

## JAVA_HOME (macOS)

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"
```

## ⚠️ 注意事項

- Material Design 依賴不可省略（報 resource not found）
- Gradle Wrapper 腳本需 `chmod +x gradlew`
- Kotlin 1.9.0 搭配 AGP 8.2.0，不可隨意升級

## 相關 Tools

- `../Tools/apk-build/build.gradle.template` — build.gradle 範本
