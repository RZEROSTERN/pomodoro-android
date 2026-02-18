package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.TasksDayStrip
import mx.dev1.pomodoro.ui.components.TasksEmptyState
import mx.dev1.pomodoro.ui.components.dayIndexFromSunday
import mx.dev1.pomodoro.ui.components.taskWeekItemsForDate
import java.time.LocalDate
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun TasksScreen(referenceDate: LocalDate = LocalDate.now()) {
    val weekItems = taskWeekItemsForDate(referenceDate)
    val initialIndex = dayIndexFromSunday(referenceDate)
    var selectedIndex by rememberSaveable(referenceDate) { mutableIntStateOf(initialIndex) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.Top
    ) {
        TasksDayStrip(
            items = weekItems,
            selectedIndex = selectedIndex,
            onSelectedIndexChange = { selectedIndex = it }
        )

        Spacer(modifier = Modifier.height(220.dp))

        TasksEmptyState(
            title = "No tasks for today",
            description = "No active tasks found for this day. Add one with the center action button."
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {
    PomodoroTheme(darkTheme = false) {
        TasksScreen(referenceDate = LocalDate.of(2026, 2, 17))
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) {
        TasksScreen(referenceDate = LocalDate.of(2026, 2, 17))
    }
}
