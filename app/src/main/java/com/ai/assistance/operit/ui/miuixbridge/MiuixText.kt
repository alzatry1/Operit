package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 常用 Text 封装（对齐 Operit 排版约定）：
 * 正文用 miuix textStyles.main（经桥接后等于旧 M3 bodyLarge=16sp），
 * 次级说明文字统一 onSurfaceVariantSummary。
 */

@Composable
fun MiuixBodyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MiuixTheme.textStyles.main,
        color = color,
    )
}

@Composable
fun MiuixSecondaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MiuixTheme.colorScheme.onSurfaceVariantSummary,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MiuixTheme.textStyles.body2,
        color = color,
    )
}

@Composable
fun MiuixTitleText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
) {
    Text(
        text = text,
        modifier = modifier,
        style = MiuixTheme.textStyles.title4,
        color = color,
    )
}
