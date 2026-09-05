package com.example.data

import com.example.domain.interfaces.ConversationRepository
import com.example.domain.interfaces.ProjectService
import com.example.domain.model.ArtifactData
import com.example.domain.model.ArtifactType
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class ConversationRepositoryImpl : ConversationRepository, ProjectService {

  private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
  private val _messages = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
  private val _projects = MutableStateFlow<List<Project>>(emptyList())
  private val _projectFiles = MutableStateFlow<Map<String, List<ProjectFile>>>(emptyMap())

  init {
    seedInitialData()
  }

  private fun seedInitialData() {
    val pinnedConvId = "c_pinned_1"
    val rec1Id = "c_rec_1"
    val rec2Id = "c_rec_2"
    val rec3Id = "c_rec_3"
    val rec4Id = "c_rec_4"
    val rec5Id = "c_rec_5"

    val initialConversations = listOf(
      Conversation(
        id = pinnedConvId,
        title = "Resume optimization for ATS score i...",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 60 * 12,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 30,
        lastMessagePreview = "To optimize your resume for ATS algorithms, ensure standard section headings...",
        isPinned = true,
      ),
      Conversation(
        id = rec1Id,
        title = "Extracting Claude's UI/UX design doc...",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 45,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 5,
        lastMessagePreview = "Claude's mobile design language centers around minimal, distraction-free surfaces...",
        isPinned = false,
      ),
      Conversation(
        id = rec2Id,
        title = "Clone and convert Hugging Face spa...",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 120,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 60,
        lastMessagePreview = "Here is the step-by-step method to clone the Gradio space repository and convert it...",
        isPinned = false,
      ),
      Conversation(
        id = rec3Id,
        title = "Greeting",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 60 * 3,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 60 * 2,
        lastMessagePreview = "Hello! How can I assist you today?",
        isPinned = false,
      ),
      Conversation(
        id = rec4Id,
        title = "Modern UI design with dark/light mo...",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 60 * 6,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 60 * 4,
        lastMessagePreview = "Using dynamic color tokens and high-contrast charcoal surfaces ensures readability...",
        isPinned = false,
      ),
      Conversation(
        id = rec5Id,
        title = "APK reverse engineering...",
        createdAt = System.currentTimeMillis() - 1000 * 60 * 60 * 24,
        updatedAt = System.currentTimeMillis() - 1000 * 60 * 60 * 18,
        lastMessagePreview = "When analyzing Android bytecode with Jadx, focus on exported components and manifest permissions...",
        isPinned = false,
      ),
    )

    val pinnedMessages = listOf(
      Message(
        id = "m_pin_1",
        conversationId = pinnedConvId,
        role = MessageRole.USER,
        content = "How can I optimize my software engineer resume for ATS systems?",
        timestamp = System.currentTimeMillis() - 1000 * 60 * 35,
      ),
      Message(
        id = "m_pin_2",
        conversationId = pinnedConvId,
        role = MessageRole.ASSISTANT,
        content = """
          Here is a prioritized guide to maximizing your ATS compatibility:

          1. **Format Cleanly**: Use standard single-column layout with consistent margins. Avoid tables or complex floating text boxes that trip up parsers.
          2. **Exact Keyword Alignment**: Mirror exact terms from the job listing (e.g. "Kotlin Coroutines", "Jetpack Compose", "Clean Architecture").
          3. **Impact Metrics**: Structure bullet points around quantifiable results: *Achieved [X] measured by [Y] by doing [Z]*.
        """.trimIndent(),
        timestamp = System.currentTimeMillis() - 1000 * 60 * 30,
        modelName = "Claude 3.7 Sonnet",
      )
    )

    val rec1Messages = listOf(
      Message(
        id = "m_rec1_1",
        conversationId = rec1Id,
        role = MessageRole.USER,
        content = "Can you extract Claude's core UI/UX design specifications from the mobile app?",
        timestamp = System.currentTimeMillis() - 1000 * 60 * 10,
      ),
      Message(
        id = "m_rec1_2",
        conversationId = rec1Id,
        role = MessageRole.ASSISTANT,
        content = """
          Here are Claude's distinctive mobile design specifications:

          - **Color Palette**: Pitch dark `#141413` canvas, rounded dark floating card `#1E1D1C`, terracotta `#D97757` asterisk emblem, and lavender `#A78BFA` Pro accent.
          - **Typography**: Editorial Serif headings paired with sans-serif UI elements.
          - **Composer**: Floating rounded capsule with Upgrade strip, Sonnet 5 Low model chip, and white speech waveform action button.
        """.trimIndent(),
        timestamp = System.currentTimeMillis() - 1000 * 60 * 5,
        modelName = "Claude 3.7 Sonnet",
      )
    )

    _conversations.value = initialConversations
    _messages.value = mapOf(
      pinnedConvId to pinnedMessages,
      rec1Id to rec1Messages
    )

    val p1 = Project(
      id = "p_1",
      name = "Claude Android Client",
      description = "Building a high-fidelity native Android reproduction of the Claude mobile app experience.",
      customInstructions = "Always prioritize clean typography, warm parchment aesthetics, and edge-to-edge responsiveness.",
      filesCount = 3,
      chatsCount = 4,
      updatedAt = System.currentTimeMillis() - 1000 * 60 * 60 * 2,
    )

    val p2 = Project(
      id = "p_2",
      name = "Hermes Agent Research",
      description = "Orchestrating agent workflows, streaming events, and tool approval interfaces.",
      customInstructions = "Format all command proposals with explicit risk descriptions and rollback steps.",
      filesCount = 2,
      chatsCount = 2,
      updatedAt = System.currentTimeMillis() - 1000 * 60 * 60 * 24,
    )

    _projects.value = listOf(p1, p2)
    _projectFiles.value = mapOf(
      p1.id to listOf(
        ProjectFile("f_1", p1.id, "design-system.md", "14 KB", "# Design System Spec"),
        ProjectFile("f_2", p1.id, "tokens.json", "3 KB", "{ \"color\": \"#D97757\" }"),
        ProjectFile("f_3", p1.id, "architecture.md", "22 KB", "# Clean Architecture"),
      ),
      p2.id to listOf(
        ProjectFile("f_4", p2.id, "hermes-events.proto", "8 KB", "// Protobuf specs"),
        ProjectFile("f_5", p2.id, "agent-loop.py", "12 KB", "# Agent dispatch loop"),
      ),
    )
  }

  override fun getConversations(): Flow<List<Conversation>> = _conversations.asStateFlow()

  override fun getConversation(id: String): Flow<Conversation?> = _conversations.map { list ->
    list.find { it.id == id }
  }

  override fun getMessages(conversationId: String): Flow<List<Message>> = _messages.map { map ->
    map[conversationId] ?: emptyList()
  }

  override suspend fun saveConversation(conversation: Conversation) {
    val current = _conversations.value.toMutableList()
    val index = current.indexOfFirst { it.id == conversation.id }
    if (index >= 0) {
      current[index] = conversation
    } else {
      current.add(0, conversation)
    }
    _conversations.value = current
  }

  override suspend fun saveMessage(message: Message) {
    val currentMap = _messages.value.toMutableMap()
    val list = currentMap[message.conversationId]?.toMutableList() ?: mutableListOf()
    val index = list.indexOfFirst { it.id == message.id }
    if (index >= 0) {
      list[index] = message
    } else {
      list.add(message)
    }
    currentMap[message.conversationId] = list
    _messages.value = currentMap

    // Also update conversation preview and timestamp
    val convs = _conversations.value.toMutableList()
    val convIdx = convs.indexOfFirst { it.id == message.conversationId }
    if (convIdx >= 0) {
      val c = convs[convIdx]
      convs[convIdx] = c.copy(
        updatedAt = message.timestamp,
        lastMessagePreview = message.content.take(60)
      )
      _conversations.value = convs
    }
  }

  override suspend fun updateMessage(message: Message) {
    saveMessage(message)
  }

  override suspend fun deleteConversation(conversationId: String) {
    _conversations.value = _conversations.value.filterNot { it.id == conversationId }
    val currentMap = _messages.value.toMutableMap()
    currentMap.remove(conversationId)
    _messages.value = currentMap
  }

  override suspend fun renameConversation(conversationId: String, title: String) {
    val current = _conversations.value.toMutableList()
    val index = current.indexOfFirst { it.id == conversationId }
    if (index >= 0) {
      current[index] = current[index].copy(title = title)
      _conversations.value = current
    }
  }

  override suspend fun pinConversation(conversationId: String, isPinned: Boolean) {
    val current = _conversations.value.toMutableList()
    val index = current.indexOfFirst { it.id == conversationId }
    if (index >= 0) {
      current[index] = current[index].copy(isPinned = isPinned)
      _conversations.value = current
    }
  }

  override fun getProjects(): Flow<List<Project>> = _projects.asStateFlow()

  override fun getProject(id: String): Flow<Project?> = _projects.map { list ->
    list.find { it.id == id }
  }

  override fun getProjectFiles(projectId: String): Flow<List<ProjectFile>> = _projectFiles.map { map ->
    map[projectId] ?: emptyList()
  }

  override suspend fun createProject(name: String, description: String, instructions: String): Project {
    val newP = Project(
      id = "p_" + UUID.randomUUID().toString().take(8),
      name = name,
      description = description,
      customInstructions = instructions,
      filesCount = 0,
      chatsCount = 0,
      updatedAt = System.currentTimeMillis(),
    )
    _projects.value = listOf(newP) + _projects.value
    return newP
  }

  override suspend fun addProjectFile(projectId: String, file: ProjectFile) {
    val currentMap = _projectFiles.value.toMutableMap()
    val list = currentMap[projectId]?.toMutableList() ?: mutableListOf()
    list.add(file)
    currentMap[projectId] = list
    _projectFiles.value = currentMap

    // update count
    val projs = _projects.value.toMutableList()
    val idx = projs.indexOfFirst { it.id == projectId }
    if (idx >= 0) {
      projs[idx] = projs[idx].copy(filesCount = list.size, updatedAt = System.currentTimeMillis())
      _projects.value = projs
    }
  }

  override suspend fun deleteProjectFile(projectId: String, fileId: String) {
    val currentMap = _projectFiles.value.toMutableMap()
    val list = currentMap[projectId]?.filterNot { it.id == fileId } ?: emptyList()
    currentMap[projectId] = list
    _projectFiles.value = currentMap

    val projs = _projects.value.toMutableList()
    val idx = projs.indexOfFirst { it.id == projectId }
    if (idx >= 0) {
      projs[idx] = projs[idx].copy(filesCount = list.size, updatedAt = System.currentTimeMillis())
      _projects.value = projs
    }
  }
}
