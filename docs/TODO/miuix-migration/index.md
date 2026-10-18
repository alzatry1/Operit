---
title: Miuix 主题体系迁移
status: in_progress
---

# Miuix 主题体系迁移

## 原状

- 应用 UI 全量基于 androidx.compose.material3（ui 树 528 个 Kotlin 文件中 295 个直接 import material3，`MaterialTheme.colorScheme` 直接读取约 3653 处）。
- 主题系统为自研 `OperitTheme` + 112 键 `ThemePreferenceSnapshot`（角色卡/群组作用域），支持动态取色、自定义双主色、背景图/视频、双液态玻璃（kyant backdrop + fletchmckee liquid）。
- 导航为完全自研路由栈（`AppRouterState` + 反射式 `ScreenRouteRegistry` + `AppContent` 转场引擎），不依赖 androidx.navigation 页面路由。
- 悬浮窗体系（6 个 WindowManager 悬浮窗）使用独立的 `FloatingWindowTheme`（硬编码 M3 lightColorScheme）或无主题包装。
- 构建链：AGP 8.13.2 + Kotlin 2.2.21 + Compose BOM 2026.02.01，本机低配无法编译，编译/打包全部走 GitHub Actions。

## 意图

- 将 UI 组件体系从 Material3 全量迁移到 miuix（top.yukonga.miuix.kmp，0.9.4），终态不依赖 material3。
- 主线采用 Kotlin 2.4.21 + Compose 1.12.x 升级后接入 miuix 0.9.4；附录 B（miuix 0.7.2 免升级）作为阻断时的备用路线。
- 保留自研导航、主题快照数据流与液态玻璃能力；全部 miuix 调用收敛进 `ui/miuixbridge/` 适配层。
- fork 仓库公开，利用 GitHub Actions 免费额度承担全部构建验证。

## 作用域

- P0 仓库与 CI 准备：fork、迁移分支、workflow 定制、基线构建。
- P1 工具链升级：Kotlin 2.4.21 + Compose 1.12.x，业务代码零改动。
- P2 主题桥接：OperitTheme 内嵌 MiuixTheme，M3 色板由 miuix Colors 派生，示范页迁移。
- P3 设置体系 23 页迁移。
- P4 业务页面约 35 页迁移。
- P5 聊天 + 悬浮窗 + 外壳迁移。
- P6 去 M3 终态：全仓清扫 material3 依赖。

## 非目标

- 不引入 miuix-nav，保留自研导航。
- 不改动业务逻辑、数据层、工具系统与插件协议。
- 不向本机引入本地编译要求。

## 验证

- 每个 Phase 遵循「本机改码 → 静态自查 → 云端构建 → 真机验收 → TODO 标 [DONE]」闭环。
- P0 验收：`assembleDebug` 云端构建绿，基线 APK 可安装可聊天。
- 详细方案见工作区《Operit×Miuix集成方案.md》（5094 行）。

- [DONE] 01 CI 准备
- [DONE] 02 工具链升级
- [DONE] 03 主题桥接与示范页
