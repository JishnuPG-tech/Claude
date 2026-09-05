package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Nightlight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Shortcut
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ClaudeAppSettings
import com.example.ui.components.ClaudeAudioWaveformIcon

/**
 * Settings Screen matching the Claude Android screenshot:
 * - Top Bar: Menu Hamburger (left), "Settings" in Serif (center), Info (i) (right)
 * - Card 1: Connectors, Permissions
 * - Card 2: Color mode (System), Font style (Default), Voice
 * - Card 3: Haptic feedback (with Blue Switch), Notifications, Time & focus, Privacy, Sharing
 * - Card 4: Log out (in soft coral red #E58B88)
 */
@Composable
fun SettingsScreen(
  settings: ClaudeAppSettings,
  onUpdateSettings: (ClaudeAppSettings) -> Unit,
  onBack: () -> Unit,
  onClearChats: () -> Unit,
  onLogout: (() -> Unit)? = null,
  onMenuClick: () -> Unit = onBack,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  val haptic = LocalHapticFeedback.current

  // Dialog states
  var showColorModeDialog by remember { mutableStateOf(false) }
  var showFontStyleDialog by remember { mutableStateOf(false) }
  var showVoiceDialog by remember { mutableStateOf(false) }
  var showConnectorsDialog by remember { mutableStateOf(false) }
  var showPermissionsDialog by remember { mutableStateOf(false) }
  var showNotificationsDialog by remember { mutableStateOf(false) }
  var showTimeFocusDialog by remember { mutableStateOf(false) }
  var showPrivacyDialog by remember { mutableStateOf(false) }
  var showLogoutDialog by remember { mutableStateOf(false) }
  var showInfoDialog by remember { mutableStateOf(false) }

  // Color tokens from screenshot
  val bgCanvas = Color(0xFF141413) // Pitch dark canvas
  val cardBg = Color(0xFF1E1D1C)   // Rounded container card
  val textPrimary = Color(0xFFEDEBE6)
  val textSecondary = Color(0xFF9E9C96)
  val logoutCoral = Color(0xFFE58B88)
  val switchBlue = Color(0xFF3B82F6)

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(bgCanvas)
      .statusBarsPadding()
      .navigationBarsPadding()
      .verticalScroll(rememberScrollState())
      .testTag("settings_screen")
  ) {
    // 1. Top Bar: Hamburger Menu | "Settings" (Serif) | Info (i)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onMenuClick,
        modifier = Modifier.testTag("settings_menu_button")
      ) {
        Icon(
          imageVector = Icons.Default.Menu,
          contentDescription = "Open navigation menu",
          tint = textPrimary,
          modifier = Modifier.size(24.dp)
        )
      }

      Text(
        text = "Settings",
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 21.sp,
        color = textPrimary,
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(1f)
      )

      IconButton(
        onClick = { showInfoDialog = true },
        modifier = Modifier.testTag("settings_info_button")
      ) {
        Icon(
          imageVector = Icons.Outlined.Info,
          contentDescription = "About Claude",
          tint = textPrimary,
          modifier = Modifier.size(23.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // GROUP 1: Connectors & Permissions
      SettingsCardContainer(cardBg = cardBg) {
        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Extension,
              contentDescription = "Connectors",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Connectors",
          onClick = { showConnectorsDialog = true },
          testTag = "settings_item_connectors"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Default.Android,
              contentDescription = "Permissions",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Permissions",
          onClick = { showPermissionsDialog = true },
          testTag = "settings_item_permissions"
        )
      }

      // GROUP 2: Color mode, Font style, Voice
      SettingsCardContainer(cardBg = cardBg) {
        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Nightlight,
              contentDescription = "Color mode",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Color mode",
          subtitle = settings.themeMode,
          onClick = { showColorModeDialog = true },
          testTag = "settings_item_color_mode"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Text(
              text = "Aa",
              fontSize = 19.sp,
              fontWeight = FontWeight.Normal,
              fontFamily = FontFamily.SansSerif,
              color = textPrimary,
              lineHeight = 19.sp
            )
          },
          title = "Font style",
          subtitle = settings.fontStyle,
          onClick = { showFontStyleDialog = true },
          testTag = "settings_item_font_style"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            ClaudeAudioWaveformIcon(
              tint = textPrimary,
              size = 22.dp
            )
          },
          title = "Voice",
          onClick = { showVoiceDialog = true },
          testTag = "settings_item_voice"
        )
      }

      // GROUP 3: Haptic feedback, Notifications, Time & focus, Privacy, Sharing
      SettingsCardContainer(cardBg = cardBg) {
        // Haptic feedback with active switch
        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Vibration,
              contentDescription = "Haptic feedback",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Haptic feedback",
          trailing = {
            Switch(
              checked = settings.hapticFeedback,
              onCheckedChange = { isChecked ->
                if (isChecked) {
                  haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
                onUpdateSettings(settings.copy(hapticFeedback = isChecked))
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = switchBlue,
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = textSecondary,
                uncheckedTrackColor = Color(0xFF2E2D2B),
                uncheckedBorderColor = Color.Transparent
              ),
              modifier = Modifier.testTag("settings_switch_haptic")
            )
          },
          onClick = {
            val newChecked = !settings.hapticFeedback
            if (newChecked) {
              haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            onUpdateSettings(settings.copy(hapticFeedback = newChecked))
          },
          testTag = "settings_item_haptic"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Notifications,
              contentDescription = "Notifications",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Notifications",
          onClick = { showNotificationsDialog = true },
          testTag = "settings_item_notifications"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Bedtime,
              contentDescription = "Time & focus",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Time & focus",
          onClick = { showTimeFocusDialog = true },
          testTag = "settings_item_time_focus"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Security,
              contentDescription = "Privacy",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Privacy",
          onClick = { showPrivacyDialog = true },
          testTag = "settings_item_privacy"
        )

        SettingsDivider()

        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.Outlined.Shortcut,
              contentDescription = "Sharing",
              tint = textPrimary,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Sharing",
          onClick = {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
              type = "text/plain"
              putExtra(Intent.EXTRA_SUBJECT, "Claude by Anthropic")
              putExtra(Intent.EXTRA_TEXT, "Chat with Claude, an AI assistant by Anthropic: https://claude.ai")
            }
            context.startActivity(Intent.createChooser(shareIntent, "Share Claude"))
          },
          testTag = "settings_item_sharing"
        )
      }

      // GROUP 4: Log out (Standalone coral red container)
      SettingsCardContainer(cardBg = cardBg) {
        SettingsRowItem(
          icon = {
            Icon(
              imageVector = Icons.AutoMirrored.Outlined.Logout,
              contentDescription = "Log out",
              tint = logoutCoral,
              modifier = Modifier.size(24.dp)
            )
          },
          title = "Log out",
          titleColor = logoutCoral,
          onClick = { showLogoutDialog = true },
          testTag = "settings_item_logout"
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      Text(
        text = "Claude for Android v1.4.0",
        color = Color(0xFF6B6964),
        fontSize = 12.5.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
      )

      Spacer(modifier = Modifier.height(20.dp))
    }
  }

  // DIALOG: Color mode (System, Dark, Light)
  if (showColorModeDialog) {
    ClaudeSettingsListDialog(
      title = "Color mode",
      options = listOf("System", "Dark", "Light"),
      selectedOption = settings.themeMode,
      onSelect = {
        onUpdateSettings(settings.copy(themeMode = it))
        showColorModeDialog = false
      },
      onDismiss = { showColorModeDialog = false }
    )
  }

  // DIALOG: Font style (Default, Editorial Serif, Monospace)
  if (showFontStyleDialog) {
    ClaudeSettingsListDialog(
      title = "Font style",
      options = listOf("Default", "Editorial Serif", "Monospace"),
      selectedOption = settings.fontStyle,
      onSelect = {
        onUpdateSettings(settings.copy(fontStyle = it))
        showFontStyleDialog = false
      },
      onDismiss = { showFontStyleDialog = false }
    )
  }

  // DIALOG: Voice (Airy, Buttery, Mellow, Glassy, Rounded)
  if (showVoiceDialog) {
    ClaudeSettingsListDialog(
      title = "Voice Tone",
      options = listOf("Airy", "Buttery", "Mellow", "Glassy", "Rounded"),
      selectedOption = settings.voiceName,
      onSelect = {
        onUpdateSettings(settings.copy(voiceName = it))
        showVoiceDialog = false
        Toast.makeText(context, "Selected $it voice", Toast.LENGTH_SHORT).show()
      },
      onDismiss = { showVoiceDialog = false }
    )
  }

  // DIALOG: Connectors (Model Context Protocol)
  if (showConnectorsDialog) {
    AlertDialog(
      onDismissRequest = { showConnectorsDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Connectors", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            "Connect external tools and services using Model Context Protocol (MCP):",
            color = textSecondary,
            fontSize = 14.sp
          )
          ConnectorStatusRow("File System Connector", "Connected")
          ConnectorStatusRow("GitHub MCP Server", "Connected")
          ConnectorStatusRow("Postgres Database Tool", "Configured")
          ConnectorStatusRow("Web Retrieval & Search", "Active")
        }
      },
      confirmButton = {
        TextButton(onClick = { showConnectorsDialog = false }) {
          Text("Done", color = textPrimary)
        }
      }
    )
  }

  // DIALOG: Permissions
  if (showPermissionsDialog) {
    AlertDialog(
      onDismissRequest = { showPermissionsDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Permissions", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          ConnectorStatusRow("Microphone", "Allowed")
          ConnectorStatusRow("Notifications", "Allowed")
          ConnectorStatusRow("Camera & Storage", "Allowed")
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            "Manage permissions anytime via system settings.",
            color = textSecondary,
            fontSize = 13.sp
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { showPermissionsDialog = false }) {
          Text("OK", color = textPrimary)
        }
      }
    )
  }

  // DIALOG: Notifications
  if (showNotificationsDialog) {
    AlertDialog(
      onDismissRequest = { showNotificationsDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Notifications", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          ConnectorStatusRow("Chat Response Alerts", "Enabled")
          ConnectorStatusRow("Extended Thinking Alerts", "Enabled")
          ConnectorStatusRow("Weekly Highlights", "Disabled")
        }
      },
      confirmButton = {
        TextButton(onClick = { showNotificationsDialog = false }) {
          Text("Save", color = textPrimary)
        }
      }
    )
  }

  // DIALOG: Time & focus
  if (showTimeFocusDialog) {
    AlertDialog(
      onDismissRequest = { showTimeFocusDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Time & focus", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            "Balance your daily focus and quiet hours with Claude.",
            color = textSecondary,
            fontSize = 14.sp
          )
          ConnectorStatusRow("Bedtime Mode (10 PM - 7 AM)", "Enabled")
          ConnectorStatusRow("Do Not Disturb during thinking", "Enabled")
        }
      },
      confirmButton = {
        TextButton(onClick = { showTimeFocusDialog = false }) {
          Text("Done", color = textPrimary)
        }
      }
    )
  }

  // DIALOG: Privacy
  if (showPrivacyDialog) {
    AlertDialog(
      onDismissRequest = { showPrivacyDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Privacy", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          ConnectorStatusRow("Model Training Opt-out", "Active")
          ConnectorStatusRow("Data Retention", "30 Days")
          ConnectorStatusRow("Local Conversation Encryption", "Enabled")
          Spacer(modifier = Modifier.height(4.dp))
          Button(
            onClick = {
              onClearChats()
              showPrivacyDialog = false
              Toast.makeText(context, "Chat history deleted", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF332020)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Delete All Chat History", color = logoutCoral, fontSize = 13.5.sp)
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showPrivacyDialog = false }) {
          Text("Close", color = textPrimary)
        }
      }
    )
  }

  // DIALOG: Log out confirmation
  if (showLogoutDialog) {
    AlertDialog(
      onDismissRequest = { showLogoutDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("Log out", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Text(
          "Are you sure you want to log out of ${settings.userName} (${settings.userEmail})? You can log back in anytime.",
          color = textSecondary,
          fontSize = 14.5.sp,
          lineHeight = 20.sp
        )
      },
      confirmButton = {
        Button(
          onClick = {
            showLogoutDialog = false
            if (onLogout != null) {
              onLogout()
            } else {
              onClearChats()
              onBack()
            }
            Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
          },
          colors = ButtonDefaults.buttonColors(containerColor = logoutCoral),
          shape = RoundedCornerShape(16.dp)
        ) {
          Text("Log out", color = Color.Black, fontWeight = FontWeight.SemiBold)
        }
      },
      dismissButton = {
        TextButton(onClick = { showLogoutDialog = false }) {
          Text("Cancel", color = textSecondary)
        }
      }
    )
  }

  // DIALOG: Info (i) button
  if (showInfoDialog) {
    AlertDialog(
      onDismissRequest = { showInfoDialog = false },
      containerColor = cardBg,
      shape = RoundedCornerShape(22.dp),
      title = {
        Text("About Claude", color = textPrimary, fontFamily = FontFamily.Serif, fontSize = 20.sp)
      },
      text = {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text("Claude for Android", color = textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
          Text("Version 1.4.0 (Build 2026.09)", color = textSecondary, fontSize = 13.5.sp)
          Text("Anthropic PBC • AI Research & Safety", color = textSecondary, fontSize = 13.5.sp)
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            "Claude is a next-generation AI assistant built by Anthropic with an emphasis on safety, helpfulness, and honesty.",
            color = textSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
          )
        }
      },
      confirmButton = {
        TextButton(onClick = { showInfoDialog = false }) {
          Text("Close", color = textPrimary)
        }
      }
    )
  }
}

