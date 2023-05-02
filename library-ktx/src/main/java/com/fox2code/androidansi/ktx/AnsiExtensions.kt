@file:Suppress("NOTHING_TO_INLINE") // Aliases to public API.

package com.fox2code.androidansi.ktx

import android.app.ActionBar
import android.app.Activity
import android.app.AlertDialog
import android.content.res.Resources
import android.text.Spannable
import android.widget.TextView
import androidx.annotation.StringRes
import com.fox2code.androidansi.AnsiContext
import com.fox2code.androidansi.AnsiParser
import com.fox2code.androidansi.AnsiTextSpan
import org.jetbrains.annotations.Contract

const val FLAG_ANSI_PARSE_DISABLE_COLORS: Int = AnsiParser.FLAG_PARSE_DISABLE_COLORS
const val FLAG_ANSI_PARSE_DISABLE_ATTRIBUTES: Int = AnsiParser.FLAG_PARSE_DISABLE_ATTRIBUTES
const val FLAG_ANSI_PARSE_DISABLE_EXTRAS_COLORS: Int = AnsiParser.FLAG_PARSE_DISABLE_EXTRAS_COLORS
const val FLAG_ANSI_PARSE_DISABLE_SUBSCRIPT: Int = AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT
const val FLAGS_ANSI_PARSE_DISABLE_ALL: Int = AnsiParser.FLAGS_DISABLE_ALL

@Contract(pure = true)
inline fun String.patchAnsiEscapeSequences(): String = AnsiParser.patchEscapeSequences(this)
@Contract(pure = true)
inline fun String.removeAnsiEscapeSequences(): String = AnsiParser.removeEscapeSequences(this)

inline fun String.parseAsAnsi(context: AnsiContext? = null, parseFlags: Int = 0): Spannable {
    return if (context == null) {
        AnsiParser.parseAsSpannable(this, parseFlags)
    } else {
        context.parseAsSpannable(this, parseFlags)
    }
}

@Contract(pure = true)
inline fun Resources.getAnsiText(@StringRes text: Int, parseFlags: Int = 0): Spannable {
    return AnsiParser.parseAsSpannable(this.getString(text), parseFlags)
}

inline fun Spannable.clearAnsiSpans() {
    this.getSpans(0, this.length, AnsiTextSpan::class.java).forEach { removeSpan(it) }
}

inline fun TextView.setAnsiText(text: String, parseFlags: Int = 0) {
    this.text = AnsiParser.parseAsSpannable(text, parseFlags)
}

inline fun Activity.setAnsiTitle(text: String, parseFlags: Int = 0) {
    this.title = AnsiParser.parseAsSpannable(text, parseFlags)
}

inline fun AlertDialog.setAnsiTitle(text: String, parseFlags: Int = 0) {
    this.setTitle(AnsiParser.parseAsSpannable(text, parseFlags))
}

inline fun AlertDialog.setAnsiMessage(text: String, parseFlags: Int = 0) {
    this.setMessage(AnsiParser.parseAsSpannable(text, parseFlags))
}

inline fun ActionBar.setAnsiTitle(text: String, parseFlags: Int = 0) {
    this.title = AnsiParser.parseAsSpannable(text, parseFlags)
}

inline fun ActionBar.setAnsiSubtitle(text: String, parseFlags: Int = 0) {
    this.subtitle = AnsiParser.parseAsSpannable(text, parseFlags)
}