package com.lalessio.patientkiosk.ui.patientCode

import androidx.compose.runtime.LaunchedEffect
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

class PatientCodeViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val questionnaireRepository: QuestionnaireRepository
) : ViewModel() {

    //un contenitore osservabile che ha sempre un valore corrente (.value) e notifica chi lo osserva a ogni cambiamento.
    private val _uiState = MutableStateFlow(PatientCodeUiState())
    val uiState: StateFlow<PatientCodeUiState> = _uiState.asStateFlow()

    //Quando cambia il codice chiamo questo metodo, che chiama l'update sulla data class. L'equivalente di un setState (prev => ({...prev, x})), cioè prende lo stato e atomicamente modifica quello che gli stai dicendo di modificare
    fun onCodeChange(newCode: String) {
        _uiState.update {
            it.copy(patientCode = newCode.uppercase(), showError = false)
        }
    }

    fun onRandomCode() {
        _uiState.update {
            it.copy(patientCode = "PZ-${(1000..9999).random()}", showError = false)

        }
    }

    /** @return validated code, or null if not valid. **/
    fun onForward(): String? {
        val state = _uiState.value
        return if (state.isCodeValid) {
            state.patientCode.trim()
        } else {
            _uiState.update { it.copy(showError = true) }
            null
        }
    }
    fun onDiscardSession() {
        val session = _uiState.value.resumableSession ?: return
        viewModelScope.launch { sessionRepository.discard(session.sessionId) }
    }

    init {
        viewModelScope.launch {
            //#TODO Fare pagina di errore e loading
            try {
                questionnaireRepository.ensureImported()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Errore di importazione")
                }
                return@launch
            }

            sessionRepository.observeResumable().collect { resumableSession ->
                if (resumableSession == null){
                    _uiState.update { it.copy(resumableSession = null) }
                    return@collect
                }

                val questionnaire: Questionnaire? = questionnaireRepository.loadQuestionnaire(resumableSession.questionnaireId)
                _uiState.update {
                    it.copy(
                        resumableSession =
                            ResumableSession(
                                sessionId = resumableSession.id,
                                patientCode = resumableSession.patientCode,
                                questionnaireId = resumableSession.questionnaireId,
                                questionnaireName = questionnaire?.name ?: resumableSession.questionnaireId,
                                currentIndex = resumableSession.currentIndex,
                                questionCount = questionnaire?.questions?.size ?: 0
                            )
                    )
                }
            }
        }
    }

}