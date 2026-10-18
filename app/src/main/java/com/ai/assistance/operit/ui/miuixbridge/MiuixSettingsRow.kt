package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.CardColors
import top.yukonga.miuix.kmp.basic.Icon
import top.yukonga.miuix.kmp.basic.Surface
import top.yukonga.miuix.kmp.preference.ArrowPreference
import top.yukonga.miuix.kmp.preference.SwitchPreference
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 设置分组容器：miuix Card 承载一组设置行（insideMargin 由行自控，此处清零）。
 */
@Composable
fun OperitSettingsGroup(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        insideMargin = PaddingValues(0.dp),
        colors = CardColors(
            color = MiuixTheme.colorScheme.surfaceContainer,
            contentColor = MiuixTheme.colorScheme.onSurface,
        ),
        content = content,
    )
}

/** 行首图标：彩色小方块包裹的 20dp 图标（延续 Operit 旧 SettingsRow 观感） */
@Composable
fun OperitSettingsRowIcon(
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(38.dp),
        shape = RoundedCornerShape(12.dp),
        color = iconTint.copy(alpha = 0.16f),
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = androidx.compose.ui.Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

/**
 * 设置行（导航/动作型）：图标 + 标题 + 副标题 + 尾部箭头。
 * 内部为 ArrowPreference，startAction 承载 Operit 风格图标。
 */
@Composable
fun OperitSettingsRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    endActions: @Composable androidx.compose.foundation.layout.RowScope.() -> Unit = {},
    bottomAction: (@Composable () -> Unit)? = null,
) {
    ArrowPreference(
        title = title,
        summary = subtitle,
        modifier = modifier,
        startAction = { OperitSettingsRowIcon(icon = icon, iconTint = iconTint) },
        endActions = endActions,
        bottomAction = bottomAction,
        onClick = onClick,
    )
}

/**
 * 设置行（开关型）：图标 + 标题 + 副标题 + miuix Switch。
 */
@Composable
fun OperitSettingsSwitchRow(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    SwitchPreference(
        checked = checked,
        onCheckedChange = onCheckedChange,
        title = title,
        summary = subtitle,
        modifier = modifier,
        startAction = { OperitSettingsRowIcon(icon = icon, iconTint = iconTint) },
    )
}
