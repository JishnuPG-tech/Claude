# Claude Code Web & Mobile Architecture

## 1. Overview
Claude Code is Anthropic's agentic engineering tool. In mobile contexts, it surfaces agent activity, real-time tool execution, command approvals, and code inspection.

---

## 2. Key Components

### 2.1 Workspace & Repository Header
- Repository selector: e.g. `anthropic/claude-android` on branch `main`.
- Session status pill: `Active Session` (green pulse) / `Idle`.

### 2.2 Agent Timeline
- Real-time event log:
  - `Thinking...` (expanding internal reasoning).
  - `Bash Execution`: Shell command card displaying command line and output stream.
  - `File Edits`: File diff viewer displaying added lines in soft green and removed lines in soft terracotta.
  - `Approval Required`: High-visibility prompt asking user permission to execute high-impact commands (e.g. `git push`, `rm -rf`, `npm install`). Includes "Approve" (Terracotta) and "Reject" (Secondary) buttons.

### 2.3 Terminal & File Tree
- Terminal console with monospace styling.
- File tree navigator allowing fast browsing of repository sources.
