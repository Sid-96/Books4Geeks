package com.b4g.sid.books4geeks;

import android.app.Application;
import android.content.Context;

/**
 * Created by Sid on 22-Dec-16.
 */

public class B4GAppClass extends Application {


    public static final String TOOLBAR_TITLE = "toolbar_title";
    public static final String LIST_NAME = "list_name";
    public static final String TAG_BESTSELLER_FRAGMENT = "bestseller_fragment";
    public static final String CURRENT_STATE = "current_state";
    public static final String BESTSELLER_LIST = "bestseller_list";
    public static final int CURRENT_STATE_LOADING = 1;
    public static final int CURRENT_STATE_LOADED = 2;
    public static final int CURRENT_STATE_FAILED = 3;
    public static final int CURRENT_STATE_LOCKED = 4;
    public static final String BESTSELLER_BACK = "back";
    public static final String ISBN_NO = "isbn_number";
    public static final String TAG_CATALOG_FRAGMENT = "book_detail";
    public static final String SEARCH_QUERY = "search_query";
    public static final String SEARCH_LIST = "search_list";
    public static final String BOOK_DETAIL = "book_detail";
    public static final String MSG_VISIBILITY = "msg_visible";
    public static final String BOOK_SHELF = "book_shelf";
    public static final String START_INDEX = "start_index";
    public static final String TOTAL_ITEMS = "total_items";
    public static final String SORT = "sort";
    public static final int SORT_TITLE = 1;
    public static final int SORT_AUTHOR = 2;

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
