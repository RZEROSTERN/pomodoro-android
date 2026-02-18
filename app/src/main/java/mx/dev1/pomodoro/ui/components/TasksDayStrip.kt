package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

data class TaskDayItem(
    val weekDay: String,
    val dayNumber: Int
)

fun dayIndexFromSunday(date: LocalDate): Int = date.dayOfWeek.value % 7

fun taskWeekItemsForDate(referenceDate: LocalDate): List<TaskDayItem> {
    val startOfWeek = referenceDate.minusDays(dayIndexFromSunday(referenceDate).toLong())
    return (0L..6L).map { offset ->
        val day = startOfWeek.plusDays(offset)
        TaskDayItem(
            weekDay = day.shortWeekday(),
            dayNumber = day.dayOfMonth
        )
    }
}

private fun LocalDate.shortWeekday(): String = when (dayOfWeek) {
    DayOfWeek.SUNDAY -> "Sun"
    DayOfWeek.MONDAY -> "Mon"
    DayOfWeek.TUESDAY -> "Tue"
    DayOfWeek.WEDNESDAY -> "Wed"
    DayOfWeek.THURSDAY -> "Thu"
    DayOfWeek.FRIDAY -> "Fri"
    DayOfWeek.SATURDAY -> "Sat"
    else -> "Sun"
}

@Composable
fun TasksDayStrip(
    items: List<TaskDayItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        itemsIndexed(items) { index, item ->
            val isSelected = index == selectedIndex
            val containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow
            val primaryTextColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            val secondaryTextColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            Box(
                modifier = Modifier
                    .size(width = 66.dp, height = 96.dp)
                    .background(containerColor, RoundedCornerShape(12.dp))
                    .clickable { onSelectedIndexChange(index) }
                    .semantics {
                        contentDescription = "${item.weekDay} ${item.dayNumber}"
                        selected = isSelected
                        role = Role.Button
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = item.weekDay,
                        style = MaterialTheme.typography.labelMedium,
                        color = primaryTextColor
                    )
                    Text(
                        text = item.dayNumber.toString(),
                        modifier = Modifier.padding(top = 6.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = secondaryTextColor
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TasksDayStripPreview() {
    PomodoroTheme {
        TasksDayStrip(
            items = taskWeekItemsForDate(LocalDate.of(2026, 2, 17)),
            selectedIndex = 2,
            onSelectedIndexChange = {}
        )
    }
}
