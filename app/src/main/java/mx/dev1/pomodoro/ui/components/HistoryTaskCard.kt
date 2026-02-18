package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import kotlin.math.roundToInt

data class HistoryTaskItem(
    val id: String,
    val title: String,
    val category: String,
    val duration: String,
    val timeRange: String,
    val icon: ImageVector,
    val iconContainerColor: Color
)

data class HistorySection(
    val title: String,
    val dateLabel: String,
    val items: List<HistoryTaskItem>
)

fun defaultHistorySections(): List<HistorySection> = listOf(
    HistorySection(
        title = "Today",
        dateLabel = "17 Aug 2021",
        items = listOf(
            HistoryTaskItem("today-languages", "Languages study", "Education", "1h 15m", "12:00 - 01:15", Icons.Default.School, Color(0xFF59D1B4)),
            HistoryTaskItem("today-yoga", "Yoga lesson", "Relaxing time", "30m", "01:15 - 01:45", Icons.Default.Spa, Color(0xFFFFA65C)),
            HistoryTaskItem("today-mobile-design", "Mobile app design", "Work", "2h 30m", "01:45 - 04:15", Icons.Default.Computer, Color(0xFF59A7F0)),
            HistoryTaskItem("today-software", "Software course", "Education", "1h 45m", "04:15 - 06:00", Icons.Default.School, Color(0xFFF2C53D)),
            HistoryTaskItem("today-relax", "Relax music", "Meditation", "45m", "06:00 - 06:45", Icons.Default.Spa, Color(0xFF7B83F2))
        )
    ),
    HistorySection(
        title = "Yesterday",
        dateLabel = "17 Aug 2021",
        items = listOf(
            HistoryTaskItem("yesterday-yoga", "Yoga lesson", "Relaxing time", "1h 30m", "09:00 - 10:30", Icons.Default.Spa, Color(0xFF59D1B4)),
            HistoryTaskItem("yesterday-home-design", "Home page design", "Work", "1h 15m", "10:30 - 11:45", Icons.Default.Home, Color(0xFFFFA65C))
        )
    )
)

@Composable
fun HistoryTaskCard(
    item: HistoryTaskItem,
    isRevealed: Boolean,
    onRevealChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val actionWidth = 56.dp
    val density = LocalDensity.current
    val actionWidthPx = with(density) { actionWidth.toPx() }
    var offsetX by remember(item.id) { mutableFloatStateOf(0f) }
    var cardHeightPx by remember(item.id) { mutableFloatStateOf(0f) }
    val cardHeightDp = with(density) { cardHeightPx.toDp() }

    LaunchedEffect(isRevealed, actionWidthPx) {
        offsetX = if (isRevealed) -actionWidthPx else 0f
    }

    Box(modifier = modifier.fillMaxWidth()) {
        if (isRevealed || offsetX < 0f) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .then(
                            if (cardHeightPx > 0f) Modifier.height(cardHeightDp) else Modifier.fillMaxHeight()
                        )
                        .width(actionWidth)
                        .background(MaterialTheme.colorScheme.error, RoundedCornerShape(10.dp))
                        .semantics { contentDescription = "Delete ${item.title} ${item.timeRange}" },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onError
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .onSizeChanged { size ->
                    cardHeightPx = size.height.toFloat()
                }
                .offset { androidx.compose.ui.unit.IntOffset(offsetX.roundToInt(), 0) }
                .pointerInput(item.title) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            offsetX = (offsetX + dragAmount).coerceIn(-actionWidthPx, 0f)
                        },
                        onDragEnd = {
                            onRevealChange(offsetX <= (-actionWidthPx * 0.5f))
                        }
                    )
                }
                .semantics {
                    contentDescription = "${item.title}, ${item.category}, ${item.duration}, ${item.timeRange}"
                },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(item.iconContainerColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.category,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = item.duration,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = item.timeRange,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Open ${item.title}",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryTaskCardPreview() {
    PomodoroTheme {
        HistoryTaskCard(
            item = defaultHistorySections().first().items.first(),
            isRevealed = false,
            onRevealChange = {}
        )
    }
}
