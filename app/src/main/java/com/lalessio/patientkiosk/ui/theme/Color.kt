package com.lalessio.patientkiosk.ui.theme

import androidx.compose.ui.graphics.Color

//Unico file dell'app in cui compaiono valori esadecimali.
//I composable non importano mai da qui: usano MaterialTheme.colorScheme.

internal val Ground = Color(0xFFF3F2F2)        // fondo delle schermate
internal val Surface = Color(0xFFEAE9E9)       // riquadri, card
internal val Ink = Color(0xFF201E1D)           // testo
internal val InkMuted = Color(0xFF605D5D)      // testo secondario
internal val Divider = Color(0x66201E1D)       // Ink al 40%, le righe da 2dp

internal val Accent = Color(0xFFEC3013)        // rosso del design system
internal val AccentTint = Color(0xFFFFF2EF)    // fondo tenue
internal val AccentLight = Color(0xFFFF9783)   // rosso leggibile su fondo scuro
internal val AccentDeep = Color(0xFFAE1800)    // rosso leggibile su testo piccolo

internal val Neutral200 = Color(0xFFEAE7E7)
internal val Neutral300 = Color(0xFFD7D3D3)