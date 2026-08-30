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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.navigation.KioskNavGraph
import com.lalessio.patientkiosk.ui.navigation.topBarTextFor
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Il NavController va creato qui e passato al NavGraph: serve anche alla top bar per sapere
            //che schermata è a video. Se lo lasciassimo creare al NavGraph ne avremmo due diversi e
            //quello osservato qui non sarebbe agganciato a nessun NavHost (backStackEntry sempre null).
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()

            PatientKioskTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        KioskTopBar(
                            text = topBarTextFor(
                                route = backStackEntry?.destination?.route,
                                arguments = backStackEntry?.arguments,
                            )
                        )
                    },
                ) { innerPadding ->
                    KioskNavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                    )
                }
            }
        }
    }
}

