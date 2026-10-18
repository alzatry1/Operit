package com.ai.assistance.operit.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import top.yukonga.miuix.kmp.basic.FabPosition
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.theme.MiuixTheme

/**
 * 自定义 Scaffold，自动禁用内容窗口插图以避免系统栏重复边距问题
 *
 * 迁移说明（P2 换芯）：内部实现由 M3 Scaffold 换成 miuix Scaffold，包名/签名不变，
 * 91 处调用方零改动；miuix Scaffold 自带 popupHost（MiuixPopupHost），Overlay 弹层
 * 在所有使用本组件的页面即刻可用。contentColor 同时注入 miuix 与 M3 两套
 * LocalContentColor，保证过渡期内混排组件的文字色一致。
 */
@Composable
fun CustomScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MiuixTheme.colorScheme.background,
    contentColor: Color = MiuixTheme.colorScheme.onBackground,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0), // 自动禁用内容窗口插图
    ) { paddingValues ->
        CompositionLocalProvider(
            top.yukonga.miuix.kmp.theme.LocalContentColor provides contentColor,
            androidx.compose.material3.LocalContentColor provides contentColor,
        ) {
            content(paddingValues)
        }
    }
}
