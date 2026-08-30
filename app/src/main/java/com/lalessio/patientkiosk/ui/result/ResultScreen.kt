package com.lalessio.patientkiosk.ui.result

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.domain.ScoreBlock
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.components.LoadingBox
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun ResultScreen(
    state: ResultUiState,
    onSend: () -> Unit,
    onRestart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        LoadingBox(modifier)
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

            if (state.isQuestionnaireIncomplete) {
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
                enabled = !state.isQuestionnaireIncomplete,
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
private fun ResultScreenPreviewScaffold(state: ResultUiState) {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar(text = "Risultato") },
        ) { innerPadding ->
            ResultScreen(
                state = state,
                onSend = {},
                onRestart = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun ResultScreenPreview() {
    ResultScreenPreviewScaffold(
        ResultUiState(
            questionnaireName = "WHO-5 Well-Being Index",
            patientCode = "PZ-4192",
            blocks = listOf(
                ScoreBlock(
                    label = null,
                    score = 52,
                    maxScore = 100,
                    band = PreviewBands.WHO5[2],
                    bands = PreviewBands.WHO5,
                ),
            ),
            isLoading = false,
        )
    )
}

/**HADS: due sottoscale, quindi due blocchi etichettati**/
@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun ResultScreenSubscalesPreview() {
    ResultScreenPreviewScaffold(
        ResultUiState(
            questionnaireName = "Hospital Anxiety and Depression Scale",
            patientCode = "PZ-4192",
            blocks = listOf(
                ScoreBlock(
                    label = "Ansia",
                    score = 9,
                    maxScore = 21,
                    band = PreviewBands.HADS[1],
                    bands = PreviewBands.HADS,
                ),
                ScoreBlock(
                    label = "Depressione",
                    score = 4,
                    maxScore = 21,
                    band = PreviewBands.HADS[0],
                    bands = PreviewBands.HADS,
                ),
            ),
            isLoading = false,
        )
    )
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun ResultScreenIncompletePreview() {
    ResultScreenPreviewScaffold(
        ResultUiState(
            questionnaireName = "WHO-5 Well-Being Index",
            patientCode = "PZ-4192",
            isLoading = false,
            isQuestionnaireIncomplete = true,
        )
    )
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun ResultScreenLoadingPreview() {
    ResultScreenPreviewScaffold(ResultUiState(isLoading = true))
}
