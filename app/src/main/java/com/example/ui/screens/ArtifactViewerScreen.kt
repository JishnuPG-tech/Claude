package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.domain.model.ArtifactData
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ArtifactViewerScreen(
  artifact: ArtifactData,
  onClose: () -> Unit,
  onCopyCode: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Code", "Preview")

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.surface)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("artifact_viewer_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onClose) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close",
            tint = colors.textPrimary
          )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
          Text(
            text = artifact.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textPrimary
          )
          Text(
            text = artifact.language.uppercase(),
            fontSize = 11.sp,
            color = colors.textSecondary
          )
        }
      }

      Button(
        onClick = { onCopyCode(artifact.code) },
        colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta),
        shape = RoundedCornerShape(16.dp)
      ) {
        Icon(
          imageVector = Icons.Default.ContentCopy,
          contentDescription = null,
          modifier = Modifier.size(16.dp),
          tint = colors.textInverse
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Copy", fontSize = 12.sp, color = colors.textInverse)
      }
    }

    // Tab Row
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = colors.surface,
      contentColor = ClaudeTerracotta,
      indicator = { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
          modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
          color = ClaudeTerracotta
        )
      }
    ) {
      tabs.forEachIndexed { index, title ->
        Tab(
          selected = selectedTab == index,
          onClick = { selectedTab = index },
          text = {
            Text(
              text = title,
              fontSize = 14.sp,
              fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
              color = if (selectedTab == index) ClaudeTerracotta else colors.textSecondary
            )
          }
        )
      }
    }

    HorizontalDivider(color = colors.borderSubtle)

    if (selectedTab == 0) {
      // Code View with line numbers
      val lines = artifact.code.lines()

      Row(
        modifier = Modifier
          .fillMaxSize()
          .background(colors.codeBg)
          .verticalScroll(rememberScrollState())
          .horizontalScroll(rememberScrollState())
          .padding(16.dp)
      ) {
        // Line numbers column
        Column(modifier = Modifier.padding(end = 16.dp)) {
          lines.indices.forEach { idx ->
            Text(
              text = "${idx + 1}",
              fontFamily = FontFamily.Monospace,
              fontSize = 13.sp,
              lineHeight = 20.sp,
              color = colors.textTertiary
            )
          }
        }

        // Code content
        Column {
          lines.forEach { line ->
            Text(
              text = line,
              fontFamily = FontFamily.Monospace,
              fontSize = 13.sp,
              lineHeight = 20.sp,
              color = colors.textPrimary
            )
          }
        }
      }
    } else {
      // Rendered Preview
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(colors.bgMain)
          .padding(24.dp),
        contentAlignment = Alignment.Center
      ) {
        Column(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
            .padding(24.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "Artifact Component Preview",
            fontFamily = FontFamily.Serif,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = colors.textPrimary
          )
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Interactive preview container running sandbox instance of ${artifact.title}.",
            fontSize = 13.sp,
            color = colors.textSecondary,
            lineHeight = 18.sp
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta),
            shape = RoundedCornerShape(20.dp)
          ) {
            Text("Sample Interactive Button", color = colors.textInverse)
          }
        }
      }
    }
  }
}
