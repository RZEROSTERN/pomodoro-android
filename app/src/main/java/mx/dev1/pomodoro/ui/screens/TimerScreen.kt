package mx.dev1.pomodoro.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.CircularProgressBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    Scaffold (
        topBar = { TopAppBar(title = { Text("Pomodoro") }) },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CircularProgressBar(
                    modifier = Modifier
                        .size(400.dp),
                    initialValue = 50,
                    primaryColor = Color.Green,
                    secondaryColor = Color.LightGray,
                    circleRadius = 320f,
                    onPositionChange = {},
                    sessions = "1 of 6 sessions"
                )

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
    )
}

@Preview
@Composable
fun TimerScreenPreview() {
    TimerScreen()
}