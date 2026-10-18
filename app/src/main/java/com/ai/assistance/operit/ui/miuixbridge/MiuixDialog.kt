package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.TextButton
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.window.WindowDialog

/**
 * miuixbridge 统一确认/信息对话框（WindowDialog 封装）。
 *
 * 覆盖 Operit 旧 M3 AlertDialog 的典型形态：标题 + 自定义内容 + 确认/取消按钮。
 * WindowDialog 是独立窗口弹层，不依赖 Scaffold 的 popupHost，任意页面可用。
 */
@Composable
fun OperitDialog(
    show: Boolean,
    title: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    summary: String? = null,
    confirmText: String? = null,
    onConfirm: (() -> Unit)? = null,
    confirmEnabled: Boolean = true,
    dismissText: String? = null,
    onDismissAction: (() -> Unit)? = null,
    enableWindowDim: Boolean = true,
    content: (@Composable ColumnScope.() -> Unit)? = null,
) {
    WindowDialog(
        show = show,
        modifier = modifier,
        title = title,
        titleColor = MiuixTheme.colorScheme.onSurface,
        summary = summary,
        summaryColor = MiuixTheme.colorScheme.onSurfaceVariantSummary,
        backgroundColor = MiuixTheme.colorScheme.surface,
        enableWindowDim = enableWindowDim,
        onDismissRequest = onDismiss,
    ) {
        if (content != null) {
            Column(modifier = Modifier.fillMaxWidth(), content = content)
        }
        if (confirmText != null || dismissText != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                if (dismissText != null) {
                    TextButton(
                        text = dismissText,
                        onClick = { (onDismissAction ?: onDismiss).invoke() },
                    )
                }
                if (confirmText != null) {
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    TextButton(
                        text = confirmText,
                        onClick = { onConfirm?.invoke() },
                        enabled = confirmEnabled,
                    )
                }
            }
        }
    }
}
