package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.yukonga.miuix.kmp.overlay.OverlayBottomSheet
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 统一底部弹层（OverlayBottomSheet 封装）。
 * 依赖最近的 miuix Scaffold（CustomScaffold 已内建 popupHost）。
 */
@Composable
fun OperitBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    OverlayBottomSheet(
        show = show,
        modifier = modifier,
        title = title,
        backgroundColor = MiuixTheme.colorScheme.surface,
        onDismissRequest = onDismiss,
        content = content,
    )
}
