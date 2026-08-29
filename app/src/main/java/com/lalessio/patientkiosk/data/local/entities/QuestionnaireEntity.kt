package com.lalessio.patientkiosk.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionnaires")
data class QuestionnaireEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val recall: String, //Finestra temporale mostrata sopra la domanda (Es. per DLQI è "Ultima settimana")
    val scale: Int,     //Moltiplicatore del punteggio, per avere una scala univoca (Es. per WHO-5 è 4)
    val maxScore: Int,
    val source: String, //Citazione bibliografica
    val position: Int,  //Ordine di comparsa nel json
)