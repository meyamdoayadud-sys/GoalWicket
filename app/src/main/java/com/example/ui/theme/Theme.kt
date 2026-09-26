package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SportsPitchGreen,
    onPrimary = Color(0xFF003818),
    primaryContainer = Color(0xFF005225),
    onPrimaryContainer = Color(0xFF86FFAA),
    secondary = SportsElectricCyan,
    onSecondary = Color(0xFF00344F),
    secondaryContainer = Color(0xFF004D73),
    onSecondaryContainer = Color(0xFFBFE9FF),
    tertiary = SportsCricketAmber,
    background = SportsDarkBg,
    onBackground = SportsTextPrimary,
    surface = SportsSurface,
    onSurface = SportsTextPrimary,
    surfaceVariant = SportsSurfaceVariant,
    onSurfaceVariant = SportsTextSecondary,
    outline = SportsCardBorder,
    error = SportsLiveRed
)

private val LightColorScheme = DarkColorScheme // Sports live score apps are best in immersive Dark stadium mode

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
