package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.IconButton
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * Operit 统一顶栏（miuix SmallTopAppBar 封装）：
 * - 对齐 Operit 的返回箭头与透明模式（toolbar_transparent / 自定义 AppBar 色由调用方决定后传入）
 * - 页面迁移时用它替换 M3 TopAppBar，行为保持一致
 */
@Composable
fun OperitTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String = "",
    transparent: Boolean = false,
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    SmallTopAppBar(
        title = title,
        subtitle = subtitle,
        modifier = modifier,
        color = if (transparent) Color.Transparent else MiuixTheme.colorScheme.surface,
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = navigationIcon,
                        contentDescription = null,
                        tint = if (transparent) MiuixTheme.colorScheme.onBackground else MiuixTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        actions = actions,
    )
}
