package com.fox2code.androidansi.builder;


import androidx.annotation.NonNull;

import com.fox2code.androidansi.AnsiContext;

public final class StringAnsiComponentBuilder extends AnsiComponentBuilder<String> {
    private final StringBuilder stringBuilder = new StringBuilder();
    public final boolean resetOnUse;

    public StringAnsiComponentBuilder() {
        this.resetOnUse = false;
    }

    public StringAnsiComponentBuilder(boolean resetOnUse) {
        this.resetOnUse = resetOnUse;
    }

    @Override
    public void notifyUse() {
        if (this.resetOnUse) {
            stringBuilder.setLength(0);
        }
    }

    @Override
    public void appendWithSpan(@NonNull String buffer, int bufferStart, int bufferEnd,
                               @NonNull AnsiContext ansiContext, int visibleStart, int visibleEnd) {
        stringBuilder.append(buffer, bufferStart, bufferEnd);
    }

    @NonNull
    @Override
    public String build() {
        return stringBuilder.toString();
    }
}
