package com.fox2code.androidansi.builder;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

import androidx.annotation.NonNull;

import com.fox2code.androidansi.AnsiContext;

public final class SpannableAnsiComponentBuilder extends AnsiComponentBuilder<Spannable> {
    private final SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    private boolean used = false;

    @Override
    public void notifyUse() {
        if (this.used) {
            throw new IllegalStateException("SpannableAnsiComponentBuilder can only be used once!");
        }
        this.used = true;
    }

    @Override
    public void appendWithSpan(@NonNull String buffer, int bufferStart, int bufferEnd,
                               @NonNull AnsiContext ansiContext, int visibleStart, int visibleEnd) {
        spannableStringBuilder.append(buffer, bufferStart, bufferEnd);
        spannableStringBuilder.setSpan(ansiContext.toAnsiTextSpan(), visibleStart, visibleEnd, 0);
    }

    @NonNull
    @Override
    public Spannable build() {
        return spannableStringBuilder;
    }
}
