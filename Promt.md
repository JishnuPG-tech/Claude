You are continuing development of an existing Android project. DO NOT start a new project and DO NOT replace the existing implementation with a generic Claude-like UI.

Your task is to finish and polish the existing Claude Android UI reconstruction until it matches the provided real Claude Android screenshots as closely as technically possible.

==================================================
1. PROJECT
==================================================

Repository:

https://github.com/JishnuPG-tech/Claude.git

The repository already contains the Android project and the current implementation.

The existing implementation is partially complete.

Some screens are already extremely close to the target UI. Some screens are significantly less accurate.

Your job is to inspect the ENTIRE existing project first, understand what already exists, then improve it incrementally.

DO NOT throw away working implementations.

DO NOT rebuild everything from scratch.

DO NOT replace the project with a simplified demo.

Preserve working functionality while correcting visual and behavioral differences.

==================================================
2. PRIMARY VISUAL SOURCE OF TRUTH
==================================================

IMPORTANT:

The repository now contains the actual reference screenshots inside:

https://github.com/JishnuPG-tech/Claude/tree/main/screenshot

The entire `screenshot/` directory is the PRIMARY VISUAL SOURCE OF TRUTH.

These screenshots represent the real target Claude Android UI that this project is reconstructing.

Before changing UI code:

1. Inspect the complete `screenshot/` directory.
2. Open and analyze every screenshot.
3. Identify what screen/state/action each screenshot represents.
4. Determine transitions between screenshots.
5. Compare every screenshot against the current implementation.
6. Record differences before implementing major changes.

DO NOT assume that a screen should look a certain way because it resembles another Claude interface.

The actual screenshots have priority.

If the existing documentation conflicts with the screenshots, follow the screenshots.

If your assumptions conflict with the screenshots, follow the screenshots.

If generic Material 3 styling conflicts with the screenshots, follow the screenshots.

If a previous implementation conflicts with the screenshots, correct the implementation.

DO NOT modify, overwrite, resize, crop, compress, or replace the reference screenshots.

They are immutable reference material.

==================================================
3. SCREENSHOT ANALYSIS REQUIREMENT
==================================================

Create:

docs/SCREENSHOT_STATE_MAP.md

This document must contain one entry for EVERY screenshot in:

screenshot/

For each screenshot record:

- Screenshot filename
- Screen name
- UI state
- How the user reached this state
- Previous state
- Next possible state
- Visible components
- Top bar structure
- Main content structure
- Bottom composer structure
- Icons
- Text
- Typography
- Colors
- Spacing
- Corner radii
- Shadows/elevation
- Animations or transient states
- Scroll position
- Keyboard state if visible
- Loading/thinking state if applicable
- Tool execution state if applicable
- Artifact state if applicable
- Claude Code state if applicable
- Differences from the current implementation
- Required implementation changes

Also create:

docs/VISUAL_FIDELITY_MATRIX.md

Use:

| Screenshot | Screen | Current Match | Target Match | Problems | Required Changes | Status |

Use percentages such as:

99%
95%
90%
75%
50%
etc.

Do not mark a screen complete simply because the code compiles.

Visual similarity is the criterion.

==================================================
4. SCREENSHOT-BY-SCREENSHOT WORKFLOW
==================================================

For EVERY screenshot:

1. Inspect the screenshot.
2. Identify its exact UI state.
3. Locate the corresponding implementation.
4. Run or inspect the existing screen.
5. Compare implementation versus reference.
6. Identify differences.
7. Fix the differences.
8. Test the screen.
9. Re-check against the screenshot.
10. Only then mark that screenshot as verified.

Do not only fix screens you think are important.

Every screenshot must eventually be accounted for.

==================================================
5. EXISTING PROJECT FIRST
==================================================

Before making changes inspect:

