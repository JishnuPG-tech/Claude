package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.ProjectFile
import com.example.ui.components.AttachmentSheet
import com.example.ui.components.ClaudeComposer
import com.example.ui.components.ClaudeDrawer
import com.example.ui.components.ClaudeTopAppBar
import com.example.ui.components.ModelSelectorSheet
import com.example.ui.screens.ArtifactViewerScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ChatsHistoryScreen
import com.example.ui.screens.ClaudeCodeScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.VoiceScreen
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.ClaudeTheme
import com.example.ui.theme.LocalClaudeColors
import kotlinx.coroutines.launch

@Composable
fun ClaudeApp(
  viewModel: ClaudeViewModel = viewModel(),
) {
  val uiState by viewModel.uiState.collectAsState()
  val conversations by viewModel.conversations.collectAsState()
  val projects by viewModel.projects.collectAsState()
  val messages by viewModel.currentMessages.collectAsState()

  // Project files mock mapping for ProjectsScreen
  val projectFiles = remember {
    mutableStateMapOf<String, List<ProjectFile>>(
      "p_1" to listOf(
        ProjectFile("f_1", "p_1", "design-system.md", "14 KB"),
        ProjectFile("f_2", "p_1", "tokens.json", "3 KB"),
        ProjectFile("f_3", "p_1", "architecture.md", "22 KB"),
      ),
      "p_2" to listOf(
        ProjectFile("f_4", "p_2", "hermes-events.proto", "8 KB"),
        ProjectFile("f_5", "p_2", "agent-loop.py", "12 KB"),
      )
    )
  }

  // Theme resolution
  val darkTheme = when (uiState.settings.themeMode) {
    "Dark" -> true
    "Light" -> false
    else -> isSystemInDarkTheme()
  }

  ClaudeTheme(darkTheme = darkTheme) {
    val colors = LocalClaudeColors.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var showRenameDialog by remember { mutableStateOf(false) }
    var renameText by remember { mutableStateOf("") }

    // System Back Press handling
    BackHandler(
      enabled = uiState.currentScreen != ClaudeScreen.LOGIN && (
        drawerState.isOpen ||
        uiState.selectedArtifact != null ||
        uiState.currentScreen != ClaudeScreen.CHATS_HISTORY
      )
    ) {
      when {
        drawerState.isOpen -> scope.launch { drawerState.close() }
        uiState.selectedArtifact != null -> viewModel.closeArtifact()
        uiState.currentScreen != ClaudeScreen.CHATS_HISTORY -> viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY)
      }
    }

    if (uiState.currentScreen == ClaudeScreen.LOGIN) {
      // Full screen Login View (Screenshot 1)
      LoginScreen(
        onLoginSuccess = { email ->
          viewModel.login(email)
        }
      )
    } else if (uiState.currentScreen == ClaudeScreen.CHATS_HISTORY) {
      // Full screen Chats History View (Screenshot 3)
      ChatsHistoryScreen(
        conversations = conversations,
        activeConversationId = uiState.activeConversationId,
        onSelectConversation = { convId ->
          viewModel.selectConversation(convId)
        },
        onNewChat = {
          viewModel.startNewChat()
        },
        onOpenProjects = {
          viewModel.navigateTo(ClaudeScreen.PROJECTS)
        },
        onOpenCode = {
          viewModel.navigateTo(ClaudeScreen.CLAUDE_CODE)
        },
        onOpenArtifacts = {
          viewModel.navigateTo(ClaudeScreen.SEARCH)
        },
        onOpenAccount = {
          viewModel.navigateTo(ClaudeScreen.SETTINGS)
        }
      )
    } else {
      ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
          ClaudeDrawer(
            conversations = conversations,
            projects = projects,
            activeConversationId = uiState.activeConversationId,
            settings = uiState.settings,
            onSelectConversation = { convId ->
              viewModel.selectConversation(convId)
              scope.launch { drawerState.close() }
            },
            onNewChat = {
              viewModel.startNewChat()
              scope.launch { drawerState.close() }
            },
            onOpenProjects = {
              viewModel.navigateTo(ClaudeScreen.PROJECTS)
              scope.launch { drawerState.close() }
            },
            onOpenClaudeCode = {
              viewModel.navigateTo(ClaudeScreen.CLAUDE_CODE)
              scope.launch { drawerState.close() }
            },
            onOpenSearch = {
              viewModel.navigateTo(ClaudeScreen.SEARCH)
              scope.launch { drawerState.close() }
            },
            onOpenSettings = {
              viewModel.navigateTo(ClaudeScreen.SETTINGS)
              scope.launch { drawerState.close() }
            },
          )
        }
      ) {
        Scaffold(
          containerColor = colors.bgMain,
          topBar = {
            if (uiState.currentScreen == ClaudeScreen.HOME || uiState.currentScreen == ClaudeScreen.CHAT) {
              ClaudeTopAppBar(
                title = uiState.activeConversationTitle,
                isChatActive = messages.isNotEmpty() && uiState.activeConversationId != null,
                selectedModel = uiState.selectedModel,
                onMenuClick = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) },
                onBackClick = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) },
                onNewChatClick = { viewModel.startNewChat() },
                onModelSelectorClick = { viewModel.setModelSelectorOpen(true) },
                onOpenAccount = { viewModel.navigateTo(ClaudeScreen.SETTINGS) },
                onRenameChat = if (messages.isNotEmpty()) {
                  {
                    renameText = uiState.activeConversationTitle
                    showRenameDialog = true
                  }
                } else null,
                onDeleteChat = if (messages.isNotEmpty()) {
                  {
                    viewModel.deleteCurrentConversation()
                    Toast.makeText(context, "Conversation deleted", Toast.LENGTH_SHORT).show()
                  }
                } else null
              )
            }
          },
          bottomBar = {
            if (uiState.currentScreen == ClaudeScreen.HOME || uiState.currentScreen == ClaudeScreen.CHAT) {
              ClaudeComposer(
                text = uiState.composerText,
                onTextChanged = { viewModel.onComposerTextChanged(it) },
                onSendClick = { viewModel.sendMessage() },
                onStopClick = { viewModel.stopStreaming() },
                isStreaming = uiState.isStreaming,
                selectedModel = uiState.selectedModel,
                onModelSelectorClick = { viewModel.setModelSelectorOpen(true) },
                onAttachmentClick = { viewModel.setAttachmentSheetOpen(true) },
                onVoiceClick = { viewModel.navigateTo(ClaudeScreen.VOICE) },
                attachments = uiState.stagedAttachments,
                onRemoveAttachment = { viewModel.removeAttachment(it) },
                placeholderText = "Chat with Claude...",
                onUpgradeClick = { viewModel.setProUpgradeDialogOpen(true) }
              )
            }
          }
        ) { innerPadding ->
          Box(
            modifier = Modifier
              .fillMaxSize()
              .then(
                if (uiState.currentScreen == ClaudeScreen.HOME || uiState.currentScreen == ClaudeScreen.CHAT) {
                  Modifier.padding(innerPadding)
                } else {
                  Modifier
                }
              )
          ) {
            when (uiState.currentScreen) {
              ClaudeScreen.HOME,
              ClaudeScreen.CHAT -> {
                ChatScreen(
                  messages = messages,
                  isStreaming = uiState.isStreaming,
                  onCopyText = { text ->
                    clipboardManager.setText(AnnotatedString(text))
                    Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                  },
                  onOpenArtifact = { artifact ->
                    viewModel.openArtifact(artifact)
                  },
                  onRegenerate = {
                    val lastUserMsg = messages.lastOrNull { it.role == com.example.domain.model.MessageRole.USER }
                    if (lastUserMsg != null) {
                      viewModel.sendMessage(lastUserMsg.content)
                    }
                  },
                  onApproveTool = { toolId ->
                    viewModel.approveTool(toolId)
                    Toast.makeText(context, "Action approved", Toast.LENGTH_SHORT).show()
                  },
                  onRejectTool = { toolId ->
                    viewModel.rejectTool(toolId)
                    Toast.makeText(context, "Action rejected", Toast.LENGTH_SHORT).show()
                  }
                )
              }
              ClaudeScreen.LOGIN -> {} // Handled above
              ClaudeScreen.CHATS_HISTORY -> {} // Handled above
              ClaudeScreen.PROJECTS -> {
                ProjectsScreen(
                  projects = projects,
                  projectFiles = projectFiles,
                  onBack = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) },
                  onCreateProject = { name, desc, instr ->
                    viewModel.createProject(name, desc, instr)
                  },
                  onAddProjectFile = { pId, file ->
                    viewModel.addProjectFile(pId, file)
                    val current = projectFiles[pId] ?: emptyList()
                    projectFiles[pId] = current + file
                  },
                  onDeleteProjectFile = { pId, fileId ->
                    viewModel.deleteProjectFile(pId, fileId)
                    val current = projectFiles[pId] ?: emptyList()
                    projectFiles[pId] = current.filterNot { it.id == fileId }
                  }
                )
              }
              ClaudeScreen.CLAUDE_CODE -> {
                ClaudeCodeScreen(
                  onBack = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) }
                )
              }
              ClaudeScreen.SETTINGS -> {
                SettingsScreen(
                  settings = uiState.settings,
                  onUpdateSettings = { viewModel.updateSettings(it) },
                  onBack = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) },
                  onMenuClick = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) },
                  onClearChats = {
                    viewModel.clearAllChats()
                    Toast.makeText(context, "All chats cleared", Toast.LENGTH_SHORT).show()
                  },
                  onLogout = {
                    viewModel.logout()
                  }
                )
              }
              ClaudeScreen.VOICE -> {
                VoiceScreen(
                  voiceName = uiState.settings.voiceName,
                  onClose = { viewModel.navigateTo(ClaudeScreen.CHAT) }
                )
              }
              ClaudeScreen.SEARCH -> {
                SearchScreen(
                  conversations = conversations,
                  onSelectConversation = { convId ->
                    viewModel.selectConversation(convId)
                  },
                  onBack = { viewModel.navigateTo(ClaudeScreen.CHATS_HISTORY) }
                )
              }
            }
          }
        }
      }
    }

      // Model Selector Bottom Sheet
      if (uiState.isModelSelectorOpen) {
        ModelSelectorSheet(
          selectedModel = uiState.selectedModel,
          thinkingEnabled = uiState.settings.thinkingEnabled,
          thinkingBudgetTokens = uiState.settings.thinkingBudgetTokens,
          onModelSelected = { viewModel.setModel(it) },
          onThinkingToggle = { viewModel.updateSettings(uiState.settings.copy(thinkingEnabled = it)) },
          onBudgetChange = { viewModel.updateSettings(uiState.settings.copy(thinkingBudgetTokens = it)) },
          onDismiss = { viewModel.setModelSelectorOpen(false) }
        )
      }

      // Attachment Picker Bottom Sheet
      if (uiState.isAttachmentSheetOpen) {
        AttachmentSheet(
          onAttachmentAdded = { viewModel.addAttachment(it) },
          onDismiss = { viewModel.setAttachmentSheetOpen(false) }
        )
      }

      // Fullscreen Artifact Viewer
      if (uiState.selectedArtifact != null) {
        ArtifactViewerScreen(
          artifact = uiState.selectedArtifact!!,
          onClose = { viewModel.closeArtifact() },
          onCopyCode = { code ->
            clipboardManager.setText(AnnotatedString(code))
            Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
          }
        )
      }

      // Rename Chat Dialog
      if (showRenameDialog) {
        AlertDialog(
          onDismissRequest = { showRenameDialog = false },
          containerColor = colors.surface,
          title = { Text("Rename Chat", color = colors.textPrimary, fontFamily = FontFamily.Serif) },
          text = {
            OutlinedTextField(
              value = renameText,
              onValueChange = { renameText = it },
              modifier = Modifier.fillMaxWidth()
            )
          },
          confirmButton = {
            Button(
              onClick = {
                if (renameText.isNotBlank()) {
                  viewModel.renameCurrentConversation(renameText)
                }
                showRenameDialog = false
              },
              colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta)
            ) {
              Text("Save", color = colors.textInverse)
            }
          },
          dismissButton = {
            Button(
              onClick = { showRenameDialog = false },
              colors = ButtonDefaults.buttonColors(containerColor = colors.surfaceSecondary)
            ) {
              Text("Cancel", color = colors.textPrimary)
            }
          }
        )
      }

      // Claude Pro Upgrade Dialog
      if (uiState.isProUpgradeDialogOpen) {
        AlertDialog(
          onDismissRequest = { viewModel.setProUpgradeDialogOpen(false) },
          containerColor = Color(0xFF1E1D1C),
          shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
          title = {
            androidx.compose.foundation.layout.Row(
              verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
              com.example.ui.components.ClaudeSunburst(
                size = 28.dp,
                color = ClaudeTerracotta
              )
              androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(start = 12.dp))
              Text(
                "Claude Pro",
                color = Color.White,
                fontFamily = FontFamily.Serif,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                fontSize = 22.sp
              )
            }
          },
          text = {
            androidx.compose.foundation.layout.Column(
              verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
            ) {
              Text(
                "Supercharge your work with our most intelligent models and exclusive mobile features:",
                color = Color(0xFFC8C6C0),
                fontSize = 14.sp,
                lineHeight = 20.sp
              )
              Text("• 5x more usage compared to Free tier", color = Color(0xFFEDEBE6), fontSize = 13.5.sp)
              Text("• Priority access to Sonnet 5 with extended thinking", color = Color(0xFFEDEBE6), fontSize = 13.5.sp)
              Text("• Full access to Projects, Code sandbox & Artifacts", color = Color(0xFFEDEBE6), fontSize = 13.5.sp)
              Text("• Early access to new experimental features", color = Color(0xFFEDEBE6), fontSize = 13.5.sp)
            }
          },
          confirmButton = {
            Button(
              onClick = {
                viewModel.setProUpgradeDialogOpen(false)
                Toast.makeText(context, "Welcome to Claude Pro!", Toast.LENGTH_LONG).show()
              },
              shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA78BFA))
            ) {
              Text("Upgrade to Pro", color = Color.Black, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            }
          },
          dismissButton = {
            Button(
              onClick = { viewModel.setProUpgradeDialogOpen(false) },
              shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A2928))
            ) {
              Text("Maybe later", color = Color(0xFFC8C6C0))
            }
          }
        )
      }
    }
  }
