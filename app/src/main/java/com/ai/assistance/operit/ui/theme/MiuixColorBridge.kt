package com.ai.assistance.operit.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import top.yukonga.miuix.kmp.theme.Colors

/**
 * 把 miuix 的 53 角色色板映射为 M3 ColorScheme。
 *
 * 这是过渡期（P2~P5）唯一的色板翻译点：未迁移的 M3 页面读 MaterialTheme.colorScheme，
 * 实际值全部由 miuix 色板派生，保证双主题下视觉一致、且不存在两套配置漂移。
 * P6 移除 M3 依赖后本文件随 MaterialTheme 提供层一起退役。
 *
 * 取舍说明：
 * - tertiary 三件套按视觉语义对齐而非名字：miuix 的 tertiaryContainer 是浅底、
 *   onTertiaryContainer 是强调色，与 M3 语义正好相反。
 * - onSurfaceVariant 取 onSurfaceSecondary（miuix 的次级内容色，最接近 M3 语义）。
 * - outlineVariant 取 dividerLine（弱分隔线语义）；scrim 取 windowDimming（遮罩语义）。
 * - surfaceBright/surfaceDim 为 M3 新增角色，miuix 无对应物，取近邻角色。
 */
fun Colors.toM3ColorScheme(isDark: Boolean): ColorScheme {
    val base = if (isDark) darkColorScheme() else lightColorScheme() // M3 基线（补齐 miuix 无对应的角色）
    return base.copy(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        inversePrimary = primaryVariant,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = onTertiaryContainer, // miuix 的 tertiary 强调色在容器内容色上
        onTertiary = tertiaryContainer,
        tertiaryContainer = tertiaryContainer,
        onTertiaryContainer = onTertiaryContainer,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceSecondary,
        surfaceTint = primary,
        inverseSurface = onBackground,
        inverseOnSurface = background,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer,
        outline = outline,
        outlineVariant = dividerLine,
        scrim = windowDimming,
        surfaceContainerLowest = surfaceVariant,
        surfaceContainerLow = surfaceContainer,
        surfaceContainer = surfaceContainer,
        surfaceContainerHigh = surfaceContainerHigh,
        surfaceContainerHighest = surfaceContainerHighest,
        surfaceBright = surfaceContainerHigh,
        surfaceDim = surface,
    )
}
