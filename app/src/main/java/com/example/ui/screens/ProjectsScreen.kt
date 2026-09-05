package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.domain.model.Project
import com.example.domain.model.ProjectFile
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

@Composable
fun ProjectsScreen(
  projects: List<Project>,
  projectFiles: Map<String, List<ProjectFile>>,
  onBack: () -> Unit,
  onCreateProject: (String, String, String) -> Unit,
  onAddProjectFile: (String, ProjectFile) -> Unit,
  onDeleteProjectFile: (String, String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current
  var selectedProject by remember { mutableStateOf<Project?>(null) }
  var showCreateDialog by remember { mutableStateOf(false) }

  if (selectedProject != null) {
    ProjectDetailView(
      project = selectedProject!!,
      files = projectFiles[selectedProject!!.id] ?: emptyList(),
      onBack = { selectedProject = null },
      onAddFile = { onAddProjectFile(selectedProject!!.id, it) },
      onDeleteFile = { onDeleteProjectFile(selectedProject!!.id, it) }
    )
    return
  }

  // Projects List
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(colors.bgMain)
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
      .testTag("projects_screen")
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = onBack,
          modifier = Modifier.testTag("projects_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = colors.textPrimary
          )
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Projects",
          fontFamily = FontFamily.Serif,
          fontWeight = FontWeight.SemiBold,
          fontSize = 20.sp,
          color = colors.textPrimary
        )
      }

      IconButton(
        onClick = { showCreateDialog = true },
        modifier = Modifier.testTag("create_project_button")
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "New Project",
          tint = ClaudeTerracotta
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = "Organize chats, instructions, and shared knowledge bases in focused workspaces.",
      fontSize = 13.5.sp,
      color = colors.textSecondary,
      lineHeight = 19.sp,
      modifier = Modifier.padding(horizontal = 8.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyColumn(
      verticalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(projects) { project ->
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(colors.surface)
            .border(1.dp, colors.borderSubtle, RoundedCornerShape(14.dp))
            .clickable { selectedProject = project }
            .padding(16.dp)
            .testTag("project_item_${project.id}")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
          ) {
            Box(
              modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ClaudeTerracotta.copy(alpha = 0.12f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Folder,
                contentDescription = null,
                tint = ClaudeTerracotta,
                modifier = Modifier.size(22.dp)
              )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = project.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary,
                fontFamily = FontFamily.SansSerif
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = project.description,
                fontSize = 13.sp,
                color = colors.textSecondary,
                lineHeight = 18.sp
              )
              Spacer(modifier = Modifier.height(8.dp))
              Row {
                Text(
                  text = "${project.filesCount} files",
                  fontSize = 11.5.sp,
                  color = colors.textTertiary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "${project.chatsCount} conversations",
                  fontSize = 11.5.sp,
                  color = colors.textTertiary
                )
              }
            }
          }
        }
      }
    }
  }

  // Create Project Dialog
  if (showCreateDialog) {
    var pName by remember { mutableStateOf("") }
    var pDesc by remember { mutableStateOf("") }
    var pInstr by remember { mutableStateOf("") }

    AlertDialog(
      onDismissRequest = { showCreateDialog = false },
      containerColor = colors.surface,
      title = {
        Text("Create Project", fontFamily = FontFamily.Serif, color = colors.textPrimary)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          OutlinedTextField(
            value = pName,
            onValueChange = { pName = it },
            label = { Text("Project Name") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = pDesc,
            onValueChange = { pDesc = it },
            label = { Text("Short Description") },
            modifier = Modifier.fillMaxWidth()
          )
          OutlinedTextField(
            value = pInstr,
            onValueChange = { pInstr = it },
            label = { Text("Project Instructions") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (pName.isNotBlank()) {
              onCreateProject(pName, pDesc, pInstr)
              showCreateDialog = false
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta),
          shape = RoundedCornerShape(16.dp)
        ) {
          Text("Create", color = colors.textInverse)
        }
      },
      dismissButton = {
        Button(
          onClick = { showCreateDialog = false },
          colors = ButtonDefaults.buttonColors(containerColor = colors.surfaceSecondary),
          shape = RoundedCornerShape(16.dp)
        ) {
          Text("Cancel", color = colors.textPrimary)
        }
      }
    )
  }
}

@Composable
private fun ProjectDetailView(
  project: Project,
  files: List<ProjectFile>,
  onBack: () -> Unit,
  onAddFile: (ProjectFile) -> Unit,
  onDeleteFile: (String) -> Unit,
) {
  val colors = LocalClaudeColors.current
  var selectedTab by remember { mutableIntStateOf(0) }
  val tabs = listOf("Knowledge", "Instructions")

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(colors.bgMain)
      .statusBarsPadding()
      .padding(horizontal = 16.dp)
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back",
          tint = colors.textPrimary
        )
      }
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = project.name,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = colors.textPrimary
      )
    }

    // Tabs
    TabRow(
      selectedTabIndex = selectedTab,
      containerColor = colors.bgMain,
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

    Spacer(modifier = Modifier.height(16.dp))

    if (selectedTab == 0) {
      // Knowledge Files Tab
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Project Files (${files.size})",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = colors.textPrimary
        )

        Button(
          onClick = {
            onAddFile(
              ProjectFile(
                id = "pf_${System.currentTimeMillis()}",
                projectId = project.id,
                name = "context_notes_${files.size + 1}.md",
                sizeText = "4 KB",
                content = "# Project Notes\nContext injected into Claude."
              )
            )
          },
          colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta),
          shape = RoundedCornerShape(16.dp)
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Add File", fontSize = 12.sp, color = colors.textInverse)
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(files) { file ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(colors.surface)
              .border(1.dp, colors.borderSubtle, RoundedCornerShape(10.dp))
              .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Description,
                contentDescription = null,
                tint = ClaudeTerracotta,
                modifier = Modifier.size(20.dp)
              )
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = file.name,
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Medium,
                  color = colors.textPrimary
                )
                Text(
                  text = file.sizeText,
                  fontSize = 11.sp,
                  color = colors.textTertiary
                )
              }
            }

            IconButton(
              onClick = { onDeleteFile(file.id) },
              modifier = Modifier.size(28.dp)
            ) {
              Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Delete",
                tint = colors.textSecondary,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    } else {
      // Instructions Tab
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(colors.surface)
          .border(1.dp, colors.borderSubtle, RoundedCornerShape(12.dp))
          .padding(16.dp)
      ) {
        Text(
          text = "Custom Instructions",
          fontSize = 14.sp,
          fontWeight = FontWeight.SemiBold,
          color = colors.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = project.customInstructions.ifEmpty { "No custom instructions set. Claude uses default behavior." },
          fontSize = 13.5.sp,
          lineHeight = 19.sp,
          color = colors.textSecondary
        )
      }
    }
  }
}
