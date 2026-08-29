package com.lalessio.patientkiosk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lalessio.patientkiosk.data.local.entities.MetaEntity

@Dao
interface MetaDao {
    @Query("SELECT value FROM meta WHERE `key` = :key")
    suspend fun findValue(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)        //Replace perché, ad esempio, se andiamo a inserire la chiave "Json-version: 1.1" lui andrà a sostituire il record con la versione vecchia del json
    suspend fun putEntry(entry: MetaEntity)
}