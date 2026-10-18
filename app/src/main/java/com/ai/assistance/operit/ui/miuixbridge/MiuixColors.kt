package com.ai.assistance.operit.ui.miuixbridge

import androidx.compose.ui.graphics.Color

/**
 * 色板工具（miuixbridge 共享）。
 * 注意：Theme.kt 内的 isColorLight 是它的私有实现（状态栏逻辑专用），
 * 本文件给迁移页面/桥接层提供公共版本。
 */

/** 判断颜色是否较浅（WCAG 亮度近似公式） */
fun Color.isLight(): Boolean {
    val luminance = 0.299 * red + 0.587 * green + 0.114 * blue
    return luminance > 0.5
}

/** 按背景色取可读的前景文字色（黑或白） */
fun Color.readableContentColor(): Color = if (isLight()) Color.Black else Color.White

/** 两色线性插值 */
fun lerpColor(start: Color, end: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = start.red + (end.red - start.red) * f,
        green = start.green + (end.green - start.green) * f,
        blue = start.blue + (end.blue - start.blue) * f,
        alpha = start.alpha + (end.alpha - start.alpha) * f,
    )
}
