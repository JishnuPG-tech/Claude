# Claude Complete Screen Inventory & Matrix

## 1. Primary Screens & Routes

### 1.1 Welcome & Authentication Flow
- **Screens**:
  - `WelcomeScreen`: Anthropic Claude logo, literary tagline, "Continue with Google", "Continue with Email", Terms and privacy notice.
  - `EmailAuthScreen`: Clean email input, "Send Magic Link", verification code input screen with 6-digit PIN boxes.
  - `OnboardingScreen`: User name, preferences, usage profile.

### 1.2 Home & Empty Conversation Screen
- **Screen**: `HomeScreen`
- **Layout**:
  - Top Bar: Menu hamburger, Claude Star, Profile / New Chat.
  - Center Hero:
    - Warm terracotta Anthropic star emblem.
    - Serif Headline: "Good afternoon, Jishnu" or "What can I help with?".
    - Prompt Suggestion Chips: "Analyze this image", "Brainstorm product ideas", "Explain quantum computing", "Help write Kotlin code".
  - Bottom: Floating Composer pill.

### 1.3 Active Chat Screen
- **Screen**: `ChatScreen`
- **Layout**:
  - Top Bar: Menu toggle, Conversation Title, Model Pill / Project indicator, Overflow menu (Rename, Delete, Share).
  - Main Body: `LazyColumn` message timeline featuring:
    - User message pills.
    - Assistant message sections with Thinking blocks, Tool invocation cards, Markdown prose, Code blocks, and Artifact cards.
  - Bottom: Sticky floating Composer with keyboard insets support.

### 1.4 Navigation Drawer (Sidebar)
- **Screen**: `DrawerSheet`
- **Contents**:
  - Top: "Start new chat" button with high-contrast pill styling.
  - Search bar shortcut.
  - Grouped lists:
    - Pinned Chats
    - Today
    - Yesterday
    - Previous 7 Days
    - Previous 30 Days
  - Projects section (e.g. "Android Dev", "Research Notes").
  - Claude Code Sessions link.
  - User footer with account info & Settings button.

### 1.5 Projects Screen
- **Screens**:
  - `ProjectsListScreen`: List of all user projects with custom icons, file counts, and descriptions.
  - `ProjectDetailScreen`: Project Instructions, Knowledge Base files (PDFs, Markdown, Code), Project-specific conversations, and Add File button.

### 1.6 Claude Code Workspace Screen
- **Screen**: `ClaudeCodeScreen`
- **Layout**:
  - Workspace selector (repo name, current branch).
  - Terminal & Agent execution tab.
  - File tree browser.
  - Diff / Code review panel.
  - Interactive approval banners ("Approval required for bash execution").

### 1.7 Search & Filter Screen
- **Screen**: `SearchScreen`
- **Layout**:
  - Search text field with instant query filtering.
  - Filter chips: All, Chats, Projects, Artifacts.
  - Results list with keyword highlights and date headers.

### 1.8 Settings Screen & Dialogs
- **Screen**: `SettingsScreen`
- **Sections**:
  - Account: Email, Plan ("Claude Pro"), Usage limits.
  - Appearance: Theme toggle (System, Light, Dark).
  - Model Preferences: Default Model (Claude 3.7 Sonnet, Claude 3.5 Sonnet, Claude 3.5 Haiku), Extended Thinking toggle & token budget slider.
  - Voice Mode: Voice selection (Buttery, Airy, Mellow, Glassy, Rounded), Auto-read responses.
  - Connectors & MCP: Manage tool integrations and permissions.
  - Data & Privacy: Export chats, Delete account, Clear history.

### 1.9 Voice Conversation Screen
- **Screen**: `VoiceModeScreen`
- **Layout**:
  - Full-screen minimal focus mode.
  - Animated pulsing organic orb responding to speech waveforms.
  - Live transcript ticker.
  - Mute, Pause, and End Session controls.
