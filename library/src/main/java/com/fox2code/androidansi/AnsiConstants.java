package com.fox2code.androidansi;

import android.graphics.Color;

import androidx.annotation.ColorInt;

// https://gist.github.com/Prakasaka/219fe5695beeb4d6311583e79933a009
// https://en.wikipedia.org/wiki/ANSI_escape_code#Colors
public final class AnsiConstants {
    // Recommended command to explicitly tell a shell script that ANSI is supported.
    public static final String ANSI_CMD_SUPPORT = "export ANSI_SUPPORT=true";
    public static final ColorTransformer NO_OP_TRANSFORMER = new ColorTransformer() {
        @ColorInt
        @Override
        public int transform(@ColorInt int color, int role) {
            return color;
        }
    };

    public static final int FLAG_STYLE_BOLD        = 0x0001;
    public static final int FLAG_STYLE_DIM         = 0x0002;
    public static final int FLAG_STYLE_ITALIC      = 0x0004;
    public static final int FLAG_STYLE_UNDERLINE   = 0x0008;
    public static final int FLAG_STYLE_STRIKE      = 0x0010;
    public static final int FLAG_STYLE_SUBSCRIPT   = 0x0020;
    public static final int FLAG_STYLE_SUPERSCRIPT = 0x0040;

    // Many terminals have different colors, make our own list
    @ColorInt public static final int
            COLOR_BLACK   = Color.rgb(0, 0, 0),
            COLOR_RED     = Color.rgb(205, 0, 0),
            COLOR_GREEN   = Color.rgb(0, 205, 0),
            COLOR_YELLOW  = Color.rgb(205, 205, 0),
            COLOR_BLUE    = Color.rgb(0, 0, 205),
            COLOR_MAGENTA = Color.rgb(205, 0, 205),
            COLOR_CYAN    = Color.rgb(0, 205, 205),
            COLOR_WHITE   = Color.rgb(229, 229, 229),
            COLOR_BRIGHT_BLACK   = Color.rgb(127, 127, 127),
            COLOR_BRIGHT_RED     = Color.rgb(255, 0, 0),
            COLOR_BRIGHT_GREEN   = Color.rgb(0, 255, 0),
            COLOR_BRIGHT_YELLOW  = Color.rgb(255, 255, 0),
            COLOR_BRIGHT_BLUE    = Color.rgb(0, 0, 255),
            COLOR_BRIGHT_MAGENTA = Color.rgb(255, 0, 255),
            COLOR_BRIGHT_CYAN    = Color.rgb(0, 255, 255),
            COLOR_BRIGHT_WHITE   = Color.rgb(255, 255, 255);

    @ColorInt
    static int colorForAnsiCode(int colorAnsi) {
        int m = colorAnsi / 10;
        if (m == 4 || m == 10)
            colorAnsi -= 10;
        switch (colorAnsi) {
            case 30:
                return COLOR_BLACK;
            case 31:
                return COLOR_RED;
            case 32:
                return COLOR_GREEN;
            case 33:
                return COLOR_YELLOW;
            case 34:
                return COLOR_BLUE;
            case 35:
                return COLOR_MAGENTA;
            case 36:
                return COLOR_CYAN;
            case 37:
                return COLOR_WHITE;
            case 90:
                return COLOR_BRIGHT_BLACK;
            case 91:
                return COLOR_BRIGHT_RED;
            case 92:
                return COLOR_BRIGHT_GREEN;
            case 93:
                return COLOR_BRIGHT_YELLOW;
            case 94:
                return COLOR_BRIGHT_BLUE;
            case 95:
                return COLOR_BRIGHT_MAGENTA;
            case 96:
                return COLOR_BRIGHT_CYAN;
            case 97:
                return COLOR_BRIGHT_WHITE;
            default:
                throw new IllegalArgumentException(colorAnsi + " is not a static color ansiCode");
        }
    }

    private AnsiConstants() {}
}
