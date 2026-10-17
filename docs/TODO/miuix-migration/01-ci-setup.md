# 01 CI 准备

## 原状

- 本地克隆直接跟踪上游 `AAswordman/Operit`，无独立 fork，无法在云端自主触发构建。
- `android-build.yml` 的 push 触发分支为 `main`，产物名不含提交标识，多轮构建难以区分。

## 修改

- fork 上游为公开仓库 `alzatry1/Operit`；本地 remote 改挂：`origin` 指向 fork，`upstream` 指向上游。
- 自 `upstream/dev` 建立迁移集成分支 `feature/miuix-migration`（与 `dev` 同属长期集成分支，子任务分支再按 CONTRIBUTING.md 前缀规范派生）。
- `.github/workflows/android-build.yml`：push 触发分支改为 `feature/miuix-migration`；新增 `Compute short SHA` 步骤，产物名改为 `operit-android(-reports)-r<run_number>-<short_sha>`。
- 配置本仓库级 git 提交身份与 `gh auth git-credential` 凭据助手（不污染全局配置）。

## 预期结果

- push 到迁移分支自动触发 `assembleDebug` 基线构建且全绿（含 gradle、CMake 源码、manual-deps、STT 资产、ripgrep 五层缓存建立）。
- artifact 可下载，基线 APK 与官方版行为一致。

[DONE]
