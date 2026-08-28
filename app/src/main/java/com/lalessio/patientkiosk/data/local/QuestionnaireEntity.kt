package com.lalessio.patientkiosk.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//#TODO Capire i commenti di ogni colonna, appena avrò un json lo capirò
@Entity(tableName = "questionnaires")
data class QuestionnaireEntity(
    @PrimaryKey val id: String,
    val name: String,
    val desc: String,
    val recall: String, //Finestra temporale mostrata sopra la domanda (Es. per DLQI è "Ultima settimana")
    val scale: Int,     //Moltiplicatore del punteggio, per avere una scala univoca (Es. per WHO-5 è 4)
    val max: Int,
    val source: String, //Citazione bibliografica
    val position: Int,  //Ordine di comparsa nel json
)