package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.CalendarDayIndicator
import mx.dev1.pomodoro.ui.components.CalendarMonthCard
import mx.dev1.pomodoro.ui.components.CalendarTagChipData
import mx.dev1.pomodoro.ui.components.CalendarTagChips
import mx.dev1.pomodoro.ui.components.WeeklyCourseChartCard
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun CalendarScreen() {
    var selectedDay by rememberSaveable { mutableIntStateOf(8) }

    val calendarIndicators = listOf(
        CalendarDayIndicator(day = 2, color = MaterialTheme.colorScheme.error),
        CalendarDayIndicator(day = 5, color = MaterialTheme.colorScheme.tertiary),
        CalendarDayIndicator(day = 13, color = MaterialTheme.colorScheme.secondary),
        CalendarDayIndicator(day = 14, color = MaterialTheme.colorScheme.primary),
        CalendarDayIndicator(day = 22, color = MaterialTheme.colorScheme.inversePrimary)
    )

    val chips = listOf(
        CalendarTagChipData("Course", MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.65f)),
        CalendarTagChipData("Yoga lesson", MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f)),
        CalendarTagChipData("Meeting", MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)),
        CalendarTagChipData("Software course", MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f)),
        CalendarTagChipData("Language study", MaterialTheme.colorScheme.surfaceContainerHighest)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CalendarMonthCard(
            monthName = "August",
            daysInMonth = 31,
            startDayOffset = 2,
            selectedDay = selectedDay,
            outlinedDay = 17,
            dayIndicators = calendarIndicators,
            onDaySelected = { selectedDay = it }
        )

        CalendarTagChips(chips = chips)
        Spacer(modifier = Modifier.height(4.dp))
        WeeklyCourseChartCard()
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    PomodoroTheme(darkTheme = false) {
        CalendarScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) {
        CalendarScreen()
    }
}
