package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
  primary = ClaudeTerracotta,
  onPrimary = ClaudeTextInverseLight,
  primaryContainer = ClaudeTerracottaLight,
  onPrimaryContainer = ClaudeTerracottaDark,
  secondary = ClaudeSurfaceSecondaryLight,
  onSecondary = ClaudeTextPrimaryLight,
  tertiary = ClaudeInfo,
  background = ClaudeBgMainLight,
  onBackground = ClaudeTextPrimaryLight,
  surface = ClaudeSurfaceLight,
  onSurface = ClaudeTextPrimaryLight,
  surfaceVariant = ClaudeSurfaceSecondaryLight,
  onSurfaceVariant = ClaudeTextSecondaryLight,
  outline = ClaudeBorderStrongLight,
  outlineVariant = ClaudeBorderSubtleLight,
)

private val DarkColorScheme = darkColorScheme(
  primary = ClaudeTerracotta,
  onPrimary = ClaudeTextInverseDark,
  primaryContainer = ClaudeTerracottaDarkContainer,
  onPrimaryContainer = ClaudeTerracotta,
  secondary = ClaudeSurfaceSecondaryDark,
  onSecondary = ClaudeTextPrimaryDark,
  tertiary = ClaudeInfo,
  background = ClaudeBgMainDark,
  onBackground = ClaudeTextPrimaryDark,
  surface = ClaudeSurfaceDark,
  onSurface = ClaudeTextPrimaryDark,
  surfaceVariant = ClaudeSurfaceSecondaryDark,
  onSurfaceVariant = ClaudeTextSecondaryDark,
  outline = ClaudeBorderStrongDark,
  outlineVariant = ClaudeBorderSubtleDark,
)

@Composable
fun ClaudeTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val claudeColors = if (darkTheme) {
    ClaudeColors(
      bgMain = ClaudeBgMainDark,
      surface = ClaudeSurfaceDark,
      surfaceSecondary = ClaudeSurfaceSecondaryDark,
      surfaceContainer = ClaudeSurfaceContainerDark,
      surfaceHighlight = ClaudeSurfaceHighlightDark,
      borderSubtle = ClaudeBorderSubtleDark,
      borderStrong = ClaudeBorderStrongDark,
      textPrimary = ClaudeTextPrimaryDark,
      textSecondary = ClaudeTextSecondaryDark,
      textTertiary = ClaudeTextTertiaryDark,
      textInverse = ClaudeTextInverseDark,
      terracotta = ClaudeTerracotta,
      terracottaVariant = ClaudeTerracottaDarkContainer,
      codeBg = ClaudeCodeBgDark,
      codeHeader = ClaudeCodeHeaderDark,
      success = ClaudeSuccess,
      warning = ClaudeWarning,
      error = ClaudeError,
      isDark = true,
    )
  } else {
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

  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  CompositionLocalProvider(LocalClaudeColors provides claudeColors) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = ClaudeTypography,
      content = content,
    )
  }
}
