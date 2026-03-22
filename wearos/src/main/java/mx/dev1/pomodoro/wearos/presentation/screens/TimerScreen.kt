package mx.dev1.pomodoro.wearos.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.tooling.preview.devices.WearDevices
import mx.dev1.pomodoro.wearos.presentation.theme.PomodoroTheme

private enum class TimerMode { Focus, ShortBreak, LongBreak }

@Composable
fun TimerScreen(
    onSessionComplete: () -> Unit = {},
    onNavigateToStats: () -> Unit = {}
) {
    var isRunning by rememberSaveable { mutableStateOf(false) }
    var mode by rememberSaveable { mutableStateOf(TimerMode.Focus) }

    // UI-only: static progress and time values
    val progress = 0.65f
    val timeRemaining = if (mode == TimerMode.Focus) "16:15" else "03:42"
    val sessionCount = 3
    val taskName = "Mobile app design"

    val progressColor = when (mode) {
        TimerMode.Focus      -> MaterialTheme.colors.primary
        TimerMode.ShortBreak -> Color(0xFF4CAF50)
        TimerMode.LongBreak  -> Color(0xFF2196F3)
    }

    Scaffold(
        timeText = { TimeText() },
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            // Background track arc
            CircularProgressIndicator(
                progress = 1f,
                modifier = Modifier.fillMaxSize(),
                startAngle = 300f,
                endAngle = 240f,
                strokeWidth = 8.dp,
                indicatorColor = MaterialTheme.colors.onBackground.copy(alpha = 0.1f),
                trackColor = Color.Transparent
            )

            // Progress arc
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                startAngle = 300f,
                endAngle = 240f,
                strokeWidth = 8.dp,
                indicatorColor = progressColor,
                trackColor = Color.Transparent
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Mode label
                Text(
                    text = when (mode) {
                        TimerMode.Focus      -> "FOCUS"
                        TimerMode.ShortBreak -> "SHORT BREAK"
                        TimerMode.LongBreak  -> "LONG BREAK"
                    },
                    style = MaterialTheme.typography.caption2,
                    color = progressColor,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Countdown
                Text(
                    text = timeRemaining,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(2.dp))

                // Session counter
                Text(
                    text = "Session $sessionCount",
                    style = MaterialTheme.typography.caption2,
                    color = MaterialTheme.colors.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Play / Pause button
                Button(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = progressColor)
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "Pause" else "Play",
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Stop button
                    CompactButton(
                        onClick = { isRunning = false; onSessionComplete() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "Stop",
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    // Stats button
                    CompactButton(
                        onClick = onNavigateToStats,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.BarChart,
                            contentDescription = "Stats",
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            // Task name at the bottom
            Text(
                text = taskName,
                style = MaterialTheme.typography.caption2,
                color = MaterialTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 24.dp),
                maxLines = 1
            )
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun TimerScreenPreview() {
    PomodoroTheme { TimerScreen() }
}
