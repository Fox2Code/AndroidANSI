package com.fox2code.androidansi.builder;

import androidx.annotation.NonNull;

import com.fox2code.androidansi.AnsiContext;

/**
 * This class is called when ANSI parsable text need to be transformed to formatted text.
 */
public abstract class AnsiComponentBuilder<T extends CharSequence> {
    /**
     * Can be overridden if you don't want your builder to be used multiple times.
     */
    public void notifyUse() {}

    /**
     * @param buffer input raw ansi buffer
     * @param bufferStart start index of part of buffer to read
     * @param bufferEnd end index of part of buffer to read
     * @param ansiContext current ansi context call
     * {@link AnsiContext#toAnsiTextSpan()} to set a span.
     * @param visibleStart start of simulated visible index if all strings were appended correctly
     * @param visibleEnd end of simulated visible index if all strings were appended correctly
     */
    public abstract void appendWithSpan(@NonNull String buffer, int bufferStart, int bufferEnd,
                                        @NonNull AnsiContext ansiContext, int visibleStart, int visibleEnd);

    @NonNull
    public abstract T build();
}
