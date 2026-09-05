package com.example.domain.model

enum class MessageRole {
  USER,
  ASSISTANT,
  SYSTEM,
}

enum class ThinkingStatus {
  THINKING,
  COMPLETED,
}

enum class ToolStatus {
  RUNNING,
  COMPLETED,
  ERROR,
  APPROVAL_REQUIRED,
}

enum class ArtifactType {
  CODE,
  MARKDOWN,
  HTML,
  SVG,
}

data class Attachment(
  val id: String,
  val name: String,
  val sizeBytes: Long,
  val mimeType: String,
  val previewUrl: String? = null,
)

data class ThinkingData(
  val content: String,
  val durationSeconds: Int = 0,
  val status: ThinkingStatus = ThinkingStatus.COMPLETED,
  val isExpanded: Boolean = false,
)

data class ToolCallData(
  val id: String,
  val name: String,
  val inputJson: String,
  val outputJson: String? = null,
  val status: ToolStatus = ToolStatus.COMPLETED,
  val isExpanded: Boolean = false,
)

data class ArtifactData(
  val id: String,
  val title: String,
  val language: String,
  val code: String,
  val type: ArtifactType = ArtifactType.CODE,
)

data class Message(
  val id: String,
  val conversationId: String,
  val role: MessageRole,
  val content: String,
  val timestamp: Long = System.currentTimeMillis(),
  val attachments: List<Attachment> = emptyList(),
  val thinking: ThinkingData? = null,
  val toolCalls: List<ToolCallData> = emptyList(),
  val artifacts: List<ArtifactData> = emptyList(),
  val modelName: String? = null,
  val isStreaming: Boolean = false,
)

data class Conversation(
  val id: String,
  val title: String,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  val lastMessagePreview: String = "",
  val isPinned: Boolean = false,
  val projectId: String? = null,
)

data class Project(
  val id: String,
  val name: String,
  val description: String,
  val customInstructions: String = "",
  val filesCount: Int = 0,
  val chatsCount: Int = 0,
  val updatedAt: Long = System.currentTimeMillis(),
)

data class ProjectFile(
  val id: String,
  val projectId: String,
  val name: String,
  val sizeText: String,
  val content: String = "",
)

data class ClaudeModel(
  val id: String,
  val displayName: String,
  val shortName: String,
  val effortLevel: String = "Low",
  val description: String,
  val tierBadge: String? = null,
  val supportsThinking: Boolean = true,
  val isDefault: Boolean = false,
)

val AvailableClaudeModels = listOf(
  ClaudeModel(
    id = "claude-fable-5-1",
    displayName = "Fable 5.1",
    shortName = "Fable 5.1",
    effortLevel = "Max",
    description = "For your toughest challenges",
    tierBadge = "Pro or Max",
    supportsThinking = true,
    isDefault = false,
  ),
  ClaudeModel(
    id = "claude-opus-5",
    displayName = "Opus 5",
    shortName = "Opus 5",
    effortLevel = "High",
    description = "For complex tasks",
    tierBadge = "Pro",
    supportsThinking = true,
    isDefault = false,
  ),
  ClaudeModel(
    id = "claude-sonnet-5",
    displayName = "Sonnet 5",
    shortName = "Sonnet 5",
    effortLevel = "Low",
    description = "Most efficient for everyday tasks",
    tierBadge = null,
    supportsThinking = true,
    isDefault = true,
  ),
  ClaudeModel(
    id = "claude-haiku-4-5",
    displayName = "Haiku 4.5",
    shortName = "Haiku 4.5",
    effortLevel = "Fast",
    description = "Fastest for quick answers",
    tierBadge = null,
    supportsThinking = false,
    isDefault = false,
  ),
)

data class ClaudeAppSettings(
  val defaultModelId: String = "claude-sonnet-5",
  val thinkingEnabled: Boolean = true,
  val thinkingBudgetTokens: Int = 16000,
  val themeMode: String = "System", // System, Light, Dark
  val fontStyle: String = "Default", // Default, Serif, Mono
  val hapticFeedback: Boolean = true,
  val voiceName: String = "Airy", // Buttery, Airy, Mellow, Glassy, Rounded
  val autoReadResponses: Boolean = false,
  val userName: String = "Jishnu",
  val userEmail: String = "jishnupg2005@gmail.com",
  val planName: String = "Free",
)
