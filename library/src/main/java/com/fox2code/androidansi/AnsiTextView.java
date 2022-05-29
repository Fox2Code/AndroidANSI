package com.fox2code.androidansi;

import static com.fox2code.androidansi.AnsiParser.parseAsSpannable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.emoji2.viewsintegration.EmojiTextViewHelper;

public class AnsiTextView extends TextView {
    public static final int FLAG_PARSE_DISABLE_COLORS = AnsiParser.FLAG_PARSE_DISABLE_COLORS;
    public static final int FLAG_PARSE_DISABLE_ATTRIBUTES = AnsiParser.FLAG_PARSE_DISABLE_ATTRIBUTES;
    public static final int FLAG_PARSE_DISABLE_EXTRAS_COLORS = AnsiParser.FLAG_PARSE_DISABLE_EXTRAS_COLORS;
    public static final int FLAG_PARSE_DISABLE_SUBSCRIPT = AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT;

    private Object mEmojiTextViewHelper; // Hold reference without requiring hard references
    private int parseFlags = 0;

    public AnsiTextView(Context context) {
        super(context);
        preInit();
    }

    public AnsiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        preInit();
        readAttr(context, attrs);
    }

    public AnsiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        preInit();
        readAttr(context, attrs);
    }

    public void setAnsiText(@NonNull String ansiText) {
        super.setText(parseAsSpannable(ansiText, this.parseFlags));
    }

    public void setAnsiText(@NonNull String ansiText, int parseFlags) {
        super.setText(parseAsSpannable(ansiText, parseFlags));
    }

    private void preInit() {
        try { // Using "new" for optional classes is ok with ART
            mEmojiTextViewHelper = new EmojiTextViewHelper(this, false);
            ((EmojiTextViewHelper) mEmojiTextViewHelper).updateTransformationMethod();
        } catch (Exception ignored) {
            mEmojiTextViewHelper = null;
        }
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray a = null;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.AnsiTextView);
            @NonNull String text = a.getString(R.styleable.AnsiTextView_ansiText);
            int parseFlags = a.getInt(R.styleable.AnsiTextView_ansiText, -1);
            a.recycle(); a = null;
            if (parseFlags != -1) {
                this.parseFlags = parseFlags;
            } else {
                parseFlags = this.parseFlags;
            }
            if (text != null) {
                super.setText(parseAsSpannable(text, parseFlags));
            }
        } catch (Resources.NotFoundException e) {
            Log.w("AnsiTextView", "Failed to resolve ansiText resource!", e);
        } finally {
            if (a != null) a.recycle();
        }
    }

    @Override
    public void setFilters(InputFilter[] filters) {
        if (this.mEmojiTextViewHelper != null) {
            filters = ((EmojiTextViewHelper) this.mEmojiTextViewHelper).getFilters(filters);
        }
        super.setFilters(filters);
    }

    @Override
    public void setAllCaps(boolean allCaps) {
        super.setAllCaps(allCaps);
        if (this.mEmojiTextViewHelper != null) {
            ((EmojiTextViewHelper) this.mEmojiTextViewHelper).setAllCaps(allCaps);
        }
    }
}
