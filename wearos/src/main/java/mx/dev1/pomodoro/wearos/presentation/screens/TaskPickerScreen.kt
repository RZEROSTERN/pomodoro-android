package mx.dev1.pomodoro.wearos.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import androidx.wear.tooling.preview.devices.WearDevices
import mx.dev1.pomodoro.wearos.presentation.theme.PomodoroTheme

private val sampleTasks = listOf(
    "No task",
    "Mobile app design",
    "Backend API review",
    "Study Kotlin coroutines",
    "Write unit tests",
    "Update documentation",
    "Fix UI bugs"
)

@Composable
fun TaskPickerScreen(
    onTaskSelected: (String) -> Unit = {}
) {
    var selectedTask by rememberSaveable { mutableStateOf(sampleTasks[0]) }
    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = listState) }
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            state = listState
        ) {
            item {
                Text(
                    text = "Select Task",
                    style = MaterialTheme.typography.caption1,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(sampleTasks) { task ->
                val isSelected = task == selectedTask
                Chip(
                    onClick = {
                        selectedTask = task
                        onTaskSelected(task)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(
                            text = task,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    icon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                modifier = Modifier.size(ChipDefaults.SmallIconSize)
                            )
                        }
                    } else null,
                    colors = if (isSelected)
                        ChipDefaults.primaryChipColors()
                    else
                        ChipDefaults.secondaryChipColors()
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun TaskPickerScreenPreview() {
    PomodoroTheme { TaskPickerScreen() }
}
