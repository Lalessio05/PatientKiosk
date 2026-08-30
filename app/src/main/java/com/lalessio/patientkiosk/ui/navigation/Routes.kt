package com.lalessio.patientkiosk.ui.navigation

object Routes {
    //Nomi degli argomenti di navigazione: li leggono il NavGraph, i ViewModel (via SavedStateHandle) e la top bar
    const val ARG_PATIENT_CODE = "patientCode"
    const val ARG_QUESTIONNAIRE_ID = "questionnaireId"
    const val ARG_SESSION_ID = "sessionId"

    const val PATIENT_CODE_ROUTE = "patientCode"

    const val QUESTIONNAIRE_LIST_ROUTE = "questionnaireList/{patientCode}"
    fun questionnaireList(patientCode: String) = "questionnaireList/$patientCode"

    const val SOURCES_ROUTE = "sources"

    const val QUESTION_ROUTE = "question/{patientCode}/{questionnaireId}?sessionId={sessionId}"
    fun question(patientCode: String, questionnaireId: String, sessionId: Long = 0L) =
        "question/$patientCode/$questionnaireId?sessionId=$sessionId"

    const val RESULT_ROUTE = "result/{sessionId}"
    fun result(sessionID: Long) = "result/$sessionID"

    const val SENT_TO_DOCTOR_ROUTE = "sendToDoctor/{patientCode}"
    fun sentToDoctor(patientCode: String) = "sendToDoctor/$patientCode"
}
