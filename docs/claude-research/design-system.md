# Claude Design System Specification

## 1. Executive Summary & Brand Identity
The Claude mobile and web application design philosophy is rooted in Anthropic's editorial, warm, humanist, and intellectually rigorous aesthetic. Unlike contemporary chat interfaces dominated by cold blues, neon purples, and high-gloss gradients, Claude features:
- **Warm Parchment / Cream Canvas**: A natural, paper-like background that evokes physical literature, clarity, and sustained reading comfort.
- **Terracotta Accent (Clay/Crail)**: A signature burnt orange/terracotta tone (`#D97757` / `#C15F3C`) representing thoughtfulness, warmth, and craft.
- **Editorial Typography Pairing**: Literary serif typography for headings, greetings, and long-form narrative output paired with a crisp, geometric sans-serif for UI controls, labels, and timestamps.
- **Restrained Elevation & Tactility**: Subtle 1px borders with warm alpha blends instead of harsh drop shadows, creating a calm, flat-yet-tactile card hierarchy.
- **Natural Interaction Motion**: Smooth spring dampening (300ms) with gentle easing for drawer reveals, bottom sheets, and expandable thinking blocks.

---

## 2. Core Visual Foundations

### 2.1 Theming Paradigm (Light & Dark)
Claude offers two primary palettes, each calibrated for high legibility, low eye-strain, and distinct contrast:

| Token | Light Theme | Dark Theme | Semantic Role |
|---|---|---|---|
| `bg-main` | `#FAF9F5` (Warm Cream) | `#181816` (Deep Charcoal) | Primary canvas background |
| `surface-primary` | `#FFFFFF` (Pure White) | `#22211F` (Dark Warm Grey) | Cards, composer box, popovers |
| `surface-secondary` | `#F0EEE6` (Parchment Muted) | `#2A2926` (Muted Charcoal) | User message pill, button hover |
| `surface-tertiary` | `#E8E5DC` (Border Wash) | `#35332F` (Deep Border Wash) | Code headers, subtle active tabs |
| `border-subtle` | `#E5E2D9` (Hairline Warm) | `#33312D` (Hairline Dark) | 1dp card borders & divider rules |
| `border-strong` | `#D1CDBF` (Defined Rule) | `#45423C` (Defined Rule Dark) | Focused input border, card outlines |
| `accent-terracotta`| `#D97757` (Anthropic Orange) | `#DA7756` (Vibrant Terracotta) | Send buttons, active pills, logo star |
| `accent-hover` | `#C15F3C` (Deep Terracotta) | `#E68A6B` (Light Terracotta) | Pressed states, active highlights |
| `text-primary` | `#1D1C16` (Near Black Ink) | `#ECEAE4` (Crisp Off-White) | Titles, headings, main response text |
| `text-secondary` | `#636159` (Warm Olive Grey) | `#A3A096` (Muted Warm Grey) | Timestamps, model badges, subtitles |
| `text-tertiary` | `#969389` (Subtle Grey) | `#747169` (Dim Hint Grey) | Placeholder text, disabled labels |
| `code-bg` | `#F3F1EB` (Code Paper) | `#1D1C1A` (Code Night) | Markdown code blocks & inline tokens |

---

## 3. Spatial & Grid System
Claude utilizes an 8dp progressive grid system with 4dp fractional increments for tight touch affordances:
- **Margins**: 16dp horizontal on standard mobile viewports, expanding to 24dp on tablets and foldables.
- **Vertical Rhythm**: 12dp between conversational exchanges, 8dp between text and action buttons, 16dp between message groups.
- **Touch Target Floor**: 48dp minimum for all interactive icons and button surfaces.

---

## 4. Radii & Corner Geometry
- **Composer Pill**: 26dp (fully rounded organic capsule)
- **User Message Pill**: 18dp (gentle conversational bubble)
- **Tool / Thinking Cards**: 12dp with 1dp borders
- **Buttons & Chips**: 20dp (compact pill style)
- **Bottom Sheets & Dialogs**: 24dp top corner radius

---

## 5. Visual Hierarchy Principles
1. **The Canvas is Sacred**: Content breathes. No unnecessary borders, visual clutter, or promotional confetti.
2. **Human Touch**: Messages feel like thoughtfully typeset literature rather than machine terminals.
3. **Transparent Execution**: Claude's reasoning ("Thinking" and "Tool calls") is clearly delineated through collapsible, subdued containers that invite inspection without disrupting the narrative flow.
