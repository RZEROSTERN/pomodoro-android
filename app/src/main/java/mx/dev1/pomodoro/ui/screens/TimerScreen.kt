package mx.dev1.pomodoro.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import mx.dev1.pomodoro.ui.components.CircularProgressBar

private const val TIMER_DURATION_SECONDS = 25 * 60   // 25 minutes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimerScreen(onSaveSession: () -> Unit = {}) {
    var remainingSeconds by rememberSaveable { mutableIntStateOf(TIMER_DURATION_SECONDS) }
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var showStopDialog by rememberSaveable { mutableStateOf(false) }

    // Countdown tick
    LaunchedEffect(isRunning) {
        while (isRunning && remainingSeconds > 0) {
            delay(1_000L)
            remainingSeconds--
        }
        if (remainingSeconds == 0) {
            isRunning = false
            // TODO: trigger session-complete event
        }
    }

    val progress = 1f - remainingSeconds.toFloat() / TIMER_DURATION_SECONDS.toFloat()
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timeText = "%02d:%02d".format(minutes, seconds)

    // Stop confirmation dialog
    if (showStopDialog) {
        AlertDialog(
            onDismissRequest = { showStopDialog = false },
            title = { Text("Stop session?") },
            text = {
                Text("If you stop now, the current session will be lost and the timer will restart from the beginning.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        isRunning = false
                        remainingSeconds = TIMER_DURATION_SECONDS
                        showStopDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Stop & reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showStopDialog = false }) {
                    Text("Keep going")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Show TaskCard only when a session is actively linked to a task
        // if (activeTask != null) { TaskCard(...) }

        CircularProgressBar(
            modifier = Modifier.size(320.dp),
            progress = progress,
            primaryColor = MaterialTheme.colorScheme.primary,
            secondaryColor = MaterialTheme.colorScheme.surfaceVariant,
            circleRadius = 240f,
            sessions = "Focus session",
            timeText = timeText
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isRunning) "Stay focused!" else if (progress > 0f) "Paused" else "Ready to focus?",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Play / Stop button
            Button(
                onClick = {
                    if (isRunning) {
                        showStopDialog = true
                    } else {
                        isRunning = true
                    }
                },
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(1.dp),
                colors = if (isRunning)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                else
                    ButtonDefaults.buttonColors()
            ) {
                Icon(
                    imageVector = if (isRunning) Icons.Default.Stop else Icons.Default.PlayArrow,
                    contentDescription = if (isRunning) "Stop" else "Start",
                    modifier = Modifier.size(32.dp)
                )
            }

            // Save session button
            OutlinedButton(
                onClick = onSaveSession,
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bookmark,
                    contentDescription = "Save session",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}
