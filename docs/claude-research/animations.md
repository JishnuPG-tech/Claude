# Claude Animation & Transition System

## 1. Motion Philosophy
Motion in Claude is calm, deliberate, and non-distracting. Animations provide visual clarity regarding AI state changes (streaming, thinking, tool calls, and completions) without excessive bouncing or flashy effects.

---

## 2. Animation Specs & Timings

### 2.1 Message Streaming
- **Type**: Progressive text token appending with smooth autoscroll lock.
- **Blinking Cursor**: Terracotta or ink block (`█` or subtle vertical bar `|`), pulsing at 500ms intervals with smooth alpha animation (1.0 to 0.2).
- **Streaming Speed**: Natural token flow (15-30ms per token), simulating active cognitive generation.

### 2.2 Thinking Indicator
- **State Transition**: Collapsed pill to expanded card.
- **Duration**: 280ms using `tween(280, easing = FastOutSlowInEasing)`.
- **Icon Animation**: Subtle rotating sparkle / glowing shimmer pulse (opacity 0.6 -> 1.0 -> 0.6, cycle duration 1400ms).
- **Timer**: Real-time counter showing "Thought for Xs" upon completion.

### 2.3 Send Button Morphing
- **Default State**: Terracotta circle with white upward arrow (`Icons.Default.ArrowUpward`).
- **Streaming State**: Dark charcoal / black circle with white filled stop square (10dp x 10dp).
- **Transition**: Crossfade / scale-in (`scaleIn(tween(180)) + fadeIn()`).

### 2.4 Navigation Drawer Reveal
- **Duration**: 300ms.
- **Easing**: `FastOutSlowInEasing`.
- **Scrim**: Smooth black fade with max alpha 0.45.

### 2.5 Bottom Sheet & Modal Dialogs
- **Slide-in**: `slideInVertically(initialOffsetY = { it })` with 320ms spring or decelerate easing.
- **Dismiss**: `slideOutVertically(targetOffsetY = { it })` with 250ms accelerated easing.

### 2.6 Tool Call Execution Pulse
- **Running State**: Amber / terracotta subtle pulse border or progress indicator (`LinearProgressIndicator`).
- **Completed State**: Smooth color morph to soft green checkmark (`Icons.Default.CheckCircle`).