/**
 * Subtle hairline divider indented to align with text column
 */
@Composable
private fun SettingsDivider() {
  HorizontalDivider(
    modifier = Modifier.padding(start = 60.dp),
    thickness = 0.6.dp,
    color = Color(0xFF282725)
  )
}

/**
 * Rounded Container Card for grouped settings items
 */
@Composable
private fun SettingsCardContainer(
  cardBg: Color,
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(20.dp))
      .background(cardBg)
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      content()
    }
  }
}

/**
 * Individual Settings Row Item with icon, title, optional subtitle, and optional trailing widget
 */
@Composable
private fun SettingsRowItem(
  icon: @Composable () -> Unit,
  title: String,
  subtitle: String? = null,
  titleColor: Color? = null,
  trailing: (@Composable () -> Unit)? = null,
  onClick: () -> Unit,
  testTag: String,
  modifier: Modifier = Modifier,
) {
  val defaultTitleColor = Color(0xFFEDEBE6)
  val defaultSubtitleColor = Color(0xFF9E9C96)

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(horizontal = 18.dp, vertical = if (subtitle != null) 14.dp else 17.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left Icon Container (24dp)
    Box(
      modifier = Modifier.size(24.dp),
      contentAlignment = Alignment.Center
    ) {
      icon()
    }

    Spacer(modifier = Modifier.width(18.dp))

    // Title & Subtitle Column
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontSize = 17.5.sp,
        fontWeight = FontWeight.Normal,
        color = titleColor ?: defaultTitleColor,
        letterSpacing = 0.1.sp
      )
      if (subtitle != null) {
        Spacer(modifier = Modifier.height(3.dp))
        Text(
          text = subtitle,
          fontSize = 14.sp,
          fontWeight = FontWeight.Normal,
          color = defaultSubtitleColor
        )
      }
    }

    // Optional Trailing element (e.g. Switch)
    if (trailing != null) {
      trailing()
    }
  }
}

