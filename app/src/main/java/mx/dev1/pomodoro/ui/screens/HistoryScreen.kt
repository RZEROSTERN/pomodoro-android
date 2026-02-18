package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.HistorySectionHeader
import mx.dev1.pomodoro.ui.components.HistoryTaskCard
import mx.dev1.pomodoro.ui.components.defaultHistorySections
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun HistoryScreen() {
    val sections = defaultHistorySections()
    val scrollState = rememberScrollState()
    var revealedCardId by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(scrollState.isScrollInProgress) {
        if (scrollState.isScrollInProgress) revealedCardId = null
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { revealedCardId = null })
            }
            .verticalScroll(scrollState)
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        sections.forEachIndexed { sectionIndex, section ->
            HistorySectionHeader(
                title = section.title,
                dateLabel = section.dateLabel
            )

            section.items.forEach { item ->
                HistoryTaskCard(
                    item = item,
                    isRevealed = revealedCardId == item.id,
                    onRevealChange = { reveal ->
                        revealedCardId = if (reveal) item.id else null
                    }
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            if (sectionIndex < sections.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    PomodoroTheme {
        HistoryScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) {
        HistoryScreen()
    }
}
