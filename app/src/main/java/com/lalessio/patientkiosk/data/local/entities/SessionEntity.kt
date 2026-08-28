package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val patientCode: String,
    val questionnaireId: String,
    /**Orario in ms**/
    val startedAt: Long,
    val currentIndex: Int,
    /**Mappa posizione -> risposta**/
    val answersJson: String = "{}",
    val status: SessionStatus,
    val completedAt: Long? = null
)