- Gradle configuration
- AndroidManifest
- Main activity
- Application class
- Navigation
- ViewModels
- State management
- Theme
- Colors
- Typography
- Components
- Screens
- Resources
- Drawables
- Icons
- Animations
- Existing Hermes integration
- Existing Claude UI components
- Existing research documentation
- Existing build configuration

Important existing components include things such as:

ClaudeApp
ClaudeViewModel
ClaudeComposer
ClaudeDrawer
ClaudeMessageItem
ClaudeThinkingBlock
ClaudeToolCard
ClaudeArtifactCard
ClaudeTopAppBar
ModelSelectorSheet
AttachmentSheet
ClaudeIcons
Home
Chat
Chat history
Claude Code
Projects
Search
Settings
Voice
Artifact viewer
Login
HermesAdapter

Do not delete these simply because they are imperfect.

Improve them.

==================================================
6. DO NOT DESTROY THE EXISTING HERMES INTEGRATION
==================================================

The project already contains Hermes-related architecture and behavior.

Preserve the existing Hermes integration.

Do not replace Hermes functionality with static screenshot content.

Do not hardcode the entire conversation.

Do not make the UI dependent on screenshot images.

The final application must remain a real Android application with real UI components and state.

The UI must react to actual application state.

If HermesAdapter currently simulates certain agent events, preserve that architecture while improving the UI representation.

Do not remove existing functionality unless it is clearly broken and replacing it is necessary.

==================================================
7. VISUAL RECONSTRUCTION RULE
==================================================

The goal is:

REAL UI COMPONENTS
+
REAL STATE
+
REAL INTERACTION
+
SCREENSHOT-ACCURATE VISUALS

Do NOT use:

- screenshot as background
- screenshot as UI
- giant image overlays
- fake clickable screenshot regions
- hardcoded bitmap screens
- fake navigation
- static conversation images
- canvas recreation of the entire application

Use proper:

- Compose layouts
- Text
- Rows
- Columns
- Boxes
- LazyColumn
- LazyRow
- Scroll states
- Modal bottom sheets
- Dialogs
- AnimatedVisibility
- transitions
- custom drawing only where appropriate
- real buttons
- real icons
- real text fields
- real state

==================================================
8. CENTRALIZE THE DESIGN SYSTEM
==================================================

Create or improve a centralized design system.

Use dedicated objects/classes such as:

ClaudeColors
ClaudeTypography
ClaudeDimensions
ClaudeShapes
ClaudeElevation
ClaudeMotion
ClaudeIcons

Do not scatter random values throughout the project.

However, do NOT blindly use values from existing documentation.

Measure and infer values from the actual screenshots.

The screenshots have priority.

==================================================
9. COLORS
==================================================

Reconstruct the actual Claude Android color system from the screenshots.

Pay special attention to:

- background
- primary surface
- secondary surface
- tertiary surface
- composer background
- cards
- borders
- dividers
- primary text
- secondary text
- muted text
- disabled text
- accent colors
- Claude orange/terracotta
- purple tool/model indicators
- code backgrounds
- thinking backgrounds
- error states
- warning states
- selected states

Avoid excessive pure black or pure white unless the screenshot actually uses it.

Do not introduce random Material colors.

All colors must visually correspond to the reference.

==================================================
10. TYPOGRAPHY
==================================================

Typography is extremely important.

Do not use generic Android fonts when the project can use the appropriate available fonts.

Inspect the repository resources and existing APK research.

The original Claude APK analysis identified fonts including:

- Anthropic Sans
- Anthropic Serif
- JetBrains Mono
- Noto Serif

Use the closest available actual fonts from the project.

Correct:

- font family
- font weight
- font size
- line height
- letter spacing
- paragraph spacing
- baseline alignment
- text color
- heading hierarchy
- assistant message typography
- user message typography
- code typography
- UI labels
- buttons
- model names
- thinking text

Assistant response typography should not simply be generic Serif if a closer Anthropic font is available.

==================================================
11. SPACING AND GEOMETRY
==================================================

