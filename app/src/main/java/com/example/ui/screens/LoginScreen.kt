package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ClaudeSunburst

/**
 * High-fidelity Claude Login Screen matching Screenshot 1.
 * Features:
 * - Top centered Anthropic terracotta asterisk + "Claude" in Editorial Serif
 * - Hand-crafted hero illustration with terracotta neural network nodes intertwined with white looping doodle ribbon
 * - Centered title "The AI for problem\nsolvers" in Editorial Serif
 * - White pill button: "Continue with Google"
 * - Hairline horizontal divider with centered "OR"
 * - Rounded input container: "Enter your email"
 * - Centered legal disclaimer with underlined links: Consumer Terms, Usage Policy, Privacy Policy
 */
@Composable
fun LoginScreen(
  onLoginSuccess: (email: String) -> Unit,
  modifier: Modifier = Modifier,
) {
  var emailInput by remember { mutableStateOf("") }
  val scrollState = rememberScrollState()

  val bgCanvas = Color(0xFF141413)
  val textPrimary = Color(0xFFEDEBE6)
  val textSecondary = Color(0xFF8E8C85)
  val dividerColor = Color(0xFF2C2B29)
  val inputBg = Color(0xFF1C1C1A)
  val inputBorder = Color(0xFF2E2D2B)
  val terracotta = Color(0xFFD97757)

  Box(
    modifier = modifier
      .fillMaxSize()
      .background(bgCanvas)
      .statusBarsPadding()
      .navigationBarsPadding()
      .testTag("login_screen")
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(horizontal = 24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      // TOP SECTION: Logo + Illustration + Headline
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Top Brand Header: Terracotta Asterisk + "Claude" in Serif
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          ClaudeSunburst(
            size = 28.dp,
            color = terracotta
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Claude",
            fontFamily = FontFamily.Serif,
            fontSize = 28.sp,
            fontWeight = FontWeight.Normal,
            color = textPrimary,
            letterSpacing = (-0.3).sp
          )
        }

        Spacer(modifier = Modifier.height(42.dp))

        // Hero Illustration: Terracotta Knowledge Graph intertwined with White Looping Cord
        ClaudeLoginHeroIllustration(
          modifier = Modifier
            .size(width = 240.dp, height = 200.dp)
            .testTag("login_hero_illustration")
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Headline
        Text(
          text = "The AI for problem\nsolvers",
          fontFamily = FontFamily.Serif,
          fontSize = 32.sp,
          fontWeight = FontWeight.Normal,
          color = textPrimary,
          textAlign = TextAlign.Center,
          lineHeight = 40.sp,
          letterSpacing = (-0.5).sp
        )
      }

      Spacer(modifier = Modifier.height(40.dp))

      // BOTTOM SECTION: Google Button, OR divider, Email Input, Legal Disclaimer
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // 1. "Continue with Google" White Pill Button
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(27.dp))
            .background(Color.White)
            .clickable {
              onLoginSuccess("jishnupg2005@gmail.com")
            }
            .padding(horizontal = 20.dp)
            .testTag("login_button_google"),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          GoogleColorIcon(modifier = Modifier.size(20.dp))
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Continue with Google",
            color = Color(0xFF141413),
            fontSize = 16.5.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.1.sp
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 2. "OR" Divider
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .weight(1f)
              .height(0.8.dp)
              .background(dividerColor)
          )
          Text(
            text = "OR",
            color = Color(0xFF7A7873),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 14.dp)
          )
          Box(
            modifier = Modifier
              .weight(1f)
              .height(0.8.dp)
              .background(dividerColor)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 3. "Enter your email" Input Field Container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(inputBg)
            .border(1.dp, inputBorder, RoundedCornerShape(14.dp))
            .padding(horizontal = 20.dp)
            .testTag("login_input_email"),
          contentAlignment = Alignment.Center
        ) {
          if (emailInput.isEmpty()) {
            Text(
              text = "Enter your email",
              color = Color(0xFF8E8C85),
              fontSize = 16.sp,
              fontWeight = FontWeight.Normal,
              textAlign = TextAlign.Center
            )
          }

          BasicTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            singleLine = true,
            textStyle = TextStyle(
              color = textPrimary,
              fontSize = 16.sp,
              textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Color.White),
            keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email,
              imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
              onDone = {
                if (emailInput.isNotBlank()) {
                  onLoginSuccess(emailInput.trim())
                } else {
                  onLoginSuccess("jishnupg2005@gmail.com")
                }
              }
            ),
            modifier = Modifier.fillMaxWidth()
          )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // 4. Terms and Privacy Policy Disclaimer
        val termsText = buildAnnotatedString {
          append("By continuing, you agree to\nAnthropic's ")
          withStyle(
            SpanStyle(
              color = textPrimary,
              textDecoration = TextDecoration.Underline
            )
          ) {
            append("Consumer Terms")
          }
          append(" and\n")
          withStyle(
            SpanStyle(
              color = textPrimary,
              textDecoration = TextDecoration.Underline
            )
          ) {
            append("Usage Policy")
          }
          append(", and acknowledge their\n")
          withStyle(
            SpanStyle(
              color = textPrimary,
              textDecoration = TextDecoration.Underline
            )
          ) {
            append("Privacy Policy")
          }
          append(".")
        }

        Text(
          text = termsText,
          color = textSecondary,
          fontSize = 13.5.sp,
          lineHeight = 20.sp,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

/**
 * Custom Hero Illustration reproducing Screenshot 1:
 * Neural network of terracotta nodes connected with straight edges,
 * intertwined with a continuous flowing white doodle loop cord.
 */
@Composable
private fun ClaudeLoginHeroIllustration(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    val terracotta = Color(0xFFD97757)
    val white = Color.White

    // Node coordinates normalized
    val n1 = Offset(w * 0.44f, h * 0.16f) // Top node
    val n2 = Offset(w * 0.28f, h * 0.32f) // Left upper
    val n3 = Offset(w * 0.22f, h * 0.48f) // Left lower
    val n4 = Offset(w * 0.45f, h * 0.42f) // Center hub
    val n5 = Offset(w * 0.82f, h * 0.36f) // Right upper
    val n6 = Offset(w * 0.50f, h * 0.62f) // Center bottom

    // Draw terracotta network lines
    val lineStroke = 3.dp.toPx()
    drawLine(terracotta, n1, n4, strokeWidth = lineStroke)
    drawLine(terracotta, n2, n4, strokeWidth = lineStroke)
    drawLine(terracotta, n3, n4, strokeWidth = lineStroke)
    drawLine(terracotta, n4, n5, strokeWidth = lineStroke)
    drawLine(terracotta, n4, n6, strokeWidth = lineStroke)
    drawLine(terracotta, n1, n5, strokeWidth = lineStroke)
    drawLine(terracotta, n2, n3, strokeWidth = lineStroke)

    // Draw terracotta nodes (circles)
    val nodeRadius = 8.dp.toPx()
    listOf(n1, n2, n3, n4, n5, n6).forEach { node ->
      drawCircle(terracotta, radius = nodeRadius, center = node)
    }

    // Continuous White Doodle Cord Path
    val ribbonPath = Path().apply {
      // Start on left wavy combs
      moveTo(w * 0.08f, h * 0.60f)
      // Wave 1
      cubicTo(w * 0.08f, h * 0.50f, w * 0.12f, h * 0.50f, w * 0.12f, h * 0.62f)
      cubicTo(w * 0.12f, h * 0.68f, w * 0.15f, h * 0.68f, w * 0.15f, h * 0.54f)
      // Wave 2
      cubicTo(w * 0.15f, h * 0.49f, w * 0.19f, h * 0.49f, w * 0.19f, h * 0.64f)
      cubicTo(w * 0.19f, h * 0.70f, w * 0.23f, h * 0.70f, w * 0.23f, h * 0.52f)
      // Wave 3
      cubicTo(w * 0.23f, h * 0.48f, w * 0.27f, h * 0.48f, w * 0.27f, h * 0.64f)
      cubicTo(w * 0.27f, h * 0.70f, w * 0.31f, h * 0.70f, w * 0.31f, h * 0.50f)
      // Wave 4
      cubicTo(w * 0.31f, h * 0.47f, w * 0.35f, h * 0.47f, w * 0.35f, h * 0.62f)
      cubicTo(w * 0.35f, h * 0.72f, w * 0.40f, h * 0.72f, w * 0.40f, h * 0.56f)

      // Bridge across center
      cubicTo(w * 0.40f, h * 0.42f, w * 0.46f, h * 0.45f, w * 0.50f, h * 0.58f)
      cubicTo(w * 0.54f, h * 0.70f, w * 0.60f, h * 0.65f, w * 0.62f, h * 0.50f)

      // Climb up towards top right
      cubicTo(w * 0.64f, h * 0.38f, w * 0.72f, h * 0.20f, w * 0.80f, h * 0.20f)
      cubicTo(w * 0.86f, h * 0.20f, w * 0.85f, h * 0.35f, w * 0.76f, h * 0.45f)

      // Loop downwards and swirl in wide sweep
      cubicTo(w * 0.66f, h * 0.55f, w * 0.62f, h * 0.65f, w * 0.64f, h * 0.78f)
      cubicTo(w * 0.66f, h * 0.94f, w * 0.84f, h * 0.95f, w * 0.87f, h * 0.80f)
      cubicTo(w * 0.90f, h * 0.66f, w * 0.78f, h * 0.60f, w * 0.73f, h * 0.60f)
    }

    drawPath(
      path = ribbonPath,
      color = white,
      style = Stroke(
        width = 4.dp.toPx(),
        cap = StrokeCap.Round,
        join = StrokeJoin.Round
      )
    )
  }
}

