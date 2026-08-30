package com.lalessio.patientkiosk.data.repo

import com.lalessio.patientkiosk.data.local.dao.SessionDao
import com.lalessio.patientkiosk.data.local.entities.SessionEntity
import com.lalessio.patientkiosk.data.local.entities.SessionStatus
import com.lalessio.patientkiosk.domain.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val dao: SessionDao,
) {
    private val jsonParser = Json

    /**La compilazione interrotta da proporre in apertura, se c'è**/
    fun observeResumable(): Flow<Session?> =
        dao.observeResumableSession().map { it?.toDomain() }

    suspend fun find(sessionId: Long): Session? = dao.findSession(sessionId)?.toDomain()

    /**
     * Apre una compilazione. Ne esiste al massimo una aperta per volta:
     * iniziarne una nuova scarta quella eventualmente in sospeso.
     */
    suspend fun start(patientCode: String, questionnaireId: String): Long {
        dao.discardInProgressSessions()
        return dao.insertSession(
            SessionEntity(
                patientCode = patientCode,
                questionnaireId = questionnaireId,
                startedAt = System.currentTimeMillis(),
                currentIndex = 0,
                status = SessionStatus.IN_PROGRESS,
            )
        )
    }

    /**Salva una risposta e la domanda corrente. Chiamata a ogni tap.**/
    suspend fun saveAnswer(sessionId: Long, questionIndex: Int, optionIndex: Int) {
        val entity = dao.findSession(sessionId) ?: return
        val answers = entity.decodeAnswers() + (questionIndex to optionIndex)
        dao.updateSession(
            entity.copy(answersJson = jsonParser.encodeToString(answers))
        )
    }

    /**Solo lo spostamento tra domande, senza toccare le risposte**/
    suspend fun saveCurrentIndex(sessionId: Long, currentIndex: Int) {
        val entity = dao.findSession(sessionId) ?: return
        dao.updateSession(entity.copy(currentIndex = currentIndex))
    }

    /**"Invia al medico"**/
    suspend fun complete(sessionId: Long) {
        val entity = dao.findSession(sessionId) ?: return
        dao.updateSession(
            entity.copy(
                status = SessionStatus.COMPLETED,
                completedAt = System.currentTimeMillis(),
            )
        )
    }

    /**"Scarta e ricomincia": la riga resta, cambia solo lo stato**/
    suspend fun discard(sessionId: Long) {
        val entity = dao.findSession(sessionId) ?: return
        dao.updateSession(entity.copy(status = SessionStatus.DISCARDED))
    }

    //Una riga corrotta non deve impedire di aprire l'app: al peggio si riparte senza risposte
    private fun SessionEntity.decodeAnswers(): Map<Int, Int> =
        runCatching { jsonParser.decodeFromString<Map<Int, Int>>(answersJson) }
            .getOrDefault(emptyMap())

    private fun SessionEntity.toDomain() = Session(
        id = id,
        patientCode = patientCode,
        questionnaireId = questionnaireId,
        startedAt = startedAt,
        currentIndex = currentIndex,
        answers = decodeAnswers(),
    )
}