package com.ai.assistance.operit.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.material3.Typography
import top.yukonga.miuix.kmp.theme.TextStyles
import top.yukonga.miuix.kmp.theme.defaultTextStyles

/**
 * M3 Typography（15 档）→ miuix TextStyles（14 档）桥接。
 *
 * createCustomTypography 已把字体族与 fontScale 烙进 M3 Typography 的每一档，
 * 经此桥接后 miuix TextStyles 自动继承同一字体与缩放，自定义字体能力零丢失。
 * 字号以 Operit 既有观感为准（bodyLarge=16sp 作为正文基准，而非 miuix 默认的 17sp）。
 */
fun Typography.toMiuixTextStyles(): TextStyles = defaultTextStyles(
    main = bodyLarge, // 正文基准
    paragraph = bodyLarge.copy(lineHeight = 1.2f.em),
    body1 = bodyMedium,
    body2 = bodySmall,
    button = labelLarge,
    footnote1 = labelMedium,
    footnote2 = labelSmall,
    headline1 = headlineSmall,
    headline2 = titleMedium,
    subtitle = titleSmall.copy(fontWeight = FontWeight.Bold),
    title1 = displaySmall,
    title2 = headlineMedium,
    title3 = headlineSmall,
    title4 = titleLarge,
)

@Composable
fun rememberMiuixTextStyles(customTypography: Typography): TextStyles =
    remember(customTypography) { customTypography.toMiuixTextStyles() }
