package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ConversationRepositoryImpl
import com.example.domain.ai.HermesAdapter
import com.example.domain.interfaces.HermesEvent
import com.example.domain.model.ArtifactData
import com.example.domain.model.Attachment
import com.example.domain.model.AvailableClaudeModels
import com.example.domain.model.ClaudeAppSettings
import com.example.domain.model.ClaudeModel
import com.example.domain.model.Conversation
import com.example.domain.model.Message
import com.example.domain.model.MessageRole
import com.example.domain.model.Project
import com.example.domain.model.ProjectFile
import com.example.domain.model.ThinkingData
import com.example.domain.model.ThinkingStatus
import com.example.domain.model.ToolCallData
import com.example.domain.model.ToolStatus
import java.util.UUID
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ClaudeScreen {
  LOGIN,
  CHATS_HISTORY,
  HOME,
  CHAT,
  PROJECTS,
  CLAUDE_CODE,
  SETTINGS,
  VOICE,
  SEARCH,
}

data class ClaudeUiState(
  val currentScreen: ClaudeScreen = ClaudeScreen.LOGIN,
  val isLoggedIn: Boolean = false,
  val activeConversationId: String? = null,
  val activeConversationTitle: String = "",
  val composerText: String = "",
  val isStreaming: Boolean = false,
  val selectedModel: ClaudeModel = AvailableClaudeModels.first { it.isDefault },
  val stagedAttachments: List<Attachment> = emptyList(),
  val selectedArtifact: ArtifactData? = null,
  val isModelSelectorOpen: Boolean = false,
  val isAttachmentSheetOpen: Boolean = false,
  val isProUpgradeDialogOpen: Boolean = false,
  val settings: ClaudeAppSettings = ClaudeAppSettings(),
)

