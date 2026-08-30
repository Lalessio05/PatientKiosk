package com.lalessio.patientkiosk.ui.questionnaireList

import androidx.lifecycle.SavedStateHandle
import com.lalessio.patientkiosk.ui.navigation.Routes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lalessio.patientkiosk.data.repo.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionnaireListViewModel @Inject constructor(
    private val repository: QuestionnaireRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val patientCode: String = savedStateHandle[Routes.ARG_PATIENT_CODE] ?: ""
    private val _uiState = MutableStateFlow(QuestionnaireListUiState())
    val uiState: StateFlow<QuestionnaireListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                repository.ensureImported()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Errore di importazione")
                }
                return@launch
            }

            repository.observeSummaries().collect { list ->
                _uiState.update { it.copy(questionnaires = list, isLoading = false) }
            }
        }
    }
}