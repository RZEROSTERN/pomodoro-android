package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.TipsAndUpdates
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun NotificationsScreen() {
    var sessionEnd by rememberSaveable { mutableStateOf(true) }
    var breakEnd by rememberSaveable { mutableStateOf(true) }
    var dailyReminder by rememberSaveable { mutableStateOf(false) }
    var goalReached by rememberSaveable { mutableStateOf(true) }
    var motivationalTips by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        NotificationSectionHeader(
            icon = { Icon(Icons.Default.Alarm, contentDescription = null, modifier = Modifier.size(20.dp)) },
            title = "Session Alerts"
        )
        NotificationSwitchRow(
            label = "Focus session ended",
            description = "Notify when a Pomodoro is complete",
            checked = sessionEnd,
            onCheckedChange = { sessionEnd = it }
        )
        NotificationSwitchRow(
            label = "Break ended",
            description = "Notify when it's time to focus again",
            checked = breakEnd,
            onCheckedChange = { breakEnd = it }
        )

        HorizontalDivider()

        NotificationSectionHeader(
            icon = { Icon(Icons.Default.NotificationsNone, contentDescription = null, modifier = Modifier.size(20.dp)) },
            title = "Daily Reminders"
        )
        NotificationSwitchRow(
            label = "Daily start reminder",
            description = "Remind me to start my first Pomodoro",
            checked = dailyReminder,
            onCheckedChange = { dailyReminder = it }
        )
        NotificationSwitchRow(
            label = "Daily goal reached",
            description = "Celebrate when I hit my daily target",
            checked = goalReached,
            onCheckedChange = { goalReached = it }
        )

        HorizontalDivider()

        NotificationSectionHeader(
            icon = { Icon(Icons.Default.TipsAndUpdates, contentDescription = null, modifier = Modifier.size(20.dp)) },
            title = "Tips"
        )
        NotificationSwitchRow(
            label = "Motivational tips",
            description = "Occasional productivity tips and tricks",
            checked = motivationalTips,
            onCheckedChange = { motivationalTips = it }
        )
    }
}

@Composable
private fun NotificationSectionHeader(icon: @Composable () -> Unit, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        icon()
        Text(text = title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun NotificationSwitchRow(
    label: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsScreenPreview() {
    PomodoroTheme { NotificationsScreen() }
}

@Preview(showBackground = true)
@Composable
private fun NotificationsScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { NotificationsScreen() }
}
