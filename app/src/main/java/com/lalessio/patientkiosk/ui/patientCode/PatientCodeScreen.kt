package com.lalessio.patientkiosk.ui.patientCode

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun PatientCodeScreen(
    state: PatientCodeUiState,
    onCodeChange: (String) -> Unit,
    onRandomCode: () -> Unit,
    onForward: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.screen, vertical = Spacing.xl)
    ) {
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
            value = state.patientCode,
            onValueChange = onCodeChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("CODICE PAZIENTE") },
            placeholder = { Text("es. PZ-4192") },
            singleLine = true,
            isError = state.showError,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,     //Mette automaticamente tutti i caratteri maiuscoli
                imeAction = ImeAction.Done                              //Serve a dire cosa far mostrare alla tastiera come tasto "Invio", in questo caso mostra la spunta
            )
        )
        if (state.showError) {
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = "Il codice deve avere almeno 3 caratteri.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
        Spacer(Modifier.height(Spacing.lg))

        Button(
            onClick = onForward,
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
            onClick = onRandomCode
        ) {
            Text("Usa codice casuale")
        }
    }
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun PatientCodeScreenPreview() {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar() }
        ) { innerPadding ->
            PatientCodeScreen(
                modifier = Modifier.padding(innerPadding),
                state = PatientCodeUiState(),
                onCodeChange = {}, onRandomCode = {}, onForward = {},
            )
        }
    }
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun PatientCodeScreenErrorPreview() {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar() }
        ) { innerPadding ->
            PatientCodeScreen(
                modifier = Modifier.padding(innerPadding),
                state = PatientCodeUiState(patientCode = "PZ", showError = true),
                onCodeChange = {}, onRandomCode = {}, onForward = {},
            )
        }
    }
}