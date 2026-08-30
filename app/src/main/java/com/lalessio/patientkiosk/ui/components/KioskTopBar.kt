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
    text: String = "Sala d'attesa",
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
            Text(
                text = text,
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
        KioskTopBar(text = "DLQI · PZ-4192")
    }
}