package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun MyTrackerScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(96.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("My Tracker Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTrackerScreenPreview() {
    PomodoroTheme {
        MyTrackerScreen()
    }
}
