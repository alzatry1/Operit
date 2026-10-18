package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardColors
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * FilterChip/AssistChip 的 miuix 替代（miuix 无 Chip 组件）：
 * 基于 Card 的选中态变色小胶囊。按压反馈由 miuix 全局 Indication 提供。
 */
@Composable
fun OperitFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val containerColor =
        if (selected) MiuixTheme.colorScheme.primary
        else MiuixTheme.colorScheme.surfaceContainerHigh
    val contentColor =
        if (selected) MiuixTheme.colorScheme.onPrimary
        else MiuixTheme.colorScheme.onSurface
    Card(
        modifier = modifier,
        cornerRadius = 8.dp,
        colors = CardColors(
            color = if (enabled) containerColor else MiuixTheme.colorScheme.disabledSecondary,
            contentColor = if (enabled) contentColor else MiuixTheme.colorScheme.disabledOnSecondary,
        ),
        insideMargin = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 6.dp),
        onClick = if (enabled) onClick else null,
    ) {
        Text(text = label, style = MiuixTheme.textStyles.footnote1)
    }
}
