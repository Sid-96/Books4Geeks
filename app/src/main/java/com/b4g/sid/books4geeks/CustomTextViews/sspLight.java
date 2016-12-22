package com.b4g.sid.books4geeks.CustomTextViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.b4g.sid.books4geeks.Util.FontUtil;

/**
 * Created by Sid on 22-Dec-16.
 * Reference taken from https://futurestud.io/tutorials/custom-fonts-on-android-extending-textview ...
 */

public class sspLight extends TextView {
    public sspLight(Context context) {
        super(context);
        applyCustomFont();
    }

    public sspLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont();
    }

    public sspLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont();
    }

    public sspLight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont();
    }

    private void applyCustomFont(){
        Typeface customFont = FontUtil.getTypeFace(FontUtil.SSP_LIGHT);
        setTypeface(customFont);
    }
}
