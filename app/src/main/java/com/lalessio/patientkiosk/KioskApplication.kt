package com.lalessio.patientkiosk

import android.app.Application
import com.lalessio.patientkiosk.data.json.QuestionnaireImporter
import com.lalessio.patientkiosk.data.local.AppDatabase

class KioskApplication : Application() {
    //by lazy serve a dirgli "Non pigliare il valore fin da subito, prendilo la prima volta che qualcuno ti legge"
    val database: AppDatabase by lazy { AppDatabase.build(this) }
    val importer: QuestionnaireImporter by lazy {
        QuestionnaireImporter(this, database)
    }
}