package com.b4g.sid.books4geeks.Util;

import android.util.DisplayMetrics;

import com.b4g.sid.books4geeks.B4GAppClass;

/**
 * Created by Sid on 25-Dec-16.
 */

public class DimensionUtil {

    private DimensionUtil(){

    }

    public static int getNumberOfColumns(int dimenId, int minColumns){

        DisplayMetrics displayMetrics = B4GAppClass.getAppContext().getResources().getDisplayMetrics();

        float widthPx = displayMetrics.widthPixels;

        float desiredPx = B4GAppClass.getAppContext().getResources().getDimensionPixelSize(dimenId);

        int columns = Math.round(widthPx/desiredPx);

        return columns>minColumns?columns:minColumns;
    }
}
