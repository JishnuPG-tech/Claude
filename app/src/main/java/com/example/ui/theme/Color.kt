package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Claude Light Mode Palette (Warm Parchment Aesthetic)
val ClaudeBgMainLight = Color(0xFFFAF9F5)
val ClaudeSurfaceLight = Color(0xFFFFFFFF)
val ClaudeSurfaceSecondaryLight = Color(0xFFF0EEE6)
val ClaudeSurfaceContainerLight = Color(0xFFE8E5DC)
val ClaudeSurfaceHighlightLight = Color(0xFFE4DFD3)
val ClaudeBorderSubtleLight = Color(0xFFE5E2D9)
val ClaudeBorderStrongLight = Color(0xFFD1CDBF)

val ClaudeTextPrimaryLight = Color(0xFF1D1C16)
val ClaudeTextSecondaryLight = Color(0xFF636159)
val ClaudeTextTertiaryLight = Color(0xFF969389)
val ClaudeTextInverseLight = Color(0xFFFFFFFF)

// Claude Dark Mode Palette (Exact from mobile screenshot)
val ClaudeBgMainDark = Color(0xFF141413)
val ClaudeSurfaceDark = Color(0xFF1E1D1C)
val ClaudeSurfaceSecondaryDark = Color(0xFF2A2928)
val ClaudeSurfaceContainerDark = Color(0xFF2E2D2B)
val ClaudeSurfaceHighlightDark = Color(0xFF383734)
val ClaudeBorderSubtleDark = Color(0xFF262523)
val ClaudeBorderStrongDark = Color(0xFF3D3C38)

val ClaudeTextPrimaryDark = Color(0xFFF5F5F0)
val ClaudeTextSecondaryDark = Color(0xFF9E9C96)
val ClaudeTextTertiaryDark = Color(0xFF75736E)
val ClaudeTextInverseDark = Color(0xFF141413)

// Anthropic Brand Accents
val ClaudeTerracotta = Color(0xFFD97757)
val ClaudeTerracottaDark = Color(0xFFC15F3C)
val ClaudeTerracottaLight = Color(0xFFF5EBE6)
val ClaudeTerracottaDarkContainer = Color(0xFF3E2822)
val ClaudeUpgradePurple = Color(0xFFA78BFA)

// Status & Code
val ClaudeSuccess = Color(0xFF3B755D)
val ClaudeWarning = Color(0xFFC27803)
val ClaudeError = Color(0xFFC53B3B)
val ClaudeInfo = Color(0xFF4D6B9C)

val ClaudeCodeBgLight = Color(0xFFF3F1EB)
val ClaudeCodeHeaderLight = Color(0xFFEAE7DF)
val ClaudeCodeBgDark = Color(0xFF141312)
val ClaudeCodeHeaderDark = Color(0xFF23211F)

@Immutable
data class ClaudeColors(
  val bgMain: Color,
  val surface: Color,
  val surfaceSecondary: Color,
  val surfaceContainer: Color,
  val surfaceHighlight: Color,
  val borderSubtle: Color,
  val borderStrong: Color,
  val textPrimary: Color,
  val textSecondary: Color,
  val textTertiary: Color,
  val textInverse: Color,
  val terracotta: Color,
  val terracottaVariant: Color,
  val codeBg: Color,
  val codeHeader: Color,
  val success: Color,
  val warning: Color,
  val error: Color,
  val isDark: Boolean,
)

val LocalClaudeColors = staticCompositionLocalOf {
  ClaudeColors(
    bgMain = ClaudeBgMainLight,
    surface = ClaudeSurfaceLight,
    surfaceSecondary = ClaudeSurfaceSecondaryLight,
    surfaceContainer = ClaudeSurfaceContainerLight,
    surfaceHighlight = ClaudeSurfaceHighlightLight,
    borderSubtle = ClaudeBorderSubtleLight,
    borderStrong = ClaudeBorderStrongLight,
    textPrimary = ClaudeTextPrimaryLight,
    textSecondary = ClaudeTextSecondaryLight,
    textTertiary = ClaudeTextTertiaryLight,
    textInverse = ClaudeTextInverseLight,
    terracotta = ClaudeTerracotta,
    terracottaVariant = ClaudeTerracottaLight,
    codeBg = ClaudeCodeBgLight,
    codeHeader = ClaudeCodeHeaderLight,
    success = ClaudeSuccess,
    warning = ClaudeWarning,
    error = ClaudeError,
    isDark = false,
  )
}