Measure the screenshots carefully.

Correct:

- horizontal margins
- vertical spacing
- top bar height
- composer position
- message padding
- card padding
- icon sizes
- button sizes
- corner radii
- bottom navigation inset
- status bar inset
- navigation bar inset
- sheet heights
- drawer width
- modal spacing
- code block padding
- artifact card dimensions

Do not assume standard Material spacing is correct.

Use screenshot-derived dimensions.

==================================================
12. ANDROID SYSTEM UI
==================================================

Match the screenshots including:

- status bar
- status bar background
- navigation bar
- navigation bar background
- edge-to-edge behavior
- light/dark system bar icons
- keyboard insets
- IME behavior
- gesture navigation area
- safe areas

The application must not look like a web page placed inside Android.

It must behave like a native Android application.

==================================================
13. HOME / MAIN CHAT
==================================================

Reconstruct the exact home/chat interface shown in the screenshots.

Pay attention to:

- top bar
- hamburger/menu
- conversation title
- new conversation button
- overflow button
- message alignment
- assistant messages
- user messages
- markdown
- code
- thinking
- tool states
- artifacts
- composer
- scrolling
- keyboard behavior

Do not introduce unnecessary UI elements.

If the current implementation contains UI not present in the target screenshots, remove it or make it state-specific.

Example:

If a "Get more with Claude Pro / Upgrade to Pro" banner is visible in the current implementation but not present in the reference screenshot, it must NOT permanently appear in the target state.

==================================================
14. TOP APP BAR
==================================================

Correct ClaudeTopAppBar to match the screenshots exactly.

Pay attention to:

- hamburger icon
- title
- title alignment
- new conversation icon
- overflow icon
- spacing
- icon stroke width
- icon size
- vertical alignment
- top inset
- menu behavior

The overflow menu must reproduce the actual screenshot state.

Where shown, support actions such as:

- Share
- Rename
- Pin
- Add to project
- Add to home
- Delete

Do not use a generic Material dropdown if its appearance differs significantly.

==================================================
15. DRAWER / SIDEBAR
==================================================

Reconstruct the drawer exactly.

Correct:

- width
- background
- Claude logo
- account section
- conversation list
- search
- projects
- settings
- new conversation
- spacing
- separators
- selected state
- icons
- text hierarchy
- animation

Do not use placeholder symbols such as "*" for the Claude logo.

Use a proper vector/icon implementation.

==================================================
16. COMPOSER
==================================================

The composer is one of the most important UI elements.

Match:

- floating shape
- radius
- background
- border
- attachment button
- input
- model selector
- microphone
- send
- stop
- waveform
- loading state
- multiline expansion
- keyboard behavior
- attachment previews
- bottom spacing

The composer must respond to state.

Required states include where applicable:

- empty
- typing
- multiline
- attached files
- voice input
- sending
- streaming
- stop generation
- agent running

Do not force all controls to appear simultaneously if the screenshots show state-specific controls.

==================================================
17. MESSAGE RENDERING
==================================================

Improve ClaudeMessageItem significantly.

Support realistic rendering of:

- plain text
- paragraphs
- headings
- bullet lists
- numbered lists
- bold
- italic
- inline code
- code blocks
- links
- citations
- tables
- tool results
- thinking blocks
- artifacts
- images
- streaming output

Do not simply strip markdown syntax.

Use a real markdown rendering strategy or a robust internal renderer.

==================================================
18. CODE BLOCKS
==================================================

Code rendering must look like the reference screenshots.

Support:

- language labels where shown
- syntax highlighting
- monospace typography
- correct padding
- dark code background
- rounded corners
- copy button
- expand button
- horizontal scrolling
- vertical scrolling where necessary

Use the project's existing highlighting resources if appropriate.

The APK research found:

assets/highlight.min.js

Use it or another proper syntax highlighting implementation where technically appropriate.

Do not render all code as one plain monospace Text block.

