package com.lalessio.patientkiosk.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun KioskTopBar(
    modifier: Modifier = Modifier,
    siglaQuestionario: String? = null,
    codicePaziente: String? = null,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.screen, vertical = Spacing.md)
            .statusBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Logo()
            Spacer(Modifier.weight(1f))
            val topBarText = listOfNotNull(
                siglaQuestionario,
                codicePaziente
            )   //Ritorna una nuova lista contenente solo gli elementi non nulli
                .joinToString(" · ")                                             //Joina i toSring di ogni elemento della lista
                .ifEmpty { "Sala d'attesa" }                                                //Default
            Text(
                text = topBarText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Preview(widthDp = 412)
@Composable
private fun KioskTopBarPreview() {
    PatientKioskTheme {
        KioskTopBar(siglaQuestionario = "DLQI", codicePaziente = "PZ-4192")
    }
}