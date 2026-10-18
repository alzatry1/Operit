package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.ListPopupColumn
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.window.WindowListPopup

/**
 * DropdownMenu 的 miuix 替代（第一版）：WindowListPopup + 简单文本行。
 * 适合「点按钮弹一列文字选项」的场景；复杂条目（图标/多行）后续在迁移到具体页面时扩展。
 */
@Composable
fun OperitDropdownMenu(
    show: Boolean,
    onDismiss: () -> Unit,
    items: List<String>,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    WindowListPopup(
        show = show,
        popupModifier = modifier,
        onDismissRequest = onDismiss,
    ) {
        ListPopupColumn {
            items.forEachIndexed { index, label ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelect(index)
                            onDismiss()
                        }
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                ) {
                    Text(
                        text = label,
                        style = MiuixTheme.textStyles.main,
                        color = MiuixTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
