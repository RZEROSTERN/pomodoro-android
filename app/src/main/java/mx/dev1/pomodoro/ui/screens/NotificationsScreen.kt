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
import mx.dev1.pomodoro.ui.components.PremiumSectionBanner
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun NotificationsScreen(onNavigateToPremium: () -> Unit = {}) {
    var sessionEnd by rememberSaveable { mutableStateOf(true) }
    var breakEnd by rememberSaveable { mutableStateOf(true) }
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

        PremiumSectionBanner(
            title = "Daily Reminders & Tips — Premium",
            description = "Daily start reminders, goal celebrations, and motivational tips are available in Premium.",
            onUpgradeClick = onNavigateToPremium
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
