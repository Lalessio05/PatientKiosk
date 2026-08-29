package com.lalessio.patientkiosk.data.repo

import com.lalessio.patientkiosk.data.json.QuestionnaireImporter
import com.lalessio.patientkiosk.data.local.dao.QuestionnaireDao
import com.lalessio.patientkiosk.domain.AnswerOption
import com.lalessio.patientkiosk.domain.Question
import com.lalessio.patientkiosk.domain.Questionnaire
import com.lalessio.patientkiosk.domain.Subscale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**Dati di cui abbiamo bisogno nella schermata di selezione del questionario**/
data class QuestionnaireSummary(
    val id: String,
    val name: String,
    val description: String,
    val source: String,
    val questionCount: Int
)


class QuestionnaireRepository(
    private val questionnaireDao: QuestionnaireDao,
    private val importer: QuestionnaireImporter
) {
    private val jsonParser: Json = Json { ignoreUnknownKeys = true }

    suspend fun ensureImported() = importer.importIfNeeded()

    fun observeSummaries(): Flow<List<QuestionnaireSummary>> =
        questionnaireDao.observeQuestionnaireSummaries().map { rows ->
            rows.map {
                QuestionnaireSummary(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    source = it.source,
                    questionCount = it.questionCount
                )
            }
        }


    suspend fun loadQuestionnaire(questionnaireId: String): Questionnaire? {
        val questionnaire = questionnaireDao.findQuestionnaire(questionnaireId) ?: return null

        val questions: List<Question> = questionnaireDao.getQuestions(questionnaireId).map {
            Question(
                text = it.text,
                options = jsonParser.decodeFromString<List<AnswerOption>>(it.optionsJson),
                subscaleKey = it.subScaleKey,
            )
        }

        val subscales: List<Subscale> = questionnaireDao.getSubscales(questionnaireId).map {
            Subscale(
                key = it.key,
                label = it.label,
                maxScore = it.maxScore
            )
        }
        return Questionnaire(
            id = questionnaire.id,
            description = questionnaire.description,
            maxScore = questionnaire.maxScore,
            name = questionnaire.name,
            questions = questions,
            subscales = subscales,
            recall = questionnaire.recall,
            scale = questionnaire.scale,
            source = questionnaire.source
        )
    }
}