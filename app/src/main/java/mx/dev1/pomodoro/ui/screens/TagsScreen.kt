package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.dev1.pomodoro.ui.components.PremiumGate
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

private data class Tag(val id: String, val name: String, val color: Color)

private val defaultTags = listOf(
    Tag("1", "Work", Color(0xFF6650A4)),
    Tag("2", "Study", Color(0xFF2196F3)),
    Tag("3", "Personal", Color(0xFF4CAF50)),
    Tag("4", "Health", Color(0xFFF44336)),
    Tag("5", "Creative", Color(0xFFFF9800)),
)

@Composable
fun TagsScreen(onNavigateToPremium: () -> Unit = {}) {
    PremiumGate(
        title = "Tags & Categories",
        description = "Organize your tasks by project, subject, or any label you define. Available in Premium.",
        onUpgradeClick = onNavigateToPremium
    ) {
    var tags by rememberSaveable { mutableStateOf(defaultTags) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: open add tag dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add tag")
            }
        }
    ) { innerPadding ->
        if (tags.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        Icons.Default.Label,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text("No tags yet", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.outline)
                    Text("Tap + to create your first tag", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tags, key = { it.id }) { tag ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(tag.color, CircleShape)
                                )
                                Text(tag.name, style = MaterialTheme.typography.bodyLarge)
                            }
                            IconButton(onClick = { tags = tags.filter { it.id != tag.id } }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete tag", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
    }
}

@Preview(showBackground = true)
@Composable
private fun TagsScreenPreview() {
    PomodoroTheme { TagsScreen() }
}

@Preview(showBackground = true)
@Composable
private fun TagsScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { TagsScreen() }
}
