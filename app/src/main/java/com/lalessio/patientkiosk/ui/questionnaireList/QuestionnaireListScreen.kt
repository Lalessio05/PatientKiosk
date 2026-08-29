package com.lalessio.patientkiosk.ui.questionnaireList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lalessio.patientkiosk.data.repo.QuestionnaireSummary
import com.lalessio.patientkiosk.ui.components.QuestionnaireRow
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
private fun QuestionnaireListScreen(
    state: QuestionnaireListUiState,
    onQuestionnaireSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Spacing.screen)
            ) {
                Text(
                    text = "Impossibile caricare i questionari",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.height(Spacing.sm))
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            LazyColumn(
                modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = Spacing.screen,
                    end = Spacing.screen,
                    top = Spacing.xl,
                    bottom = Spacing.xl
                )
            ) {
                item {
                    Text(
                        text = "PASSO 2 DI 3",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(Spacing.md))

                    Text(
                        text = "Seleziona questionario",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Spacer(Modifier.height(Spacing.xl))
                }
                items(state.questionnaires) {
                    QuestionnaireRow(
                        questionnaire = it,
                        onClick = { onQuestionnaireSelected(it.id) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun QuestionnaireListScreenLoadingPreview() {
    PatientKioskTheme {
        QuestionnaireListScreen(
            state = QuestionnaireListUiState(isLoading = true),
            onQuestionnaireSelected = {}
        )
    }
}

@Preview
@Composable
private fun QuestionnaireListScreenErrorPreview() {
    PatientKioskTheme {
        QuestionnaireListScreen(
            state = QuestionnaireListUiState(isLoading = false, errorMessage = "Errore"),
            onQuestionnaireSelected = {}
        )
    }
}

@Preview
@Composable
private fun QuestionnaireListScreenPreview() {
    PatientKioskTheme {
        QuestionnaireListScreen(
            state = QuestionnaireListUiState(
                isLoading = false,
                questionnaires = listOf(
                    QuestionnaireSummary(
                        "DLQI", "Dermatology Life Quality Index",
                        "Impatto della malattia della pelle sulla qualità della vita.", "", 10
                    ),
                    QuestionnaireSummary(
                        "WHO-5", "WHO-5 Well-Being Index",
                        "Benessere psicologico nelle ultime due settimane.", "", 5
                    ),
                ),
            ),
            onQuestionnaireSelected = {}
        )
    }
}