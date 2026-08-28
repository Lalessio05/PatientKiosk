package com.lalessio.patientkiosk.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

//Nessun angolo arrotondato: è una scelta del design system.
//Impostandolo qui vale per bottoni, card e dialog senza ripeterlo ogni volta.
//#TODO Valutare, sui bottoni non funziona perché materialUI ha uno styling suo per la shape dei bottoni, non è modificabile in questo modo, andrebbero fatti dei componenti a sè
private val Square = RoundedCornerShape(2.dp)

internal val KioskShapes = Shapes(
    extraSmall = Square,
    small = Square,
    medium = Square,
    large = Square,
    extraLarge = Square,
)