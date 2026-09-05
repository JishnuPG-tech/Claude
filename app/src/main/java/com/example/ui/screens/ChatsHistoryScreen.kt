package com.example.ui.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Conversation
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

/**
 * Screen matching Screenshot 2: Full-screen Chats & Navigation Drawer view.
 * Features:
 * - "Claude" editorial serif title
 * - Pill-selected "Chats", "Projects", "Code", "Artifacts"
 * - "Pinned" and "Recents" chat transcript list
 * - Bottom left user avatar badge "J"
 * - Bottom right white "+ New chat" action button
 */
@Composable
fun ChatsHistoryScreen(
  conversations: List<Conversation>,
  activeConversationId: String?,
  onSelectConversation: (String) -> Unit,
  onNewChat: () -> Unit,
  onOpenProjects: () -> Unit,
  onOpenCode: () -> Unit,
  onOpenArtifacts: () -> Unit,
  onOpenAccount: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  val pinnedChats = conversations.filter { it.isPinned }
  val recentChats = conversations.filterNot { it.isPinned }

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(colors.bgMain)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("chats_history_screen")
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 20.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      item {
        Spacer(modifier = Modifier.height(12.dp))
        // "Claude" Title in Serif
        Text(
          text = "Claude",
          fontFamily = FontFamily.Serif,
          fontWeight = FontWeight.Normal,
          fontSize = 34.sp,
          color = colors.textPrimary,
          modifier = Modifier.padding(vertical = 12.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
      }

      // 1. "Chats"
      item {
        HistoryNavItem(
          icon = Icons.Outlined.ChatBubbleOutline,
          label = "Chats",
          onClick = { /* Already on Chats */ },
          testTag = "nav_item_chats"
        )
      }

      // 2. "Projects"
      item {
        HistoryNavItem(
          icon = Icons.Outlined.Inventory2,
          label = "Projects",
          onClick = onOpenProjects,
          testTag = "nav_item_projects"
        )
      }

      // 3. "Code"
      item {
        HistoryNavItem(
          icon = Icons.Default.Code,
          label = "Code",
          onClick = onOpenCode,
          testTag = "nav_item_code"
        )
      }

      // 4. "Artifacts"
      item {
        HistoryNavItem(
          icon = Icons.Outlined.Category,
          label = "Artifacts",
          onClick = onOpenArtifacts,
          testTag = "nav_item_artifacts"
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color(0xFF262523))
        Spacer(modifier = Modifier.height(8.dp))
      }

      // Pinned Section
      if (pinnedChats.isNotEmpty()) {
        item {
          Text(
            text = "Pinned",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textTertiary,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
          )
        }

        items(pinnedChats) { chat ->
          ChatHistoryTitleRow(
            title = chat.title,
            onClick = { onSelectConversation(chat.id) }
          )
        }

        item {
          Spacer(modifier = Modifier.height(10.dp))
        }
      }

      // Recents Section
      item {
        Text(
          text = "Recents",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = colors.textTertiary,
          modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
      }

      items(recentChats) { chat ->
        ChatHistoryTitleRow(
          title = chat.title,
          onClick = { onSelectConversation(chat.id) }
        )
      }

      // Bottom Spacer so content is never hidden behind floating buttons
      item {
        Spacer(modifier = Modifier.height(90.dp))
      }
    }

    // Bottom Bar Floating Elements: User Avatar & White "+ New chat" Button
    Row(
      modifier = Modifier
        .align(Alignment.BottomCenter)
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 18.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // User Profile Avatar "J" (in Terracotta Circle)
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(ClaudeTerracotta)
          .clickable { onOpenAccount() }
          .testTag("chats_avatar_button"),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "J",
          color = Color.White,
          fontSize = 19.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      // Floating "+ New chat" Pill Button
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(24.dp))
          .background(Color.White)
          .clickable { onNewChat() }
          .padding(horizontal = 20.dp, vertical = 13.dp)
          .testTag("chats_new_chat_button"),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
          tint = Color.Black,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "New chat",
          color = Color.Black,
          fontSize = 15.5.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
private fun HistoryNavItem(
  icon: ImageVector,
  label: String,
  onClick: () -> Unit,
  testTag: String,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 4.dp, vertical = 13.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = Color(0xFFEDEBE6),
      modifier = Modifier.size(24.dp)
    )
    Spacer(modifier = Modifier.width(18.dp))
    Text(
      text = label,
      fontSize = 17.5.sp,
      color = Color(0xFFEDEBE6)
    )
  }
}

@Composable
private fun ChatHistoryTitleRow(
  title: String,
  onClick: () -> Unit,
) {
  val colors = LocalClaudeColors.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .clickable { onClick() }
      .padding(horizontal = 6.dp, vertical = 11.dp)
      .testTag("chat_history_item"),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = title,
      fontSize = 16.sp,
      color = Color(0xFFEDEBE6),
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}
