package com.lalessio.patientkiosk.domain

data class Session(
    val id: Long,
    val patientCode: String,
    val questionnaireId: String,
    val startedAt: Long,
    val currentIndex: Int,
    /**Posizione della domanda -> indice della risposta scelta**/
    val answers: Map<Int, Int>,
)
