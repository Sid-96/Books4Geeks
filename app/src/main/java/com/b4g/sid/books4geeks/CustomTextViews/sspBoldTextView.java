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

public class sspBoldTextView extends TextView {


    public sspBoldTextView(Context context) {
        super(context);
        applyCustomFont();
    }

    public sspBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont();
    }

    public sspBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont();
    }

    public sspBoldTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont();
    }

    private void applyCustomFont(){
        Typeface customFont = FontUtil.getTypeFace(FontUtil.SSP_BOLD);
        setTypeface(customFont);
    }
}
