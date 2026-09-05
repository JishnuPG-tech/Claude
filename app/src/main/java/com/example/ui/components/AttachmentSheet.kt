package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Attachment
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachmentSheet(
  onAttachmentAdded: (Attachment) -> Unit,
  onDismiss: () -> Unit,
) {
  val colors = LocalClaudeColors.current
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = colors.surface,
    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    modifier = Modifier.testTag("attachment_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 8.dp)
        .padding(bottom = 32.dp)
    ) {
      Text(
        text = "Add to Chat",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = colors.textPrimary,
      )
      Spacer(modifier = Modifier.height(16.dp))

      AttachmentOptionRow(
        icon = Icons.Default.CameraAlt,
        title = "Take photo",
        subtitle = "Capture an image with your camera",
        onClick = {
          onAttachmentAdded(
            Attachment(
              id = UUID.randomUUID().toString(),
              name = "camera_capture_${System.currentTimeMillis() % 1000}.jpg",
              sizeBytes = 1024 * 512,
              mimeType = "image/jpeg"
            )
          )
          onDismiss()
        }
      )

      Spacer(modifier = Modifier.height(8.dp))

      AttachmentOptionRow(
        icon = Icons.Default.AddPhotoAlternate,
        title = "Choose photo",
        subtitle = "Upload an image from your gallery",
        onClick = {
          onAttachmentAdded(
            Attachment(
              id = UUID.randomUUID().toString(),
              name = "screenshot_analysis.png",
              sizeBytes = 1024 * 768,
              mimeType = "image/png"
            )
          )
          onDismiss()
        }
      )

      Spacer(modifier = Modifier.height(8.dp))

      AttachmentOptionRow(
        icon = Icons.Default.Description,
        title = "Upload document",
        subtitle = "PDF, Markdown, text, or code file",
        onClick = {
          onAttachmentAdded(
            Attachment(
              id = UUID.randomUUID().toString(),
              name = "architecture_spec.pdf",
              sizeBytes = 1024 * 1024 * 2,
              mimeType = "application/pdf"
            )
          )
          onDismiss()
        }
      )
    }
  }
}

@Composable
private fun AttachmentOptionRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  title: String,
  subtitle: String,
  onClick: () -> Unit,
) {
  val colors = LocalClaudeColors.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box(
      modifier = Modifier
        .size(44.dp)
        .clip(CircleShape)
        .background(colors.surfaceSecondary),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = ClaudeTerracotta,
        modifier = Modifier.size(22.dp)
      )
    }

    Spacer(modifier = Modifier.width(14.dp))

    Column {
      Text(
        text = title,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = colors.textPrimary
      )
      Text(
        text = subtitle,
        fontSize = 12.sp,
        color = colors.textSecondary
      )
    }
  }
}
