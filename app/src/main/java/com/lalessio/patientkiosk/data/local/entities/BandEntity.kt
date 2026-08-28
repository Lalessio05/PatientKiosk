package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bands",
    foreignKeys = [
        ForeignKey(
            entity = QuestionnaireEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionnaireId"],
            onDelete = ForeignKey.CASCADE

        )
    ],
    indices = [Index("questionnaireId")]
)
/**Una fascia, ad esempio da 0-10 lieve, 11-20 medio...**/
data class BandEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val questionnaireId: String,
    val position: Int,
    //Indici inclusivi
    val min: Int,
    val max: Int,
    val label: String,
    val note: String
)