==================================================
19. THINKING / REASONING STATES
==================================================

Reconstruct Claude's thinking UI shown in the screenshots.

Support dynamic states such as:

- Still working on it…
- Mulling
- Crystallizing
- Cogitating

Match:

- animation
- sparkle
- spinner
- typography
- color
- position
- expansion/collapse
- duration
- transition

Do not use a generic circular Material progress indicator if the screenshot uses Claude-specific motion.

==================================================
20. TOOL EXECUTION
==================================================

Claude Code/tool execution is a major part of the screenshots.

Support visual states including:

- tool started
- tool running
- tool completed
- tool failed
- command execution
- terminal output
- file creation
- file modification
- search
- tool approval
- collapsed tool
- expanded tool
- step counter
- spinner
- completion indicator

Match the actual screenshots.

Tool cards must feel like Claude's interface rather than generic Material cards.

==================================================
21. CLAUDE CODE
==================================================

Reconstruct the Claude Code UI shown in the reference screenshots.

Pay attention to:

- tool timeline
- execution history
- terminal/file icons
- command cards
- active execution
- completed execution
- step counter
- file creation
- code output
- thinking indicator
- dynamic status text
- bottom composer while agent is running
- stop-generation control

Claude Code should feel integrated into the same Claude application.

Do not build a generic terminal screen.

==================================================
22. ARTIFACTS
==================================================

Improve ClaudeArtifactCard and ArtifactViewerScreen.

The current artifact viewer must be compared carefully against the screenshots.

Do not assume that generic:

Code | Preview

tabs are correct.

If the screenshot shows a minimal top bar such as:

X
centered filename
three-dot menu

then reproduce that structure.

Support:

- artifact card
- artifact title
- file type
- preview
- code
- syntax highlighting
- copy
- expand
- overflow menu
- scrolling
- loading
- generated state

==================================================
23. MODEL SELECTOR
==================================================

Reconstruct the model selector bottom sheet.

Match:

- sheet shape
- drag handle
- header
- model rows
- selected indicator
- model descriptions
- effort selection
- spacing
- colors
- typography
- animation
- dismissal

Do not use a generic AlertDialog if the screenshot clearly shows a bottom sheet.

==================================================
24. ATTACHMENT SHEET
==================================================

Reconstruct attachment functionality and its visual states.

Support where shown:

- photos
- camera
- files
- documents
- PDFs
- images
- previews
- remove attachment
- attachment viewer
- loading state

Match the screenshots rather than generic Android pickers for the in-app UI.

==================================================
25. BOTTOM SHEETS
==================================================

The screenshots show modal surfaces and bottom sheets.

Reproduce:

- drag handle
- sheet radius
- background
- dimming
- height
- content spacing
- close button
- animation
- dismissal
- scroll behavior

Avoid default Material sheet styling when it visibly differs.

==================================================
26. SETTINGS
==================================================

Inspect every settings screenshot.

Reconstruct:

- settings home
- account
- appearance
- model behavior
- notifications
- voice
- privacy
- connectors
- usage
- billing
- shared links
- permissions
- memory
- other available settings

Do not invent settings that are not represented.

Maintain consistent navigation and visual hierarchy.

==================================================
27. PROJECTS
==================================================

Reconstruct Projects from the screenshots.

Match:

- project list
- project cards
- project detail
- project title
- project instructions
- files
- new project
- project actions
- navigation
- empty states

==================================================
28. SEARCH
==================================================

Reconstruct search.

Match:

- search entry
- search field
- results
- conversation results
- empty state
- clear button
- navigation
- typography
- spacing

==================================================
29. LOGIN / AUTHENTICATION
==================================================

Reconstruct login screens shown in the screenshots.

Pay attention to:

- logo
- email field
- buttons
- magic link state
- Google/SSO options if shown
- loading
- errors
- keyboard behavior
- typography
- spacing

Do not create a generic login page.

