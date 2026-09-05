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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.AvailableClaudeModels
import com.example.domain.model.ClaudeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModelSelectorSheet(
  selectedModel: ClaudeModel,
  thinkingEnabled: Boolean,
  thinkingBudgetTokens: Int,
  onModelSelected: (ClaudeModel) -> Unit,
  onThinkingToggle: (Boolean) -> Unit,
  onBudgetChange: (Int) -> Unit,
  onDismiss: () -> Unit,
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  val sheetBg = Color(0xFF141413)
  val cardBg = Color(0xFF1E1D1C)
  val textPrimary = Color(0xFFEDEBE6)
  val textSecondary = Color(0xFF9E9C96)
  val activeBlue = Color(0xFF60A5FA)
  val checkmarkBlue = Color(0xFF3B82F6)
  val badgeBg = Color(0xFF1E2838)
  val badgeText = Color(0xFF70A5F8)
  val dividerColor = Color(0xFF282725)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = sheetBg,
    dragHandle = {
      Box(
        modifier = Modifier
          .padding(top = 10.dp, bottom = 4.dp)
          .size(width = 38.dp, height = 4.dp)
          .clip(RoundedCornerShape(2.dp))
          .background(Color(0xFF454340))
      )
    },
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    modifier = Modifier.testTag("model_selector_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .padding(bottom = 36.dp)
    ) {
      // Header: Close icon on left, "Select model" centered
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp)
      ) {
        IconButton(
          onClick = onDismiss,
          modifier = Modifier
            .align(Alignment.CenterStart)
            .size(32.dp)
            .testTag("model_selector_close")
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = textPrimary,
            modifier = Modifier.size(20.dp)
          )
        }

        Text(
          text = "Select model",
          fontSize = 18.sp,
          fontWeight = FontWeight.SemiBold,
          color = textPrimary,
          modifier = Modifier.align(Alignment.Center)
        )
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Model List in Rounded Card Container (Exact from Screenshot 2)
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(cardBg)
      ) {
        Column(modifier = Modifier.fillMaxWidth()) {
          AvailableClaudeModels.forEachIndexed { index, model ->
            val isSelected = model.id == selectedModel.id

            Row(
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  onModelSelected(model)
                  onDismiss()
                }
                .padding(horizontal = 18.dp, vertical = 14.dp)
                .testTag("model_option_${model.shortName.lowercase().replace(" ", "_")}"),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = model.displayName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) activeBlue else textPrimary
                  )

                  if (model.tierBadge != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(badgeBg)
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                      Text(
                        text = model.tierBadge,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = badgeText
                      )
                    }
                  }
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                  text = model.description,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Normal,
                  color = if (isSelected) activeBlue else textSecondary
                )
              }

              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = checkmarkBlue,
                  modifier = Modifier.size(22.dp)
                )
              }
            }

            if (index < AvailableClaudeModels.size - 1) {
              HorizontalDivider(
                modifier = Modifier.padding(start = 18.dp),
                thickness = 0.5.dp,
                color = dividerColor
              )
            }
          }
        }
      }
    }
  }
}
