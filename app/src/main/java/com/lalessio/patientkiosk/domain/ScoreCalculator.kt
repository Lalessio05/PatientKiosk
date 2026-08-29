package com.lalessio.patientkiosk.domain

/**
 * Un blocco di risultato da mostrare. I questionari senza sottoscale ne
 * producono uno solo (con [label] null), HADS ne produce due.
 */
data class ScoreBlock(
    val label: String?,
    val score: Int,
    val maxScore: Int,
    /**La fascia raggiunta, o null se nessuna contiene il punteggio (dati incoerenti)**/
    val band: Band?,
    /**Tutte le fasce, per disegnare la scaletta con quella raggiunta evidenziata**/
    val bands: List<Band>,
)

/**
 * @param answeredCount quante domande hanno una risposta
 * @param questionCount quante domande ha il questionario
 */
data class QuestionnaireResult(
    val blocks: List<ScoreBlock>,
    val answeredCount: Int,
    val questionCount: Int,
) {
    val isComplete: Boolean get() = answeredCount == questionCount
}

object ScoreCalculator {

    /**
     * Calcolo guidato dai dati: nessun caso speciale per questionario, tutto
     * viene dal JSON (punti, moltiplicatore scale, fasce).
     *
     * @param questionnaire Il questionario attuale
     * @param answers Mappa posizione della domanda -> indice (0 based) della risposta scelta
     */
    fun calculateScore(
        questionnaire: Questionnaire,
        answers: Map<Int, Int>,
    ): QuestionnaireResult {
        val blocks = if (questionnaire.hasSubscales) {
            questionnaire.subscales.map { subscale ->
                val score = sumPoints(questionnaire, answers) {
                    questionnaire.questions[it].subscaleKey == subscale.key
                } * questionnaire.scale

                ScoreBlock(
                    label = subscale.label,
                    //Il max della sottoscala (21), non quello del questionario (42):
                    //il totale HADS non si interpreta mai
                    maxScore = subscale.maxScore,
                    score = score,
                    band = questionnaire.bands.firstOrNull { it.contains(score) },
                    bands = questionnaire.bands,
                )
            }
        } else {
            val score = sumPoints(questionnaire, answers) { true } * questionnaire.scale

            listOf(
                ScoreBlock(
                    label = null,
                    score = score,
                    maxScore = questionnaire.maxScore,
                    band = questionnaire.bands.firstOrNull { it.contains(score) },
                    bands = questionnaire.bands,
                )
            )
        }

        return QuestionnaireResult(
            blocks = blocks,
            answeredCount = answers.size,
            questionCount = questionnaire.questions.size,
        )
    }

    /**
     * Somma i punti delle risposte date, tenendo solo le domande che [keep] accetta.
     * Le domande senza risposta non sono nella mappa, quindi una compilazione
     * parziale produce un punteggio parziale senza casi speciali.
     */
    private fun sumPoints(
        questionnaire: Questionnaire,
        answers: Map<Int, Int>,
        keep: (Int) -> Boolean,
    ): Int = answers.entries
        .filter { (questionIndex, _) -> keep(questionIndex) }
        .sumOf { (questionIndex, optionIndex) ->
            questionnaire.questions[questionIndex].options[optionIndex].points
        }
}