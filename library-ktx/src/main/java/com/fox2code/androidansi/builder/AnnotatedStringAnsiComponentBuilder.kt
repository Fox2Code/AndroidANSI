package com.fox2code.androidansi.builder

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.Builder
import com.fox2code.androidansi.AnsiContext
import com.fox2code.androidansi.ktx.toAnsiSpanStyle

class AnnotatedStringAnsiComponentBuilder : AnsiComponentBuilder<AnnotatedString>() {
    private val builder: Builder = Builder()
    private var used = false
    override fun notifyUse() {
        check(!used) { "AnnotatedStringAnsiComponentBuilder can only be used once!" }
        used = true
    }

    override fun appendWithSpan(buffer: String, bufferStart: Int, bufferEnd: Int, ansiContext: AnsiContext, visibleStart: Int, visibleEnd: Int) {
        builder.append(buffer, bufferStart, bufferEnd)
        builder.addStyle(ansiContext.toAnsiSpanStyle(), visibleStart, visibleEnd)
    }

    override fun build(): AnnotatedString {
        return builder.toAnnotatedString()
    }
}