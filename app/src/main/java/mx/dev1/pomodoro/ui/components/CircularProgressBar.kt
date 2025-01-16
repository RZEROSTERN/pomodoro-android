package mx.dev1.pomodoro.ui.components

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue: Int = 0,
    maxValue: Int = 100,
    circleRadius: Float,
    sessions: String,
    onPositionChange: (Int) -> Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableIntStateOf(initialValue)
    }


        Canvas(
            modifier = modifier
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 30f
            circleCenter = Offset(x = width / 2f, y = height / 2f)
            val outerRadius = circleRadius + circleThickness / 2f
            val gap = 16f

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )

            drawCircle(
                style = Stroke(
                    width = circleThickness,
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f / maxValue) * positionValue.toFloat(),
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

            for(i in 0 .. (maxValue - minValue)) {
                val color = if(i < positionValue - minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i * 360 / (maxValue - minValue).toFloat()
                val angleInRadians = angleInDegrees * (Math.PI / 180f) + Math.PI / 2f

                val yGapAdjustment = cos(angleInDegrees * Math.PI / 180f) * gap
                val xGapAdjustment = -sin(angleInDegrees * Math.PI / 180f) * gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRadians) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRadians) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRadians) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRadians) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ) {
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx()
                    )
                }
            }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$sessions",
                        circleCenter.x,
                        circleCenter.y - 80.dp.toPx() / 3f,
                        Paint().apply {
                            textSize = 13.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.DarkGray.toArgb()
                            isFakeBoldText = true
                        }
                    )

                    drawText(
                        "24:59",
                        circleCenter.x,
                        circleCenter.y + 64.dp.toPx() / 3f,
                        Paint().apply {
                            textSize = 42.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.DarkGray.toArgb()
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
    CircularProgressBar(
        modifier = Modifier
            .size(250.dp)
            .background(Color.White),
        initialValue = 100,
        primaryColor = Color.Green,
        secondaryColor = Color.Gray,
        circleRadius = 230f,
        onPositionChange = {},
        sessions = "1 of 6 sessions"
    )
}