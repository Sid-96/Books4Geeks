package com.b4g.sid.books4geeks;

import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Created by Sid on 22-Dec-16.
 * Reference taken from https://futurestud.io/tutorials/custom-fonts-on-android-extending-textview ...
 */

public class FontUtil {

    public static final String SSP_REGULAR = "fonts/SourceSansPro-Regular.ttf";
    public static final String SSP_LIGHT = "fonts/SourceSansPro-Light.ttf";
    public static final String SSP_BOLD = "fonts/SourceSansPro-Bold.ttf";

    private static HashMap<String,Typeface> mp = new HashMap<>();

    public static Typeface getTypeFace(String name){
        Typeface tf = mp.get(name);
        if(tf==null){
            try{
                tf = Typeface.createFromAsset(B4GAppClass.getAppContext().getAssets(),name);
            }
            catch (Exception e){
                return null;
            }
        }
        mp.put(name,tf);
        return tf;
    }
}
