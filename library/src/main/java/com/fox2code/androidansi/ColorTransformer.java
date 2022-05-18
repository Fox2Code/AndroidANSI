package com.fox2code.androidansi;

import androidx.annotation.ColorInt;

public interface ColorTransformer {
    int ROLE_FOREGROUND = 0;
    int ROLE_BACKGROUND = 1;
    int ROLE_UNDERLINE  = 2;

    @ColorInt int transform(@ColorInt int color, int role);
}
