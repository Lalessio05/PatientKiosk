package com.lalessio.patientkiosk.data.json

import android.content.Context
import androidx.room.withTransaction
import com.lalessio.patientkiosk.data.local.AppDatabase
import com.lalessio.patientkiosk.data.local.dao.QuestionnaireDao
import com.lalessio.patientkiosk.data.local.entities.BandEntity
import com.lalessio.patientkiosk.data.local.entities.MetaEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionnaireEntity
import com.lalessio.patientkiosk.data.local.entities.SubscaleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

//Il context serve perché il nostro file è nella cartella assets del progetto
class QuestionnaireImporter(
    private val context: Context,
    private val database: AppDatabase
) {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    //withContext serve a dire di eseguire questo metodo in un thread dedicato, in questo caso, alla IO. Rispetto alla launch il withContext è bloccante
    private suspend fun readFile(): QuestionnaireFileDto = withContext(Dispatchers.IO) {
        val text = context.assets.open(FILE_NAME).bufferedReader().use { it.readText() }
        jsonParser.decodeFromString<QuestionnaireFileDto>(text)
    }

    suspend fun importIfNeeded() {
        val questionnaireFileDto: QuestionnaireFileDto = readFile()

        val shouldImport: Boolean =
            database.metaDao().findValue(FILE_VERSION_META_KEY) != questionnaireFileDto.version
        if (!shouldImport) return

        writeToDatabase(questionnaireFileDto)
        database.metaDao().putEntry(MetaEntity(FILE_VERSION_META_KEY, questionnaireFileDto.version))
    }

    private suspend fun writeToDatabase(file: QuestionnaireFileDto) {
        database.withTransaction {
            val questionnaireDao: QuestionnaireDao = database.questionnaireDao()

            //C'è ON DELETE CASCADE ovunque, quindi eliminando i questionari eliminiamo anche tutto il resto
            questionnaireDao.deleteAllQuestionnaires()

            questionnaireDao.insertQuestionnaires(
                file.questionnaires.mapIndexed { index, dto ->
                    QuestionnaireEntity(
                        id = dto.id,
                        name = dto.name,
                        description = dto.description,
                        recall = dto.recall,
                        scale = dto.scale,
                        maxScore = dto.maxScore,
                        source = dto.source,
                        position = index
                    )
                }
            )

            file.questionnaires.forEach {
                questionnaireDao.insertSubscales(
                    it.subscales.mapIndexed { index, subscaleDto ->
                        SubscaleEntity(
                            questionnaireId = it.id,
                            key = subscaleDto.key,
                            label = subscaleDto.label,
                            maxScore = subscaleDto.maxScore,
                            position = index
                        )
                    }
                )
                questionnaireDao.insertQuestions(
                    it.questions.mapIndexed { index, questionDto ->
                        val options: List<OptionDto> = questionDto.options ?: it.options
                        QuestionEntity(
                            questionnaireId = it.id,
                            text = questionDto.text,
                            subScaleKey = questionDto.subscale,
                            optionsJson = jsonParser.encodeToString(options),
                            position = index
                        )
                    }
                )
                questionnaireDao.insertBands(
                    it.bands.mapIndexed { index, bandDto ->
                        BandEntity(
                            questionnaireId = it.id,
                            min = bandDto.min,
                            max = bandDto.max,
                            label = bandDto.label,
                            note = bandDto.note,
                            position = index
                        )
                    }
                )
            }
        }
    }

    //#TODO In futuro potrebbe essere in un file di config, lasciamolo qui per ora
    private companion object {
        const val FILE_NAME = "questionnaires.json"
        const val FILE_VERSION_META_KEY = "JSON-VERSION"
    }
}
