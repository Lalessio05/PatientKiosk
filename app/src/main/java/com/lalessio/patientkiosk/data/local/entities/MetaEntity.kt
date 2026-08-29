package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "meta")
/**Contiene i dati generali del programma, ad esempio l'ultima versione del json letta**/
data class MetaEntity(
    @PrimaryKey val key: String,
    val value: String
)
