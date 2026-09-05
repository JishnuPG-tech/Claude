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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.Conversation
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun SearchScreen(
  conversations: List<Conversation>,
  onSelectConversation: (String) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var searchQuery by remember { mutableStateOf("") }

  val filteredConversations = remember(searchQuery, conversations) {
    if (searchQuery.isBlank()) {
      conversations
    } else {
      conversations.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
          it.lastMessagePreview.contains(searchQuery, ignoreCase = true)
      }
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.bgMain)
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
      .testTag("search_screen")
  ) {
    // Search Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = colors.textPrimary
        )
      }

      Spacer(modifier = Modifier.width(4.dp))

      Row(
        modifier = Modifier
          .weight(1f)
          .clip(RoundedCornerShape(20.dp))
          .background(colors.surfaceSecondary)
          .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = null,
          tint = colors.textSecondary,
          modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
          modifier = Modifier.weight(1f),
          contentAlignment = Alignment.CenterStart
        ) {
          if (searchQuery.isEmpty()) {
            Text(
              text = "Search all chats...",
              fontSize = 14.sp,
              color = colors.textSecondary
            )
          }

          BasicTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            textStyle = TextStyle(
              fontFamily = FontFamily.SansSerif,
              fontSize = 14.sp,
              color = colors.textPrimary
            ),
            cursorBrush = SolidColor(ClaudeTerracotta),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("search_input_field")
          )
        }

        if (searchQuery.isNotEmpty()) {
          Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear search",
            tint = colors.textSecondary,
            modifier = Modifier
              .size(16.dp)
              .clickable { searchQuery = "" }
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Results List
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(filteredConversations) { conv ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surface)
            .clickable { onSelectConversation(conv.id) }
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.ChatBubbleOutline,
            contentDescription = null,
            tint = ClaudeTerracotta,
            modifier = Modifier.size(20.dp)
          )

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = conv.title,
              fontSize = 14.5.sp,
              fontWeight = FontWeight.SemiBold,
              color = colors.textPrimary
            )
            if (conv.lastMessagePreview.isNotEmpty()) {
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = conv.lastMessagePreview,
                fontSize = 12.5.sp,
                color = colors.textSecondary,
                maxLines = 1
              )
            }
          }
        }
      }
    }
  }
}
