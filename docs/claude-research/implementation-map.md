# Claude Android Implementation Map

## 1. Package Architecture
All code will reside under clean modular packages in `com.example`:
- `com.example.ui.theme`:
  - `Color.kt`: Semantic Claude light/dark tokens (`ClaudeBgMain`, `ClaudeTerracotta`, etc.).
  - `Type.kt`: Literary Serif pairings, clean Sans-Serif and Monospace scales.
  - `Theme.kt`: `ClaudeTheme` with Local Claude color providers.
- `com.example.ui.components`:
  - `ClaudeTopAppBar.kt`: Header with menu, title, model chip, action buttons.
  - `ClaudeComposer.kt`: Floating rounded pill with expandable input, attachments tray, model selector, mic, send/stop button.
  - `ClaudeMessageItem.kt`: User message pill, assistant markdown renderer, message action bar.
  - `ClaudeThinkingBlock.kt`: Collapsible reasoning container with elapsed duration badge.
  - `ClaudeToolCard.kt`: MCP and tool execution view with inputs/outputs/status.
  - `ClaudeArtifactCard.kt`: Artifact summary and interactive full viewer.
  - `ClaudeDrawer.kt`: Navigation drawer with recent chats, projects, search, settings, profile.
- `com.example.ui.screens`:
  - `HomeScreen.kt`: Welcome hero, prompt suggestions, composer.
  - `ChatScreen.kt`: Conversational stream with real-time streaming, thinking, tools.
  - `ProjectsScreen.kt`: Project list, instructions, files, knowledge base.
  - `ClaudeCodeScreen.kt`: Agent activity, terminal logs, diff viewer, command approvals.
  - `SettingsScreen.kt`: Appearance, models, extended thinking tokens, voice styles.
  - `VoiceScreen.kt`: Minimalist voice session with animated audio orb.
  - `SearchScreen.kt`: Real-time chat & project search.
  - `ArtifactViewerScreen.kt`: Fullscreen artifact inspector.
- `com.example.domain`:
  - `model/`: Data classes for `Message`, `Conversation`, `Project`, `Artifact`, `ToolCall`, `ClaudeModel`.
  - `ai/`:
    - `AIProvider.kt`: Abstract interface for streaming AI responses.
    - `HermesAdapter.kt`: Dedicated Hermes Agent bridge supporting events (`message_start`, `message_delta`, `thinking_delta`, `tool_start`, `tool_result`, `approval_required`, etc.).
    - `ClaudeStreamingEngine.kt`: Reactive streaming engine simulating or orchestrating live streaming tokens, thinking processes, and tool dispatches.
- `com.example.data`:
  - `ConversationRepository.kt`: Local persistence for conversations, messages, projects, and settings.
