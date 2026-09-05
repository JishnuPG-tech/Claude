package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ArtifactData
import com.example.domain.model.Message
import com.example.domain.model.MessageRole
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeMessageItem(
  message: Message,
  onCopyText: (String) -> Unit,
  onOpenArtifact: (ArtifactData) -> Unit,
  onRegenerate: (() -> Unit)? = null,
  onApproveTool: ((String) -> Unit)? = null,
  onRejectTool: ((String) -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  if (message.role == MessageRole.USER) {
    // User Message (Right Aligned Capsule)
    Box(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      contentAlignment = Alignment.CenterEnd
    ) {
      Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.widthIn(max = 320.dp)
      ) {
        // Attachment badges if present
        if (message.attachments.isNotEmpty()) {
          Row(
            modifier = Modifier.padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            message.attachments.forEach { att ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(8.dp))
                  .background(colors.surfaceContainer)
                  .padding(horizontal = 8.dp, vertical = 4.dp)
              ) {
                Text(
                  text = att.name,
                  fontSize = 11.sp,
                  color = colors.textSecondary
                )
              }
            }
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .background(colors.surfaceSecondary)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("user_message_${message.id}")
        ) {
          Text(
            text = message.content,
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            color = colors.textPrimary,
          )
        }
      }
    }
  } else {
    // Assistant Message (Full Width Literary Prose)
    var copied by remember { mutableStateOf(false) }
    var thumbsUp by remember { mutableStateOf(false) }
    var thumbsDown by remember { mutableStateOf(false) }

    val cursorAnim = rememberInfiniteTransition(label = "cursorBlink")
    val cursorAlpha by cursorAnim.animateFloat(
      initialValue = 0.1f,
      targetValue = 1.0f,
      animationSpec = infiniteRepeatable(
        animation = tween(500, easing = LinearEasing),
        repeatMode = RepeatMode.Reverse
      ),
      label = "cursorAlpha"
    )

    Column(
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .testTag("assistant_message_${message.id}")
    ) {
      // Thinking Block if present
      if (message.thinking != null) {
        ClaudeThinkingBlock(
          thinking = message.thinking,
          modifier = Modifier.padding(bottom = 10.dp)
        )
      }

      // Tool Calls if present
      if (message.toolCalls.isNotEmpty()) {
        Column(
          modifier = Modifier.padding(bottom = 10.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          message.toolCalls.forEach { toolCall ->
            ClaudeToolCard(
              toolCall = toolCall,
              onApprove = if (onApproveTool != null) { { onApproveTool(toolCall.id) } } else null,
              onReject = if (onRejectTool != null) { { onRejectTool(toolCall.id) } } else null,
            )
          }
        }
      }

      // Main Text Content with Markdown Formatting
      ClaudeMarkdownRenderer(
        content = message.content,
        isStreaming = message.isStreaming,
        cursorAlpha = cursorAlpha
      )

      // Artifacts Cards if present
      if (message.artifacts.isNotEmpty()) {
        Column(
          modifier = Modifier.padding(top = 10.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          message.artifacts.forEach { artifact ->
            ClaudeArtifactCard(
              artifact = artifact,
              onOpenArtifact = onOpenArtifact,
              onCopyCode = onCopyText
            )
          }
        }
      }

      // Action Row (when response generation is finished)
      if (!message.isStreaming && message.content.isNotEmpty()) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.testTag("message_actions_row")
        ) {
          // Copy Button
          IconButton(
            onClick = {
              onCopyText(message.content)
              copied = true
            },
            modifier = Modifier.size(30.dp)
          ) {
            Icon(
              imageVector = if (copied) Icons.Default.Check else Icons.Default.ContentCopy,
              contentDescription = "Copy message",
              tint = if (copied) colors.success else colors.textSecondary,
              modifier = Modifier.size(16.dp)
            )
          }

          // Thumbs Up
          IconButton(
            onClick = {
              thumbsUp = !thumbsUp
              if (thumbsUp) thumbsDown = false
            },
            modifier = Modifier.size(30.dp)
          ) {
            Icon(
              imageVector = if (thumbsUp) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
              contentDescription = "Good response",
              tint = if (thumbsUp) ClaudeTerracotta else colors.textSecondary,
              modifier = Modifier.size(16.dp)
            )
          }

          // Thumbs Down
          IconButton(
            onClick = {
              thumbsDown = !thumbsDown
              if (thumbsDown) thumbsUp = false
            },
            modifier = Modifier.size(30.dp)
          ) {
            Icon(
              imageVector = if (thumbsDown) Icons.Filled.ThumbDown else Icons.Outlined.ThumbDown,
              contentDescription = "Poor response",
              tint = if (thumbsDown) colors.error else colors.textSecondary,
              modifier = Modifier.size(16.dp)
            )
          }

          // Regenerate
          if (onRegenerate != null) {
            IconButton(
              onClick = onRegenerate,
              modifier = Modifier.size(30.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Regenerate response",
                tint = colors.textSecondary,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ClaudeMarkdownRenderer(
  content: String,
  isStreaming: Boolean,
  cursorAlpha: Float,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  // Split lines to detect headings, code blocks, lists
  val lines = content.split("\n")
  var inCodeBlock = false
  val codeBuffer = StringBuilder()
  var codeLanguage = ""

  Column(modifier = modifier.fillMaxWidth()) {
    var i = 0
    while (i < lines.size) {
      val line = lines[i]

      if (line.startsWith("```")) {
        if (inCodeBlock) {
          // End of code block
          val codeText = codeBuffer.toString().trimEnd()
          CodeBlockView(language = codeLanguage, code = codeText)
          codeBuffer.clear()
          inCodeBlock = false
        } else {
          // Start of code block
          inCodeBlock = true
          codeLanguage = line.removePrefix("```").trim().ifEmpty { "code" }
        }
      } else if (inCodeBlock) {
        codeBuffer.append(line).append("\n")
      } else {
        when {
          line.startsWith("### ") -> {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = line.removePrefix("### "),
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp,
              color = colors.textPrimary,
              lineHeight = 23.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
          }
          line.startsWith("## ") -> {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = line.removePrefix("## "),
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 19.sp,
              color = colors.textPrimary,
              lineHeight = 25.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
          }
          line.startsWith("# ") -> {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
              text = line.removePrefix("# "),
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.Bold,
              fontSize = 22.sp,
              color = colors.textPrimary,
              lineHeight = 28.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
          }
          line.startsWith("- ") || line.startsWith("* ") -> {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp, horizontal = 4.dp)
            ) {
              Text(
                text = "•",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ClaudeTerracotta,
                modifier = Modifier.padding(end = 8.dp)
              )
              Text(
                text = cleanMarkdownInline(line.substring(2)),
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                color = colors.textPrimary,
                modifier = Modifier.weight(1f)
              )
            }
          }
          line.isNotBlank() -> {
            Text(
              text = cleanMarkdownInline(line),
              fontFamily = FontFamily.Serif,
              fontSize = 16.sp,
              lineHeight = 24.sp,
              color = colors.textPrimary,
              modifier = Modifier.padding(vertical = 4.dp)
            )
          }
        }
      }
      i++
    }

    // If still in code block at end of streaming
    if (inCodeBlock && codeBuffer.isNotEmpty()) {
      CodeBlockView(language = codeLanguage, code = codeBuffer.toString().trimEnd())
    }

    // Blinking streaming cursor
    if (isStreaming) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(width = 8.dp, height = 18.dp)
            .alpha(cursorAlpha)
            .background(ClaudeTerracotta)
        )
      }
    }
  }
}

@Composable
private fun CodeBlockView(
  language: String,
  code: String,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(colors.codeBg)
      .border(1.dp, colors.borderSubtle, RoundedCornerShape(8.dp))
  ) {
    // Code header with language and copy
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(colors.codeHeader)
        .padding(horizontal = 12.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = language.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = colors.textSecondary
      )
    }

    // Code lines
    Text(
      text = code,
      fontFamily = FontFamily.Monospace,
      fontSize = 13.sp,
      lineHeight = 19.sp,
      color = colors.textPrimary,
      modifier = Modifier.padding(12.dp)
    )
  }
}

private fun cleanMarkdownInline(raw: String): String {
  // Strip bold and italic marks for clean serif display
  return raw
    .replace("**", "")
    .replace("__", "")
    .replace("`", "")
}
