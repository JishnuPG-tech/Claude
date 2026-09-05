# Claude Spacing & Dimensions Specification

## 1. Spatial Baseline Grid
All measurements follow an 8dp progressive scale, with 4dp used for micro-spacing:
- `space-2xs`: 2dp (Hairline offset, badge badge padding)
- `space-xs`: 4dp (Inner chip padding, icon-to-label gap)
- `space-sm`: 8dp (Inner button padding, compact item gaps)
- `space-md`: 12dp (Paragraph spacing, card internal padding, list item gaps)
- `space-lg`: 16dp (Screen horizontal margin, message exchange separation)
- `space-xl`: 20dp (Top bar padding, drawer section header spacing)
- `space-2xl`: 24dp (Hero margins, dialog internal padding)
- `space-3xl`: 32dp (Empty state top padding, section breaks)
- `space-4xl`: 48dp (Welcome greeting hero spacing)

---

## 2. Component Dimensions & Padding Table

| Component | Dimensions / Min Height | Padding | Corner Radius |
|---|---|---|---|
| **Top App Bar** | Height: 56dp | Horizontal: 12dp, Vertical: 8dp | None (flat) |
| **Drawer Container** | Width: 300dp (or 80% screen width) | Horizontal: 16dp, Top: Insets, Bottom: Insets | 0dp right edge or 16dp subtle |
| **Start New Chat Button** | Height: 44dp, full drawer width | Horizontal: 16dp, Vertical: 10dp | 22dp (pill) |
| **User Message Bubble** | Max width: 85% screen width | Horizontal: 16dp, Vertical: 12dp | 18dp (rounded capsule) |
| **Assistant Message Container** | Full width (100%) | Horizontal: 16dp, Vertical: 8dp | 0dp (sits directly on canvas) |
| **Composer Container** | Min Height: 52dp, Max Height: 180dp | Horizontal: 14dp, Vertical: 8dp | 26dp (pill container) |
| **Send / Stop Button** | 36dp x 36dp (Touch target: 48dp) | Internal icon: 20dp | Circle (50%) or 18dp radius |
| **Model Selection Pill** | Height: 30dp | Horizontal: 10dp, Vertical: 4dp | 15dp (pill) |
| **Attachment Icon Button** | 36dp x 36dp (Touch target: 48dp) | Internal icon: 22dp | Circle or 10dp |
| **Thinking Container** | Full width or inline card | Horizontal: 12dp, Vertical: 8dp | 10dp rounded rect |
| **Tool Execution Card** | Full width | Horizontal: 14dp, Vertical: 10dp | 12dp rounded rect |
| **Artifact Floating Card** | Full width | Horizontal: 14dp, Vertical: 12dp | 14dp rounded rect |
| **Bottom Sheet** | Height: Adaptive (30% - 90% screen) | Horizontal: 20dp, Vertical: 16dp | Top corners: 24dp |
| **Action Icon Buttons** | 32dp x 32dp (Touch target: 48dp) | Internal icon: 18dp | Circle or 8dp |

---

## 3. Screen Edge & Safe Area Relationships
- **Top Inset**: Respects status bar + cutout (`WindowInsets.statusBars`).
- **Bottom Inset**: Respects navigation bar + IME (`WindowInsets.navigationBars`, `WindowInsets.ime`). The composer floats effortlessly above the keyboard when focused.
- **Side Margins**: 16dp minimum breathing room on left and right edges.
