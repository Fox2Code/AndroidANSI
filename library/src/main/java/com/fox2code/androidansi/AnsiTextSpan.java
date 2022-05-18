package com.fox2code.androidansi;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextPaint;
import android.text.style.StyleSpan;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

// IPC for Spannable will strip color and only return a StyleSpan
public final class AnsiTextSpan extends StyleSpan implements Parcelable {
    private static final int DIM_ALPHA = 0x90;
    public static final Creator<AnsiTextSpan> CREATOR
            = new Parcelable.Creator<AnsiTextSpan>() {
        public AnsiTextSpan createFromParcel(Parcel in) {
            return new AnsiTextSpan(in.readInt(), in.readInt(), in.readInt(), in.readInt());
        }

        public AnsiTextSpan[] newArray(int size) {
            return new AnsiTextSpan[size];
        }
    };

    @ColorInt private final int mForegroundColor;
    @ColorInt private final int mBackgroundColor;
    @ColorInt private final int mUnderlineColor;
    private final int mAnsiStyle;

    public AnsiTextSpan(@ColorInt int foreground,@ColorInt int background,
                        @ColorInt int underline, int style) {
        super(fromAnsiToTypeface(style));
        this.mForegroundColor = foreground;
        this.mBackgroundColor = background;
        this.mUnderlineColor = underline;
        this.mAnsiStyle = style;
    }

    private static int fromAnsiToTypeface(int ansiStyle) {
        int typefaceStyle = 0;
        if ((ansiStyle & AnsiConstants.FLAG_STYLE_BOLD) != 0)
            typefaceStyle |= Typeface.BOLD;
        if ((ansiStyle & AnsiConstants.FLAG_STYLE_ITALIC) != 0)
            typefaceStyle |= Typeface.ITALIC;
        return typefaceStyle;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mForegroundColor);
        dest.writeInt(this.mBackgroundColor);
        dest.writeInt(this.mUnderlineColor);
        dest.writeInt(this.mAnsiStyle);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint textPaint) {
        super.updateDrawState(textPaint);
        int color = this.mForegroundColor;
        if (color == Color.TRANSPARENT && Color.alpha(textPaint.getColor()) != DIM_ALPHA &&
                (this.mAnsiStyle & AnsiConstants.FLAG_STYLE_DIM) != 0) {
            color = textPaint.getColor();
        }
        if (color != Color.TRANSPARENT) {
            if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_DIM) != 0) {
                color = Color.argb(DIM_ALPHA, Color.red(color),
                        Color.green(color), Color.blue(color));
            }
            textPaint.setColor(color);
            textPaint.linkColor = color;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            color = Color.TRANSPARENT;
            if (this.mUnderlineColor != Color.TRANSPARENT) {
                color = this.mUnderlineColor;
            } else if (this.mForegroundColor != Color.TRANSPARENT) {
                color = this.mForegroundColor;
            }
            if (color == Color.TRANSPARENT && Color.alpha(textPaint.underlineColor) != DIM_ALPHA &&
                    (this.mAnsiStyle & AnsiConstants.FLAG_STYLE_DIM) != 0) {
                color = textPaint.underlineColor;
            }
            if (color != Color.TRANSPARENT) {
                if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_DIM) != 0) {
                    color = Color.argb(DIM_ALPHA, Color.red(color),
                            Color.green(color), Color.blue(color));
                }
                textPaint.underlineColor = color;
            }
        }
        if (this.mBackgroundColor != Color.TRANSPARENT) {
            textPaint.bgColor = this.mBackgroundColor;
        }
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_UNDERLINE) != 0) {
            textPaint.setUnderlineText(true);
        }
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_STRIKE) != 0) {
            textPaint.setStrikeThruText(true);
        }
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_SUBSCRIPT) != 0) {
            textPaint.baselineShift -= (int) (textPaint.ascent() / 2);
        }
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_SUPERSCRIPT) != 0) {
            textPaint.baselineShift += (int) (textPaint.ascent() / 2);
        }
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        super.updateMeasureState(textPaint);
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_SUBSCRIPT) != 0) {
            textPaint.baselineShift -= (int) (textPaint.ascent() / 2);
        }
        if ((this.mAnsiStyle & AnsiConstants.FLAG_STYLE_SUPERSCRIPT) != 0) {
            textPaint.baselineShift += (int) (textPaint.ascent() / 2);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        AnsiTextSpan that = (AnsiTextSpan) o;
        return this.mForegroundColor == that.mForegroundColor &&
                this.mBackgroundColor == that.mBackgroundColor &&
                this.mUnderlineColor == that.mUnderlineColor &&
                this.mAnsiStyle == that.mAnsiStyle;
    }

    @Override
    public int hashCode() {
        int result = this.mForegroundColor;
        result = 31 * result + this.mBackgroundColor;
        result = 31 * result + this.mAnsiStyle;
        return result;
    }

    @ColorInt
    public int getForegroundColor() {
        return this.mForegroundColor;
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    @ColorInt
    public int getUnderlineColor() {
        return this.mUnderlineColor;
    }

    public int getAnsiStyle() {
        return this.mAnsiStyle;
    }
}
