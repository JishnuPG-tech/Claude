package com.example.ui.components

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
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ArtifactData
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeArtifactCard(
  artifact: ArtifactData,
  onOpenArtifact: (ArtifactData) -> Unit,
  onCopyCode: ((String) -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(colors.surface)
      .border(1.dp, colors.borderSubtle, RoundedCornerShape(12.dp))
      .clickable { onOpenArtifact(artifact) }
      .padding(12.dp)
      .testTag("artifact_card_${artifact.id}")
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ClaudeTerracotta.copy(alpha = 0.12f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Code,
            contentDescription = null,
            tint = ClaudeTerracotta,
            modifier = Modifier.size(20.dp)
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
          Text(
            text = artifact.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textPrimary,
            fontFamily = FontFamily.SansSerif
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = artifact.language.uppercase(),
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = colors.textSecondary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "• Click to open",
              fontSize = 11.sp,
              color = colors.textTertiary
            )
          }
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        if (onCopyCode != null) {
          IconButton(
            onClick = { onCopyCode(artifact.code) },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.ContentCopy,
              contentDescription = "Copy artifact code",
              tint = colors.textSecondary,
              modifier = Modifier.size(16.dp)
            )
          }
        }

        Icon(
          imageVector = Icons.AutoMirrored.Filled.OpenInNew,
          contentDescription = "Open",
          tint = colors.textSecondary,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}
