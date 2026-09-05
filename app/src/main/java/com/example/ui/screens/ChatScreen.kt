package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ArtifactData
import com.example.domain.model.Message
import com.example.ui.components.ClaudeMessageItem
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ChatScreen(
  messages: List<Message>,
  isStreaming: Boolean,
  onCopyText: (String) -> Unit,
  onOpenArtifact: (ArtifactData) -> Unit,
  onRegenerate: (() -> Unit)? = null,
  onApproveTool: ((String) -> Unit)? = null,
  onRejectTool: ((String) -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  val listState = rememberLazyListState()

  // Autoscroll to bottom when streaming tokens arrive
  LaunchedEffect(messages.lastOrNull()?.content, messages.lastOrNull()?.thinking?.content, messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(colors.bgMain)
  ) {
    if (messages.isEmpty()) {
      // Empty Chat State: Centered 14-spoke Sunburst + "Let's noodle" (Exact from Screenshot 1)
      androidx.compose.foundation.layout.Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 120.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
      ) {
        com.example.ui.components.ClaudeSunburst(
          size = 64.dp,
          color = com.example.ui.theme.ClaudeTerracotta
        )

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(top = 20.dp))

        androidx.compose.material3.Text(
          text = "Let's noodle",
          fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
          fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
          fontSize = 32.sp,
          color = colors.textPrimary,
          textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
      }
    } else {
      LazyColumn(
        state = listState,
        modifier = Modifier
          .fillMaxSize()
          .padding(bottom = 12.dp)
          .testTag("chat_messages_list")
      ) {
        items(
          items = messages,
          key = { it.id }
        ) { msg ->
          ClaudeMessageItem(
            message = msg,
            onCopyText = onCopyText,
            onOpenArtifact = onOpenArtifact,
            onRegenerate = onRegenerate,
            onApproveTool = onApproveTool,
            onRejectTool = onRejectTool,
          )
        }
      }
    }
  }
}
