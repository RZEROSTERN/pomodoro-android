package mx.dev1.pomodoro.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun MainScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("25:00", style = MaterialTheme.typography.displayLarge)
        Row {
            Button(
                onClick = {

                },
            ) {
                Text("Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {

                }
            ) {
                Text("Reset")
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}