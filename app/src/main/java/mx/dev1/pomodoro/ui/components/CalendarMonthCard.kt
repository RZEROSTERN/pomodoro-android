package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.ui.graphics.Color
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

data class CalendarDayIndicator(
    val day: Int,
    val color: Color
)

fun buildMonthGrid(daysInMonth: Int, startDayOffset: Int): List<Int?> {
    require(daysInMonth in 28..31)
    require(startDayOffset in 0..6)

    val cells = MutableList<Int?>(startDayOffset) { null }
    cells.addAll((1..daysInMonth).toList())
    while (cells.size % 7 != 0) cells.add(null)
    return cells
}

@Composable
fun CalendarMonthCard(
    monthName: String,
    daysInMonth: Int,
    startDayOffset: Int,
    selectedDay: Int,
    outlinedDay: Int?,
    dayIndicators: List<CalendarDayIndicator>,
    onDaySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val gridCells = buildMonthGrid(daysInMonth = daysInMonth, startDayOffset = startDayOffset)
    val indicatorMap = dayIndicators.associateBy { it.day }
    val weekdayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous month",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = monthName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next month",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                weekdayLabels.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.width(36.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            gridCells.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    week.forEach { day ->
                        val isSelected = day == selectedDay
                        val isOutlined = day == outlinedDay
                        val dayIndicator = day?.let { indicatorMap[it] }

                        DayCell(
                            day = day,
                            isSelected = isSelected,
                            isOutlined = isOutlined,
                            indicatorColor = dayIndicator?.color,
                            monthName = monthName,
                            onClick = { day?.let(onDaySelected) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarMonthCardPreview() {
    PomodoroTheme {
        CalendarMonthCard(
            monthName = "August",
            daysInMonth = 31,
            startDayOffset = 2,
            selectedDay = 8,
            outlinedDay = 17,
            dayIndicators = listOf(
                CalendarDayIndicator(day = 2, color = MaterialTheme.colorScheme.error),
                CalendarDayIndicator(day = 5, color = MaterialTheme.colorScheme.tertiary),
                CalendarDayIndicator(day = 13, color = MaterialTheme.colorScheme.secondary)
            ),
            onDaySelected = {}
        )
    }
}
