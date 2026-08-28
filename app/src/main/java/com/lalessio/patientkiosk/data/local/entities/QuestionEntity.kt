package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "questions",
    foreignKeys = [
        ForeignKey(
            entity = QuestionnaireEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionnaireId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubscaleEntity::class,
            parentColumns = ["questionnaireId", "key"],
            childColumns = ["questionnaireId", "subScaleKey"],
        )
    ],
    indices = [Index("questionnaireId"), Index("questionnaireId", "subScaleKey")]
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionnaireId: String,
    /**Ordine voluto dal JSON: senza ORDER BY una tabella non ha ordine garantito. Si potrebbe usare l'id, ma quello cambia a ogni reimport mentre position no.**/
    val position: Int,      //Numero domanda
    val text: String,
    val subScaleKey: String?,  //Nullable, solo HADS ce l'ha
    val optionsJson: String //Risposte serializzate, si potrebbe fare un'altra tabella da joinare, preferisco tenerlo raw
)