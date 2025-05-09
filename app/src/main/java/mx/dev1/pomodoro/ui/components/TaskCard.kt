package mx.dev1.pomodoro.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TaskCard(
    title: String,
    completedSessions: Int,
    totalSessions: Int,
    totalTime: String,
    sessionTime: String,
    icon: ImageVector
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF8AB4F8), // Example color
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFFE3F2FD),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = totalTime,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$completedSessions/$totalSessions",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = sessionTime,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskCard() {
    TaskCard(
        title = "Mobile app design",
        completedSessions = 4,
        totalSessions = 6,
        totalTime = "150 minutes",
        sessionTime = "25 minutes",
        icon = Icons.Default.Home
    )
}