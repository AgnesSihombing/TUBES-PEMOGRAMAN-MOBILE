package com.kampus.sigap.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val SigapColorScheme = lightColorScheme(
    primary = SigapPrimary,
    background = SigapBackground,
    surface = SigapBackground,
    onBackground = SigapText,
    onSurface = SigapText
)

@Composable
fun SigapTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SigapColorScheme,
        typography = Typography,
        content = content
    )
}