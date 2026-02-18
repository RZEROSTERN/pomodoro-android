package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.MyTrackerCategoryRow
import mx.dev1.pomodoro.ui.components.MyTrackerProgressCard
import mx.dev1.pomodoro.ui.components.defaultTrackerCategories
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun MyTrackerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.Top
    ) {
        MyTrackerProgressCard(
            progressPercent = 72,
            totalTasks = 78
        )

        Spacer(modifier = Modifier.height(60.dp))

        MyTrackerCategoryRow(categories = defaultTrackerCategories())

        Spacer(modifier = Modifier.height(60.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            androidx.compose.material3.Text(
                text = "VIEW DETAILS",
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTrackerScreenPreview() {
    PomodoroTheme {
        MyTrackerScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTrackerScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) {
        MyTrackerScreen()
    }
}
