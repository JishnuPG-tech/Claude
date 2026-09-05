package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ClaudeAppSettings
import com.example.domain.model.Conversation
import com.example.domain.model.Project
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ClaudeDrawer(
  conversations: List<Conversation>,
  projects: List<Project>,
  activeConversationId: String?,
  settings: ClaudeAppSettings,
  onSelectConversation: (String) -> Unit,
  onNewChat: () -> Unit,
  onOpenProjects: () -> Unit,
  onOpenClaudeCode: () -> Unit,
  onOpenSearch: () -> Unit,
  onOpenSettings: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  Column(
    modifier = modifier
      .width(310.dp)
      .fillMaxHeight()
      .background(colors.bgMain)
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 16.dp, vertical = 12.dp)
      .testTag("claude_drawer")
  ) {
    // 1. Header with Claude Logo & Wordmark
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Claude Terracotta Star Badge
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(ClaudeTerracotta),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "*",
          color = colors.textInverse,
          fontWeight = FontWeight.Black,
          fontSize = 22.sp,
          modifier = Modifier.padding(bottom = 2.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Text(
        text = "Claude",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        color = colors.textPrimary,
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // 2. Start New Chat Button (Prominent Terracotta/Contrast Pill)
    Button(
      onClick = onNewChat,
      modifier = Modifier
        .fillMaxWidth()
        .height(44.dp)
        .testTag("drawer_new_chat_button"),
      shape = RoundedCornerShape(22.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = colors.surfaceSecondary,
        contentColor = colors.textPrimary
      ),
      border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderSubtle)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = null,
          tint = ClaudeTerracotta,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Start new chat",
          fontSize = 14.sp,
          fontWeight = FontWeight.Medium,
          color = colors.textPrimary
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 3. Search Bar Shortcut
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(colors.surfaceSecondary.copy(alpha = 0.5f))
        .clickable { onOpenSearch() }
        .padding(horizontal = 12.dp, vertical = 10.dp)
        .testTag("drawer_search_button"),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search chats",
        tint = colors.textSecondary,
        modifier = Modifier.size(18.dp)
      )
      Spacer(modifier = Modifier.width(10.dp))
      Text(
        text = "Search chats...",
        fontSize = 13.sp,
        color = colors.textSecondary
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // 4. Scrollable List of Chats & Workspaces
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      // Workspaces & Code
      item {
        DrawerSectionHeader("WORKSPACES")
      }

      item {
        DrawerItemRow(
          icon = Icons.Default.Folder,
          label = "Projects",
          badge = "${projects.size}",
          isSelected = false,
          onClick = onOpenProjects
        )
      }

      item {
        DrawerItemRow(
          icon = Icons.Default.Terminal,
          label = "Claude Code",
          badge = "BETA",
          isSelected = false,
          onClick = onOpenClaudeCode
        )
      }

      // Recent Conversations Grouped
      val pinned = conversations.filter { it.isPinned }
      val recent = conversations.filterNot { it.isPinned }

      if (pinned.isNotEmpty()) {
        item {
          Spacer(modifier = Modifier.height(10.dp))
          DrawerSectionHeader("PINNED")
        }
        items(pinned) { conv ->
          DrawerItemRow(
            icon = Icons.Default.PushPin,
            label = conv.title,
            isSelected = conv.id == activeConversationId,
            onClick = { onSelectConversation(conv.id) }
          )
        }
      }

      if (recent.isNotEmpty()) {
        item {
          Spacer(modifier = Modifier.height(10.dp))
          DrawerSectionHeader("RECENT")
        }
        items(recent) { conv ->
          DrawerItemRow(
            icon = Icons.Default.ChatBubbleOutline,
            label = conv.title,
            isSelected = conv.id == activeConversationId,
            onClick = { onSelectConversation(conv.id) }
          )
        }
      }
    }

    // 5. User Account Footer Card & Settings
    HorizontalDivider(color = colors.borderSubtle)
    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .clickable { onOpenSettings() }
        .padding(horizontal = 8.dp, vertical = 6.dp)
        .testTag("drawer_user_card"),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        // Avatar circle
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(ClaudeTerracotta.copy(alpha = 0.2f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = settings.userName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            color = ClaudeTerracotta
          )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = settings.userName,
              fontSize = 14.sp,
              fontWeight = FontWeight.Medium,
              color = colors.textPrimary,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(6.dp))
            // Pro pill
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(ClaudeTerracotta.copy(alpha = 0.15f))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = settings.planName.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = ClaudeTerracotta
              )
            }
          }
          Text(
            text = settings.userEmail,
            fontSize = 11.sp,
            color = colors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      IconButton(
        onClick = onOpenSettings,
        modifier = Modifier.size(32.dp)
      ) {
        Icon(
          imageVector = Icons.Default.Settings,
          contentDescription = "Settings",
          tint = colors.textSecondary,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Composable
private fun DrawerSectionHeader(title: String) {
  val colors = LocalClaudeColors.current
  Text(
    text = title,
    fontSize = 11.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.SansSerif,
    color = colors.textTertiary,
    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
  )
}

@Composable
private fun DrawerItemRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  badge: String? = null,
) {
  val colors = LocalClaudeColors.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(if (isSelected) colors.surfaceSecondary else androidx.compose.ui.graphics.Color.Transparent)
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 9.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = if (isSelected) ClaudeTerracotta else colors.textSecondary,
      modifier = Modifier.size(18.dp)
    )

    Spacer(modifier = Modifier.width(10.dp))

    Text(
      text = label,
      fontSize = 13.5.sp,
      fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
      color = colors.textPrimary,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.weight(1f)
    )

    if (badge != null) {
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(6.dp))
          .background(colors.surfaceContainer)
          .padding(horizontal = 6.dp, vertical = 2.dp)
      ) {
        Text(
          text = badge,
          fontSize = 10.sp,
          fontWeight = FontWeight.Medium,
          color = colors.textSecondary
        )
      }
    }
  }
}
