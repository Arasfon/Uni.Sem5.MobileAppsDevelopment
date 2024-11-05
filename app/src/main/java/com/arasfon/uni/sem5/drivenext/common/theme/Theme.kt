package com.arasfon.uni.sem5.drivenext.common.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = LightText,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = DarkText,
    secondary = Secondary,
    onSecondary = LightText,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = Accent,
    background = Background,
    onBackground = DarkText,
    surface = SecondaryContainerHigh,
    surfaceContainer = SecondaryContainer,
    surfaceContainerHigh = SecondaryContainerHigh,
    surfaceContainerHighest = SecondaryContainerHighest,
    onSurfaceVariant = OnSurfaceVariant,
    outline = OnSurfaceVariant,
    outlineVariant = OutlineVariant,
    error = Error
)

@Composable
fun DriveNextTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