==================================================
30. VOICE
==================================================

Reconstruct the voice UI if represented in the screenshots.

Match:

- microphone state
- waveform
- recording state
- animation
- controls
- voice feedback
- settings
- transitions

==================================================
31. ICONS
==================================================

Do not rely blindly on default Material icons.

Claude's iconography has specific:

- stroke widths
- geometry
- proportions
- corner treatment
- visual weight

Inspect the screenshots and existing ClaudeIcons implementation.

Create accurate custom vector icons when required.

Important icons include:

- hamburger
- plus
- overflow
- send
- stop
- microphone
- attachment
- search
- settings
- project
- file
- terminal
- check
- close
- back
- share
- pin
- delete
- edit
- sparkle
- loading/thinking indicators

==================================================
32. ANIMATIONS
==================================================

Animations matter.

Reconstruct visible animations including:

- drawer opening
- bottom sheet opening
- message streaming
- cursor
- thinking indicator
- sparkle
- tool execution
- loading
- artifact generation
- composer state transitions
- voice waveform
- button state transitions
- screen navigation

Animations must be subtle and close to the screenshots.

Do not add unnecessary animations.

Do not use generic Material motion everywhere.

==================================================
33. SCROLLING BEHAVIOR
==================================================

Match actual mobile scrolling behavior.

Test:

- long conversations
- streaming messages
- tool output
- code blocks
- artifact content
- bottom sheets
- settings
- projects
- drawer
- keyboard appearance

The composer must remain correctly positioned.

When new messages arrive during streaming, scrolling must behave naturally.

==================================================
34. RESPONSIVE AND DEVICE BEHAVIOR
==================================================

The screenshots represent Android mobile UI.

Optimize for the screenshot device dimensions while keeping the UI responsive.

Do not hardcode the entire screen to one pixel resolution.

Use:

- dp
- sp
- window insets
- density-aware layouts
- responsive constraints

However, screenshot-specific dimensions should be respected where appropriate.

==================================================
35. PRESERVE HIGH-FIDELITY SCREENS
==================================================

Some screens are already approximately 99% accurate.

DO NOT unnecessarily rewrite them.

Before changing an existing screen:

1. Compare it against the screenshot.
2. Determine whether it actually needs modification.
3. If already highly accurate, preserve it.
4. Only fix measurable differences.

The goal is to improve the project, not introduce regressions.

==================================================
36. DOCUMENTATION
==================================================

Create/update:

docs/SCREENSHOT_STATE_MAP.md
docs/VISUAL_FIDELITY_MATRIX.md
docs/UI_RECONSTRUCTION_NOTES.md

UI_RECONSTRUCTION_NOTES.md should record:

- discovered UI measurements
- color values
- typography
- spacing
- icon observations
- animation observations
- state transitions
- implementation decisions
- uncertain areas
- screenshot references

Whenever a screenshot reveals a new behavior, document it.

==================================================
37. ARCHITECTURE
==================================================

Keep the project maintainable.

Prefer:

UI
↓
State/ViewModel
↓
Domain logic
↓
Hermes integration

Do not put all UI behavior into one enormous composable.

Break reusable components into appropriate files.

Use stable state models.

Avoid unnecessary recomposition.

Avoid memory leaks.

Avoid unnecessary dependencies.

==================================================
38. NO GENERIC MATERIAL UI
==================================================

Material components may be used where appropriate.

But the final UI must NOT look like:

"Android Material 3 demo app"

Avoid:

- default Material cards
- default Material buttons
- default Material typography
- default Material dialogs
- default Material dropdowns
- default Material switches
- default Material spacing

Whenever the screenshot differs, customize the component.

==================================================
39. BUILD QUALITY
==================================================

After modifications:

1. Run Gradle build.
2. Fix all compilation errors.
3. Fix resource errors.
4. Fix Kotlin errors.
5. Fix Compose errors.
6. Fix navigation errors.
7. Fix runtime crashes.
8. Verify APK generation.

