package com.b4g.sid.books4geeks;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sid on 22-Dec-16.
 */

public class B4GAppClass extends Application {


    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
