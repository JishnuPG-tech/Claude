package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ClaudeTerracotta
import com.example.ui.theme.LocalClaudeColors

data class CodeLogEntry(
  val id: String,
  val type: String, // "INFO", "CMD", "DIFF", "SUCCESS"
  val text: String,
)

@Composable
fun ClaudeCodeScreen(
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val colors = LocalClaudeColors.current

  val logs = remember {
    mutableStateListOf(
      CodeLogEntry("1", "INFO", "Connected to session claude-code-dev-491"),
      CodeLogEntry("2", "CMD", "$ git status --porcelain"),
      CodeLogEntry("3", "INFO", "M app/src/main/java/com/example/ui/theme/Color.kt"),
      CodeLogEntry("4", "CMD", "$ gradle :app:compileDebugKotlin"),
      CodeLogEntry("5", "SUCCESS", "BUILD SUCCESSFUL in 1.4s"),
      CodeLogEntry("6", "INFO", "Agent running: Inspecting architectural compliance..."),
    )
  }

  var pendingApproval by remember {
    mutableStateOf<String?>("git push origin feature/claude-ui-polish")
  }

  var commandInput by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(logs.size) {
    listState.animateScrollToItem(logs.size - 1)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFF141312))
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
      .testTag("claude_code_screen")
  ) {
    // Header
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color(0xFFECEAE4)
          )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Claude Code",
              fontFamily = FontFamily.Serif,
              fontWeight = FontWeight.SemiBold,
              fontSize = 17.sp,
              color = Color(0xFFECEAE4)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF33312D))
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = "BETA",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = ClaudeTerracotta
              )
            }
          }
          Text(
            text = "anthropic/claude-mobile (main)",
            fontFamily = FontFamily.Monospace,
            fontSize = 11.sp,
            color = Color(0xFFACA89E)
          )
        }
      }

      // Online status dot
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF22211F))
          .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(Color(0xFF3B755D))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Active",
          fontSize = 11.sp,
          color = Color(0xFFECEAE4),
          fontFamily = FontFamily.Monospace
        )
      }
    }

    // Terminal Log Output
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(logs, key = { it.id }) { log ->
        val logColor = when (log.type) {
          "CMD" -> ClaudeTerracotta
          "SUCCESS" -> Color(0xFF3B755D)
          "DIFF" -> Color(0xFF4D6B9C)
          else -> Color(0xFFACA89E)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
          Text(
            text = log.text,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.5.sp,
            lineHeight = 18.sp,
            color = logColor
          )
        }
      }
    }

    // Pending Approval Box (if any)
    if (pendingApproval != null) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(Color(0xFF22211F))
          .border(1.dp, Color(0xFFC27803), RoundedCornerShape(12.dp))
          .padding(14.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = null,
              tint = Color(0xFFC27803),
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Command Approval Required",
              fontFamily = FontFamily.SansSerif,
              fontWeight = FontWeight.SemiBold,
              fontSize = 13.sp,
              color = Color(0xFFECEAE4)
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = pendingApproval!!,
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            color = ClaudeTerracotta,
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFF141312))
              .padding(8.dp)
              .fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            OutlinedButton(
              onClick = {
                logs.add(CodeLogEntry(System.currentTimeMillis().toString(), "INFO", "Command rejected by user."))
                pendingApproval = null
              },
              shape = RoundedCornerShape(16.dp),
            ) {
              Text("Reject", fontSize = 12.sp, color = Color(0xFFACA89E))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = {
                logs.add(CodeLogEntry(System.currentTimeMillis().toString(), "CMD", "$ " + pendingApproval!!))
                logs.add(CodeLogEntry((System.currentTimeMillis() + 1).toString(), "SUCCESS", "Command executed successfully."))
                pendingApproval = null
              },
              shape = RoundedCornerShape(16.dp),
              colors = ButtonDefaults.buttonColors(containerColor = ClaudeTerracotta)
            ) {
              Text("Approve & Run", fontSize = 12.sp, color = Color.White)
            }
          }
        }
      }
    }

    // Terminal Input Prompt
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 10.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(Color(0xFF22211F))
        .border(1.dp, Color(0xFF33312D), RoundedCornerShape(24.dp))
        .padding(horizontal = 14.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = ">",
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp,
        color = ClaudeTerracotta
      )
      Spacer(modifier = Modifier.width(8.dp))

      Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.CenterStart
      ) {
        if (commandInput.isEmpty()) {
          Text(
            text = "Tell Claude Code what to build...",
            fontFamily = FontFamily.SansSerif,
            fontSize = 13.sp,
            color = Color(0xFF757269)
          )
        }
        BasicTextField(
          value = commandInput,
          onValueChange = { commandInput = it },
          textStyle = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontSize = 13.sp,
            color = Color(0xFFECEAE4)
          ),
          cursorBrush = SolidColor(ClaudeTerracotta),
          modifier = Modifier.fillMaxWidth()
        )
      }

      IconButton(
        onClick = {
          if (commandInput.isNotBlank()) {
            val cmd = commandInput
            logs.add(CodeLogEntry(System.currentTimeMillis().toString(), "CMD", "> $cmd"))
            logs.add(CodeLogEntry((System.currentTimeMillis() + 1).toString(), "INFO", "Executing task in sandbox..."))
            commandInput = ""
          }
        },
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(ClaudeTerracotta)
      ) {
        Icon(
          imageVector = Icons.Default.ArrowUpward,
          contentDescription = "Send Command",
          tint = Color.White,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}
