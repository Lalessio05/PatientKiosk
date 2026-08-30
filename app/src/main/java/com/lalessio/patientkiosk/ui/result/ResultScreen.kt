package com.lalessio.patientkiosk.ui.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.domain.Band
import com.lalessio.patientkiosk.domain.ScoreBlock
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun ResultScreen(
    state: ResultUiState,
    onSend: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.screen, vertical = Spacing.xl),
        ) {
            Text(
                text = "PASSO 3 DI 3 — RISULTATO",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = state.questionnaireName,
                style = MaterialTheme.typography.headlineMedium,
            )
            Spacer(Modifier.height(Spacing.lg))

            if (state.isQuesionnaireIncomplete) {
                Text(
                    text = "Il questionario non è stato completato: il punteggio non " +
                            "può essere calcolato in modo attendibile.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                )
            } else {
                state.blocks.forEach { block ->
                    ScoreBlockView(block)
                }
            }
        }

        Column(
            modifier = Modifier.padding(
                horizontal = Spacing.screen,
                vertical = Spacing.md,
            )
        ) {
            Button(
                onClick = onSend,
                enabled = !state.isQuesionnaireIncomplete,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 60.dp),
            ) {
                Text(
                    text = "Invia al medico",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )
            }
            Spacer(Modifier.height(Spacing.xs))
            TextButton(onClick = onRestart) { Text("Compila un altro questionario") }
        }
    }
}

@Composable
private fun ScoreBlockView(block: ScoreBlock, modifier: Modifier = Modifier) {
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

//#TODO Spostare il componente in un altro file
//#TODO Scrivere preview