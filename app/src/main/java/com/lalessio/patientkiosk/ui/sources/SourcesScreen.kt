package com.lalessio.patientkiosk.ui.sources

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.data.repo.QuestionnaireSummary
import com.lalessio.patientkiosk.ui.components.ErrorBox
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.components.LoadingBox
import com.lalessio.patientkiosk.ui.questionnaireCatalog.QuestionnaireCatalogUiState
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

//Riusiamo QuestionnaireCatalogUiState perché i viewModel e gli UiState sarebbero esattamente uguali, trattando gli stessi elementi
@Composable
fun SourcesScreen(
    state: QuestionnaireCatalogUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        state.isLoading -> LoadingBox(modifier)

        state.errorMessage != null -> ErrorBox(
            title = "Impossibile caricare i questionari",
            message = state.errorMessage,
            modifier = modifier,
        )

        else -> {
            Column(
                modifier = modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        start = Spacing.screen, end = Spacing.screen,
                        top = Spacing.xl, bottom = Spacing.xl,
                    ),
                    verticalArrangement = Arrangement.spacedBy(Spacing.md),
                ) {
                    item {
                        Text(
                            text = "Fonti dei questionari",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(Modifier.height(Spacing.sm))
                        Text(
                            text = "I questionari sono caricati da questionnaires.json e provengono dalle pubblicazioni originali.",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Spacer(Modifier.height(Spacing.sm))
                    }
                    items(state.questionnaires) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            HorizontalDivider(
                                thickness = 2.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Spacer(Modifier.height(Spacing.md))
                            Text(
                                text = "${it.id} - ${it.name}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(Modifier.height(Spacing.xs))
                            Text(
                                text = it.source,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.outline)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.screen, vertical = Spacing.sm),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TextButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            //Il testo accanto descrive già l'azione: per TalkBack l'icona è decorativa
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(Spacing.xs))
                        Text("Torna ai questionari")
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun SourcesScreenPreview() {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar() }
        ) { innerPadding ->
            SourcesScreen(
                state = QuestionnaireCatalogUiState(
                    isLoading = false,
                    questionnaires = listOf(
                        QuestionnaireSummary(
                            "DLQI", "Dermatology Life Quality Index",
                            "Impatto della malattia della pelle sulla qualità della vita.",
                            "Finlay AY, Khan GK. Dermatology Life Quality Index (DLQI), Clin Exp Dermatol 1994.",
                            10
                        ),
                        QuestionnaireSummary(
                            "WHO-5", "WHO-5 Well-Being Index",
                            "Benessere psicologico nelle ultime due settimane.",
                            "WHO-5 Well-Being Index, WHO Regional Office for Europe, 1998.",
                            5
                        ),
                    )
                ),
                onBack = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
