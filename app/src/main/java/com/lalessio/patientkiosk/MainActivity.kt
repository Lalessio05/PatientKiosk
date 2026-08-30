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

//#TODO Risolvere gli altri todo in giro per il codice
//#TODO Check generale UI rispetto al design
//#TODO Riorganizzare meglio i file nelle folder e separarli, in linea generale un file per classe, ma parliamone
//#TODO Rinominare QuestionnaireListUiState e viewModel dato che servono anche a sources e spostarli(?)
/*#TODO cambiare font
private val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

private val Archivo = FontFamily(
    Font(GoogleFont("Archivo"), provider, FontWeight.Normal),
    Font(GoogleFont("Archivo"), provider, FontWeight.SemiBold),
    Font(GoogleFont("Archivo"), provider, FontWeight.ExtraBold),
)

types.kt

dipendenza androidx.compose.ui:ui-text-google-fonts
 */
//#TODO Sostituire il carattere della freccetta con le icone di Material
