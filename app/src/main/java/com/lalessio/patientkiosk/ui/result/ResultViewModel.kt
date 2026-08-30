package com.lalessio.patientkiosk.ui.result

import androidx.lifecycle.SavedStateHandle
import com.lalessio.patientkiosk.ui.navigation.Routes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalessio.patientkiosk.data.repo.QuestionnaireRepository
import com.lalessio.patientkiosk.data.repo.SessionRepository
import com.lalessio.patientkiosk.domain.ScoreCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val questionnaireRepository: QuestionnaireRepository,
    private val sessionRepository: SessionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val sessionId: Long = savedStateHandle[Routes.ARG_SESSION_ID] ?: 0L

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val session = sessionRepository.find(sessionId) ?: return@launch
            val questionnaire = questionnaireRepository.loadQuestionnaire(session.questionnaireId)
                ?: return@launch

            val result = ScoreCalculator.calculateScore(questionnaire, session.answers)

            _uiState.update {
                it.copy(
                    questionnaireName = questionnaire.name,
                    patientCode = session.patientCode,
                    blocks = result.blocks,
                    isQuesionnaireIncomplete = !result.isComplete,
                    isLoading = false,
                )
            }
        }
    }

    /**"Invia al medico": marca la sessione come completata**/
    fun onSend() {
        viewModelScope.launch { sessionRepository.complete(sessionId) }
    }
}