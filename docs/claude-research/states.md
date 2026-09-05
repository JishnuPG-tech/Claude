# Claude States & Screen Matrix

## 1. Screen & State Matrix

| Screen | State | Visual Representation | User Affordance |
|---|---|---|---|
| `Home` | Initial Empty | Warm cream canvas, literary greeting, 4 suggestion chips, empty composer | Tap suggestion chip or type query |
| `Chat` | Idle | Full conversation history, ready composer | Scroll, copy messages, start typing |
| `Chat` | User Typing | Multiline composer expanding, send button turns terracotta | Tap send |
| `Chat` | Streaming Thinking | Pulsing thinking chip with live duration counter | Expand to view reasoning stream |
| `Chat` | Streaming Text | Incremental token arrival with flashing cursor `█`, stop button active | Tap stop button to halt |
| `Chat` | Tool Invocation | Card showing tool name, spinner, command string | Inspect tool input/output |
| `Chat` | Approval Required | Prominent amber card with command diff and "Approve" / "Reject" | User must tap one to continue |
| `Chat` | Error / Network | Terracotta banner with retry button | Tap retry or edit original prompt |
| `Drawer` | Open | Scrim overlay, pinned chats, grouped history, profile footer | Select chat, start new chat, open settings |
| `Projects` | Overview | Grid/List of project cards with knowledge file counters | Tap project, create project |
| `Claude Code` | Running Task | Live console log, diff viewer, terminal execution stream | Approve command, inspect files |
| `Voice` | Active Session | Pulsing terracotta audio visualizer orb, live transcription | Speak, mute, cancel |
| `Artifact` | Active Viewer | Code viewer with syntax highlight & Preview tab | Copy code, export, close |
