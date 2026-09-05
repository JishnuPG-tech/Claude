package com.example.domain.interfaces

import com.example.domain.model.Attachment
import com.example.domain.model.ClaudeModel
import com.example.domain.model.Conversation
import com.example.domain.model.Message
import com.example.domain.model.Project
import com.example.domain.model.ProjectFile
import kotlinx.coroutines.flow.Flow

sealed interface HermesEvent {
  data class MessageStart(val messageId: String, val conversationId: String, val model: String) : HermesEvent
  data class ThinkingStart(val messageId: String) : HermesEvent
  data class ThinkingDelta(val messageId: String, val text: String) : HermesEvent
  data class ThinkingComplete(val messageId: String, val durationSeconds: Int) : HermesEvent
  data class MessageDelta(val messageId: String, val textDelta: String) : HermesEvent
  data class ToolStart(val messageId: String, val toolId: String, val toolName: String, val inputJson: String) : HermesEvent
  data class ToolUpdate(val messageId: String, val toolId: String, val outputDelta: String) : HermesEvent
  data class ToolResult(val messageId: String, val toolId: String, val outputJson: String, val isError: Boolean = false) : HermesEvent
  data class ApprovalRequired(val messageId: String, val toolId: String, val command: String, val riskDescription: String) : HermesEvent
  data class ArtifactProduced(val messageId: String, val artifactId: String, val title: String, val language: String, val code: String) : HermesEvent
  data class MessageComplete(val messageId: String) : HermesEvent
  data class AgentComplete(val messageId: String, val summary: String) : HermesEvent
  data class AgentError(val messageId: String, val errorMessage: String, val isRecoverable: Boolean = true) : HermesEvent
}

interface AIProvider {
  fun streamMessage(
    prompt: String,
    conversationId: String,
    model: ClaudeModel,
    thinkingEnabled: Boolean,
    attachments: List<Attachment> = emptyList(),
  ): Flow<HermesEvent>
}

interface ConversationRepository {
  fun getConversations(): Flow<List<Conversation>>
  fun getConversation(id: String): Flow<Conversation?>
  fun getMessages(conversationId: String): Flow<List<Message>>
  suspend fun saveConversation(conversation: Conversation)
  suspend fun saveMessage(message: Message)
  suspend fun updateMessage(message: Message)
  suspend fun deleteConversation(conversationId: String)
  suspend fun renameConversation(conversationId: String, title: String)
  suspend fun pinConversation(conversationId: String, isPinned: Boolean)
}

interface StreamingService {
  fun streamEvents(events: Flow<HermesEvent>): Flow<HermesEvent>
}

interface ToolService {
  suspend fun executeTool(toolName: String, inputJson: String): String
  suspend fun approveTool(toolCallId: String, approved: Boolean)
}

interface FileService {
  suspend fun uploadAttachment(name: String, bytes: ByteArray, mimeType: String): Attachment
  suspend fun deleteAttachment(attachmentId: String)
}

interface ProjectService {
  fun getProjects(): Flow<List<Project>>
  fun getProject(id: String): Flow<Project?>
  fun getProjectFiles(projectId: String): Flow<List<ProjectFile>>
  suspend fun createProject(name: String, description: String, instructions: String): Project
  suspend fun addProjectFile(projectId: String, file: ProjectFile)
  suspend fun deleteProjectFile(projectId: String, fileId: String)
}

interface MemoryService {
  suspend fun getProjectMemory(projectId: String): String
  suspend fun updateMemory(projectId: String, key: String, value: String)
}

interface AgentService {
  fun getClaudeCodeEvents(): Flow<HermesEvent>
  suspend fun submitCommandApproval(commandId: String, approved: Boolean)
}
