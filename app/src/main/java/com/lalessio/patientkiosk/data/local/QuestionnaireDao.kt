package com.lalessio.patientkiosk.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionnaireDao {
    //Flow serve a far avere sempre la value più recente al chiamante, ogni volta che c'è un nuovo valore room ri-emette
    //In questo caso non va suspend perché restituisce più valori e non solo uno
    @Query("SELECT * FROM questionnaires ORDER BY position")
    fun getAll(): Flow<List<QuestionnaireEntity>>

    //suspend è l'equivalente di una async, non restituisce un valore finché il db non glielo fornisce e aspetta autonomamente
    @Query("SELECT * FROM questionnaires WHERE id = :id")
    fun get(id: String): QuestionnaireEntity?

    @Query("SELECT COUNT(*) FROM questionnaires")
    fun count(): Int

    @Insert
    fun insertAll(items: List<QuestionnaireEntity>)

    @Query("DELETE FROM questionnaires")
    suspend fun deleteAll()
}