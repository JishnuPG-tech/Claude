# Claude Component Inventory & Anatomy

## 1. Primary Component Catalog

### 1.1 Top App Bar
- **Anatomy**:
  - Left: Hamburger Menu Icon (48dp touch target) or Back Chevron (`Icons.AutoMirrored.Filled.ArrowBack`).
  - Center: Conversation Title (Serif/Sans, 16sp, truncated with ellipsis) or Claude Logo Star.
  - Right: Action Icons (New Chat Plus, Project Pin, More Options Overflow).
- **Background**: Transparent or `bgMain` with subtle hairline divider upon scrolling.

### 1.2 Navigation Drawer (Sidebar)
- **Header**:
  - Claude Wordmark with Anthropic Star icon.
  - "Start new chat" pill button with terracotta icon and high contrast.
- **Sections**:
  - Section Headers: Uppercase micro-typography ("RECENT", "PROJECTS", "CLAUDE CODE").
  - Conversation List Item: Leading chat bubble icon, title (single line, max 26 chars), trailing options/pin indicator. Active item highlighted with `surfaceSecondary`.
- **Footer**:
  - User Account Card: Rounded avatar with user initials, Display Name ("Jishnu P G"), subscription tier pill ("Pro"), and Settings gear icon.

### 1.3 User Message Bubble
- **Anatomy**:
  - Alignment: Right aligned (`Alignment.End`).
  - Container: Rounded pill/box, background `surfaceSecondary` (`#F0EEE6` light / `#2A2926` dark).
  - Max Width: 85% of viewport.
  - Typography: Sans-serif, 15sp, line-height 22sp.
  - Optional: Attachment chips pinned to top of the bubble.
  - Action Row (on tap / long press): Edit pencil, Copy button.

### 1.4 Assistant Message Container
- **Anatomy**:
  - Alignment: Full width left-aligned (`Alignment.Start`).
  - Container: Flat on canvas (no bubble container), high literary readability.
  - Avatar: Optional Anthropic star icon badge at top-left.
  - Sub-elements:
    - Thinking Card (collapsible block with duration badge).
    - Tool Invocation Card (collapsible command / execution view).
    - Markdown content (H1-H3, code blocks, lists, quotes, tables).
    - Artifact preview cards (interactive or copyable units).
    - Action bar: Copy to clipboard, Regenerate response, Thumbs up, Thumbs down, Branch / Share.

### 1.5 Claude Message Composer
- **Anatomy**:
  - Floating pill container (radius 26dp), white background in light mode, dark warm grey in dark mode, with 1dp subtle border.
  - Top or Inner Content: Multi-line text field with placeholder "Reply to Claude..." or "What can I help with?".
  - Bottom Controls Row:
    - Left: Attachment Plus / Paperclip button (opens picker sheet for Camera, Photos, Files).
    - Left-Center: Model Selector Pill (e.g., "Claude 3.7 Sonnet" with chevron down).
    - Right: Voice input microphone button.
    - Far-Right: Send / Stop button (Terracotta circle with upward arrow, morphs to black square stop button during generation).

### 1.6 Thinking Block (Claude 3.7 Extended Thinking)
- **Anatomy**:
  - Border: 1dp solid `borderSubtle`.
  - Background: `surfaceContainer`.
  - Header: Sparkle / Brain icon, "Thinking..." (with timer during streaming, e.g. "Thought for 8s"), and expand/collapse chevron.
  - Expanded Body: Monospace or muted serif typography rendering internal cognitive scratchpad.

### 1.7 Tool Execution Block (MCP & Function Calling)
- **Anatomy**:
  - Container: 12dp rounded card with subtle accent border.
  - Header: Terminal / Web / Database icon, Tool Name (`mcp__database_query`, `read_file`, `web_search`), Status pill (Running / Completed / Approval Required).
  - Content: Input payload JSON or query string, and collapsible output preview.

### 1.8 Artifact Container
- **Anatomy**:
  - Header: Document / Code icon, Artifact Title, Language / Type pill, "Open" chevron or Fullscreen button.
  - Body: Code viewer with line numbers and syntax highlighting or rendered visual preview.
  - Footer: Copy Code button, Download/Share button.
