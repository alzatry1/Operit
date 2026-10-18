package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import top.yukonga.miuix.kmp.basic.SnackbarHost
import top.yukonga.miuix.kmp.basic.SnackbarHostState

/**
 * Snackbar 桥接：旧 M3 调用形态是
 *   val state = remember { SnackbarHostState() }
 *   scope.launch { state.showSnackbar("...") }
 *   CustomScaffold(snackbarHost = { SnackbarHost(state) })
 * miuix 的 SnackbarHostState.showSnackbar(message, actionLabel, ...) 签名同构，
 * 本桥接仅做命名隔离，页面迁移时把 import 指到这里即可。
 */
typealias OperitSnackbarHostState = SnackbarHostState

@Composable
fun OperitSnackbarHost(
    hostState: OperitSnackbarHostState,
    modifier: Modifier = Modifier,
) {
    SnackbarHost(state = hostState, modifier = modifier)
}
