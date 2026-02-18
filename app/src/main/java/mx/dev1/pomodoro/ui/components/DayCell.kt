package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun DayCell(
    day: Int?,
    isSelected: Boolean,
    isOutlined: Boolean,
    indicatorColor: Color?,
    monthName: String,
    onClick: () -> Unit
) {
    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (isOutlined && !isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Box(
        modifier = Modifier
            .size(36.dp)
            .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(8.dp))
            .background(containerColor, RoundedCornerShape(8.dp))
            .semantics(mergeDescendants = true) {
                if (day != null) {
                    contentDescription = if (isSelected) {
                        "$monthName $day selected"
                    } else {
                        "$monthName $day"
                    }
                    selected = isSelected
                    role = Role.Button
                }
            }
            .clickable(enabled = day != null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (day != null) {
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
            if (!isSelected && indicatorColor != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 3.dp)
                        .size(6.dp)
                        .background(color = indicatorColor, shape = CircleShape)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DayCellPreview() {
    PomodoroTheme {
        DayCell(
            day = 17,
            isSelected = false,
            isOutlined = true,
            indicatorColor = MaterialTheme.colorScheme.secondary,
            monthName = "August",
            onClick = {}
        )
    }
}
