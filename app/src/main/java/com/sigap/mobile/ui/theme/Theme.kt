package com.sigap.mobile.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Color scheme: komponen Material 3 otomatis memakai warna ini
private val LightColors = lightColorScheme(
    primary = SigapTeal,
    onPrimary = Color.White,
    primaryContainer = SigapTealLight,
    background = SigapBackground,
    surface = Color.White,
    outline = SigapBorder,
    error = StatusDitolakFg
)

// Pembungkus seluruh aplikasi agar semua layar memakai tema yang sama
@Composable
fun SIGAPTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}