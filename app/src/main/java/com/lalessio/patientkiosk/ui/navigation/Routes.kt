package com.lalessio.patientkiosk.ui.navigation

object Routes {
    const val PATIENT_CODE_ROUTE = "patientCode"

    const val QUESTIONNAIRE_LIST_ROUTE = "questionnaireList/{patientCode}"
    fun questionnaireList(patientCode: String) = "questionnaireList/$patientCode"

    const val SOURCES_ROUTE = "sources"

    const val QUESTION_ROUTE = "question/{patientCode}/{questionnaireId}"
    fun question(patientCode: String, questionnaireId: String) = "question/$patientCode/$questionnaireId"
}