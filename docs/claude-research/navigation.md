# Claude Navigation Architecture

## 1. Navigation Paradigm
Claude mobile operates on a hierarchical stack with a persistent navigation drawer:
- **Primary Level**: `HomeScreen` & `ChatScreen` with drawer toggle (`Icons.Default.Menu`).
- **Drawer Overlay**: Modal drawer sliding from start, containing Chat History, Projects, Claude Code, Search, and Settings.
- **Secondary Modals**:
  - `ModelSelectorBottomSheet`: Select model & toggle Extended Thinking.
  - `AttachmentBottomSheet`: Camera, Photo Library, Documents, Audio.
  - `ArtifactViewerScreen`: Fullscreen viewer for code/preview artifacts.
  - `SettingsScreen`: Account, appearance, preferences.
  - `ProjectsScreen`: Knowledge and workspace management.
  - `ClaudeCodeScreen`: Terminal and agent activity workspace.
  - `VoiceScreen`: Full-bleed live voice orb session.

---

## 2. Deep-Link & State Preserving Routing
Each screen destination is defined as a type-safe route:
- `Route.Home`: Initial empty state with suggestions.
- `Route.Chat(conversationId)`: Active conversation stream.
- `Route.Projects`: Project management hub.
- `Route.ProjectDetail(projectId)`: Project files, instructions, chats.
- `Route.ClaudeCode`: Claude Code terminal and workspace.
- `Route.Settings`: Application preferences.
- `Route.Search`: Global search.
- `Route.Voice`: Voice mode.
