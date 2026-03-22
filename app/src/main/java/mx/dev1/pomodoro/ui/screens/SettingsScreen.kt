package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun SettingsScreen() {
    var focusDuration by rememberSaveable { mutableFloatStateOf(25f) }
    var shortBreak by rememberSaveable { mutableFloatStateOf(5f) }
    var longBreak by rememberSaveable { mutableFloatStateOf(15f) }
    var autoStartBreaks by rememberSaveable { mutableStateOf(false) }
    var autoStartPomodoros by rememberSaveable { mutableStateOf(false) }
    var soundEnabled by rememberSaveable { mutableStateOf(true) }
    var vibrationEnabled by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Timer section
        SettingsSectionHeader(icon = { Icon(Icons.Default.PlayCircleOutline, contentDescription = null, modifier = Modifier.size(20.dp)) }, title = "Timer")
        SettingsSliderRow(
            label = "Focus duration",
            value = focusDuration,
            valueRange = 5f..60f,
            unit = "min",
            onValueChange = { focusDuration = it }
        )
        SettingsSliderRow(
            label = "Short break",
            value = shortBreak,
            valueRange = 1f..30f,
            unit = "min",
            onValueChange = { shortBreak = it }
        )
        SettingsSliderRow(
            label = "Long break",
            value = longBreak,
            valueRange = 5f..60f,
            unit = "min",
            onValueChange = { longBreak = it }
        )

        HorizontalDivider()

        // Auto-start section
        SettingsSectionHeader(icon = { Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(20.dp)) }, title = "Behaviour")
        SettingsSwitchRow(label = "Auto-start breaks", checked = autoStartBreaks, onCheckedChange = { autoStartBreaks = it })
        SettingsSwitchRow(label = "Auto-start Pomodoros", checked = autoStartPomodoros, onCheckedChange = { autoStartPomodoros = it })

        HorizontalDivider()

        // Sound section
        SettingsSectionHeader(icon = { Icon(Icons.Default.VolumeUp, contentDescription = null, modifier = Modifier.size(20.dp)) }, title = "Sound & Haptics")
        SettingsSwitchRow(label = "Sound effects", checked = soundEnabled, onCheckedChange = { soundEnabled = it })
        SettingsSwitchRow(label = "Vibration", checked = vibrationEnabled, onCheckedChange = { vibrationEnabled = it })
    }
}

@Composable
private fun SettingsSectionHeader(icon: @Composable () -> Unit, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        icon()
        Text(text = title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun SettingsSliderRow(
    label: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    unit: String,
    onValueChange: (Float) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = "${value.toInt()} $unit",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Slider(value = value, onValueChange = onValueChange, valueRange = valueRange)
    }
}

@Composable
private fun SettingsSwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    PomodoroTheme { SettingsScreen() }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { SettingsScreen() }
}
