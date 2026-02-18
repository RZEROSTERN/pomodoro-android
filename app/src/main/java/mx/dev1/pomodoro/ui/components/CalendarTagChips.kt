package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

data class CalendarTagChipData(
    val text: String,
    val containerColor: androidx.compose.ui.graphics.Color
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CalendarTagChips(
    chips: List<CalendarTagChipData>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        chips.forEach { chip ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = chip.containerColor,
                modifier = Modifier.semantics { contentDescription = "${chip.text} tag" }
            ) {
                Text(
                    text = "${chip.text}   x",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalendarTagChipsPreview() {
    PomodoroTheme {
        CalendarTagChips(
            chips = listOf(
                CalendarTagChipData("Course", MaterialTheme.colorScheme.tertiaryContainer),
                CalendarTagChipData("Meeting", MaterialTheme.colorScheme.secondaryContainer),
                CalendarTagChipData("Language study", MaterialTheme.colorScheme.surfaceContainerHighest)
            )
        )
    }
}
