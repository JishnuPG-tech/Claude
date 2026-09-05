package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.ClaudeTerracotta

/**
 * The official Anthropic Claude 14-spoke radiating sunburst/asterisk mark.
 */
@Composable
fun ClaudeSunburst(
  modifier: Modifier = Modifier,
  size: Dp = 56.dp,
  color: Color = ClaudeTerracotta,
  petalCount: Int = 14,
) {
  Canvas(modifier = modifier.size(size)) {
    val center = Offset(this.size.width / 2f, this.size.height / 2f)
    val minDim = this.size.minDimension
    val innerRadius = minDim * 0.13f
    val outerRadius = minDim * 0.44f
    val strokeWidth = minDim * 0.062f

    for (i in 0 until petalCount) {
      val angleDeg = i * (360f / petalCount) - 90f
      val angleRad = Math.toRadians(angleDeg.toDouble())
      val cos = Math.cos(angleRad).toFloat()
      val sin = Math.sin(angleRad).toFloat()

      val startX = center.x + innerRadius * cos
      val startY = center.y + innerRadius * sin
      val endX = center.x + outerRadius * cos
      val endY = center.y + outerRadius * sin

      drawLine(
        color = color,
        start = Offset(startX, startY),
        end = Offset(endX, endY),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
      )
    }
  }
}

/**
 * The Claude mascot/avatar ghost icon seen at the top right of the chat screen.
 */
@Composable
fun ClaudeGhostIcon(
  modifier: Modifier = Modifier,
  tint: Color = Color(0xFFF5F5F0),
  size: Dp = 24.dp,
) {
  Canvas(modifier = modifier.size(size)) {
    val strokeWidth = 1.8.dp.toPx()
    val w = this.size.width
    val h = this.size.height

    // Draw dome with wavy/scalloped bottom
    val path = Path().apply {
      moveTo(w * 0.16f, h * 0.86f)
      lineTo(w * 0.16f, h * 0.46f)
      cubicTo(
        w * 0.16f, h * 0.12f,
        w * 0.84f, h * 0.12f,
        w * 0.84f, h * 0.46f
      )
      lineTo(w * 0.84f, h * 0.86f)
      // Wavy scallops at bottom
      cubicTo(
        w * 0.72f, h * 0.76f,
        w * 0.62f, h * 0.94f,
        w * 0.50f, h * 0.85f
      )
      cubicTo(
        w * 0.38f, h * 0.76f,
        w * 0.28f, h * 0.94f,
        w * 0.16f, h * 0.86f
      )
      close()
    }

    drawPath(
      path = path,
      color = tint,
      style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )

    // Two eyes
    val eyeRadius = 1.35.dp.toPx()
    drawCircle(
      color = tint,
      radius = eyeRadius,
      center = Offset(w * 0.36f, h * 0.45f)
    )
    drawCircle(
      color = tint,
      radius = eyeRadius,
      center = Offset(w * 0.64f, h * 0.45f)
    )
  }
}

/**
 * The 5-bar vertical speech waveform icon inside the white circular button.
 */
@Composable
fun ClaudeAudioWaveformIcon(
  modifier: Modifier = Modifier,
  tint: Color = Color.Black,
  size: Dp = 20.dp,
) {
  Canvas(modifier = modifier.size(size)) {
    val w = this.size.width
    val h = this.size.height
    val barWidth = 2.4.dp.toPx()
    val heights = listOf(0.38f, 0.68f, 0.96f, 0.60f, 0.34f)
    val count = heights.size
    val totalBarWidth = count * barWidth
    val totalSpacing = w - totalBarWidth
    val spacing = totalSpacing / (count - 1).coerceAtLeast(1)

    heights.forEachIndexed { i, factor ->
      val barH = h * factor
      val x = i * (barWidth + spacing) + barWidth / 2f
      val startY = (h - barH) / 2f
      val endY = startY + barH
      drawLine(
        color = tint,
        start = Offset(x, startY),
        end = Offset(x, endY),
        strokeWidth = barWidth,
        cap = StrokeCap.Round
      )
    }
  }
}
