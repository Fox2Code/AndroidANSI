package com.fox2code.androidansi;

import static com.fox2code.androidansi.AnsiParser.parseAsSpannable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class AnsiTextView extends TextView{

    public final int FLAG_PARSE_DISABLE_COLORS = AnsiParser.FLAG_PARSE_DISABLE_COLORS;
    public final int FLAG_PARSE_DISABLE_ATTRIBUTES = AnsiParser.FLAG_PARSE_DISABLE_ATTRIBUTES;
    public final int FLAG_PARSE_DISABLE_EXTRAS_COLORS = AnsiParser.FLAG_PARSE_DISABLE_EXTRAS_COLORS;
    public final int FLAG_PARSE_DISABLE_SUBSCRIPT = AnsiParser.FLAG_PARSE_DISABLE_SUBSCRIPT;

    public AnsiTextView(Context context) {
        super(context);
    }

    public AnsiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttr(context,attrs);
    }

    public AnsiTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        readAttr(context,attrs);
    }

    public void setAnsiText(@NonNull String ansiText) {
        super.setText(parseAsSpannable(ansiText));
    }

    public void setAnsiText(@NonNull String ansiText, int parseFlags) {
        super.setText(parseAsSpannable(ansiText, parseFlags));
    }

    private void readAttr(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnsiTextView);
        @NonNull String text = a.getString(R.styleable.AnsiTextView_setAnsiText) ;
        if (text != null) {
            super.setText(parseAsSpannable(text));
        }
        a.recycle();
    }
}
