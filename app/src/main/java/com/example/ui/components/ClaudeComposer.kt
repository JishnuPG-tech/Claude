package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Attachment
import com.example.domain.model.ClaudeModel
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeComposer(
  text: String,
  onTextChanged: (String) -> Unit,
  onSendClick: () -> Unit,
  onStopClick: () -> Unit,
  isStreaming: Boolean,
  selectedModel: ClaudeModel,
  onModelSelectorClick: () -> Unit,
  onAttachmentClick: () -> Unit,
  onVoiceClick: () -> Unit,
  attachments: List<Attachment> = emptyList(),
  onRemoveAttachment: ((Attachment) -> Unit)? = null,
  placeholderText: String = "Chat with Claude...",
  onUpgradeClick: (() -> Unit)? = null,
  showUpgradeBanner: Boolean = true,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  val hasText = text.isNotBlank() || attachments.isNotEmpty()

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(colors.bgMain)
      .navigationBarsPadding()
      .imePadding()
      .padding(horizontal = 16.dp, vertical = 8.dp)
  ) {
    // Floating Pill Container (Exact from Screenshot 1)
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(26.dp))
        .background(Color(0xFF1E1D1C))
        .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {

        // Top Banner Strip: "Get more with Claude Pro" | "Upgrade to Pro"
        if (showUpgradeBanner) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(Color(0xFF282725))
              .clickable { onUpgradeClick?.invoke() }
              .padding(horizontal = 14.dp, vertical = 9.dp)
              .testTag("composer_upgrade_strip"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "Get more with Claude Pro",
              fontSize = 13.5.sp,
              fontFamily = FontFamily.SansSerif,
              color = Color(0xFF9E9C96)
            )

            Text(
              text = "Upgrade to Pro",
              fontSize = 13.5.sp,
              fontWeight = FontWeight.SemiBold,
              fontFamily = FontFamily.SansSerif,
              color = Color(0xFFA78BFA) // Exact lavender from Screenshot 1
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
        }

        // Optional: Attached files preview horizontal list
        if (attachments.isNotEmpty()) {
          LazyRow(
            modifier = Modifier
              .fillMaxWidth()
              .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(attachments) { item ->
              Row(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color(0xFF2A2928))
                  .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Text(
                  text = item.name,
                  fontSize = 12.sp,
                  color = colors.textPrimary,
                  maxLines = 1
                )
                if (onRemoveAttachment != null) {
                  Spacer(modifier = Modifier.width(6.dp))
                  Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove attachment",
                    tint = colors.textSecondary,
                    modifier = Modifier
                      .size(14.dp)
                      .clickable { onRemoveAttachment(item) }
                  )
                }
              }
            }
          }
        }

        // Multi-line Text Input Field with "Chat with Claude..."
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 36.dp, max = 130.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp),
          contentAlignment = Alignment.CenterStart
        ) {
          if (text.isEmpty()) {
            Text(
              text = placeholderText,
              style = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontSize = 16.5.sp,
                color = Color(0xFF6E6C68),
              )
            )
          }

          BasicTextField(
            value = text,
            onValueChange = onTextChanged,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("composer_text_field"),
            textStyle = TextStyle(
              fontFamily = FontFamily.SansSerif,
              fontSize = 16.5.sp,
              color = colors.textPrimary,
              lineHeight = 22.sp
            ),
            cursorBrush = SolidColor(Color(0xFFD97757)),
            maxLines = 6,
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Bottom Controls Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          // Left Controls: Plus (+) Circle Button & "Sonnet 5 Low" Pill
          Row(verticalAlignment = Alignment.CenterVertically) {
            // Circular (+) Button
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2928))
                .clickable { onAttachmentClick() }
                .testTag("composer_attachment_button"),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Attachment",
                tint = Color(0xFFC8C6C0),
                modifier = Modifier.size(20.dp)
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Model Selection Pill: "Sonnet 5 Low"
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(19.dp))
                .background(Color(0xFF2A2928))
                .clickable { onModelSelectorClick() }
                .padding(horizontal = 14.dp, vertical = 9.dp)
                .testTag("composer_model_pill"),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = selectedModel.shortName,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF5F5F0),
              )
              Spacer(modifier = Modifier.width(5.dp))
              Text(
                text = selectedModel.effortLevel,
                fontSize = 13.5.sp,
                color = Color(0xFF8A8884),
              )
            }
          }

          // Right Controls: Mic Circle & White Speech Waveform Action Button
          Row(verticalAlignment = Alignment.CenterVertically) {
            // Circular Mic Button
            Box(
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A2928))
                .clickable { onVoiceClick() }
                .testTag("composer_mic_button"),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = "Dictation",
                tint = Color(0xFFC8C6C0),
                modifier = Modifier.size(20.dp)
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Circular White Audio Waveform Button (Exact from Screenshot 1)
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable {
                  when {
                    isStreaming -> onStopClick()
                    hasText -> onSendClick()
                    else -> onVoiceClick()
                  }
                }
                .testTag("composer_action_button"),
              contentAlignment = Alignment.Center
            ) {
              when {
                isStreaming -> {
                  // Stop square
                  Box(
                    modifier = Modifier
                      .size(12.dp)
                      .clip(RoundedCornerShape(2.dp))
                      .background(Color.Black)
                  )
                }
                hasText -> {
                  // Send upward arrow in black
                  Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = "Send Message",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                  )
                }
                else -> {
                  // Pure black 5-bar vertical speech waveform (Screenshot 1)
                  ClaudeAudioWaveformIcon(
                    tint = Color.Black,
                    size = 20.dp
                  )
                }
              }
            }
          }
        }
      }
    }
  }
}
