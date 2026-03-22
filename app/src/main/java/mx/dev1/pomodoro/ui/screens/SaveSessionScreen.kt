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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val FREE_SESSION_LIMIT = 3

// TODO: Replace these with real auth/premium/session state from ViewModel
private val mockIsLoggedIn = false
private val mockIsPremium = false
private val mockSavedSessionCount = 0

// Simulated current session data — replace with real timer state
private data class SessionSummary(
    val taskName: String,
    val mode: String,
    val durationMinutes: Int,
    val date: String
)

private val mockSession = SessionSummary(
    taskName = "Mobile app design",
    mode = "Focus",
    durationMinutes = 25,
    date = "Today, 10:30 AM"
)

@Composable
fun SaveSessionScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToPremium: () -> Unit = {}
) {
    // TODO: Inject real values from ViewModel
    val isLoggedIn = mockIsLoggedIn
    val isPremium = mockIsPremium
    val savedSessionCount = mockSavedSessionCount
    val session = mockSession

    when {
        !isLoggedIn -> NotLoggedInState(onNavigateToLogin = onNavigateToLogin)

        isPremium -> SaveForm(
            session = session,
            limitState = null,
            onNavigateToPremium = onNavigateToPremium
        )

        savedSessionCount >= FREE_SESSION_LIMIT -> LimitReachedState(
            onNavigateToPremium = onNavigateToPremium
        )

        else -> SaveForm(
            session = session,
            limitState = Pair(savedSessionCount, FREE_SESSION_LIMIT),
            onNavigateToPremium = onNavigateToPremium
        )
    }
}

@Composable
private fun NotLoggedInState(onNavigateToLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Lock,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Login required",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "You need to be logged in to save your Pomodoro sessions.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onNavigateToLogin, modifier = Modifier.fillMaxWidth()) {
            Text("Log In")
        }
    }
}

@Composable
private fun LimitReachedState(onNavigateToPremium: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Session limit reached",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Free accounts can save up to $FREE_SESSION_LIMIT sessions. Upgrade to Premium for unlimited saves.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onNavigateToPremium,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
            Text("  Upgrade to Premium")
        }
    }
}

@Composable
private fun SaveForm(
    session: SessionSummary,
    limitState: Pair<Int, Int>?,   // Pair(used, limit), null if premium
    onNavigateToPremium: () -> Unit
) {
    var notes by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Free tier counter banner
        if (limitState != null) {
            val (used, limit) = limitState
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Free saves: $used of $limit used",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        OutlinedButton(
                            onClick = onNavigateToPremium,
                            modifier = Modifier.height(28.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                                horizontal = 10.dp, vertical = 0.dp
                            )
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                "  Go Premium",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { used.toFloat() / limit.toFloat() },
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.15f)
                    )
                }
            }
        }

        // Session summary card
        Text(
            text = "Session Summary",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SessionDetailRow(label = "Task", value = session.taskName)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SessionDetailRow(label = "Mode", value = session.mode)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SessionDetailRow(label = "Duration", value = "${session.durationMinutes} min")
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SessionDetailRow(label = "Date", value = session.date)
            }
        }

        // Notes field
        Text(
            text = "Notes",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Add notes about this session (optional)") },
            minLines = 3,
            maxLines = 5,
            leadingIcon = {
                Icon(Icons.Default.Timer, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Save button
        Button(
            onClick = { /* TODO: persist session to repository */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(18.dp))
            Text("  Save Session")
        }
    }
}

@Composable
private fun SessionDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveSessionScreenPreview() {
    SaveSessionScreen()
}
