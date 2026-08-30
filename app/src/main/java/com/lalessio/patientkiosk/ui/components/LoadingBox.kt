package com.lalessio.patientkiosk.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme

/**Riempitivo mostrato mentre lo UiState è in caricamento, al posto del contenuto della schermata**/
@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Preview(widthDp = 412, heightDp = 400)
@Composable
private fun LoadingBoxPreview() {
    PatientKioskTheme {
        LoadingBox()
    }
}
