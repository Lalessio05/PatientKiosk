package com.lalessio.patientkiosk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lalessio.patientkiosk.data.local.dao.QuestionnaireDao
import com.lalessio.patientkiosk.data.local.dao.SessionDao
import com.lalessio.patientkiosk.data.local.entities.BandEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionEntity
import com.lalessio.patientkiosk.data.local.entities.QuestionnaireEntity
import com.lalessio.patientkiosk.data.local.entities.SessionEntity
import com.lalessio.patientkiosk.data.local.entities.SubscaleEntity

@TypeConverters(SessionStatusConverter::class)
@Database(
    entities = [QuestionnaireEntity::class, QuestionEntity::class, SubscaleEntity::class, BandEntity::class, SessionEntity::class],
    version = 1,
    exportSchema = false,       //Esporta il db in un file per il versioning di questo, non ci serve
)
abstract class AppDatabase : RoomDatabase() {

    //Un metodo per DAO, poi li genera Room, per questo abstract
    abstract fun questionnaireDao(): QuestionnaireDao
    abstract fun sessionDao(): SessionDao

    //Qui dentro ci vanno i metodi "statici", se in c# posso etichettare qualcosa come static, in kotlin vanno messi dentro questo companion object.
    //A livello pratico è un singleton annidato dentro alla classe
    companion object {
        private const val DATABASE_NAME = "patientkiosk.db"

        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                //A ogni cambio di schema butta via il vecchio DB (droppa tutto) e ri-genererà dal json
                .fallbackToDestructiveMigration(true)
                .build()
    }
}