Do not stop after code editing.

The final project must build successfully.

==================================================
40. VISUAL REGRESSION TESTING
==================================================

For each target screenshot:

1. Launch the corresponding state.
2. Capture the application screen.
3. Compare it with the reference screenshot in:

screenshot/

Compare:

- overall geometry
- colors
- typography
- spacing
- icons
- components
- state
- animation frame when relevant
- system bars
- composer
- scrolling
- modal surfaces

Fix the largest visual differences first.

Repeat until the screen is highly accurate.

==================================================
41. SCREENSHOT PRIORITY
==================================================

Use this priority order:

1. Actual screenshots in `screenshot/`
2. Existing working implementation
3. APK/reverse-engineering findings
4. Existing project documentation
5. General Claude UI knowledge
6. Your own assumptions

Never reverse this order.

==================================================
42. DO NOT FABRICATE VISUAL DETAILS
==================================================

If something is visible in the screenshot, reproduce it.

If something is NOT visible, do not add it simply because another Claude version has it.

Do not assume web Claude and Android Claude are identical.

Do not assume Claude Code web and Claude Android are identical.

This project specifically targets the supplied Android screenshots.

==================================================
43. IMPORTANT EXISTING RESEARCH
==================================================

The project may contain research about the original Claude APK.

Relevant discoveries include:

- Jetpack Compose/Kotlin Multiplatform style UI
- Anthropic Sans
- Anthropic Serif
- JetBrains Mono
- Noto Serif
- Claude-specific vectors
- Claude-specific theme resources
- WebView/WebCompat
- chat navigation
- tool states
- artifacts
- Claude Code
- bottom sheets
- model selection
- connectors
- projects
- voice
- settings

Use this research to understand architecture and behavior.

But screenshots remain the final authority for visual reconstruction.

==================================================
44. IMPORTANT CURRENT IMPLEMENTATION ISSUES TO CHECK
==================================================

Explicitly inspect these areas:

A. ClaudeComposer

Current implementation is relatively close, but verify:

- radius
- spacing
- model pill
- microphone
- send/stop
- waveform
- attachment previews
- keyboard behavior
- unnecessary Pro banner

B. ClaudeMessageItem

Improve:

- markdown
- typography
- code
- syntax highlighting
- thinking
- tool cards
- artifacts
- streaming cursor
- actions

C. ClaudeTopAppBar

Verify:

- hamburger
- title
- new conversation
- overflow
- correct menu actions
- spacing

D. ClaudeDrawer

Remove placeholder logo behavior.

Match actual Claude branding and layout.

E. ArtifactViewerScreen

Compare directly against the screenshot.

Do not preserve generic tabs if the target screenshot does not contain them.

F. ClaudeThinkingBlock

Reconstruct exact thinking animation and typography.

G. ClaudeToolCard

Match command/tool execution cards exactly.

H. ClaudeArtifactCard

Match artifact preview cards exactly.

I. ModelSelectorSheet

Match the actual screenshot rather than default Material sheet styling.

J. AttachmentSheet

Match the actual screenshot states.

==================================================
45. STATE COVERAGE
==================================================

Do not only implement static screens.

Implement state transitions for:

- initial
- empty
- typing
- sending
- streaming
- thinking
- tool execution
- tool complete
- artifact generation
- artifact complete
- error
- stopped
- voice
- attachment
- modal
- bottom sheet
- drawer
- search
- settings
- project
- Claude Code

Every screenshot should correspond to a reproducible application state.

==================================================
46. PERFORMANCE
==================================================

Keep scrolling smooth.

Avoid:

- unnecessary recomposition
- huge bitmap backgrounds
- expensive custom drawing for simple UI
- excessive nested layouts
- unnecessary network calls
- blocking the main thread

The application should feel responsive.

==================================================
47. SECURITY AND FUNCTIONALITY
==================================================

Do not expose secrets.

