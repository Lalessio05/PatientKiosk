package com.lalessio.patientkiosk.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.domain.Band
import com.lalessio.patientkiosk.domain.ScoreBlock
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

/**
 * Il punteggio di un questionario (o di una sua sottoscala) con la scaletta delle fasce,
 * quella raggiunta in evidenza. ResultScreen ne disegna uno per ogni blocco calcolato.
 */
@Composable
fun ScoreBlockView(block: ScoreBlock, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.onSurface)
        Spacer(Modifier.height(Spacing.lg))

        //L'etichetta c'è solo per le sottoscale (Ansia / Depressione)
        if (block.label != null) {
            Text(
                text = block.label.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(Spacing.sm))
        }

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = block.score.toString(),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.width(Spacing.md))
            Text(
                text = "/ ${block.maxScore} PUNTI",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = Spacing.xs),
            )
        }

        Spacer(Modifier.height(Spacing.md))
        Text(
            text = block.band?.label ?: "Punteggio fuori scala",
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(Spacing.xs))
        Text(
            text = block.band?.note.orEmpty(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(Modifier.height(Spacing.md))
        block.bands.forEach { band ->
            BandRow(band = band, reached = band == block.band)
        }

        Spacer(Modifier.height(Spacing.lg))
    }
}

/**Una riga della scaletta delle fasce. Privata: ha senso solo dentro a [ScoreBlockView].**/
@Composable
private fun BandRow(band: Band, reached: Boolean, modifier: Modifier = Modifier) {
    val color =
        if (reached) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onSurfaceVariant
    val weight = if (reached) FontWeight.ExtraBold else FontWeight.Normal

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.sm),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = band.label,
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = weight,
            )
            Text(
                text = "${band.min}–${band.max}",
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = weight,
            )
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
    }
}

@Preview(widthDp = 412)
@Composable
private fun ScoreBlockViewPreview() {
    PatientKioskTheme {
        ScoreBlockView(
            block = ScoreBlock(
                label = null,
                score = 52,
                maxScore = 100,
                band = PreviewBands.WHO5[1],
                bands = PreviewBands.WHO5,
            ),
            modifier = Modifier.padding(Spacing.screen),
        )
    }
}
