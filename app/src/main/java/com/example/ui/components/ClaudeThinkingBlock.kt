package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ThinkingData
import com.example.domain.model.ThinkingStatus
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeThinkingBlock(
  thinking: ThinkingData,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var expanded by remember { mutableStateOf(thinking.isExpanded) }

  // Infinite shimmer pulse for when thinking is in progress
  val infiniteTransition = rememberInfiniteTransition(label = "thinkingPulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.45f,
    targetValue = 1.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(800, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "pulseAlpha"
  )

  val isThinking = thinking.status == ThinkingStatus.THINKING

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(colors.surfaceContainer)
      .border(1.dp, colors.borderSubtle, RoundedCornerShape(10.dp))
      .clickable { expanded = !expanded }
      .padding(horizontal = 12.dp, vertical = 8.dp)
      .testTag("thinking_block")
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Header Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = null,
          tint = if (isThinking) ClaudeTerracotta else colors.textSecondary,
          modifier = Modifier
            .size(16.dp)
            .alpha(if (isThinking) pulseAlpha else 1.0f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
          text = if (isThinking) {
            "Thinking..."
          } else {
            "Thought for ${thinking.durationSeconds}s"
          },
          fontSize = 13.sp,
          fontFamily = FontFamily.SansSerif,
          color = colors.textSecondary,
          modifier = Modifier.weight(1f)
        )

        Icon(
          imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = if (expanded) "Collapse thinking" else "Expand thinking",
          tint = colors.textSecondary,
          modifier = Modifier.size(18.dp)
        )
      }

      // Expandable Body
      AnimatedVisibility(
        visible = expanded,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 4.dp)
        ) {
          Text(
            text = thinking.content.ifEmpty { "Evaluating prompt context and formulating structured response..." },
            fontSize = 13.sp,
            fontFamily = FontFamily.SansSerif,
            lineHeight = 19.sp,
            color = colors.textSecondary,
          )
        }
      }
    }
  }
}
