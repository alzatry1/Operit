package com.ai.assistance.operit.ui.theme

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.ai.assistance.operit.data.preferences.ThemePreferenceSnapshot
import com.ai.assistance.operit.data.preferences.UserPreferencesManager
import com.materialkolor.dynamiccolor.ColorSpec
import com.materialkolor.hct.Hct
import com.materialkolor.scheme.DynamicScheme
import com.materialkolor.scheme.SchemeTonalSpot
import top.yukonga.miuix.kmp.theme.darkColorScheme as miuixDarkColorScheme
import top.yukonga.miuix.kmp.theme.lightColorScheme as miuixLightColorScheme

/**
 * 非可组合主题解析器：供 WebChatHttpBridge 等无 Compose 组合环境的调用点把当前主题导出给 Web 端。
 *
 * 与 OperitTheme（组合路径）的语义对齐关系：
 * - 静态亮/暗：与 ThemeController 静态模式完全一致（同一份 miuix 默认色板 → 同一个 toM3ColorScheme 翻译点）。
 * - 自定义主色 / 系统动态（可读到 overlay 种子时）：与 miuix Monet 同一引擎（materialkolor
 *   SchemeTonalSpot / SPEC_2021 / PHONE），DynamicScheme 角色直接落到 M3 ColorScheme；
 *   角色取值与 miuix 内部 53 角色映射在 Web 端消费的 13 个字段上语义一致。
 * - 系统动态但读不到 overlay 种子（S~T 或非 Pixel 系 ROM）：用 M3 动态色（同一系统调色板来源，
 *   角色映射为 M3 官方版，与 miuix 观感有轻微差异，仅影响 Web 预览面）。
 */
fun resolveThemeColorScheme(
    context: Context,
    snapshot: ThemePreferenceSnapshot
): ColorScheme {
    val systemDarkTheme =
        (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES
    val darkTheme =
        if (snapshot.useSystemTheme) {
            systemDarkTheme
        } else {
            snapshot.themeMode == UserPreferencesManager.THEME_MODE_DARK
        }

    val scheme =
        when {
            // 1. 自定义主色 → Monet 种子（与运行时同一引擎）
            snapshot.useCustomColors && snapshot.customPrimaryColor != null ->
                monetM3ColorScheme(snapshot.customPrimaryColor, darkTheme)
            // 2. 系统动态取色（API 31+）：先按 miuix 同款方式读系统 overlay 种子
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
                readSystemPaletteSeedArgb(context)?.let { seedArgb ->
                    monetM3ColorScheme(seedArgb, darkTheme)
                } ?: if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            // 3. 静态：miuix 默认色板 → M3 翻译（与运行时 ThemeController 静态模式同值）
            darkTheme -> miuixDarkColorScheme().toM3ColorScheme(isDark = true)
            else -> miuixLightColorScheme().toM3ColorScheme(isDark = false)
        }

    return scheme.withResolverOverrides(snapshot)
}

/** Monet 引擎：与 miuix colorsFromSeed 相同的 TonalSpot/SPEC_2021/PHONE 配置，角色直落 M3 色板 */
private fun monetM3ColorScheme(seedArgb: Int, dark: Boolean): ColorScheme {
    val scheme = SchemeTonalSpot(
        sourceColorHct = Hct.fromInt(seedArgb),
        isDark = dark,
        contrastLevel = 0.0,
        specVersion = ColorSpec.SpecVersion.SPEC_2021,
        platform = DynamicScheme.Platform.PHONE,
    )
    val base = if (dark) darkColorScheme() else lightColorScheme()
    return base.copy(
        primary = Color(scheme.primary),
        onPrimary = Color(scheme.onPrimary),
        primaryContainer = Color(scheme.primaryContainer),
        onPrimaryContainer = Color(scheme.onPrimaryContainer),
        inversePrimary = Color(scheme.inversePrimary),
        secondary = Color(scheme.secondary),
        onSecondary = Color(scheme.onSecondary),
        secondaryContainer = Color(scheme.secondaryContainer),
        onSecondaryContainer = Color(scheme.onSecondaryContainer),
        tertiary = Color(scheme.tertiary),
        onTertiary = Color(scheme.onTertiary),
        tertiaryContainer = Color(scheme.tertiaryContainer),
        onTertiaryContainer = Color(scheme.onTertiaryContainer),
        background = Color(scheme.background),
        onBackground = Color(scheme.onBackground),
        surface = Color(scheme.surface),
        onSurface = Color(scheme.onSurface),
        surfaceVariant = Color(scheme.surfaceVariant),
        onSurfaceVariant = Color(scheme.onSurfaceVariant),
        surfaceTint = Color(scheme.primary),
        inverseSurface = Color(scheme.inverseSurface),
        inverseOnSurface = Color(scheme.inverseOnSurface),
        error = Color(scheme.error),
        onError = Color(scheme.onError),
        errorContainer = Color(scheme.errorContainer),
        onErrorContainer = Color(scheme.onErrorContainer),
        outline = Color(scheme.outline),
        outlineVariant = Color(scheme.outlineVariant),
        scrim = Color(scheme.scrim),
        surfaceContainerLowest = Color(scheme.surfaceContainerLowest),
        surfaceContainerLow = Color(scheme.surfaceContainerLow),
        surfaceContainer = Color(scheme.surfaceContainer),
        surfaceContainerHigh = Color(scheme.surfaceContainerHigh),
        surfaceContainerHighest = Color(scheme.surfaceContainerHighest),
        surfaceBright = Color(scheme.surfaceBright),
        surfaceDim = Color(scheme.surfaceDim),
    )
}

/** 与 miuix DynamicColors.android 相同的系统 overlay 种子读取（API 33+ 的 theme_customization_overlay_packages） */
private fun readSystemPaletteSeedArgb(context: Context): Int? {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return null
    return runCatching {
        val json = Settings.Secure.getString(
            context.contentResolver,
            "theme_customization_overlay_packages",
        ) ?: return null
        val jsonObject = org.json.JSONObject(json)
        val seedHex = jsonObject.optString("android.theme.customization.system_palette", "")
        if (seedHex.isBlank()) return null
        android.graphics.Color.parseColor(if (seedHex.startsWith("#")) seedHex else "#$seedHex")
    }.getOrNull()
}

/** 与 OperitTheme.withOperitOverrides 相同的用户键覆盖（ColorScheme 层等价物） */
private fun ColorScheme.withResolverOverrides(snapshot: ThemePreferenceSnapshot): ColorScheme {
    if (!snapshot.useCustomColors) return this
    var result = this
    snapshot.customSecondaryColor?.let { secondaryArgb ->
        result = result.copy(secondary = Color(secondaryArgb))
    }
    when (snapshot.onColorMode) {
        UserPreferencesManager.ON_COLOR_MODE_LIGHT -> result = result.copy(onPrimary = Color.White)
        UserPreferencesManager.ON_COLOR_MODE_DARK -> result = result.copy(onPrimary = Color.Black)
    }
    return result
}
