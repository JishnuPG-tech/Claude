package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ClaudeAppSettings
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
  settings: ClaudeAppSettings,
  onSuggestionClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  val suggestions = listOf(
    "How does Claude's Extended Thinking work?",
    "Build a clean Android Compose component",
    "Explain quantum computing simply",
    "Review my architectural approach"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.bgMain)
      .verticalScroll(rememberScrollState())
      .padding(horizontal = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Spacer(modifier = Modifier.height(40.dp))

    // Terracotta Claude 14-spoke Sunburst Emblem (Exact from Screenshot 1)
    com.example.ui.components.ClaudeSunburst(
      size = 64.dp,
      color = ClaudeTerracotta
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Literary Greeting: "Let's noodle" in Serif (Exact from Screenshot 1)
    Text(
      text = "Let's noodle",
      fontFamily = FontFamily.Serif,
      fontWeight = FontWeight.Normal,
      fontSize = 32.sp,
      color = colors.textPrimary,
      textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Prompt Suggestion Chips (FlowRow)
    FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      suggestions.forEach { suggestion ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(colors.surface)
            .border(1.dp, colors.borderSubtle, RoundedCornerShape(20.dp))
            .clickable { onSuggestionClick(suggestion) }
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .testTag("suggestion_chip")
        ) {
          Text(
            text = suggestion,
            fontSize = 13.5.sp,
            color = colors.textPrimary,
            fontFamily = FontFamily.SansSerif,
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
      }
    }

    Spacer(modifier = Modifier.height(60.dp))
  }
}
