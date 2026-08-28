package com.lalessio.patientkiosk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.lalessio.patientkiosk.data.local.entities.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    /**@return Id del record appena creato**/
    @Insert
    suspend fun insertSession(session: SessionEntity): Long

    @Update
    suspend fun updateSession(session: SessionEntity)

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun findSession(id: Long): SessionEntity?

    @Query("SELECT * FROM sessions WHERE status = 'IN_PROGRESS' ORDER BY startedAt DESC LIMIT 1")
    fun observeResumableSession(): Flow<SessionEntity?>

    @Query("UPDATE sessions SET status = 'DISCARDED' WHERE status = 'IN_PROGRESS'")
    suspend fun discardInProgressSessions()
}
