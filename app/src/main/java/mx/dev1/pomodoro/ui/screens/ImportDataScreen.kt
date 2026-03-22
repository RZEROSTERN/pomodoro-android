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
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.PremiumGate
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun ImportDataScreen(onNavigateToPremium: () -> Unit = {}) {
    PremiumGate(
        title = "Import Data",
        description = "Restore your history and tasks from a backup file. Available in Premium.",
        onUpgradeClick = onNavigateToPremium
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Import previously exported Pomodoro data to restore your history and tasks.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ImportOptionCard(
            icon = { Icon(Icons.Default.GridOn, contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.primary) },
            title = "CSV",
            description = "Import a CSV file previously exported from this app or compatible Pomodoro trackers.",
            onImport = { /* TODO */ }
        )

        ImportOptionCard(
            icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.primary) },
            title = "JSON",
            description = "Import a full structured JSON backup. Restores sessions, tasks, tags, and history.",
            onImport = { /* TODO */ }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Importing will overwrite your data",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                    Text(
                        text = "All existing sessions, tasks, and history will be permanently replaced by the imported file. This action cannot be undone.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
    }
}

@Composable
private fun ImportOptionCard(
    icon: @Composable () -> Unit,
    title: String,
    description: String,
    onImport: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                icon()
                Text(text = title, style = MaterialTheme.typography.titleMedium)
            }
            Text(text = description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Button(
                onClick = onImport,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.FileUpload, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(text = "  Import $title")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImportDataScreenPreview() {
    PomodoroTheme { ImportDataScreen() }
}

@Preview(showBackground = true)
@Composable
private fun ImportDataScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { ImportDataScreen() }
}
