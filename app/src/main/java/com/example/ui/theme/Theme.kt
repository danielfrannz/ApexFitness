package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AthleticDarkColorScheme = darkColorScheme(
    primary = VoltLime,
    onPrimary = VoidBlack,
    primaryContainer = TitaniumElevated,
    onPrimaryContainer = VoltLime,
    secondary = CyberCyan,
    onSecondary = VoidBlack,
    secondaryContainer = TitaniumElevated,
    onSecondaryContainer = CyberCyan,
    tertiary = ElectricOrange,
    onTertiary = VoidBlack,
    background = VoidBlack,
    onBackground = TextPrimary,
    surface = TitaniumDark,
    onSurface = TextPrimary,
    surfaceVariant = TitaniumElevated,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    outlineVariant = BorderSubtle,
    error = BurnRed,
    onError = Color.White
)

private val AthleticLightColorScheme = lightColorScheme(
    primary = VoltLimeDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF1F5F9),
    onPrimaryContainer = VoltLimeDark,
    secondary = Color(0xFF0284C7),
    onSecondary = Color.White,
    tertiary = ElectricOrange,
    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),
    surface = Color.White,
    onSurface = Color(0xFF0F172A),
    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF475569),
    outline = Color(0xFFCBD5E1),
    error = BurnRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Default to minimalist athletic dark theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AthleticDarkColorScheme else AthleticLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
