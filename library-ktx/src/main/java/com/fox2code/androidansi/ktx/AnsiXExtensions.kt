@file:Suppress("NOTHING_TO_INLINE") // Aliases to public API.

package com.fox2code.androidansi.ktx

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.fox2code.androidansi.AnsiParser

inline fun AppCompatDialog.setAnsiTitle(text: String, parseFlags: Int = 0) {
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