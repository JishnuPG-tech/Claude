package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ClaudeModel
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeTopAppBar(
  title: String,
  isChatActive: Boolean,
  selectedModel: ClaudeModel,
  onMenuClick: () -> Unit,
  onBackClick: (() -> Unit)? = null,
  onNewChatClick: () -> Unit,
  onModelSelectorClick: () -> Unit,
  onRenameChat: (() -> Unit)? = null,
  onDeleteChat: (() -> Unit)? = null,
  onOpenAccount: (() -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var menuExpanded by remember { mutableStateOf(false) }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .background(colors.bgMain)
      .statusBarsPadding()
      .height(56.dp)
      .padding(horizontal = 8.dp),
    contentAlignment = Alignment.CenterStart,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      // Left Navigation Icon: Menu on Home, Back arrow when in active chat
      if (isChatActive && onBackClick != null) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier.testTag("top_bar_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = colors.textPrimary,
          )
        }
      } else {
        IconButton(
          onClick = onMenuClick,
          modifier = Modifier.testTag("top_bar_menu_button")
        ) {
          Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            tint = colors.textPrimary,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Spacer(modifier = Modifier.width(4.dp))

      // Center Title (shown only when in active chat; Home keeps center empty matching Screenshot 2)
      Row(
        modifier = Modifier
          .weight(1f)
          .padding(end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        if (isChatActive) {
          Text(
            text = title,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = colors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        } else {
          Spacer(modifier = Modifier.weight(1f))
        }
      }

      // Right Action: Cute Claude Ghost Avatar Icon (from Screenshot 1)
      IconButton(
        onClick = { if (onRenameChat != null || onDeleteChat != null) menuExpanded = true else onOpenAccount?.invoke() },
        modifier = Modifier
          .padding(end = 4.dp)
          .testTag("top_bar_ghost_button")
      ) {
        ClaudeGhostIcon(
          tint = colors.textPrimary,
          size = 24.dp
        )
      }

      // Overflow menu if in chat
      if (isChatActive && (onRenameChat != null || onDeleteChat != null)) {
        Box {
          DropdownMenu(
            expanded = menuExpanded,
            onDismissRequest = { menuExpanded = false },
            modifier = Modifier.background(colors.surface)
          ) {
            if (onRenameChat != null) {
              DropdownMenuItem(
                text = { Text("Rename Chat", color = colors.textPrimary) },
                onClick = {
                  menuExpanded = false
                  onRenameChat()
                }
              )
            }
            if (onDeleteChat != null) {
              DropdownMenuItem(
                text = { Text("Delete Chat", color = colors.error) },
                onClick = {
                  menuExpanded = false
                  onDeleteChat()
                }
              )
            }
          }
        }
      }
    }
  }
}
