package com.lalessio.patientkiosk.ui.result

import com.lalessio.patientkiosk.domain.ScoreBlock

data class ResultUiState(
    val questionnaireName: String = "",
    val patientCode: String = "",
    val blocks: List<ScoreBlock> = emptyList(),
    val isLoading: Boolean = true,
    val isQuestionnaireIncomplete: Boolean = false,
)