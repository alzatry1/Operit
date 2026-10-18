# 02 工具链升级（Kotlin 2.4.21 + Compose 1.12.1 + AGP 9.4.1）

## 原状

- Kotlin 2.2.21、Compose BOM 2026.02.01（1.10.x）、AGP 8.13.2、Gradle 8.13、compileSdk 36。
- miuix 0.9.4 以 Kotlin 2.4.21 + Compose Multiplatform 1.12.x 构建，元数据版本无法被 Kotlin 2.2 读取。

## 修改

- Kotlin 2.2.21 → 2.4.21（含 serialization 插件同升、kotlinx-serialization 1.9.0 → 1.11.0）；kapt 类路径钉住 `kotlin-metadata-jvm` 与编译器同版本（Room 2.8.4 自带的元数据库 2.2.0 读不动 Kotlin 2.4 输出的 2.4.0 元数据）。
- Compose BOM 2026.02.01 → 2026.09.00（ui/foundation/animation/runtime 全量 1.12.1）；6 个散钉 1.10.4 的 compose `-android` 变体同步钉 1.12.1；`material3-window-size-class` 换 miuix 同链路 JetBrains 坐标 1.12.0-alpha03。
- Compose 1.12 的 AAR 元数据硬性要求 minAgpVersion 9.1.0 + minCompileSdk 37（不可抑制），触发 R-03 预案：AGP 8.13.2 → 9.4.1、Gradle 8.13 → 9.8.1（官方分发 + sha256 校验）、compileSdk 36 → 37（app 与全部库模块）、CI 增装 `platforms;android-37.0` 与 `build-tools;37.0.0`（SDK 平台包已进入次要版本命名时代）。
- AGP 9 适配：全模块移除 `org.jetbrains.kotlin.android` 插件（内置 Kotlin 接管）；kapt 改由 `com.android.legacy-kapt`（版本随 AGP）承接；ObjectBox 5.3.0 → 5.4.2（AGP 9 支持）；`applicationVariants` 旧 Variant API 产物改名块删除（所改名字即默认名，纯冗余）；`project.exec`/`buildDir` 等 Gradle 9 移除 API 改写（ProcessBuilder / layout.buildDirectory）；`buildFeatures.resValues = true`（buildType 内 resValue 需要）；删除 dragonbones 冗余 `sourceSets["main"]` 访问器；删除两处从不存在的 consumer-rules.pro 声明（terminal 子模块经 fork `alzatry1/OperitTerminalCore` 的 `agp9-compat` 分支承接，gitlink 基于上游 pin 0458689 叠加）。
- tensorflow-lite 2.10.0 → 2.16.1（AGP 9 命名空间唯一性强校验：2.10 的 tensorflow-lite 与 tensorflow-lite-api 共用 `org.tensorflow.lite`；app 代码不直接用 TFLite，经 mediapipe 传递）。
- 移除未使用的 accompanist-systemuicontroller 依赖及关于页对应致谢条目。
- 引入 miuix 0.9.4 四坐标（miuix-ui/miuix-preference/miuix-icons/miuix-blur 的 `-android` 变体），Manifest 以 `tools:overrideLibrary` 覆盖 miuix-blur 的 minSdk 33（运行时由 `isRuntimeShaderSupported()` 门控）。

## 预期结果

- 四次 CI 构建全绿（Kotlin 升级、Compose+AGP 升级、miuix 依赖解析），产物 APK 真机冒烟通过。

[DONE]

实测记录：Kotlin 提交首轮 Room kapt 元数据失败，钉 kotlin-metadata-jvm 后转绿（30m07s）；Compose 提交首轮 AAR 元数据硬校验失败（minAgp 9.1.0 / minCompileSdk 37），AGP 9 改造链经 5 轮迭代转绿（23m04s）；miuix 依赖提交一轮通过（25m05s）。
