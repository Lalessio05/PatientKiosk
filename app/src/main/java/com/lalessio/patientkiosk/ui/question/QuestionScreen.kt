package com.lalessio.patientkiosk.ui.question

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lalessio.patientkiosk.ui.theme.Spacing
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

//Ritardo dell'avanzamento automatico dopo la selezione di una risposta
private const val AUTO_ADVANCE_MS = 220L

@Composable
fun QuestionScreen(
    state: QuestionUiState,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    modifier: Modifier = Modifier,
) {
    //Con TalkBack attivo l'avanzamento automatico cambierebbe schermata
    //mentre lo screen reader sta ancora leggendo: lo disattiviamo.
    val context = LocalContext.current
    val screenReaderOn = remember {
        val manager = context.getSystemService(Context.ACCESSIBILITY_SERVICE)
                as AccessibilityManager
        manager.isTouchExplorationEnabled
    }

    LaunchedEffect(state.selectedOption, state.currentIndex) {
        if (state.pendingAutoAdvance && !screenReaderOn && !state.isLastQuestion) {
            delay(AUTO_ADVANCE_MS.milliseconds)
            onNext()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        LinearProgressIndicator(
            progress = { state.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            gapSize = 0.dp,
            drawStopIndicator = {},
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Spacing.screen, vertical = Spacing.lg),
        ) {
            Text(
                text = "DOMANDA ${state.currentIndex + 1} DI ${state.questionCount}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(Spacing.sm))
            Text(
                text = state.recall,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(Spacing.md))
            Text(
                text = state.questionText,
                style = MaterialTheme.typography.headlineSmall,
            )
            Spacer(Modifier.height(Spacing.xl))

            state.options.forEachIndexed { index, option ->
                AnswerButton(
                    label = option.label,
                    selected = index == state.selectedOption,
                    onClick = { onAnswerSelected(index) },
                )
                Spacer(Modifier.height(Spacing.sm))
            }
        }

        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.outline)

        Column(modifier = Modifier.padding(Spacing.screen)) {
            if (state.selectedOption == null) {
                Text(
                    text = "Selezioni una risposta per continuare.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(Spacing.sm))
            }
            Button(
                onClick = onNext,
                enabled = state.selectedOption != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp),
            ) {
                Text(
                    text = if (state.isLastQuestion) "Calcola risultato" else "Prossima domanda",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                )
            }
            Spacer(Modifier.height(Spacing.xs))
            TextButton(onClick = onPrevious) { Text("← Indietro") }
        }
    }
}

@Composable
private fun AnswerButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 62.dp)
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceContainerLowest
            )
            .border(
                width = 2.dp,
                color = if (selected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
            )
            //selectable annuncia a TalkBack "selezionato"/"non selezionato"
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .padding(horizontal = Spacing.lg, vertical = Spacing.md),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onSurface,
        )
    }
}

//#TODO Fare varie preview
//#TODO Mancano le animazioni, è bruttino così