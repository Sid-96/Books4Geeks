package com.b4g.sid.books4geeks.CustomTextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.b4g.sid.books4geeks.FontUtil;

/**
 * Created by Sid on 22-Dec-16.
 * Reference taken from https://futurestud.io/tutorials/custom-fonts-on-android-extending-textview ...
 */

public class sspRegular extends TextView {
    public sspRegular(Context context) {
        super(context);
        applyCustomFont();
    }

    public sspRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont();
    }

    public sspRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont();
    }

    public sspRegular(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont();
    }

    private void applyCustomFont(){
        Typeface customFont = FontUtil.getTypeFace(FontUtil.SSP_REGULAR);
        setTypeface(customFont);
    }
}
