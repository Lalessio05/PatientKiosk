package com.lalessio.patientkiosk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.navigation.KioskNavGraph
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            setContent {
                PatientKioskTheme {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = { KioskTopBar() },
                    ) { innerPadding ->
                        KioskNavGraph(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

//#TODO Risolvere gli altri todo in giro per il codice
//#TODO Check generale UI rispetto al design
//#TODO Topbar
//#TODO Riorganizzare meglio i file nelle folder e separarli, in linea generale un file per classe
//#TODO Rinominare QuestionnaireListUiState e viewModel dato che servono anche a sources