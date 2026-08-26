package com.lalessio.patientkiosk.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme

@Composable
//#TODO mi piacerebbe fosse più figo
//Mandare modifier come parametro opzionale è l'equivalente di mettere un className in react, convenzione
fun Logo(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = "Patient",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Kiosk",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
fun LogoPreview() {
    PatientKioskTheme {
        Logo()
    }}