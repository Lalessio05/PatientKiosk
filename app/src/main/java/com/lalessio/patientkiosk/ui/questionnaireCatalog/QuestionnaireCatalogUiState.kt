package com.lalessio.patientkiosk.ui.questionnaireCatalog

import com.lalessio.patientkiosk.data.repo.QuestionnaireSummary

/**
 * Il catalogo dei questionari importati. Lo condividono QuestionnaireListScreen (che li fa scegliere)
 * e SourcesScreen (che ne mostra le fonti): stessi dati, stessa provenienza, quindi un solo stato.
 */
data class QuestionnaireCatalogUiState(
    val questionnaires: List<QuestionnaireSummary> = emptyList(),
    val isLoading: Boolean = true,
    /**Messaggio dell'importer se il JSON è illeggibile, null se tutto ok**/
    val errorMessage: String? = null,
)
