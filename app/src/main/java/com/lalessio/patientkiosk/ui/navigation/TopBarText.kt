package com.lalessio.patientkiosk.ui.navigation

import android.os.Bundle

private const val WAITING_ROOM = "Sala d'attesa"

/**
 * Testo mostrato a destra nella top bar, ricavato dalla schermata correntemente a video.
 * È una funzione normale e non un composable: prende rotta e argomenti e restituisce una stringa,
 * così MainActivity resta corta e la logica sta vicino alle rotte a cui si riferisce.
 */
fun topBarTextFor(route: String?, arguments: Bundle?): String = when (route) {
    Routes.SOURCES_ROUTE -> "Riferimenti"
    Routes.SENT_TO_DOCTOR_ROUTE -> "Completato"
    Routes.RESULT_ROUTE -> "Risultato"
    Routes.QUESTIONNAIRE_LIST_ROUTE -> arguments?.getString(Routes.ARG_PATIENT_CODE) ?: "—"
    Routes.QUESTION_ROUTE -> listOfNotNull(
        arguments?.getString(Routes.ARG_QUESTIONNAIRE_ID),
        arguments?.getString(Routes.ARG_PATIENT_CODE),
    )   //Ritorna una nuova lista contenente solo gli elementi non nulli
        .joinToString(" · ")
        .ifEmpty { WAITING_ROOM }
    //Comprende PATIENT_CODE_ROUTE e la rotta ancora nulla al primo frame, prima che il NavHost si agganci
    else -> WAITING_ROOM
}