class ClaudeViewModel(
  private val repository: ConversationRepositoryImpl = ConversationRepositoryImpl(),
  private val hermesAdapter: HermesAdapter = HermesAdapter(),
) : ViewModel() {

  private val _uiState = MutableStateFlow(ClaudeUiState())
  val uiState: StateFlow<ClaudeUiState> = _uiState.asStateFlow()

  val conversations: StateFlow<List<Conversation>> = repository.getConversations()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val projects: StateFlow<List<Project>> = repository.getProjects()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _currentMessages = MutableStateFlow<List<Message>>(emptyList())
  val currentMessages: StateFlow<List<Message>> = _currentMessages.asStateFlow()

  private var streamingJob: Job? = null
  private var activeMessageFlowJob: Job? = null

  init {
    // Select first conversation or remain on Home
  }

  fun navigateTo(screen: ClaudeScreen) {
    _uiState.value = _uiState.value.copy(currentScreen = screen)
  }

  fun onComposerTextChanged(newText: String) {
    _uiState.value = _uiState.value.copy(composerText = newText)
  }

  fun setModel(model: ClaudeModel) {
    _uiState.value = _uiState.value.copy(selectedModel = model, isModelSelectorOpen = false)
  }

  fun setModelSelectorOpen(open: Boolean) {
    _uiState.value = _uiState.value.copy(isModelSelectorOpen = open)
  }

  fun setAttachmentSheetOpen(open: Boolean) {
    _uiState.value = _uiState.value.copy(isAttachmentSheetOpen = open)
  }

  fun addAttachment(attachment: Attachment) {
    val current = _uiState.value.stagedAttachments
    _uiState.value = _uiState.value.copy(stagedAttachments = current + attachment)
  }

  fun removeAttachment(attachment: Attachment) {
    val current = _uiState.value.stagedAttachments.filterNot { it.id == attachment.id }
    _uiState.value = _uiState.value.copy(stagedAttachments = current)
  }

  fun openArtifact(artifact: ArtifactData) {
    _uiState.value = _uiState.value.copy(selectedArtifact = artifact)
  }

  fun closeArtifact() {
    _uiState.value = _uiState.value.copy(selectedArtifact = null)
  }

  fun updateSettings(newSettings: ClaudeAppSettings) {
    _uiState.value = _uiState.value.copy(settings = newSettings)
  }

  fun setProUpgradeDialogOpen(open: Boolean) {
    _uiState.value = _uiState.value.copy(isProUpgradeDialogOpen = open)
  }

  fun login(email: String = "jishnupg2005@gmail.com") {
    _uiState.value = _uiState.value.copy(
      isLoggedIn = true,
      currentScreen = ClaudeScreen.HOME,
      activeConversationId = null,
      activeConversationTitle = "",
      settings = _uiState.value.settings.copy(userEmail = email)
    )
    _currentMessages.value = emptyList()
  }

  fun logout() {
    _uiState.value = _uiState.value.copy(
      isLoggedIn = false,
      currentScreen = ClaudeScreen.LOGIN,
      activeConversationId = null,
      activeConversationTitle = ""
    )
    _currentMessages.value = emptyList()
  }

  fun startNewChat() {
    streamingJob?.cancel()
    _uiState.value = _uiState.value.copy(
      currentScreen = ClaudeScreen.HOME,
      activeConversationId = null,
      activeConversationTitle = "",
      composerText = "",
      isStreaming = false,
      stagedAttachments = emptyList()
    )
    _currentMessages.value = emptyList()
  }

  fun selectConversation(conversationId: String) {
    streamingJob?.cancel()
    activeMessageFlowJob?.cancel()

    val conv = conversations.value.find { it.id == conversationId }
    _uiState.value = _uiState.value.copy(
      currentScreen = ClaudeScreen.CHAT,
      activeConversationId = conversationId,
      activeConversationTitle = conv?.title ?: "Chat",
      composerText = "",
      isStreaming = false,
      stagedAttachments = emptyList()
    )

    activeMessageFlowJob = viewModelScope.launch {
      repository.getMessages(conversationId).collect { msgs ->
        _currentMessages.value = msgs
      }
    }
  }

  fun sendMessage(promptText: String = _uiState.value.composerText) {
    if (promptText.isBlank() && _uiState.value.stagedAttachments.isEmpty()) return

    val currentConvId = _uiState.value.activeConversationId ?: run {
      // Create new conversation
      val newId = "c_" + UUID.randomUUID().toString().take(8)
      val newTitle = promptText.take(30).ifEmpty { "New conversation" }
      val newConv = Conversation(
        id = newId,
        title = newTitle,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis(),
        lastMessagePreview = promptText.take(40)
      )
      viewModelScope.launch {
        repository.saveConversation(newConv)
      }
      newId
    }

    val userMessage = Message(
      id = "m_user_" + UUID.randomUUID().toString().take(8),
      conversationId = currentConvId,
      role = MessageRole.USER,
      content = promptText,
      timestamp = System.currentTimeMillis(),
      attachments = _uiState.value.stagedAttachments,
    )

    _uiState.value = _uiState.value.copy(
      currentScreen = ClaudeScreen.CHAT,
      activeConversationId = currentConvId,
      composerText = "",
      stagedAttachments = emptyList(),
      isStreaming = true
    )

    viewModelScope.launch {
      repository.saveMessage(userMessage)
    }

    // Launch streaming response
    val assistantMessageId = "m_asst_" + UUID.randomUUID().toString().take(8)
    var currentAssistantMsg = Message(
      id = assistantMessageId,
      conversationId = currentConvId,
      role = MessageRole.ASSISTANT,
      content = "",
      timestamp = System.currentTimeMillis(),
      modelName = _uiState.value.selectedModel.displayName,
      isStreaming = true,
    )

    streamingJob = viewModelScope.launch {
      // Save placeholder assistant message
      repository.saveMessage(currentAssistantMsg)

      val eventFlow = hermesAdapter.streamMessage(
        prompt = promptText,
        conversationId = currentConvId,
        model = _uiState.value.selectedModel,
        thinkingEnabled = _uiState.value.settings.thinkingEnabled,
        attachments = userMessage.attachments
      )

      eventFlow.collect { event ->
        when (event) {
          is HermesEvent.MessageStart -> {
            // Already initialized
          }
          is HermesEvent.ThinkingStart -> {
            currentAssistantMsg = currentAssistantMsg.copy(
              thinking = ThinkingData(
                content = "",
                durationSeconds = 0,
                status = ThinkingStatus.THINKING,
                isExpanded = true
              )
            )
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.ThinkingDelta -> {
            val prevThinking = currentAssistantMsg.thinking
            if (prevThinking != null) {
              currentAssistantMsg = currentAssistantMsg.copy(
                thinking = prevThinking.copy(content = prevThinking.content + event.text)
              )
              repository.updateMessage(currentAssistantMsg)
            }
          }
          is HermesEvent.ThinkingComplete -> {
            val prevThinking = currentAssistantMsg.thinking
            if (prevThinking != null) {
              currentAssistantMsg = currentAssistantMsg.copy(
                thinking = prevThinking.copy(
                  durationSeconds = event.durationSeconds,
                  status = ThinkingStatus.COMPLETED,
                  isExpanded = false
                )
              )
              repository.updateMessage(currentAssistantMsg)
            }
          }
          is HermesEvent.ToolStart -> {
            val newTool = ToolCallData(
              id = event.toolId,
              name = event.toolName,
              inputJson = event.inputJson,
              status = ToolStatus.RUNNING,
              isExpanded = true
            )
            currentAssistantMsg = currentAssistantMsg.copy(
              toolCalls = currentAssistantMsg.toolCalls + newTool
            )
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.ToolUpdate -> {
            val updatedTools = currentAssistantMsg.toolCalls.map { t ->
              if (t.id == event.toolId) t.copy(outputJson = (t.outputJson ?: "") + event.outputDelta) else t
            }
            currentAssistantMsg = currentAssistantMsg.copy(toolCalls = updatedTools)
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.ToolResult -> {
            val updatedTools = currentAssistantMsg.toolCalls.map { t ->
              if (t.id == event.toolId) t.copy(
                outputJson = event.outputJson,
                status = if (event.isError) ToolStatus.ERROR else ToolStatus.COMPLETED,
                isExpanded = false
              ) else t
            }
            currentAssistantMsg = currentAssistantMsg.copy(toolCalls = updatedTools)
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.ApprovalRequired -> {
            val updatedTools = currentAssistantMsg.toolCalls.map { t ->
              if (t.id == event.toolId) t.copy(
                status = ToolStatus.APPROVAL_REQUIRED,
                isExpanded = true
              ) else t
            }
            currentAssistantMsg = currentAssistantMsg.copy(toolCalls = updatedTools)
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.ArtifactProduced -> {
            val newArt = ArtifactData(
              id = event.artifactId,
              title = event.title,
              language = event.language,
              code = event.code
            )
            currentAssistantMsg = currentAssistantMsg.copy(
              artifacts = currentAssistantMsg.artifacts + newArt
            )
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.MessageDelta -> {
            currentAssistantMsg = currentAssistantMsg.copy(
              content = currentAssistantMsg.content + event.textDelta
            )
            repository.updateMessage(currentAssistantMsg)
          }
          is HermesEvent.MessageComplete, is HermesEvent.AgentComplete -> {
            currentAssistantMsg = currentAssistantMsg.copy(isStreaming = false)
            repository.updateMessage(currentAssistantMsg)
            _uiState.value = _uiState.value.copy(isStreaming = false)
          }
          is HermesEvent.AgentError -> {
            currentAssistantMsg = currentAssistantMsg.copy(
              content = currentAssistantMsg.content + "\n\n*[Error: ${event.errorMessage}]*",
              isStreaming = false
            )
            repository.updateMessage(currentAssistantMsg)
            _uiState.value = _uiState.value.copy(isStreaming = false)
          }
        }
      }
      _uiState.value = _uiState.value.copy(isStreaming = false)
    }
  }

  fun stopStreaming() {
    streamingJob?.cancel()
    _uiState.value = _uiState.value.copy(isStreaming = false)
  }

  fun approveTool(toolId: String) {
    val msgs = _currentMessages.value.toMutableList()
    val msgIdx = msgs.indexOfLast { it.toolCalls.any { t -> t.id == toolId } }
    if (msgIdx >= 0) {
      val msg = msgs[msgIdx]
      val updatedTools = msg.toolCalls.map { t ->
        if (t.id == toolId) t.copy(
          status = ToolStatus.COMPLETED,
          outputJson = "{\"status\": \"APPROVED_AND_EXECUTED\"}",
          isExpanded = false
        ) else t
      }
      val updatedMsg = msg.copy(toolCalls = updatedTools)
      viewModelScope.launch {
        repository.updateMessage(updatedMsg)
      }
    }
  }

  fun rejectTool(toolId: String) {
    val msgs = _currentMessages.value.toMutableList()
    val msgIdx = msgs.indexOfLast { it.toolCalls.any { t -> t.id == toolId } }
    if (msgIdx >= 0) {
      val msg = msgs[msgIdx]
      val updatedTools = msg.toolCalls.map { t ->
        if (t.id == toolId) t.copy(
          status = ToolStatus.ERROR,
          outputJson = "{\"status\": \"REJECTED_BY_USER\"}",
          isExpanded = false
        ) else t
      }
      val updatedMsg = msg.copy(toolCalls = updatedTools)
      viewModelScope.launch {
        repository.updateMessage(updatedMsg)
      }
    }
  }

  fun deleteCurrentConversation() {
    val id = _uiState.value.activeConversationId ?: return
    viewModelScope.launch {
      repository.deleteConversation(id)
      startNewChat()
    }
  }

  fun renameCurrentConversation(newTitle: String) {
    val id = _uiState.value.activeConversationId ?: return
    viewModelScope.launch {
      repository.renameConversation(id, newTitle)
      _uiState.value = _uiState.value.copy(activeConversationTitle = newTitle)
    }
  }

  fun clearAllChats() {
    viewModelScope.launch {
      conversations.value.forEach {
        repository.deleteConversation(it.id)
      }
      startNewChat()
    }
  }

  fun createProject(name: String, description: String, instructions: String) {
    viewModelScope.launch {
      repository.createProject(name, description, instructions)
    }
  }

  fun addProjectFile(projectId: String, file: ProjectFile) {
    viewModelScope.launch {
      repository.addProjectFile(projectId, file)
    }
  }

  fun deleteProjectFile(projectId: String, fileId: String) {
    viewModelScope.launch {
      repository.deleteProjectFile(projectId, fileId)
    }
  }
}
