@file:Suppress("NOTHING_TO_INLINE") // Aliases to public API.

package com.fox2code.androidansi.ktx

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import com.fox2code.androidansi.AnsiConstants
import com.fox2code.androidansi.AnsiContext
import com.fox2code.androidansi.AnsiParser
import com.fox2code.androidansi.builder.AnnotatedStringAnsiComponentBuilder
import org.jetbrains.annotations.Contract

private fun intAsColor(color: Int): Color {
    return Color(android.graphics.Color.red(color),
            android.graphics.Color.green(color),
            android.graphics.Color.blue(color))
}

@Contract(pure = true)
fun AnsiContext.toAnsiSpanStyle(): SpanStyle {
    var baselineShift: BaselineShift = BaselineShift.None
    if (this.style and AnsiConstants.FLAG_STYLE_SUBSCRIPT != 0) {
        baselineShift = BaselineShift.Subscript
    } else if (this.style and AnsiConstants.FLAG_STYLE_SUPERSCRIPT != 0) {
        baselineShift = BaselineShift.Superscript
    }
    val backgroundColor: Color = when(
            this.background.toULong() or 0xFF000000.toULong()) {
        this.defaultBackground.toULong() -> Color.Unspecified
        0xFF000000.toULong() -> Color.Black
        0xFF444444.toULong() -> Color.DarkGray
        0xFF888888.toULong() -> Color.Gray
        0xFFCCCCCC.toULong() -> Color.LightGray
        0xFFFFFFFF.toULong() -> Color.White
        0xFFFF0000.toULong() -> Color.Red
        0xFF00FF00.toULong() -> Color.Green
        0xFF0000FF.toULong() -> Color.Blue
        0xFFFFFF00.toULong() -> Color.Yellow
        0xFFFFFF00.toULong() -> Color.Cyan
        0xFFFFFF00.toULong() -> Color.Magenta
        else -> intAsColor(this.background)
    }
    var foregroundColor: Color = when(
            this.foreground.toULong() or 0xFF000000.toULong()) {
        this.defaultForeground.toULong() -> Color.Unspecified
        0xFF000000.toULong() -> Color.Black
        0xFF444444.toULong() -> Color.DarkGray
        0xFF888888.toULong() -> Color.Gray
        0xFFCCCCCC.toULong() -> Color.LightGray
        0xFFFFFFFF.toULong() -> Color.White
        0xFFFF0000.toULong() -> Color.Red
        0xFF00FF00.toULong() -> Color.Green
        0xFF0000FF.toULong() -> Color.Blue
        0xFFFFFF00.toULong() -> Color.Yellow
        0xFFFFFF00.toULong() -> Color.Cyan
        0xFFFFFF00.toULong() -> Color.Magenta
        else -> intAsColor(foreground)
    }
    val textDecoration: TextDecoration = when(this.style and (
            AnsiConstants.FLAG_STYLE_UNDERLINE or AnsiConstants.FLAG_STYLE_STRIKE
            )) {
        AnsiConstants.FLAG_STYLE_UNDERLINE or
                AnsiConstants.FLAG_STYLE_STRIKE ->
            TextDecoration.Underline.plus(TextDecoration.LineThrough)
        AnsiConstants.FLAG_STYLE_UNDERLINE -> TextDecoration.Underline
        AnsiConstants.FLAG_STYLE_STRIKE -> TextDecoration.LineThrough
        else -> TextDecoration.None
    }
    if (this.style and AnsiConstants.FLAG_STYLE_DIM != 0) {
        foregroundColor = foregroundColor.copy(alpha = (9F/16F))
    }
    val fontStyle: FontStyle? = when(this.style and AnsiConstants.FLAG_STYLE_ITALIC) {
        AnsiConstants.FLAG_STYLE_ITALIC -> FontStyle.Italic
        else -> null
    }
    val fontWeight: FontWeight? = when(this.style and AnsiConstants.FLAG_STYLE_BOLD) {
        AnsiConstants.FLAG_STYLE_BOLD -> FontWeight.Bold
        else -> null
    }
    return SpanStyle(baselineShift = baselineShift,
            textDecoration = textDecoration,
            background = backgroundColor,
            color = foregroundColor,
            fontStyle = fontStyle,
            fontWeight = fontWeight)
}

@Contract(pure = true)
inline fun AnsiContext.parseAsAnsiAnnotatedString(text: String, parseFlags: Int = 0): AnnotatedString {
    return AnsiParser.parseWithBuilder(AnnotatedStringAnsiComponentBuilder(), text, this, parseFlags)
}

@Contract(pure = true)
inline fun String.parseAsAnsiAnnotatedString(context: AnsiContext? = null, parseFlags: Int = 0): AnnotatedString {
    return AnsiParser.parseWithBuilder(AnnotatedStringAnsiComponentBuilder(), this, context, parseFlags)
}