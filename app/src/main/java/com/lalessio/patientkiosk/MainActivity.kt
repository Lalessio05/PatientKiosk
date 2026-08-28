package com.lalessio.patientkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeViewModel
import com.lalessio.patientkiosk.ui.patientCode.PatientCodeScreen
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatientKioskTheme {
                //viewModel() serve a creare l'activity e a ridarti la stessa istanza a ogni re-render
                val viewModel: PatientCodeViewModel = viewModel()
                //Il by serve a dire, non darmi lo stato (che è come una scatola, su cui poi dovrei chiamare .value, ma dammi direttamente la value, collectAsStateWithLifecycle() serve a prendere uno stato (flow) e a tenerlo aggiornato finché l'app non va in background (ad esempio), continua finché è almeno STARTED
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { KioskTopBar() }
                ) { innerPadding->
                    PatientCodeScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        //:: è l'operatore di riferimento (a metodo in questo caso), in react avrei usato x.metodo senza le parentesi
                        onCodeChange = viewModel::onCodeChange,
                        onRandomCode = viewModel::onRandomCode,
                        onForward = {
                            val patientCode = viewModel.onForward()
                            if (patientCode !=  null) {
                                //Naviga al questionario
                            }

                        },

                        )
                }

                }
            }
        }
}
