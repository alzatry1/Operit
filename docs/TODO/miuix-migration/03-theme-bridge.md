# 03 主题桥接与示范页

## 原状

- `OperitTheme` 直出 M3 ColorScheme（动态取色/自研双主色派生算法），全局无 miuix 主题提供层。
- 页面骨架 `CustomScaffold` 包装 M3 Scaffold；关于页全部使用 M3 组件（AlertDialog ×4、Dialog ×2、Switch、ListItem 等）。

## 修改

- 新增 `ui/theme/MiuixColorBridge.kt`：miuix 53 角色 → M3 ColorScheme 的唯一翻译点（tertiary 按视觉语义互换、onSurfaceVariant←onSurfaceSecondary、outlineVariant←dividerLine、scrim←windowDimming 等，取舍依据见文件注释）。
- 新增 `ui/theme/MiuixTextStylesBridge.kt`：M3 Typography 15 档 → miuix TextStyles 14 档；createCustomTypography 的字体族与 fontScale 经桥接无损进入 miuix。
- `Theme.kt` 改造：新增 `rememberMiuixThemeController`（自定义主色 → Monet 种子；API 31+ 未自定义 → MonetSystem；否则静态亮/暗），组合期 `currentColors()` 解析后叠加 `withOperitOverrides`（custom_secondary_color 仅覆盖 secondary/secondaryVariant；on_color_mode 显式时覆盖 onPrimary）；`MiuixTheme(colors, textStyles)` 嵌套于 MaterialTheme 外层，MaterialTheme 色板改为 `miuixColors.toM3ColorScheme(darkTheme)`（有背景图时 surface 系 8 色 alpha 归 1 的逻辑保留为 `withBackgroundAlphaFix`）；状态栏/导航栏着色改读 miuix 色板；自研派生算法与 M3 静态基线全部删除。
- `ThemeColorSchemeResolver.kt` 重写：非可组合路径与运行时同引擎（materialkolor SchemeTonalSpot/SPEC_2021/PHONE，种子直落 M3 色板）；静态路径走 miuix 默认色板 + 同一翻译点；系统动态优先读 overlay 种子（与 miuix 同法），读不到用 M3 动态色（差异仅影响 Web 预览面）。
- `CustomScaffold` 换芯为 miuix Scaffold（签名不变，91 处调用方零改动；contentWindowInsets 维持清零；contentColor 双写 miuix/M3 两套 LocalContentColor）。miuix Scaffold 自带 MiuixPopupHost，Overlay 弹层全局就位。
- 新增 `ui/miuixbridge/` 适配层：MiuixDialog（WindowDialog 确认式）/MiuixSettingsRow（Card 组 + Arrow/SwitchPreference 行）/MiuixTopBar/MiuixText/MiuixSnackbar（SnackbarHostState 同构桥接）/MiuixBottomSheet/MiuixChip（Card 自研）/MiuixMenu（WindowListPopup）/MiuixColors（公共色彩工具）。
- 示范页迁移：`AboutScreen.kt` + `OpenSourceLicenses.kt` 全量迁移（SettingsGroup→Card、SettingsRow→ArrowPreference/SwitchPreference、6 个对话框→WindowDialog/OperitDialog、进度件/分隔线/按钮全部换 miuix），业务逻辑（更新状态机/镜像测速）零改动。

## 预期结果

- 关于页呈现完整 HyperOS 观感；其余未迁移页面读派生 M3 色板，视觉与迁移前一致。
- 自定义主色/动态取色/暗色/自定义字体/背景图视频/液态玻璃等 112 键能力不丢。

[DONE]

实测记录：首轮 WindowDialog 尾随 lambda 少 2 个闭括号（语法错误，本机静态自查已能发现，已修）；次轮三处编译错误（foundation `weight` 误 import、OverlayBottomSheet content 无 ColumnScope、materialkolor 系 miuix runtime 依赖需显式引入、快照属性智能转型）——修复后 run 39107815129 全绿（22m30s）。
