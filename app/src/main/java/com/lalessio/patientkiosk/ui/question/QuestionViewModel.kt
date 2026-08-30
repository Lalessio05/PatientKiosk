package com.lalessio.patientkiosk.ui.question

import androidx.lifecycle.SavedStateHandle
import com.lalessio.patientkiosk.ui.navigation.Routes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalessio.patientkiosk.data.repo.QuestionnaireRepository
import com.lalessio.patientkiosk.data.repo.SessionRepository
import com.lalessio.patientkiosk.domain.Questionnaire
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val questionnaireRepository: QuestionnaireRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val questionnaireId: String = savedStateHandle[Routes.ARG_QUESTIONNAIRE_ID] ?: ""
    private val patientCode: String = savedStateHandle[Routes.ARG_PATIENT_CODE] ?: ""

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    //Tenuti fuori dallo stato UI: servono al ViewModel, non alla schermata
    private var questionnaire: Questionnaire? = null
    private var answers: Map<Int, Int> = emptyMap()

    private val resumableSessionId: Long = savedStateHandle[Routes.ARG_SESSION_ID] ?: 0

    init {
        viewModelScope.launch {
            questionnaire =
                questionnaireRepository.loadQuestionnaire(questionnaireId) ?: return@launch
            if (resumableSessionId == 0L) {
                _uiState.update {
                    it.copy(
                        sessionId = sessionRepository.start(
                            patientCode,
                            questionnaireId
                        )
                    )
                }
                render(index = 0)
                return@launch
            }
            //Ripresa: recuperiamo risposte e posizione già salvate
            val session = sessionRepository.find(resumableSessionId)
            if (session != null) {
                answers = session.answers
                _uiState.update { it.copy(sessionId = session.id) }
                render(session.currentIndex)
                return@launch
            }


        }
    }

    fun onAnswerSelected(optionIndex: Int) {
        val index = _uiState.value.currentIndex
        answers = answers + (index to optionIndex)
        _uiState.update { it.copy(selectedOption = optionIndex, pendingAutoAdvance = true) }

        viewModelScope.launch {
            sessionRepository.saveAnswer(_uiState.value.sessionId, index, optionIndex)
        }
    }

    fun onNext() {
        val index = _uiState.value.currentIndex
        if (index < (questionnaire?.questions?.size ?: 0) - 1) {
            render(index + 1)
            viewModelScope.launch {
                sessionRepository.saveCurrentIndex(
                    _uiState.value.sessionId,
                    index + 1
                )
            }
        }
    }

    fun onPrevious() {
        val index = _uiState.value.currentIndex
        if (index > 0) {
            render(index - 1)
            viewModelScope.launch {
                sessionRepository.saveCurrentIndex(
                    _uiState.value.sessionId,
                    index - 1
                )
            }
        }
    }

    /**Estrae la domanda [index] e la mette nello stato**/
    private fun render(index: Int) {
        val q = questionnaire ?: return
        val question = q.questions[index]
        _uiState.update {
            it.copy(
                questionnaireName = q.name,
                questionnaireId = q.id,
                recall = q.recall,
                questionText = question.text,
                options = question.options,
                selectedOption = answers[index],
                currentIndex = index,
                questionCount = q.questions.size,
                isLoading = false,
                pendingAutoAdvance = false,
            )
        }
    }
}