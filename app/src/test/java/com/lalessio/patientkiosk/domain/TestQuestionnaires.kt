package com.lalessio.patientkiosk.domain

/**
 * Copie dei questionari di assets/questionnaires.json, ridotte a ciò che
 * influenza il calcolo. I testi delle domande sono segnaposto: non contano.
 */
object TestQuestionnaires {

    fun dlqi(): Questionnaire {
        val options = listOf(
            AnswerOption("Per niente", 0),
            AnswerOption("Poco", 1),
            AnswerOption("Abbastanza", 2),
            AnswerOption("Moltissimo", 3),
        )
        return Questionnaire(
            id = "DLQI",
            name = "Dermatology Life Quality Index",
            description = "",
            recall = "",
            scale = 1,
            maxScore = 30,
            source = "",
            questions = (1..10).map { Question("Domanda $it", options) },
            bands = listOf(
                Band(0, 1, "Nessun effetto", ""),
                Band(2, 5, "Effetto lieve", ""),
                Band(6, 10, "Effetto moderato", ""),
                Band(11, 20, "Effetto forte", ""),
                Band(21, 30, "Effetto molto forte", ""),
            ),
        )
    }

    fun who5(): Questionnaire {
        val options = (0..5).map { AnswerOption("Opzione $it", it) }
        return Questionnaire(
            id = "WHO-5",
            name = "WHO-5 Well-Being Index",
            description = "",
            recall = "",
            scale = 4,
            maxScore = 100,
            source = "",
            questions = (1..5).map { Question("Domanda $it", options) },
            bands = listOf(
                Band(0, 28, "Benessere ridotto", ""),
                Band(29, 50, "Segnale di allerta", ""),
                Band(51, 100, "Benessere adeguato", ""),
            ),
        )
    }

    fun hads(): Questionnaire {
        val options = (0..3).map { AnswerOption("Opzione $it", it) }
        //Nel JSON le domande si alternano A, D, A, D… (7 per sottoscala)
        val questions = (0 until 14).map { index ->
            Question(
                text = "Domanda ${index + 1}",
                options = options,
                subscaleKey = if (index % 2 == 0) "A" else "D",
            )
        }
        return Questionnaire(
            id = "HADS",
            name = "Hospital Anxiety and Depression Scale",
            description = "",
            recall = "",
            scale = 1,
            maxScore = 42,
            source = "",
            questions = questions,
            subscales = listOf(
                Subscale("A", "Ansia (HADS-A)", 21),
                Subscale("D", "Depressione (HADS-D)", 21),
            ),
            bands = listOf(
                Band(0, 7, "Nella norma", ""),
                Band(8, 10, "Caso borderline", ""),
                Band(11, 21, "Caso probabile", ""),
            ),
        )
    }
}