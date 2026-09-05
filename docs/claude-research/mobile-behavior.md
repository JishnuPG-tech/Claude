# Claude Mobile & Android Native Adaptation

## 1. Native Android Ergonomics

### 1.1 Edge-to-Edge System Bars
- **Full Bleed**: The app leverages `enableEdgeToEdge()` with transparent status bar and navigation bar.
- **Top Safe Area**: The top header is padded by `WindowInsets.statusBars` or `safeDrawing.only(WindowInsetsSides.Top)`.
- **Bottom Safe Area & IME**: The message composer dynamically reacts to the virtual keyboard using `WindowInsets.ime` and `WindowInsets.navigationBars`, keeping the active input comfortably above user thumbs without jank.

---

## 2. Touch Targets & Gesture Affordances
- **Minimum Interactive Size**: Every interactive button, pill, toggle, or action icon has an enforced minimum touch footprint of **48dp x 48dp** using `Modifier.minimumInteractiveComponentSize()`.
- **Back Gesture Handling**:
  - If Navigation Drawer is open → back gesture closes drawer.
  - If Bottom Sheet (Model selector, Project files, Tool inspector, Settings) is open → back gesture dismisses sheet.
  - If inside a conversation → back gesture navigates back to Home or toggles sidebar.
  - If inside Claude Code workspace or Search → back returns to main conversation.

---

## 3. Keyboard & Autoscroll Behavior
- **Smart Scroll Lock**: When user is at the bottom of the conversation and streaming tokens arrive, the `LazyListState` smoothly autoscrolls to the newest token (`animateScrollToItem(lastIndex)`).
- **User Breakout**: If the user manually scrolls up during streaming to inspect earlier text, autoscroll locks off so the user's reading position is never violently hijacked.
- **Composer Expandability**: Composer starts at single-line height (approx 52dp) and expands up to 5 lines (approx 140dp) before enabling internal scrolling.

---

## 4. Adaptive Layouts (Phone, Foldable & Tablet)
- **Compact Viewport (< 600dp width)**:
  - Slide-over Modal Navigation Drawer (`ModalNavigationDrawer`).
  - Single column conversation stream.
  - Full-width composer with floating pill aesthetics.
- **Expanded / Tablet Viewport (> 600dp width)**:
  - Permanent side navigation rail or dual-pane layout (History & Projects list on left, Active Chat on right).
  - Max container width for text bubbles clamped to 680dp (`Modifier.widthIn(max = 680.dp)`) to prevent awkward wide-screen stretching.
