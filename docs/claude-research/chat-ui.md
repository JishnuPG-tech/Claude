# Claude Chat UI & Message Engine

## 1. Message Stream Architecture
Conversations in Claude are composed of alternating turns between User and Assistant, with rich structured nodes:
- `Message`:
  - `id`: Unique UUID
  - `role`: `USER` or `ASSISTANT`
  - `timestamp`: Epoch milliseconds
  - `content`: Formatted text with full markdown support
  - `attachments`: List of files/images attached to the turn
  - `thinking`: Optional extended thinking block (with duration in seconds, status `THINKING` / `COMPLETED`, content)
  - `toolCalls`: List of tool invocations (`name`, `input`, `output`, `status`)
  - `artifacts`: List of generated artifacts (`title`, `language`, `code`, `type`)
  - `model`: Associated model name (e.g., "Claude 3.7 Sonnet")

---

## 2. Markdown & Rich Content Presentation
Claude renders markdown with literary elegance:
- **Headings**: Serif bold, generous margin top (16dp), subtle bottom margin (6dp).
- **Paragraphs**: Serif body with 1.5x line-height.
- **Code Blocks**:
  - Rounded container (8dp radius) with language banner at top and one-tap Copy button.
  - Monospace font with dark syntax background (`#1D1C1A` or `#F3F1EB`).
- **Lists**: Clean bullet points or numbered counters with crisp indentation.
- **Tables**: Horizontal scrollable card with zebra-striping and clear borders.
- **Blockquotes**: Left border (3dp terracotta or olive gray) with 12dp left padding and italicized serif text.

---

## 3. Streaming & Interaction Mechanics
- **Streaming State**: Real-time token appending with pulsing cursor `█`.
- **Autoscroll**: Animated scroll to bottom while new tokens stream, pausing if user touches or scrolls away.
- **Stop Button**: Tapping the black stop button immediately halts streaming while preserving partial output.
- **Message Action Bar**: Underneath assistant replies:
  - Copy to clipboard (with checkmark confirmation toast).
  - Thumbs up / Thumbs down feedback.
  - Retry / Regenerate response.
  - Share message.
