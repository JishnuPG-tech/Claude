# Claude Projects Specification

## 1. Architecture & Concept
Projects in Claude provide persistent memory, project-specific custom instructions, and a shared knowledge base (files, docs, code) across a cluster of conversations.

---

## 2. Project UI Components

### 2.1 Project List View
- Header: "Projects" with "Create Project" plus button.
- Card items:
  - Custom project icon / colored badge.
  - Project title (e.g., "Android App Architecture").
  - Description summary.
  - Metadata row: "4 files · 6 conversations · Updated 2h ago".

### 2.2 Project Detail View
- **Tab Navigation**:
  - `Chats`: Conversations created specifically within this project context.
  - `Knowledge`: Files, documents, guidelines attached to the project.
  - `Instructions`: Custom prompt injected as the system persona.
- **Knowledge Base File Manager**:
  - File list with file icon, size, and deletion action.
  - "Add Content" button (Upload Document, Paste Text).
