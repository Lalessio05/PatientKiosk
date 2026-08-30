package com.lalessio.patientkiosk.ui.question

import com.lalessio.patientkiosk.domain.AnswerOption

data class QuestionUiState(
    val sessionId: Long = 0,
    val questionnaireName: String = "",
    val questionnaireId: String = "",
    val recall: String = "",
    val questionText: String = "",
    val options: List<AnswerOption> = emptyList(),
    /**Indice della risposta scelta per la domanda corrente, null se non risposta**/
    val selectedOption: Int? = null,
    val currentIndex: Int = 0,
    val questionCount: Int = 0,
    val isLoading: Boolean = true,
    val pendingAutoAdvance: Boolean = false
) {
    val progress: Float
        get() = if (questionCount == 0) 0f else (currentIndex + 1).toFloat() / questionCount
    val isLastQuestion: Boolean get() = currentIndex == questionCount - 1

}