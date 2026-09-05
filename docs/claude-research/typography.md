# Claude Typography Specification

## 1. Font Family System
Claude's typography creates an intellectual, literary personality through two distinct families:
1. **Editorial Serif Font Family**:
   - Web & Print Identity: Tiempos / Copernicus / Lora / Georgia.
   - Usage in App: Screen titles, welcome greetings ("Good afternoon", "What can I help with?"), Claude assistant prose, long-form quotes, and markdown titles (`# H1`, `## H2`).
   - Android Native Strategy: `FontFamily.Serif` with custom weight mappings and elegant letter-spacing to render crisp literary text across all Android versions.
2. **Clean Sans-Serif Font Family**:
   - Web & Mobile Identity: Söhne / Poppins / Inter / System Sans.
   - Usage in App: Navigation headers, drawer item labels, timestamps, button labels, model pills, input text fields, and user bubbles.
   - Android Native Strategy: `FontFamily.SansSerif` with standard Material 3 tracking.
3. **Monospace Font Family**:
   - Usage in App: Code snippets, terminal logs, file trees, diff viewers, and token metrics.
   - Android Native Strategy: `FontFamily.Monospace`.

---

## 2. Type Scale & Hierarchy

| Style Token | Font Family | Size | Weight | Line Height | Tracking | Application |
|---|---|---|---|---|---|---|
| `displayLarge` | Serif | 30sp | SemiBold | 38sp | -0.5sp | Welcome title ("Good afternoon") |
| `displayMedium` | Serif | 24sp | Medium | 32sp | -0.25sp | Project title, Artifact title |
| `headlineLarge` | Serif | 22sp | SemiBold | 28sp | 0sp | Markdown H1, Dialog titles |
| `headlineMedium` | Serif | 18sp | SemiBold | 24sp | 0sp | Markdown H2, Section headers |
| `headlineSmall` | Sans-Serif | 16sp | SemiBold | 22sp | 0.1sp | Markdown H3, Card titles |
| `bodyLarge` | Serif | 16.5sp | Normal | 25sp | 0.15sp | Assistant response body text |
| `bodyMedium` | Sans-Serif | 15sp | Normal | 22sp | 0.1sp | User message bubble, Settings items |
| `bodySmall` | Sans-Serif | 13.5sp | Normal | 18sp | 0.2sp | Metadata, subtitles, file sizes |
| `labelLarge` | Sans-Serif | 14sp | Medium | 20sp | 0.25sp | Primary buttons, tab labels |
| `labelMedium` | Sans-Serif | 12sp | Medium | 16sp | 0.3sp | Model selector chip, thinking timer |
| `labelSmall` | Sans-Serif | 11sp | Medium | 14sp | 0.4sp | Badges (PRO, BETA, MCP) |
| `codeBlock` | Monospace | 13.5sp | Normal | 20sp | 0sp | Code blocks, diff lines, shell output |

---

## 3. Typographic Details & Spacing
- **Line Height for Reading**: Assistant responses feature generous line height (1.52x font size) to ensure sustained long-form comprehension.
- **Paragraph Separation**: 12dp spacing between markdown paragraphs.
- **Lists & Indentation**: 20dp left indentation with custom terracotta bullet markers (`•`) or numbers.