Do not hardcode API keys.

Do not remove authentication boundaries.

Do not bypass security merely to make a screenshot work.

Do not replace real application architecture with fake UI.

==================================================
48. FINAL AUDIT
==================================================

Before considering the task complete:

1. Inspect every file in `screenshot/`.
2. Verify every screenshot has a corresponding state.
3. Verify every state can be reached.
4. Compare every target screen against the actual implementation.
5. Check visual fidelity.
6. Check typography.
7. Check colors.
8. Check spacing.
9. Check icons.
10. Check animations.
11. Check scrolling.
12. Check keyboard behavior.
13. Check system bars.
14. Check tool execution.
15. Check thinking states.
16. Check artifacts.
17. Check Claude Code.
18. Check model selection.
19. Check attachment states.
20. Check settings.
21. Check projects.
22. Check search.
23. Check login.
24. Check voice.
25. Check drawer.
26. Check overflow menus.
27. Check all bottom sheets.
28. Build the APK.
29. Install/run it.
30. Perform a final regression pass.

==================================================
49. FINAL QUALITY STANDARD
==================================================

The final result should NOT merely be:

"an Android app that looks similar to Claude."

The target is:

"an Android reconstruction whose screens, states, interactions, animations, typography, colors, spacing, icons, tool execution UI, artifacts, Claude Code UI and composer closely reproduce the supplied real Claude Android screenshots."

The screenshots in:

screenshot/

are the visual contract.

==================================================
50. WORK ORDER
==================================================

Follow this exact order:

PHASE 1
Inspect the complete repository.

PHASE 2
Inspect every screenshot in `screenshot/`.

PHASE 3
Create:

docs/SCREENSHOT_STATE_MAP.md
docs/VISUAL_FIDELITY_MATRIX.md
docs/UI_RECONSTRUCTION_NOTES.md

PHASE 4
Map screenshots to existing screens/components.

PHASE 5
Identify the largest visual mismatches.

PHASE 6
Fix the global design system.

PHASE 7
Fix navigation and system UI.

PHASE 8
Fix home/chat/top bar/drawer.

PHASE 9
Fix message rendering and typography.

PHASE 10
Fix composer.

PHASE 11
Fix thinking/tool execution.

PHASE 12
Fix Claude Code.

PHASE 13
Fix artifacts and code viewer.

PHASE 14
Fix model selector and attachment sheets.

PHASE 15
Fix settings/projects/search/login/voice.

PHASE 16
Fix animations and transitions.

PHASE 17
Run full build.

PHASE 18
Run screenshot-by-screenshot visual regression.

PHASE 19
Fix remaining mismatches.

PHASE 20
Final build and verification.

==================================================
51. CRITICAL RULE
==================================================

DO NOT STOP AT ANALYSIS.

After inspecting the repository and screenshots, actually modify the project.

Do not merely tell me what should be changed.

Implement the changes.

Do not ask me to manually implement obvious fixes.

Do not create a new project.

Do not replace working code unnecessarily.

Do not stop after fixing one screen.

Continue through the entire screenshot set.

==================================================
52. FINAL DELIVERABLE
==================================================

At the end provide:

1. Summary of implemented changes.
2. Screens/screenshots completed.
3. Screens that were already preserved because they were high fidelity.
4. Major visual corrections.
5. Major behavioral corrections.
6. Animation corrections.
7. Hermes integration status.
8. Build status.
9. APK output path.
10. Remaining known mismatches, if any.
11. Final visual fidelity matrix.

Most importantly:

THE ACTUAL FILES INSIDE

`screenshot/`

MUST BE USED AS THE REFERENCE DURING IMPLEMENTATION AND FINAL VERIFICATION.

Do not rely only on the written prompt.

Do not rely only on existing documentation.

Open the screenshots, inspect them, compare them, and implement what they actually show.

Start by inspecting the repository and the complete `screenshot/` directory now.
