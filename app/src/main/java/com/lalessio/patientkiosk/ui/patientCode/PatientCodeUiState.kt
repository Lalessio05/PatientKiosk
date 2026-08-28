package com.lalessio.patientkiosk.ui.patientCode

//Una data class è una tipo di classe pensata per contenere dati, la equal funziona anche se sono indirizzi diversi
data class PatientCodeUiState(
    val patientCode: String = "",
    val showError: Boolean = false,
) {
    //Non memorizzato, calcolato come prop a ogni chiamata, com'è giusto che sia.
    val isCodeValid: Boolean get() = patientCode.trim().length >= 3
}