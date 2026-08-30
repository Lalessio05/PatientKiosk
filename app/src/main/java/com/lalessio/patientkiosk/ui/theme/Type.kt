package com.lalessio.patientkiosk.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//Font di sistema. Per usare Archivo: file .ttf in res/font, poi
//FontFamily(Font(R.font.archivo_extrabold, FontWeight.ExtraBold), ...)
private val Display = FontFamily.SansSerif
private val Body = FontFamily.SansSerif

internal val KioskTypography = Typography(
    //Titolo di apertura, punteggio del risultato
    displaySmall = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 34.sp, lineHeight = 38.sp, letterSpacing = (-0.5).sp,
    ),
    displayLarge = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 64.sp, lineHeight = 56.sp, letterSpacing = (-2).sp,
    ),
    //Testo della domanda: minimo 20sp per il requisito di usabilità
    headlineSmall = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp, lineHeight = 32.sp, letterSpacing = (-0.2).sp,
    ),
    titleLarge = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp, lineHeight = 26.sp,
    ),
    //Logo e intestazioni di componenti
    titleMedium = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp, lineHeight = 22.sp,
    ),
    //Testo delle risposte
    bodyLarge = TextStyle(
        fontFamily = Body, fontWeight = FontWeight.Normal,
        fontSize = 18.sp, lineHeight = 26.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Body, fontWeight = FontWeight.Normal,
        fontSize = 16.sp, lineHeight = 24.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Body, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 20.sp,
    ),
    //Etichette dentro i bottoni
    labelLarge = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp, lineHeight = 20.sp, letterSpacing = 0.5.sp,
    ),
    //Kicker: "PASSO 1 DI 3"
    labelMedium = TextStyle(
        fontFamily = Display, fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 1.2.sp,
    ),

    )