package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ToolCallData
import com.example.domain.model.ToolStatus
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeToolCard(
  toolCall: ToolCallData,
  onApprove: (() -> Unit)? = null,
  onReject: (() -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var expanded by remember { mutableStateOf(toolCall.isExpanded) }

  val toolIcon = when {
    toolCall.name.contains("search") || toolCall.name.contains("web") -> Icons.Default.Language
    else -> Icons.Default.Terminal
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(colors.surface)
      .border(
        width = 1.dp,
        color = if (toolCall.status == ToolStatus.APPROVAL_REQUIRED) colors.warning else colors.borderSubtle,
        shape = RoundedCornerShape(12.dp)
      )
      .padding(12.dp)
      .testTag("tool_card_${toolCall.id}")
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Top Row: Tool Name & Status
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { expanded = !expanded },
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = toolIcon,
          contentDescription = null,
          tint = if (toolCall.status == ToolStatus.APPROVAL_REQUIRED) colors.warning else ClaudeTerracotta,
          modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
          text = toolCall.name,
          fontFamily = FontFamily.Monospace,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium,
          color = colors.textPrimary,
          modifier = Modifier.weight(1f)
        )

        // Status indicator
        when (toolCall.status) {
          ToolStatus.RUNNING -> {
            CircularProgressIndicator(
              modifier = Modifier.size(14.dp),
              strokeWidth = 2.dp,
              color = ClaudeTerracotta
            )
          }
          ToolStatus.COMPLETED -> {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = "Completed",
              tint = colors.success,
              modifier = Modifier.size(16.dp)
            )
          }
          ToolStatus.ERROR -> {
            Icon(
              imageVector = Icons.Default.Error,
              contentDescription = "Error",
              tint = colors.error,
              modifier = Modifier.size(16.dp)
            )
          }
          ToolStatus.APPROVAL_REQUIRED -> {
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(colors.warning.copy(alpha = 0.15f))
                .padding(horizontal = 6.dp, vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = colors.warning,
                modifier = Modifier.size(12.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Approval Required",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = colors.warning
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
          imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
          contentDescription = "Toggle output",
          tint = colors.textSecondary,
          modifier = Modifier.size(18.dp)
        )
      }

      // If approval required: Show Action buttons
      if (toolCall.status == ToolStatus.APPROVAL_REQUIRED) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "This tool execution will affect environment state. Do you want to approve this action?",
          fontSize = 13.sp,
          color = colors.textSecondary,
          lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          if (onReject != null) {
            OutlinedButton(
              onClick = onReject,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier.testTag("tool_reject_button")
            ) {
              Text("Reject", fontSize = 12.sp, color = colors.textSecondary)
            }
          }
          Spacer(modifier = Modifier.width(8.dp))
          if (onApprove != null) {
            Button(
              onClick = onApprove,
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta),
              modifier = Modifier.testTag("tool_approve_button")
            ) {
              Text("Approve", fontSize = 12.sp, color = colors.textInverse)
            }
          }
        }
      }

      // Expandable Arguments & Output
      AnimatedVisibility(visible = expanded) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
        ) {
          Text(
            text = "INPUT",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = colors.textTertiary,
            fontFamily = FontFamily.SansSerif
          )
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(6.dp))
              .background(colors.codeBg)
              .padding(8.dp)
          ) {
            Text(
              text = toolCall.inputJson,
              fontSize = 12.sp,
              fontFamily = FontFamily.Monospace,
              color = colors.textPrimary
            )
          }

          if (!toolCall.outputJson.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "OUTPUT",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = colors.textTertiary,
              fontFamily = FontFamily.SansSerif
            )
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(colors.codeBg)
                .padding(8.dp)
            ) {
              Text(
                text = toolCall.outputJson,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                color = colors.textPrimary
              )
            }
          }
        }
      }
    }
  }
}
