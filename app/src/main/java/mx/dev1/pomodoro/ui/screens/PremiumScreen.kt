package mx.dev1.pomodoro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme

private enum class PricingPlan { Monthly, Annual, Lifetime }

private data class PremiumFeature(val icon: ImageVector, val title: String, val description: String)

private val premiumFeatures = listOf(
    PremiumFeature(Icons.Default.Task,               "Unlimited Tasks",        "No cap on active tasks — manage everything in one place"),
    PremiumFeature(Icons.Default.History,            "Full History",           "Access your complete session history, not just the last 7 days"),
    PremiumFeature(Icons.Default.CalendarMonth,      "Calendar View",          "Plan ahead and visualize your focus sessions by date"),
    PremiumFeature(Icons.Default.BarChart,           "My Tracker & Stats",     "Deep analytics on your productivity patterns over time"),
    PremiumFeature(Icons.Default.Label,              "Tags & Categories",      "Organize tasks by project, subject, or any label you define"),
    PremiumFeature(Icons.Default.NotificationsActive,"Advanced Notifications", "Daily reminders, goal alerts, and motivational tips"),
    PremiumFeature(Icons.Default.FileDownload,       "Export & Import Data",   "Backup and restore your data in CSV or JSON format"),
    PremiumFeature(Icons.Default.CloudSync,          "Cloud Sync",             "Keep all your devices in sync automatically"),
)

@Composable
fun PremiumScreen(
    onNavigateBack: () -> Unit = {}
) {
    var selectedPlan by rememberSaveable { mutableStateOf(PricingPlan.Annual) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(vertical = 36.dp, horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(56.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Go Premium",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Unlock your full productivity potential",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Features list
            premiumFeatures.forEach { feature ->
                PremiumFeatureRow(feature)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Pricing plans
            Text(
                text = "Choose your plan",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            PricingCard(
                plan = PricingPlan.Monthly,
                title = "Monthly",
                price = "$4.99",
                period = "/ month",
                badge = null,
                selected = selectedPlan == PricingPlan.Monthly,
                onClick = { selectedPlan = PricingPlan.Monthly }
            )
            PricingCard(
                plan = PricingPlan.Annual,
                title = "Annual",
                price = "$29.99",
                period = "/ year",
                badge = "Save 50%",
                selected = selectedPlan == PricingPlan.Annual,
                onClick = { selectedPlan = PricingPlan.Annual }
            )
            PricingCard(
                plan = PricingPlan.Lifetime,
                title = "Lifetime",
                price = "$49.99",
                period = "one-time",
                badge = "Best deal",
                selected = selectedPlan == PricingPlan.Lifetime,
                onClick = { selectedPlan = PricingPlan.Lifetime }
            )

            Spacer(modifier = Modifier.height(4.dp))

            // CTA
            Button(
                onClick = { /* TODO: start purchase flow */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = when (selectedPlan) {
                        PricingPlan.Monthly  -> "Start 7-day free trial"
                        PricingPlan.Annual   -> "Start 7-day free trial"
                        PricingPlan.Lifetime -> "Get Lifetime Access"
                    },
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Text(
                text = when (selectedPlan) {
                    PricingPlan.Monthly  -> "Then $4.99/month. Cancel anytime."
                    PricingPlan.Annual   -> "Then $29.99/year. Cancel anytime."
                    PricingPlan.Lifetime -> "Pay once. Yours forever."
                },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            TextButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Maybe later", color = MaterialTheme.colorScheme.outline)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PremiumFeatureRow(feature: PremiumFeature) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = feature.title, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
            Text(text = feature.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Composable
private fun PricingCard(
    plan: PricingPlan,
    title: String,
    price: String,
    period: String,
    badge: String?,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceContainerLow

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (selected) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    if (badge != null) {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(text = badge, fontSize = 10.sp, color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                if (plan == PricingPlan.Annual) {
                    Text(
                        text = "$4.99/mo billed annually",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(text = price, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = period,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PremiumScreenPreview() {
    PomodoroTheme { PremiumScreen() }
}

@Preview(showBackground = true)
@Composable
private fun PremiumScreenDarkPreview() {
    PomodoroTheme(darkTheme = true) { PremiumScreen() }
}
