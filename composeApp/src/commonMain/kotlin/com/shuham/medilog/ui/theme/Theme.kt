package com.shuham.medilog.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Light Color Scheme - Primary theme for MediLog
 */
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = Secondary,
    onPrimaryContainer = Primary,
    
    secondary = Secondary,
    onSecondary = Primary,
    secondaryContainer = Secondary,
    onSecondaryContainer = Primary,
    
    tertiary = Primary,
    onTertiary = OnPrimary,
    
    background = Background,
    onBackground = TextPrimary,
    
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextSecondary,
    
    error = Error,
    onError = OnPrimary,
    errorContainer = ErrorBackground,
    onErrorContainer = Error,
    
    outline = Border,
    outlineVariant = Divider,
    
    inverseSurface = TextPrimary,
    inverseOnSurface = Background,
    inversePrimary = PrimaryDark
)

/**
 * Dark Color Scheme - For future dark mode support
 */
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = TextPrimaryDark,
    primaryContainer = SurfaceDark,
    onPrimaryContainer = PrimaryDark,
    
    secondary = SurfaceDark,
    onSecondary = TextPrimaryDark,
    secondaryContainer = SurfaceDark,
    onSecondaryContainer = PrimaryDark,
    
    tertiary = PrimaryDark,
    onTertiary = TextPrimaryDark,
    
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceDark,
    onSurfaceVariant = TextSecondaryDark,
    
    error = Error,
    onError = OnPrimary,
    errorContainer = Error,
    onErrorContainer = OnPrimary,
    
    outline = Border,
    outlineVariant = Divider,
    
    inverseSurface = Background,
    inverseOnSurface = TextPrimary,
    inversePrimary = Primary
)

/**
 * Extended colors for MediLog-specific use cases
 * (Risk levels, status indicators)
 */
data class ExtendedColors(
    val success: Color = Success,
    val onSuccess: Color = OnPrimary,
    val successContainer: Color = SuccessBackground,
    val onSuccessContainer: Color = Success,
    
    val warning: Color = Warning,
    val onWarning: Color = OnPrimary,
    val warningContainer: Color = WarningBackground,
    val onWarningContainer: Color = Warning,
    
    val highRisk: Color = Error,
    val onHighRisk: Color = OnPrimary,
    val highRiskContainer: Color = ErrorBackground,
    val onHighRiskContainer: Color = Error,
    
    val inputBackground: Color = InputBackground,
    val inputBorder: Color = InputBorder,
    val inputBorderFocused: Color = InputBorderFocused
)

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }

/**
 * MediLog Theme Composable
 * Wraps Material3 theme with extended colors
 */
@Composable
fun MediLogTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (darkTheme) {
        ExtendedColors(
            success = Success,
            warning = Warning,
            highRisk = Error,
            inputBackground = SurfaceDark
        )
    } else {
        ExtendedColors()
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = MediLogTypography,
            content = content
        )
    }
}

/**
 * Helper to access extended colors from any composable
 */
object MediLogTheme {
    val extendedColors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}
