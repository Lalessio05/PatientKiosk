package com.lalessio.patientkiosk.ui.screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.theme.Spacing

@Preview
@Composable
fun PatientCodeScreen(
    modifier: Modifier = Modifier,
    onForward: (String) -> Unit = {},       //Funzione come parametro opzionale, con un singolo parametro string, la quale ritorna void
) {
    var patientCode by rememberSaveable { mutableStateOf("") }
    var showError by rememberSaveable {mutableStateOf(false) }

    val validCode = patientCode.trim().length >= 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        //#TODO Da fixare, intersecato con la telecamera e il resto
        KioskTopBar()
        Column(modifier = Modifier.padding(horizontal = Spacing.screen, vertical = Spacing.xl)) {
            Text(
                text = "PASSO 1 DI 3",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(Spacing.md))

            Text(
                text = "Identificazione Paziente",
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(Modifier.height(Spacing.md))

            Text(
                text = "Inserisca il codice riportato sul foglio consegnato in accettazione. Nessun dato personale verrà richiesto.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(Spacing.lg))

            OutlinedTextField(
                value = patientCode,
                onValueChange = {
                    patientCode = it.uppercase()    //it sarebbe x=>x. delle lambda classiche
                    showError = false               //Resetta l'errore
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {Text("es. PZ-4192")},
                singleLine = true,
                isError = showError,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,     //Mette automaticamente tutti i caratteri maiuscoli
                    imeAction = ImeAction.Done                              //Serve a dire cosa far mostrare alla tastiera come tasto "Invio", in questo caso mostra la spunta
                )
            )
            if (showError){
                Text(
                    text="Il codice deve avere almeno 3 caratteri.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(Modifier.height(Spacing.lg))

            Button(
                onClick = {
                    if (validCode) onForward(patientCode) else showError = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "AVANTI",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
            TextButton(
                onClick = {patientCode = "PZ-"+ (1000..9999).random().toString()}
            ) {
                Text("Usa codice casuale")
            }
        }
    }
}

