package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = PrimarySaffron,
    onPrimary = Color(0xFF1E1508),
    primaryContainer = DarkSoftPeach,
    onPrimaryContainer = SaffronLight,
    secondary = SaffronLight,
    onSecondary = DarkInkBackground,
    background = DarkInkBackground,
    onBackground = DarkTextPrimary,
    surface = DarkInkSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkInkSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkInkBorder
)

private val LightColorScheme = lightColorScheme(
    primary = DeepKesari,
    onPrimary = Color.White,
    primaryContainer = SoftPeach,
    onPrimaryContainer = DeepKesari,
    secondary = PrimarySaffron,
    onSecondary = MainText,
    background = MainBackground,
    onBackground = MainText,
    surface = MainSurface,
    onSurface = MainText,
    surfaceVariant = SoftPeachLight,
    onSurfaceVariant = SecondaryText,
    outline = SubtleBorder
)

@Composable
fun ConceptXTheme(
    darkTheme: Boolean = false, // Default to false for the warm white, saffron & deep kesari aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
