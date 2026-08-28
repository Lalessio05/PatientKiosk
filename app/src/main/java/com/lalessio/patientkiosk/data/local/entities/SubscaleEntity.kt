package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


/**
 * Il questionario HADS valuta sia ansia che depressione, quindi ogni domanda valuta o ansia o depressione
 **/
@Entity(
    tableName = "subscales",
    primaryKeys = ["questionnaireId", "key"],
    foreignKeys = [
        ForeignKey(
            entity = QuestionnaireEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionnaireId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index("questionnaireId")]
)
data class SubscaleEntity(
    /**Questionario a cui è associata la subscale**/
    val questionnaireId: String,
    val position: Int,
    val key: String,
    /**Ad esempio per HADS può essere "Ansia" e "Depressione"**/
    val label: String,
    /**Punteggio massimo di questa sottoscala*/
    val maxScore: Int
    )