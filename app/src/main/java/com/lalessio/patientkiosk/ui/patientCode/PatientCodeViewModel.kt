package com.lalessio.patientkiosk.ui.patientCode

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PatientCodeViewModel : ViewModel() {

    //un contenitore osservabile che ha sempre un valore corrente (.value) e notifica chi lo osserva a ogni cambiamento.
    private val _uiState = MutableStateFlow(PatientCodeUiState())
    val uiState: StateFlow<PatientCodeUiState> = _uiState.asStateFlow()

    //Quando cambia il codice chiamo questo metodo, che chiama l'update sulla data class. L'equivalente di un setState (prev => ({...prev, x})), cioè prende lo stato e atomicamente modifica quello che gli stai dicendo di modificare
    fun onCodeChange(newCode : String){
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
    fun onForward() : String?{
        val state = _uiState.value
        return if (state.validCode){
            state.patientCode.trim()
        }
        else {
            _uiState.update { it.copy(showError = true) }
            null
        }
    }

}