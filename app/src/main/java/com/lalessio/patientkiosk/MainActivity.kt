package com.lalessio.patientkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lalessio.patientkiosk.data.local.entities.QuestionnaireEntity
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeScreen
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeViewModel
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //LocalContext.current ottiene il contesto dell'app android, come se in react passassi un context a ogni componente contenente le info del sito web. Contiene (oltre alla roba android) il nostro db dichiarato in KioskApplication
            val context = LocalContext.current
            val questionnaireDao = remember {
                (context.applicationContext as KioskApplication).database.questionnaireDao()
            }
            val questionnaires by questionnaireDao.observeQuestionnaires()
                .collectAsStateWithLifecycle(initialValue = emptyList())
            val scope = rememberCoroutineScope()
            LaunchedEffect(Unit) {
                (context.applicationContext as KioskApplication).importer.importIfNeeded()
            }
            PatientKioskTheme {
                //viewModel() serve a creare il ViewModel e a ridarti la stessa istanza a ogni re-render
                val viewModel: PatientCodeViewModel = viewModel()
                //Il by serve a dire, non darmi lo stato (che è come una scatola, su cui poi dovrei chiamare .value, ma dammi direttamente la value, collectAsStateWithLifecycle() serve a prendere uno stato (flow) e a tenerlo aggiornato finché l'app non va in background (ad esempio), continua finché è almeno STARTED
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { KioskTopBar() }
                ) { innerPadding ->
                    PatientCodeScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = uiState,
                        //:: è l'operatore di riferimento (a metodo in questo caso), in react avrei usato x.metodo senza le parentesi
                        onCodeChange = viewModel::onCodeChange,
                        onRandomCode = viewModel::onRandomCode,
//                        onForward = {
//                            val patientCode = viewModel.onForward()
//                            if (patientCode !=  null) {
//                                //Naviga al questionario
//                            }
//
//                        },
                        //#TODO Rimuovere, test per vedere il db che funge
                        onForward = {
                            scope.launch {
                                questionnaireDao.insertQuestionnaires(
                                    listOf(
                                        QuestionnaireEntity(
                                            id = "TEST-${(1..999).random()}",
                                            name = "Prova", description = "", recall = "",
                                            scale = 1, maxScore = 30, source = "", position = 0,
                                        )
                                    )
                                )
                            }
                        },
                    )
                }

            }
            Text("Righe nel DB: ${questionnaires.size}")

        }
    }
}