@Composable
private fun ConnectorStatusRow(name: String, status: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(name, color = Color(0xFFEDEBE6), fontSize = 14.sp)
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(6.dp))
        .background(Color(0xFF2B2A28))
        .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
      Text(status, color = Color(0xFFB5B3AD), fontSize = 12.sp)
    }
  }
}

/**
 * Reusable selection dialog for list options (e.g. Color mode, Font style, Voice tone)
 */
@Composable
private fun ClaudeSettingsListDialog(
  title: String,
  options: List<String>,
  selectedOption: String,
  onSelect: (String) -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    containerColor = Color(0xFF1E1D1C),
    shape = RoundedCornerShape(22.dp),
    title = {
      Text(title, color = Color(0xFFEDEBE6), fontFamily = FontFamily.Serif, fontSize = 20.sp)
    },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        options.forEach { option ->
          val isSelected = option.equals(selectedOption, ignoreCase = true)
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) Color(0xFF2E2D2B) else Color.Transparent)
              .clickable { onSelect(option) }
              .padding(horizontal = 16.dp, vertical = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = option,
              fontSize = 16.sp,
              fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
              color = if (isSelected) Color.White else Color(0xFFC8C6C0)
            )
            if (isSelected) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = Color(0xFF3B82F6),
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel", color = Color(0xFF9E9C96))
      }
    }
  )
}

