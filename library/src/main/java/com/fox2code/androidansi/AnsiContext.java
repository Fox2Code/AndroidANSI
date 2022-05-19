package com.fox2code.androidansi;

import android.graphics.Color;
import android.text.Spannable;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import java.util.Objects;

public final class AnsiContext {
    public static final AnsiContext DARK = new AnsiContext(Color.WHITE, Color.BLACK, true);
    public static final AnsiContext LIGHT = new AnsiContext(Color.BLACK, Color.WHITE, true);
    @ColorInt final int defaultForeground;
    @ColorInt final int defaultBackground;
    final boolean isImmutable;
    ColorTransformer transformer;
    @ColorInt int foreground;
    @ColorInt int background;
    @ColorInt int underline;
    int style;

    private AnsiContext(@ColorInt int defaultForeground,@ColorInt int defaultBackground,
                        boolean isImmutable) {
        this.defaultForeground = defaultForeground;
        this.defaultBackground = defaultBackground;
        this.foreground = defaultForeground;
        this.background = defaultBackground;
        this.isImmutable = isImmutable;
        this.transformer = AnsiConstants.NO_OP_TRANSFORMER;
    }

    public AnsiContext(@ColorInt int defaultForeground,@ColorInt int defaultBackground) {
        this(defaultForeground, defaultBackground, AnsiConstants.NO_OP_TRANSFORMER);
    }

    public AnsiContext(@ColorInt int defaultForeground,@ColorInt int defaultBackground,
                       @NonNull ColorTransformer transformer) {
        this.defaultForeground = defaultForeground;
        this.defaultBackground = defaultBackground;
        this.foreground = defaultForeground;
        this.background = defaultBackground;
        this.underline = Color.TRANSPARENT;
        this.isImmutable = false;
        this.transformer = Objects.requireNonNull(transformer);
    }

    private AnsiContext(AnsiContext ansiContext, boolean isImmutable) {
        this.transformer = ansiContext.transformer;
        this.defaultForeground = ansiContext.defaultForeground;
        this.defaultBackground = ansiContext.defaultBackground;
        this.foreground = ansiContext.foreground;
        this.background = ansiContext.background;
        this.underline = ansiContext.underline;
        this.style = ansiContext.style;
        this.isImmutable = isImmutable;
    }

    @ColorInt
    public int getDefaultForeground() {
        return defaultForeground;
    }

    @ColorInt
    public int getDefaultBackground() {
        return defaultBackground;
    }

    @ColorInt
    public int getForeground() {
        return foreground;
    }

    public void setForeground(@ColorInt int foreground) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.foreground = foreground;
    }

    @ColorInt
    public int getBackground() {
        return background;
    }

    public void setBackground(@ColorInt int background) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.background = background;
    }

    @ColorInt
    public int getUnderline() {
        int underline = this.underline;
        return underline == Color.TRANSPARENT ?
                this.foreground : underline;
    }

    public void setUnderline(@ColorInt int underline) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.underline = underline;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.style = style;
    }

    public ColorTransformer getTransformer() {
        return transformer;
    }

    public void setTransformer(ColorTransformer transformer) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.transformer = transformer == null ?
                AnsiConstants.NO_OP_TRANSFORMER : transformer;
    }

    public AnsiContext copy() {
        return new AnsiContext(this, this.isImmutable &&
                this != DARK && this != LIGHT);
    }

    public AnsiContext asMutable() {
        return this.isImmutable ? new AnsiContext(this, false) : this;
    }

    public AnsiContext asImmutable() {
        return this.isImmutable ? this : new AnsiContext(this, true);
    }

    @Deprecated
    public boolean isStaticContext() {
        return this.isImmutable;
    }

    public void reset() {
        this.foreground = this.defaultForeground;
        this.background = this.defaultBackground;
        this.underline = Color.TRANSPARENT;
        this.style = 0;
    }

    public void copyStateFrom(AnsiContext ansiContext) {
        if (this.isImmutable)
            throw new IllegalStateException();
        this.foreground = ansiContext.foreground;
        this.background = ansiContext.background;
        this.underline = ansiContext.underline;
        this.style = ansiContext.style;
    }

    public void copyStateTo(AnsiContext ansiContext) {
        if (ansiContext.isImmutable)
            throw new IllegalStateException();
        ansiContext.foreground = this.foreground;
        ansiContext.background = this.background;
        ansiContext.underline = this.underline;
        ansiContext.style = this.style;
    }

    public Spannable parseAsSpannable(@NonNull AnsiTextView view, @NonNull String text) {
        return view.parseAsSpannable(text, this);
    }

    public Spannable parseAsSpannable(@NonNull AnsiTextView view, @NonNull String text, int parseFlags) {
        return view.parseAsSpannable(text, this, parseFlags);
    }

    public void setAnsiText(@NonNull AnsiTextView view, @NonNull String ansiText) {
        view.setText(view.parseAsSpannable(ansiText, this));
    }

    public void setAnsiText(@NonNull AnsiTextView view, @NonNull String ansiText, int parseFlags) {
        view.setText(view.parseAsSpannable(ansiText, this, parseFlags));
    }

    public AnsiTextSpan toAnsiTextSpan() {
        return new AnsiTextSpan(
                this.foreground == this.defaultForeground ? Color.TRANSPARENT : this.foreground,
                this.background == this.defaultBackground ? Color.TRANSPARENT : this.background,
                this.underline == foreground ? Color.TRANSPARENT : this.underline,
                this.style);
    }
}
