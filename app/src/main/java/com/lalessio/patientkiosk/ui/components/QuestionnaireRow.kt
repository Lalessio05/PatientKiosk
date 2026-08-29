package com.lalessio.patientkiosk.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.data.repo.QuestionnaireSummary
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun QuestionnaireRow(
    questionnaire: QuestionnaireSummary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            //Clickable rende tutta la colonna cliccabile
            .clickable(onClick = onClick)
            .semantics {
                contentDescription =
                    "${questionnaire.name}, ${questionnaire.questionCount} domande"
            }
            .padding(vertical = Spacing.lg)
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = questionnaire.id,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "${questionnaire.questionCount} domande",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.outline)

        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = questionnaire.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(Modifier.height(Spacing.md))
        Text(
            text = "Inizia →",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(Modifier.height(Spacing.lg))
    }
}

@Preview
@Composable
private fun QuestionnaireRowPreview() {
    PatientKioskTheme {
        QuestionnaireRow(
            questionnaire = QuestionnaireSummary(
                "DLQI", "Dermatology Life Quality Index",
                "Impatto della malattia della pelle sulla qualità della vita.", "", 10
            ),
            onClick = {}
        )
    }
}