package com.lalessio.patientkiosk.data.local

import androidx.room.TypeConverter
import com.lalessio.patientkiosk.data.local.entities.SessionStatus

class SessionStatusConverter {
    @TypeConverter
    fun statusToString(status: SessionStatus): String = status.name

    @TypeConverter
    fun stringToStatus(value: String): SessionStatus = SessionStatus.valueOf(value)
}