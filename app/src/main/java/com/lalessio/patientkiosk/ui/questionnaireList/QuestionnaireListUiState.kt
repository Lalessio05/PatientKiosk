package com.lalessio.patientkiosk.ui.questionnaireList

import com.lalessio.patientkiosk.data.repo.QuestionnaireSummary

data class QuestionnaireListUiState(
    val questionnaires: List<QuestionnaireSummary> = emptyList(),
    val isLoading: Boolean = true,
    /**Messaggio dell'importer se il JSON è illeggibile, null se tutto ok**/
    val errorMessage: String? = null,
)