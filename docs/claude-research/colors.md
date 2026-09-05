# Claude Color System Specification

## 1. Color Palette Tokens

### 1.1 Canvas & Surface Colors
Claude's visual foundation is defined by warm parchment tones rather than stark cold whites or saturated grays.

#### Light Mode Palette
- `ClaudeBgMain`: `#FAF9F5` — Core screen background across all views.
- `ClaudeSurface`: `#FFFFFF` — Elevated surfaces (Cards, Dialogs, Composer Box, Bottom Sheets).
- `ClaudeSurfaceVariant`: `#F0EEE6` — User message bubbles, search input bars, secondary buttons.
- `ClaudeSurfaceContainer`: `#E8E5DC` — Tool execution cards, code block headers, inactive chip fills.
- `ClaudeSurfaceHighlight`: `#E4DFD3` — Pressed state for list items and drawer selections.

#### Dark Mode Palette
- `ClaudeBgMainDark`: `#181816` — Deep warm charcoal canvas.
- `ClaudeSurfaceDark`: `#22211F` — Main cards, bottom sheets, composer field container.
- `ClaudeSurfaceVariantDark`: `#2A2926` — User message bubble fill, secondary pill buttons.
- `ClaudeSurfaceContainerDark`: `#35332F` — Tool cards, code blocks, active filter indicators.
- `ClaudeSurfaceHighlightDark`: `#3F3D37` — Hover / press state for drawer and history items.

---

### 1.2 Brand & Semantic Accent Colors
- `ClaudeTerracotta`: `#D97757` — Official Anthropic Terracotta. Used on primary action buttons (Send message), Claude logo icon, Pro badges, active toggles.
- `ClaudeTerracottaDark`: `#C15F3C` — Pressed state and high-contrast variant of Terracotta.
- `ClaudeTerracottaLight`: `#F4EAE6` — Subtle terracotta wash for subtle tags, badges, and active state highlights in light theme.
- `ClaudeTerracottaContainerDark`: `#3E2922` — Warm tinted background in dark theme.

### 1.3 Text & Iconography Colors
- `ClaudeTextPrimary`: `#1D1C16` (Light) / `#ECEAE4` (Dark) — 100% emphasis for titles, headings, and assistant text.
- `ClaudeTextSecondary`: `#636159` (Light) / `#A3A096` (Dark) — 70% emphasis for timestamps, metadata, helper text, and inactive icons.
- `ClaudeTextTertiary`: `#969389` (Light) / `#747169` (Dark) — 45% emphasis for placeholders, disabled controls, and minor borders.
- `ClaudeTextInverse`: `#FFFFFF` (Light) / `#181816` (Dark) — Text on terracotta or high-contrast solid buttons.

### 1.4 System Status & Alert Colors
- `ClaudeSuccess`: `#3B755D` (Forest Green) — Checkmarks, completion badges, copy feedback.
- `ClaudeWarning`: `#C27803` (Warm Amber) — Rate limit warnings, approval required badges.
- `ClaudeError`: `#C53B3B` (Terracotta Red) — Generation failed, network errors, delete actions.
- `ClaudeInfo`: `#4D6B9C` (Muted Slate Blue) — Informational banners and connector tags.

### 1.5 Code Syntax & Artifact Tokens
- `ClaudeCodeBg`: `#F3F1EB` (Light) / `#161514` (Dark) — Code editor background.
- `ClaudeCodeHeader`: `#EBE8E0` (Light) / `#232220` (Dark) — Language banner with copy button.
- `ClaudeKeyword`: `#A13824` / `#E56954` — Syntax keyword orange-red.
- `ClaudeString`: `#3E7259` / `#74B393` — Syntax string soft forest.
- `ClaudeFunction`: `#2B5C8F` / `#6E9ECE` — Syntax function muted cobalt.
- `ClaudeComment`: `#8C897E` / `#6B685F` — Syntax comment warm slate.

---

## 2. Dynamic Theme Mapping
In Compose, these colors map directly to an expressive `ClaudeColorScheme` holding both standard Material 3 tokens and custom Claude semantic tokens (`terracotta`, `surfaceContainer`, `codeBg`, etc.).
