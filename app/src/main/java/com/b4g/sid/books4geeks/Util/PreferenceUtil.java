package com.b4g.sid.books4geeks.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.b4g.sid.books4geeks.B4GAppClass;

/**
 * Created by Sid on 05-Jan-17.
 */

public class PreferenceUtil {

    private static final String SORT_TYPE = "sort_type";
    private static final String PREFERENCE_NAME = "user_settings";

    private static SharedPreferences getPreferences(){
        return B4GAppClass.getAppContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static void setSortType(int sortType){
        getPreferences().edit().putInt(SORT_TYPE,sortType).commit();
    }

    public static int getSortType(){
        return getPreferences().getInt(SORT_TYPE,B4GAppClass.SORT_TITLE);
    }
}
