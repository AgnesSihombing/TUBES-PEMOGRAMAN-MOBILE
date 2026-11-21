package com.kampus.sigapapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SigapColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = PrimarySoft,
    onPrimaryContainer = PrimaryDark,
    inversePrimary = BrandSecondary,

    secondary = BrandSecondary,
    onSecondary = TextDark,
    secondaryContainer = ChipInactive,
    onSecondaryContainer = PrimaryDark,

    tertiary = BrandAccent,
    onTertiary = TextDark,
    tertiaryContainer = ChipWaitingBg,
    onTertiaryContainer = ChipWaitingText,

    background = AppBackground,
    onBackground = TextDark,

    surface = AppSurface,
    onSurface = TextDark,
    surfaceVariant = FieldBg,
    onSurfaceVariant = TextGray,
    surfaceTint = BrandPrimary,
    surfaceBright = AppSurface,
    surfaceContainerLowest = AppSurface,
    surfaceContainerLow = AppBackground,
    surfaceContainer = AppSurface,
    surfaceContainerHigh = FieldBg,
    surfaceContainerHighest = ChipInactive,

    inverseSurface = TextDark,
    inverseOnSurface = AppBackground,

    error = DangerRed,
    onError = Color.White,
    errorContainer = ChipRejectedBg,
    onErrorContainer = ChipRejectedText,

    outline = LineColor,
    outlineVariant = StepInactive,
    scrim = Color.Black
)

@Composable
fun SigapTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = SigapColorScheme, content = content)
}