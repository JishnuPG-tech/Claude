# Claude Composer Specification

## 1. Physical Layout & Geometry
The Claude composer is a distinctive organic capsule floating at the bottom of the screen:
- **Shape**: Rounded pill with corner radius 26dp.
- **Background**:
  - Light mode: Solid pure white `#FFFFFF` with 1dp border `#E5E2D9`.
  - Dark mode: Dark charcoal `#22211F` with 1dp border `#33312D`.
- **Elevation**: Subtle 1dp shadow/elevation in light mode to lift it from `#FAF9F5`.

---

## 2. Controls & Interactions

### 2.1 Multi-line Text Area
- Expands dynamically from single-line (min 48dp) to maximum 5 lines (approx 140dp) as user types.
- Hint text: "Reply to Claude..." in ongoing chats, or "How can Claude help you today?" on new chat.

### 2.2 Bottom Actions Row
- **Plus / Attachment Button**:
  - Icon: `Icons.Default.Add` or Paperclip.
  - Tapping opens the Attachment Bottom Sheet (Photos, Files, Camera).
- **Model Selector Pill**:
  - Label: e.g. "Claude 3.7 Sonnet" with trailing down arrow (`Icons.Default.KeyboardArrowDown`).
  - Background: `surfaceSecondary` with 14dp radius.
  - Tapping opens the Model Selection Bottom Sheet.
- **Voice Button**:
  - Microphone icon (`Icons.Default.Mic`).
  - Launches full-screen voice mode.
- **Send / Stop Action Button**:
  - Dimensions: 36dp x 36dp circular button.
  - Idle / Text empty: Subtle muted circle or transparent.
  - Text present: Solid Terracotta `#D97757` circle with crisp white upward arrow (`Icons.Default.ArrowUpward`).
  - Generation active: Dark solid circle with crisp white square stop glyph.

---

## 3. Attachment Previews
When images or documents are selected:
- Horizontal scrolling tray directly above the text field inside the composer pill.
- Miniature thumbnail (48dp x 48dp) with file extension badge and remove `✕` circle icon.
