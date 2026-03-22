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
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.PremiumGate
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

@Composable
fun ExportDataScreen(onNavigateToPremium: () -> Unit = {}) {
    PremiumGate(
        title = "Export Data",
        description = "Back up your full history and tasks to CSV or JSON. Available in Premium.",
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
            text = "Export your Pomodoro history and tasks to a file you can open in any spreadsheet or app.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ExportOptionCard(
            icon = { Icon(Icons.Default.GridOn, contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.primary) },
            title = "CSV",
            description = "Compatible with Excel, Google Sheets, and Numbers. Best for viewing your data in a table.",
            onExport = { /* TODO */ }
        )

        ExportOptionCard(
            icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(28.dp), tint = MaterialTheme.colorScheme.primary) },
            title = "JSON",
            description = "Full structured export. Best for importing into other apps or creating backups.",
            onExport = { /* TODO */ }
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.History,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "What is included",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "All completed sessions, task names, durations, tags, and dates from your full history.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
    }
}

@Composable
private fun ExportOptionCard(
    icon: @Composable () -> Unit,
    title: String,
    description: String,
    onExport: () -> Unit
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
                onClick = onExport,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(text = "  Export $title")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExportDataScreenPreview() {
    PomodoroTheme { ExportDataScreen() }
}

@Preview(showBackground = true)
@Composable
private fun ExportDataScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { ExportDataScreen() }
}
