package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DeepSchoolBlueDark,
    secondary = LightSchoolBlueDark,
    tertiary = SchoolGoldDark,
    background = SchoolBgDark,
    surface = SchoolSurfaceDark,
    onPrimary = SchoolBgDark,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = DeepSchoolBlue,
    secondary = LightSchoolBlue,
    tertiary = SchoolGold,
    background = SchoolBgLight,
    surface = SchoolSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = SchoolTextPrimary,
    onSurface = SchoolTextPrimary,
    primaryContainer = Color(0xFFEFF6FF), // blue-50 equivalent
    onPrimaryContainer = DeepSchoolBlue, // blue-700 equivalent
    secondaryContainer = Color(0xFFDBEAFE), // blue-100 equivalent
    onSecondaryContainer = Color(0xFF1E40AF), // blue-800
    surfaceVariant = Color(0xFFF1F5F9), // slate-100 borders/background
    onSurfaceVariant = Color(0xFF64748B), // Slate-500 text
    outline = Color(0xFFE2E8F0) // slate-200 border line
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set default false to ensure we always show the School Theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
