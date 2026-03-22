package mx.dev1.pomodoro.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.dev1.pomodoro.ui.theme.PomodoroTheme
import kotlin.math.cos
import kotlin.math.sin

private const val DOT_COUNT = 100

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,            // 0f (empty) → 1f (full)
    primaryColor: Color,
    secondaryColor: Color,
    circleRadius: Float,
    sessions: String,
    timeText: String
) {
    val labelTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val timerTextColor = MaterialTheme.colorScheme.onSurface

    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier.semantics {
            contentDescription = "Pomodoro timer. $sessions. Remaining time $timeText."
            progressBarRangeInfo = ProgressBarRangeInfo(
                current = progress,
                range = 0f..1f
            )
        }
    ) {
        val width = size.width
        val height = size.height
        val circleThickness = width / 30f
        circleCenter = Offset(x = width / 2f, y = height / 2f)
        val outerRadius = circleRadius + circleThickness / 2f
        val gap = 16f

        // Background fill
        drawCircle(
            brush = Brush.radialGradient(
                listOf(
                    primaryColor.copy(if (progress > 0f) 0.45f else 0.15f),
                    secondaryColor.copy(0.15f)
                )
            ),
            radius = circleRadius,
            center = circleCenter
        )

        // Track arc (secondary)
        drawCircle(
            style = Stroke(width = circleThickness),
            color = secondaryColor,
            radius = circleRadius,
            center = circleCenter
        )

        // Progress arc (primary)
        if (progress > 0f) {
            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f) / 2f,
                    (height - circleRadius * 2f) / 2f
                )
            )
        }

        // Tick marks
        for (i in 0..DOT_COUNT) {
            val filled = i.toFloat() / DOT_COUNT < progress
            val color = if (filled) primaryColor else primaryColor.copy(alpha = 0.3f)
            val angleInDegrees = i * 360f / DOT_COUNT
            val angleInRadians = angleInDegrees * (Math.PI / 180f) + Math.PI / 2f

            val yGapAdjustment = cos(angleInDegrees * Math.PI / 180f) * gap
            val xGapAdjustment = -sin(angleInDegrees * Math.PI / 180f) * gap

            val start = Offset(
                x = (outerRadius * cos(angleInRadians) + circleCenter.x + xGapAdjustment).toFloat(),
                y = (outerRadius * sin(angleInRadians) + circleCenter.y + yGapAdjustment).toFloat()
            )
            val end = Offset(
                x = start.x,
                y = (outerRadius * sin(angleInRadians) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
            )

            rotate(angleInDegrees, pivot = start) {
                drawLine(
                    color = color,
                    start = start,
                    end = end,
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        // Text labels
        drawContext.canvas.nativeCanvas.apply {
            drawIntoCanvas {
                drawText(
                    sessions,
                    circleCenter.x,
                    circleCenter.y - 80.dp.toPx() / 3f,
                    Paint().apply {
                        textSize = 13.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        color = labelTextColor.toArgb()
                        isFakeBoldText = true
                    }
                )
                drawText(
                    timeText,
                    circleCenter.x,
                    circleCenter.y + 64.dp.toPx() / 3f,
                    Paint().apply {
                        textSize = 42.sp.toPx()
                        textAlign = Paint.Align.CENTER
                        color = timerTextColor.toArgb()
                        isFakeBoldText = true
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun CircularProgressBarPreview() {
    PomodoroTheme {
        CircularProgressBar(
            modifier = Modifier.size(250.dp),
            progress = 0.4f,
            primaryColor = MaterialTheme.colorScheme.primary,
            secondaryColor = MaterialTheme.colorScheme.surfaceVariant,
            circleRadius = 230f,
            sessions = "1 of 6 sessions",
            timeText = "14:59"
        )
    }
}