/**
 * 4-color Google G icon
 */
@Composable
private fun GoogleColorIcon(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    val blue = Color(0xFF4285F4)
    val red = Color(0xFFEA4335)
    val yellow = Color(0xFFFBBC05)
    val green = Color(0xFF34A853)

    val strokeWidth = w * 0.22f
    val radius = (w - strokeWidth) / 2f
    val center = Offset(w / 2f, h / 2f)

    // Red arc (top)
    drawArc(
      color = red,
      startAngle = 180f,
      sweepAngle = 120f,
      useCenter = false,
      topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
      size = androidx.compose.ui.geometry.Size(w - strokeWidth, h - strokeWidth),
      style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )

    // Yellow arc (left-bottom)
    drawArc(
      color = yellow,
      startAngle = 120f,
      sweepAngle = 60f,
      useCenter = false,
      topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
      size = androidx.compose.ui.geometry.Size(w - strokeWidth, h - strokeWidth),
      style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )

    // Green arc (bottom-right)
    drawArc(
      color = green,
      startAngle = 0f,
      sweepAngle = 120f,
      useCenter = false,
      topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
      size = androidx.compose.ui.geometry.Size(w - strokeWidth, h - strokeWidth),
      style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )

    // Blue horizontal crossbar and arc (right)
    drawArc(
      color = blue,
      startAngle = 300f,
      sweepAngle = 60f,
      useCenter = false,
      topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
      size = androidx.compose.ui.geometry.Size(w - strokeWidth, h - strokeWidth),
      style = Stroke(strokeWidth, cap = StrokeCap.Round)
    )

    // Blue crossbar
    drawLine(
      color = blue,
      start = Offset(center.x, center.y),
      end = Offset(w - strokeWidth / 4, center.y),
      strokeWidth = strokeWidth,
      cap = StrokeCap.Square
    )
  }
}
