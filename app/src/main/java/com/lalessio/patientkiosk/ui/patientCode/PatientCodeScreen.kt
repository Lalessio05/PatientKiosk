package com.lalessio.patientkiosk.ui.patientCode

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import com.lalessio.patientkiosk.ui.components.ErrorBox
import com.lalessio.patientkiosk.ui.components.KioskTopBar
import com.lalessio.patientkiosk.ui.components.LoadingBox
import com.lalessio.patientkiosk.ui.theme.PatientKioskTheme
import com.lalessio.patientkiosk.ui.theme.Spacing

@Composable
fun PatientCodeScreen(
    state: PatientCodeUiState,
    onCodeChange: (String) -> Unit,
    onRandomCode: () -> Unit,
    onForward: () -> Unit,
    onResume: () -> Unit,
    onDiscardSession: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isLoading) {
        LoadingBox(modifier)
        return
    }
    if (state.errorMessage != null) {
        ErrorBox(
            title = "Impossibile caricare i questionari",
            message = state.errorMessage,
            modifier = modifier,
        )
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.screen, vertical = Spacing.xl)
    ) {
        //Parte scorrevole: con la tastiera aperta e il font ingrandito
        //il contenuto non ci sta
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "PASSO 1 DI 3",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(Spacing.md))

            Text(
                text = "Identificazione Paziente",
                style = MaterialTheme.typography.displaySmall,
            )
            Spacer(Modifier.height(Spacing.md))

            Text(
                text = "Inserisca il codice riportato sul foglio consegnato in accettazione. " +
                        "Nessun dato personale verrà richiesto.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Done,
                ),
            )

            if (state.showError) {
                Spacer(Modifier.height(Spacing.sm))
                Text(
                    text = "Il codice deve avere almeno 3 caratteri.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            state.resumableSession?.let { session ->
                Spacer(Modifier.height(Spacing.lg))
                ResumeBox(
                    session = session,
                    onResume = onResume,
                    onDiscard = onDiscardSession,
                )
            }
        }

        //Azioni ancorate in fondo, come nel mockup
        Spacer(Modifier.height(Spacing.lg))
        Button(
            onClick = onForward,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 60.dp),
        ) {
            Text(
                text = "AVANTI",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
        }
        TextButton(onClick = onRandomCode) { Text("Usa codice casuale") }
    }
}

@Composable
private fun ResumeBox(
    session: ResumableSession,
    onResume: () -> Unit,
    onDiscard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.primary)
            .padding(Spacing.lg),
    ) {
        Text(
            text = "COMPILAZIONE INTERROTTA",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(Modifier.height(Spacing.sm))
        Text(
            text = "${session.questionnaireName} — paziente ${session.patientCode}, " +
                    "domanda ${session.currentIndex + 1} di ${session.questionCount}.",
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(Modifier.height(Spacing.md))
        Button(
            onClick = onResume,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp),
        ) {
            Text(
                text = "Riprendi compilazione",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
            )
        }
        TextButton(onClick = onDiscard) { Text("Scarta e ricomincia") }
    }
}

@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun PatientCodeScreenResumePreview() {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar() },
        ) { innerPadding ->
            PatientCodeScreen(
                modifier = Modifier.padding(innerPadding),
                state = PatientCodeUiState(
                    isLoading = false,
                    resumableSession = ResumableSession(
                        sessionId = 1,
                        patientCode = "PZ-4192",
                        questionnaireId = "HADS",
                        questionnaireName = "Hospital Anxiety and Depression Scale",
                        currentIndex = 5,
                        questionCount = 14,
                    ),
                ),
                onCodeChange = {}, onRandomCode = {}, onForward = {},
                onResume = {}, onDiscardSession = {},
            )
        }
    }
}


@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun PatientCodeScreenPreview() {
    PatientKioskTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { KioskTopBar() },
        ) { innerPadding ->
            PatientCodeScreen(
                modifier = Modifier.padding(innerPadding),
                state = PatientCodeUiState(isLoading = false),
                onCodeChange = {}, onRandomCode = {}, onForward = {},
                onResume = {}, onDiscardSession = {},
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
            topBar = { KioskTopBar() },
        ) { innerPadding ->
            PatientCodeScreen(
                modifier = Modifier.padding(innerPadding),
                state = PatientCodeUiState(patientCode = "PZ", showError = true, isLoading = false),
                onCodeChange = {}, onRandomCode = {}, onForward = {},
                onResume = {}, onDiscardSession = {},
            )
        }
    }
}
