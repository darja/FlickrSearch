package com.darja.flickrsearch.util;

import android.text.InputFilter;
import android.text.Spanned;

public class TrimInputFilter implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        if ((dest.length() == 0 || dest.toString().endsWith(" ") || dest.toString().endsWith("\n")) &&
            source.toString().trim().length() == 0) {
            return "";
        }
        return source;
    }
}