package com.lalessio.patientkiosk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lalessio.patientkiosk.data.local.entities.BandEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionnaireEntity
import com.lalessio.patientkiosk.data.local.entities.SubscaleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionnaireDao {
    //Flow serve a far avere sempre la value più recente al chiamante, ogni volta che c'è un nuovo valore room ri-emette
    //In questo caso non va suspend perché restituisce più valori nel tempo e non solo un set subito e arrivederci
    @Query("SELECT * FROM questionnaires ORDER BY position")
    fun observeQuestionnaires(): Flow<List<QuestionnaireEntity>>

    //suspend è l'equivalente di una async, non restituisce un valore finché il db non glielo fornisce e aspetta autonomamente
    @Query("SELECT * FROM questionnaires WHERE id = :questionnaireId")
    suspend fun findQuestionnaire(questionnaireId: String): QuestionnaireEntity?

    @Query("SELECT COUNT(*) FROM questionnaires")
    suspend fun countQuestionnaires(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionnaires(questionnaires: List<QuestionnaireEntity>)

    @Query("DELETE FROM questionnaires")
    suspend fun deleteAllQuestionnaires()

    @Query("SELECT * FROM questions WHERE questionnaireId = :questionnaireId ORDER BY position")
    suspend fun getQuestions(questionnaireId: String): List<QuestionEntity>

    @Query("SELECT * FROM subscales WHERE questionnaireId = :questionnaireId ORDER BY position")
    suspend fun getSubscales(questionnaireId: String): List<SubscaleEntity>

    @Insert
    suspend fun insertQuestions(questions: List<QuestionEntity>)

    @Insert
    suspend fun insertSubscales(subscales: List<SubscaleEntity>)

    @Insert
    suspend fun insertBands(bands: List<BandEntity>)

    @Query(
        """
    SELECT q.id, q.name, q.description, q.source,
           (SELECT COUNT(*) FROM questions WHERE questionnaireId = q.id) AS questionCount
    FROM questionnaires q
    ORDER BY q.position
"""
    )
    fun observeQuestionnaireSummaries(): Flow<List<QuestionnaireSummaryRow>>
}
