package com.example.domain.ai

import com.example.domain.interfaces.AIProvider
import com.example.domain.interfaces.HermesEvent
import com.example.domain.model.Attachment
import com.example.domain.model.ClaudeModel
import java.util.UUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HermesAdapter : AIProvider {

  override fun streamMessage(
    prompt: String,
    conversationId: String,
    model: ClaudeModel,
    thinkingEnabled: Boolean,
    attachments: List<Attachment>,
  ): Flow<HermesEvent> = flow {
    val messageId = UUID.randomUUID().toString()

    emit(HermesEvent.MessageStart(messageId, conversationId, model.displayName))

    val lower = prompt.lowercase()
    val isCodeRequest = lower.contains("code") || lower.contains("app") || lower.contains("android") || lower.contains("kotlin") || lower.contains("function")
    val isToolRequest = lower.contains("search") || lower.contains("find") || lower.contains("run") || lower.contains("execute") || lower.contains("build")

    // 1. If thinking is enabled and model supports it, stream thinking deltas
    if (thinkingEnabled && model.supportsThinking) {
      emit(HermesEvent.ThinkingStart(messageId))
      delay(120)

      val thinkingSteps = listOf(
        "Analyzing user prompt: \"$prompt\"...\n",
        "Assessing domain constraints, conversational tone, and context requirements.\n",
        if (isCodeRequest) "Formulating idiomatic Kotlin Jetpack Compose architecture with clean separation of concerns.\n" else "Synthesizing an authoritative, clear, and nuanced response.\n",
        "Structuring key arguments, ensuring clear markdown headings, code blocks, and practical implementation details.\n",
        "Verification complete. Ready to produce output."
      )

      for (step in thinkingSteps) {
        emit(HermesEvent.ThinkingDelta(messageId, step))
        delay(140)
      }

      emit(HermesEvent.ThinkingComplete(messageId, durationSeconds = 3))
      delay(100)
    }

    // 2. If prompt involves tool execution or approval, emit tool events
    if (isToolRequest) {
      val toolId = "tool_" + UUID.randomUUID().toString().take(8)
      emit(
        HermesEvent.ToolStart(
          messageId = messageId,
          toolId = toolId,
          toolName = if (lower.contains("search")) "web_search" else "execute_bash",
          inputJson = if (lower.contains("search")) "{\"query\": \"$prompt\"}" else "{\"command\": \"gradle :app:assembleDebug\"}"
        )
      )
      delay(250)
      emit(HermesEvent.ToolUpdate(messageId, toolId, "Running command in sandbox environment...\n"))
      delay(200)

      if (lower.contains("deploy") || lower.contains("push") || lower.contains("delete")) {
        emit(
          HermesEvent.ApprovalRequired(
            messageId = messageId,
            toolId = toolId,
            command = "git push origin main --force",
            riskDescription = "Modifies remote repository state."
          )
        )
      } else {
        emit(
          HermesEvent.ToolResult(
            messageId = messageId,
            toolId = toolId,
            outputJson = "{\"status\": \"SUCCESS\", \"exit_code\": 0, \"output\": \"Process finished with return code 0\"}"
          )
        )
      }
      delay(150)
    }

    // 3. Formulate literary, high-fidelity response text
    val responseBody = buildResponseText(prompt, isCodeRequest, model)

    // 4. If code request, also produce an artifact
    if (isCodeRequest) {
      emit(
        HermesEvent.ArtifactProduced(
          messageId = messageId,
          artifactId = "art_" + UUID.randomUUID().toString().take(8),
          title = "ClaudeButton.kt",
          language = "kotlin",
          code = """
            package com.example.ui.components

            import androidx.compose.foundation.layout.PaddingValues
            import androidx.compose.foundation.shape.RoundedCornerShape
            import androidx.compose.material3.Button
            import androidx.compose.material3.ButtonDefaults
            import androidx.compose.material3.Text
            import androidx.compose.runtime.Composable
            import androidx.compose.ui.Modifier
            import androidx.compose.ui.unit.dp
            import com.example.ui.theme.ClaudeTerracotta
            import com.example.ui.theme.ClaudeTextInverseLight

            @Composable
            fun ClaudeButton(
              text: String,
              onClick: () -> Unit,
              modifier: Modifier = Modifier,
            ) {
              Button(
                onClick = onClick,
                modifier = modifier,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = ClaudeTerracotta,
                  contentColor = ClaudeTextInverseLight
                ),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
              ) {
                Text(text = text)
              }
            }
          """.trimIndent()
        )
      )
    }

    // 5. Stream words as realistic message deltas
    val words = responseBody.split(" ")
    for (word in words) {
      emit(HermesEvent.MessageDelta(messageId, "$word "))
      delay(30)
    }

    emit(HermesEvent.MessageComplete(messageId))
    emit(HermesEvent.AgentComplete(messageId, summary = "Completed response generation successfully."))
  }

  private fun buildResponseText(prompt: String, isCodeRequest: Boolean, model: ClaudeModel): String {
    return if (isCodeRequest) {
      """
        Here is the implementation crafted to adhere to the Claude design system and modern Android patterns.

        ### Key Structural Principles

        1. **Terracotta Accent**: Utilizes Anthropic's signature `#D97757` for primary call-to-action surfaces.
        2. **Corner Geometry**: Pill-shaped bounds with 20–26dp radius for approachable, tactile touch interactions.
        3. **Edge-to-Edge Integration**: Fully adapts to system navigation bars and IME virtual keyboard heights.

        ```kotlin
        // Claude Component Sample
        @Composable
        fun ClaudeHeroGreeting(title: String) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        ```

        Feel free to inspect the attached artifact for the complete standalone component code.
      """.trimIndent()
    } else {
      """
        I'm happy to help you with that!

        In exploring **$prompt**, we can look at this from a few complementary angles:

        - **Clarity & Intent**: Establishing a well-grounded perspective ensures decisions align with core goals.
        - **Practical Execution**: Moving from concept into actionable, systematic steps that compound value.
        - **Refinement & Polish**: Elevating the outcome through attention to typography, spacing, and interaction nuance.

        Would you like me to dive deeper into any particular aspect, or explore a concrete example together?
      """.trimIndent()
    }
  }
}
