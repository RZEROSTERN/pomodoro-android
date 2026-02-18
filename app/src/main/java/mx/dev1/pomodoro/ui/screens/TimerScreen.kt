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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.CircularProgressBar
import mx.dev1.pomodoro.ui.components.TaskCard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        TaskCard(
            title = "Mobile app design",
            completedSessions = 4,
            totalSessions = 6,
            totalTime = "150 minute",
            sessionTime = "25 minute",
            icon = Icons.Default.Home
        )

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressBar(
            modifier = Modifier.size(320.dp),
            initialValue = 50,
            primaryColor = MaterialTheme.colorScheme.primary,
            secondaryColor = MaterialTheme.colorScheme.surfaceVariant,
            circleRadius = 240f,
            onPositionChange = {},
            sessions = "1 of 6 sessions"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Take a break!")

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = { /* Lógica para iniciar */ },
                modifier = Modifier.size(64.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Start",
                    modifier = Modifier.size(32.dp)
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
