package com.lalessio.patientkiosk.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KioskColorScheme = lightColorScheme(
    //--- Azione primaria: il rosso ---
    primary = Accent,
    onPrimary = Ground,
    primaryContainer = AccentTint,
    onPrimaryContainer = AccentDeep,
    inversePrimary = AccentLight,

    //--- Secondario e terziario: schema mono, tutto sull'inchiostro ---
    secondary = Ink,
    onSecondary = Ground,
    secondaryContainer = Neutral200,
    onSecondaryContainer = Ink,
    tertiary = Ink,
    onTertiary = Ground,
    tertiaryContainer = Neutral200,
    onTertiaryContainer = Ink,

    //--- Fondi ---
    background = Ground,
    onBackground = Ink,
    surface = Ground,
    onSurface = Ink,
    surfaceVariant = Surface,
    onSurfaceVariant = InkMuted,
    //Material tinge di primary le superfici "sollevate": qui il design è piatto
    surfaceTint = Color.Transparent,

    //--- Livelli di superficie usati dai componenti Material ---
    surfaceBright = Ground,
    surfaceDim = Neutral200,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Ground,
    surfaceContainer = Surface,
    surfaceContainerHigh = Neutral200,
    surfaceContainerHighest = Neutral300,

    //--- Inversi: usati da snackbar e tooltip ---
    inverseSurface = Ink,
    inverseOnSurface = Ground,

    //--- Bordi ---
    outline = Divider,
    outlineVariant = Neutral300,

    //--- Errori: rosso profondo, leggibile a testo piccolo ---
    error = AccentDeep,
    onError = Ground,
    errorContainer = AccentTint,
    onErrorContainer = AccentDeep,

    scrim = Ink,
)

/**
 * Un solo tema chiaro. Niente dynamicColor: prenderebbe i colori dallo sfondo
 * del telefono e cancellerebbe il rosso del progetto.
 */
@Composable
fun PatientKioskTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KioskColorScheme,
        typography = KioskTypography,
        shapes = KioskShapes,
        content = content,
    )
}