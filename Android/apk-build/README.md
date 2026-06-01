# 🔨 Android APK Build — 標準化編譯流程

從零開始建構 Android APK 的標準流程，
經過 HelloWorld → 小六壬 專案驗證。

## 環境需求

| 項目 | 版本 | 說明 |
|------|------|------|
| Java | OpenJDK 17.0.19 | Homebrew: `brew install openjdk@17` |
| Gradle | 8.5 (wrapper) | 自動下載，不需手動安裝 |
| AGP | 8.2.0 | build.gradle 中設定 |
| Kotlin | 1.9.0 | build.gradle 中設定 |
| compileSdk | 34 | Android SDK |
| minSdk | 21 | 支援 Android 5.0+ |
| targetSdk | 34 | Android 14 |

## 8 步標準流程

```
1. mkdir MyApp && cd MyApp
2. 建立 build.gradle, settings.gradle, gradle.properties
3. 設定 Gradle Wrapper (gradle-wrapper.properties + 腳本)
4. 建立 app/build.gradle (設定 SDK + 依賴)
5. 建立 AndroidManifest.xml
6. 撰寫 MainActivity.kt + layout XML
7. ./gradlew clean assembleDebug
8. 複製 APK → 交付
```

## JAVA_HOME 設定 (macOS)

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"
```

## build.gradle 範本

```groovy
plugins {
    id 'com.android.application'
    id 'kotlin-android'
}
android {
    namespace = 'com.example.myapp'
    compileSdk 34
    defaultConfig {
        applicationId "com.example.myapp"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "v1.0"
    }
    buildTypes {
        release { minifyEnabled false }
        debug { minifyEnabled false }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = '17' }
}
dependencies {
    implementation 'androidx.core:core-ktx:1.10.0'
    implementation 'androidx.appcompat:appcompat:1.6.0'
    implementation 'com.google.android.material:material:1.9.0'
}
```

## ⚠️ 注意事項

- Material Design 依賴不可省略（會報 resource not found）
- Gradle Wrapper 腳本必須有執行權限：`chmod +x gradlew`
- Kotlin 1.9.0 需搭配 AGP 8.2.0，不可隨意升級
