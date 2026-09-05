package com.example.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun VoiceScreen(
  voiceName: String,
  onClose: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var isMuted by remember { mutableStateOf(false) }
  var isPaused by remember { mutableStateOf(false) }

  // Multi-layered pulsing orb animation
  val infiniteTransition = rememberInfiniteTransition(label = "orbPulse")
  val pulseScale1 by infiniteTransition.animateFloat(
    initialValue = 0.85f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "scale1"
  )
  val pulseScale2 by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = 1.35f,
    animationSpec = infiniteRepeatable(
      animation = tween(1900, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "scale2"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(if (colors.isDark) Color(0xFF141312) else Color(0xFFFAF9F5))
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(24.dp)
      .testTag("voice_screen"),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    // Top Bar: Voice Profile Tag
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "Claude Voice",
          fontFamily = FontFamily.Serif,
          fontWeight = FontWeight.SemiBold,
          fontSize = 18.sp,
          color = colors.textPrimary
        )
        Text(
          text = "$voiceName Voice",
          fontSize = 12.sp,
          color = colors.textSecondary
        )
      }
    }

    // Center: Animated Terracotta Ambient Audio Orb
    Box(
      modifier = Modifier.size(240.dp),
      contentAlignment = Alignment.Center
    ) {
      // Outer ripple
      Box(
        modifier = Modifier
          .size(200.dp)
          .scale(if (isPaused) 1.0f else pulseScale2)
          .clip(CircleShape)
          .background(ClaudeTerracotta.copy(alpha = 0.12f))
      )
      // Middle ripple
      Box(
        modifier = Modifier
          .size(150.dp)
          .scale(if (isPaused) 1.0f else pulseScale1)
          .clip(CircleShape)
          .background(ClaudeTerracotta.copy(alpha = 0.25f))
      )
      // Core glowing circle
      Box(
        modifier = Modifier
          .size(100.dp)
          .clip(CircleShape)
          .background(ClaudeTerracotta),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "*",
          color = Color.White,
          fontWeight = FontWeight.Black,
          fontSize = 64.sp,
          modifier = Modifier.padding(bottom = 8.dp)
        )
      }
    }

    // Live Transcript Ticker
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = if (isPaused) "Session paused" else if (isMuted) "Microphone muted" else "Listening...",
        fontFamily = FontFamily.Serif,
        fontSize = 16.sp,
        color = colors.textSecondary,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Speak naturally with Claude",
        fontSize = 13.sp,
        color = colors.textTertiary,
        textAlign = TextAlign.Center
      )
    }

    // Bottom Action Controls
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Mute Toggle
      IconButton(
        onClick = { isMuted = !isMuted },
        modifier = Modifier
          .size(56.dp)
          .clip(CircleShape)
          .background(colors.surfaceSecondary)
      ) {
        Icon(
          imageVector = if (isMuted) Icons.Default.MicOff else Icons.Default.Mic,
          contentDescription = "Toggle Mute",
          tint = if (isMuted) colors.error else colors.textPrimary,
          modifier = Modifier.size(24.dp)
        )
      }

      // End Session (Red Circle)
      IconButton(
        onClick = onClose,
        modifier = Modifier
          .size(68.dp)
          .clip(CircleShape)
          .background(colors.error)
          .testTag("voice_end_session_button")
      ) {
        Icon(
          imageVector = Icons.Default.CallEnd,
          contentDescription = "End Call",
          tint = Color.White,
          modifier = Modifier.size(30.dp)
        )
      }

      // Pause/Resume
      IconButton(
        onClick = { isPaused = !isPaused },
        modifier = Modifier
          .size(56.dp)
          .clip(CircleShape)
          .background(colors.surfaceSecondary)
      ) {
        Icon(
          imageVector = if (isPaused) Icons.Default.PlayArrow else Icons.Default.Pause,
          contentDescription = "Pause",
          tint = colors.textPrimary,
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}
